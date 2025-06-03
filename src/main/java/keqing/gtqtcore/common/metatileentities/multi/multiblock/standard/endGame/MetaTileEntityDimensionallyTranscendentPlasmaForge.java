package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.endGame;

import gregicality.multiblocks.api.recipes.GCYMRecipeMaps;
import gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.HeatingCoilRecipeLogic;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.properties.impl.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockComputerCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.capability.ILaser;
import keqing.gtqtcore.api.capability.IWarpSwarm;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.LASER_INPUT;
import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.WARP_SWARM_MULTIBLOCK_ABILITY;
import static keqing.gtqtcore.common.block.blocks.BlockQuantumCasing.CasingType.DIMENSIONAL_BRIDGE_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockQuantumCasing.CasingType.ULTIMATE_HIGH_ENERGY_CASING;

public class MetaTileEntityDimensionallyTranscendentPlasmaForge extends MultiMapMultiblockController implements IHeatingCoil {

    protected int heatingCoilLevel;
    protected int coilTier;
    private int blastFurnaceTemperature;

    public MetaTileEntityDimensionallyTranscendentPlasmaForge(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.FURNACE_RECIPES,
                RecipeMaps.ARC_FURNACE_RECIPES,
                RecipeMaps.BLAST_RECIPES,
                RecipeMaps.ALLOY_SMELTER_RECIPES,
                GTQTcoreRecipeMaps.EXTRADIMENSIONAL_SMELTING_RECIPES,
                GTQTcoreRecipeMaps.BURNER_REACTOR_RECIPES,
                GTQTcoreRecipeMaps.DRYER_RECIPES,
                GTQTcoreRecipeMaps.VACUUM_DRYING_FURNACE_RECIPES,
                GCYMRecipeMaps.ALLOY_BLAST_RECIPES
        });
        this.recipeMapWorkable = new DTPFRecipeLogic(this);
    }

    private static IBlockState getCasingState() {
        return MetaBlocks.COMPUTER_CASING.getState(BlockComputerCasing.CasingType.HIGH_POWER_CASING);
    }

    private static IBlockState getSecondCasingState() {
        return GTQTMetaBlocks.blockQuantumCasing.getState(ULTIMATE_HIGH_ENERGY_CASING);
    }

    private static IBlockState getThirdCasingState() {
        return GTQTMetaBlocks.blockQuantumCasing.getState(DIMENSIONAL_BRIDGE_CASING);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityDimensionallyTranscendentPlasmaForge(metaTileEntityId);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.blastFurnaceTemperature = ((IHeatingCoilBlockStats) coilType).getCoilTemperature();
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
            this.coilTier = ((IHeatingCoilBlockStats) coilType).getTier();
        } else {
            this.blastFurnaceTemperature = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
            this.coilTier = BlockWireCoil.CoilType.CUPRONICKEL.getTier();
        }

        this.blastFurnaceTemperature += 100 * Math.max(0, GTUtility.getTierByVoltage(getEnergyContainer().getInputVoltage()) - GTValues.MV);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        blastFurnaceTemperature = 0;
        heatingCoilLevel = 0;
    }

    @Override
    protected void initializeAbilities() {
        this.inputInventory = new ItemHandlerList(this.getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.inputFluidInventory = new FluidTankList(this.allowSameFluidFillForOutputs(), this.getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(this.getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.outputFluidInventory = new FluidTankList(this.allowSameFluidFillForOutputs(), this.getAbilities(MultiblockAbility.EXPORT_FLUIDS));
        List<IEnergyContainer> energyContainer = new ArrayList<>(this.getAbilities(MultiblockAbility.INPUT_ENERGY));
        energyContainer.addAll(this.getAbilities(MultiblockAbility.INPUT_LASER));
        this.energyContainer = new EnergyContainerList(energyContainer);
    }

    @Override
    public boolean checkRecipe(Recipe recipe,
                               boolean consumeIfSuccess) {
        return this.blastFurnaceTemperature >= recipe.getProperty(TemperatureProperty.getInstance(), 0);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" NNN   NNN             NNN   NNN ", "                                 ", "                                 ", "                                 ", " NNN   NNN             NNN   NNN ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " NNN   NNN             NNN   NNN ", "                                 ", "                                 ", "                                 ", " NNN   NNN   N     N   NNN   NNN ", "         N   N     N   N         ", "         N   N     N   N         ", "                                 ", "                                 ", "                                 ", "         N   N     N   N         ", "         N   N     N   N         ", "         N   N     N   N         ", "                                 ")
                .aisle("NbbbN NbbbN    N N    NbbbN NbbbN", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", "NbbbN NbbbN           NbbbN NbbbN", "  N     N               N     N  ", "  N     N               N     N  ", "                                 ", "  N     N               N     N  ", "  N     N               N     N  ", "NbbbN NbbbN           NbbbN NbbbN", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", " CCC   CCC   N     N   CCC   CCC ", "NbbbN NbbNCCCb     bCCCNbbN NbbbN", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         N   N     N   N         ", "                                 ", "         N   N     N   N         ", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         N   N     N   N         ")
                .aisle("NbbbN NbbbNNNNNsNsNNNNNbbbN NbbbN", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", "NbbbN NbbbN           NbbbN NbbbN", " NNN   NNN             NNN   NNN ", " NNN   NNN             NNN   NNN ", "  s     s               s     s  ", " NNN   NNN             NNN   NNN ", " NNN   NNN             NNN   NNN ", "NbbbN NbbbN           NbbbN NbbbN", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", " CbC   CbC   N     N   CbC   CbC ", "NbbbN NbbNCCCb     bCCCNbbN NbbbN", "  N     sbbbbbNNsNNbbbbbs     N  ", "  N      bCCCb     bCCCb      N  ", "  N      N   N     N   N      N  ", "   s                         s   ", "   s     N   N     N   N     s   ", "    ss   bCCCb     bCCCb   ss    ", "      NNNbbbbbNNsNNbbbbbNNN      ", "         bCCCb     bCCCb         ", "         N   N     N   N         ")
                .aisle("NbbbNNNbbbN    NbN    NbbbNNNbbbN", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", "NbbbNNNbbbN           NbbbNNNbbbN", "  N     N               N     N  ", "  N     N               N     N  ", "                                 ", "  N     N               N     N  ", "  N     N               N     N  ", "NbbbNNNbbbN           NbbbNNNbbbN", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC   N     N   CCCCCCCCC ", "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         NCCCN     NCCCN         ", "  s      NCCCN     NCCCN      s  ", "  s      NCCCN     NCCCN      s  ", "         bCCCb     bCCCb         ", "    ss   bCCCb     bCCCb   ss    ", "         bCCCb     bCCCb         ", "         N   N     N   N         ")
                .aisle(" NNN   NNN     NbN     NNN   NNN ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " NNN   NNN             NNN   NNN ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " NNN   NNN             NNN   NNN ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " NNN   NN    N     N    NN   NNN ", "         N   N     N   N         ", "         NCCCN     NCCCN         ", "                                 ", "                                 ", "                                 ", "  s      NCCCN     NCCCN      s  ", "   s     N   N     N   N     s   ", "         N   N     N   N         ", "                                 ")
                .aisle("   N   N       NbN       N   N   ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   N   N                 N   N   ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "   N   N                 N   N   ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   N   N                 N   N   ", "                                 ", "         NCCCN     NCCCN         ", "                                 ", "                                 ", "                                 ", "  s      NCCCN     NCCCN      s  ", "   s                         s   ", "                                 ", "                                 ")
                .aisle(" NNN   NNN     NbN     NNN   NNN ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " NNN   NNN             NNN   NNN ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " NNN   NNN             NNN   NNN ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " NNN   NN    N     N    NN   NNN ", "         N   N     N   N         ", "         NCCCN     NCCCN         ", "                                 ", "                                 ", "                                 ", "         NCCCN     NCCCN         ", "  N      N   N     N   N      N  ", "         N   N     N   N         ", "                                 ")
                .aisle("NbbbNNNbbbN    NbN    NbbbNNNbbbN", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", "NbbbNNNbbbN           NbbbNNNbbbN", "  N     N               N     N  ", "  N     N               N     N  ", "                                 ", "  N     N               N     N  ", "  N     N               N     N  ", "NbbbNNNbbbN           NbbbNNNbbbN", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC   N     N   CCCCCCCCC ", "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         NCCCN     NCCCN         ", "         NCCCN     NCCCN         ", "         NCCCN     NCCCN         ", "         bCCCb     bCCCb         ", "  N      bCCCb     bCCCb      N  ", "         bCCCb     bCCCb         ", "         N   N     N   N         ")
                .aisle("NbbbN NbbbNNNNNsNsNNNNNbbbN NbbbN", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", "NbbbN NbbbN           NbbbN NbbbN", " NNN   NNN             NNN   NNN ", " NNN   NNN             NNN   NNN ", "  s     s               s     s  ", " NNN   NNN             NNN   NNN ", " NNN   NNN             NNN   NNN ", "NbbbN NbbbN           NbbbN NbbbN", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", " CbC   CbC   N     N   CbC   CbC ", "NbbbN NbbNCCCb     bCCCNbbN NbbbN", "  s     sbbbbbNNsNNbbbbbs     s  ", "         bCCCb     bCCCb         ", "         N   N     N   N         ", "                                 ", "         N   N     N   N         ", "         bCCCb     bCCCb         ", "  N     sbbbbbNNsNNbbbbbs     N  ", "         bCCCb     bCCCb         ", "         N   N     N   N         ")
                .aisle("NbbbN NbbbN    NbN    NbbbN NbbbN", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", "NbbbN NbbbN           NbbbN NbbbN", "  N     N               N     N  ", "  N     N               N     N  ", "                                 ", "  N     N               N     N  ", "  N     N               N     N  ", "NbbbN NbbbN           NbbbN NbbbN", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", " CCC   CCC   N     N   CCC   CCC ", "NNNN   NNNCCCb     bCCCNNN   NNNN", "NbbbN NbbNCCCb     bCCCNbbN NbbbN", "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN", " NNN   NNN   N     N   NNN   NNN ", "   N   N                 N   N   ", " NNN   NNN   N     N   NNN   NNN ", "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN", "NbbbN NbbNCCCb     bCCCNbbN NbbbN", "NbbbN NbbNCCCb     bCCCNbbN NbbbN", " NNN   NNN   N     N   NNN   NNN ")
                .aisle(" NNN   NNN     NbN     NNN   NNN ", "                                 ", "                                 ", "                                 ", " NNN   NNN             NNN   NNN ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " NNN   NNN             NNN   NNN ", "                                 ", "                                 ", "                                 ", " CCC   CCC   N     N   CCC   CCC ", " CbC   CbC   N     N   CbC   CbC ", " CCCCCCCCC   N     N   CCCCCCCCC ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " CCCCCCCCC   N     N   CCCCCCCCC ", " CbC   CbC   N     N   CbC   CbC ", " CCC   CCC   N     N   CCC   CCC ", "                                 ")
                .aisle("  N     N      NbN      N     N  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " CCC   CCC             CCC   CCC ", " CbC   CbC             CbC   CbC ", " CCCCCCCCC             CCCCCCCCC ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " CCCCCCCCC             CCCCCCCCC ", " CbC   CbC             CbC   CbC ", " CCC   CCC             CCC   CCC ", "                                 ")
                .aisle("  N     N      NbN      N     N  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " CCC   CCC             CCC   CCC ", " CbC   CbC             CbC   CbC ", " CCCCCCCCC             CCCCCCCCC ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " CCCCCCCCC             CCCCCCCCC ", " CbC   CbC             CbC   CbC ", " CCC   CCC             CCC   CCC ", "                                 ")
                .aisle("  N     N     NsNsN     N     N  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " NNN   NNN             NNN   NNN ", "NbbbN NbbbN           NbbbN NbbbN", "NbbbN NbbbN           NbbbN NbbbN", "NbbbNNNbbbN           NbbbNNNbbbN", " NNN   NNN             NNN   NNN ", "   N   N                 N   N   ", " NNN   NNN             NNN   NNN ", "NbbbNNNbbbN           NbbbNNNbbbN", "NbbbN NbbbN           NbbbN NbbbN", "NbbbN NbbbN           NbbbN NbbbN", " NNN   NNN             NNN   NNN ")
                .aisle("  N     N    NbbbbbN    N     N  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  N     N               N     N  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  N     N               N     N  ", "                                 ", "                                 ")
                .aisle(" NsNNNNNsNNNNsbbbbbsNNNNsNNNNNsN ", "                N                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  N     N               N     N  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  N     N               N     N  ", "                                 ", "                                 ")
                .aisle("  NbbbbbNbbbbNbbbbbNbbbbNbbbbbN  ", "               NNN               ", "                ~                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  s     s               s     s  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  s     s               s     s  ", "                                 ", "                                 ")
                .aisle(" NsNNNNNsNNNNsbbbbbsNNNNsNNNNNsN ", "                N                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  N     N               N     N  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  N     N               N     N  ", "                                 ", "                                 ")
                .aisle("  N     N    NbbbbbN    N     N  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  N     N               N     N  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  N     N               N     N  ", "                                 ", "                                 ")
                .aisle("  N     N     NsNsN     N     N  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " NNN   NNN             NNN   NNN ", "NbbbN NbbbN           NbbbN NbbbN", "NbbbN NbbbN           NbbbN NbbbN", "NbbbNNNbbbN           NbbbNNNbbbN", " NNN   NNN             NNN   NNN ", "   N   N                 N   N   ", " NNN   NNN             NNN   NNN ", "NbbbNNNbbbN           NbbbNNNbbbN", "NbbbN NbbbN           NbbbN NbbbN", "NbbbN NbbbN           NbbbN NbbbN", " NNN   NNN             NNN   NNN ")
                .aisle("  N     N      NbN      N     N  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " CCC   CCC             CCC   CCC ", " CbC   CbC             CbC   CbC ", " CCCCCCCCC             CCCCCCCCC ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " CCCCCCCCC             CCCCCCCCC ", " CbC   CbC             CbC   CbC ", " CCC   CCC             CCC   CCC ", "                                 ")
                .aisle("  N     N      NbN      N     N  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " CCC   CCC             CCC   CCC ", " CbC   CbC             CbC   CbC ", " CCCCCCCCC             CCCCCCCCC ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " CCCCCCCCC             CCCCCCCCC ", " CbC   CbC             CbC   CbC ", " CCC   CCC             CCC   CCC ", "                                 ")
                .aisle(" NNN   NNN     NbN     NNN   NNN ", "                                 ", "                                 ", "                                 ", " NNN   NNN             NNN   NNN ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " NNN   NNN             NNN   NNN ", "                                 ", "                                 ", "                                 ", " CCC   CCC   N     N   CCC   CCC ", " CbC   CbC   N     N   CbC   CbC ", " CCCCCCCCC   N     N   CCCCCCCCC ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " CCCCCCCCC   N     N   CCCCCCCCC ", " CbC   CbC   N     N   CbC   CbC ", " CCC   CCC   N     N   CCC   CCC ", "                                 ")
                .aisle("NbbbN NbbbN    NbN    NbbbN NbbbN", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", "NbbbN NbbbN           NbbbN NbbbN", "  N     N               N     N  ", "  N     N               N     N  ", "                                 ", "  N     N               N     N  ", "  N     N               N     N  ", "NbbbN NbbbN           NbbbN NbbbN", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", " CCC   CCC   N     N   CCC   CCC ", "NNNN   NNNCCCb     bCCCNNN   NNNN", "NbbbN NbbNCCCb     bCCCNbbN NbbbN", "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN", " NNN   NNN   N     N   NNN   NNN ", "   N   N                 N   N   ", " NNN   NNN   N     N   NNN   NNN ", "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN", "NbbbN NbbNCCCb     bCCCNbbN NbbbN", "NbbbN NbbNCCCb     bCCCNbbN NbbbN", " NNN   NNN   N     N   NNN   NNN ")
                .aisle("NbbbN NbbbNNNNNsNsNNNNNbbbN NbbbN", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", "NbbbN NbbbN           NbbbN NbbbN", " NNN   NNN             NNN   NNN ", " NNN   NNN             NNN   NNN ", "  s     s               s     s  ", " NNN   NNN             NNN   NNN ", " NNN   NNN             NNN   NNN ", "NbbbN NbbbN           NbbbN NbbbN", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", " CbC   CbC   N     N   CbC   CbC ", "NbbbN NbbNCCCb     bCCCNbbN NbbbN", "  s     sbbbbbNNsNNbbbbbs     s  ", "         bCCCb     bCCCb         ", "         N   N     N   N         ", "                                 ", "         N   N     N   N         ", "         bCCCb     bCCCb         ", "  N     sbbbbbNNsNNbbbbbs     N  ", "         bCCCb     bCCCb         ", "         N   N     N   N         ")
                .aisle("NbbbNNNbbbN    NbN    NbbbNNNbbbN", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", "NbbbNNNbbbN           NbbbNNNbbbN", "  N     N               N     N  ", "  N     N               N     N  ", "                                 ", "  N     N               N     N  ", "  N     N               N     N  ", "NbbbNNNbbbN           NbbbNNNbbbN", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC   N     N   CCCCCCCCC ", "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         NCCCN     NCCCN         ", "         NCCCN     NCCCN         ", "         NCCCN     NCCCN         ", "         bCCCb     bCCCb         ", "  N      bCCCb     bCCCb      N  ", "         bCCCb     bCCCb         ", "         N   N     N   N         ")
                .aisle(" NNN   NNN     NbN     NNN   NNN ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " NNN   NNN             NNN   NNN ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " NNN   NNN             NNN   NNN ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " NNN   NN    N     N    NN   NNN ", "         N   N     N   N         ", "         NCCCN     NCCCN         ", "                                 ", "                                 ", "                                 ", "         NCCCN     NCCCN         ", "  N      N   N     N   N      N  ", "         N   N     N   N         ", "                                 ")
                .aisle("   N   N       NbN       N   N   ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   N   N                 N   N   ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "   N   N                 N   N   ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   N   N                 N   N   ", "                                 ", "         NCCCN     NCCCN         ", "                                 ", "                                 ", "                                 ", "  s      NCCCN     NCCCN      s  ", "   s                         s   ", "                                 ", "                                 ")
                .aisle(" NNN   NNN     NbN     NNN   NNN ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " NNN   NNN             NNN   NNN ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " NNN   NNN             NNN   NNN ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " NNN   NN    N     N    NN   NNN ", "         N   N     N   N         ", "         NCCCN     NCCCN         ", "                                 ", "                                 ", "                                 ", "  s      NCCCN     NCCCN      s  ", "   s     N   N     N   N     s   ", "         N   N     N   N         ", "                                 ")
                .aisle("NbbbNNNbbbN    NbN    NbbbNNNbbbN", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", "NbbbNNNbbbN           NbbbNNNbbbN", "  N     N               N     N  ", "  N     N               N     N  ", "                                 ", "  N     N               N     N  ", "  N     N               N     N  ", "NbbbNNNbbbN           NbbbNNNbbbN", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC   N     N   CCCCCCCCC ", "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         NCCCN     NCCCN         ", "  s      NCCCN     NCCCN      s  ", "  s      NCCCN     NCCCN      s  ", "         bCCCb     bCCCb         ", "    ss   bCCCb     bCCCb   ss    ", "         bCCCb     bCCCb         ", "         N   N     N   N         ")
                .aisle("NbbbN NbbbNNNNNsNsNNNNNbbbN NbbbN", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", "NbbbN NbbbN           NbbbN NbbbN", " NNN   NNN             NNN   NNN ", " NNN   NNN             NNN   NNN ", "  s     s               s     s  ", " NNN   NNN             NNN   NNN ", " NNN   NNN             NNN   NNN ", "NbbbN NbbbN           NbbbN NbbbN", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", " CbC   CbC   N     N   CbC   CbC ", "NbbbN NbbNCCCb     bCCCNbbN NbbbN", "  N     sbbbbbNNsNNbbbbbs     N  ", "  N      bCCCb     bCCCb      N  ", "  N      N   N     N   N      N  ", "   s                         s   ", "   s     N   N     N   N     s   ", "    ss   bCCCb     bCCCb   ss    ", "      NNNbbbbbNNsNNbbbbbNNN      ", "         bCCCb     bCCCb         ", "         N   N     N   N         ")
                .aisle("NbbbN NbbbN    N N    NbbbN NbbbN", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", "NbbbN NbbbN           NbbbN NbbbN", "  N     N               N     N  ", "  N     N               N     N  ", "                                 ", "  N     N               N     N  ", "  N     N               N     N  ", "NbbbN NbbbN           NbbbN NbbbN", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", " CCC   CCC   N     N   CCC   CCC ", "NbbbN NbbNCCCb     bCCCNbbN NbbbN", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         N   N     N   N         ", "                                 ", "         N   N     N   N         ", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         N   N     N   N         ")
                .aisle(" NNN   NNN             NNN   NNN ", "                                 ", "                                 ", "                                 ", " NNN   NNN             NNN   NNN ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " NNN   NNN             NNN   NNN ", "                                 ", "                                 ", "                                 ", " NNN   NNN   N     N   NNN   NNN ", "         N   N     N   N         ", "         N   N     N   N         ", "                                 ", "                                 ", "                                 ", "         N   N     N   N         ", "         N   N     N   N         ", "         N   N     N   N         ", "                                 ")
                .where('~', this.selfPredicate())
                .where('C', heatingCoils())
                .where('b', states(getCasingState())
                        .setMinGlobalLimited(1100)
                        .or(autoAbilities())
                        .or(abilities(MultiblockAbility.INPUT_LASER)
                                .setMaxGlobalLimited(1))
                        .or(abilities(WARP_SWARM_MULTIBLOCK_ABILITY)
                                .setExactLimit(1))
                )
                .where('N', states(getSecondCasingState()))
                .where('s', states(getThirdCasingState()))
                .where(' ', any())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return iMultiblockPart == null ? GTQTTextures.DIMENSIONAL_BRIDGE_CASING : Textures.HIGH_POWER_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.DIMENSIONALLY_TRANSCENDENT_PLASMA_FORGE_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack,
                               World player,
                               List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.BLINKING_RED + I18n.format("我是熔炉"));
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            IEnergyContainer energyContainer = this.recipeMapWorkable.getEnergyContainer();
            if (energyContainer != null && energyContainer.getEnergyCapacity() > 0L) {
                textList.add(2, new TextComponentTranslation("gregtech.multiblock.blast_furnace.max_temperature", blastFurnaceTemperature)
                        .setStyle(new Style().setColor(TextFormatting.RED)));
            }
        }
    }

    public IWarpSwarm getAbility() {
        if (this.getAbilities(WARP_SWARM_MULTIBLOCK_ABILITY) != null) return this.getAbilities(WARP_SWARM_MULTIBLOCK_ABILITY).get(0);
        return null;
    }
    @Override
    public int getCurrentTemperature() {
        return this.blastFurnaceTemperature;
    }

    protected class DTPFRecipeLogic extends HeatingCoilRecipeLogic {

        public DTPFRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public int getParallelLimit() {
            return getAbility().isAvailable()?getAbility().getParallel():super.getParallelLimit();
        }
        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (maxProgress*getTimeBound()));
        }
        public double getTimeBound() {
            if(getAbility().isAvailable())return  (10 - getAbility().getWarpSwarmTier()) /10.0;
            return 1;
        }
        @Override
        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);
                if (++this.progressTime > this.maxProgressTime) {
                    getAbility().applyDamage(1);
                    this.completeRecipe();
                }
                if (this.hasNotEnoughEnergy && this.getEnergyInputPerSecond() > 19L * this.recipeEUt) {
                    this.hasNotEnoughEnergy = false;
                }
            } else if (this.recipeEUt > 0L) {
                this.hasNotEnoughEnergy = true;
                this.decreaseProgress();
            }
        }
    }
}
