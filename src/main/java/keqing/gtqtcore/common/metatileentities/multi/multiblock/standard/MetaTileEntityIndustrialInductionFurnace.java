package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1.CasingType.HastelloyN;

public class MetaTileEntityIndustrialInductionFurnace extends MultiMapMultiblockController {

    protected int heatingCoilLevel;
    protected int heatingCoilDiscount;

    public MetaTileEntityIndustrialInductionFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.REACTION_FURNACE_RECIPES,
                RecipeMaps.FURNACE_RECIPES,
                RecipeMaps.ALLOY_SMELTER_RECIPES
        });
        this.recipeMapWorkable = new InductionFurnaceRecipeLogic(this);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing1.getState(HastelloyN);
    }

    public static int getEUtForParallel(int parallel, int discount) {
        return 4 * Math.max(1, parallel / 8 / discount);
    }

    public static int getMaxParallel(int heatingCoilLevel) {
        return (int) Math.pow(2, Math.min(heatingCoilLevel, 8));
    }

    public static int getDurationForParallel(int parallel, int parallelLimit) {
        return (int) Math.max(1.0, (double) (256 * parallel) / Math.max(1.0, parallelLimit));
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityIndustrialInductionFurnace(metaTileEntityId);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
            this.heatingCoilDiscount = ((IHeatingCoilBlockStats) coilType).getEnergyDiscount();
        } else {
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
            this.heatingCoilDiscount = BlockWireCoil.CoilType.CUPRONICKEL.getEnergyDiscount();
        }

    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.heatingCoilLevel = 0;
        this.heatingCoilDiscount = 0;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "CCC", "CCC", "XXX")
                .aisle("XXX", "C#C", "C#C", "XMX")
                .aisle("XSX", "CCC", "CCC", "XXX")
                .where('S', this.selfPredicate())
                .where('X', states(getCasingState())
                        .setMinGlobalLimited(9)
                        .or(this.autoAbilities(true, true, true, true, true, true, false)))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('C', heatingCoils())
                .where('#', air())
                .build();
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.iif.1"));
        tooltip.add(I18n.format("gregtech.machine.iif.2"));
        tooltip.add(I18n.format("gregtech.machine.iif.3"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.HastelloyN;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.MULTI_FURNACE_OVERLAY;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, this.isStructureFormed())
                .setWorkingStatus(this.recipeMapWorkable.isWorkingEnabled(), this.recipeMapWorkable.isActive())
                .addEnergyUsageLine(this.recipeMapWorkable.getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(this.recipeMapWorkable.getMaxVoltage()))
                .addCustom((tl) -> {
                    if (this.isStructureFormed()) {
                        TextComponentString parallels;
                        TextComponentTranslation bodyText;
                        TextComponentTranslation hoverText;
                        if (this.heatingCoilDiscount > 1) {
                            parallels = TextComponentUtil.stringWithColor(TextFormatting.AQUA, TextFormattingUtil.formatNumbers(100.0 / (double) this.heatingCoilDiscount) + "%");
                            bodyText = TextComponentUtil.translationWithColor(TextFormatting.GRAY, "gregtech.multiblock.multi_furnace.heating_coil_discount", parallels);
                            hoverText = TextComponentUtil.translationWithColor(TextFormatting.GRAY, "gregtech.multiblock.multi_furnace.heating_coil_discount_hover");
                            TextComponentUtil.setHover(bodyText, hoverText);
                            tl.add(bodyText);
                        }
                        if (this.recipeMapWorkable.getParallelLimit() > 1) {
                            parallels = TextComponentUtil.stringWithColor(TextFormatting.DARK_PURPLE, TextFormattingUtil.formatNumbers(this.recipeMapWorkable.getParallelLimit()));
                            bodyText = TextComponentUtil.translationWithColor(TextFormatting.GRAY, "gregtech.multiblock.parallel", parallels);
                            hoverText = TextComponentUtil.translationWithColor(TextFormatting.GRAY, "gregtech.multiblock.multi_furnace.parallel_hover");
                            tl.add(TextComponentUtil.setHover(bodyText, hoverText));
                        }
                    }
                })
                .addWorkingStatusLine().addProgressLine(this.recipeMapWorkable.getProgressPercent());
    }

    protected class InductionFurnaceRecipeLogic extends MultiblockRecipeLogic {

        public InductionFurnaceRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        public ParallelLogicType getParallelLogicType() {
            return ParallelLogicType.APPEND_ITEMS;
        }

        @Override
        public void applyParallelBonus(RecipeBuilder<?> builder) {
            builder.EUt(getEUtForParallel(builder.getParallel(), heatingCoilDiscount)).duration(getDurationForParallel(builder.getParallel(), this.getParallelLimit()));
        }

        @Override
        public int getParallelLimit() {
            return getMaxParallel(heatingCoilLevel);
        }
    }
}
