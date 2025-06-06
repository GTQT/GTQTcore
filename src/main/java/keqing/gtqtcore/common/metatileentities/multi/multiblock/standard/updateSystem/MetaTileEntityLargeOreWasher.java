package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.updateSystem;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import com.llamalad7.mixinextras.lib.apache.commons.ArrayUtils;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.CubeRendererState;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.cclop.ColourOperation;
import gregtech.client.renderer.cclop.LightMapOperation;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.BloomEffectUtil;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metaileentity.GTQTRecipeMapMultiblockController;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class MetaTileEntityLargeOreWasher extends GTQTRecipeMapMultiblockController {
    private int coilLevel;
    private int casingTier;
    private int tubeTier;

    public MetaTileEntityLargeOreWasher(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.ORE_WASHER_RECIPES,
                RecipeMaps.CHEMICAL_BATH_RECIPES
        });

        setTierFlag(true);
        //setTier(auto);
        setMaxParallel(64);
        setMaxParallelFlag(true);
        //setMaxVoltage(auto);
        setMaxVoltageFlag(true);
        //setTimeReduce(coilLevel);
        setTimeReduceFlag(true);
        setOverclocking(0.33);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(),
                recipeMapWorkable.isActive(), recipeMapWorkable.isWorkingEnabled());
        if (recipeMapWorkable.isActive() && isStructureFormed()) {
            EnumFacing back = getFrontFacing().getOpposite();
            for (float i = -2; i <= 2; i++) {
                for (float j = -2; j <= 2; j++) {
                    Matrix4 offset = translation.copy().translate(back.getXOffset() * 3 + i, 0.6, back.getZOffset() * 3 + j);
                    CubeRendererState op = Textures.RENDER_STATE.get();
                    Textures.RENDER_STATE.set(new CubeRendererState(op.layer, CubeRendererState.PASS_MASK, op.world));
                    Textures.renderFace(renderState, offset,
                            ArrayUtils.addAll(pipeline, new LightMapOperation(240, 240), new ColourOperation(0xFF00FFFF)),
                            EnumFacing.UP, Cuboid6.full, TextureUtils.getBlockTexture("water_still"),
                            BloomEffectUtil.getEffectiveBloomLayer());
                    Textures.RENDER_STATE.set(op);
                }
            }
        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLargeOreWasher(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXXXX", "XXXXXXX", "XXXXXXX")
                .aisle("XCCXCCX", "XPPXPPX", "X#####X")
                .aisle("XCXXXCX", "XPPXPPX", "X#####X")
                .aisle("XXXXXXX", "XXXXXXX", "X#####X")
                .aisle("XCXXXCX", "XPPXPPX", "X#####X")
                .aisle("XCCXCCX", "XPPXPPX", "X#####X")
                .aisle("XXXXXXX", "XXXSXXX", "XXXXXXX")
                .where('S', selfPredicate())
                .where('X', TiredTraceabilityPredicate.CP_CASING.get().setMinGlobalLimited(40)
                        .or(autoAbilities()))
                .where('P', TiredTraceabilityPredicate.CP_TUBE.get())
                .where('C', heatingCoils())
                .where('#', any())
                .build();
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("gtqtcore.coilTire", coilLevel));
        textList.add(new TextComponentTranslation("gtqtcore.casingTire", casingTier));
        textList.add(new TextComponentTranslation("gtqtcore.tubeTire", tubeTier));
        if (casingTier != tubeTier)
            textList.add(new TextComponentTranslation("gtqtcore.equal", casingTier, tubeTier));
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        switch (this.casingTier) {
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
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object coilType = context.get("CoilType");
        Object casingTier = context.get("ChemicalPlantCasingTieredStats");
        Object tubeTier = context.get("ChemicalPlantTubeTieredStats");
        this.coilLevel = GTQTUtil.getOrDefault(() -> coilType instanceof IHeatingCoilBlockStats,
                () -> ((IHeatingCoilBlockStats) coilType).getLevel(),
                BlockWireCoil.CoilType.CUPRONICKEL.getLevel());
        this.casingTier = GTQTUtil.getOrDefault(() -> casingTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) casingTier).getIntTier(),
                0);
        this.tubeTier = GTQTUtil.getOrDefault(() -> tubeTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) tubeTier).getIntTier(),
                0);

        setTier(Math.min(this.casingTier, this.tubeTier));
        setMaxVoltage(Math.min(this.casingTier, this.tubeTier));
        setTimeReduce((100 - Math.min(coilLevel, 10) * 5.0) / 100);

        this.writeCustomData(GTQTValue.UPDATE_TIER20, buf -> buf.writeInt(this.casingTier));
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.casingTier);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.casingTier = buf.readInt();
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GTQTValue.UPDATE_TIER20) {
            this.casingTier = buf.readInt();
        }
        if (dataId == GTQTValue.REQUIRE_DATA_UPDATE20) {
            this.writeCustomData(GTQTValue.UPDATE_TIER20, buf1 -> buf1.writeInt(this.casingTier));
        }
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }
}