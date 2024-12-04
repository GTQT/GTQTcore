package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.ArrayList;
import java.util.List;

public class MetaTileEntityBioHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IBio>, IBio {

    private final FluidTank waterTank;
    private final FluidTank bioTank;
    private final int tier;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("WaterTank", waterTank.writeToNBT(new NBTTagCompound()));
        data.setTag("BioTank", bioTank.writeToNBT(new NBTTagCompound()));
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        waterTank.readFromNBT(data.getCompoundTag("WaterTank"));
        bioTank.readFromNBT(data.getCompoundTag("BioTank"));
    }

    public MetaTileEntityBioHatch(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.waterTank = new FluidTank(16000); // 设置水槽容量为16000 mB
        this.bioTank = new FluidTank(16000);   // 设置生物流体槽容量为16000 mB
        this.tier = tier;
    }
    public void update() {
        super.update();
        if (!this.getWorld().isRemote) {
            this.pullFluidsFromNearbyHandlers(this.getFrontFacing());
        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityBioHatch(metaTileEntityId, getTier());
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.defaultBuilder();

        // Water Tank Section
        builder.widget(new LabelWidget(10, 10, "Water Tank", 0xFFFFFF)); // Adjusted position and color
        builder.widget(new TankWidget(waterTank, 10, 20, 24, 64) // Adjusted size and position
                .setBackgroundTexture(GuiTextures.SLOT) // Changed texture for better appearance
                .setContainerClicking(true, true)
                .setAlwaysShowFull(true)
                .setHideTooltip(false));
        builder.widget(new AdvancedTextWidget(40, 40, this::addWaterTankAmountText, 16777215).setMaxWidthLimit(181));
        builder.widget(new AdvancedTextWidget(40, 50, this::addWaterTankFluidText, 16777215).setMaxWidthLimit(181));

        // Bio Tank Section
        builder.widget(new LabelWidget(100, 10, "Bio Tank", 0xFFFFFF)); // Adjusted position and color
        builder.widget(new TankWidget(bioTank, 100, 20, 24, 64) // Adjusted size and position
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
        textList.add(new TextComponentString("Amount: " + waterTank.getFluidAmount()));
    }

    protected void addWaterTankFluidText(List<ITextComponent> textList) {
        if(waterTank.getFluid()!=null)textList.add(new TextComponentString("Fluid: " + waterTank.getFluid().getLocalizedName()));
        else textList.add(new TextComponentString("Fluid: null"));
    }

    protected void addBioTankAmountText(List<ITextComponent> textList) {
        textList.add(new TextComponentString("Amount: " + bioTank.getFluidAmount()));
    }

    protected void addBioTankFluidText(List<ITextComponent> textList) {
        if(bioTank.getFluid()!=null)textList.add(new TextComponentString("Fluid: " + bioTank.getFluid().getLocalizedName()));
        else textList.add(new TextComponentString("Fluid: null"));
    }

    public MultiblockAbility<IBio> getAbility() {
        return GTQTMultiblockAbility.BIO_MULTIBLOCK_ABILITY;
    }

    public void registerAbilities(List<IBio> list) {
        list.add(this);
    }

    @Override
    public int getWaterAmount() {
        return waterTank.getFluidAmount();
    }

    @Override
    public int getBioAmount() {
        return bioTank.getFluidAmount();
    }

    @Override
    public int getTier() {
        return tier;
    }

    public IFluidHandler getWaterTank() {
        return waterTank;
    }

    public IFluidHandler getBioTank() {
        return bioTank;
    }
}

