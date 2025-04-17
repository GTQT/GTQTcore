package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.LaserSystem;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.capability.ICatalystHatch;
import keqing.gtqtcore.api.capability.impl.MultiblockLaserRecipeLogic;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.metaileentity.multiblock.RecipeMapLaserMultiblockController;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.CatalystProperties;
import keqing.gtqtcore.api.recipes.properties.ChemicalPlantProperties;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.NQ_MACHINE_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.NQ_TURBINE_CASING;

public class MetaTileEntityLaserChemicalPlant extends RecipeMapLaserMultiblockController {

    public MetaTileEntityLaserChemicalPlant(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.CHEMICAL_PLANT);
        this.recipeMapWorkable = new ChemicalPlantLogic(this);
    }

    private static IBlockState getUniqueCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(NQ_TURBINE_CASING);
    }

    private static IBlockState getThirdCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(NQ_MACHINE_CASING);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.IRIDIUM_CASING);
    }

    private static IBlockState getSecondCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.ADVANCED_FILTER_CASING);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("EEEEEEE", "C#####C", "C#####C", "C#####C", "C#####C", "C#####C", "CCCCCCC")
                .aisle("EMMMMME", "#MMMMM#", "#######", "#######", "#######", "#MMMMM#", "CCCCCCC")
                .aisle("EMMMMME", "#MXXXM#", "##TTT##", "##XXX##", "##TTT##", "#MXXXM#", "CCCCCCC")
                .aisle("EMMMMME", "#MXAXM#", "##TAT##", "##XAX##", "##TAT##", "#MXAXM#", "CCCCCCC")
                .aisle("EMMMMME", "#MXXXM#", "##TTT##", "##XXX##", "##TTT##", "#MXXXM#", "CCCCCCC")
                .aisle("EMMMMME", "#MMMMM#", "#######", "#######", "#######", "#MMMMM#", "CCCCCCC")
                .aisle("EEHSQEE", "C#####C", "C#####C", "C#####C", "C#####C", "C#####C", "CCCCCCC")

                .where('S', this.selfPredicate())
                .where('E', states(getCasingState())
                        .or(autoAbilities(false, false, true, true, true, true, false))
                        .or(abilities(GTQTMultiblockAbility.LASER_INPUT).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(abilities(GTQTMultiblockAbility.CATALYST_MULTIBLOCK_ABILITY).setExactLimit(1))
                )
                .where('C', states(getCasingState()))
                .where('A', getFramePredicate())
                .where('M', states(getSecondCasingState()))
                .where('X', states(getUniqueCasingState()))
                .where('T', states(getThirdCasingState()))
                .where('H', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('Q', abilities(MultiblockAbility.MAINTENANCE_HATCH))
                .where('#', any())
                .build();
    }

    public TraceabilityPredicate getFramePredicate() {
        return frames(Materials.Naquadah);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLaserChemicalPlant(metaTileEntityId);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.IRIDIUM_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        if (!super.checkRecipe(recipe, consumeIfSuccess)) return false;
        return (int) ((long) this.getTemp() / 1800) >= recipe.getProperty(ChemicalPlantProperties.getInstance(), 0);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            super.addDisplayText(textList);
            textList.add(new TextComponentTranslation("激光转换化工厂等级：%s", (int) ((long) this.getTemp() / 1800)));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("激光转换化工厂等级：温度 /1800"));
    }

    public ICatalystHatch getCatalystHatch() {
        List<ICatalystHatch> abilities = getAbilities(GTQTMultiblockAbility.CATALYST_MULTIBLOCK_ABILITY);
        if (abilities.isEmpty())
            return null;
        return abilities.get(0);
    }

    protected class ChemicalPlantLogic extends MultiblockLaserRecipeLogic {

        public ChemicalPlantLogic(RecipeMapLaserMultiblockController tileEntity) {
            super(tileEntity);

        }

        @Override
        public boolean checkRecipe(@Nonnull Recipe recipe) {
            if (!super.checkRecipe(recipe)) {
                return false;
            }

            CatalystProperties catalystProps = CatalystProperties.getInstance();

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
