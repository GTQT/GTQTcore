package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockBoilerCasing.BoilerCasingType;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityFluidHatch;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.Steam;

public class MetaTileEntityDistillationKettle extends RecipeMapMultiblockController {
    public MetaTileEntityDistillationKettle(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.DISTILLATION_KETTLE);
        this.recipeMapWorkable = new MFSWorkableHandler(this);
    }
    int steam;
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("steam", steam);
        return super.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        steam = data.getInteger("steam");
    }
    FluidStack STEAM = Steam.getFluid(1000);
    @Override
    public void update() {
        super.update();
        if (steam <= 10000) {
            IMultipleTankHandler inputTank = getInputFluidInventory();
            if (STEAM.isFluidStackIdentical(inputTank.drain(STEAM, false))) {
                inputTank.drain(STEAM, true);
                steam = steam + 500;

            }
        }
    }
    private class MFSWorkableHandler extends MultiblockRecipeLogic {
        public MFSWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }
        @Override
        public int getParallelLimit() {
            if(getStatue()) {
                return 2;
            }
            return 1;
        }

        @Override
        public void setMaxProgress(int maxProgress)
        {
            if(getStatue()) {
                maxProgressTime = (int) (maxProgress *0.8);
            }
            else  maxProgressTime = maxProgress*2;

        }
        public  boolean getStatue()
        {
            return steam>8000;
        }
        protected void updateRecipeProgress() {
            if (canRecipeProgress && drawEnergy(recipeEUt, true)){
                this.drawEnergy(this.recipeEUt, false);
                if (++progressTime > maxProgressTime)
                {
                    steam=(int) (steam*0.64);
                    completeRecipe();
                }
            }
        }
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (getInputFluidInventory() != null) {
            FluidStack SteamStack = getInputFluidInventory().drain(Steam.getFluid(Integer.MAX_VALUE), false);
            int SteamAmount = SteamStack == null ? 0 : SteamStack.amount;
            textList.add(new TextComponentTranslation("蒸汽缓存：%s",TextFormattingUtil.formatNumbers(SteamAmount)));
        }
        textList.add(new TextComponentTranslation("已装填蒸汽缓存：%s / 10000", steam));

    }
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityDistillationKettle(this.metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" F F ", " F F ", " CCC ", " COC ", " COC ", " COC ", " COC ", " COC ", " COC ", " FCF ")
                .aisle("F   F", "F   F", "CCCCC", "CPPPC", "C   C", "CPPPC", "C   C", "CPPPC", "CCCCC", "FCCCF")
                .aisle("     ", "     ", "CCCCC", "CPPPC", "C E C", "CPEPC", "C E C", "CPPPC", "CCECC", "CCCCC")
                .aisle("F   F", "F   F", "CCCCC", "CPPPC", "C   C", "CPPPC", "C   C", "CPPPC", "CCCCC", "FCCCF")
                .aisle(" F F ", " F F ", " CCC ", " CSC ", " CCC ", " CCC ", " CCC ", " CCC ", " CCC ", " FCF ")
                .where('S', selfPredicate())
                .where('C', states(MetaBlocks.METAL_CASING.getState(MetalCasingType.STEEL_SOLID)).setMinGlobalLimited(96)
                        .or(autoAbilities(true, true, true, true, true, false, false)))
                .where('F', states(MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel)))
                .where('P', states(MetaBlocks.BOILER_CASING.getState(BoilerCasingType.STEEL_PIPE)))
                .where('E', states(MetaBlocks.METAL_CASING.getState(MetalCasingType.STEEL_SOLID)))
                .where('O', states((MetaBlocks.METAL_CASING.getState(MetalCasingType.STEEL_SOLID)))
                        .or(metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.EXPORT_FLUIDS).stream()
                                .filter(mte->(mte instanceof MetaTileEntityFluidHatch))
                                .toArray(MetaTileEntity[]::new))))
                .where(' ', any())
                .build();
    }
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("谁家LV做石化", new Object[0]));
        tooltip.add(I18n.format("gtqt.machine.distillation_kettle.1"));
        tooltip.add(I18n.format("输入蒸汽来为机器预热！每轮配方会消耗百分之六十四的蒸汽缓存"));
        tooltip.add(I18n.format("当蒸汽缓存大于8000时享有二倍并行，配方耗时减半"));
    }


    protected ICubeRenderer getFrontOverlay() {
        return Textures.BLAST_FURNACE_OVERLAY;
    }
}
