package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import com.google.common.collect.Lists;
import gregtech.api.GTValues;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.HeatingCoilRecipeLogic;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.capability.impl.NoEnergyMultiblockRecipeLogic;
import keqing.gtqtcore.api.metaileentity.multiblock.NoEnergyMultiblockController;
import keqing.gtqtcore.api.pattern.GTQTTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.CACasingTierProperty;
import keqing.gtqtcore.api.recipes.properties.PCBPartProperty;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.MetaTileEntityIndustrialPrimitiveBlastFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static gregtech.api.unification.material.Materials.*;

public class MetaTileEntityPCB extends RecipeMapMultiblockController {
    protected IMultipleTankHandler inputFluidInventory;
    protected IMultipleTankHandler outputFluidInventory;
    public MetaTileEntityPCB(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.PCB);
        this.recipeMapWorkable = new MetaTileEntityPCBWorkable(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPCB(metaTileEntityId);
    }

    int Temp=3000;
    int x=0;
    int y=0;
    int z=0;
    int w=0;

    int thresholdPercentage=10;
    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 18, "", this::decrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("gtqtcore.multiblock.pcb.threshold_decrement"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 18, "", this::incrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("gtqtcore.multiblock.pcb.threshold_increment"));
        return group;
    }

    private void incrementThreshold(Widget.ClickData clickData) {
        if(z==1)
            this.thresholdPercentage = MathHelper.clamp(thresholdPercentage + 5, 5, 40);
        else
            this.thresholdPercentage = MathHelper.clamp(thresholdPercentage + 5, 5, 20);
    }

    private void decrementThreshold(Widget.ClickData clickData) {
        if(z==1)
            this.thresholdPercentage = MathHelper.clamp(thresholdPercentage - 5, 5, 40);
        else
            this.thresholdPercentage = MathHelper.clamp(thresholdPercentage - 5, 5, 20);
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        if(recipe.getProperty(PCBPartProperty.getInstance(), 0)==1)
        {
            if(x==1)return super.checkRecipe(recipe, consumeIfSuccess);
            return false;
        }
        if(recipe.getProperty(PCBPartProperty.getInstance(), 0)==2)
        {
            if(y==1)return super.checkRecipe(recipe, consumeIfSuccess);
            return false;
        }
        return false;
    }




    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle ("    AAA    "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "           "   ,
                        "           "   ,
                        "           "   ,
                        "           "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "    A A    "   ,
                        "    A A    "   ,
                        "    A A    "   ,
                        "    A A    "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "    AAA    "
                )
                .aisle (
                        "   AAAAA   "   ,
                        "   AD DA   "   ,
                        "   AD DA   "   ,
                        "   AD DA   "   ,
                        "   AAAAA   "   ,
                        "    FFF    "   ,
                        "    FFF    "   ,
                        "    FFF    "   ,
                        "    FFF    "   ,
                        "   AAAAA   "   ,
                        "   AAGAA   "   ,
                        "   A G A   "   ,
                        "   A G A   "   ,
                        "   A G A   "   ,
                        "   A G A   "   ,
                        "   A   A   "   ,
                        "   AAAAA   "   ,
                        "   AAAAA   "
                )
                .aisle (
                        "YYYAAAAAXXX"   ,
                        "YYYA   AXXX"   ,
                        "YYYA   AXXX"   ,
                        "   A   A   "   ,
                        "   AAAAA   "   ,
                        "    HFH    "   ,
                        "    HFH    "   ,
                        "    HFH    "   ,
                        "    HFH    "   ,
                        "   AAAAA   "   ,
                        "    GGG    "   ,
                        "   A   A   "   ,
                        "   A   A   "   ,
                        "   A   A   "   ,
                        "   A   A   "   ,
                        "   A   A   "   ,
                        "   AAAAA   "   ,
                        "   AAMAA   "
                )
                .aisle (
                        "   AAAAA   "   ,
                        "   A   A   "   ,
                        "   A   A   "   ,
                        "   A   A   "   ,
                        "   AAAAA   "   ,
                        "    JFJ    "   ,
                        "    JFJ    "   ,
                        "    JFJ    "   ,
                        "    JFJ    "   ,
                        "   AAAAA   "   ,
                        "    GGG    "   ,
                        "   AAAAA   "   ,
                        "   AAAAA   "   ,
                        "   AAAAA   "   ,
                        "   AAAAA   "   ,
                        "   AAAAA   "   ,
                        "   AAAAA   "   ,
                        "   AAAAA   "
                )
                .aisle (
                        "WWWAAAAAZZZ"   ,
                        "WWWA   AZZZ"   ,
                        "WWWA   AZZZ"   ,
                        "   A   A   "   ,
                        "   A   A   "   ,
                        "   A   A   "   ,
                        "   A   A   "   ,
                        "   A   A   "   ,
                        "   A   A   "   ,
                        "   AAAAA   "   ,
                        "    GGG    "   ,
                        "   AAAAA   "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "    AAA    "
                )
                .aisle (
                        "   AAAAA   "   ,
                        "   AD DA   "   ,
                        "   AD DA   "   ,
                        "   AD DA   "   ,
                        "   AD DA   "   ,
                        "   AD DA   "   ,
                        "   AD DA   "   ,
                        "   AD DA   "   ,
                        "   AD DA   "   ,
                        "   AAAAA   "   ,
                        "    GGG    "   ,
                        "   AAAAA   "   ,
                        "           "   ,
                        "           "   ,
                        "           "   ,
                        "           "   ,
                        "           "   ,
                        "           "
                )
                .aisle (
                        "    AAA    "   ,
                        "    ACA    "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "    AAA    "   ,
                        "           "   ,
                        "           "   ,
                        "           "   ,
                        "           "   ,
                        "           "   ,
                        "           "
                )
                .where('C', selfPredicate())
                .where('A', states(getCasingState()).setMinGlobalLimited(283)
                        .or(autoAbilities(true, true, true, true, true, true, false)))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('X', GTQTTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace1", getCasingStateX()))
                .where('Y', GTQTTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace2", getCasingStateY()))
                .where('Z', GTQTTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace3", getCasingStateZ()))
                .where('W', GTQTTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace4", getCasingStateW()))
                .where('D', states(getCasingState1()))
		    	.where('F', states(getCasingState1()))
		    	.where('G', states(getCasingState1()))
			    .where('H', states(getCasingState1()))
			    .where('J', states(getCasingState1()))
                .where(' ', air())
                .build();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
        if (context.get("AuxiliaryBlastFurnace1") != null) {
            x += 1;
        }
        if (context.get("AuxiliaryBlastFurnace2") != null) {
            y += 1;
        }
        if (context.get("AuxiliaryBlastFurnace3") != null) {
            z += 1;
        }
        if (context.get("AuxiliaryBlastFurnace4") != null) {
            w += 1;
        }
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);

            if (getInputFluidInventory() != null) {
                FluidStack STACK = getInputFluidInventory().drain(Water.getFluid(Integer.MAX_VALUE), false);
                int liquidOxygenAmount = STACK == null ? 0 : STACK.amount;
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.pcb.amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
            }
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.pcb1.amount",thresholdPercentage,30,Temp/10,1000));
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.pcb.xy",x,y,z,w));

    }

    @Override
    public void addInformation(ItemStack stack,  World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("家门口的电路板工厂", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.pcb.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.pcb.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.pcb.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.machine.pcb.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.machine.pcb.tooltip.5"));
    }


    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = null;
        if (Blocks.AIR != null) {
            builder = MultiblockShapeInfo.builder()
                    .aisle ("    AAA    "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "           "   ,
                            "           "   ,
                            "           "   ,
                            "           "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "    A A    "   ,
                            "    A A    "   ,
                            "    A A    "   ,
                            "    A A    "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "    AAA    "
                    )
                    .aisle (
                            "   AAAAA   "   ,
                            "   AD DA   "   ,
                            "   AD DA   "   ,
                            "   AD DA   "   ,
                            "   AAAAA   "   ,
                            "    FFF    "   ,
                            "    FFF    "   ,
                            "    FFF    "   ,
                            "    FFF    "   ,
                            "   AAAAA   "   ,
                            "   AAGAA   "   ,
                            "   A G A   "   ,
                            "   A G A   "   ,
                            "   A G A   "   ,
                            "   A G A   "   ,
                            "   A   A   "   ,
                            "   AAAAA   "   ,
                            "   AAAAA   "
                    )
                    .aisle (
                            "YYYAAAAAXXX"   ,
                            "YYYA   AXXX"   ,
                            "YYYA   AXXX"   ,
                            "   A   A   "   ,
                            "   AAAAA   "   ,
                            "    HFH    "   ,
                            "    HFH    "   ,
                            "    HFH    "   ,
                            "    HFH    "   ,
                            "   AAAAA   "   ,
                            "    GGG    "   ,
                            "   A   A   "   ,
                            "   A   A   "   ,
                            "   A   A   "   ,
                            "   A   A   "   ,
                            "   A   A   "   ,
                            "   AAAAA   "   ,
                            "   AAAAA   "
                    )
                    .aisle (
                            "   AAAAA   "   ,
                            "   A   A   "   ,
                            "   A   A   "   ,
                            "   A   A   "   ,
                            "   AAAAA   "   ,
                            "    JFJ    "   ,
                            "    JFJ    "   ,
                            "    JFJ    "   ,
                            "    JFJ    "   ,
                            "   AAAAA   "   ,
                            "    GGG    "   ,
                            "   AAAAA   "   ,
                            "   AAAAA   "   ,
                            "   AAAAA   "   ,
                            "   AAAAA   "   ,
                            "   AAAAA   "   ,
                            "   AAAAA   "   ,
                            "   AAAAA   "
                    )
                    .aisle (
                            "WWWAAAAAZZZ"   ,
                            "WWWA   AZZZ"   ,
                            "WWWA   AZZZ"   ,
                            "   A   A   "   ,
                            "   A   A   "   ,
                            "   A   A   "   ,
                            "   A   A   "   ,
                            "   A   A   "   ,
                            "   A   A   "   ,
                            "   AAAAA   "   ,
                            "    GGG    "   ,
                            "   AAAAA   "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "    AAA    "
                    )
                    .aisle (
                            "   AAAAA   "   ,
                            "   AD DA   "   ,
                            "   AD DA   "   ,
                            "   AD DA   "   ,
                            "   AD DA   "   ,
                            "   AD DA   "   ,
                            "   AD DA   "   ,
                            "   AD DA   "   ,
                            "   AD DA   "   ,
                            "   AAAAA   "   ,
                            "    GGG    "   ,
                            "   AAAAA   "   ,
                            "           "   ,
                            "           "   ,
                            "           "   ,
                            "           "   ,
                            "           "   ,
                            "           "
                    )
                    .aisle (
                            "    AAA    "   ,
                            "    ACA    "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "    AAA    "   ,
                            "           "   ,
                            "           "   ,
                            "           "   ,
                            "           "   ,
                            "           "   ,
                            "           "
                    )
                    .where('C', GTQTMetaTileEntities.PCB, EnumFacing.SOUTH)
                    .where('D', getCasingState1())
                    .where('A', getCasingState())
                    .where('F', getCasingState1())
                    .where('G', getCasingState1())
                    .where('H', getCasingState1())
                    .where('J', getCasingState1())
                    .where(' ', Blocks.AIR.getDefaultState());
            shapeInfo.add(builder.build());
            shapeInfo.add(builder
                    .where('X', getCasingStateX())
                    .build());
            shapeInfo.add(builder
                    .where('Y', getCasingStateY())
                    .build());
            shapeInfo.add(builder
                    .where('Z', getCasingStateZ())
                    .build());
            shapeInfo.add(builder
                    .where('W', getCasingStateW())
                    .build());
        }
        return shapeInfo;
    }

    protected IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.BRICK);
    }

    protected IBlockState getCasingState1() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_MACHINE_CASING);
    }

    protected IBlockState getCasingStateX() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_MACHINE_CASING);
    }
    protected IBlockState getCasingStateY() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_MACHINE_CASING);
    }
    protected IBlockState getCasingStateZ() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_MACHINE_CASING);
    }
    protected IBlockState getCasingStateW() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_MACHINE_CASING);
    }
    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.BRICK;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }



    public static int getMaxParallel(int Temp,int w) {
        if(w==1)
        {
            if(Temp<4000)return 4;
            if(Temp<6000)return 16;
            if(Temp<10000) return 8;
        }
        return 1;
    }
    protected class MetaTileEntityPCBWorkable extends MultiblockRecipeLogic {
        private final MetaTileEntityPCB PCB;

        public MetaTileEntityPCBWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
            this.PCB = (MetaTileEntityPCB) tileEntity;
        }

        @Override
        public int getParallelLimit() {
            return getMaxParallel(Temp, w);
        }

        FluidStack COLD_STACK = Water.getFluid(1);

        @Override
        public void update() {
            super.update();
            if (z == 1 && Temp > 3000) {
                IMultipleTankHandler inputTank = getInputFluidInventory();
                if (COLD_STACK.isFluidStackIdentical(inputTank.drain(COLD_STACK, false))) {
                    for(int i=0;i<thresholdPercentage;i++) inputTank.drain(COLD_STACK, true);
                    Temp = Temp - thresholdPercentage;
                }
            }
        }

        protected void updateRecipeProgress() {
            if (Temp > 3000) Temp = Temp - 10;
            if (canRecipeProgress) {
                if (Temp < 10000) Temp = Temp + 40;
                if (z == 1) if (++progressTime % 3 == 0) maxProgressTime--;
                if (Temp < 9000) {
                    if (++progressTime % 5 == 0 && w == 1)
                        maxProgressTime = maxProgressTime - 1;
                    if (++progressTime > maxProgressTime) {
                        completeRecipe();
                    }
                }
            }

        }
    }
}