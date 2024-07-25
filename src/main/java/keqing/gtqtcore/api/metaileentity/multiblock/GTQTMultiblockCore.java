package keqing.gtqtcore.api.metaileentity.multiblock;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.*;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.common.items.MetaItems;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.MetaTileEntityStewStoolStove;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBaseWithControl;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.UV;
import static gregtech.api.GTValues.VA;

public abstract class GTQTMultiblockCore extends MetaTileEntityBaseWithControl {
    public long currentEU = 0;
    public long currentRecipeEU = 0;

    protected IItemHandlerModifiable inputInventory;
    protected IItemHandlerModifiable outputInventory;
    protected IMultipleTankHandler inputFluidInventory;
    protected IMultipleTankHandler outputFluidInventory;



    //暴露

    //线程数量
    public int getCoreNum() {
        return 2;
    }
    //配方
    public RecipeMap<SimpleRecipeBuilder> getCORE_RECIPES()
    {
        return RecipeMaps.FURNACE_RECIPES;
    }
    //最大工作电压 默认能源仓等级
    public int getMinVa()
    {
        if((Math.min(this.energyContainer.getEnergyCapacity()/32,maxEU)*20)==0)return 1;
        return (int)(Math.min(this.energyContainer.getEnergyCapacity()/32,maxEU));
    }




    IItemHandlerModifiable []importItemsList=new IItemHandlerModifiable[getCoreNum()];
    IMultipleTankHandler[]importFluidList=new IMultipleTankHandler[getCoreNum()];
    boolean []ListWork=new boolean[getCoreNum()];
    public int [][]timeHelper=new int[getCoreNum()][3];
    int maxEU;
    int circuit;
    int p;
    boolean speed;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("circuit", circuit);
        data.setBoolean("speed", speed);
        return super.writeToNBT(data);
    }
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        circuit = data.getInteger("circuit");
        speed = data.getBoolean("speed");
    }


    @Override
    protected void updateFormedValid() {
        maxEU= (int) (this.energyContainer.getEnergyCapacity()/64);
        for (int i = 0; i < getCoreNum(); i++)
        {
            if(!ListWork[i]) {
                if (inputInventory != null||inputFluidInventory != null) {
                    Recipe coreRecipe= getCORE_RECIPES().findRecipe(VA[UV], inputInventory,inputFluidInventory);
                    if (coreRecipe != null && coreRecipe.matches(false, inputInventory,inputFluidInventory)) {
                        this.importItemsList[i]=inputInventory;
                        this.importFluidList[i]=inputFluidInventory;
                        ListWork[i]=true;
                        coreRecipe.matches(true, inputInventory,inputFluidInventory);
                        //线程置入工作
                        timeHelper[i][1]=coreRecipe.getDuration();
                        timeHelper[i][2]=coreRecipe.getEUt();

                        ListWork[i]=true;
                    }
                }
            }
            if(ListWork[i]) {
                p = (int) ((this.energyContainer.getEnergyStored() + energyContainer.getInputPerSec()) / (getMinVa() == 0 ? 1 : getMinVa()));
                if (speed && (energyContainer.getEnergyStored() - (long) timeHelper[i][2] * p > 0)) {
                    energyContainer.removeEnergy((long) timeHelper[i][2] * p);
                    timeHelper[i][0] += p;
                } else {
                    if (energyContainer.getEnergyStored() - timeHelper[i][2] >= 0) {
                        energyContainer.removeEnergy(timeHelper[i][2]);
                        timeHelper[i][0]++;
                    }
                }
                if (timeHelper[i][0] > timeHelper[i][1]) {
                    Recipe chemicalReactorRecipe1 = getCORE_RECIPES().findRecipe(VA[UV], this.importItemsList[i], this.importFluidList[i]);
                    if (chemicalReactorRecipe1 != null) {
                        GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false, chemicalReactorRecipe1.getFluidOutputs());
                        GTTransferUtils.addItemsToItemHandler(this.outputInventory, false, chemicalReactorRecipe1.getOutputs());
                    }
                    timeHelper[i][0] = 0;
                    timeHelper[i][1] = 0;
                    ListWork[i] = false;
                }
            }
        }
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentTranslation("总线程:%s 智能超频:%s",getCoreNum(),speed));
        if(speed) textList.add(new TextComponentTranslation("超频倍数:%s 超频单元耗能:%s EU/t",p,getMinVa()));
        textList.add(new TextComponentTranslation("=================="));
        for (int i = -2-(speed?0:1); i <= 3; i++) {

            if (i == 0) {

                {
                    textList.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD,">>线程:%s 状态：%s 耗能：%s 耗时 %s/%s",circuit+1,ListWork[circuit],timeHelper[circuit][2],timeHelper[circuit][0],timeHelper[circuit][1]));
                }
            } else if (circuit + i >= 0 && circuit + i < getCoreNum()) {
                {
                    textList.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY,">>线程:%s 状态：%s 耗能：%s 耗时 %s/%s",circuit+i+1,ListWork[circuit+i],timeHelper[circuit+i][2],timeHelper[circuit+i][0],timeHelper[circuit+i][1]));
                }
            }
        }


    }
    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 9, "", this::decrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("序列向后"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 9, "", this::incrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("序列向前"));
        group.addWidget(new ClickButtonWidget(0, 9, 9, 9, "", this::speed)
                .setButtonTexture(GuiTextures.BUTTON_CLEAR_GRID)
                .setTooltipText("超频"));
        group.addWidget(new ClickButtonWidget(9, 9, 9, 9, "", this::stop)
                .setButtonTexture(GuiTextures.BUTTON_LOCK)
                .setTooltipText("紧急停机"));
        return group;
    }

    private void incrementThreshold(Widget.ClickData clickData) {
        this.circuit = MathHelper.clamp(circuit + 1, 0, getCoreNum());
    }

    private void decrementThreshold(Widget.ClickData clickData) {
        this.circuit = MathHelper.clamp(circuit - 1, 0, getCoreNum());
    }

    private void speed(Widget.ClickData clickData) {
        speed=!speed;
    }

    private void stop(Widget.ClickData clickData) {
        for (int i = 0; i < getCoreNum(); i++)ListWork[i]=false;
    }
    public GTQTMultiblockCore(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.fluidInventory = new FluidTankList(false, makeFluidTanks(25));
        this.itemInventory = new NotifiableItemStackHandler(this, 25, this, false);
        this.exportFluids = (FluidTankList) fluidInventory;
        this.importFluids = (FluidTankList) fluidInventory;
        this.exportItems = (IItemHandlerModifiable) itemInventory;
        this.importItems = (IItemHandlerModifiable) itemInventory;

    }
    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.inputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.inputFluidInventory = new FluidTankList(false, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.outputFluidInventory = new FluidTankList(false, getAbilities(MultiblockAbility.EXPORT_FLUIDS));
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    @Override
    public TraceabilityPredicate autoAbilities() {
        TraceabilityPredicate predicate = super.autoAbilities();
        return predicate.or(abilities(MultiblockAbility.IMPORT_ITEMS))
                .or(abilities(MultiblockAbility.IMPORT_FLUIDS))
                .or(abilities(MultiblockAbility.EXPORT_ITEMS))
                .or(abilities(MultiblockAbility.EXPORT_FLUIDS));
    }

    private List<FluidTank> makeFluidTanks(int length) {
        List<FluidTank> fluidTankList = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            fluidTankList.add(new NotifiableFluidTank(32000, this, false));
        }
        return fluidTankList;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), true,
                true);
    }
}
