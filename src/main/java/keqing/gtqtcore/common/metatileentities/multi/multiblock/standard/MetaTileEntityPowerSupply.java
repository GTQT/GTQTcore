package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.utils.TooltipHelper;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gregtech.api.GTValues.VA;
import static keqing.gtqtcore.common.block.blocks.GTQTPowerSupply.SupplyType.*;

public class  MetaTileEntityPowerSupply extends MultiblockWithDisplayBase  {
    int tier=10;//基础电量缓存等级
    int time;
    int eu=0;
    int maxTier=0;
    int updatetime=1;
    int t=0;
    private IEnergyContainer inenergyContainer;
    private IEnergyContainer outenergyContainer;
    boolean work;
    boolean charge=true;//供能模式
    boolean network;//互联模式
    boolean fastCharge;//闪充模式
    BlockPos poss1;
    BlockPos poss2;
    BlockPos poss3;
    BlockPos poss4;
    BlockPos poss5;
    BlockPos poss6;
    BlockPos poss7;
    BlockPos poss8;
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("eu", eu);
        data.setInteger("tier", tier);
        data.setInteger("maxTier", maxTier);
        data.setInteger("updatetime", updatetime);
        data.setBoolean("work", work);

        data.setBoolean("charge", charge);
        data.setBoolean("network", network);
        data.setBoolean("fastCharge", fastCharge);
        return super.writeToNBT(data);
    }
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        eu = data.getInteger("eu");
        tier = data.getInteger("tier");
        maxTier = data.getInteger("maxTier");
        updatetime = data.getInteger("updatetime");
        work = data.getBoolean("work");

        charge = data.getBoolean("charge");
        network = data.getBoolean("network");
        fastCharge = data.getBoolean("fastCharge");
    }

    public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if(updatetime<=19) updatetime++;
        else updatetime=1;
        playerIn.sendMessage(new TextComponentTranslation("检测频率：%s tick",updatetime));
        return true;
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentTranslation("蓄能：%s/%sEU 状态：%s 检测频率：%s", eu,tier*2048,work,updatetime));
        textList.add(new TextComponentTranslation("闪充：%s 互联：%s 供能模式：%s", fastCharge,network,charge));
        if (isStructureFormed()&&network) {
            if (checkNetwork(poss1, false)) textList.add(new TextComponentTranslation("连接：%s  %s / %s", poss1,getNetEu(poss1),getNetMax(poss1)));
            if (checkNetwork(poss2, false)) textList.add(new TextComponentTranslation("连接：%s  %s / %s", poss2,getNetEu(poss2),getNetMax(poss2)));
            if (checkNetwork(poss3, false)) textList.add(new TextComponentTranslation("连接：%s  %s / %s", poss3,getNetEu(poss3),getNetMax(poss3)));
            if (checkNetwork(poss4, false)) textList.add(new TextComponentTranslation("连接：%s  %s / %s", poss4,getNetEu(poss4),getNetMax(poss4)));
            if (checkNetwork(poss5, false)) textList.add(new TextComponentTranslation("连接：%s  %s / %s", poss5,getNetEu(poss5),getNetMax(poss5)));
            if (checkNetwork(poss6, false)) textList.add(new TextComponentTranslation("连接：%s  %s / %s", poss6,getNetEu(poss6),getNetMax(poss6)));
            if (checkNetwork(poss7, false)) textList.add(new TextComponentTranslation("连接：%s  %s / %s", poss7,getNetEu(poss7),getNetMax(poss7)));
            if (checkNetwork(poss8, false)) textList.add(new TextComponentTranslation("连接：%s  %s / %s", poss8,getNetEu(poss8),getNetMax(poss8)));
        }
    }
    @Override
    protected void updateFormedValid() {

        final int xDir = this.getFrontFacing().getOpposite().getXOffset() * 5;
        final int zDir = this.getFrontFacing().getOpposite().getZOffset() * 5;

        time++;
        if(time==200)
        {
            PreCheck();
            time=0;
        }


        if(updatetime==0)updatetime=1;
        //更新频率
        t++;
        if(t==updatetime)
        {
            t=0;
            checkNetwork(poss1,network);
            checkNetwork(poss2,network);
            checkNetwork(poss3,network);
            checkNetwork(poss4,network);
            checkNetwork(poss5,network);
            checkNetwork(poss6,network);
            checkNetwork(poss7,network);
            checkNetwork(poss8,network);

            if (charge) {
                if (this.inenergyContainer != null && this.inenergyContainer.getEnergyStored() > 0 && eu < tier * 2048) {
                    eu = eu + Math.max((int) this.inenergyContainer.getEnergyStored(), 0);
                    this.inenergyContainer.removeEnergy(this.inenergyContainer.getEnergyStored());
                }
            } else {
                if (this.outenergyContainer != null && this.outenergyContainer.getEnergyStored() < this.outenergyContainer.getEnergyCapacity()) {
                    if (eu >= (this.outenergyContainer.getEnergyCapacity() - this.outenergyContainer.getEnergyStored())) {
                        eu = (int) (eu - (this.outenergyContainer.getEnergyCapacity() - this.outenergyContainer.getEnergyStored()));
                        this.outenergyContainer.addEnergy(this.outenergyContainer.getEnergyCapacity() - this.outenergyContainer.getEnergyStored());
                    } else {
                        this.outenergyContainer.addEnergy(eu);
                        eu = 0;
                    }
                }
            }
            if (work) for (int i = -5; i <= 5; ++i) {
                for (int j = -5; j <= 5; ++j) {
                    BlockPos poss = this.getPos().add(xDir + i, 0, zDir + j);
                    if (GetTier(poss, i, j) != 11 && GetTier(poss, i, j) != 0)//排除不供电区
                        if (GTUtility.getMetaTileEntity(this.getWorld(), poss.add(0, 1, 0)) instanceof MetaTileEntity) {
                            MetaTileEntity mte = (MetaTileEntity) GTUtility.getMetaTileEntity(this.getWorld(), poss.add(0, 1, 0));
                            int Energy = VA[GetTier(poss, i, j)];
                            for (EnumFacing facing : EnumFacing.VALUES) {
                                if (mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                                    IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);
                                    if (charge) {
                                        //如果供电等级大于方块等级 则持续给满
                                        if (!fastCharge) {
                                            if (container.getEnergyStored() < container.getEnergyCapacity() && container.getEnergyCapacity() < Energy && eu > (container.getEnergyCapacity() - container.getEnergyStored())) {
                                                container.addEnergy(container.getEnergyCapacity() - container.getEnergyStored());
                                                eu -= (int) (container.getEnergyCapacity() - container.getEnergyStored());
                                            }
                                            //否则 只供Energy
                                            if (container.getEnergyStored() < (container.getEnergyCapacity() - Energy) && eu > Energy) {
                                                container.addEnergy(Energy);
                                                eu -= Energy;
                                            }
                                        }
                                        //闪充
                                        else if (container.getEnergyStored() < container.getEnergyCapacity()) {
                                            if (eu > (container.getEnergyCapacity() - container.getEnergyStored())) {
                                                container.addEnergy(container.getEnergyCapacity() - container.getEnergyStored());
                                                eu -= (int) (container.getEnergyCapacity() - container.getEnergyStored());
                                            } else {
                                                container.addEnergy(eu);
                                                eu = 0;
                                            }
                                        }
                                    } else//充能模式
                                    {
                                        if (container.getEnergyStored() > 0 && eu < tier * 2048) {
                                            eu += container.getEnergyStored();
                                            container.removeEnergy(container.getEnergyStored());
                                        }
                                    }
                                }
                            }
                        }
                }
            }
        }
    }
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("谁还需要地板", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.5"));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.6"));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.7"));
    }
    @Override
    public boolean isMultiblockPartWeatherResistant(@Nonnull IMultiblockPart part) {
        return true;
    }
    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (!work) {
            textList.add(new TextComponentTranslation("结构错误"));
        }
    }
    public void PreCheck()
    {
        maxTier=0;
        work=true;
        //提前检测
        tier=10;//送你的
        final int xDir = this.getFrontFacing().getOpposite().getXOffset() * 5;
        final int zDir = this.getFrontFacing().getOpposite().getZOffset() * 5;
        for (int i = -4; i <= 4; ++i) {
            for (int j = -4; j <= 4; ++j) {//判断中心区域 外围无需判断
                BlockPos poss = this.getPos().add(xDir + i, 0, zDir + j);
                if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_I)){tier+=20;}
                if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_II)){tier+=80;}
                if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_III)){tier+=320;}
                if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_IV)){tier+=1280;}
                if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_V)){tier+=5120;}
                if(GetTier(poss,i,j)>maxTier&&GetTier(poss,i,j)!=11)maxTier=GetTier(poss,i,j);
                if (GetTier(poss,i,j) == 11) {
                    work=false;
                }
            }
        }

        poss1 = this.getPos().add( + 11, 0,  + 11);
        poss2 = this.getPos().add( + 11, 0,0 );
        poss3 = this.getPos().add( + 11, 0,  - 11);

        poss4 = this.getPos().add(0,0,  + 11);
        poss5 = this.getPos().add(0, 0,  - 11);

        poss6 = this.getPos().add( - 11, 0,  + 11);
        poss7 = this.getPos().add( - 11, 0,0 );
        poss8 = this.getPos().add( - 11, 0,  - 11);
    }

    public int GetTier(BlockPos poss,int i,int j)
    {
        if(i==-5||i==5||j==-5||j==5)
        {
            return maxTier;//框架方块
        }
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BASIC))return 0;//框架方块
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_I))return 0;//框架方块
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_II))return 0;//框架方块
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_III))return 0;//框架方块
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_IV))return 0;//框架方块
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_V))return 0;//框架方块
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_I))return 1;//超导方块1
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_II))return 2;//超导方块2
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_III))return 3;//超导方块3
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_IV))return 4;//超导方块4
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_V))return 5;//超导方块5
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_VI))return 6;//超导方块6
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_VII))return 7;//超导方块7
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_VIII))return 8;//超导方块8
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_IVV))return 9;//超导方块9
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_VV))return 10;//超导方块10

        return 11;//不认识就开摆
    }
    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 9, "", this::Refresh)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("刷新"));

        group.addWidget(new ClickButtonWidget(9, 0, 9, 9, "", this::FastCharge)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("闪充模式"));

        group.addWidget(new ClickButtonWidget(0, 9, 9, 9, "", this::NetWork)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("互联模式"));

        group.addWidget(new ClickButtonWidget(9, 9, 9, 9, "", this::Mode)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("模式切换"));

        return group;
    }
    private void FastCharge(Widget.ClickData clickData) {
        fastCharge=!fastCharge;
    }
    private void NetWork(Widget.ClickData clickData) {
        network=!network;
    }
    private void Mode(Widget.ClickData clickData) {
        charge=!charge;
    }
    private void Refresh(Widget.ClickData clickData) {
        tier=0;
        PreCheck();
    }

    public MetaTileEntityPowerSupply(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPowerSupply(metaTileEntityId);
    }
    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCCCCCCCC")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("CCCCCSCCCCC")
                .where('S', selfPredicate())
                .where(' ', any())
                .where('C', states(getCasingAState())
                        .or(abilities(MultiblockAbility.OUTPUT_ENERGY).setMaxLayerLimited(4).setMinGlobalLimited(0))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMaxLayerLimited(4).setMinGlobalLimited(0)))
                .build();
    }
    private IBlockState getCasingAState() {
        return GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BASIC);
    }
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.IRIDIUM_CASING;
    }
    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }
    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }
    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.isActive(),
                true);
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        PreCheck();//成型的时候刷新一遍区块
        initializeAbilities();
    }
    public boolean checkNetwork(BlockPos poss,boolean work) {
        MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), poss);
        if (mte instanceof MetaTileEntityPowerSupply) {
            if (work && ((MetaTileEntityPowerSupply) mte).isNetwork()&& ((MetaTileEntityPowerSupply) mte).isWork()) {
                if(eu<((MetaTileEntityPowerSupply) mte).getEu()&&eu<getMax()&&eu>=0&&((MetaTileEntityPowerSupply) mte).getEu()>=0)
                {
                    if(((MetaTileEntityPowerSupply) mte).getEu()>getMax()*2)
                    {
                        ((MetaTileEntityPowerSupply) mte).removeEu(getMax()-eu);
                        eu=getMax();
                    }
                    ((MetaTileEntityPowerSupply) mte).removeEu(VA[maxTier]);
                    eu += VA[maxTier];

                }
            }
            return true;
        }
        return false;
    }
    public int getNetMax(BlockPos poss)
    {
        MetaTileEntity mte =GTUtility.getMetaTileEntity(this.getWorld(),poss);
        if (mte instanceof MetaTileEntityPowerSupply) {
            if (work && ((MetaTileEntityPowerSupply) mte).isNetwork())
            {
                return ((MetaTileEntityPowerSupply) mte).getMax();
            }

        }
        return 0;
    }
    public int getNetEu(BlockPos poss)
    {
        MetaTileEntity mte =GTUtility.getMetaTileEntity(this.getWorld(),poss);
        if (mte instanceof MetaTileEntityPowerSupply) {
            if (work && ((MetaTileEntityPowerSupply) mte).isNetwork())
            {
                return ((MetaTileEntityPowerSupply) mte).getEu();
            }
        }
        return 0;
    }
    //初始化能源仓室
    public int getEu()
    {
        return eu;
    }
    public void removeEu(int remove)
    {
        if(eu>remove)eu-=remove;
        else eu=0;
    }
    public void setEu(int set)
    {
        if(set<tier*2048)eu=set;
    }
    public int getMax()
    {
        return tier*2048;
    }
    public boolean isNetwork()
    {
        return network;
    }
    public boolean isWork()
    {
        return work;
    }
    private void initializeAbilities() {
        this.inenergyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
        this.outenergyContainer =new EnergyContainerList(getAbilities(MultiblockAbility.OUTPUT_ENERGY));

    }
    private void resetTileAbilities() {
        this.inenergyContainer = new EnergyContainerList(new ArrayList());
        this.outenergyContainer = new EnergyContainerList(new ArrayList());
    }
    @Override
    public void invalidateStructure() {
        super.invalidateStructure();

        resetTileAbilities();
    }

}
