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
    boolean work;
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("eu", eu);
        data.setInteger("tier", tier);
        data.setInteger("maxTier", maxTier);
        data.setBoolean("work", work);
        return super.writeToNBT(data);
    }
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        eu = data.getInteger("eu");
        tier = data.getInteger("tier");
        maxTier = data.getInteger("maxTier");
        work = data.getBoolean("work");
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentTranslation("%s %s %s", eu,tier,work));
    }
    @Override
    protected void updateFormedValid() {

        time++;
        if(time==2000)
        {
            PreCheck();
            time=0;
        }

        if (this.inenergyContainer != null && this.inenergyContainer.getEnergyStored() > 0 && eu < tier*2048) {
            eu =eu + Math.max((int) this.inenergyContainer.getEnergyStored(), 0);
            this.inenergyContainer.removeEnergy(this.inenergyContainer.getEnergyStored());
        }
        final int xDir = this.getFrontFacing().getOpposite().getXOffset() * 5;
        final int zDir = this.getFrontFacing().getOpposite().getZOffset() * 5;
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
                            //如果供电等级大于方块等级 则持续给满
                            if(container.getEnergyStored()<container.getEnergyCapacity()&&container.getEnergyCapacity()<Energy&& eu>(container.getEnergyCapacity()-container.getEnergyStored()))
                            {
                                container.addEnergy(container.getEnergyCapacity()-container.getEnergyStored());
                                eu -= (int) (container.getEnergyCapacity()-container.getEnergyStored());
                            }
                            //否则 只供Energy
                            if(container.getEnergyStored()<(container.getEnergyCapacity()-Energy)&& eu>Energy) {
                                container.addEnergy(Energy);
                                eu -= Energy;
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
        //提前检测
        final int xDir = this.getFrontFacing().getOpposite().getXOffset() * 5;
        final int zDir = this.getFrontFacing().getOpposite().getZOffset() * 5;
        for (int i = -4; i <= 4; ++i) {
            for (int j = -4; j <= 4; ++j) {//判断中心区域 外围无需判断
                BlockPos poss = this.getPos().add(xDir + i, 0, zDir + j);
                if(GetTier(poss,i,j)>maxTier)maxTier=GetTier(poss,i,j);
                if (GetTier(poss,i,j) == 3){ GTQTLog.logger.info("我是金块");}
                if (GetTier(poss,i,j) == 0) {tier++;}//如果是框架方块就加缓存
                if (GetTier(poss,i,j) == 11) {
                    work=false;
                }
                else tier+=GetTier(poss,i,j);
                //在这里对非0 11 项目存数组
            }
        }
        work=true;
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
        group.addWidget(new ClickButtonWidget(0, 0, 18, 18, "", this::Refresh)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("刷新"));

        return group;
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
                .where('C', states(getCasingAState()) .or(abilities(MultiblockAbility.INPUT_ENERGY).setMaxLayerLimited(4).setMinGlobalLimited(1)))
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
    //初始化能源仓室

    private void initializeAbilities() {
        this.inenergyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
    }
    private void resetTileAbilities() {
        this.inenergyContainer = new EnergyContainerList(new ArrayList());
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();

        resetTileAbilities();
    }

}
