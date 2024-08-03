package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.GTValues;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.logic.OverclockingLogic;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockWireCoil.CoilType;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metaileentity.GTQTRecipeMapMultiblockController;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTADVBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;

import static gregtech.api.GTValues.HV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;
import static java.lang.Math.max;

public class MetaTileEntityElectricArcFurnace extends GTQTRecipeMapMultiblockController implements IHeatingCoil {
    private int eleTier;
    private int tubeTier;
    private int blastFurnaceTemperature;

    private int simulateTemp; //模拟炉温

    //模拟元素
    private int Fe;
    private int Ni;
    private int Mn;
    private int Cr;
    private int V;
    private int W;
    //private int Mo;
    private boolean isOutput=false;
    private boolean isInput=false;
    private int runTime = 0;
    private int maxTime = 0;
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("simulateTemp", simulateTemp);
        data.setInteger("Fe", Fe);
        data.setInteger("Ni", Ni);
        data.setInteger("Mn", Mn);
        data.setInteger("Cr", Cr);
        return super.writeToNBT(data);
    }
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        simulateTemp = data.getInteger("simulateTemp");
        Fe = data.getInteger("Fe");
        Ni = data.getInteger("Ni");
        Mn = data.getInteger("Mn");
        Cr = data.getInteger("Cr");
    }
    public MetaTileEntityElectricArcFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[] {
                GTQTcoreRecipeMaps.BLAST_ARC_RECIPES,
                RecipeMaps.BLAST_RECIPES,
                RecipeMaps.ARC_FURNACE_RECIPES
        });
        this.recipeMapWorkable = new ElectricArcFurnaceLogic(this);
    }
    private boolean ArcBlast() {
        return this.getRecipeMap() == GTQTcoreRecipeMaps.BLAST_ARC_RECIPES;
    }
    private boolean Blast() {
        return this.getRecipeMap() == RecipeMaps.BLAST_RECIPES;
    }
    private boolean Arc() {
        return this.getRecipeMap() == RecipeMaps.ARC_FURNACE_RECIPES;
    }
    public boolean fillTanks(FluidStack stack, boolean simulate) {
        return GTTransferUtils.addFluidsToFluidHandler(getOutputFluidInventory(), simulate, Collections.singletonList(stack));
    }
    int ParallelNum=1;
    int ParallelLim;
    int P;

    public int getMinVa()
    {
        if((Math.min(this.energyContainer.getEnergyCapacity()/32,VA[HV])*20)==0)return 1;
        return (int)(Math.min(this.energyContainer.getEnergyCapacity()/32,VA[HV]));

    }

    @Override
    public void update() {
        super.update();
        IMultipleTankHandler inputTank = getInputFluidInventory();

        ParallelLim = (int) Math.pow(2, eleTier);

        P = (int) ((this.energyContainer.getEnergyStored() + energyContainer.getInputPerSec())/(getMinVa()==0?1:getMinVa()));
        ParallelNum = Math.min(P, ParallelLim);

        if(ArcBlast())
        {
            if (isActive()&& maxTime ==2000)if(simulateTemp>=0&&simulateTemp<blastFurnaceTemperature)simulateTemp+=max((blastFurnaceTemperature-simulateTemp)*0.001*eleTier,1);
            if(isActive()&&isInput)
            {
                //元素配比
                FluidStack FeF = Iron.getFluid(144);//Fe
                if(Fe<16*tubeTier) {
                    if (FeF.isFluidStackIdentical(inputTank.drain(FeF, false))) {
                        inputTank.drain(FeF, true);
                        Fe = Fe + 1;
                        if(simulateTemp>=110)simulateTemp-=10;
                    }
                }

                FluidStack NiF = Nickel.getFluid(144);//Ni
                if(Ni<16*tubeTier) {
                    if (NiF.isFluidStackIdentical(inputTank.drain(NiF, false))) {
                        inputTank.drain(NiF, true);
                        Ni = Ni + 1;
                        if(simulateTemp>=110)simulateTemp-=10;
                    }
                }

                FluidStack MnF = Manganese.getFluid(144);//Mn
                if(Mn<16*tubeTier) {
                    if (MnF.isFluidStackIdentical(inputTank.drain(MnF, false))) {
                        inputTank.drain(MnF, true);
                        Mn = Mn + 1;
                        if(simulateTemp>=110)simulateTemp-=10;
                    }
                }

                FluidStack CrF = Chrome.getFluid(144);//Cr
                if(Cr<16*tubeTier) {
                    if (CrF.isFluidStackIdentical(inputTank.drain(CrF, false))) {
                        inputTank.drain(CrF, true);
                        Cr = Cr + 1;
                        if(simulateTemp>=110)simulateTemp-=10;
                    }
                }
                FluidStack VF = Vanadium.getFluid(144);//V
                if(V<16*tubeTier) {
                    if (VF.isFluidStackIdentical(inputTank.drain(VF, false))) {
                        inputTank.drain(VF, true);
                        V = V + 1;
                        if(simulateTemp>=110)simulateTemp-=10;
                    }
                }
                FluidStack WF = Tungsten.getFluid(144);//W
                if(W<16*tubeTier) {
                    if (WF.isFluidStackIdentical(inputTank.drain(WF, false))) {
                        inputTank.drain(WF, true);
                        W = W + 1;
                        if(simulateTemp>=110)simulateTemp-=10;
                    }
                }
                /*
                FluidStack MoF = Molybdenum.getFluid(144);//Mo
                if(Mo<16*tubeTier) {
                    if (MoF.isFluidStackIdentical(inputTank.drain(MoF, false))) {
                        inputTank.drain(MoF, true);
                        Mo = Mo + 1;
                        simulateTemp-=10;
                    }
                }

                 */
            }
            if(simulateTemp>=100&&!isActive())//模拟降温
            {
                simulateTemp-=(double)simulateTemp/4000.0*tubeTier;
            }




            if(isOutput) {
                //模拟冶炼 这里按照优先度 从上往下写
                //钨钢
                FluidStack TUNGSTENSTEEL = TungstenSteel.getFluid(144 * 2 * simulateTemp / getCurrentTemperature());
                if (simulateTemp > 4500) {
                    if (Fe >= 1 && W >= 1) {
                        Fe -= 1;
                        W -= 1;
                        fillTanks(TUNGSTENSTEEL, false);
                    }
                }
                //不锈钢
                FluidStack STAINLESSSTEEL = StainlessSteel.getFluid(144 * 9 * simulateTemp / getCurrentTemperature());
                if (simulateTemp > 2000) {
                    if (Fe >= 6 && Ni >= 1 && Mn >= 1 && Cr >= 1) {
                        Fe -= 6;
                        Ni -= 1;
                        Mn -= 1;
                        Cr -= 1;
                        fillTanks(STAINLESSSTEEL, false);
                    }
                }
                //殷钢
                FluidStack INVARSTEEL = Invar.getFluid(144 * 3 * simulateTemp / getCurrentTemperature());
                if (simulateTemp > 1600) {
                    if (Fe >= 2 && Ni >= 1) {
                        Fe -= 2;
                        Ni -= 1;
                        fillTanks(INVARSTEEL, false);
                    }
                }
                //钒钢
                FluidStack VANADIUMSTEEL = VanadiumSteel.getFluid(144 * 9 * simulateTemp / getCurrentTemperature());
                if (simulateTemp > 1400) {
                    if (Fe >= 7 && V >= 1 && Cr>=1) {
                        Fe -= 7;
                        V -= 1;
                        Cr -= 1;
                        fillTanks(VANADIUMSTEEL, false);
                    }
                }
                //钢
                FluidStack STEEL = Steel.getFluid(144 * simulateTemp / getCurrentTemperature());
                if (simulateTemp > 1000) {
                    if (Fe >= 1) {
                        Fe -= 1;
                        fillTanks(STEEL, false);
                    }
                }
                if(Fe<=1&&ArcBlast()&& isOutput) {
                    FluidStack STEEL16 = Steel.getFluid(144*16);
                    fillTanks(STEEL16, false);
                    isOutput = false;
                }
            }



        }


    }

    protected class ElectricArcFurnaceLogic extends MultiblockRecipeLogic {

        public ElectricArcFurnaceLogic(RecipeMapMultiblockController metaTileEntity) {
            super(metaTileEntity);
            if (!(metaTileEntity instanceof IHeatingCoil)) {
                throw new IllegalArgumentException("MetaTileEntity must be instanceof IHeatingCoil");
            }
        }
        public long getMaxVoltage() {
            if(Arc())return VA[HV];
            return super.getMaxVoltage();
        }
        @Override
        public int getParallelLimit() {
            if(ArcBlast())return 1;
            return ParallelNum;
        }
        public boolean output()
        {
            return isOutput;
        }

        protected void modifyOverclockPre( int[] values,  IRecipePropertyStorage storage) {
            super.modifyOverclockPre(values, storage);
            values[0] = OverclockingLogic.applyCoilEUtDiscount(values[0], ((IHeatingCoil)this.metaTileEntity).getCurrentTemperature(), (Integer)storage.getRecipePropertyValue(TemperatureProperty.getInstance(), 0));
        }

        protected  int[] runOverclockingLogic( IRecipePropertyStorage propertyStorage, int recipeEUt, long maxVoltage, int duration, int amountOC) {
            return OverclockingLogic.heatingCoilOverclockingLogic(Math.abs(recipeEUt), maxVoltage, duration, amountOC, ((IHeatingCoil)this.metaTileEntity).getCurrentTemperature(), (Integer)propertyStorage.getRecipePropertyValue(TemperatureProperty.getInstance(), 0));
        }

        @Override
        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);
                if(ArcBlast()) {
                    if (++this.progressTime > this.maxProgressTime) {
                        this.completeRecipe();
                        simulateTemp = simulateTemp * 11 / 12;
                        isOutput = false;
                    }
                    else{
                        runTime =this.progressTime;
                        maxTime =this.maxProgressTime;
                        if (runTime <1800&& maxTime ==2000)isInput=true;
                        if (runTime ==1800&& maxTime ==2000) {
                            isInput = false;
                            isOutput = true;
                        }
                    }
                }
                else
                if (++this.progressTime > this.maxProgressTime) {
                    this.completeRecipe();
                }
            }
        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityElectricArcFurnace(metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addCustom(tl -> {
                    // Coil heat capacity line
                    if (isStructureFormed()) {
                        ITextComponent heatString = TextComponentUtil.stringWithColor(
                                TextFormatting.RED,
                                TextFormattingUtil.formatNumbers(blastFurnaceTemperature) + "K");

                        tl.add(TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gregtech.multiblock.blast_furnace.max_temperature",
                                heatString));
                    }
                })
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgressPercent());
        textList.add(new TextComponentTranslation("gtqtcore.machine.arc1", eleTier,tubeTier));
        if(!ArcBlast())textList.add(new TextComponentTranslation("gtqtcore.parr",ParallelNum,ParallelLim));
        if(ArcBlast())
        {
            textList.add(new TextComponentTranslation("gtqtcore.machine.arc2",simulateTemp));
            textList.add(new TextComponentTranslation("gtqtcore.machine.arc3",Fe,Ni,Mn,Cr,V,W));


        }
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.blastFurnaceTemperature = 0;
    }

    @Override
    public boolean checkRecipe( Recipe recipe, boolean consumeIfSuccess) {
        return this.blastFurnaceTemperature >= recipe.getProperty(TemperatureProperty.getInstance(), 0);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("  AAAAAAAA", "    ABBBA ", "   AABBBAA", "    ABBBA ", "    AAAA  ", "          ", "          ")
                .aisle("AAAAAAAAAA", "  AAA   AA", "  AAA   AA", "  AAA   AA", "   AAAAAA ", "          ", "          ")
                .aisle("AAAAAAAAAA", " ACB D D B", " ACB D D B", " ACB D D B", "  CAADADAA", "  CCCD D  ", "     D D  ")
                .aisle("AAAAAAAAAA", " ACB     B", " MCB     B", " ACB     B", "  CAAAAAAA", "  CCC     ", "          ")
                .aisle("AAAAAAAAAA", " ACB D D B", " SCB D D B", " ACB D D B", "  CAADADAA", "  CCCD D  ", "     D D  ")
                .aisle("AAAAAAAAAA", "  AAA   AA", "  AAA   AA", "  AAA   AA", "   AAAAAA ", "          ", "          ")
                .aisle("  AAAAAAAA", "    ABBBA ", "   AABBBAA", "    ABBBA ", "    AAAA  ", "          ", "          ")
                .where('S', selfPredicate())
                .where('A', states(getCasingState()).setMinGlobalLimited(140)
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2).setPreviewCount(2))).where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('B', heatingCoils())
                .where('D', TiredTraceabilityPredicate.CP_ELE_CASING)
                .where('C', TiredTraceabilityPredicate.CP_TUBE)
                .where('#', air())
                .build();
    }
    protected IBlockState getCasingState() {
        return GTQTMetaBlocks.ADV_BLOCK.getState(GTQTADVBlock.CasingType.Talonite);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.TALONITE;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        if (type instanceof IHeatingCoilBlockStats) {
            this.blastFurnaceTemperature = ((IHeatingCoilBlockStats) type).getCoilTemperature();
        } else {
            this.blastFurnaceTemperature = CoilType.CUPRONICKEL.getCoilTemperature();
        }
        // the subtracted tier gives the starting level (exclusive) of the +100K heat bonus
        this.blastFurnaceTemperature += 100 *
                max(0, GTUtility.getFloorTierByVoltage(getEnergyContainer().getInputVoltage()) - GTValues.MV);

        Object eleTier = context.get("EleTiredStats");
        Object tubeTier = context.get("ChemicalPlantTubeTiredStats");
        this.eleTier = GTQTUtil.getOrDefault(() -> eleTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)eleTier).getIntTier(),
                0);

        this.tubeTier = GTQTUtil.getOrDefault(() -> tubeTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)tubeTier).getIntTier(),
                0);
    }
    @Override
    public void addInformation(ItemStack stack,  World world,  List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.3"));
        tooltip.add(I18n.format("gregtech.machine.cracker.gtqtupdate.1"));
        tooltip.add(I18n.format("gregtech.machine.cracker.gtqtupdate.2"));
        tooltip.add(I18n.format("gregtech.machine.arc.1"));
        tooltip.add(I18n.format("gregtech.machine.arc.2"));
    }
    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("gtqtcore.multiblock.arc.description")};
    }
    @Override
    public int getCurrentTemperature() {
        return this.blastFurnaceTemperature;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.BLAST_FURNACE_OVERLAY;
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
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = super.getDataInfo();
        list.add(new TextComponentTranslation("gregtech.multiblock.blast_furnace.max_temperature",
                new TextComponentTranslation(TextFormattingUtil.formatNumbers(blastFurnaceTemperature) + "K")
                        .setStyle(new Style().setColor(TextFormatting.RED))));
        return list;
    }
}