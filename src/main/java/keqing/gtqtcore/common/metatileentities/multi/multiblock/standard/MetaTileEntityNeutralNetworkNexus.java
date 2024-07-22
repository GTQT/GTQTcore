package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMultiblockCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.recipes.properties.SwarmTierProperty;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import keqing.gtqtcore.loaders.recipes.GTQTRecipes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.ZPM;
import static keqing.gtqtcore.api.pattern.GTQTTraceabilityPredicate.optionalAbilities;
import static keqing.gtqtcore.api.pattern.GTQTTraceabilityPredicate.optionalStates;
import static keqing.gtqtcore.common.block.blocks.GTQTMultiblockCasing.CasingType.NAQUADAH_ALLOY_CASING;
import static keqing.gtqtcore.common.block.blocks.GTQTMultiblockCasing.CasingType.NAQUADRIA_CASING;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing.TurbineCasingType.HYPER_CASING;

public class MetaTileEntityNeutralNetworkNexus extends MultiMapMultiblockController {

    private byte auxiliaryUpgradeNumber = 0;

    public MetaTileEntityNeutralNetworkNexus(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.BLAST_RECIPES});
                //GTLiteRecipeMaps.NEUTRAL_NETWORK_NEXUS_ASSEMBLING_MODE,
                //GTLiteRecipeMaps.NEUTRAL_NETWORK_NEXUS_BREEDING_MODE,
                //GTLiteRecipeMaps.NEUTRAL_NETWORK_NEXUS_HYBRIDIZING_MODE});
        this.recipeMapWorkable = new MultiblockRecipeLogic(this, true);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityNeutralNetworkNexus(metaTileEntityId);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
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
    public boolean checkRecipe( Recipe recipe,
                               boolean consumeIfSuccess) {
        return super.checkRecipe(recipe, consumeIfSuccess) && recipe.getProperty(SwarmTierProperty.getInstance(), 0) <= auxiliaryUpgradeNumber;
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
                        .or(autoAbilities()))
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

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.MULTI_CASING.getState(NAQUADAH_ALLOY_CASING);
    }

    private static IBlockState getSecondCasingState() {
        return GTQTMetaBlocks.MULTI_CASING.getState(NAQUADRIA_CASING);
    }

    private static IBlockState getThirdCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(HYPER_CASING);
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
    public void addInformation(ItemStack stack, World player, List<String> tooltip,boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gregtech.machine.perfect_oc"));
        tooltip.add(I18n.format("gtqtcore.machine.neutral_network_nexus.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.neutral_network_nexus.tooltip.2"));
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
                    .aisle("aaaaaaaa   CCCCCCC   cccccccc", "      f      FDF             ", "             FDF             ", "             FDF             ", " f           FDF             ", "             FDF             ", "             FDF             ", "      f      FDF             ", "             FDF             ", "             FDF             ", " f          FD#DF            ", "             D#D             ", "            DD#DD            ", "      f     DD#DD            ", "            DD#DD            ", "            DD#DD            ", " f          DD#DD            ", "            DD#DD            ", "            DD#DD            ", "      f     DD#DD            ", "            FD#DF            ", "            FD#DF            ", " f          FD#DF            ", "            FD#DF            ", "             FDF             ", "      f      FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "             FDF             ", "              F              ", "              F              ", "              F              ", "              F              ", "              F              ")
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
}
