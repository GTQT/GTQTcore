package keqing.gtqtcore.api.metaileentity.multiblock;

import gregicality.multiblocks.api.capability.IParallelMultiblock;
import gregicality.multiblocks.api.capability.impl.GCYMMultiblockRecipeLogic;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public abstract class GTQTRecipeMapMultiblockControllerOverwrite extends MultiMapMultiblockController implements IParallelMultiblock {

    public GTQTRecipeMapMultiblockControllerOverwrite(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        this(metaTileEntityId, new RecipeMap[]{recipeMap});
    }

    public GTQTRecipeMapMultiblockControllerOverwrite(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMaps) {
        super(metaTileEntityId, recipeMaps);
        this.recipeMapWorkable = new GCYMMultiblockRecipeLogic(this);
    }
    public int ParallelLim;
    public int ParallelNumA;
    public int modern;
    public int P;
    int tier;



    /*
    int ParallelNum=1;
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("modern", modern);
        return super.writeToNBT(data);
    }
    @Override
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
        if((Math.min(this.energyContainer.getEnergyCapacity()/32,VA[tier])*20)==0)return 1;
        return (int)(Math.min(this.energyContainer.getEnergyCapacity()/32,VA[tier]));

    }
    需要添加的

    @Override
        public int getParallelLimit() {
            return ParallelNum;
        }
        配方逻辑写这个

         ParallelLim=(int)Math.pow(2, tier);
        ParallelNum=ParallelLim;
        成型写这个


         if(modern==0) textList.add(new TextComponentTranslation("gtqtcore.tire1",tier));
        if(modern==1) textList.add(new TextComponentTranslation("gtqtcore.tire2",tier));
        textList.add(new TextComponentTranslation("gtqtcore.parr",ParallelNum,ParallelLim));

       替换文本
     */
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);

    }
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 9, "", this::decrementThrottle)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("gregtech.multiblock.parr.throttle_decrement"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 9, "", this::incrementThrottle)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("gregtech.multiblock.parr.throttle_increment"));

        group.addWidget(new ClickButtonWidget(0, 9, 9, 9, "", this::decrementThrottle1)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("gregtech.multiblock.hand.throttle_decrement"));
        group.addWidget(new ClickButtonWidget(9, 9, 9, 9, "", this::incrementThrottle1)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("gregtech.multiblock.hand.throttle_increment"));
        return group;
    }
    private void incrementThrottle(Widget.ClickData clickData) {
        if(ParallelLim<16) this.ParallelNumA = MathHelper.clamp(ParallelNumA +1, 1, ParallelLim);
        this.ParallelNumA = MathHelper.clamp(ParallelNumA + ParallelLim/16, 1, ParallelLim);
    }

    private void decrementThrottle(Widget.ClickData clickData) {
        if(ParallelLim<16) this.ParallelNumA = MathHelper.clamp(ParallelNumA -1, 1, ParallelLim);
        this.ParallelNumA = MathHelper.clamp(ParallelNumA - ParallelLim/16, 1, ParallelLim);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("ParallelNumA", ParallelNumA);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        ParallelNumA = data.getInteger("ParallelNumA");
    }
    private void incrementThrottle1(Widget.ClickData clickData) {
        this.modern = MathHelper.clamp(modern + 1, 0, 1);
    }

    private void decrementThrottle1(Widget.ClickData clickData) {
        this.modern = MathHelper.clamp(modern - 1, 0, 1);
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));

    }
    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("gtqt.tooltip.update")};
    }

}
