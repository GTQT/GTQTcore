package keqing.gtqtcore.common.metatileentities.multi.generators;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.*;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.BlockInfo;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

import static gregtech.api.util.RelativeDirection.*;
import static java.lang.Math.*;

//动能电池
public class MetaTileEntityKineticEnergyBattery extends MultiblockWithDisplayBase {
    //定义电压
    private int tier;
    //定义外壳
    private int casing;
    //定义线圈
    private int coilLevel;
    private int coilCount;
    private int length;
    private double speed;
    private int eu=0;
    private long inputEu = 0;
    private long outputEu = 0;
    private long euDelta = 0;
    private long runTick = 0;
    private long runDay = 0;
    private long runTime = 0;
    private long runMin = 0;
    private long runS = 0;
    private int timeCheck;
    private boolean isActive=true, isWorkingEnabled = true;
    private final MetaTileEntityKineticEnergyBatteryLogic logic;
    private IEnergyContainer inenergyContainer;
    private IEnergyContainer outenergyContainer;

    //主方块工具提示
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.multiblock.eke.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.multiblock.eke.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.multiblock.eke.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.multiblock.eke.tooltip.4"));
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("把电存起来", new Object[0]));
    }


    //多方块方块摆放和组成
    @Override
    protected BlockPattern createStructurePattern() {
        FactoryBlockPattern pattern = FactoryBlockPattern.start(RIGHT,UP,FRONT)
                .aisle("       ", "  BBB  ", " BBBBB ", " BBSBB ", " BBBBB ", "  BBB  ", "       ")
                .aisle("ABBBBBA", "BC   CB", "B     B", "B  B  B", "B     B", "BC   CB", "ABBBBBA").setRepeatable(1, 10)
                .aisle("       ", "  BBB  ", " BBBBB ", " BBBBB ", " BBBBB ", "  BBB  ", "       ")
                .where('S', selfPredicate())
                .where('A', states(MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel)))
                .where('B', TiredTraceabilityPredicate.CP_CASING.setMinGlobalLimited(40)
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(10).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.OUTPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(10).setPreviewCount(1))
                )
                .where('C',coilPredicate())
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
        switch (this.casing) {
            case (2) -> {
                return Textures.SOLID_STEEL_CASING;
            }
            case (3) -> {
                return Textures.FROST_PROOF_CASING;
            }
            case (4) -> {
                return Textures.CLEAN_STAINLESS_STEEL_CASING;
            }
            case (5) -> {
                return Textures.STABLE_TITANIUM_CASING;
            }
            case (6) -> {
                return Textures.ROBUST_TUNGSTENSTEEL_CASING;
            }
            case (7) -> {
                return GTQTTextures.PD_CASING;
            }
            case (8) -> {
                return GTQTTextures.NQ_CASING;
            }
            case (9) -> {
                return GTQTTextures.ST_CASING;
            }
            case (10) -> {
                return GTQTTextures.AD_CASING;
            }
            default -> {
                return Textures.BRONZE_PLATED_BRICKS;
            }
        }
    }


    //初始化能源仓室

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

    //多方块组成状态获取
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
        Object casing = context.get("ChemicalPlantCasingTiredStats");
        Object coilType = context.get("CoilType");
        this.casing = GTQTUtil.getOrDefault(() -> casing instanceof WrappedIntTired,
                () -> ((WrappedIntTired)casing).getIntTier(),
                0);
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.coilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
        } else {
            this.coilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
        }

        coilCount = context.getInt("Count");

        this.length = coilCount/4;

        this.tier = this.casing;

        this.writeCustomData(GTQTValue.UPDATE_TIER, buf -> buf.writeInt(this.tier));
    }


    //gui数据
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("gtqtcore.eke.count",length,casing,coilLevel));
        textList.add(new TextComponentTranslation("gtqtcore.eke.euMax",logic.euMax(),logic.speedMax()));
        textList.add(new TextComponentTranslation("gtqtcore.eke.eu",eu,(double)(round(speed*100))/100, inputEu, outputEu));
        if (euDelta>0){
            textList.add(new TextComponentTranslation("gtqtcore.eke.output1",logic.powerMax(),euDelta,runDay, runTime, runMin,runS));
        }
        if (euDelta<0){
            textList.add(new TextComponentTranslation("gtqtcore.eke.output2",logic.powerMax(),-euDelta,runDay, runTime, runMin,runS));
        }
        if (euDelta==0){
            textList.add(new TextComponentTranslation("gtqtcore.eke.output3",logic.powerMax(),runDay, runTime, runMin,runS));
        }
    }


    //主方块数据存储
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("Eu", eu);
        data.setInteger("Speed", (int) speed);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        eu = data.getInteger("Eu");
        speed = data.getInteger("Speed");

    }


    //储能上限计算
    protected class MetaTileEntityKineticEnergyBatteryLogic {
        private final MetaTileEntityKineticEnergyBattery metaTileEntity;
        int tier;
        public MetaTileEntityKineticEnergyBatteryLogic(MetaTileEntityKineticEnergyBattery metaTileEntity, int tier) {
            this.metaTileEntity = metaTileEntity;
            this.tier = tier;
        }

        long wkcl = 0;
        int flcl = 8 ;
        long zjcl = 0 ;


        //外壳储能上限计算
        public long euMax() {
            long euMax;
            this.wkcl = (long) (4800000 * casing);
            this.zjcl = (long) (4800000 * 0);
            euMax = ( wkcl + zjcl ) * length;
            return euMax;
        }
        //飞轮转速上限计算
        public long speedMax() {
            long speedMax;
            speedMax = (long) sqrt(euMax() / length);
            return speedMax;
        }
        //飞轮充能功率上限计算
        public long powerMax(){
            long powerMax;
            powerMax = (long) (length * pow(2,coilLevel)*16);
            return powerMax;
        }


    }


    //每t更新内容
    @Override
    protected void updateFormedValid() {
        int store = 0;
        inputEu = 0;
        outputEu = 0;
        euDelta = 0;
        runTick = 0;
        runDay =0;
        runTime = 0;
        runMin = 0;
        runS =0;
        if(this.inenergyContainer!=null && this.inenergyContainer.getEnergyStored()> 0 && logic.euMax() >= eu && eu >=0)
        {
            store = (int) Math.min(this.inenergyContainer.getEnergyStored(),logic.euMax() - eu);
            inputEu = store;
            this.inenergyContainer.changeEnergy(-store);
            eu = (int) (eu + store);
        }

        store=0;
        if(this.outenergyContainer!=null  && eu>0)
        {
            store = (int) Math.min(this.outenergyContainer.getEnergyCapacity() - this.outenergyContainer.getEnergyStored(),eu);
            outputEu = store;
            this.outenergyContainer.changeEnergy(store);
            eu = (int) (eu - store);
        }
        euDelta = inputEu - outputEu;
        if (euDelta>0){
            runTick = (logic.euMax() - eu)/euDelta/20;
        }
        if (euDelta<0){
            runTick = -eu/euDelta/20;
        }
        if (euDelta == 0){
            runTick = eu/logic.powerMax()/20;
        }
        if (runTick !=0){
            runDay = runTick /1440;
            runTime = runTick %1440/360;
            runMin = runTick %1440%360/60;
            runS = runTick %1440%360%60;
        }
        speed = sqrt(eu/length);
        if(eu>logic.euMax()/100){
            timeCheck++;
            if(timeCheck == 12000) {
                timeCheck = 0;
                Random rand = new Random();
                double randomNum = rand.nextInt(10);
                randomNum = randomNum * 0.1;
                speed = speed - speed*0.001*randomNum;
                eu = (int)(round(speed * speed * length));
            }

        }

    }


    public boolean isWorkingEnabled() {
        return this.isWorkingEnabled;
    }


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
        if(dataId == GTQTValue.UPDATE_TIER){
            this.casing = buf.readInt();
        }
        if(dataId == GTQTValue.REQUIRE_DATA_UPDATE){
            this.writeCustomData(GTQTValue.UPDATE_TIER,buf1 -> buf1.writeInt(this.casing));
        }
    }



    //未知用途
    public MetaTileEntityKineticEnergyBattery(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.logic = new MetaTileEntityKineticEnergyBatteryLogic(this, tier);
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityKineticEnergyBattery(metaTileEntityId);
    }



    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.isActive(),
                this.isWorkingEnabled());
    }















}
