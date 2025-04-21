package keqing.gtqtcore.common.metatileentities.multi.multiblock.primitive;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
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
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtsteam.api.metatileentity.multiblock.RecipeMapNoEnergyMultiblockController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;

public class MetaTileEntityPrimitiveRoaster extends RecipeMapNoEnergyMultiblockController {

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

    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.PRIMITIVE_BACKGROUND, 176, 204).shouldColor(false).widget(new TankWidget(this.importFluids.getTankAt(0), 22, 25, 20, 58).setBackgroundTexture(GuiTextures.PRIMITIVE_LARGE_FLUID_TANK).setOverlayTexture(GuiTextures.PRIMITIVE_LARGE_FLUID_TANK_OVERLAY).setContainerClicking(true, true)).widget(new LabelWidget(5, 5, this.getMetaFullName())).widget((new SlotWidget(this.importItems, 0, 52, 25, true, true)).setBackgroundTexture(GuiTextures.PRIMITIVE_SLOT, GuiTextures.PRIMITIVE_FURNACE_OVERLAY)).widget((new SlotWidget(this.importItems, 1, 52, 45, true, true)).setBackgroundTexture(GuiTextures.PRIMITIVE_SLOT, GuiTextures.PRIMITIVE_FURNACE_OVERLAY)).widget((new SlotWidget(this.importItems, 2, 52, 65, true, true)).setBackgroundTexture(GuiTextures.PRIMITIVE_SLOT, GuiTextures.PRIMITIVE_FURNACE_OVERLAY)).widget(new RecipeProgressWidget(this.recipeMapWorkable::getProgressPercent, 76, 46, 20, 15, GuiTextures.PRIMITIVE_BLAST_FURNACE_PROGRESS_BAR, ProgressWidget.MoveType.HORIZONTAL, GTQTcoreRecipeMaps.PRIMITIVE_ROASTING_RECIPES)).widget((new SlotWidget(this.exportItems, 0, 103, 25, true, false)).setBackgroundTexture(GuiTextures.PRIMITIVE_SLOT, GuiTextures.PRIMITIVE_FURNACE_OVERLAY)).widget((new SlotWidget(this.exportItems, 1, 103, 45, true, false)).setBackgroundTexture(GuiTextures.PRIMITIVE_SLOT, GuiTextures.PRIMITIVE_FURNACE_OVERLAY)).widget((new SlotWidget(this.exportItems, 2, 103, 65, true, false)).setBackgroundTexture(GuiTextures.PRIMITIVE_SLOT, GuiTextures.PRIMITIVE_FURNACE_OVERLAY)).widget((new TankWidget(this.exportFluids.getTankAt(0), 134, 25, 20, 58)).setBackgroundTexture(GuiTextures.PRIMITIVE_LARGE_FLUID_TANK).setOverlayTexture(GuiTextures.PRIMITIVE_LARGE_FLUID_TANK_OVERLAY).setContainerClicking(true, false)).bindPlayerInventory(entityPlayer.inventory, GuiTextures.PRIMITIVE_SLOT, 37);
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