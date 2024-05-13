package keqing.gtqtcore.common.metatileentities.multi.generators;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMufflerHatch;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.capability.impl.AlgaeFarmLogic;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.IMerchant;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static gregtech.api.unification.material.Materials.Lubricant;
import static gregtech.api.util.RelativeDirection.*;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing.TurbineCasingType.PD_TURBINE_CASING;

//水电站
public class MetaTileEntityWaterPowerStation extends MultiblockWithDisplayBase implements  IProgressBarMultiblock {
    private int coilLevel;
    private int number;
    private int outputEu;
    private int water=0;
    private boolean isWorkingEnabled;
    int tier;
    private final MetaTileEntityWaterPowerStationLogic logic;
    private IEnergyContainer energyContainer;
    protected IMultipleTankHandler inputFluidInventory;
    public MetaTileEntityWaterPowerStation(ResourceLocation metaTileEntityId,int tier) {
        super(metaTileEntityId);
        this.logic = new MetaTileEntityWaterPowerStationLogic(this);
        this.tier = tier;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.OUTPUT_ENERGY));
        this.inputFluidInventory = new FluidTankList(true, this.getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        List<IEnergyContainer> i = getAbilities(MultiblockAbility.OUTPUT_ENERGY);
        this.number=i.size()+4;
        Object coilLevel = context.get("CoilType");
        this.coilLevel = GTQTUtil.getOrDefault(() -> coilLevel instanceof IHeatingCoilBlockStats,
                () ->  ((IHeatingCoilBlockStats) coilLevel).getLevel(),
                BlockWireCoil.CoilType.CUPRONICKEL.getLevel());
        this.water = logic.checkWater();
    }
    public IMultipleTankHandler getInputFluidInventory() {
        return this.inputFluidInventory;
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("======================="));
        textList.add(new TextComponentTranslation("gtqtcore.wps.count", number,coilLevel));
        textList.add(new TextComponentTranslation("gtqtcore.wps.checkwater", water,(number*2+1)*(number*2+1)));
        textList.add(new TextComponentTranslation("gtqtcore.wps.output1", outputEu));
        textList.add(new TextComponentTranslation("gtqtcore.wps.output2", geteu()));
        if (getInputFluidInventory() != null) {
            FluidStack LubricantStack = getInputFluidInventory().drain(Lubricant.getFluid(Integer.MAX_VALUE), false);
            int liquidOxygenAmount = LubricantStack == null ? 0 : LubricantStack.amount;
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.ma.amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
        }
        textList.add(new TextComponentTranslation("======================="));
    }
    @Override
    protected void updateFormedValid() {
        this.logic.update();
        generator();
        this.outputEu = (int) (water*Math.pow(2,tier-1) *coilLevel/32);
    }

    private long geteu()
    {
        Random rand = new Random();
        int randomNum = rand.nextInt(40);
        return (long) (((long) water *coilLevel*(randomNum+80)/800)*Math.pow(2,tier-1)/4);
    }
    private final FluidStack LUBRICANT_STACK = Lubricant.getFluid(tier);
    private void generator() {
        IMultipleTankHandler inputTank = this.getInputFluidInventory();
        isWorkingEnabled=this.energyContainer.getEnergyStored()<this.energyContainer.getEnergyCapacity()&&water > 0;
        if(isWorkingEnabled&&LUBRICANT_STACK.isFluidStackIdentical(inputTank.drain(LUBRICANT_STACK, false)))
        {
            inputTank.drain(LUBRICANT_STACK, true);
            this.energyContainer.addEnergy((long) (geteu()*Math.pow(2,tier-1)));
        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityWaterPowerStation(metaTileEntityId,tier);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        FactoryBlockPattern pattern = FactoryBlockPattern.start(RIGHT, UP, FRONT)
                .aisle("YYY", "YYY", "YYY", " S ")
                .aisle("YYY", "YYY", "YYY", " Y ")
                .aisle("YYY", "YYY", "YYY", " Y ")
                .aisle("FFF", "FCF", "EYE", " E ").setRepeatable(1, 36)
                .aisle("YYY", "YYY", "YYY", " Y ")
                .where('S', selfPredicate())
                .where('Y',states(getCasingAState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1).setPreviewCount(1))
                )
                .where('E',states(getCasingAState())
                        .or(abilities(MultiblockAbility.OUTPUT_ENERGY).setMinLayerLimited(1).setMaxLayerLimited(1))
                )
                .where('C', heatingCoils())
                .where('F', states(getFrameState()))
                .where(' ', any());
        return pattern.build();
    }

    public boolean hasMufflerMechanics() {
        return false;
    }
    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }
    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        if(tier==1)return Textures.FROST_PROOF_CASING;
        if(tier==2)return Textures.STABLE_TITANIUM_CASING;
        return GTQTTextures.PD_CASING;
    }
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.isActive(),
                this.isWorkingEnabled());
    }
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.multiblock.wps.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.multiblock.wps.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.multiblock.wps.tooltip.3"));
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("无中生有！现在！！", new Object[0]));
    }

    @Override
    public int getNumProgressBars() {
        return 1;
    }


    @Override
    public double getFillPercentage(int index) {
        return  ((double)geteu()/(Math.pow(2,tier-1)))/((2*(number+1))*(2*(number+1))*coilLevel*0.156);
    }

    @Override
    public TextureArea getProgressBarTexture(int index) {
        return GuiTextures.PROGRESS_BAR_HPCA_COMPUTATION;
    }

    @Override
    public void addBarHoverText(List<ITextComponent> hoverList, int index) {
            ITextComponent cwutInfo = TextComponentUtil.stringWithColor(
                    TextFormatting.AQUA,
                    geteu()+"* 倍率："+Math.pow(2,tier-1)/4+ " / " + ((2*(number+1))*(2*(number+1))*coilLevel*0.15) + " EU/t");
            hoverList.add(TextComponentUtil.translationWithColor(
                    TextFormatting.GRAY,
                    "gregtech.multiblock.wps.computation",
                    cwutInfo));
    }


    private boolean isWorkingEnabled() {
        return isWorkingEnabled;
    }
    private IBlockState getCasingAState() {
        if(tier==1)return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.ALUMINIUM_FROSTPROOF);
        if(tier==2)return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TITANIUM_STABLE);
        return GTQTMetaBlocks.TURBINE_CASING.getState(PD_TURBINE_CASING);
    }
    private IBlockState getFrameState() {
        if(tier==1)return MetaBlocks.FRAMES.get(Materials.Aluminium).getBlock(Materials.Aluminium);
        if(tier==2)return MetaBlocks.FRAMES.get(Materials.Titanium).getBlock(Materials.Titanium);
        return MetaBlocks.FRAMES.get(Materials.RhodiumPlatedPalladium).getBlock(Materials.RhodiumPlatedPalladium);
    }
    protected class MetaTileEntityWaterPowerStationLogic {
        private final MetaTileEntityWaterPowerStation metaTileEntity;
        public MetaTileEntityWaterPowerStationLogic(MetaTileEntityWaterPowerStation metaTileEntity) {
            this.metaTileEntity = metaTileEntity;
        }
        public int checkWater()
        {
            int waterpos=0;
            int mOffsetX_Lower;
            int mOffsetX_Upper;
            int mOffsetZ_Lower;
            int mOffsetZ_Upper;

            mOffsetX_Lower = -number;
            mOffsetX_Upper = number;
            mOffsetZ_Lower = -number;
            mOffsetZ_Upper = number;

            final int xDir = this.metaTileEntity.getFrontFacing().getOpposite().getXOffset();
            final int zDir = this.metaTileEntity.getFrontFacing().getOpposite().getZOffset();

            for (int i = mOffsetX_Lower + 1; i <= mOffsetX_Upper - 1; ++i) {
                for (int j = mOffsetZ_Lower + 1; j <= mOffsetZ_Upper - 1; ++j) {
                    for (int h = -4; h < 0; h++) {
                        BlockPos waterCheckPos =this.metaTileEntity.getPos().add(xDir + i, h, zDir + j);
                        if(this.metaTileEntity.getWorld().getBlockState(waterCheckPos)== Blocks.WATER.getDefaultState())waterpos++;
                    }
                }
            }
            return waterpos;
        }

        public void update() {
        }
    }
}
