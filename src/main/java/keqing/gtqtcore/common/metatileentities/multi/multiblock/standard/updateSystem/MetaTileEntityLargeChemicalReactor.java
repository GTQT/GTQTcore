package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.updateSystem;

import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.ui.KeyManager;
import gregtech.api.metatileentity.multiblock.ui.UISyncer;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.KeyUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metatileentity.GTQTRecipeMapMultiblockController;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class MetaTileEntityLargeChemicalReactor extends GTQTRecipeMapMultiblockController {
    private int coilLevel;
    private int casingTier;
    private int tubeTier;

    public MetaTileEntityLargeChemicalReactor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.CHEMICAL_RECIPES,
                GTQTcoreRecipeMaps.POLYMERIZATION_RECIPES
        });
        setTierFlag(true);
        //setTier(auto);
        setMaxParallel(64);
        setMaxParallelFlag(true);
        //setMaxVoltage(auto);
        setMaxVoltageFlag(true);
        //setTimeReduce(coilLevel);
        setTimeReduceFlag(true);
        setOverclocking(3);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLargeChemicalReactor(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("X###X", "XXXXX", "X###X", "XXXXX", "X###X")
                .aisle("XXXXX", "XPPPX", "XCCCX", "XPPPX", "XXXXX")
                .aisle("XXXXX", "XPPPX", "XCPCX", "XPPPX", "XXXXX")
                .aisle("XXXXX", "XPPPX", "XCCCX", "XPPPX", "XXXXX")
                .aisle("X###X", "SXXXX", "X###X", "XXXXX", "X###X")
                .where('S', selfPredicate())
                .where('X', TiredTraceabilityPredicate.CP_CASING.get().setMinGlobalLimited(40)
                        .or(autoAbilities()))
                .where('P', TiredTraceabilityPredicate.CP_TUBE.get())
                .where('C', heatingCoils())
                .where('#', any())
                .build();
    }

    @Override
    public void addCustomData(KeyManager keyManager, UISyncer syncer) {
        super.addCustomData(keyManager, syncer);
        Integer syncedCoil = syncer.syncInt(coilLevel);
        Integer syncedCasing = syncer.syncInt(casingTier);
        Integer syncedTube = syncer.syncInt(tubeTier);

        keyManager.add(KeyUtil.lang(TextFormatting.GRAY ,"gtqtcore.coilTire" , syncedCoil));
        keyManager.add(KeyUtil.lang(TextFormatting.GRAY, "gtqtcore.casingTire", syncedCasing));
        keyManager.add(KeyUtil.lang(TextFormatting.GRAY, "gtqtcore.tubeTire", syncedTube));

        if (casingTier != tubeTier) {
            keyManager.add(KeyUtil.lang(TextFormatting.GRAY, "gtqtcore.equal", syncedCasing, syncedTube));
        }
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

        this.writeCustomData(GTQTValue.UPDATE_TIER17, buf -> buf.writeInt(this.casingTier));
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
        if (dataId == GTQTValue.UPDATE_TIER17) {
            this.casingTier = buf.readInt();
        }
        if (dataId == GTQTValue.REQUIRE_DATA_UPDATE17) {
            this.writeCustomData(GTQTValue.UPDATE_TIER17, buf1 -> buf1.writeInt(this.casingTier));
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