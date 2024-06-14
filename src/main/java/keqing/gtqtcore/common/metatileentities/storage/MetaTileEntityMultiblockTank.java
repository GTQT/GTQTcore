package keqing.gtqtcore.common.metatileentities.storage;

import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.PropertyFluidFilter;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockSteamCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;

import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing1;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;

import java.util.List;

public class MetaTileEntityMultiblockTank extends MultiblockWithDisplayBase {

    private final int tier;

    public MetaTileEntityMultiblockTank(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId);
        this.tier = tier;
        initializeInventory();
    }

    public int getCapacity()
    {
        return 1000000*(int)Math.pow(2, this.tier);
    }
    @Override
    protected void initializeInventory() {
        super.initializeInventory();

        FilteredFluidHandler tank = new FilteredFluidHandler(getCapacity());
        if (tier<=2) {
            tank.setFilter(new PropertyFluidFilter(340, true, true, true, true));
        }

        this.exportFluids = this.importFluids = new FluidTankList(true, tank);
        this.fluidInventory = tank;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMultiblockTank(metaTileEntityId, tier);
    }

    @Override
    protected void updateFormedValid() {}

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "X X", "XXX")
                .aisle("XXX", "XSX", "XXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(23)
                        .or(metaTileEntities(getValve()).setMaxGlobalLimited(2)))
                .where(' ', air())
                .build();
    }

    private IBlockState getCasingState() {
        switch (this.tier) {
            case (2) -> {
                return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
            }
            case (3) -> {
                return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.ALUMINIUM_FROSTPROOF);
            }
            case (4) -> {
                return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
            }
            case (5) -> {
                return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TITANIUM_STABLE);
            }
            case (6) -> {
                return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST);
            }
            case (7) -> {
                return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.PD_TURBINE_CASING);
            }
            case (8) -> {
                return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_TURBINE_CASING);
            }
            case (9) -> {
                return GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.ST_TURBINE_CASING);
            }
            case (10) -> {
                return GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.AD_TURBINE_CASING);
            }
            default -> {
                return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.BRONZE_BRICKS);
            }
        }
    }

    private MetaTileEntity getValve() {
            return MetaTileEntities.STEEL_TANK_VALVE;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        switch (this.tier) {
            case (2) -> {
                return Textures.SOLID_STEEL_CASING;
            }
            case (3) -> {
                return Textures.FROST_PROOF_CASING;
            }
            case (4) -> {
                return Textures.CLEAN_STAINLESS_STEEL_CASING;
            }
            case (5) -> {
                return Textures.STABLE_TITANIUM_CASING;
            }
            case (6) -> {
                return Textures.ROBUST_TUNGSTENSTEEL_CASING;
            }
            case (7) -> {
                return GTQTTextures.PD_CASING;
            }
            case (8) -> {
                return GTQTTextures.NQ_CASING;
            }
            case (9) -> {
                return GTQTTextures.ST_CASING;
            }
            case (10) -> {
                return GTQTTextures.AD_CASING;
            }
            default -> {
                return Textures.BRONZE_PLATED_BRICKS;
            }
        }
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                CuboidRayTraceResult hitResult) {
        if (!isStructureFormed())
            return false;
        return super.onRightClick(playerIn, hand, facing, hitResult);
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return isStructureFormed();
    }

    @Override
    protected ModularUI.Builder createUITemplate( EntityPlayer entityPlayer) {
        return ModularUI.defaultBuilder()
                .widget(new LabelWidget(6, 6, getMetaFullName()))
                .widget(new TankWidget(importFluids.getTankAt(0), 52, 18, 72, 61)
                        .setBackgroundTexture(GuiTextures.SLOT)
                        .setContainerClicking(false, false))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @SideOnly(Side.CLIENT)

    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.MULTIBLOCK_TANK_OVERLAY;
    }

    @Override
    public void addInformation(ItemStack stack,  World player,  List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.multiblock.tank.tooltip"));
        tooltip.add(I18n.format("gregtech.universal.tooltip.fluid_storage_capacity", getCapacity()));
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (isStructureFormed()) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidInventory);
            } else {
                return null;
            }
        }
        return super.getCapability(capability, side);
    }
}