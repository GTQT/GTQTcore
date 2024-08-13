package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;


import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.shader.postprocessing.BloomEffect;


import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.GTQTDataCode;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUniverUtil;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.client.utils.BloomEffectUtil;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTADVBlock;
import keqing.gtqtcore.common.block.blocks.GTQTADVGlass;
import keqing.gtqtcore.common.block.blocks.GTQTQuantumCasing;
import keqing.gtqtcore.common.block.blocks.GTQTQuantumForceTransformerCasing;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static gregtech.api.GTValues.UHV;

public class MetaTileEntityQuantumForceTransformer extends RecipeMapMultiblockController implements IFastRenderMetaTileEntity {
    private int ManipulatorCasingTier;
    private int manioulatorTier;
    private int coreTier;
    private int glassTier;
    private int tier;
    private static boolean init = false;
    private static List<IBlockState> finalListManipulatorCasing;
    private static List<IBlockState> finalListShieldingCoreCasing;
    private static List<IBlockState> finalListGlass;

    public MetaTileEntityQuantumForceTransformer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.QUANTUM_FORCE_TRANSFORMER_RECIPES);
        this.recipeMapWorkable = new MetaTileEntityQuantumForceTransformerHandler(this);
    }
    @Override
    public boolean canBeDistinct() {return true;}
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityQuantumForceTransformer(metaTileEntityId);
    }



    @Override
    public void update() {
        super.update();

        if (this.getWorld().isRemote && this.ManipulatorCasingTier == 0) {
            this.writeCustomData(GTQTDataCode.GTQT_CHANNEL_9, buf -> {});
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("    A     A    ", "    A     A    ", "    A     A    ", "   BA     AB   ", "   BABBABBAB   ", "   BAAAAAAAB   ", "   BBBBABBBB   ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "               ", "               ", "  A         A  ", "  A         A  ", "  B         B  ", "  BAAAAAAAAAB  ", "   AAABBBAAA   ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "               ", "               ", " A           A ", " A           A ", " B           B ", " BAA       AAB ", "  AA       AA  ", "    AA   AA    ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("A             A", "A             A", "A             A", "A             A", "A             A", "B             B", "BAA         AAB", " AA         AA ", "   AA     AA   ", "     BAAAB     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("      HHH      ", "      EEE      ", "      EEE      ", "      EEE      ", "B     DDD     B", "B     EEE     B", "BA    DDD    AB", " A    EEE    A ", "  AA  EEE  AA  ", "    BAEEEAB    ", "      DDD      ", "      EEE      ", "      EEE      ", "      EEE      ", "      DDD      ", "      EEE      ", "      DDD      ", "      EEE      ", "      EEE      ", "      EEE      ", "      HHH      ")
                .aisle("     HHHHH     ", "     ECCCE     ", "     ECCCE     ", "B    ECCCE    B", "B    D   D    B", "B    ECCCE    B", "BA   D   D   AB", " A   ECCCE   A ", "  A  ECCCE  A  ", "   BAECCCEAB   ", "     D   D     ", "     ECCCE     ", "     ECCCE     ", "     ECCCE     ", "     D   D     ", "     ECCCE     ", "     D   D     ", "     ECCCE     ", "     ECCCE     ", "     ECCCE     ", "     HHHHH     ")
                .aisle("    HHHHHHH    ", "    EC   CE    ", "A   EC   CE   A", "A   EC   CE   A", "A   D     D   A", "A   EC   CE   A", "BA  D     D  AB", "BB  EC   CE  BB", " B  EC   CE  B ", "  BAEC   CEAB  ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    ECCCCCE    ", "    HHHHHHH    ")
                .aisle("    HHHHHHH    ", "    EC   CE    ", "    EC   CE    ", "    EC   CE    ", "A   D     D   A", "A   EC   CE   A", "AA  D     D  AA", "AB  EC   CE  BA", " A  EC   CE  A ", "  AAEC   CEAA  ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    ECCCCCE    ", "    HHHHHHH    ")
                .aisle("    HHHHHHH    ", "    EC   CE    ", "    EC   CE    ", "A   EC   CE   A", "A   D     D   A", "A   EC   CE   A", "BA  D     D  AB", "BB  EC   CE  BB", " B  EC   CE  B ", "  BAEC   CEAB  ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    ECCCCCE    ", "    HHHHHHH    ")
                .aisle("     HHHHH     ", "     ECCCE     ", "     ECCCE     ", "B    ECCCE    B", "B    D   D    B", "B    ECCCE    B", "BA   D   D   AB", " A   ECCCE   A ", "  A  ECCCE  A  ", "   BAECCCEAB   ", "     D   D     ", "     ECCCE     ", "     ECCCE     ", "     ECCCE     ", "     D   D     ", "     ECCCE     ", "     D   D     ", "     ECCCE     ", "     ECCCE     ", "     ECCCE     ", "     HHHHH     ")
                .aisle("      HSH      ", "      EEE      ", "      EEE      ", "      EEE      ", "B     DDD     B", "B     EEE     B", "BA    DDD    AB", " A    EEE    A ", "  AA  EEE  AA  ", "    BAEEEAB    ", "      DDD      ", "      EEE      ", "      EEE      ", "      EEE      ", "      DDD      ", "      EEE      ", "      DDD      ", "      EEE      ", "      EEE      ", "      EEE      ", "      HHH      ")
                .aisle("A             A", "A             A", "A             A", "A             A", "A             A", "B             B", "BAA          AB", " AA         AA ", "   AA     AA   ", "     BAAAB     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "               ", "               ", " A           A ", " A           A ", " B           B ", " BA         AB ", "  AA       AA  ", "    AA   AA    ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "               ", "               ", "  A         A  ", "  A         A  ", "  B         B  ", "  BAAAAAAAAAB  ", "   AAABBBAAA   ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("    A     A    ", "    A     A    ", "    A     A    ", "   BA     AB   ", "   BABBABBAB   ", "   BAAAAAAAB   ", "   BBBBABBBB   ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .where('S', this.selfPredicate())
                .where('A', TiredTraceabilityPredicate.QFT_MANIPULATOR.get())
                .where('B', TiredTraceabilityPredicate.QFT_SHIELDING_CORE.get())
                .where('C', states(getCoilState()))
                .where('D', states(getCasingState()))
                .where('E', TiredTraceabilityPredicate.QFT_GLASS.get())
                .where('H', states(getCasingState())
                        .setMinGlobalLimited(55)
                        .or(autoAbilities()))
                .build();
    }

    private IBlockState getCasingState() {
        return GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.QUANTUM_CONSTRAINT_CASING);
    }


    private IBlockState getCoilState() {
        return GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.QUANTUM_FORCE_TRANSFORMER_COIL);
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.QUANTUM_CONSTRAINT_CASING;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.machine.quantum_force_transformer.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.quantum_force_transformer.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.quantum_force_transformer.tooltip.3"));
        tooltip.add(I18n.format("结构内玻璃每升级一次，耗能减百分之五"));
        tooltip.add(I18n.format("结构内核心每升级一次，耗时减百分之十"));
        tooltip.add(I18n.format("结构内主体升级一次，并行翻倍"));
    }

    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (this.isStructureFormed()) {

            textList.add(new TextComponentTranslation("gtqtcore.multiblock.md.level", coreTier));
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.md.glass", glassTier));
            textList.add(new TextComponentTranslation("gtqtcore.casingTire", manioulatorTier));
            textList.add(new TextComponentTranslation("gtqtcore.tire", ManipulatorCasingTier));
            textList.add(new TextComponentTranslation("gregtech.multiblock.cracking_unit.energy", 100 - 10 * this.glassTier));
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.fu.level", 10*coreTier));
        }

    }


    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object manioulatorTier = context.get("ManipulatprTieredStats");
        Object coreTier = context.get("CoreTieredStats");
        Object glassTier = context.get("QFTGlassTieredStats");
        this.manioulatorTier = GTQTUtil.getOrDefault(() -> manioulatorTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)manioulatorTier).getIntTier(),
                0);
        this.coreTier = GTQTUtil.getOrDefault(() -> coreTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)coreTier).getIntTier(),
                0);
        this.glassTier = GTQTUtil.getOrDefault(() -> glassTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)glassTier).getIntTier(),
                0);

        this.tier = this.ManipulatorCasingTier = this.coreTier = this.glassTier;

        this.writeCustomData(GTQTDataCode.GTQT_CHANNEL_8, buf -> buf.writeInt(this.ManipulatorCasingTier));
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GTQTDataCode.GTQT_CHANNEL_8) {
            this.ManipulatorCasingTier = buf.readInt();
        }
        if (dataId == GTQTDataCode.GTQT_CHANNEL_9) {
            this.writeCustomData(GTQTDataCode.GTQT_CHANNEL_8, buf1 -> buf1.writeInt(this.ManipulatorCasingTier));
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.ManipulatorCasingTier);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.ManipulatorCasingTier = buf.readInt();
    }

    @SideOnly(Side.CLIENT)
    private void renderForceField(BufferBuilder buffer, double x, double y, double z, int side, double minU, double maxU, double minV, double maxV) {

        switch (side) {
            case 0 -> {
                buffer.pos(x + 3, y, z + 7).tex(maxU, maxV).endVertex();
                buffer.pos(x + 3, y + 4, z + 7).tex(maxU, minV).endVertex();
                buffer.pos(x - 3, y + 4, z + 7).tex(minU, minV).endVertex();
                buffer.pos(x - 3, y, z + 7).tex(minU, maxV).endVertex();
            }
            case 1 -> {
                buffer.pos(x + 7, y, z + 4).tex(maxU, maxV).endVertex();
                buffer.pos(x + 7, y + 4, z + 4).tex(maxU, minV).endVertex();
                buffer.pos(x + 7, y + 4, z - 4).tex(minU, minV).endVertex();
                buffer.pos(x + 7, y, z - 4).tex(minU, maxV).endVertex();
            }
            case 2 -> {
                buffer.pos(x + 3, y, z - 7).tex(maxU, maxV).endVertex();
                buffer.pos(x + 3, y + 4, z - 7).tex(maxU, minV).endVertex();
                buffer.pos(x - 3, y + 4, z - 7).tex(minU, minV).endVertex();
                buffer.pos(x - 3, y, z - 7).tex(minU, maxV).endVertex();
            }
            case 3 -> {
                buffer.pos(x - 7, y, z + 4).tex(maxU, maxV).endVertex();
                buffer.pos(x - 7, y + 4, z + 4).tex(maxU, minV).endVertex();
                buffer.pos(x - 7, y + 4, z - 4).tex(minU, minV).endVertex();
                buffer.pos(x - 7, y, z - 4).tex(minU, maxV).endVertex();
            }
            case 4 -> {
                buffer.pos(x - 3, y, z + 7).tex(maxU, maxV).endVertex();
                buffer.pos(x - 3, y + 4, z + 7).tex(maxU, minV).endVertex();
                buffer.pos(x - 7, y + 4, z + 4).tex(minU, minV).endVertex();
                buffer.pos(x - 7, y, z + 4).tex(minU, maxV).endVertex();
            }
            case 5 -> {
                buffer.pos(x - 3, y, z - 7).tex(maxU, maxV).endVertex();
                buffer.pos(x - 3, y + 4, z - 7).tex(maxU, minV).endVertex();
                buffer.pos(x - 7, y + 4, z - 4).tex(minU, minV).endVertex();
                buffer.pos(x - 7, y, z - 4).tex(minU, maxV).endVertex();
            }
            case 6 -> {
                buffer.pos(x + 3, y, z + 7).tex(maxU, maxV).endVertex();
                buffer.pos(x + 3, y + 4, z + 7).tex(maxU, minV).endVertex();
                buffer.pos(x + 7, y + 4, z + 4).tex(minU, minV).endVertex();
                buffer.pos(x + 7, y, z + 4).tex(minU, maxV).endVertex();
            }
            case 7 -> {
                buffer.pos(x + 3, y, z - 7).tex(maxU, maxV).endVertex();
                buffer.pos(x + 3, y + 4, z - 7).tex(maxU, minV).endVertex();
                buffer.pos(x + 7, y + 4, z - 4).tex(minU, minV).endVertex();
                buffer.pos(x + 7, y, z - 4).tex(minU, maxV).endVertex();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(double x, double y, double z, float partialTicks) {
        TextureAtlasSprite forceField = GTQTTextures.FORCE_FIELD;
        if (isActive() && MinecraftForgeClient.getRenderPass() == 0) {

            BloomEffectUtil.requestCustomBloom(RENDER_HANDLER, (buffer) -> {
                Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
                if (entity != null) {
                    double minU = forceField.getMinU();
                    double maxU = forceField.getMaxU();
                    double minV = forceField.getMinV();
                    double maxV = forceField.getMaxV();
                    double xBaseOffset = 3 * getFrontFacing().getOpposite().getXOffset();
                    double zBaseOffset = 3 * getFrontFacing().getOpposite().getZOffset();
                    GlStateManager.pushMatrix();
                    GlStateManager.disableCull();
                    GlStateManager.disableAlpha();
                    GlStateManager.enableBlend();
                    Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                    //Center O:  0,  0         1 ------- 8
                    //Corner 1:  7, -2        /           \
                    //Corner 2:  3, -6     2 /             \ 7
                    //Corner 3: -2, -6      |               |
                    //Corner 4: -6, -2      |       O       |
                    //Corner 5: -6,  3      |               |
                    //Corner 6: -2,  7     3 \             / 6
                    //Corner 7:  3,  7        \           /
                    //Corner 8:  7,  3         4 ------- 5
                    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                    GlStateManager.translate(x + xBaseOffset + 0.5, 0 , z + zBaseOffset + 0.5);
                    if (zBaseOffset == 0) {
                        GlStateManager.rotate(90F, 0F, 1F, 0F);
                    }
                    for (int i = 0; i < 8; i++) {
                        renderForceField(buffer, 0, y, 0, i, minU, maxU, minV, maxV);
                    }
                    Tessellator.getInstance().draw();
                    GlStateManager.disableBlend();
                    GlStateManager.enableAlpha();
                    GlStateManager.enableCull();
                    GlStateManager.popMatrix();
                }
            });
        }
    }

    public AxisAlignedBB getRenderBoundingBox() {
        double xBaseOffset = 3 * getFrontFacing().getOpposite().getXOffset();
        double zBaseOffset = 3 * getFrontFacing().getOpposite().getZOffset();
        BlockPos pos = new BlockPos(this.getPos().getX() + xBaseOffset + 0.5, this.getPos().getY() , this.getPos().getZ() + zBaseOffset + 0.5);
        return new AxisAlignedBB(pos).grow(6, 6, 6);
    }

    public boolean shouldRenderInPass(int pass) {
        return pass == 0;
    }

    public boolean isGlobalRenderer() {
        return true;
    }

    static BloomEffectUtil.IBloomRenderFast RENDER_HANDLER = new BloomEffectUtil.IBloomRenderFast() {
        @Override
        public int customBloomStyle() {
            return 2;
        }

        float lastBrightnessX;
        float lastBrightnessY;

        @Override
        @SideOnly(Side.CLIENT)
        public void preDraw(BufferBuilder buffer) {
            BloomEffect.strength = 1.5F;
            BloomEffect.baseBrightness = 0.0F;
            BloomEffect.highBrightnessThreshold = 1.3F;
            BloomEffect.lowBrightnessThreshold = 0.3F;
            BloomEffect.step = 1;

            lastBrightnessX = OpenGlHelper.lastBrightnessX;
            lastBrightnessY = OpenGlHelper.lastBrightnessY;
            GlStateManager.color(1, 1, 1, 1);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void postDraw(BufferBuilder buffer) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
        }
    };
    protected int getGlassTire() {
        return this.glassTier;
    }
    public static int getMaxParallel(int manioulatorTier) {
        return manioulatorTier;
    }
    private class MetaTileEntityQuantumForceTransformerHandler extends MultiblockRecipeLogic {

        public MetaTileEntityQuantumForceTransformerHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = maxProgress*(100-coreTier*5)/100;

        }

        @Override
        public int getParallelLimit() {
            return getMaxParallel(manioulatorTier);
        }

        protected void modifyOverclockPost(int[] resultOverclock, @Nonnull IRecipePropertyStorage storage) {
            super.modifyOverclockPost(resultOverclock, storage);
            int glassTier = ((MetaTileEntityQuantumForceTransformer)this.metaTileEntity).getGlassTire();
            if (glassTier > 0) {
                resultOverclock[0] = (int)((double)resultOverclock[0] * (100 - (double)glassTier*3)/100);
                resultOverclock[0] = Math.max(1, resultOverclock[0]);
            }
        }
    }
}
