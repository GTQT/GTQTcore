package keqing.gtqtcore.api.metaileentity.multiblock;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregicality.multiblocks.api.capability.impl.GCYMMultiblockRecipeLogic;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.*;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBaseWithControl;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.UV;
import static gregtech.api.GTValues.VA;

public abstract class GTQTMultiblockCore extends MultiMapMultiblockController implements IDataInfoProvider{
    protected IItemHandlerModifiable inputInventory;
    protected IItemHandlerModifiable outputInventory;
    protected IMultipleTankHandler inputFluidInventory;
    protected IMultipleTankHandler outputFluidInventory;
    int target = 0;
    //暴露
    private final RecipeMap<?>[] recipeMaps;
    public GTQTMultiblockCore(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMaps) {
        super(metaTileEntityId,recipeMaps);
        this.recipeMapWorkable = new GCYMMultiblockRecipeLogic(this);
        this.recipeMaps = recipeMaps;

        for (int i = 0; i < getCoreNum(); i++) {
            importItemsList.add(new ArrayList<>());
            importFluidList.add(new ArrayList<>());
        }
        this.fluidInventory = new FluidTankList(false, makeFluidTanks(25));
        this.itemInventory = new NotifiableItemStackHandler(this, 25, this, false);
        this.exportFluids = (FluidTankList) fluidInventory;
        this.importFluids = (FluidTankList) fluidInventory;
        this.exportItems = (IItemHandlerModifiable) itemInventory;
        this.importItems = (IItemHandlerModifiable) itemInventory;
    }
    //线程数量
    public int getCoreNum() {
        return 2;
    }
    //配方

    //最大工作电压 默认能源仓等级
    public int getMinVa()
    {
        if((Math.min(this.energyContainer.getEnergyCapacity()/32,maxEU)*20)==0)return 1;
        return (int)(Math.min(this.energyContainer.getEnergyCapacity()/32,maxEU));
    }


    //(todo)
    //没加NBT的 dr看我
    List<List<ItemStack>> importItemsList = new ArrayList<>();
    List<List<FluidStack>> importFluidList = new ArrayList<>();
    boolean []ListWork=new boolean[getCoreNum()];
    public int [][]timeHelper=new int[getCoreNum()][3];


    int maxEU;
    int circuit;
    int p;
    public boolean speed;
    boolean stop;
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("circuit", circuit);
        data.setBoolean("speed", speed);
        data.setBoolean("stop", stop);
        data.setInteger("target",target);

        /*
        writeItemHandlerToNBT(data,this.inputInventory,"In");
        writeItemHandlerToNBT(data,this.outputInventory,"Out");
        for (int i = 0; i <inputFluidInventory.getTanks(); i++) {
            data.setTag("FluidIn"+i,inputFluidInventory.getFluidTanks().get(i).trySerialize());
        }
        for (int i = 0; i <outputFluidInventory.getTanks(); i++) {
            data.setTag("FluidOut"+i,outputFluidInventory.getFluidTanks().get(i).trySerialize());
        }

         */
        return super.writeToNBT(data);
    }
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        circuit = data.getInteger("circuit");
        speed = data.getBoolean("speed");
        stop = data.getBoolean("stop");
        target = data.getInteger("target");

        /*
        readItemHandlerFromNBT(data,this.inputInventory,"In");
        readItemHandlerFromNBT(data,this.outputInventory,"Out");
        for (int i = 0; i <inputFluidInventory.getTanks(); i++) {
            NBTTagCompound tag = data.getCompoundTag("FluidIn"+i);
            if(tag!=null)
                inputFluidInventory.getFluidTanks().get(i).tryDeserialize(tag);
        }
        for (int i = 0; i <outputFluidInventory.getTanks(); i++) {
            NBTTagCompound tag = data.getCompoundTag("FluidOut"+i);
            if(tag!=null)
                outputFluidInventory.getFluidTanks().get(i).tryDeserialize(tag);
        }

         */
    }
    public static NBTTagCompound writeItemHandlerToNBT(NBTTagCompound compound, IItemHandlerModifiable handler,String in_out) {
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setInteger("Slot"+in_out, i);
                stack.writeToNBT(itemTag);
                nbtTagList.appendTag(itemTag);
            }
        }
        compound.setTag(in_out+"Items", nbtTagList);
        return compound;
    }
    public static void readItemHandlerFromNBT(NBTTagCompound compound, IItemHandlerModifiable handler,String in_out) {
        NBTTagList nbtTagList = compound.getTagList(in_out + "Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < nbtTagList.tagCount(); i++) {
            NBTTagCompound itemTag = nbtTagList.getCompoundTagAt(i);
            int slot = itemTag.getInteger("Slot" + in_out);
            if (slot >= 0 && slot < handler.getSlots()) {
                handler.setStackInSlot(slot, new ItemStack(itemTag));
            }
        }
    }
    @Override
    protected void updateFormedValid() {
        maxEU= (int) (this.energyContainer.getEnergyCapacity()/64);

        for (int i = 0; i < getCoreNum(); i++)
        {

            if(!ListWork[i]) {
                if (inputInventory != null||inputFluidInventory != null) {
                    Recipe coreRecipe = recipeMaps[target].findRecipe(getMinVa(), inputInventory, inputFluidInventory);
                    if (coreRecipe != null && coreRecipe.matches(false, inputInventory, inputFluidInventory)) {
                        this.importFluidList.set(i, coreRecipe.getFluidOutputs());
                        this.importItemsList.set(i, coreRecipe.getOutputs());
                        ListWork[i] = true;
                        coreRecipe.matches(true, inputInventory, inputFluidInventory);
                        //线程置入工作
                        timeHelper[i][1] = coreRecipe.getDuration();
                        timeHelper[i][2] = coreRecipe.getEUt();
                        ListWork[i] = true;
                    }
                }

            }
            if(ListWork[i]) if(stop||GTTransferUtils.addFluidsToFluidHandler(outputFluidInventory, true, importFluidList.get(i))&&GTTransferUtils.addItemsToItemHandler(outputInventory, true, importItemsList.get(i)))
            {
                p =Math.min( (int) ((this.energyContainer.getEnergyStored() + energyContainer.getInputPerSec()) / (getMinVa() == 0 ? 1 : getMinVa()))   ,20);
                if (speed && (energyContainer.getEnergyStored() - (long) timeHelper[i][2] * p > 0)) {
                    energyContainer.removeEnergy((long) timeHelper[i][2] * p*p);
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
        textList.add(new TextComponentTranslation("总线程:%s 超频:%s 配方:%s 溢出:%s",getCoreNum(),speed,recipeMaps[target].getLocalizedName(),stop));
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
                .setTooltipText("智能超频"));
        group.addWidget(new ClickButtonWidget(9, 9, 9, 9, "", this::stop)
                .setButtonTexture(GuiTextures.BUTTON_LOCK)
                .setTooltipText("溢出检测"));
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
        stop=!stop;
    }

    public void addInformation(ItemStack stack,  World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("工作模式：独立线程", new Object[0]));
        tooltip.add(I18n.format(">>核心线程数量:%s", getCoreNum()));
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

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }
    boolean work=true;
    @Override
    public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        work=!work;
        if(work)
        {
            if(target+1>=recipeMaps.length) target = 0;
            else target++;
        }
        return super.onScrewdriverClick(playerIn,hand,facing,hitResult);
    }
}
