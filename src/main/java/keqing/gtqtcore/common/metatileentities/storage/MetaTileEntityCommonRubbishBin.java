package keqing.gtqtcore.common.metatileentities.storage;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IPropertyFluidFilter;
import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.GTFluidHandlerItemStack;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;

import static gregtech.api.unification.material.Materials.Neutronium;
import static gregtech.api.unification.material.Materials.Steel;

public class MetaTileEntityCommonRubbishBin extends MetaTileEntity{
    private final Material material= Steel;
    private final int inventorySize=1;
    protected ItemStackHandler inventory;
    private final IPropertyFluidFilter fluidFilter;
    private final int tankSize=4000;
    private FilteredFluidHandler fluidTank;

    public MetaTileEntityCommonRubbishBin(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        IPropertyFluidFilter filter = (IPropertyFluidFilter)Neutronium.getProperty(PropertyKey.FLUID_PIPE);
        if (filter == null) {
            throw new IllegalArgumentException("Material " + Steel + " requires FluidPipeProperty for Drums");
        } else {
            this.fluidFilter = filter;
            this.initializeInventory();
        }
        this.initializeInventory();
    }
    public MetaTileEntityCommonRubbishBin(ResourceLocation metaTileEntityId,  IPropertyFluidFilter fluidFilter) {
        super(metaTileEntityId);
        this.fluidFilter = fluidFilter;
        this.initializeInventory();
    }
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCommonRubbishBin(this.metaTileEntityId, this.fluidFilter);

    }

    public boolean hasFrontFacing() {
        return false;
    }

    protected void initializeInventory() {
        super.initializeInventory();
        this.inventory = new GTItemStackHandler(this, this.inventorySize);
        this.itemInventory = this.inventory;
        if (this.fluidFilter != null) {
            super.initializeInventory();
            this.fluidTank = (new FilteredFluidHandler(this.tankSize)).setFilter(this.fluidFilter);
            this.fluidInventory = this.fluidTank;
        }
    }


    public ICapabilityProvider initItemStackCapabilities(ItemStack itemStack) {
        return (new GTFluidHandlerItemStack(itemStack, this.tankSize)).setFilter(this.fluidTank.getFilter());
    }

    
    public void update() {
        super.update();
        if (!this.getWorld().isRemote&& !this.inventory.getStackInSlot(0).isEmpty()) {
            this.inventory.setStackInSlot(0, ItemStack.EMPTY);
        }
        if (!this.getWorld().isRemote&&fluidTank.getFluid()!=null) {
            fluidTank.drain(new FluidStack(fluidTank.getFluid(),fluidTank.getFluidAmount()),true);
        }
    }

    public boolean showToolUsages() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public Pair<TextureAtlasSprite, Integer> getParticleTexture() {
        int color = ColourRGBA.multiply(GTUtility.convertRGBtoOpaqueRGBA_CL(this.material.getMaterialRGB()), GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering()));
        color = GTUtility.convertOpaqueRGBA_CLtoRGB(color);
        return Pair.of(Textures.METAL_CRATE.getParticleTexture(), color);

    }
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {

        int baseColor = ColourRGBA.multiply(GTUtility.convertRGBtoOpaqueRGBA_CL(this.material.getMaterialRGB()), GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering()));Textures.METAL_CRATE.render(renderState, translation, baseColor, pipeline);
    }
    public int getDefaultPaintingColor() {
        return 16777215;
    }

    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 130).label(10, 5, this.getMetaFullName());
        builder.slot(this.inventory, 0, 72 , 18 , GuiTextures.SLOT);
        builder.widget((new TankWidget(fluidTank, 90,18, 18, 18)).setContainerClicking(true,true).setBackgroundTexture(GuiTextures.FLUID_SLOT).setAlwaysShowFull(true));
        return builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 7, 48).build(this.getHolder(), entityPlayer);
    }
    public int getItemStackLimit(ItemStack stack) {
        return super.getItemStackLimit(stack);
    }


    protected boolean shouldSerializeInventories() {
        return false;
    }
}
