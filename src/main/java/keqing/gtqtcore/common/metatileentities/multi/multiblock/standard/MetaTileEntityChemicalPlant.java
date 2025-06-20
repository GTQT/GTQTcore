package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockWireCoil;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.ICatalystHatch;
import keqing.gtqtcore.api.metaileentity.GTQTRecipeMapMultiblockController;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.CatalystProperties;
import keqing.gtqtcore.api.recipes.properties.ChemicalPlantProperties;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import java.util.List;

public class MetaTileEntityChemicalPlant extends GTQTRecipeMapMultiblockController {

    private int coilLevel;
    private int casingTier;
    private int tubeTier;
    private int voltageTier;

    public MetaTileEntityChemicalPlant(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.CHEMICAL_PLANT
        });
        this.recipeMapWorkable = new ChemicalPlantLogic(this);
        setTierFlag(true);
        //setTier(auto);
        setMaxParallel(256);
        setMaxParallelFlag(true);
        //setMaxVoltage(auto);
        setMaxVoltageFlag(true);
        //setTimeReduce(coilLevel);
        setTimeReduceFlag(true);
        setOverclocking(0.33);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("EEEEEEE", "C#####C", "C#####C", "C#####C", "C#####C", "C#####C", "CCCCCCC")
                .aisle("EMMMMME", "#MMMMM#", "#######", "#######", "#######", "#MMMMM#", "CCCCCCC")
                .aisle("EMMMMME", "#MXXXM#", "##TTT##", "##XXX##", "##TTT##", "#MXXXM#", "CCCCCCC")
                .aisle("EMMMMME", "#MXAXM#", "##TAT##", "##XAX##", "##TAT##", "#MXAXM#", "CCCCCCC")
                .aisle("EMMMMME", "#MXXXM#", "##TTT##", "##XXX##", "##TTT##", "#MXXXM#", "CCCCCCC")
                .aisle("EMMMMME", "#MMMMM#", "#######", "#######", "#######", "#MMMMM#", "CCCCCCC")
                .aisle("EEESEEE", "C#####C", "C#####C", "C#####C", "C#####C", "C#####C", "CCCCCCC")
                .where('S', selfPredicate())
                .where('E', TiredTraceabilityPredicate.CP_CASING.get()
                        .or(autoAbilities())
                        .or(abilities(GTQTMultiblockAbility.CATALYST_MULTIBLOCK_ABILITY).setExactLimit(1)))
                .where('C', TiredTraceabilityPredicate.CP_CASING.get())
                .where('X', heatingCoils())
                .where('M', TiredTraceabilityPredicate.MACHINE_CASINGS.get())
                .where('T', TiredTraceabilityPredicate.CP_TUBE.get())
                .where('#', any())
                .where('A', air())
                .build();
    }

    public ICatalystHatch getCatalystHatch() {
        List<ICatalystHatch> abilities = getAbilities(GTQTMultiblockAbility.CATALYST_MULTIBLOCK_ABILITY);
        if (abilities.isEmpty())
            return null;
        return abilities.get(0);
    }

    @Override
    public boolean shouldShowVoidingModeButton() {
        return false;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.CHEMICAL_PLANT_OVERLAY;
    }

    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.recipeMapWorkable.isActive(), this.recipeMapWorkable.isWorkingEnabled());
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GTQTValue.UPDATE_TIER4) {
            this.casingTier = buf.readInt();
        }
        if (dataId == GTQTValue.REQUIRE_DATA_UPDATE4) {
            this.writeCustomData(GTQTValue.UPDATE_TIER4, buf1 -> buf1.writeInt(this.casingTier));
        }
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("gtqtcore.coilTire", coilLevel));
        textList.add(new TextComponentTranslation("gtqtcore.casingTire", casingTier));
        textList.add(new TextComponentTranslation("gtqtcore.tubeTire", tubeTier));
        textList.add(new TextComponentTranslation("gtqtcore.voltageTier", voltageTier));
        if (casingTier != tubeTier)
            textList.add(new TextComponentTranslation("gtqtcore.equal", casingTier, tubeTier));
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityChemicalPlant(metaTileEntityId);
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
        Object voltageTier = context.get("MachineCasingTypeTieredStats");
        this.coilLevel = GTQTUtil.getOrDefault(() -> coilType instanceof IHeatingCoilBlockStats,
                () -> ((IHeatingCoilBlockStats) coilType).getLevel(),
                BlockWireCoil.CoilType.CUPRONICKEL.getLevel());
        this.casingTier = GTQTUtil.getOrDefault(() -> casingTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) casingTier).getIntTier(),
                0);
        this.tubeTier = GTQTUtil.getOrDefault(() -> tubeTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) tubeTier).getIntTier(),
                0);
        this.voltageTier = GTQTUtil.getOrDefault(() -> voltageTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) voltageTier).getIntTier(),
                0);

        setTier(Math.min(this.casingTier, this.voltageTier));
        setMaxVoltage(Math.min(this.casingTier, this.voltageTier));
        setTimeReduce((100 - Math.min(coilLevel, 10) * 5.0) / 100);
        this.writeCustomData(GTQTValue.UPDATE_TIER4, buf -> buf.writeInt(this.casingTier));
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

    protected class ChemicalPlantLogic extends GTQTMultiblockLogic {

        public ChemicalPlantLogic(RecipeMapMultiblockController tileEntity) {
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