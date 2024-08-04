package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.IProgressBarMultiblock;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static gregtech.api.unification.material.Materials.Steam;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing1.TurbineCasingType.AL_TURBINE_CASING;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing1.TurbineCasingType.SA_TURBINE_CASING;

public class MetaTileEntityFixBed extends RecipeMapMultiblockController implements IProgressBarMultiblock {

    public MetaTileEntityFixBed(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.FIX_BED);
        this.recipeMapWorkable = new FixBedLogic(this);
    }
    int updatetime=1;
    boolean work=true;
    public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        work=!work;
        if(!work)return true;
        if(updatetime<=19) updatetime++;
        else updatetime=1;
        playerIn.sendMessage(new TextComponentTranslation("输入效率：%s tick",updatetime));
        return true;
    }
    private class FixBedLogic extends MultiblockRecipeLogic {

        public FixBedLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }
        @Override
        public int getParallelLimit() {
            if(getStatue()) {
                return 4;
            }
            return 1;
        }
        @Override
        public void setMaxProgress(int maxProgress)
        {
            if(getStatue()) {
                maxProgressTime = maxProgress/4;
            }
            else this.maxProgressTime = maxProgress;
        }
        public  boolean getStatue()
        {
            return Hydrogen[0] > 6000 && Hydrogen[1] > 6000 && Hydrogen[2] > 6000;
        }
        protected void updateRecipeProgress() {
            if (canRecipeProgress && drawEnergy(recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);
                if (++progressTime > maxProgressTime)
                {
                    Hydrogen[0]=(int) (Hydrogen[0]*0.72);
                    Hydrogen[1]=(int) (Hydrogen[1]*0.84);
                    Hydrogen[2]=(int) (Hydrogen[2]*0.96);
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
            textList.add(new TextComponentTranslation("gtqtcore.gc.count1", TextFormattingUtil.formatNumbers(SteamAmount)));
        }
        textList.add(new TextComponentTranslation("gtqtcore.msf.count2", (double)Hydrogen[0]/1000,(double)Hydrogen[1]/1000,(double)Hydrogen[2]/1000));
        if(getStatue())  textList.add(new TextComponentTranslation("gtqtcore.msf.good"));
        else textList.add(new TextComponentTranslation("gtqtcore.msf.no"));
    }
    int[] Hydrogen=new int[3];
    FluidStack STEAM = Steam.getFluid(1000*updatetime);
    @Override
    public void update() {
        super.update();
        if (Hydrogen[0] <= 9000) {
            IMultipleTankHandler inputTank = getInputFluidInventory();
            if (STEAM.isFluidStackIdentical(inputTank.drain(STEAM, false))) {
                inputTank.drain(STEAM, true);
                Hydrogen[0] = Hydrogen[0] + 800*updatetime;

            }
        }

        for(int i=0;i<3;i++) {
            if (Hydrogen[0] > Hydrogen[1]) {
                Hydrogen[0] = Hydrogen[0] - 40;
                Hydrogen[1] = Hydrogen[1] + 30;
            }

            if (Hydrogen[0] < Hydrogen[1]) {
                Hydrogen[1] = Hydrogen[1] - 30;
                Hydrogen[0] = Hydrogen[0] + 40;
            }

            if (Hydrogen[1] > Hydrogen[2]) {
                Hydrogen[1] = Hydrogen[1] - 10;
                Hydrogen[2] = Hydrogen[2] + 10;
            }

            if (Hydrogen[1] < Hydrogen[2]) {
                Hydrogen[2] = Hydrogen[2] - 10;
                Hydrogen[1] = Hydrogen[1] + 10;
            }
        }

    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("fluid1", Hydrogen[0]);
        data.setInteger("fluid2", Hydrogen[1]);
        data.setInteger("fluid3", Hydrogen[2]);
        data.setInteger("updatetime",updatetime);
        return super.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        Hydrogen[0] = data.getInteger("fluid1");
        Hydrogen[1] = data.getInteger("fluid2");
        Hydrogen[2] = data.getInteger("fluid3");
        updatetime = data.getInteger("updatetime");
    }
    public  boolean getStatue()
    {
        return Hydrogen[0] > 6000 && Hydrogen[1] > 6000 && Hydrogen[2] > 6000;
    }
    @Override
    public int getNumProgressBars() {
        return 3;
    }

    @Override
    public void addBarHoverText(List<ITextComponent> hoverList, int index) {
        ITextComponent cwutInfo = TextComponentUtil.stringWithColor(
                TextFormatting.AQUA,
                (Hydrogen[index] + 1000)+ " / " + (10000) + " kPa");
        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "gregtech.multiblock.msf.computation",
                cwutInfo));
    }
    @Override
    public double getFillPercentage(int index) {
        return (double) (Hydrogen[index] + 1000) / (10000);
    }



    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityFixBed(metaTileEntityId);
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" A  A ", "      ", "  B   ", " CCIC ", " CCIC ", " CCIC ", " CDCC ")
                .aisle("AA  AA", " A  A ", "AABAAA", "ACECCA", "ACEECA", " CE C ", " CDCC ")
                .aisle(" A  A ", "      ", "  B   ", " C CC ", " C  C ", " C  C ", " CDCC ")
                .aisle(" A  A ", "      ", "  B   ", " CECC ", " CEEC ", " CE C ", " CDCC ")
                .aisle(" A  A ", "      ", "  B   ", " C CC ", " C  C ", " C  C ", " CDCC ")
                .aisle("AA  AA", " A  A ", "AABAAA", "ACECCA", "ACEECA", " CE C ", " CDCM ")
                .aisle(" A  A ", "      ", "  B   ", " C CC ", " C  C ", " C  C ", " CDCC ")
                .aisle(" A  A ", "      ", "  B   ", " CECC ", " CEEC ", " CE C ", " CDCC ")
                .aisle(" A  A ", "      ", "  B   ", " C CC ", " C  C ", " C  C ", " CDCC ")
                .aisle("AA  AA", " A  A ", "AABAAA", "ACECCA", "ACEECA", " CE C ", " CDCC ")
                .aisle(" A  A ", "      ", "  B   ", " CCOC ", " CSOC ", " CCOC ", " CDCC ")

                .where('S', selfPredicate())
                .where('A', states(getFrameState1()))
                .where('B', states(getTurbine2()))
                .where('C', states(getCasingState1())
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2)))
                .where('D', states(getTurbine1()))
                .where('E', states(getFrameState2()))

                .where('I', abilities(MultiblockAbility.IMPORT_FLUIDS))
                .where('O', abilities(MultiblockAbility.EXPORT_FLUIDS))
                .where('M', abilities(MultiblockAbility.MAINTENANCE_HATCH))
                .where(' ', any())
                .build();
    }

    private static IBlockState getFrameState1() {
        return MetaBlocks.FRAMES.get(Materials.StainlessSteel).getBlock(Materials.StainlessSteel);
    }
    private static IBlockState getFrameState2() {
        return MetaBlocks.FRAMES.get(Materials.Aluminium).getBlock(Materials.Aluminium);
    }
    private static IBlockState getCasingState1() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
    }
    private static IBlockState getTurbine1() {
        return GTQTMetaBlocks.TURBINE_CASING1.getState(SA_TURBINE_CASING);
    }
    private static IBlockState getTurbine2() {
        return GTQTMetaBlocks.TURBINE_CASING1.getState(AL_TURBINE_CASING);
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
                return Textures.CLEAN_STAINLESS_STEEL_CASING;
    }
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("不是大号固化机", new Object[0]));
        tooltip.add(I18n.format("gregtech.machine.fb.tooltip.4"));
    }
    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

}