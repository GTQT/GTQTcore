package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import keqing.gtqtcore.api.capability.IBio;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;
import java.util.List;

public class MetaTileEntityBioHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IBio>, IBio {


    private final FluidTankList fluidTankList;
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        if(fluidTankList!=null)
            data.setTag("fluidlist", fluidTankList.serializeNBT());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        if(data.hasKey("fluidlist"))
            fluidTankList.deserializeNBT(data.getCompoundTag("fluidlist"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (this.shouldRenderOverlay()) {
            GTQTTextures.CATALYST_HATCH.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }

    public MetaTileEntityBioHatch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, 3);
        FluidTank[] list= new FluidTank[2];
        FluidTank water = new FluidTank(4000){
            @Override
            public boolean canFillFluidType(FluidStack fluid) {
                return (fluid==null?false:fluid.getFluid()==FluidRegistry.WATER);
            }
        };
        FluidTank bio = new FluidTank(4000){
            @Override
            public boolean canFillFluidType(FluidStack fluid) {
                return (fluid==null?false:fluid.getFluid()==Materials.Biomass.getFluid());
            }
        };
        list[0] = water;
        list[1] = bio;
        this.fluidTankList = new FluidTankList(false,list);
    }
    public void update() {
        super.update();
        if (!this.getWorld().isRemote) {
            this.pullFluidsFromNearbyHandlers(this.getFrontFacing());
        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityBioHatch(metaTileEntityId);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.defaultBuilder();

        // Water Tank Section
        builder.widget(new LabelWidget(10, 10, "Water Tank", 0xFFFFFF)); // Adjusted position and color
        builder.widget(new TankWidget(fluidTankList.getTankAt(0), 10, 20, 24, 64) // Adjusted size and position
                .setBackgroundTexture(GuiTextures.SLOT) // Changed texture for better appearance
                .setContainerClicking(true, true)
                .setAlwaysShowFull(true)
                .setHideTooltip(false));
        builder.widget(new AdvancedTextWidget(40, 40, this::addWaterTankAmountText, 16777215).setMaxWidthLimit(181));
        builder.widget(new AdvancedTextWidget(40, 50, this::addWaterTankFluidText, 16777215).setMaxWidthLimit(181));

        // Bio Tank Section
        builder.widget(new LabelWidget(100, 10, "Bio Tank", 0xFFFFFF)); // Adjusted position and color
        builder.widget(new TankWidget(fluidTankList.getTankAt(1), 100, 20, 24, 64) // Adjusted size and position
                .setBackgroundTexture(GuiTextures.SLOT) // Changed texture for better appearance
                .setContainerClicking(true, true)
                .setAlwaysShowFull(true)
                .setHideTooltip(false));
        builder.widget(new AdvancedTextWidget(130, 40, this::addBioTankAmountText, 16777215).setMaxWidthLimit(181));
        builder.widget(new AdvancedTextWidget(130, 50, this::addBioTankFluidText, 16777215).setMaxWidthLimit(181));

        // Player Inventory
        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 7, 85); // Adjusted position

        return builder.build(this.getHolder(), entityPlayer);
    }

    protected void addWaterTankAmountText(List<ITextComponent> textList) {
        textList.add(new TextComponentString("Amount: " + fluidTankList.getTankAt(0).getFluidAmount()));
    }

    protected void addWaterTankFluidText(List<ITextComponent> textList) {
        if(fluidTankList.getTankAt(0).getFluid()!=null)textList.add(new TextComponentString("Fluid: " + fluidTankList.getTankAt(0).getFluid().getLocalizedName()));
        else textList.add(new TextComponentString("Fluid: null"));
    }

    protected void addBioTankAmountText(List<ITextComponent> textList) {
        textList.add(new TextComponentString("Amount: " + fluidTankList.getTankAt(1).getFluidAmount()));
    }

    protected void addBioTankFluidText(List<ITextComponent> textList) {
        if(fluidTankList.getTankAt(1).getFluid()!=null)textList.add(new TextComponentString("Fluid: " + fluidTankList.getTankAt(1).getFluid().getLocalizedName()));
        else textList.add(new TextComponentString("Fluid: null"));
    }

    public MultiblockAbility<IBio> getAbility() {
        return GTQTMultiblockAbility.BIO_MULTIBLOCK_ABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.fluidTankList);
        }
        return super.getCapability(capability, side);
    }

    public void registerAbilities(List<IBio> list) {
        list.add(this);
    }

    @Override
    public int getWaterAmount() {
        return fluidTankList.getTankAt(0).getFluidAmount();
    }

    @Override
    public int getBioAmount() {
        return fluidTankList.getTankAt(1).getFluidAmount();
    }

    @Override
    public boolean drainWaterAmount(int amount) {
        if(amount>=fluidTankList.getTankAt(0).getFluidAmount()) {
            fluidTankList.getTankAt(0).drain(amount, true);
            return true;
        }
        return false;
    }

    @Override
    public boolean drainBioAmount(int amount) {
        if(amount>=fluidTankList.getTankAt(1).getFluidAmount()) {
            fluidTankList.getTankAt(1).drain(amount, true);
            return true;
        }
        return false;
    }

    public IFluidHandler getWaterTank() {
        return fluidTankList.getTankAt(0);
    }

    public IFluidHandler getBioTank() {
        return fluidTankList.getTankAt(1);
    }


}

