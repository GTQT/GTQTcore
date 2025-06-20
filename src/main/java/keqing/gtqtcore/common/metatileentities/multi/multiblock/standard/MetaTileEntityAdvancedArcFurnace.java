package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregicality.multiblocks.api.capability.IParallelMultiblock;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.logic.OCParams;
import gregtech.api.recipes.logic.OCResult;
import gregtech.api.recipes.logic.OverclockingLogic;
import gregtech.api.recipes.properties.RecipePropertyStorage;
import gregtech.api.recipes.properties.impl.TemperatureProperty;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockBoilerCasing.BoilerCasingType;
import gregtech.common.blocks.BlockFireboxCasing;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.capability.IElectrode;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.recipes.logic.OverclockingLogic.heatingCoilOC;


public class MetaTileEntityAdvancedArcFurnace extends MultiMapMultiblockController implements IParallelMultiblock {
    private int ElectrodeTier;
    @Override
    public boolean usesMui2() {
        return false;
    }
    public MetaTileEntityAdvancedArcFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.ARC_FURNACE_RECIPES,
                RecipeMaps.FURNACE_RECIPES
        });
        this.recipeMapWorkable = new AdvancedArcFurnaceWorkableHandler(this);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAdvancedArcFurnace(this.metaTileEntityId);
    }


    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("电极状态：%s 电极等级：%s", checkAvailable(), ElectrodeTier));

    }

    @Override
    public void updateFormedValid() {
        super.updateFormedValid();
        if (!isStructureFormed()) return;
        if (!checkAvailable()) return;
        ElectrodeTier = getElectrodeTier();
    }

    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("  AAA  ", "  AAA  ", "  BBB  ", "  AAA  ", "  BBB  ", "  AAA  ", "       ", "       ", "       ", "       ", "       ")
                .aisle(" AAAAA ", " A   A ", " B   B ", " A   A ", " B   B ", " A   A ", "  AAA  ", "       ", "       ", "  AAA  ", "       ")
                .aisle("AAAAAAA", "A C C A", "B C C B", "A C C A", "B C C B", "A C C A", " ACACA ", "  C C  ", "  C C  ", " ACACA ", "  D D  ")
                .aisle("AAAAAAA", "A     A", "B     B", "A     A", "B     B", "A     A", " AAAAA ", "       ", "       ", " AAAAA ", "       ")
                .aisle("AAAAAAA", "A C C A", "B C C B", "A C C A", "B C C B", "A C C A", " ACACA ", "  C C  ", "  C C  ", " ACACA ", "  D D  ")
                .aisle(" AAAAA ", " A   A ", " B   B ", " A   A ", " B   B ", " A   A ", "  AAA  ", "       ", "       ", "  AAA  ", "       ")
                .aisle("  AAA  ", "  ASA  ", "  BBB  ", "  AAA  ", "  BBB  ", "  AAA  ", "  E E  ", "  E E  ", "  E E  ", "  AAA  ", "       ")
                .where('S', selfPredicate())
                .where('A', states(MetaBlocks.METAL_CASING.getState(MetalCasingType.TUNGSTENSTEEL_ROBUST)).setMinGlobalLimited(55)
                        .or(autoAbilities()))
                .where('D', abilities(GTQTMultiblockAbility.ELECTRODE_MULTIBLOCK_ABILITY))
                .where('C', states(MetaBlocks.BOILER_CASING.getState((BoilerCasingType.STEEL_PIPE))))
                .where('E', states(MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel)))
                .where('B', states(MetaBlocks.BOILER_FIREBOX_CASING.getState(BlockFireboxCasing.FireboxCasingType.TUNGSTENSTEEL_FIREBOX)))
                .where(' ', any())
                .where('#', air())
                .build();
    }

    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.ROBUST_TUNGSTENSTEEL_CASING;
    }

    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("彼岸双生"));
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gregtech.machine.perfect_oc", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.modify_overclock", "Electrode Tier"));
        tooltip.add(I18n.format("gtqtcore.machine.parallel.pow.custom", 2, "Electrode Tier", 64));
        tooltip.add(I18n.format("gtqtcore.machine.max_voltage"));
    }

    public IElectrode getElectrodeHatch(int i) {
        List<IElectrode> abilities = getAbilities(GTQTMultiblockAbility.ELECTRODE_MULTIBLOCK_ABILITY);
        if (abilities.isEmpty())
            return null;
        return abilities.get(i);
    }

    public int getElectrodeTier() {
        int minTier = 5;
        for (int i = 0; i < 4; i++) {
            if (minTier > getElectrodeHatch(i).getElectrodeTier())
                minTier = getElectrodeHatch(i).getElectrodeTier();
        }
        return minTier;
    }

    public boolean checkAvailable() {
        for (int i = 0; i < 4; i++) {
            if (!getElectrodeHatch(i).isAvailable())
                return false;
        }
        return true;
    }

    public void setWork(boolean work) {
        if (checkAvailable()) {
            for (int i = 0; i < 14; i++)
                getElectrodeHatch(i).setWork(work);
        }
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }

    private class AdvancedArcFurnaceWorkableHandler extends MultiblockRecipeLogic {
        public AdvancedArcFurnaceWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity, true);
        }

        @Override
        public int getParallelLimit() {
            return Math.min((int) Math.pow(2, ElectrodeTier), 64);
        }

        @Override
        protected void modifyOverclockPre(OCParams ocParams, RecipePropertyStorage storage) {
            super.modifyOverclockPre(ocParams, storage);
            // coil EU/t discount
            ocParams.setEut(OverclockingLogic.applyCoilEUtDiscount(ocParams.eut(),
                    ElectrodeTier,
                    1));
        }

        @Override
        protected void runOverclockingLogic(OCParams ocParams, OCResult ocResult,
                                            RecipePropertyStorage propertyStorage, long maxVoltage) {
            heatingCoilOC(ocParams, ocResult, maxVoltage, ElectrodeTier,
                    1);
        }
    }
}