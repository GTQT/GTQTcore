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
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTRecipeMapMultiblockControllerOverwrite;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.utils.GTQTUtil;
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
    public MetaTileEntityAdvancedArcFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[] {
                RecipeMaps.ARC_FURNACE_RECIPES,
                RecipeMaps.FURNACE_RECIPES
        });
        this.recipeMapWorkable = new AdvancedArcFurnaceWorkableHandler(this);
    }
    @Override
    public boolean canBeDistinct() {return true;}
    private class AdvancedArcFurnaceWorkableHandler extends MultiblockRecipeLogic {
        @Override
        public int getParallelLimit() {
            return ParallelNum;
        }
        @Override
        protected void modifyOverclockPost(int[] resultOverclock,  IRecipePropertyStorage storage) {
            super.modifyOverclockPost(resultOverclock, storage);

            int coilTier = eleTier;
            if (coilTier <= 0)
                return;

            resultOverclock[0] *= 1.0f - coilTier * 0.1; // each coil above cupronickel (coilTier = 0) uses 10% less
            // energy
            resultOverclock[0] = Math.max(1, resultOverclock[0]);
        }
        public AdvancedArcFurnaceWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }
    }
    private int eleTier;
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAdvancedArcFurnace(this.metaTileEntityId);
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object eleTier = context.get("EleTieredStats");
        this.eleTier = GTQTUtil.getOrDefault(() -> eleTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)eleTier).getIntTier(),
                0);
        ParallelLim=(int)Math.pow(2, 5);
        ParallelNum=ParallelLim;
    }
    int ParallelNum=1;
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("电极:%s",eleTier));
        if(modern==0) textList.add(new TextComponentTranslation("gtqtcore.tire1",5));
        if(modern==1) textList.add(new TextComponentTranslation("gtqtcore.tire2",5));
        textList.add(new TextComponentTranslation("gtqtcore.parr",ParallelNum,ParallelLim));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("modern", modern);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        modern = data.getInteger("modern");
    }

    @Override
    public void update() {
        super.update();
        if (modern == 0)
        {
            ParallelNum=ParallelNumA;
        }
        if (modern == 1)
        {
            P = (int) ((this.energyContainer.getEnergyStored() + energyContainer.getInputPerSec())/(getMinVa()==0?1:getMinVa()));
            ParallelNum = Math.min(P, ParallelLim);
        }
    }
    public int getMinVa()
    {
        if((Math.min(this.energyContainer.getEnergyCapacity()/32,VA[5])*20)==0)return 1;
        return (int)(Math.min(this.energyContainer.getEnergyCapacity()/32,VA[5]));

    }
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" AAA ", " AAA ", " EEE ", "     ")
                .aisle("AAAAA", "A#C#A", "E#C#E", " ACA ")
                .aisle("CAAAC", "C###C", "C###C", "CAAAC")
                .aisle("AAAAA", "A###A", "E###E", " AAA ")
                .aisle(" AAA ", " ASA ", " EEE ", "     ")
                .where('S', selfPredicate())
                .where('A', states(MetaBlocks.METAL_CASING.getState(MetalCasingType.TUNGSTENSTEEL_ROBUST)).setMinGlobalLimited(28)
                        .or(autoAbilities()))
                .where('C', TiredTraceabilityPredicate.CP_ELE_CASING.get())
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

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }
}