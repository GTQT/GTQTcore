package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.endGame;

import gregicality.multiblocks.api.recipes.GCYMRecipeMaps;
import gregtech.api.GTValues;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.capability.IMultipleTankHandler;
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
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.properties.impl.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockWireCoil;
import keqing.gtqtcore.api.capability.IWarpSwarm;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.WARP_SWARM_MULTIBLOCK_ABILITY;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.block.blocks.BlockQuantumCasing.CasingType.*;

public class MetaTileEntityDimensionallyTranscendentPlasmaForge extends MultiMapMultiblockController implements IHeatingCoil {

    private static final FluidStack CatalystMKI = SuperDimensionalCatalystMKI.getPlasma(1);
    private static final FluidStack CatalystMKII = SuperDimensionalCatalystMKII.getPlasma(1);
    private static final FluidStack CatalystMKIII = SuperDimensionalCatalystMKIII.getPlasma(1);

    protected int heatingCoilLevel;
    protected int coilTier;
    int heat;
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
        return GTQTMetaBlocks.blockQuantumCasing.getState(DIMENSIONAL_INJECTION_CASING);
    }

    private static IBlockState getSecondCasingState() {
        return GTQTMetaBlocks.blockQuantumCasing.getState(HIGH_ENERGY_CASING);
    }

    private static IBlockState getThirdCasingState() {
        return GTQTMetaBlocks.blockQuantumCasing.getState(DIMENSIONAL_BRIDGE_CASING);
    }
    @Override
    public boolean usesMui2() {
        return false;
    }
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("heat", heat);
        return data;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound data) {
        super.readFromNBT(data);
        heat = data.getInteger("heat");
    }

    @Override
    public void update() {
        super.update();
        if (heat < 0) heat = 0;
        if (heat > 576000) {
            heat -= 2;
        } else {
            getBoosterByFluidStack();
            heat -= 1;
        }
    }

    public void getBoosterByFluidStack() {
        IMultipleTankHandler inputTank = this.getInputFluidInventory();
        if (CatalystMKI.isFluidStackIdentical(inputTank.drain(CatalystMKI, false))) {
            inputTank.drain(CatalystMKI, true);
            heat += 4;
        }
        if (CatalystMKII.isFluidStackIdentical(inputTank.drain(CatalystMKII, false))) {
            inputTank.drain(CatalystMKII, true);
            heat += 6;
        }
        if (CatalystMKIII.isFluidStackIdentical(inputTank.drain(CatalystMKIII, false))) {
            inputTank.drain(CatalystMKIII, true);
            heat += 8;
        }
    }

    @Override
    public boolean shouldDelayCheck() {
        return true;
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
        return iMultiblockPart == null ? GTQTTextures.DIMENSIONAL_BRIDGE_CASING : GTQTTextures.DIMENSIONAL_INJECTION_CASING;
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
    public boolean shouldShowVoidingModeButton() {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack,
                               World player,
                               List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.BLINKING_RED + I18n.format("超越维度的边界"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("本设备支持纳米蜂群仓，每完成一次配方会消耗一点耐久（无视并行）"));
        tooltip.add(I18n.format("每等级纳米蜂群提供Math.pow(2,tier)*256的并行"));
        tooltip.add(I18n.format("每等级纳米蜂群提供10%%的耗时减免"));
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.laser.tooltip"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("每Tick消耗 1mb 超维度催化剂MKI,产生4点维度翘曲点"));
        tooltip.add(I18n.format("每Tick消耗 1mb 超维度催化剂MKII,产生6点维度翘曲点"));
        tooltip.add(I18n.format("每Tick消耗 1mb 超维度催化剂MKIII,产生8点维度翘曲点"));
        tooltip.add(I18n.format("维度翘曲点数量多于576000时，多方块进入无损超频模式"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("正常情况下机器每Tick降低1点维度翘曲点"));
        tooltip.add(I18n.format("维度翘曲点数量多于576000时,每Tick降低2点维度翘曲点"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.3"));
        tooltip.add(I18n.format("=============================================="));
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
            textList.add(new TextComponentTranslation("维度翘曲点数量：%s", heat));
            if (heat > 576000)
                textList.add(new TextComponentTranslation("已进入无损超频"));
        }
    }

    public IWarpSwarm getAbility() {
        if (this.getAbilities(WARP_SWARM_MULTIBLOCK_ABILITY) != null)
            return this.getAbilities(WARP_SWARM_MULTIBLOCK_ABILITY).get(0);
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
            return getAbility().isAvailable() ? getAbility().getParallel() : super.getParallelLimit();
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (maxProgress * getTimeBound()));
        }

        public double getTimeBound() {
            if (getAbility().isAvailable()) return (10 - getAbility().getWarpSwarmTier()) / 10.0;
            return 1;
        }

        protected double getOverclockingDurationFactor() {
            return heat > 576000 ? 0.25 : 0.5;
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
