package keqing.gtqtcore.api.metaileentity.multiblock;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.capability.impl.NotifiableFluidTank;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.utils.TooltipHelper;
import keqing.gtqtcore.api.capability.impl.GTQTCoreLogic;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.IV;
import static keqing.gtqtcore.GTQTCoreConfig.MachineSwitch;

public abstract class GTQTMultiblockCore extends MultiMapMultiblockController implements IDataInfoProvider {
    //暴露
    private final RecipeMap<?>[] recipeMaps;
    public int[][] timeHelper = new int[getCoreNum()][3];
    public boolean speed;
    protected IItemHandlerModifiable inputInventory;
    protected IItemHandlerModifiable outputInventory;
    protected IMultipleTankHandler inputFluidInventory;
    protected IMultipleTankHandler outputFluidInventory;
    int target = 0;
    //配方
    //(todo)
    //没加NBT的 dr看我
    List<List<ItemStack>> importItemsList = new ArrayList<>();
    List<List<FluidStack>> importFluidList = new ArrayList<>();
    boolean[] ListWork = new boolean[getCoreNum()];
    int maxEU;
    int circuit;
    int p;
    boolean stop;
    public GTQTMultiblockCore(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMaps) {
        super(metaTileEntityId, recipeMaps);
        this.recipeMapWorkable = new GTQTCoreLogic(this);
        this.recipeMaps = recipeMaps;


        for (int i = 0; i < getCoreNum(); i++) {
            importItemsList.add(new ArrayList<>());
            importFluidList.add(new ArrayList<>());
        }
        if (MachineSwitch.CoreMachineNBTStoreSwitch) {
            this.fluidInventory = new FluidTankList(false, makeFluidTanks(25));
            this.itemInventory = new NotifiableItemStackHandler(this, 25, this, false);
            this.exportFluids = (FluidTankList) fluidInventory;
            this.importFluids = (FluidTankList) fluidInventory;
            this.exportItems = (IItemHandlerModifiable) itemInventory;
            this.importItems = (IItemHandlerModifiable) itemInventory;
        }
    }

    //线程数量
    public int getCoreNum() {
        return 2;
    }

    //最大工作电压 默认能源仓等级
    public int getMinVa() {
        return (int) (this.energyContainer.getEnergyCapacity() / 16 + this.energyContainer.getInputVoltage());
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("circuit", circuit);
        data.setBoolean("speed", speed);
        data.setBoolean("stop", stop);
        data.setInteger("target", target);
        if (MachineSwitch.CoreMachineNBTStoreSwitch) {
            for (int i = 0; i < getCoreNum(); i++) data.setIntArray("timeHelper" + i, timeHelper[i]);
            for (int i = 0; i < getCoreNum(); i++) {
                data.setBoolean("ListWork" + i, ListWork[i]);
            }

            if (!importItemsList.isEmpty()) {
                //总尺寸
                data.setInteger("importItemsListSize", importItemsList.size());
                for (int i = 0; i < importItemsList.size(); i++) {
                    if (!importItemsList.get(i).isEmpty()) {
                        var list = importItemsList.get(i);
                        //单个列表尺寸
                        data.setInteger("importItemsListContentSize" + i, list.size());
                        NBTTagCompound tag = new NBTTagCompound();
                        for (int j = 0; j < list.size(); j++) {
                            NBTTagCompound ctag = new NBTTagCompound();
                            list.get(j).writeToNBT(ctag);
                            tag.setTag("ContentItem" + j, ctag);
                        }
                        //单个列表物品
                        data.setTag("importItemsListContentItem" + i, tag);
                    }
                }
            }
            if (!importFluidList.isEmpty()) {
                //总尺寸
                int TotalSize = importFluidList.size();
                data.setInteger("importFluidListSize", TotalSize);
                for (int i = 0; i < TotalSize; i++) {
                    //单列表
                    if (!importFluidList.get(i).isEmpty()) {
                        var list = importFluidList.get(i);
                        data.setInteger("importFluidListContentSize" + i, list.size());
                        NBTTagCompound tag = new NBTTagCompound();
                        for (int j = 0; j < list.size(); j++) {
                            NBTTagCompound ctag = new NBTTagCompound();
                            list.get(j).writeToNBT(ctag);
                            tag.setTag("ContentFluid" + j, ctag);
                        }
                        //单个列表流体
                        data.setTag("importItemsListContentFluid" + i, tag);
                    }
                }
            }
        }
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        circuit = data.getInteger("circuit");
        speed = data.getBoolean("speed");
        stop = data.getBoolean("stop");
        target = data.getInteger("target");
        if (MachineSwitch.CoreMachineNBTStoreSwitch) {
            for (int i = 0; i < getCoreNum(); i++) timeHelper[i] = data.getIntArray("timeHelper" + i);
            for (int i = 0; i < getCoreNum(); i++) {
                ListWork[i] = data.getBoolean("ListWork" + i);
            }
            //总尺寸>0
            if (data.hasKey("importItemsListSize") && data.getInteger("importItemsListSize") > 0) {
                int totalsize = data.getInteger("importItemsListSize");
                importItemsList = new ArrayList<>();
                for (int i = 0; i < totalsize; i++) {
                    //获取单列列表尺寸
                    List<ItemStack> ls = new ArrayList<>();
                    if (data.hasKey("importItemsListContentSize" + i) && data.getInteger("importItemsListContentSize" + i) > 0) {
                        int singlesize = data.getInteger("importItemsListContentSize" + i);
                        NBTTagCompound tag = data.getCompoundTag("importItemsListContentItem" + i);
                        for (int j = 0; j < singlesize; j++) {
                            ls.add(new ItemStack(tag.getCompoundTag("ContentItem" + j)));
                        }
                    }
                    importItemsList.add(ls);
                }
            }
            //总尺寸>0
            if (data.hasKey("importFluidListSize") && data.getInteger("importFluidListSize") > 0) {
                int totalsize = data.getInteger("importFluidListSize");
                importFluidList = new ArrayList<>();
                for (int i = 0; i < totalsize; i++) {
                    //获取单列列表尺寸
                    List<FluidStack> ls = new ArrayList<>();
                    if (data.hasKey("importFluidListContentSize" + i) && data.getInteger("importFluidListContentSize" + i) > 0) {
                        int singlesize = data.getInteger("importFluidListContentSize" + i);
                        NBTTagCompound tag = data.getCompoundTag("importItemsListContentFluid" + i);
                        for (int j = 0; j < singlesize; j++) {
                            ls.add(FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("ContentFluid" + j)));
                        }
                    }
                    importFluidList.add(ls);
                }
            }
        }
    }

    @Override
    protected void updateFormedValid() {
        if (!this.recipeMapWorkable.isWorkingEnabled()) return;

        for (int i = 0; i < getCoreNum(); i++) {

            if (!ListWork[i]) {
                if (inputInventory != null || inputFluidInventory != null) {
                    Recipe coreRecipe = recipeMaps[target].findRecipe(getMinVa(), inputInventory, inputFluidInventory);
                    if (coreRecipe != null && coreRecipe.matches(false, inputInventory, inputFluidInventory)) {
                        coreRecipe.matches(true, inputInventory, inputFluidInventory);

                        int recipeTier = GTUtility.getTierByVoltage(coreRecipe.getEUt());
                        int machineTier = IV;

                        this.importFluidList.set(i, GTUtility
                                .copyFluidList(coreRecipe.getResultFluidOutputs(recipeTier, machineTier, recipeMaps[target])));

                        this.importItemsList.set(i, GTUtility
                                .copyStackList(coreRecipe.getResultItemOutputs(recipeTier, machineTier, recipeMaps[target])));


                        ListWork[i] = true;

                        //线程置入工作
                        timeHelper[i][1] = coreRecipe.getDuration();
                        timeHelper[i][2] = (int) coreRecipe.getEUt();
                        ListWork[i] = true;
                    }
                }

            }
            if (ListWork[i])
                if (stop || (GTTransferUtils.addFluidsToFluidHandler(outputFluidInventory, true, importFluidList.get(i)) && GTTransferUtils.addItemsToItemHandler(outputInventory, true, importItemsList.get(i)))) {
                    p = Math.min((int) ((this.energyContainer.getEnergyStored() + energyContainer.getInputPerSec()) / (getMinVa() == 0 ? 1 : getMinVa())), 20);

                    if (speed && (energyContainer.getEnergyStored() - (long) timeHelper[i][2] * p > 0)) {
                        energyContainer.removeEnergy((long) timeHelper[i][2] * p * p);
                        timeHelper[i][0] += p;
                    } else {
                        if (energyContainer.getEnergyStored() - timeHelper[i][2] > 0) {
                            energyContainer.removeEnergy(timeHelper[i][2]);
                            timeHelper[i][0]++;
                        }
                    }

                    if (timeHelper[i][0] >= timeHelper[i][1]) {

                        GTTransferUtils.addFluidsToFluidHandler(outputFluidInventory, false, importFluidList.get(i));
                        GTTransferUtils.addItemsToItemHandler(outputInventory, false, importItemsList.get(i));

                        this.importFluidList.set(i, null);
                        this.importItemsList.set(i, null);

                        timeHelper[i][0] = 0;
                        timeHelper[i][1] = 0;
                        timeHelper[i][2] = 0;
                        ListWork[i] = false;
                    }
                }
        }
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentTranslation("线程:%s/超频:%s/配方:%s/销毁:%s", getCoreNum(), speed, recipeMaps[target].getLocalizedName(), stop));
        if (speed) textList.add(new TextComponentTranslation("超频倍数:%s 超频单元耗能:%s EU/t", p, getMinVa()));
        textList.add(new TextComponentTranslation("=================="));
        for (int i = -2 - (speed ? 0 : 1); i <= 3; i++) {

            if (i == 0) {
                textList.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, ">>线程:%s 状态：%s 耗能：%s 耗时 %s/%s", circuit + 1, ListWork[circuit], timeHelper[circuit][2], timeHelper[circuit][0], timeHelper[circuit][1]));
            } else if (circuit + i >= 0 && circuit + i < getCoreNum()) {
                textList.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, ">>线程:%s 状态：%s 耗能：%s 耗时 %s/%s", circuit + i + 1, ListWork[circuit + i], timeHelper[circuit + i][2], timeHelper[circuit + i][0], timeHelper[circuit + i][1]));
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
                .setTooltipText("智能超频"));
        group.addWidget(new ClickButtonWidget(9, 9, 9, 9, "", this::stop)
                .setButtonTexture(GuiTextures.BUTTON_LOCK)
                .setTooltipText("溢出检测"));
        return group;
    }

    private void incrementThreshold(Widget.ClickData clickData) {
        this.circuit = MathHelper.clamp(circuit + 1, 0, getCoreNum() - 1);
    }

    private void decrementThreshold(Widget.ClickData clickData) {
        this.circuit = MathHelper.clamp(circuit - 1, 0, getCoreNum() - 1);
    }

    private void speed(Widget.ClickData clickData) {
        speed = !speed;
    }

    private void stop(Widget.ClickData clickData) {
        stop = !stop;
    }

    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gtqtcore.core.tooltip.1", new Object[0]));
        tooltip.add(TooltipHelper.BLINKING_CYAN + I18n.format("gtqtcore.core.tooltip.2", getCoreNum()));
        tooltip.add(I18n.format("gtqtcore.core.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.core.tooltip.4", getMinVa()));
        tooltip.add(I18n.format("gtqtcore.core.tooltip.5"));
        if (MachineSwitch.CoreMachineNBTStoreSwitch)
            tooltip.add(I18n.format("config中已开启NBT转存，会导致你的控制器在存档重载后消失！"));
        else tooltip.add(I18n.format("config中未开启NBT转存，会清空离线时的运行配方，存档重载后回归初始状态！"));
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
    public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if (target + 1 >= recipeMaps.length) target = 0;
        else target++;
        return super.onScrewdriverClick(playerIn, hand, facing, hitResult);
    }

    public boolean isActive() {
        if (!isStructureFormed()) return false;
        for (int i = 0; i < getCoreNum(); i++)
            if (ListWork[i]) return true;
        return false;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return true;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }
}
