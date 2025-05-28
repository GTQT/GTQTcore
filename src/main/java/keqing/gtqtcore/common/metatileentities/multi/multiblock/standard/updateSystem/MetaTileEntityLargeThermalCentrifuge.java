package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.updateSystem;

import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metaileentity.GTQTRecipeMapMultiblockController;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class MetaTileEntityLargeThermalCentrifuge extends GTQTRecipeMapMultiblockController {
    private int coilLevel;
    private int casingTier;
    private int tubeTier;

    public MetaTileEntityLargeThermalCentrifuge(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.THERMAL_CENTRIFUGE_RECIPES,
                RecipeMaps.CENTRIFUGE_RECIPES
        });
        setTierFlag(true);
        //setTier(auto);
        setMaxParallel(64);
        setMaxParallelFlag(true);
        //setMaxVoltage(auto);
        setMaxVoltageFlag(true);
        //setTimeReduce(coilLevel);
        setTimeReduceFlag(true);
        setOverclocking(3.0);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLargeThermalCentrifuge(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" CCC ", " CUC ", " CCC ", "  C  ", "  C  ", " CCC ", " CUC ", " CCC ")
                .aisle("CCCCC", "CP PC", "CG GC", " P P ", " P P ", "CG GC", "CP PC", " CCC ")
                .aisle("CCCCC", "U F U", "C F C", "C F C", "C F C", "C F C", "U F U", " CCC ")
                .aisle("CCCCC", "CP PC", "CG GC", " P P ", " P P ", "CG GC", "CP PC", " CCC ")
                .aisle(" CCC ", " CSC ", " CCC ", "  C  ", "  C  ", " CCC ", " CUC ", " CCC ")
                .where('S', selfPredicate())
                .where('C', TiredTraceabilityPredicate.CP_CASING.get()
                        .or(autoAbilities()))
                .where('P', TiredTraceabilityPredicate.CP_TUBE.get())
                .where('U', heatingCoils())
                .where('G', heatingCoils())
                .where('F', states(getFrameState()))
                .where('#', any())
                .build();
    }

    private IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Aluminium).getBlock(Materials.Aluminium);
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

        this.writeCustomData(GTQTValue.UPDATE_TIER21, buf -> buf.writeInt(this.casingTier));
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
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GTQTValue.UPDATE_TIER21) {
            this.casingTier = buf.readInt();
        }
        if (dataId == GTQTValue.REQUIRE_DATA_UPDATE21) {
            this.writeCustomData(GTQTValue.UPDATE_TIER21, buf1 -> buf1.writeInt(this.casingTier));
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