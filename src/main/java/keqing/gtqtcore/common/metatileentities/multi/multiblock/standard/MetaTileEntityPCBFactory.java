package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;


import gregicality.multiblocks.api.unification.GCYMMaterials;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.*;
import gregtech.api.recipes.Recipe;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.*;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.PCBFactoryBioUpgradeProperty;
import keqing.gtqtcore.api.recipes.properties.PCBFactoryProperty;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4;
import keqing.gtqtcore.common.block.blocks.BlockPCBFactoryCasing;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.LuV;
import static gregtech.api.GTValues.ZPM;
import static gregtech.api.unification.material.Materials.Gold;
import static gregtech.api.unification.material.Materials.Silver;
import static keqing.gtqtcore.GTQTCoreConfig.MachineSwitch;
import static keqing.gtqtcore.api.pattern.GTQTTraceabilityPredicate.optionalStates;
import static keqing.gtqtcore.api.unification.GTQTMaterials.SiliconCarbide;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.swarm;
import static keqing.gtqtcore.api.utils.GTQTMathUtil.clamp;
import static keqing.gtqtcore.api.utils.GTQTUtil.getAccelerateByCWU;
import static keqing.gtqtcore.common.block.blocks.BlockPCBFactoryCasing.PCBFactoryCasingType.SUBSTRATE_CASING;

public class MetaTileEntityPCBFactory extends RecipeMapMultiblockController implements IOpticalComputationReceiver {

    //  Traceability Predicate Utility
    //  Used to check snow layer on block (for Multiblock Structure).
    private static final TraceabilityPredicate SNOW_LAYER = new TraceabilityPredicate(blockWorldState -> GTUtility.isBlockSnow(blockWorldState.getBlockState()));
    private final int minTraceSize = 25;
    private final int maxTraceSize = 200;
    int requestCWUt;
    private IOpticalComputationProvider computationProvider;
    //  Upgrade Number
    private byte mainUpgradeNumber = 0;
    private byte bioUpgradeNumber = 0;
    private byte coolingUpgradeNumber = 0;
    //  Special Parameters (Default: 100μm, Range: 25μm-200μm)
    private int traceSize = 100;

    public MetaTileEntityPCBFactory(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.PCB_FACTORY_RECIPES);
        this.recipeMapWorkable = new PCBFactoryRecipeLogic(this);
    }

    private static IBlockState getCasingState(String type) {
        return switch (type) {
            case "T1StructureCasing" ->
                    GTQTMetaBlocks.blockPCBFactoryCasing.getState(BlockPCBFactoryCasing.PCBFactoryCasingType.BASIC_PHOTOLITHOGRAPHIC_FRAMEWORK_CASING);
            case "T2StructureCasing" ->
                    GTQTMetaBlocks.blockPCBFactoryCasing.getState(BlockPCBFactoryCasing.PCBFactoryCasingType.MOLD_PRINTING_ASSEMBLY_FRAMEWORK_CASING);
            case "T3StructureCasing" ->
                    GTQTMetaBlocks.blockPCBFactoryCasing.getState(BlockPCBFactoryCasing.PCBFactoryCasingType.RADIATION_PROOF_SCAN_FRAMEWORK_CASING);
            case "NaquadahAlloyCasing" ->
                    GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.NQ_TURBINE_CASING);
            case "BioChamberCasing" ->
                    GTQTMetaBlocks.blockPCBFactoryCasing.getState(BlockPCBFactoryCasing.PCBFactoryCasingType.BIOLOGICAL_STERILE_MACHINE_CASING);
            case "ThermosinkCasing" ->
                    GTQTMetaBlocks.blockPCBFactoryCasing.getState(BlockPCBFactoryCasing.PCBFactoryCasingType.INFINITY_COOLED_MACHINE_CASING);
            default -> null;
        };
    }

    private static IBlockState getSecondCasingState() {
        return MetaBlocks.CLEANROOM_CASING.getState(BlockCleanroomCasing.CasingType.PLASCRETE);
    }

    private static IBlockState getThirdCasingState(String type) {
        return switch (type) {
            case "T1Grate" ->
                    MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING);
            case "T1Substrate" -> GTQTMetaBlocks.blockPCBFactoryCasing.getState(SUBSTRATE_CASING);
            case "CoolingTowerIntake" ->
                    MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.EXTREME_ENGINE_INTAKE_CASING);
            default -> null;
        };
    }

    private static IBlockState getBoilerCasingState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE);
    }

    private static IBlockState getCoilState() {
        return MetaBlocks.FUSION_CASING.getState(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL);
    }

    private static IBlockState getFrameState(String type) {
        return switch (type) {
            case "T1Frame" -> MetaBlocks.FRAMES.get(GCYMMaterials.HSLASteel).getBlock(GCYMMaterials.HSLASteel);
            case "T2Frame" -> MetaBlocks.FRAMES.get(Materials.TungstenSteel).getBlock(Materials.TungstenSteel);
            case "CoolingTowerFrame" -> MetaBlocks.FRAMES.get(Materials.BlackSteel).getBlock(Materials.BlackSteel);
            case "BioChamberFrame" -> MetaBlocks.FRAMES.get(SiliconCarbide).getBlock(SiliconCarbide);
            case "ThermosinkFrame" -> MetaBlocks.FRAMES.get(Materials.Naquadah).getBlock(Materials.Naquadah);
            default -> null;
        };
    }

    private static IBlockState getGlassState(String type) {
        return switch (type) {
            case "T1StructureGlass" ->
                    MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.LAMINATED_GLASS);
            case "BioChamberGlass" ->
                    MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.CLEANROOM_GLASS);
            default -> null;
        };
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
    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPCBFactory(metaTileEntityId);
    }

    @Override
    public void updateFormedValid() {
        super.updateFormedValid();
        if (isStructureFormed() && isActive()) {
            requestCWUt = computationProvider.requestCWUt(2048, false);
        }
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        List<IOpticalComputationHatch> providers = this.getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
        if (providers != null && !providers.isEmpty()) {
            this.computationProvider = providers.get(0);
        }
        //  Main Structure T1
        this.mainUpgradeNumber += 1;
        //  Main Structure T2
        if (context.get("mainUpgradeT2") != null) {
            this.mainUpgradeNumber += 1;
        }
        //  Main Structure T3
        if (context.get("mainUpgradeT3") != null) {
            this.mainUpgradeNumber += 1;
        }
        //  Bio Upgrade
        if (context.get("bioUpgrade") != null) {
            this.bioUpgradeNumber += 1;
        }
        //  Cooling Upgrade T1 (Liquid Cooling Tower)
        if (context.get("coolingUpgradeT1") != null) {
            this.coolingUpgradeNumber += 1;
        }
        //  Cooling Upgrade T2 (Thermosink)
        if (context.get("coolingUpgradeT2") != null) {
            this.coolingUpgradeNumber += 1;
        }
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.mainUpgradeNumber = 0;
        this.bioUpgradeNumber = 0;
        this.coolingUpgradeNumber = 0;
    }

    @Override
    public boolean shouldDelayCheck() {
        return true;
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe,
                               boolean consumeIfSuccess) {
        return super.checkRecipe(recipe, consumeIfSuccess)
                && recipe.getProperty(PCBFactoryProperty.getInstance(), 0) <= this.mainUpgradeNumber
                && recipe.getProperty(PCBFactoryBioUpgradeProperty.getInstance(), 0) <= this.bioUpgradeNumber;
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("              gHHHg  nTTTn       ", "              gPPPg  nQQQn       ", "              g   g  n   n       ", "              g   g  n   n       ", "              gJJJg  nRRRn       ", "              g   g  n   n       ", "              g   g  n   n       ", "              g   g  n   n       ", "              g   g  n   n       ", "              gIIIg  nTTTn       ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("              HHHHH  TTTTT       ", "              PIIIP  QOOOQ       ", "               III    OOO        ", "               III    OOO        ", "              JIIIJ  ROOOR       ", "               III    OOO        ", "               III    OOO        ", "               PPP    QQQ        ", "               III    TTT        ", "              I###I  T###T       ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("              HHHHH  TTTTT       ", "              PI*IP  QOUOQ       ", "               I#I    OUO        ", "               I#I    OUO        ", "              JI#IJ  ROUOR       ", "               I#I    OUO        ", "               I#I    OUO        ", "               P#P    QUQ        ", "               I#I    TUT        ", "              I###I  T###T       ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle(" KKKKK        HHHHH  TTTTT       ", "              PIIIP  QOOOQ       ", "               III    OOO        ", "               III    OOO        ", "              JIIIJ  ROOOR       ", "               III    OOO        ", "               III    OOO        ", "               PPP    QQQ        ", "               III    TTT        ", "              I###I  T###T       ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("KKKKKKK       gHHHg  nTTTn       ", "  KKK         gPPPg  nQQQn       ", "  KKK         g   g  n   n       ", "  KKK         g   g  n   n       ", "  KKK         gJJJg  nRRRn       ", "  KKK         g   g  n   n       ", "  KKK         g   g  n   n       ", "  KKK         g   g  n   n       ", "  KKK         g   g  n   n       ", "  KKK         gIIIg  nTTTn       ", "  KKK                            ", "  KKK                            ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("KKKKKKK                          ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", "  LLL                            ", "  LLL                            ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("KKKKKKK                          ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", "  L#L                            ", " K###K                           ", "  L#L                            ", "  L#L                            ", "  LKL                            ", "  LKL                            ", "   K                             ", "   K                             ", "   K                             ", "   K                             ", "   K                             ", "                                 ")
                .aisle("KKKKKKK  fEEf                    ", " K###K   fEEf                    ", " K###K   fEEf                    ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", "  L#L                            ", " K###K                           ", "  L#L                            ", "  L#L                            ", "  L#L                            ", "  L#L                            ", "  L#L                            ", "  L#L                            ", "  L#L                            ", "  L#L                            ", "   K                             ", "   K                             ")
                .aisle("KKKKKKK  EEEE                    ", " K###K   E##E                    ", " K###K   E##E                    ", " K###K   fEEf                    ", " K###K   fEEf                    ", " K###K                           ", "  L#L                            ", "  L#L                            ", "  L#L                            ", " K###K                           ", "  L#L                            ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", "  L#L                            ", "  L#L                            ", "   K                             ", "   K                             ")
                .aisle("KKKKKKK  EEEEFCCCCCF             ", " K###K   E##EFCCCCCF             ", " K###K   E##EFCCCCCF             ", " K###K   E##EFCCCCCF             ", " K###K   E##EF     F             ", " K###K   fEEf                    ", "  L#L                            ", "  L#L                            ", "  L#L                            ", " K###K                           ", "  L#L                            ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", "  L#L                            ", "  LKL                            ", "   K                             ", "                                 ")
                .aisle("KKKKKKK  EEEECcccccC hMMMh  hMMMh", " K###K   E##EC#####C hNNNh  hNNNh", " K###K   E##EC#####C hNNNh  hNNNh", " K###K   E##EC#####C hNNNh  hNNNh", " K###K   E##ECCCCCCC h   h  h   h", " K###K   EEEEF     F             ", "  LLL    fEEf                    ", "  LLL                            ", "  LLL                            ", " K###K                           ", " K###K                           ", " K###K                           ", " K#L#K                           ", " K#L#K                           ", " K#L#K                           ", " K###K                           ", " K###K                           ", " K###K                           ", "  LLL                            ", "  LLL                            ", "                                 ", "                                 ")
                .aisle("KKKKKKK  EEEECcccccC MMMMM  MMMMM", "  KKK    E##ED#XXX#D N###N  N###N", "  KKK    E##ED#####D N###N  N###N", "  KKK    E##EC#####C N###N  N###N", "  KKK    E##ECCCCCCC  MMM    MMM ", "  KKK    EEEEF     F             ", "         fEEf                    ", "                                 ", "                                 ", "  KKK                            ", "  KKK                            ", "  KKK                            ", "  K K                            ", "  K K                            ", "  K K                            ", "  KKK                            ", "  KKK                            ", "  KKK                            ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle(" KKKKK   EEEECcccccC MMMMM  MMMMM", "         E##ED#XXX#D N###N  N###N", "         E##ED#####D N###N  N###N", "         E##EC#####C N###N  N###N", "         E##ECCCCCCC  MMM    MMM ", "         EEEEFFFFFFF   M      M  ", "         fEEf           MMMMMM   ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("         EEEECcccccC MMMMM  MMMMM", "         E##ED#XXX#D N###N  N###N", "         E##ED#####D N###N  N###N", "         E##EC#####C N###N  N###N", "         E##ECGGGGGC  MMM    MMM ", "         EEEEF     F             ", "         fEEf                    ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("         EEEECcccccC hMMMh  hMMMh", "         E##EC#####C hNNNh  hNNNh", "         E##EC#####C hNNNh  hNNNh", "         E##EC#####C hNNNh  hNNNh", "         E##ECGGGGGC h   h  h   h", "         EEEEF     F             ", "         fEEf                    ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("         EEEEFCCSCCF             ", "         E##EFGGGGGF             ", "         E##EFGGGGGF             ", "         E##EFGGGGGF             ", "         E##EFFFFFFF             ", "         fEEf                    ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("         EEEE                    ", "         E##E                    ", "         E##E                    ", "         fEEf                    ", "         fEEf                    ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("         fEEf                    ", "         fEEf                    ", "         fEEf                    ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                //  Controller
                .where('S', this.selfPredicate())
                //  Main Structure (T1)
                .where('C', states(getCasingState("T1StructureCasing")) // Basic Photolithographic Framework casing
                        .setMinGlobalLimited(40)
                        .or(abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1))
                        .or(abilities(MultiblockAbility.INPUT_LASER).setMaxGlobalLimited(1))
                        .or(this.autoAbilities())) // Energy hatch (1-3), Maintenance hatch, Item import/export hatch, Fluid import hatch
                .where('c', states(getSecondCasingState())) // Plascrete
                .where('D', states(getThirdCasingState("T1Grate")))  // Grate casing
                .where('F', states(getFrameState("T1Frame"))) // HSLA Steel frame
                .where('G', states(getGlassState("T1StructureGlass"))) // Laminated glass
                .where('X', states(getThirdCasingState("T1Substrate"))) // Substrate casing
                //  Main Structure (T2)
                .where('E', optionalStates("mainUpgradeT2", getCasingState("T2StructureCasing"))) // Mold Printing Assembly Framework casing
                .where('f', optionalStates("mainUpgradeT2", getFrameState("T2Frame"))) // Tungsten Steel Frame
                //  Liquid Cooling Tower (Cooling Upgrade T1)
                .where('H', optionalStates("coolingUpgradeT1", getCasingState("T2StructureCasing"))) // Mold Printing Assembly Framework casing
                .where('I', optionalStates("coolingUpgradeT1", getCasingState("NaquadahAlloyCasing"))) // Naquadah Alloy casing
                .where('J', optionalStates("coolingUpgradeT1", getThirdCasingState("CoolingTowerIntake"))) // Extreme Intake casing
                .where('P', optionalStates("coolingUpgradeT1", getBoilerCasingState())) // Tungsten Steel Pipe casing
                .where('g', optionalStates("coolingUpgradeT1", getFrameState("CoolingTowerFrame"))) // Black Steel Frame
                //  Main Structure (T3)
                .where('K', optionalStates("mainUpgradeT3", getCasingState("T3StructureCasing"))) // Radiation Proof Scan Framework casing
                .where('L', optionalStates("mainUpgradeT3", getCasingState("NaquadahAlloyCasing"))) // Naquadah Alloy casing
                //  Bio Chamber (Bio Upgrade)
                .where('M', optionalStates("bioUpgrade", getCasingState("BioChamberCasing"))) // Biological Sterile Machine casing
                .where('h', optionalStates("bioUpgrade", getFrameState("BioChamberFrame"))) // Silicon Carbide frame
                .where('N', optionalStates("bioUpgrade", getGlassState("BioChamberGlass"))) // Cleanroom glass
                //  Thermosink (Cooling Upgrade T2)
                .where('O', optionalStates("coolingUpgradeT2", getCasingState("ThermosinkCasing"))) // Infinity Cooled Machine casing
                .where('Q', optionalStates("coolingUpgradeT2", getBoilerCasingState())) // Tungsten Steel Pipe casing
                .where('R', optionalStates("coolingUpgradeT2", getThirdCasingState("CoolingTowerIntake"))) // Extreme Intake casing
                .where('T', optionalStates("coolingUpgradeT2", getCasingState("T2StructureCasing"))) // Mold Printing Assembly Framework casing
                .where('U', optionalStates("coolingUpgradeT2", getCoilState())) // Superconductor Coil block
                .where('n', optionalStates("coolingUpgradeT2", getFrameState("ThermosinkFrame"))) // Naquadah frame
                //  Other Miscs
                .where('#', air())
                .where('*', air()
                        .or(SNOW_LAYER))
                .where(' ', any())
                .build();
    }

    @Nonnull
    @Override
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 18, "", this::decrementTraceSize)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("gtqtcore.machine.pcb_factory.trace_size.decrement"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 18, "", this::incrementTraceSize)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("gtqtcore.machine.pcb_factory.trace_size.increment"));
        return group;
    }

    private void incrementTraceSize(Widget.ClickData clickData) {
        this.traceSize = clamp(traceSize - 25, minTraceSize, maxTraceSize);
    }

    private void decrementTraceSize(Widget.ClickData clickData) {
        this.traceSize = clamp(traceSize + 25, minTraceSize, maxTraceSize);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("TraceSize", traceSize);
        return super.writeToNBT(data);
    }


    public void readFromNBT(NBTTagCompound data) {
        traceSize = data.getInteger("TraceSize");
        super.readFromNBT(data);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeVarInt(traceSize);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        traceSize = buf.readVarInt();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.BASIC_PHOTOLITHOGRAPHIC_FRAMEWORK_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = null;
        if (Blocks.AIR != null) {
            builder = MultiblockShapeInfo.builder()
                    .aisle("              gHHHg  nTTTn       ", "              gPPPg  nQQQn       ", "              g   g  n   n       ", "              g   g  n   n       ", "              gJJJg  nRRRn       ", "              g   g  n   n       ", "              g   g  n   n       ", "              g   g  n   n       ", "              g   g  n   n       ", "              gIIIg  nTTTn       ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("              HHHHH  TTTTT       ", "              PIIIP  QOOOQ       ", "               III    OOO        ", "               III    OOO        ", "              JIIIJ  ROOOR       ", "               III    OOO        ", "               III    OOO        ", "               PPP    QQQ        ", "               III    TTT        ", "              I###I  T###T       ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("              HHHHH  TTTTT       ", "              PI*IP  QOUOQ       ", "               I#I    OUO        ", "               I#I    OUO        ", "              JI#IJ  ROUOR       ", "               I#I    OUO        ", "               I#I    OUO        ", "               P#P    QUQ        ", "               I#I    TUT        ", "              I###I  T###T       ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle(" KKKKK        HHHHH  TTTTT       ", "              PIIIP  QOOOQ       ", "               III    OOO        ", "               III    OOO        ", "              JIIIJ  ROOOR       ", "               III    OOO        ", "               III    OOO        ", "               PPP    QQQ        ", "               III    TTT        ", "              I###I  T###T       ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("KKKKKKK       gHHHg  nTTTn       ", "  KKK         gPPPg  nQQQn       ", "  KKK         g   g  n   n       ", "  KKK         g   g  n   n       ", "  KKK         gJJJg  nRRRn       ", "  KKK         g   g  n   n       ", "  KKK         g   g  n   n       ", "  KKK         g   g  n   n       ", "  KKK         g   g  n   n       ", "  KKK         gIIIg  nTTTn       ", "  KKK                            ", "  KKK                            ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("KKKKKKK                          ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", "  LLL                            ", "  LLL                            ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("KKKKKKK                          ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", "  L#L                            ", " K###K                           ", "  L#L                            ", "  L#L                            ", "  LKL                            ", "  LKL                            ", "   K                             ", "   K                             ", "   K                             ", "   K                             ", "   K                             ", "                                 ")
                    .aisle("KKKKKKK  fEEf                    ", " K###K   fEEf                    ", " K###K   fEEf                    ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", "  L#L                            ", " K###K                           ", "  L#L                            ", "  L#L                            ", "  L#L                            ", "  L#L                            ", "  L#L                            ", "  L#L                            ", "  L#L                            ", "  L#L                            ", "   K                             ", "   K                             ")
                    .aisle("KKKKKKK  EEEE                    ", " K###K   E##E                    ", " K###K   E##E                    ", " K###K   fEEf                    ", " K###K   fEEf                    ", " K###K                           ", "  L#L                            ", "  L#L                            ", "  L#L                            ", " K###K                           ", "  L#L                            ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", "  L#L                            ", "  L#L                            ", "   K                             ", "   K                             ")
                    .aisle("KKKKKKK  EEEEFCCCpeF             ", " K###K   E##EFCCCCCF             ", " K###K   E##EFCCCCCF             ", " K###K   E##EFCCCCCF             ", " K###K   E##EF     F             ", " K###K   fEEf                    ", "  L#L                            ", "  L#L                            ", "  L#L                            ", " K###K                           ", "  L#L                            ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", " K###K                           ", "  L#L                            ", "  LKL                            ", "   K                             ", "                                 ")
                    .aisle("KKKKKKK  EEEECcccccC hMMMh  hMMMh", " K###K   E##EC#####C hNNNh  hNNNh", " K###K   E##EC#####C hNNNh  hNNNh", " K###K   E##EC#####C hNNNh  hNNNh", " K###K   E##ECCCCCCC h   h  h   h", " K###K   EEEEF     F             ", "  LLL    fEEf                    ", "  LLL                            ", "  LLL                            ", " K###K                           ", " K###K                           ", " K###K                           ", " K#L#K                           ", " K#L#K                           ", " K#L#K                           ", " K###K                           ", " K###K                           ", " K###K                           ", "  LLL                            ", "  LLL                            ", "                                 ", "                                 ")
                    .aisle("KKKKKKK  EEEECcccccC MMMMM  MMMMM", "  KKK    E##ED#XXX#D N###N  N###N", "  KKK    E##ED#####D N###N  N###N", "  KKK    E##EC#####C N###N  N###N", "  KKK    E##ECCCCCCC  MMM    MMM ", "  KKK    EEEEF     F             ", "         fEEf                    ", "                                 ", "                                 ", "  KKK                            ", "  KKK                            ", "  KKK                            ", "  K K                            ", "  K K                            ", "  K K                            ", "  KKK                            ", "  KKK                            ", "  KKK                            ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle(" KKKKK   EEEECcccccC MMMMM  MMMMM", "         E##ED#XXX#D N###N  N###N", "         E##ED#####D N###N  N###N", "         E##EC#####C N###N  N###N", "         E##ECCCCCCC  MMM    MMM ", "         EEEEFFFFFFF   M      M  ", "         fEEf           MMMMMM   ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("         EEEECcccccC MMMMM  MMMMM", "         E##ED#XXX#D N###N  N###N", "         E##ED#####D N###N  N###N", "         E##EC#####C N###N  N###N", "         E##ECGGGGGC  MMM    MMM ", "         EEEEF     F             ", "         fEEf                    ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("         EEEECcccccw hMMMh  hMMMh", "         E##EC#####C hNNNh  hNNNh", "         E##EC#####C hNNNh  hNNNh", "         E##EC#####C hNNNh  hNNNh", "         E##ECGGGGGC h   h  h   h", "         EEEEF     F             ", "         fEEf                    ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("         EEEEFijSkkF             ", "         E##EFGGGGGF             ", "         E##EFGGGGGF             ", "         E##EFGGGGGF             ", "         E##EFFFFFFF             ", "         fEEf                    ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("         EEEE                    ", "         E##E                    ", "         E##E                    ", "         fEEf                    ", "         fEEf                    ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("         fEEf                    ", "         fEEf                    ", "         fEEf                    ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .where('S', GTQTMetaTileEntities.PCB_FACTORY, EnumFacing.SOUTH)
                    .where('C', getCasingState("T1StructureCasing")) // Basic Photolithographic Framework casing
                    .where('c', getSecondCasingState()) // Plascrete
                    .where('D', getThirdCasingState("T1Grate"))  // Grate casing
                    .where('F', getFrameState("T1Frame")) // HSLA Steel frame
                    .where('G', getGlassState("T1StructureGlass")) // Laminated glass
                    .where('X', getThirdCasingState("T1Substrate")) // Substrate casing
                    .where('w', MetaTileEntities.COMPUTATION_HATCH_RECEIVER[ZPM], EnumFacing.EAST)
                    .where('i', MetaTileEntities.ITEM_IMPORT_BUS[1], EnumFacing.SOUTH)
                    .where('j', MetaTileEntities.ITEM_EXPORT_BUS[1], EnumFacing.SOUTH)
                    .where('k', MetaTileEntities.FLUID_IMPORT_HATCH[1], EnumFacing.SOUTH)
                    .where('e', MetaTileEntities.ENERGY_INPUT_HATCH[LuV], EnumFacing.NORTH)
                    .where('p', MetaTileEntities.MAINTENANCE_HATCH, EnumFacing.NORTH)
                    .where('#', Blocks.AIR.getDefaultState())
                    .where('*', Blocks.AIR.getDefaultState())
                    .where(' ', Blocks.AIR.getDefaultState());
            shapeInfo.add(builder.build());
            //  Main Structure (T2)
            shapeInfo.add(builder
                    .where('E', getCasingState("T2StructureCasing")) // Mold Printing Assembly Framework casing
                    .where('f', getFrameState("T2Frame")) // Tungsten Steel Frame
                    .build());
            //  Liquid Cooling Tower (Cooling Upgrade T1)
            shapeInfo.add(builder
                    .where('H', getCasingState("T2StructureCasing")) // Mold Printing Assembly Framework casing
                    .where('I', getCasingState("NaquadahAlloyCasing")) // Naquadah Alloy casing
                    .where('J', getThirdCasingState("CoolingTowerIntake")) // Extreme Intake casing
                    .where('P', getBoilerCasingState()) // Tungsten Steel Pipe casing
                    .where('g', getFrameState("CoolingTowerFrame")) // Black Steel Frame
                    .build());
            //  Main Structure (T3)
            shapeInfo.add(builder
                    .where('K', getCasingState("T3StructureCasing")) // Radiation Proof Scan Framework casing
                    .where('L', getCasingState("NaquadahAlloyCasing")) // Naquadah Alloy casing
                    .build());
            //  Bio Chamber (Bio Upgrade)
            shapeInfo.add(builder
                    .where('M', getCasingState("BioChamberCasing")) // Biological Sterile Machine casing
                    .where('h', getFrameState("BioChamberFrame")) // Silicon Carbide frame
                    .where('N', getGlassState("BioChamberGlass")) // Cleanroom glass
                    .build());
            //  Thermosink (Cooling Upgrade T2)
            shapeInfo.add(builder
                    .where('O', getCasingState("ThermosinkCasing")) // Infinity Cooled Machine casing
                    .where('Q', getBoilerCasingState()) // Tungsten Steel Pipe casing
                    .where('R', getThirdCasingState("CoolingTowerIntake")) // Extreme Intake casing
                    .where('T', getCasingState("T2StructureCasing")) // Mold Printing Assembly Framework casing
                    .where('U', getCoilState()) // Superconductor Coil block
                    .where('n', getFrameState("ThermosinkFrame")) // Naquadah frame
                    .build());
        }
        return shapeInfo;
    }

    @Override
    public void addInformation(ItemStack stack,
                               @Nullable World player,
                               @Nonnull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("先进的电子厂", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.pcb_factory.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.pcb_factory.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.pcb_factory.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.machine.pcb_factory.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.machine.pcb_factory.tooltip.5"));
        tooltip.add(I18n.format("gtqtcore.machine.pcb_factory.tooltip.6"));
        tooltip.add(I18n.format("gtqtcore.machine.pcb_factory.tooltip.7"));
        tooltip.add(I18n.format("gtqtcore.machine.pcb_factory.tooltip.8"));
        tooltip.add(I18n.format("gtqtcore.machine.pcb_factory.tooltip.9")
                + TooltipHelper.RAINBOW_SLOW + I18n.format("gtqtcore.machine.pcb_factory.tooltip.9.25")
                + TextFormatting.GRAY + I18n.format("gtqtcore.machine.pcb_factory.tooltip.9.5"));
        tooltip.add(I18n.format("gtqtcore.machine.pcb_factory.tooltip.10"));
        tooltip.add(I18n.format("gtqtcore.machine.pcb_factory.tooltip.11"));
        tooltip.add(I18n.format("gtqtcore.machine.pcb_factory.tooltip.12"));
        tooltip.add(I18n.format("gtqtcore.machine.pcb_factory.tooltip.13"));
        tooltip.add(I18n.format("gtqtcore.machine.pcb_factory.tooltip.14"));
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.acc.tooltip"));
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.laser.tooltip"));
    }

    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("gtqtcore.machine.pcb_factory.description")};
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, this.isStructureFormed())
                .addCustom((tl) -> {
                    if (this.isStructureFormed()) {
                        tl.add(new TextComponentTranslation("gtqtcore.kqcc_accelerate", requestCWUt, getAccelerateByCWU(requestCWUt)));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "gtqtcore.machine.pcb_factory.structure.info", this.getMainUpgradeNumber(), this.getTraceSize()));
                        if (this.getCoolingUpgradeNumber() > 0) {
                            tl.add(TextComponentUtil.translationWithColor(TextFormatting.AQUA, "gtqtcore.machine.pcb_factory.structure.cooling_tower"));
                            if (this.getCoolingUpgradeNumber() == 2) {
                                tl.add(TextComponentUtil.translationWithColor(TextFormatting.BLUE, "gtqtcore.machine.pcb_factory.structure.thermosink"));
                            }
                        }
                        if (this.getBioUpgradeNumber() == 1) {
                            tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "gtqtcore.machine.pcb_factory.structure.bio_chamber"));
                        }
                    }
                })
                .setWorkingStatus(this.recipeMapWorkable.isWorkingEnabled(), this.recipeMapWorkable.isActive())
                .addEnergyUsageLine(this.recipeMapWorkable.getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(this.recipeMapWorkable.getMaxVoltage()))
                .addParallelsLine(this.recipeMapWorkable.getParallelLimit())
                .addWorkingStatusLine()
                .addProgressLine(this.recipeMapWorkable.getProgressPercent());
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return true;
    }

    public int getMainUpgradeNumber() {
        return this.mainUpgradeNumber;
    }

    public int getBioUpgradeNumber() {
        return this.bioUpgradeNumber;
    }

    public int getCoolingUpgradeNumber() {
        return this.coolingUpgradeNumber;
    }

    public int getTraceSize() {
        return this.traceSize;
    }

    private class PCBFactoryRecipeLogic extends MultiblockRecipeLogic {

        public PCBFactoryRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        /**
         * Tweak OC effect of PCB Factory.
         *
         * <ul>
         *     <li>{@link #coolingUpgradeNumber = 0} -> No OC ({@code 1.0});</li>
         *     <li>{@link #coolingUpgradeNumber = 1} -> Common OC ({@code 2.0};</li>
         *     <li>{@link #coolingUpgradeNumber = 2} -> Perfect OC ({@code 4.0}.</li>
         * </ul>
         *
         * @return Duration Divisor of OC Duration.
         */
        @Override
        protected double getOverclockingDurationFactor() {
            return switch (coolingUpgradeNumber) {
                case 1 -> 0.5;
                case 2 -> 0.25;
                default -> 1.0;
            };
        }

        @Override
        public int getParallelLimit() {
            return this.calculateParallelBySwarm();
        }

        public int calculateParallelBySwarm() {
            List<IItemHandlerModifiable> itemInputInventory = getAbilities(MultiblockAbility.IMPORT_ITEMS);
            IItemHandlerModifiable itemInputs = new ItemHandlerList(itemInputInventory);
            int parallelBase = 0;
            for (int i = 0; i < itemInputs.getSlots(); i++) {
                parallelBase = itemInputs.getStackInSlot(i).getCount();
                if (mainUpgradeNumber == 2) {
                    if (itemInputs.getStackInSlot(i).isItemEqual(OreDictUnifier.get(swarm, Silver))) {
                        return parallelBase;
                    }
                }
                if (mainUpgradeNumber == 3) {
                    if (itemInputs.getStackInSlot(i).isItemEqual(OreDictUnifier.get(swarm, Gold))) {
                        return parallelBase * 2;
                    }
                }
            }
            return parallelBase;
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            maxProgress = (int) Math.floor(maxProgress * getAccelerateByCWU(requestCWUt));
            this.maxProgressTime = switch (traceSize) {
                case 25 -> (int) Math.floor(0.4 * maxProgress);
                case 50 -> (int) Math.floor(0.6 * maxProgress);
                case 75 -> (int) Math.floor(0.8 * maxProgress);
                case 125 -> (int) Math.floor(1.2 * maxProgress);
                case 150 -> (int) Math.floor(1.4 * maxProgress);
                case 175 -> (int) Math.floor(1.6 * maxProgress);
                case 200 -> (int) Math.floor(1.8 * maxProgress);
                default -> maxProgress;
            };
        }

        @Override
        protected void updateRecipeProgress() {
            int actuallyEnergyConsumed = (int) (this.recipeEUt * switch (traceSize) {
                case 25 -> 2.5;
                case 50 -> 2;
                case 75 -> 1.5;
                case 125 -> 0.9;
                case 150 -> 0.8;
                case 175 -> 0.7;
                case 200 -> 0.6;
                default -> 1;
            });
            if (this.canRecipeProgress && this.drawEnergy(actuallyEnergyConsumed, true)) {
                this.drawEnergy(actuallyEnergyConsumed, false);
                if (++this.progressTime > this.maxProgressTime) {
                    this.completeRecipe();
                }
                if (this.hasNotEnoughEnergy && this.getEnergyInputPerSecond() > 19L * (long) actuallyEnergyConsumed) {
                    this.hasNotEnoughEnergy = false;
                }
            } else if (actuallyEnergyConsumed > 0) {
                this.hasNotEnoughEnergy = true;
                this.decreaseProgress();
            }
        }

    }

}