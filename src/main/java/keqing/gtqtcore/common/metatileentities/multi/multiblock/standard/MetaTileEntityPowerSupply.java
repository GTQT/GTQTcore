package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityElectricBlastFurnace;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import keqing.gtqtcore.common.metatileentities.multi.generators.MetaTileEntityWaterPowerStation;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeInfo;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gregtech.api.GTValues.VA;
import static gregtech.common.blocks.BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN;
import static keqing.gtqtcore.api.utils.GTQTUniverUtil.getTileEntity;

public class MetaTileEntityPowerSupply extends MultiblockWithDisplayBase  {
    private final Map<BlockPos, EnumFacing> energyHandlers = new HashMap<>();
    int tier=40;//基础电量缓存等级
    int time;
    int eu=0;
    int maxTier=0;
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
        work = data.getBoolean("work");

        charge = data.getBoolean("charge");
        network = data.getBoolean("network");
        fastCharge = data.getBoolean("fastCharge");
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentTranslation("蓄能：%s / %s EU  %s", eu,tier*2048,work));
        textList.add(new TextComponentTranslation("闪充模式：%s 互联模式：%s 供能模式：%s ", fastCharge,network,charge));
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
        if(time==2000)
        {
            PreCheck();
            time=0;
        }
        checkNetwork(poss1,network);
        checkNetwork(poss2,network);
        checkNetwork(poss3,network);
        checkNetwork(poss4,network);
        checkNetwork(poss5,network);
        checkNetwork(poss6,network);
        checkNetwork(poss7,network);
        checkNetwork(poss8,network);

        if(charge) {
            if (this.inenergyContainer != null && this.inenergyContainer.getEnergyStored() > 0 && eu < tier * 2048) {
                eu = eu + Math.max((int) this.inenergyContainer.getEnergyStored(), 0);
                this.inenergyContainer.removeEnergy(this.inenergyContainer.getEnergyStored());
            }
        }
        else {
            if (this.outenergyContainer != null && this.outenergyContainer.getEnergyStored() < this.outenergyContainer.getEnergyCapacity()) {
                if (eu >= (this.outenergyContainer.getEnergyCapacity() - this.outenergyContainer.getEnergyStored())) {
                    eu = (int) (eu - (this.outenergyContainer.getEnergyCapacity() - this.outenergyContainer.getEnergyStored()));
                    this.outenergyContainer.addEnergy(this.outenergyContainer.getEnergyCapacity() - this.outenergyContainer.getEnergyStored());
                }
                else
                {
                    this.outenergyContainer.addEnergy(eu);
                    eu=0;
                }
            }
        }
        if(work) for (int i = -5; i <= 5; ++i) {
            for (int j = -5; j <= 5; ++j) {
                BlockPos poss = this.getPos().add(xDir + i, 0, zDir + j);
                if (GetTier(poss,i,j) !=11&&GetTier(poss,i,j) !=0)//排除不供电区
                    if (GTUtility.getMetaTileEntity(this.getWorld(), poss.add(0,1,0)) instanceof MetaTileEntity) {
                    MetaTileEntity mte = (MetaTileEntity) GTUtility.getMetaTileEntity(this.getWorld(), poss.add(0,1,0));
                    int Energy=VA[GetTier(poss,i,j)];
                    for (EnumFacing facing : EnumFacing.VALUES) {
                        if (mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                            IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);
                            if(charge) {
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
                                    if(eu > (container.getEnergyCapacity() - container.getEnergyStored()))
                                    {
                                        container.addEnergy(container.getEnergyCapacity() - container.getEnergyStored());
                                        eu -= (int) (container.getEnergyCapacity() - container.getEnergyStored());
                                    }
                                    else
                                    {
                                        container.addEnergy(eu);
                                        eu =0;
                                    }
                                }
                            }
                            else//充能模式
                            {
                                if (container.getEnergyStored()>0&& eu < tier * 2048) {
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
        final int xDir = this.getFrontFacing().getOpposite().getXOffset() * 5;
        final int zDir = this.getFrontFacing().getOpposite().getZOffset() * 5;
        for (int i = -4; i <= 4; ++i) {
            for (int j = -4; j <= 4; ++j) {//判断中心区域 外围无需判断
                BlockPos poss = this.getPos().add(xDir + i, 0, zDir + j);
                if(GetTier(poss,i,j)>maxTier)maxTier=GetTier(poss,i,j);
                if (GetTier(poss,i,j) == 0) {tier++;}//如果是框架方块就加缓存
                if (GetTier(poss,i,j) == 11) {
                    work=false;
                }
                else tier+=GetTier(poss,i,j);
                //在这里对非0 11 项目存数组
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
        if(this.getWorld().getBlockState(poss)==Blocks.STONE.getDefaultState())return 0;//框架方块
        if(this.getWorld().getBlockState(poss)==Blocks.DIRT.getDefaultState())return 1;//超导方块1
        if(this.getWorld().getBlockState(poss)==Blocks.COBBLESTONE.getDefaultState())return 2;//超导方块2
        if(this.getWorld().getBlockState(poss)==Blocks.GOLD_BLOCK.getDefaultState())return 3;//超导方块3
        //边框
        if(i==-5||i==5||j==-5||j==5)
        {
            return maxTier;//框架方块
        }
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
        return MetaBlocks.METAL_CASING.getState(STAINLESS_CLEAN);
    }
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.CLEAN_STAINLESS_STEEL_CASING;
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
                /*
                int total = eu + ((MetaTileEntityPowerSupply) mte).getEu();
                if((getMax())>total / 2&&(((MetaTileEntityPowerSupply) mte).getMax())>total / 2) {
                    eu = total / 2;
                    ((MetaTileEntityPowerSupply) mte).setEu(total / 2);
                    total = 0;
                }
                else if((getMax())>total / 2&&(((MetaTileEntityPowerSupply) mte).getMax())<total / 2) {
                    eu =total-(((MetaTileEntityPowerSupply) mte).getMax()-((MetaTileEntityPowerSupply) mte).getEu());
                    ((MetaTileEntityPowerSupply) mte).setEu(((MetaTileEntityPowerSupply) mte).getMax());
                    total = 0;
                }
                else if((getMax())<total / 2&&(((MetaTileEntityPowerSupply) mte).getMax())>total / 2) {
                    ((MetaTileEntityPowerSupply) mte).setEu(total-(getMax()-getEu()));
                    eu=getMax();
                    total = 0;
                }
                else {
                    eu=getMax();
                    ((MetaTileEntityPowerSupply) mte).setEu(((MetaTileEntityPowerSupply) mte).getMax());
                    total = 0;
                }

                 */
                if(eu<((MetaTileEntityPowerSupply) mte).getEu()&&eu<getMax())
                {
                    ((MetaTileEntityPowerSupply) mte).removeEu(VA[maxTier]);
                    eu+=VA[maxTier];
                }
                return true;
            }
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
