package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.GTValues.V;

public class MetaTileEntityDissolutionTank extends RecipeMapMultiblockController {
    private int glass_tier;

    public MetaTileEntityDissolutionTank(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.DISSOLUTION_TANK_RECIPES);
        this.recipeMapWorkable = new DissolutionTankWorkableHandler(this);
    }

    private static IBlockState getCasingBState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF);
    }

    protected IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing1.getState(BlockMultiblockCasing1.CasingType.Talonite);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.Talonite;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityDissolutionTank(metaTileEntityId);
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        if (super.checkRecipe(recipe, consumeIfSuccess)) {
            List<IItemHandlerModifiable> itemInputInventory = getAbilities(MultiblockAbility.IMPORT_ITEMS);
            List<IFluidTank> fluidInputInventory = getAbilities(MultiblockAbility.IMPORT_FLUIDS);
            double ratio = 1;
            int count = 0;
            for (IFluidTank input : fluidInputInventory) {
                for (GTRecipeInput recipe_input : recipe.getFluidInputs()) {
                    if (recipe_input.acceptsFluid(input.getFluid())) {
                        if (count == 0) {
                            ratio = (double) recipe_input.getAmount() / input.getFluidAmount();
                            count = 1;
                        } else {
                            if (!(ratio == (double) recipe_input.getAmount() / input.getFluidAmount())) {
                                return false;
                            }
                        }
                    }
                }
            }
            for (IItemHandlerModifiable input : itemInputInventory) {
                for (GTRecipeInput recipe_input : recipe.getInputs()) {
                    if (recipe_input.isNonConsumable()) {
                        continue;
                    }
                    for (int index = 0; index < input.getSlots(); index++) {
                        // Skip this slot if it is empty.
                        final ItemStack currentInputItem = input.getStackInSlot(index);
                        if (currentInputItem.isEmpty())
                            continue;
                        if (recipe_input.acceptsStack(input.getStackInSlot(index))) {
                            if (count == 1) {
                                if (!(ratio == (double) recipe_input.getAmount() / input.getStackInSlot(index).getCount())) {
                                    return false;
                                }
                            } else {
                                ratio = (double) recipe_input.getAmount() / input.getStackInSlot(index).getCount();
                                count = 1;
                            }
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("M   M", "MMMMM", "MGGGM", "MGGGM", " MMM ")
                .aisle("     ", "MNNNM", "G###G", "G###G", "MMMMM")
                .aisle("     ", "MNNNM", "G###G", "G###G", "MMMMM")
                .aisle("     ", "MNNNM", "G###G", "G###G", "MMMMM")
                .aisle("M   M", "MMSMM", "MGGGM", "MGGGM", " MMM ")
                .where('S', selfPredicate())
                .where('M', states(getCasingState()).or(autoAbilities()))
                .where('G', TiredTraceabilityPredicate.CP_LGLASS.get())
                .where('N', states(getCasingBState()))
                .where('#', air())
                .where(' ', any())
                .build();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object glass_tier = context.get("LGLTieredStats");

        this.glass_tier = GTQTUtil.getOrDefault(() -> glass_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) glass_tier).getIntTier(),
                0);
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("自动化噩梦", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.dissolution_tank.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.gtqt.update.1"));
        tooltip.add(I18n.format("gtqtcore.machine.progress_time", "maxProgress * (100 - glass_tier) / 100"));
        tooltip.add(I18n.format("gtqtcore.machine.modify_overclock", "Coil Tier"));
        tooltip.add(I18n.format("gtqtcore.machine.parallel.pow.custom", 2, "Glass Tier", 256));
        tooltip.add(I18n.format("gtqtcore.machine.max_voltage"));

    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
    }

    protected class DissolutionTankWorkableHandler extends MultiblockRecipeLogic {
        public DissolutionTankWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }


        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = maxProgress * (100 - glass_tier) / 100;
        }

        public long getMaxVoltage() {
            return V[glass_tier];
        }

        @Override
        public int getParallelLimit() {
            return Math.min((int) Math.pow(2, glass_tier), 256);
        }
    }
}
