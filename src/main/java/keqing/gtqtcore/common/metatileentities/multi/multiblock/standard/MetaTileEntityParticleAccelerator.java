package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.GTQTCapabilities;
import keqing.gtqtcore.api.capability.chemical_plant.ChemicalPlantProperties;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.PAPartProperty;
import keqing.gtqtcore.api.recipes.properties.PCBPartProperty;
import keqing.gtqtcore.api.utils.GTQTUniverUtil;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static gregtech.api.GTValues.EV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.Lava;

public class MetaTileEntityParticleAccelerator extends RecipeMapMultiblockController {
    private EnergyContainerList inputEnergyContainers;
    private int coilLevel;   //加速线圈等级
    private int casingTier;  //加速器等级
    private int tubeTier;    //探针等级
    private int voltageTier;
    private int tier;

    private float speed;       //速度
    private int angle;       //角度

    private int Mode;        //加速 稳定 减速模式


    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 9, "", this::decrementThresholdS)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("gtqtcore.multiblock.pam.threshold_decrement"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 9, "", this::incrementThresholdS)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("gtqtcore.multiblock.pam.threshold_increment"));

        group.addWidget(new ClickButtonWidget(0, 9, 9, 9, "", this::decrementThresholdA)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("gtqtcore.multiblock.paa.threshold_decrement"));
        group.addWidget(new ClickButtonWidget(9, 9, 9, 9, "", this::incrementThresholdA)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("gtqtcore.multiblock.paa.threshold_increment"));
        return group;
    }

    private void incrementThresholdS(Widget.ClickData clickData) {
        this.Mode = MathHelper.clamp(Mode + 1, 1, 3);
    }

    private void decrementThresholdS(Widget.ClickData clickData) {
         this.Mode = MathHelper.clamp(Mode - 1, 1, 3);
    }

    private void incrementThresholdA(Widget.ClickData clickData) {
        this.angle = MathHelper.clamp(angle + 1, 0, 8);
    }

    private void decrementThresholdA(Widget.ClickData clickData) {
         this.angle = MathHelper.clamp(angle - 1, 0, 8);
    }

    public MetaTileEntityParticleAccelerator(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES);
        this.recipeMapWorkable = new ChemicalPlantLogic(this);
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("EEEEEEE", "C#####C", "C#####C", "C#####C", "C#####C", "C#####C", "CCCCCCC")
                .aisle("EMMMMME", "#MMMMM#", "#######", "#######", "#######", "#MMMMM#", "CCCCCCC")
                .aisle("EMMMMME", "#MXXXM#", "##TTT##", "##XXX##", "##TTT##", "#MXXXM#", "CCCCCCC")
                .aisle("EMMMMME", "#MXAXM#", "##TAT##", "##XAX##", "##TAT##", "#MXAXM#", "CCCCCCC")
                .aisle("EMMMMME", "#MXXXM#", "##TTT##", "##XXX##", "##TTT##", "#MXXXM#", "CCCCCCC")
                .aisle("EMMMMME", "#MMMMM#", "#######", "#######", "#######", "#MMMMM#", "CCCCCCC")
                .aisle("EEESEEE", "C#####C", "C#####C", "C#####C", "C#####C", "C#####C", "CCCCCCC")
                .where('S', selfPredicate())
                .where('E', TiredTraceabilityPredicate.CP_CASING
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(GTQTCapabilities.CATALYST).setMaxGlobalLimited(2).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2).setPreviewCount(1)))
                .where('C',TiredTraceabilityPredicate.CP_CASING)
                .where('X', heatingCoils())
                .where('M', TiredTraceabilityPredicate.MACHINE_CASINGS)
                .where('T', TiredTraceabilityPredicate.CP_TUBE)
                .where('#', any())
                .where('A', air())
                .build();
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.CHEMICAL_PLANT_OVERLAY;
    }

    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.recipeMapWorkable.isActive(), this.recipeMapWorkable.isWorkingEnabled());
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
    protected void addDisplayText(List<ITextComponent> textList) {


        if(Mode==1) textList.add(new TextComponentTranslation("gtqtcore.pa.mode1",angle,speed/100000));
        if(Mode==2) textList.add(new TextComponentTranslation("gtqtcore.pa.mode2",angle,speed/100000));
        if(Mode==3) textList.add(new TextComponentTranslation("gtqtcore.pa.mode3",angle,speed/100000));


        textList.add(new TextComponentTranslation("gtqtcore.pa.agp1"));
        if(angle==1)textList.add(new TextComponentTranslation("gtqtcore.pa.ag1"));
        else {
            if (angle == 2) textList.add(new TextComponentTranslation("gtqtcore.pa.ag2"));
                else
                {
                    if (angle == 8) textList.add(new TextComponentTranslation("gtqtcore.pa.ag8"));
                    else textList.add(new TextComponentTranslation("gtqtcore.pa.agp2"));
                }
            }

        if(angle==0)textList.add(new TextComponentTranslation("gtqtcore.pa.ag0"));
        else {
            if (angle == 3) textList.add(new TextComponentTranslation("gtqtcore.pa.ag3"));
            else {if (angle == 7) textList.add(new TextComponentTranslation("gtqtcore.pa.ag7"));
            else textList.add(new TextComponentTranslation("gtqtcore.pa.agp3"));
        }}

        if(angle==4)textList.add(new TextComponentTranslation("gtqtcore.pa.ag4"));
        else {
            if (angle == 5) textList.add(new TextComponentTranslation("gtqtcore.pa.ag5"));
            else
            {if (angle == 6) textList.add(new TextComponentTranslation("gtqtcore.pa.ag6"));
            else textList.add(new TextComponentTranslation("gtqtcore.pa.agp4"));
        }}
        textList.add(new TextComponentTranslation("gtqtcore.pa.agp1"));

        textList.add(new TextComponentTranslation("gtqtcore.pa.level", coilLevel,casingTier,tubeTier));

        if (getInputFluidInventory() != null) {
            FluidStack STACK = getInputFluidInventory().drain(Lava.getFluid(Integer.MAX_VALUE), false);
            int liquidOxygenAmount = STACK == null ? 0 : STACK.amount;
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.pa.amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityParticleAccelerator(metaTileEntityId);
    }
    @Override
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
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object coilType = context.get("CoilType");
        Object casingTier = context.get("ChemicalPlantCasingTiredStats");
        Object tubeTier = context.get("ChemicalPlantTubeTiredStats");
        Object voltageTier = context.get("MachineCasingTypeTiredStats");
        this.coilLevel = GTQTUtil.getOrDefault(() -> coilType instanceof IHeatingCoilBlockStats,
                () ->  ((IHeatingCoilBlockStats) coilType).getLevel(),
                BlockWireCoil.CoilType.CUPRONICKEL.getLevel());
        this.casingTier = GTQTUtil.getOrDefault(() -> casingTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)casingTier).getIntTier(),
                0);
        this.tubeTier = GTQTUtil.getOrDefault(() -> tubeTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)tubeTier).getIntTier(),
                0);
        this.voltageTier = GTQTUtil.getOrDefault(() -> voltageTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)voltageTier).getIntTier(),
                0);

        this.tier = Math.min(this.casingTier,this.tubeTier);

        this.writeCustomData(GTQTValue.UPDATE_TIER,buf -> buf.writeInt(this.casingTier));
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
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        Random random = new Random();
        int number=recipe.getProperty(PAPartProperty.getInstance(), 0);
        int unit = number % 10; // 个位  角度匹配
        int ten = (number / 10) % 10; // 十位
        int hundred = (number / 100) % 10; // 百位   速度匹配
        int thousand = (number / 1000) % 10; // 千位
        int tenth = (number / 10000) % 10; // 万位     外壳匹配
        //匹配
        if(tenth<=casingTier) {
            if (unit == angle) {
                int se = ten + hundred * 10;
                if (se - tubeTier < (speed / 1000) && (speed / 1000) < se + tubeTier&&Mode ==2) {
                    int randomNumber = random.nextInt(10) + 1;//随机生成 1-10的数字
                    if (0 < thousand)
                        return super.checkRecipe(recipe, consumeIfSuccess);
                }
            }
        }

        return  false;
    }

    protected class ChemicalPlantLogic extends MultiblockRecipeLogic {


        public ChemicalPlantLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity,true);
        }

        int d;

        protected void updateRecipeProgress() {
            if (canRecipeProgress) {

                if (++progressTime > maxProgressTime) {
                    completeRecipe();
                    speed=0;
                }

            }

        }

        public void update() {
            long energyToDrain = GTValues.VA[EV];
            long resultEnergy = energyContainer.getEnergyStored() - energyToDrain;
            FluidStack HEAT_STACK = Lava.getFluid(d);
//               //待机默认减速
                if(speed>=0){speed=speed-1;d=1;}
                if(speed>20000){speed=speed-1;d=2;}
                if(speed>40000){speed=speed-1;d=4;}
                if(speed>60000){speed=speed-1;d=6;}
                if(speed>80000){speed=speed-1;d=8;}


                if(Mode==1)
                {
                         IMultipleTankHandler inputTank = getInputFluidInventory();
                            if(speed<99000){
                             if (HEAT_STACK.isFluidStackIdentical(inputTank.drain(HEAT_STACK, false))) {
                                 if (resultEnergy >= 0L && resultEnergy <= energyContainer.getEnergyCapacity()) {
                                     inputTank.drain(HEAT_STACK, true);
                                     speed = speed + 2 + 2 * coilLevel;
                                     energyContainer.changeEnergy(-energyToDrain);
                                 }
                             }
                      }
                }
                if(Mode==2) {
                    if (resultEnergy >= 0L && resultEnergy <= energyContainer.getEnergyCapacity()) {
                        energyContainer.changeEnergy(-energyToDrain);
                        if (speed > 0) speed = speed + 1;
                        if (speed > 20000) speed = speed + 2;
                        if (speed > 40000) speed = speed + 4;
                        if (speed > 60000) speed = speed + 6;
                        if (speed > 80000) speed = speed + 8;
                    }
                }
                if(Mode==3) {
                    if (speed > 0) speed = speed - 20;
                }


        }



    }
}