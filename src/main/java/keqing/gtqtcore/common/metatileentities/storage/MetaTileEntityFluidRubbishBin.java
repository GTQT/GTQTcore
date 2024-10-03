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
    private final int color=Steel.getMaterialRGB();
    private final int tankSize=16000;
    private FilteredFluidHandler fluidTank;

    public MetaTileEntityFluidRubbishBin(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        IPropertyFluidFilter filter = (IPropertyFluidFilter)Neutronium.getProperty(PropertyKey.FLUID_PIPE);
        if (filter == null) {
            throw new IllegalArgumentException("Material " + Steel + " requires FluidPipeProperty for Drums");
        } else {
            this.fluidFilter = filter;
            this.initializeInventory();
        }
    }

    public MetaTileEntityFluidRubbishBin(ResourceLocation metaTileEntityId,  IPropertyFluidFilter fluidFilter) {
        super(metaTileEntityId);
        this.fluidFilter = fluidFilter;
        this.initializeInventory();
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityFluidRubbishBin(this.metaTileEntityId, this.fluidFilter);
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

    public ICapabilityProvider initItemStackCapabilities(ItemStack itemStack) {
        return (new GTFluidHandlerItemStack(itemStack, this.tankSize)).setFilter(this.fluidTank.getFilter());
    }

    public void update() {
        super.update();
        if (!this.getWorld().isRemote&&fluidTank.getFluid()!=null) {
            fluidTank.drain(new FluidStack(fluidTank.getFluid(),fluidTank.getFluidAmount()),true);
        }
    }

    @SideOnly(Side.CLIENT)
    public Pair<TextureAtlasSprite, Integer> getParticleTexture() {

        int color = GTUtility.convertOpaqueRGBA_CLtoRGB(ColourRGBA.multiply(GTUtility.convertRGBtoOpaqueRGBA_CL(this.color), GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering())));return Pair.of(Textures.DRUM.getParticleTexture(), color);

    }
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        ColourMultiplier multiplier;

        multiplier = new ColourMultiplier(ColourRGBA.multiply(GTUtility.convertRGBtoOpaqueRGBA_CL(this.color), GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering())));Textures.DRUM.render(renderState, translation, (IVertexOperation[])ArrayUtils.add(pipeline, multiplier), this.getFrontFacing());Textures.DRUM_OVERLAY.render(renderState, translation, pipeline);

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
    protected boolean shouldSerializeInventories() {
        return false;
    }
}
