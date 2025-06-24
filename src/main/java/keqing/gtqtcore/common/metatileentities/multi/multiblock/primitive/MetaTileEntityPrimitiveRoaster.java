package keqing.gtqtcore.common.metatileentities.multi.multiblock.primitive;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.widgets.ItemSlot;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import gregtech.api.GTValues;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapPrimitiveMultiblockController;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIFactory;
import gregtech.api.mui.GTGuiTextures;
import gregtech.api.mui.GTGuiTheme;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.CubeRendererState;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.cclop.ColourOperation;
import gregtech.client.renderer.cclop.LightMapOperation;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.BloomEffectUtil;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.mui.widget.GTFluidSlot;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtsteam.api.metatileentity.multiblock.NoEnergyMultiblockController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;

public class MetaTileEntityPrimitiveRoaster extends RecipeMapPrimitiveMultiblockController {

    public MetaTileEntityPrimitiveRoaster(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.PRIMITIVE_ROASTING_RECIPES);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.BACK, RelativeDirection.UP).aisle("F#F", "#C#", "F#F").aisle("FCF", "C C", "FSF").aisle("HHH", "H H", "HHH").aisle("#C#", "C C", "#C#").aisle("#C#", "C C", "#C#").where('S', selfPredicate()).where('#', TraceabilityPredicate.ANY).where(' ', TraceabilityPredicate.AIR).where('C', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS))).where('H', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS)).or(autoAbilities())).where('F', states(MetaBlocks.FRAMES.get(Materials.Invar).getBlock(Materials.Invar))).build();
    }

    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.PRIMITIVE_BRICKS;
    }
    @Override
    protected MultiblockUIFactory createUIFactory() {
        return new MultiblockUIFactory(this)
                .disableButtons()
                .disableDisplay()
                .setSize(176, 166)
                .addScreenChildren((parent, syncManager) -> {
                    // 标题位置调整（居中）
                    parent.child(IKey.lang(getMetaFullName()).asWidget().pos(5, 5));

                    // 输入流体槽（左侧）
                    parent.child(new GTFluidSlot()
                            .overlay(GTGuiTextures.PRIMITIVE_LARGE_FLUID_TANK_OVERLAY.asIcon()
                                    .alignment(Alignment.CenterLeft)
                                    .marginLeft(1))
                            .syncHandler(GTFluidSlot.sync(importFluids.getTankAt(0))
                                    .drawAlwaysFull(false)
                                    .showAmountOnSlot(false)
                                    .accessibility(true, false))
                            .pos(5, 2) // 移动到最左侧
                            .size(20, 58));

                    // 输入物品槽 - 2x2布局 (4个槽位)
                    for (int y = 0; y < 2; y++) {
                        for (int x = 0; x < 2; x++) {
                            int index = y * 2 + x;
                            parent.child(new ItemSlot()
                                    .slot(new ModularSlot(importItems, index)
                                            .singletonSlotGroup())
                                    .pos(30 + x * 18, 30 + y * 18)); // X坐标从30开始
                        }
                    }

                    // 进度条（居中）
                    parent.child(new ProgressWidget()
                            .texture(GTGuiTextures.PRIMITIVE_BLAST_FURNACE_PROGRESS_BAR, -1)
                            .size(20, 15)
                            .pos(76, 39) // 调整到物品槽中间位置
                            .value(new DoubleSyncValue(recipeMapWorkable::getProgressPercent)));

                    // 输出物品槽 - 2x2布局 (4个槽位)
                    for (int y = 0; y < 2; y++) {
                        for (int x = 0; x < 2; x++) {
                            int index = y * 2 + x;
                            parent.child(new ItemSlot()
                                    .slot(new ModularSlot(exportItems, index)
                                            .accessibility(false, true))
                                    .pos(106 + x * 18, 30 + y * 18)); // X坐标从106开始
                        }
                    }

                    // 输出流体槽（右侧）
                    parent.child(new GTFluidSlot()
                            .overlay(GTGuiTextures.PRIMITIVE_LARGE_FLUID_TANK_OVERLAY.asIcon()
                                    .alignment(Alignment.CenterLeft)
                                    .marginLeft(1))
                            .syncHandler(GTFluidSlot.sync(exportFluids.getTankAt(0))
                                    .drawAlwaysFull(false)
                                    .showAmountOnSlot(false)
                                    .accessibility(true, false))
                            .pos(151, 20) // 移动到最右侧（176-20-5=151）
                            .size(20, 58));
                });
    }

    @Override
    public GTGuiTheme getUITheme() {
        return GTGuiTheme.PRIMITIVE;
    }

    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, this.getFrontFacing(), this.recipeMapWorkable.isActive(), this.recipeMapWorkable.isWorkingEnabled());
        if (this.recipeMapWorkable.isActive() && this.isStructureFormed()) {
            EnumFacing back = this.getFrontFacing().getOpposite();
            Matrix4 offset = translation.copy().translate(back.getXOffset(), -0.3, back.getZOffset());
            CubeRendererState op = Textures.RENDER_STATE.get();
            Textures.RENDER_STATE.set(new CubeRendererState(op.layer, CubeRendererState.PASS_MASK, op.world));
            Textures.renderFace(renderState, offset, ArrayUtils.addAll(pipeline, new LightMapOperation(240, 240), new ColourOperation(-1)), EnumFacing.UP, Cuboid6.full, TextureUtils.getBlockTexture("lava_still"), BloomEffectUtil.getEffectiveBloomLayer());
            Textures.RENDER_STATE.set(op);
        }

    }

    @Nonnull
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ROASTER_OVERLAY;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPrimitiveRoaster(this.metaTileEntityId);
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public void update() {
        super.update();
        if (this.isActive()) {
            if (getWorld().isRemote) {
                pollutionParticles();
            }
        }
    }

    private void pollutionParticles() {
        BlockPos pos = this.getPos();
        EnumFacing facing = this.getFrontFacing().getOpposite();
        float xPos = (float) facing.getXOffset() * 0.76F + (float) pos.getX() + 0.5F;
        float yPos = (float) facing.getYOffset() * 0.76F + (float) pos.getY() + 0.25F;
        float zPos = (float) facing.getZOffset() * 0.76F + (float) pos.getZ() + 0.5F;
        float ySpd = (float) facing.getYOffset() * 0.1F + 0.2F + 0.1F * GTValues.RNG.nextFloat();
        arunMufflerEffect(xPos, yPos, zPos, 0, ySpd, 0);
    }

    public void arunMufflerEffect(float xPos, float yPos, float zPos, float xSpd, float ySpd, float zSpd) {
        this.getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, xPos, yPos, zPos, xSpd, ySpd, zSpd);
    }

}