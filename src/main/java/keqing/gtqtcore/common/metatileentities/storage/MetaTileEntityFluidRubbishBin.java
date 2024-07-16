package keqing.gtqtcore.common.metatileentities.storage;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IPropertyFluidFilter;
import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.GTFluidHandlerItemStack;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.IGuiTexture;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.metatileentities.storage.MetaTileEntityDrum;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.List;

import static gregtech.api.unification.material.Materials.Neutronium;
import static gregtech.api.unification.material.Materials.Steel;

public class MetaTileEntityFluidRubbishBin extends MetaTileEntity{
    private final IPropertyFluidFilter fluidFilter;
    private final boolean isWood;
    private final int color;
    private final int tankSize;
    private FilteredFluidHandler fluidTank;
    private boolean isAutoOutput = false;

    public MetaTileEntityFluidRubbishBin(ResourceLocation metaTileEntityId,  Material material, int tankSize) {
        super(metaTileEntityId);
        IPropertyFluidFilter filter = (IPropertyFluidFilter)material.getProperty(PropertyKey.FLUID_PIPE);
        if (filter == null) {
            throw new IllegalArgumentException("Material " + material + " requires FluidPipeProperty for Drums");
        } else {
            this.fluidFilter = filter;
            this.isWood = ModHandler.isMaterialWood(material);
            this.color = material.getMaterialRGB();
            this.tankSize = tankSize;
            this.initializeInventory();
        }
    }

    public MetaTileEntityFluidRubbishBin(ResourceLocation metaTileEntityId,  IPropertyFluidFilter fluidFilter, boolean isWood, int color, int tankSize) {
        super(metaTileEntityId);
        this.fluidFilter = fluidFilter;
        this.isWood = isWood;
        this.color = color;
        this.tankSize = tankSize;
        this.initializeInventory();
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityFluidRubbishBin(this.metaTileEntityId, this.fluidFilter, this.isWood, this.color, this.tankSize);
    }

    public String getHarvestTool() {
        return this.isWood ? "axe" : "wrench";
    }

    public boolean hasFrontFacing() {
        return false;
    }

    protected void initializeInventory() {
        if (this.fluidFilter != null) {
            super.initializeInventory();
            this.fluidTank = (new FilteredFluidHandler(this.tankSize)).setFilter(this.fluidFilter);
            this.fluidInventory = this.fluidTank;
        }
    }

    public void initFromItemStackData(NBTTagCompound itemStack) {
        super.initFromItemStackData(itemStack);
        if (itemStack.hasKey("Fluid", 10)) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(itemStack.getCompoundTag("Fluid"));
            this.fluidTank.setFluid(fluidStack);
        }

    }

    public void writeItemStackData(NBTTagCompound itemStack) {
        super.writeItemStackData(itemStack);
        FluidStack fluidStack = this.fluidTank.getFluid();
        if (fluidStack != null && fluidStack.amount > 0) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            fluidStack.writeToNBT(tagCompound);
            itemStack.setTag("Fluid", tagCompound);
        }

    }

    public ICapabilityProvider initItemStackCapabilities(ItemStack itemStack) {
        return (new GTFluidHandlerItemStack(itemStack, this.tankSize)).setFilter(this.fluidTank.getFilter());
    }

    public void writeInitialSyncData( PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        FluidStack fluidStack = this.fluidTank.getFluid();
        buf.writeBoolean(fluidStack != null);
        if (fluidStack != null) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            fluidStack.writeToNBT(tagCompound);
            buf.writeCompoundTag(tagCompound);
        }

        buf.writeBoolean(this.isAutoOutput);
    }

    public void receiveInitialSyncData( PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        FluidStack fluidStack = null;
        if (buf.readBoolean()) {
            try {
                NBTTagCompound tagCompound = buf.readCompoundTag();
                fluidStack = FluidStack.loadFluidStackFromNBT(tagCompound);
            } catch (IOException var4) {
            }
        }

        this.fluidTank.setFluid(fluidStack);
        this.isAutoOutput = buf.readBoolean();
    }

    public void receiveCustomData(int dataId,  PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GregtechDataCodes.UPDATE_AUTO_OUTPUT) {
            this.isAutoOutput = buf.readBoolean();
            this.scheduleRenderUpdate();
        }

    }

    public void update() {
        super.update();
        if (!this.getWorld().isRemote&&fluidTank.getFluid()!=null) {
            fluidTank.drain(new FluidStack(fluidTank.getFluid(),fluidTank.getFluidAmount()),true);
        }
    }

    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if (!playerIn.getHeldItem(hand).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, (EnumFacing)null)) {
            return super.onRightClick(playerIn, hand, facing, hitResult);
        } else {
            return this.getWorld().isRemote || !playerIn.isSneaking() && FluidUtil.interactWithFluidHandler(playerIn, hand, this.fluidTank);
        }
    }

    public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing wrenchSide, CuboidRayTraceResult hitResult) {
        if (!playerIn.isSneaking()) {
            if (this.getWorld().isRemote) {
                this.scheduleRenderUpdate();
                return true;
            } else {
                playerIn.sendStatusMessage(new TextComponentTranslation("gregtech.machine.drum." + (this.isAutoOutput ? "disable" : "enable") + "_output", new Object[0]), true);
                this.toggleOutput();
                return true;
            }
        } else {
            return super.onScrewdriverClick(playerIn, hand, wrenchSide, hitResult);
        }
    }

    private void toggleOutput() {
        this.isAutoOutput = !this.isAutoOutput;
        if (!this.getWorld().isRemote) {
            this.notifyBlockUpdate();
            this.writeCustomData(GregtechDataCodes.UPDATE_AUTO_OUTPUT, (buf) -> {
                buf.writeBoolean(this.isAutoOutput);
            });
            this.markDirty();
        }

    }

    @SideOnly(Side.CLIENT)
    public Pair<TextureAtlasSprite, Integer> getParticleTexture() {
        if (this.isWood) {
            return Pair.of(Textures.WOODEN_DRUM.getParticleTexture(), this.getPaintingColorForRendering());
        } else {
            int color = GTUtility.convertOpaqueRGBA_CLtoRGB(ColourRGBA.multiply(GTUtility.convertRGBtoOpaqueRGBA_CL(this.color), GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering())));
            return Pair.of(Textures.DRUM.getParticleTexture(), color);
        }
    }

    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        ColourMultiplier multiplier;
        if (this.isWood) {
            multiplier = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering()));
            Textures.WOODEN_DRUM.render(renderState, translation, (IVertexOperation[])ArrayUtils.add(pipeline, multiplier), this.getFrontFacing());
        } else {
            multiplier = new ColourMultiplier(ColourRGBA.multiply(GTUtility.convertRGBtoOpaqueRGBA_CL(this.color), GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering())));
            Textures.DRUM.render(renderState, translation, (IVertexOperation[])ArrayUtils.add(pipeline, multiplier), this.getFrontFacing());
            Textures.DRUM_OVERLAY.render(renderState, translation, pipeline);
        }

        if (this.isAutoOutput) {
            Textures.STEAM_VENT_OVERLAY.renderSided(EnumFacing.DOWN, renderState, translation, pipeline);
        }

    }

    public int getDefaultPaintingColor() {
        return 16777215;
    }

    public boolean showToolUsages() {
        return false;
    }

    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 130).label(10, 5, this.getMetaFullName());
        builder.widget((new TankWidget(fluidTank, 81,18, 18, 18)).setContainerClicking(true,true).setBackgroundTexture(GuiTextures.FLUID_SLOT).setAlwaysShowFull(true));
        return builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 7, 48).build(this.getHolder(), entityPlayer);
    }
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("FluidInventory", ((FluidTank)this.fluidInventory).writeToNBT(new NBTTagCompound()));
        data.setBoolean("AutoOutput", this.isAutoOutput);
        return data;
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        ((FluidTank)this.fluidInventory).readFromNBT(data.getCompoundTag("FluidInventory"));
        this.isAutoOutput = data.getBoolean("AutoOutput");
    }

    protected boolean shouldSerializeInventories() {
        return false;
    }
}
