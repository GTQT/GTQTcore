package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockBoilerCasing.BoilerCasingType;
import gregtech.common.blocks.BlockFireboxCasing;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.capability.IElectrode;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTRecipeMapMultiblockControllerOverwrite;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.GTValues.VA;

public class MetaTileEntityAdvancedArcFurnace extends GTQTRecipeMapMultiblockControllerOverwrite {
    int ParallelNum = 1;
    private int ElectrodeTier;

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
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        ParallelLim = (int) Math.pow(2, 5);
        ParallelNum = ParallelLim;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("电极状态：%s 电极等级：%s", checkAvailable(), ElectrodeTier));
        if (modern == 0) textList.add(new TextComponentTranslation("gtqtcore.tire1", 5));
        if (modern == 1) textList.add(new TextComponentTranslation("gtqtcore.tire2", 5));
        textList.add(new TextComponentTranslation("gtqtcore.parr", ParallelNum, ParallelLim));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("modern", modern);
        data.setInteger("ElectrodeTier", ElectrodeTier);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        modern = data.getInteger("modern");
        ElectrodeTier = data.getInteger("ElectrodeTier");
    }

    @Override
    public void update() {
        super.update();
        if (modern == 0) {
            ParallelNum = ParallelNumA;
        }
        if (modern == 1) {
            P = (int) ((this.energyContainer.getEnergyStored() + energyContainer.getInputPerSec()) / (getMinVa() == 0 ? 1 : getMinVa()));
            ParallelNum = Math.min(P, ParallelLim);
        }
    }

    @Override
    public void updateFormedValid() {
        if (!isStructureFormed()) return;
        if (!checkAvailable()) return;
        ElectrodeTier = getElectrodeTier();
    }

    public int getMinVa() {
        if ((Math.min(this.energyContainer.getEnergyCapacity() / 32, VA[5]) * 20) == 0) return 1;
        return (int) (Math.min(this.energyContainer.getEnergyCapacity() / 32, VA[5]));

    }

    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" AAA ", " AAA ", " EEE ", "     ")
                .aisle("AAAAA", "A#C#A", "E#C#E", " ACA ")
                .aisle("CAAAC", "C###C", "C###C", "CAAAC")
                .aisle("AAAAA", "A#C#A", "E#C#E", " ACA ")
                .aisle(" AAA ", " ASA ", " EEE ", "     ")
                .where('S', selfPredicate())
                .where('A', states(MetaBlocks.METAL_CASING.getState(MetalCasingType.TUNGSTENSTEEL_ROBUST)).setMinGlobalLimited(28)
                        .or(autoAbilities()))
                .where('C', abilities(GTQTMultiblockAbility.ELECTRODE_MULTIBLOCK_ABILITY))
                .where('D', states(MetaBlocks.BOILER_CASING.getState((BoilerCasingType.TUNGSTENSTEEL_PIPE))))
                .where('E', states(MetaBlocks.BOILER_FIREBOX_CASING.getState(BlockFireboxCasing.FireboxCasingType.TUNGSTENSTEEL_FIREBOX)))
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
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("升级电极获得并行以及额外超频", new Object[0]));
    }

    public IElectrode getElectrodeHatch(int i) {
        List<IElectrode> abilities = getAbilities(GTQTMultiblockAbility.ELECTRODE_MULTIBLOCK_ABILITY);
        if (abilities.isEmpty())
            return null;
        return abilities.get(i);
    }

    public int getElectrodeTier() {
        int minTier = 1;
        for (int i = 0; i < 14; i++) {
            if (minTier > getElectrodeHatch(i).getElectrodeTier())
                minTier = getElectrodeHatch(i).getElectrodeTier();
        }
        return minTier;
    }

    public boolean checkAvailable() {
        for (int i = 0; i < 14; i++) {
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
            super(tileEntity);
        }

        @Override
        public int getParallelLimit() {
            return ParallelNum;
        }

        @Override
        protected void modifyOverclockPost(int[] resultOverclock, IRecipePropertyStorage storage) {
            super.modifyOverclockPost(resultOverclock, storage);

            int coilTier = ElectrodeTier;
            if (coilTier <= 0)
                return;

            resultOverclock[0] *= 1.0f - coilTier * 0.1; // each coil above cupronickel (coilTier = 0) uses 10% less
            // energy
            resultOverclock[0] = Math.max(1, resultOverclock[0]);
        }
    }
}