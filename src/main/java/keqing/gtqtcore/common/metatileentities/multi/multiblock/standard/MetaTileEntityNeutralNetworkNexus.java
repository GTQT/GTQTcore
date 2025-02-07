package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
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
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMultiblockCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.SwarmTierProperty;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.ZPM;
import static keqing.gtqtcore.GTQTCoreConfig.MachineSwitch;
import static keqing.gtqtcore.api.pattern.GTQTTraceabilityPredicate.optionalAbilities;
import static keqing.gtqtcore.api.pattern.GTQTTraceabilityPredicate.optionalStates;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.SWARM_ASSEMBLER;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.SWARM_GROWTH;
import static keqing.gtqtcore.api.utils.GTQTUtil.getAccelerateByCWU;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3.CasingType.NAQUADAH_ALLOY_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3.CasingType.NAQUADRIA_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.HYPER_CASING;

public class MetaTileEntityNeutralNetworkNexus extends MultiMapMultiblockController implements IOpticalComputationReceiver {

    int requestCWUt;
    private byte auxiliaryUpgradeNumber = 0;
    private IOpticalComputationProvider computationProvider;

    public MetaTileEntityNeutralNetworkNexus(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.NEUTRAL_NETWORK_NEXUS_BREEDING_MODE,
                SWARM_GROWTH,
                SWARM_ASSEMBLER
        });
        this.recipeMapWorkable = new MetaTileEntityNeutralNetworkNexusWorkable(this);
    }
    @Override
    public void checkStructurePattern() {
        if(MachineSwitch.DelayStructureCheckSwitch) {
            if (this.getOffsetTimer() % 100 == 0 || this.isFirstTick()) {
                super.checkStructurePattern();
            }
        }
        else super.checkStructurePattern();
    }
    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing3.getState(NAQUADAH_ALLOY_CASING);
    }

    private static IBlockState getSecondCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing3.getState(NAQUADRIA_CASING);
    }

    private static IBlockState getThirdCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(HYPER_CASING);
    }

    private static IBlockState getFourthCasingState() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.ASSEMBLY_LINE_CASING);
    }

    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(GTQTMaterials.HastelloyC59).getBlock(GTQTMaterials.HastelloyC59);
    }

    private static IBlockState getSecondFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Neutronium).getBlock(Materials.Neutronium);
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
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityNeutralNetworkNexus(metaTileEntityId);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        List<IOpticalComputationHatch> providers = this.getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
        if (providers != null && !providers.isEmpty()) {
            this.computationProvider = providers.get(0);
        }

        if (context.get("AuxiliaryUpgradeTier2") != null) {
            auxiliaryUpgradeNumber += 1;
        }
        if (context.get("AuxiliaryUpgradeTier3") != null) {
            auxiliaryUpgradeNumber += 1;
        }
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        auxiliaryUpgradeNumber = 1;
    }

    @Override
    public boolean checkRecipe(Recipe recipe,
                               boolean consumeIfSuccess) {
        return super.checkRecipe(recipe, consumeIfSuccess) && recipe.getProperty(SwarmTierProperty.getInstance(), 0) <= auxiliaryUpgradeNumber;
    }

    @Override
    public void updateFormedValid() {
        super.updateFormedValid();
        if (isStructureFormed() && isActive()) {
            requestCWUt = computationProvider.requestCWUt(2048, false);
        }
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("gtqtcore.kqcc_accelerate", requestCWUt, getAccelerateByCWU(requestCWUt)));
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "              D              ", "              D              ", "              D              ", "              D              ", "              D              ", "              D              ", "              D              ", "              D              ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("            CCCCC            ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("           CCCCCCC           ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "            FD#DF            ", "             D#D             ", "            DD#DD            ", "            DD#DD            ", "            DD#DD            ", "            DD#DD            ", "            DD#DD            ", "            DD#DD            ", "            DD#DD            ", "            DD#DD            ", "            FD#DF            ", "            FD#DF            ", "            FD#DF            ", "            FD#DF            ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "              F              ", "              F              ", "              F              ", "              F              ", "              F              ")
                .aisle("          CCCCCCCCC          ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "            FD#DF            ", "            FD#DF            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "           DD###DD           ", "           DD###DD           ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "              D              ", "              D              ", "              D              ", "              D              ", "              D              ")
                .aisle("          CCCCCCCCC          ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "            FD#DF            ", "            FD#DF            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "           DD###DD           ", "           DD###DD           ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "              D              ", "              D              ", "              D              ", "              D              ", "              D              ")
                .aisle("          CCCCCCCCC          ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "            FD#DF            ", "            FD#DF            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "           DD###DD           ", "           DD###DD           ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "              D              ", "              D              ", "              D              ", "              D              ", "              D              ")
                .aisle(" aaaaaa   CCCCCCCCC   cccccc ", "             D#D             ", "    ff       D#D             ", "  ff         D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "    ff      FD#DF            ", "  ff        FD#DF            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "    ff      D###D            ", "  ff       DD###DD           ", "           DD###DD           ", "            D###D            ", "            D###D            ", "            D###D            ", "    ff      D###D            ", "  ff        D###D            ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "              D              ", "              D              ", "              D              ", "              D              ", "              D              ")
                .aisle("aaaaaaaa   CCCCCCC   cccccccc", "      f      FDF             ", "             FDF             ", "             FDF             ", " f           FDF             ", "             FDF             ", "             FDF             ", "      f      FDF             ", "             FDF             ", "             FDF             ", " f          FD#DF            ", "             D#D             ", "            DD#DD            ", "      f     DD#DD            ", "            DD#DD            ", "            DD#DD            ", " f          DD#DD            ", "            DD#DD            ", "            DD#DD            ", "      f     DD#DD            ", "            FD#DF            ", "            FD#DF            ", " f          FD#DF            ", "            FD#DF            ", "             FDF             ", "      f      FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "              F              ", "              F              ", "              F              ", "              F              ", "              F              ")
                .aisle("aaaaaaaa    CCSCC    cccccccc", "                             ", "                             ", "                             ", "   bb                        ", "f  yy                   dd   ", "   bb  f                xx   ", "                        dd   ", "                             ", "                             ", "             FDF             ", "f            FDF             ", "       f     FDF        dd   ", "             FDF        xx   ", "   bb        FDF        dd   ", "   bb        FDF             ", "   bb        FDF             ", "f  bb        FDF             ", "       f     FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "f            FDF             ", "   bb  f                     ", "   yyf                       ", "   bb                        ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("aaaaaaaa             cccccccc", "   bb                   dd   ", "   bb                   dd   ", "   bb                   dd   ", "  bbbb                  dd   ", "f ybby                 dddd  ", "  bbbb f               xddx  ", "   bb                  dddd  ", "   bb                   dd   ", "   bb                   dd   ", "   bb                   dd   ", "f  bb                   dd   ", "   bb  f      D        dddd  ", "   bb         D        xddx  ", "  bbbb        D        dddd  ", "  bbbb        D              ", "  bbbb        D              ", "f bbbb        D              ", "   bb  f      D              ", "   bb         D              ", "   bb                        ", "   bb                        ", "   bb                        ", "f  bb                        ", "  bbbb f                     ", "  ybby                       ", "  bbbb                       ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("aaaaaaaa             cccccccc", "   bb                   dd   ", "   bb                   dd   ", "   bb                   dd   ", "  bbbb                  dd   ", "  ybby f               dddd  ", "f bbbb                 xddx  ", "   bb                  dddd  ", "   bb                   dd   ", "   bb                   dd   ", "   bb                   dd   ", "   bb  f                dd   ", "f  bb                  dddd  ", "   bb                  xddx  ", "  bbbb                 dddd  ", "  bbbb                       ", "  bbbb                       ", "  bbbb f                     ", "f  bb                        ", "   bb                        ", "   bb                        ", "   bb                        ", "   bb                        ", "   bb  f                     ", "f bbbb                       ", "  ybby                       ", "  bbbb                       ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("aaaaaaaa             cccccccc", "                             ", "                             ", "                             ", "   bb                        ", "   yy  f                dd   ", "f  bb                   xx   ", "                        dd   ", "                             ", "                             ", "                             ", "       f                     ", "f                       dd   ", "                        xx   ", "   bb                   dd   ", "   bb                        ", "   bb                        ", "   bb  f                     ", "f                            ", "                             ", "                             ", "                             ", "                             ", "       f                     ", "f  bb                        ", "  fyy                        ", "   bb                        ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("aaaaaaaa             cccccccc", " f                           ", "                             ", "                             ", "      f                      ", "                             ", "                             ", " f                           ", "                             ", "                             ", "      f                      ", "                             ", "                             ", " f                           ", "                             ", "                             ", "      f                      ", "                             ", "                             ", " f                           ", "                             ", "                             ", "      f                      ", "                             ", "                             ", " f                           ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle(" aaaaaa               cccccc ", "                             ", "  ff                         ", "    ff                       ", "                             ", "                             ", "                             ", "                             ", "  ff                         ", "    ff                       ", "                             ", "                             ", "                             ", "                             ", "  ff                         ", "    ff                       ", "                             ", "                             ", "                             ", "                             ", "  ff                         ", "    ff                       ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .where('S', this.selfPredicate())
                .where('C', states(getCasingState())
                        .setMinGlobalLimited(50)
                        .or(autoAbilities())
                        .or(abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1))
                        .or(abilities(MultiblockAbility.INPUT_LASER).setMaxGlobalLimited(1))
                )
                .where('c', optionalStates("AuxiliaryUpgradeTier2", getSecondCasingState())
                        .setMinGlobalLimited(50)
                        .or(optionalAbilities("AuxiliaryUpgradeTier2", MultiblockAbility.IMPORT_ITEMS))
                        .or(optionalAbilities("AuxiliaryUpgradeTier2", MultiblockAbility.EXPORT_ITEMS))
                        .or(optionalAbilities("AuxiliaryUpgradeTier2", MultiblockAbility.IMPORT_FLUIDS))
                        .or(optionalAbilities("AuxiliaryUpgradeTier2", MultiblockAbility.EXPORT_FLUIDS)))
                .where('a', optionalStates("AuxiliaryUpgradeTier3", getThirdCasingState())
                        .setMinGlobalLimited(50)
                        .or(optionalAbilities("AuxiliaryUpgradeTier3", MultiblockAbility.IMPORT_ITEMS))
                        .or(optionalAbilities("AuxiliaryUpgradeTier3", MultiblockAbility.EXPORT_ITEMS))
                        .or(optionalAbilities("AuxiliaryUpgradeTier3", MultiblockAbility.IMPORT_FLUIDS))
                        .or(optionalAbilities("AuxiliaryUpgradeTier3", MultiblockAbility.EXPORT_FLUIDS)))
                .where('b', optionalStates("AuxiliaryUpgradeTier3", getThirdCasingState()))
                .where('d', optionalStates("AuxiliaryUpgradeTier2", getSecondCasingState()))
                .where('D', states(getCasingState()))
                .where('F', states(getFrameState()))
                .where('f', optionalStates("AuxiliaryUpgradeTier3", getSecondFrameState()))
                .where('x', optionalStates("AuxiliaryUpgradeTier2", getFourthCasingState()))
                .where('y', optionalStates("AuxiliaryUpgradeTier3", getFourthCasingState()))
                .where('#', air())
                .where(' ', any())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.NQ_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.NEUTRAL_NETWORK_NEXUS_OVERLAY;
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("马蜂窝", new Object[0]));
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gregtech.machine.perfect_oc"));
        tooltip.add(I18n.format("gtqtcore.machine.neutral_network_nexus.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.neutral_network_nexus.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.acc.tooltip"));
        tooltip.add(I18n.format("本机器允许使用激光能源仓代替能源仓！"));
    }

    @Override
    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = null;
        if (Blocks.AIR != null) {
            builder = MultiblockShapeInfo.builder()
                    .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "              D              ", "              D              ", "              D              ", "              D              ", "              D              ", "              D              ", "              D              ", "              D              ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                    .aisle("            CEMEC            ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                    .aisle("           CCCCCCC           ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "            FD#DF            ", "             D#D             ", "            DD#DD            ", "            DD#DD            ", "            DD#DD            ", "            DD#DD            ", "            DD#DD            ", "            DD#DD            ", "            DD#DD            ", "            DD#DD            ", "            FD#DF            ", "            FD#DF            ", "            FD#DF            ", "            FD#DF            ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "              F              ", "              F              ", "              F              ", "              F              ", "              F              ")
                    .aisle("          CCCCCCCCC          ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "            FD#DF            ", "            FD#DF            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "           DD###DD           ", "           DD###DD           ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "              D              ", "              D              ", "              D              ", "              D              ", "              D              ")
                    .aisle("          CCCCCCCCC          ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "            FD#DF            ", "            FD#DF            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "           DD###DD           ", "           DD###DD           ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "              D              ", "              D              ", "              D              ", "              D              ", "              D              ")
                    .aisle("          CCCCCCCCC          ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "            FD#DF            ", "            FD#DF            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "           DD###DD           ", "           DD###DD           ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "              D              ", "              D              ", "              D              ", "              D              ", "              D              ")
                    .aisle(" aaaaaa   CCCCCCCCC   cccccc ", "             D#D             ", "    ff       D#D             ", "  ff         D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "    ff      FD#DF            ", "  ff        FD#DF            ", "            D###D            ", "            D###D            ", "            D###D            ", "            D###D            ", "    ff      D###D            ", "  ff       DD###DD           ", "           DD###DD           ", "            D###D            ", "            D###D            ", "            D###D            ", "    ff      D###D            ", "  ff        D###D            ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "             D#D             ", "              D              ", "              D              ", "              D              ", "              D              ", "              D              ")
                    .aisle("aaaaaaaa   CCCCCCH   cccccccc", "      f      FDF             ", "             FDF             ", "             FDF             ", " f           FDF             ", "             FDF             ", "             FDF             ", "      f      FDF             ", "             FDF             ", "             FDF             ", " f          FD#DF            ", "             D#D             ", "            DD#DD            ", "      f     DD#DD            ", "            DD#DD            ", "            DD#DD            ", " f          DD#DD            ", "            DD#DD            ", "            DD#DD            ", "      f     DD#DD            ", "            FD#DF            ", "            FD#DF            ", " f          FD#DF            ", "            FD#DF            ", "             FDF             ", "      f      FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "              F              ", "              F              ", "              F              ", "              F              ", "              F              ")
                    .aisle("aaaaaaaa    IJSKL    cccccccc", "                             ", "                             ", "                             ", "   bb                        ", "f  yy                   dd   ", "   bb  f                xx   ", "                        dd   ", "                             ", "                             ", "             FDF             ", "f            FDF             ", "       f     FDF        dd   ", "             FDF        xx   ", "   bb        FDF        dd   ", "   bb        FDF             ", "   bb        FDF             ", "f  bb        FDF             ", "       f     FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "f            FDF             ", "   bb  f                     ", "   yyf                       ", "   bb                        ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                    .aisle("aaaaaaaa             cccccccc", "   bb                   dd   ", "   bb                   dd   ", "   bb                   dd   ", "  bbbb                  dd   ", "f ybby                 dddd  ", "  bbbb f               xddx  ", "   bb                  dddd  ", "   bb                   dd   ", "   bb                   dd   ", "   bb                   dd   ", "f  bb                   dd   ", "   bb  f      D        dddd  ", "   bb         D        xddx  ", "  bbbb        D        dddd  ", "  bbbb        D              ", "  bbbb        D              ", "f bbbb        D              ", "   bb  f      D              ", "   bb         D              ", "   bb                        ", "   bb                        ", "   bb                        ", "f  bb                        ", "  bbbb f                     ", "  ybby                       ", "  bbbb                       ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                    .aisle("aaaaaaaa             cccccccc", "   bb                   dd   ", "   bb                   dd   ", "   bb                   dd   ", "  bbbb                  dd   ", "  ybby f               dddd  ", "f bbbb                 xddx  ", "   bb                  dddd  ", "   bb                   dd   ", "   bb                   dd   ", "   bb                   dd   ", "   bb  f                dd   ", "f  bb                  dddd  ", "   bb                  xddx  ", "  bbbb                 dddd  ", "  bbbb                       ", "  bbbb                       ", "  bbbb f                     ", "f  bb                        ", "   bb                        ", "   bb                        ", "   bb                        ", "   bb                        ", "   bb  f                     ", "f bbbb                       ", "  ybby                       ", "  bbbb                       ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                    .aisle("aaaaaaaa             cccccccc", "                             ", "                             ", "                             ", "   bb                        ", "   yy  f                dd   ", "f  bb                   xx   ", "                        dd   ", "                             ", "                             ", "                             ", "       f                     ", "f                       dd   ", "                        xx   ", "   bb                   dd   ", "   bb                        ", "   bb                        ", "   bb  f                     ", "f                            ", "                             ", "                             ", "                             ", "                             ", "       f                     ", "f  bb                        ", "  fyy                        ", "   bb                        ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                    .aisle("aaaaaaaa             cccccccc", " f                           ", "                             ", "                             ", "      f                      ", "                             ", "                             ", " f                           ", "                             ", "                             ", "      f                      ", "                             ", "                             ", " f                           ", "                             ", "                             ", "      f                      ", "                             ", "                             ", " f                           ", "                             ", "                             ", "      f                      ", "                             ", "                             ", " f                           ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                    .aisle(" aaaaaa               cccccc ", "                             ", "  ff                         ", "    ff                       ", "                             ", "                             ", "                             ", "                             ", "  ff                         ", "    ff                       ", "                             ", "                             ", "                             ", "                             ", "  ff                         ", "    ff                       ", "                             ", "                             ", "                             ", "                             ", "  ff                         ", "    ff                       ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                    .where('S', GTQTMetaTileEntities.NEUTRAL_NETWORK_NEXUS, EnumFacing.SOUTH)
                    .where('C', getCasingState())
                    .where('D', getCasingState())
                    .where('F', getFrameState())
                    .where('H', MetaTileEntities.COMPUTATION_HATCH_RECEIVER, EnumFacing.SOUTH)
                    .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[ZPM], EnumFacing.NORTH)
                    .where('I', MetaTileEntities.ITEM_IMPORT_BUS[ZPM], EnumFacing.SOUTH)
                    .where('J', MetaTileEntities.ITEM_EXPORT_BUS[ZPM], EnumFacing.SOUTH)
                    .where('K', MetaTileEntities.FLUID_IMPORT_HATCH[ZPM], EnumFacing.SOUTH)
                    .where('L', MetaTileEntities.FLUID_EXPORT_HATCH[ZPM], EnumFacing.SOUTH)
                    .where('M', MetaTileEntities.MAINTENANCE_HATCH, EnumFacing.NORTH)
                    .where(' ', Blocks.AIR.getDefaultState())
                    .where('#', Blocks.AIR.getDefaultState());
            shapeInfo.add(builder.build());
            shapeInfo.add(builder
                    .where('c', getSecondCasingState())
                    .where('d', getSecondCasingState())
                    .where('x', getFourthCasingState())
                    .build());
            shapeInfo.add(builder
                    .where('a', getThirdCasingState())
                    .where('b', getThirdCasingState())
                    .where('y', getFourthCasingState())
                    .where('f', getSecondFrameState())
                    .build());
        }
        return shapeInfo;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    protected class MetaTileEntityNeutralNetworkNexusWorkable extends MultiblockRecipeLogic {

        public MetaTileEntityNeutralNetworkNexusWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity, true);
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (maxProgress*getAccelerateByCWU(requestCWUt)));
        }
    }
}
