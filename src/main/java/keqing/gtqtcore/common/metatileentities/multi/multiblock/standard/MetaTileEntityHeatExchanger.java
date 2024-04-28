package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IMultipleTankHandler;
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
import gregtech.api.pattern.*;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.impl.NoEnergyMultiblockRecipeLogic;
import keqing.gtqtcore.api.metaileentity.multiblock.NoEnergyMultiblockController;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.util.RelativeDirection.*;

public class MetaTileEntityHeatExchanger extends NoEnergyMultiblockController implements IProgressBarMultiblock {
    private int coilLevel;
    private int number;
    private int casingTier;
    private int tubeTier;
    private int glassTier;
    private int tier;
    private int thresholdPercentage = 10;
    double TempA=4000.0;
    double TempB=2000.0;
    private double rate;
    public MetaTileEntityHeatExchanger(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.HEAT_EXCHANGE);
        this.recipeMapWorkable = new AKLogic(this);
    }
    protected class AKLogic extends NoEnergyMultiblockRecipeLogic {
        protected void updateRecipeProgress() {
            if (canRecipeProgress) {
                if(TempA<5000*coilLevel*number)TempA=TempA+50;

                if (++progressTime > maxProgressTime) {
                    completeRecipe();
                }


            }
        }

        public AKLogic(NoEnergyMultiblockController tileEntity) {
            super(tileEntity, tileEntity.recipeMap);
        }


    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityHeatExchanger(metaTileEntityId);
    }
    public int getmax(int coilLevel)
    {
        if(coilLevel==0)return 1;
        return coilLevel;
    }
    public  int getNumber()
    {
        if(number==0)return 1;
        return number;
    }
    FluidStack WATER_STACK = Water.getFluid(thresholdPercentage*getNumber()*2*getmax(coilLevel)*getrate());
    FluidStack DWATER_STACK = DistilledWater.getFluid(thresholdPercentage*getNumber()*getmax(coilLevel)*getrate());
    FluidStack STEAM_STACK = Steam.getFluid(thresholdPercentage*getNumber()*64*getmax(coilLevel)*getrate());

    public int getrate()
    {
        if(TempA-TempB>10000) return 1;
        if(TempA-TempB>8000) return 2;
        if(TempA-TempB>6000) return 3;
        if(TempA-TempB>4000) return 4;
        if(TempA-TempB>2000) return 6;
        return 1;
    }

    public boolean fillTanks(FluidStack stack, boolean simulate) {
        return GTTransferUtils.addFluidsToFluidHandler(getOutputFluidInventory(), simulate, Collections.singletonList(stack));
    }


    @Override
    public void update() {
        super.update();
        IMultipleTankHandler inputTank = getInputFluidInventory();
        rate=TempB/TempA;
        if(TempB>4000)  TempB-=TempB/400;  //自然降温
        if(TempA>10000)  TempB-=TempB/400;
        if(TempA>TempB&&TempA>10000)
        if (WATER_STACK.isFluidStackIdentical(inputTank.drain(WATER_STACK, false))) {
            inputTank.drain(WATER_STACK, true);
            TempB +=(TempA - TempB)*getmax(coilLevel)*tubeTier/ (thresholdPercentage*800.0/number);
            TempA -=(TempA - TempB)*getmax(coilLevel)*tubeTier / (thresholdPercentage*1600.0/number);
            fillTanks(STEAM_STACK,false);
        }

        if(TempA>TempB&&TempA>10000)
            if (DWATER_STACK.isFluidStackIdentical(inputTank.drain(DWATER_STACK, false))) {
                inputTank.drain(DWATER_STACK, true);
                TempB +=(TempA - TempB)*getmax(coilLevel)*tubeTier / (thresholdPercentage*720.0/number);
                TempA -=(TempA - TempB)*getmax(coilLevel)*tubeTier / (thresholdPercentage*1440.0/number);
                fillTanks(STEAM_STACK,false);
            }


    }
    @Override
    public int getNumProgressBars() {
        return 2;
    }
    @Override
    public double getFillPercentage(int index) {
        return index == 0 ?  TempB / TempA :
                TempA / 500000;
    }
    @Override
    public TextureArea getProgressBarTexture(int index) {
        return GuiTextures.PROGRESS_BAR_LCE_FUEL;
    }
    @Override
    public void addBarHoverText(List<ITextComponent> hoverList, int index) {
        ITextComponent cwutInfo;
        if (index == 0) {
            cwutInfo = TextComponentUtil.stringWithColor(
                    TextFormatting.AQUA,
                    TempB + " / " + TempA + " k");
        } else {
            cwutInfo = TextComponentUtil.stringWithColor(
                    TextFormatting.AQUA,
                    TempA + " / " + 5000.0 * 100 + " k");
        }
        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "gregtech.multiblock.hc.computation",
                cwutInfo));
    }
    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 18, "", this::decrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("gtqtcore.multiblock.large_heat_exchanger.threshold_decrement"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 18, "", this::incrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("gtqtcore.multiblock.large_heat_exchanger.threshold_increment"));
        return group;
    }

    private void incrementThreshold(Widget.ClickData clickData) {
        this.thresholdPercentage = MathHelper.clamp(thresholdPercentage + 1, 1, 20);
    }

    private void decrementThreshold(Widget.ClickData clickData) {
        this.thresholdPercentage = MathHelper.clamp(thresholdPercentage - 1, 1, 20);
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);

        textList.add(new TextComponentTranslation("gtqtcore.hc.count1", number, coilLevel));
        textList.add(new TextComponentTranslation("gtqtcore.hc.count2", tier, glassTier));
        textList.add(new TextComponentTranslation("gtqtcore.hc.count3", (int)TempA, (int)TempB));
        textList.add(new TextComponentTranslation("gtqtcore.hc.count4", thresholdPercentage, rate));
    }
    @Override
    protected BlockPattern createStructurePattern() {
        FactoryBlockPattern pattern = FactoryBlockPattern.start(FRONT, UP,RIGHT)
                .aisle(" XXX "," XIX ", " XXX ", " XXX ", " XIX "," XXX ")
                .aisle("XXXXX","XTTTX", "STTTX", "XTTTX", "XTTTX","XXXXX")
                .aisle("XXXXX","GTTTG", "GTTTG", "GTTTG", "GTTTG","XXXXX")
                .aisle("XXXXX","GCTCG", "GCTCG", "GCTCG", "GCTCG","XXXXX").setRepeatable(1, 10)
                .aisle("XXXXX","GTTTG", "GTTTG", "GTTTG", "GTTTG","XXXXX")
                .aisle("XXXXX","XTTTX", "XTTTX", "XTTTX", "XTTTX","XXXXX")
                .aisle(" XHX "," XOX ", " XXX ", " XXX ", " XOX "," XXX ")
                .where('S', selfPredicate())
                .where('H',(abilities(MultiblockAbility.MAINTENANCE_HATCH)))
                .where('O',(abilities(MultiblockAbility.EXPORT_FLUIDS)))
                .where('I',(abilities(MultiblockAbility.IMPORT_FLUIDS)))
                .where('C', coilPredicate())
                .where('X', TiredTraceabilityPredicate.CP_CASING)
                .where('T', TiredTraceabilityPredicate.CP_TUBE)
                .where('G', TiredTraceabilityPredicate.CP_LGLASS)
                .where(' ', any());
        return pattern.build();
    }
    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        switch (this.casingTier) {
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
    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }
    @SideOnly(Side.CLIENT)
    @Override
    public SoundEvent getSound() {
        return GTSoundEvents.ELECTROLYZER;
    }
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
    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == GTQTValue.UPDATE_TIER){
            this.casingTier = buf.readInt();
        }
        if(dataId == GTQTValue.REQUIRE_DATA_UPDATE){
            this.writeCustomData(GTQTValue.UPDATE_TIER,buf1 -> buf1.writeInt(this.casingTier));
        }
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("casingTier", casingTier);
        data.setDouble("TempA", TempA);
        data.setDouble("TempB", TempB);
        data.setInteger("ThresholdPercentage", thresholdPercentage);
        return super.writeToNBT(data);
    }
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        casingTier = data.getInteger("casingTier");
        TempA = data.getDouble("TempA");
        TempB = data.getDouble("TempB");
        thresholdPercentage = data.getInteger("ThresholdPercentage");
    }
    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.casingTier);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.casingTier = buf.readInt();
    }
    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public boolean canVoidRecipeItemOutputs() {
        return true;
    }

    @Override
    public boolean canVoidRecipeFluidOutputs() {
        return true;
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        number = context.getInt("Count")/8;


        Object casingTier = context.get("ChemicalPlantCasingTiredStats");
        Object tubeTier = context.get("ChemicalPlantTubeTiredStats");
        Object glassTier = context.get("LGLTiredStats");
        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.coilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
        } else {
            this.coilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
        }
        this.casingTier = GTQTUtil.getOrDefault(() -> casingTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)casingTier).getIntTier(),
                0);
        this.tubeTier = GTQTUtil.getOrDefault(() -> tubeTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)tubeTier).getIntTier(),
                0);
        this.glassTier = GTQTUtil.getOrDefault(() -> glassTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)glassTier).getIntTier(),
                0);

        this.tier = Math.min(this.casingTier,this.tubeTier);

        this.writeCustomData(GTQTValue.UPDATE_TIER, buf -> buf.writeInt(this.casingTier));



    }
}
