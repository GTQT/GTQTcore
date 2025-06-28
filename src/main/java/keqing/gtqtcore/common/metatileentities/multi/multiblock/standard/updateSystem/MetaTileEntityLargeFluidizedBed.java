package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.updateSystem;

import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.metatileentity.multiblock.ui.KeyManager;
import gregtech.api.metatileentity.multiblock.ui.UISyncer;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.KeyUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.ICatalystHatch;
import keqing.gtqtcore.api.metatileentity.GTQTRecipeMapMultiblockController;
import keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.CatalystProperties;
import keqing.gtqtcore.api.recipes.properties.ChemicalPlantProperties;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class MetaTileEntityLargeFluidizedBed extends GTQTRecipeMapMultiblockController {
    private int coilLevel;
    private int casingTier;
    private int tubeTier;

    public MetaTileEntityLargeFluidizedBed(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.FLUIDIZED_BED
        });
        this.recipeMapWorkable = new FluidizedBedLogic(this);
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
        return new MetaTileEntityLargeFluidizedBed(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCC", "CCCCC", "CCCCC", "CCCCC", "CCCCC")
                .aisle("CCCCC", "UUFUU", "UPFPU", "UUFUU", "CCCCC")
                .aisle("CCCCC", "CPFPC", "CPFPC", "CPFPC", "CCCCC")
                .aisle("CCCCC", "UUFUU", "UPFPU", "UUFUU", "CCCCC")
                .aisle("CCCCC", "CCCCC", "CCSCC", "CCCCC", "CCCCC")
                .where('S', selfPredicate())
                .where('C', TiredTraceabilityPredicate.CP_CASING.get()
                        .or(autoAbilities())
                        .or(abilities(GTQTMultiblockAbility.CATALYST_MULTIBLOCK_ABILITY).setMinGlobalLimited(0).setPreviewCount(1)))
                .where('P', TiredTraceabilityPredicate.CP_TUBE.get())
                .where('U', heatingCoils())
                .where('F', states(getFrameState()))
                .where(' ', any())
                .build();
    }

    private IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Aluminium).getBlock(Materials.Aluminium);
    }

    @Override
    public void addCustomData(KeyManager keyManager, UISyncer syncer) {
        super.addCustomData(keyManager, syncer);
        keyManager.add(KeyUtil.lang(TextFormatting.GRAY, "gtqtcore.coilTire" , syncer.syncInt(coilLevel)));
        keyManager.add(KeyUtil.lang(TextFormatting.GRAY, "gtqtcore.casingTire" , syncer.syncInt(casingTier)));
        keyManager.add(KeyUtil.lang(TextFormatting.GRAY, "gtqtcore.tubeTire" , syncer.syncInt(tubeTier)));
        if (casingTier != tubeTier)
            keyManager.add(KeyUtil.lang(TextFormatting.GRAY, "gtqtcore.equal" , syncer.syncInt(casingTier), syncer.syncInt(tubeTier)));
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

        this.writeCustomData(GTQTValue.UPDATE_TIER30, buf -> buf.writeInt(this.casingTier));
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
        if (dataId == GTQTValue.UPDATE_TIER30) {
            this.casingTier = buf.readInt();
        }
        if (dataId == GTQTValue.REQUIRE_DATA_UPDATE30) {
            this.writeCustomData(GTQTValue.UPDATE_TIER30, buf1 -> buf1.writeInt(this.casingTier));
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

    public ICatalystHatch getCatalystHatch() {
        List<ICatalystHatch> abilities = getAbilities(GTQTMultiblockAbility.CATALYST_MULTIBLOCK_ABILITY);
        if (abilities.isEmpty())
            return null;
        return abilities.get(0);
    }

    protected class FluidizedBedLogic extends GTQTMultiblockLogic {

        public FluidizedBedLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public boolean checkRecipe(@Nonnull Recipe recipe) {
            if (!super.checkRecipe(recipe)) {
                return false;
            }

            ChemicalPlantProperties chemicalProps = ChemicalPlantProperties.getInstance();
            CatalystProperties catalystProps = CatalystProperties.getInstance();

            // Check chemical plant properties
            if (recipe.hasProperty(chemicalProps)) {
                Integer tierValue = recipe.getProperty(chemicalProps, 0);
                if (tierValue == null || tierValue > tier) {
                    return false;
                }
            }

            // Check catalyst properties
            if (recipe.hasProperty(catalystProps)) {
                ItemStack catalystStack = recipe.getProperty(catalystProps, ItemStack.EMPTY);
                if (catalystStack != null && !catalystStack.isEmpty()) {
                    try {
                        if (getCatalystHatch().hasCatalyst(catalystStack)) {
                            getCatalystHatch().consumeCatalyst(catalystStack, 1);
                            return true;
                        }
                    } catch (Exception e) {
                        return false;
                    }
                    return false;
                }
            }

            return true;
        }
    }
}