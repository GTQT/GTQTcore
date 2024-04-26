package keqing.gtqtcore.common.metatileentities.multi.generators;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.*;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.IProgressBarMultiblock;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.*;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.*;

import static gregtech.api.util.RelativeDirection.*;
import static java.lang.Math.round;

//动能电池2024.3.14v1.0
public class MetaTileEntityGrayElectricPowerBank extends MultiblockWithDisplayBase implements IWorkable, IControllable, IProgressBarMultiblock {
    //定义电压
    private int tier;
    int mod;
    //定义线圈
    private int coilLevel;
    private int coilCount;
    private int length;
    private double speed;
    private long eu=0;
    private long euplus=0;
    private long inputEu = 0;
    private long outputEu = 0;
    private long euDelta = 0;
    private long runTick = 0;
    private long runDay = 0;
    private long runTime = 0;
    private long runMin = 0;
    private long runS = 0;
    private int timeCheck;

    public int process=0;
    public int maxProcess = 100;
    private boolean isActive=true, isWorkingEnabled = true;
    private final MetaTileEntityKineticEnergyBatteryLogic logic;
    private IEnergyContainer inenergyContainer;
    private IEnergyContainer outenergyContainer;
//    protected ItemHandlerList itemImportInventory;
//    protected ItemHandlerList itemExportInventory;

    //主方块工具提示
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.multiblock.gepb.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.multiblock.gepb.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.multiblock.gepb.tooltip.3"));
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("钱存银行能生钱，电为什么不行", new Object[0]));
    }


    //多方块方块摆放和组成

    @Override
    protected BlockPattern createStructurePattern() {
        FactoryBlockPattern pattern = FactoryBlockPattern.start(RIGHT,FRONT,UP)
                .aisle("AAAAAAAAAAA","AAAAAAAAAAA","AAAAAAAAAAA","AAAAAAAAAAA","AAAAAAAAAAA","AAAAAAAAAAA","AAAAAAAAAAA","AAAAAAAAAAA","AAAAAAAAAAA","AAAAAAAAAAA","AAAAAAAAAAA")
                .aisle("AAAAASAAAAA","ABAABBBAABA","AABBABABBAA","AABBABABBAA","ABAABBBAABA","ABBBBBBBBBA","ABAABBBAABA","AABBABABBAA","AABBABABBAA","ABAABBBAABA","AAAAAAAAAAA")
                .aisle("           "," BBBBBBBBB "," BBBAAABBB "," BBBABABBB "," BAABBBAAB "," BABBBBBAB "," BAABBBAAB "," BBBABABBB "," BBBAAABBB "," BBBBBBBBB ","           ")
                .aisle("           ","           ","  BB   BB  ","  BBABABB  ","   ABBBA   ","   BBBBB   ","   ABBBA   ","  BBABABB  ","  BB   BB  ","           ","           ")
                .aisle("           ","           ","  BB   BB  ","  BB B BB  ","    BBB    ","   BBBBB   ","    BBB    ","  BB B BB  ","  BB   BB  ","           ","           ").setRepeatable(1, 100)
                .aisle("           ","           ","           ","     B     ","    BBB    ","   BBBBB   ","    BBB    ","     B     ","           ","           ","           ")
                .aisle("           ","           ","           ","     B     ","    BBB    ","   BBBBB   ","    BBB    ","     B     ","           ","           ","           ")
                .aisle("           ","           ","           ","           ","    BBB    ","    BBB    ","    BBB    ","           ","           ","           ","           ")
                .aisle("           ","           ","           ","           ","    BBB    ","    BBB    ","    BBB    ","           ","           ","           ","           ")
                .aisle("           ","           ","           ","           ","     B     ","    BBB    ","     B     ","           ","           ","           ","           ")
                .aisle("           ","           ","           ","           ","     B     ","    BBB    ","     B     ","           ","           ","           ","           ")
                .aisle("           ","           ","           ","           ","           ","     B     ","           ","           ","           ","           ","           ")
                .aisle("           ","           ","           ","           ","           ","     B     ","           ","           ","           ","           ","           ")
                .where('S',selfPredicate())
                .where('A',states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setMinGlobalLimited(1).setMaxGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(16).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.OUTPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(16).setPreviewCount(1))
                )
                .where('B',coilPredicate())
                .where(' ',any());

        return pattern.build();


    }


    //线圈检测
    private TraceabilityPredicate coilPredicate() {
        return new TraceabilityPredicate((blockWorldState) -> {
            IBlockState blockState = blockWorldState.getBlockState();
            if (blockState.getBlock() instanceof BlockWireCoil) {
                BlockWireCoil blockWireCoil = (BlockWireCoil) blockState.getBlock();
                BlockWireCoil.CoilType coilType = blockWireCoil.getState(blockState);
                Object currentCoilType = blockWorldState.getMatchContext().getOrPut("CoilType", coilType);
                if (!currentCoilType.toString().equals(coilType.getName())) {
                    blockWorldState.setError(new PatternStringError("gregtech.multiblock.pattern.error.coils"));
                    return false;
                } else {
                    blockWorldState.getMatchContext().increment("Count", 1);
                    blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>()).add(blockWorldState.getPos());
                    return true;
                }
            } else {
                return false;
            }
        }, () -> Arrays.stream(BlockWireCoil.CoilType.values()).map((type) -> new BlockInfo(MetaBlocks.WIRE_COIL.getState(type), null)).toArray(BlockInfo[]::new)).addTooltips("gregtech.multiblock.pattern.error.coils");
    }


    //材质设置
    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }


    //初始化能源仓室

    private void initializeAbilities() {
        this.inenergyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
        this.outenergyContainer =new EnergyContainerList(getAbilities(MultiblockAbility.OUTPUT_ENERGY));
//        this.itemImportInventory = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
//        this.itemExportInventory = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));

    }
    private void resetTileAbilities() {
        this.inenergyContainer = new EnergyContainerList(new ArrayList());
        this.outenergyContainer = new EnergyContainerList(new ArrayList());
//        this.itemImportInventory = new ItemHandlerList(new ArrayList());
//        this.itemExportInventory = new ItemHandlerList(new ArrayList());
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();

        resetTileAbilities();
    }

    //多方块组成状态获取
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.coilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
        } else {
            this.coilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
        }
        coilCount = context.getInt("Count");
        this.length = (coilCount-195)/29;
    }

    //gui数据
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("======================="));
        textList.add(new TextComponentTranslation("gtqtcore.gepb.count",length,coilLevel));
        textList.add(new TextComponentTranslation("gtqtcore.gepb.euMax",eu/10000,logic.euMax()/10000,(double)round(eu*10000/logic.euMax())/100));
        textList.add(new TextComponentTranslation("gtqtcore.gepb.eu1", inputEu, outputEu));
        textList.add(new TextComponentTranslation("gtqtcore.gepb.eu2", euplus));
        if (this.isWorkingEnabled){
            if (euDelta>0){
                textList.add(new TextComponentTranslation("gtqtcore.gepb.output1",euDelta));
                textList.add(new TextComponentTranslation("gtqtcore.gepb.outputA",runDay,runTime,runMin,runS));
            }
            if (euDelta<0){
                textList.add(new TextComponentTranslation("gtqtcore.gepb.output2",-euDelta));
                textList.add(new TextComponentTranslation("gtqtcore.gepb.outputB",runDay,runTime,runMin,runS));
            }
            if (euDelta==0){
                textList.add(new TextComponentTranslation("gtqtcore.gepb.output3"));
            }
        }else {
            textList.add(new TextComponentTranslation("gtqtcore.gepb.output4"));
        }
        textList.add(new TextComponentTranslation("======================="));
    }

    //进度条
    @Override
    public int getNumProgressBars() {
        return 1;
    }

    @Override
    public double getFillPercentage(int index) {
        return ((eu*1.0) / logic.euMax());
    }

    @Override
    public TextureArea getProgressBarTexture(int index) {
        return GuiTextures.PROGRESS_BAR_HPCA_COMPUTATION;
    }

    @Override
    public void addBarHoverText(List<ITextComponent> hoverList, int index) {
            ITextComponent cwutInfo = TextComponentUtil.stringWithColor(
                    TextFormatting.AQUA,
                    (eu/10000)+ " / " +  logic.euMax()/10000 + "万EU");
            hoverList.add(TextComponentUtil.translationWithColor(
                    TextFormatting.GRAY,
                    "gregtech.multiblock.battery.EU",
                    cwutInfo));
        
    }
    //按钮
    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 18, "", this::decrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("gtqtcore.multiblock.battery.threshold_decrement"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 18, "", this::incrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("gtqtcore.multiblock.battery.threshold_increment"));
        return group;
    }

    private void incrementThreshold(Widget.ClickData clickData) {
        this.mod = MathHelper.clamp(mod + 1, 0, 3);
    }

    private void decrementThreshold(Widget.ClickData clickData) {
        this.mod = MathHelper.clamp(mod - 1, 0, 3);
    }

    //主方块数据存储
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setBoolean("isActive", isActive);
        data.setBoolean("isWorkingEnabled", isWorkingEnabled);
        data.setLong("Eu", eu);
        data.setInteger("mod", mod);
        data.setInteger("Speed", (int) speed);
        return  super.writeToNBT(data);
    }
//NBT读写这块需要搬过来  还有
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        isActive = data.getBoolean("isActive");
        isWorkingEnabled = data.getBoolean("isWorkingEnabled");
        eu = data.getLong("Eu");
        mod = data.getInteger("mod");
        speed = data.getInteger("Speed");

    }
    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(isActive);
        buf.writeBoolean(isWorkingEnabled);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        isActive = buf.readBoolean();
        isWorkingEnabled = buf.readBoolean();
    }



    //储能上限计算
    protected class MetaTileEntityKineticEnergyBatteryLogic {
        private final MetaTileEntityGrayElectricPowerBank metaTileEntity;
        public MetaTileEntityKineticEnergyBatteryLogic(MetaTileEntityGrayElectricPowerBank metaTileEntity, int tier) {
            this.metaTileEntity = metaTileEntity;
        }

        //储能上限计算
        public long euMax() {
            long euMax;
            euMax = 10000000L *coilLevel* coilLevel*length;
            return euMax;
        }

    }

    //每t更新内容
    @Override
    protected void updateFormedValid() {
        long store;
        inputEu = 0;
        outputEu = 0;
        euDelta = 0;
        runTick = 0;
        runDay =0;
        runTime = 0;
        runMin = 0;
        runS =0;
        if(!this.getWorld().isRemote && this.isWorkingEnabled)
        {
            if(this.inenergyContainer!=null && this.inenergyContainer.getEnergyStored()> 0 && logic.euMax() >= eu && eu >=0)
            {
                store = Math.min(this.inenergyContainer.getEnergyStored(),logic.euMax() - eu);
                inputEu = store;
                this.inenergyContainer.changeEnergy(-store);
                eu = eu + store;
            }

            if(this.outenergyContainer!=null  && eu>0)
            {
                store = Math.min(this.outenergyContainer.getEnergyCapacity() - this.outenergyContainer.getEnergyStored(),eu);
                outputEu = store;
                this.outenergyContainer.changeEnergy(store);
                eu = eu - store;
            }
            euDelta = inputEu - outputEu;
            if(eu>0){
                timeCheck++;
                euplus = (long) (eu * coilLevel * 0.002);
                if(timeCheck == 1200) {
                    timeCheck = 0;
                    eu = eu + euplus;
                }
            }
            if (euDelta>0){
                runTick = (logic.euMax() - eu)/euDelta/20;
            }
            if (euDelta<0){
                runTick = -eu/euDelta/20;
            }
            if (runTick !=0){
                runDay = runTick /86400;
                runTime = runTick %86400/3600;
                runMin = runTick %86400%3600/60;
                runS = runTick %86400%3600%60;
            }
        }
        euDelta = inputEu - outputEu;

    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public int getProgress() {
        return this.process;
    }
    @Override
    public int getMaxProgress() {
        return this.maxProcess;
    }
    public boolean isWorkingEnabled() {
        return this.isWorkingEnabled;
    }


    //先跑起来看看
    public void setWorkingEnabled(boolean b) {
        this.isWorkingEnabled = b;
        markDirty();
        World world = getWorld();
        if (world != null && !world.isRemote) {
            writeCustomData(GregtechDataCodes.WORKING_ENABLED, buf -> buf.writeBoolean(isWorkingEnabled));
        }
    }
    @Override
    public boolean isActive() {
        return super.isActive() && this.isActive;
    }

    public void setActive(boolean active) {
        if (this.isActive != active) {
            this.isActive = active;
            markDirty();
            World world = getWorld();
            if (world != null && !world.isRemote) {
                writeCustomData(GregtechDataCodes.WORKABLE_ACTIVE, buf -> buf.writeBoolean(active));
            }
        }
    }

    //渲染
    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
    }



    //未知用途
    public MetaTileEntityGrayElectricPowerBank(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.logic = new MetaTileEntityKineticEnergyBatteryLogic(this, tier);
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityGrayElectricPowerBank(metaTileEntityId);
    }



    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.isActive(),
                this.isWorkingEnabled());
    }

}
