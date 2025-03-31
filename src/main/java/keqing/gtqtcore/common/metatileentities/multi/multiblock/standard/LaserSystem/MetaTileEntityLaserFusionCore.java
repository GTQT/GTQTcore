package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.LaserSystem;

import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.IRenderSetup;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.shader.postprocessing.BloomEffect;
import gregtech.client.shader.postprocessing.BloomType;
import gregtech.client.utils.BloomEffectUtil;
import gregtech.client.utils.EffectRenderContext;
import gregtech.client.utils.IBloomEffect;
import gregtech.client.utils.RenderBufferHelper;
import gregtech.common.ConfigHolder;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.capability.impl.MultiblockLaserRecipeLogic;
import keqing.gtqtcore.api.metaileentity.multiblock.RecipeMapLaserMultiblockController;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing7;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import static gregtech.api.metatileentity.multiblock.MultiblockAbility.*;
import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.LASER_INPUT;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockGlass.CasingType.ANTIMATTER_CONTAINMENT_CASING;
import static net.minecraft.util.EnumFacing.Axis.*;

public class MetaTileEntityLaserFusionCore extends RecipeMapLaserMultiblockController implements IBloomEffect, IFastRenderMetaTileEntity {

    protected static final int NO_COLOR = 0;
    boolean backA;
    int RadomTime;
    private int fusionRingColor = NO_COLOR;
    private boolean registeredBloomRenderTicket;

    public MetaTileEntityLaserFusionCore(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.FUSION_RECIPES);
        this.recipeMapWorkable = new MultiblockLaserRecipeLogic(this);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing7.getState(BlockMultiblockCasing7.CasingType.MAGNETIC_FIELD_CASING);
    }

    private static IBlockState getGlassState() {
        return GTQTMetaBlocks.blockMultiblockGlass.getState(ANTIMATTER_CONTAINMENT_CASING);
    }

    private static BloomType getBloomType() {
        ConfigHolder.FusionBloom fusionBloom = ConfigHolder.client.shader.fusionBloom;
        return BloomType.fromValue(fusionBloom.useShader ? fusionBloom.bloomStyle : -1);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLaserFusionCore(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("  AAA  ", " ABBBA ", "ABBBBBA", "ABBCBBA", "ABBBBBA", " ABBBA ", "  AAA  ")
                .aisle(" ABBBA ", "A     A", "B     B", "B     B", "B     B", "A     A", " ABBBA ")
                .aisle("ABBBBBA", "B     B", "B     B", "B     B", "B     B", "B     B", "ABBBBBA")
                .aisle("ABBCBBA", "B     B", "B     B", "C     C", "B     B", "B     B", "ABBCBBA")
                .aisle("ABBBBBA", "B     B", "B     B", "B     B", "B     B", "B     B", "ABBBBBA")
                .aisle(" ABBBA ", "A     A", "B     B", "B     B", "B     B", "A     A", " ABBBA ")
                .aisle("  ASA  ", " ABBBA ", "ABBBBBA", "ABBCBBA", "ABBBBBA", " ABBBA ", "  AAA  ")
                .where('S', this.selfPredicate())
                .where('C', abilities(LASER_INPUT))
                .where('A', states(getCasingState())
                        .or(abilities(MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(EXPORT_FLUIDS).setMaxGlobalLimited(1))
                        .or(abilities(IMPORT_FLUIDS).setMaxGlobalLimited(2))

                )
                .where('B', states(getGlassState()))

                .where(' ', any())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.MAGNETIC_FIELD_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    protected int getFusionRingColor() {
        return this.fusionRingColor;
    }

    protected void setFusionRingColor(int fusionRingColor) {
        if (this.fusionRingColor != fusionRingColor) {
            this.fusionRingColor = fusionRingColor;
        }
    }

    protected boolean hasFusionRingColor() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(double x, double y, double z, float partialTicks) {
        if (this.hasFusionRingColor() && !this.registeredBloomRenderTicket) {
            this.registeredBloomRenderTicket = true;
            BloomEffectUtil.registerBloomRender(FusionBloomSetup.INSTANCE, getBloomType(), this, this);
        }
    }

    @Override
    public void update() {
        super.update();
        if (!backA) if (RadomTime <= 10) RadomTime++;
        if (backA) if (RadomTime >= -10) RadomTime--;
        if (RadomTime == 10) {
            backA = true;
        }
        if (RadomTime == -10) {
            backA = false;
        }
        setFusionRingColor(0xFF000000 + RadomTime * 1250 * 50);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderBloomEffect(BufferBuilder buffer, EffectRenderContext context) {
        int color = this.getFusionRingColor();


        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        EnumFacing relativeBack = RelativeDirection.BACK.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());

        if (isStructureFormed()&&this.getRecipeMapWorkable().isActive()) {

            RenderBufferHelper.renderRing(buffer,
                    getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 3 + 0.5,
                    getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 3.5,
                    getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 3 + 0.5,
                    1, 2, 10, 20,
                    r, g, b, a, X);

            RenderBufferHelper.renderRing(buffer,
                    getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 3 + 0.5,
                    getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 3.5,
                    getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 3 + 0.5,
                    1, 2, 10, 20,
                    r, g, b, a, Z);

            RenderBufferHelper.renderRing(buffer,
                    getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 3 + 0.5,
                    getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 3.5,
                    getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 3 + 0.5,
                    1, 2, 10, 20,
                    r, g, b, a, Y);


        }

    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRenderBloomEffect(EffectRenderContext context) {
        return this.hasFusionRingColor();
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        EnumFacing relativeRight = RelativeDirection.RIGHT.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());
        EnumFacing relativeBack = RelativeDirection.BACK.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());

        return new AxisAlignedBB(
                this.getPos().offset(relativeBack).offset(relativeRight, 6),
                this.getPos().offset(relativeBack, 13).offset(relativeRight.getOpposite(), 6));
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 0;
    }

    @Override
    public boolean isGlobalRenderer() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    private static final class FusionBloomSetup implements IRenderSetup {

        private static final FusionBloomSetup INSTANCE = new FusionBloomSetup();

        float lastBrightnessX;
        float lastBrightnessY;

        @Override
        public void preDraw(BufferBuilder buffer) {
            BloomEffect.strength = (float) ConfigHolder.client.shader.fusionBloom.strength;
            BloomEffect.baseBrightness = (float) ConfigHolder.client.shader.fusionBloom.baseBrightness;
            BloomEffect.highBrightnessThreshold = (float) ConfigHolder.client.shader.fusionBloom.highBrightnessThreshold;
            BloomEffect.lowBrightnessThreshold = (float) ConfigHolder.client.shader.fusionBloom.lowBrightnessThreshold;
            BloomEffect.step = 1;

            lastBrightnessX = OpenGlHelper.lastBrightnessX;
            lastBrightnessY = OpenGlHelper.lastBrightnessY;
            GlStateManager.color(1, 1, 1, 1);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GlStateManager.disableTexture2D();

            buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
        }

        @Override
        public void postDraw(BufferBuilder buffer) {
            Tessellator.getInstance().draw();

            GlStateManager.enableTexture2D();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
        }
    }
}
