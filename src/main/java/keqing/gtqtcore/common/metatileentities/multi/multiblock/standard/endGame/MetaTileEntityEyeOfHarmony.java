package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.endGame;

import com.cleanroommc.modularui.api.GuiAxis;
import com.cleanroommc.modularui.api.IPanelHandler;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.ItemDrawable;
import com.cleanroommc.modularui.drawable.Rectangle;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.utils.Color;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.value.sync.StringSyncValue;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import com.cleanroommc.modularui.widgets.SliderWidget;
import com.cleanroommc.modularui.widgets.layout.Flow;
import com.cleanroommc.modularui.widgets.textfield.TextFieldWidget;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIBuilder;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIFactory;
import gregtech.api.mui.GTGuiTextures;
import gregtech.api.mui.GTGuis;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.KeyUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.GeneratorProperty;
import keqing.gtqtcore.api.recipes.properties.SpacetimeCompressionProperty;
import keqing.gtqtcore.api.recipes.properties.SuccessChanceProperty;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockQuantumCasing;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

import static gregtech.api.util.RelativeDirection.*;
import static keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Spacetime;
import static keqing.gtqtcore.api.utils.GTQTUtil.logBase;

public class MetaTileEntityEyeOfHarmony extends RecipeMapMultiblockController {
    //+成功率
    //+额外产出
    //减少电量产出
    //成功率
    int successChance;
    //-耗时 OK
    //-成功率 OK
    //发电量
    long generator;
    //配方等级 OK
    //+并行 OK
    int maxAllowedOc;
    //时间膨胀发生器
    private int timeAcceleration;
    //压缩时空发生器
    private int spaceTimeCompression;
    //稳定力场发生器
    private int stabilization;

    public MetaTileEntityEyeOfHarmony(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.VIRTUAL_COSMOS_SIMULATOR_RECIPES);
        this.recipeMapWorkable = new EOHRecipeLogic(this);
    }

    private static IBlockState getCommonState() {
        return GTQTMetaBlocks.blockQuantumCasing.getState(BlockQuantumCasing.CasingType.INFINITE_SPACETIME_ENERGY_CASING);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.timeAcceleration = 0;
        this.spaceTimeCompression = 0;
        this.stabilization = 0;
        this.maxAllowedOc = 0;
    }

    @Override
    protected void configureDisplayText(MultiblockUIBuilder builder) {
        builder.setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addCustom((textList, syncer) -> {
                    textList.add(KeyUtil.lang( "时间膨胀发生器:%s", syncer.syncInt(timeAcceleration)));
                    textList.add(KeyUtil.lang( "压缩时空发生器:%s", syncer.syncInt(spaceTimeCompression)));
                    textList.add(KeyUtil.lang( "时间膨胀发生器:%s", syncer.syncInt(stabilization)));
                    textList.add(KeyUtil.lang( "星阵数量:%s", syncer.syncInt(calculateStarArray())));
                    textList.add(KeyUtil.lang( "最大超频次数:%s", syncer.syncInt(maxAllowedOc)));
                })
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgress(), recipeMapWorkable.getMaxProgress())
                .addRecipeOutputLine(recipeMapWorkable);
    }

    @Override
    protected MultiblockUIFactory createUIFactory() {
        return super.createUIFactory()
                .createFlexButton((guiData, syncManager) -> {
                    var throttle = syncManager.panel("throttle_panel", this::makeThrottlePanel, true);

                    return new ButtonWidget<>()
                            .size(18)
                            .overlay(GTGuiTextures.FILTER_SETTINGS_OVERLAY.asIcon().size(16))
                            .addTooltipLine(IKey.lang("调整最大超频次数"))
                            .onMousePressed(i -> {
                                if (throttle.isPanelOpen()) {
                                    throttle.closePanel();
                                } else {
                                    throttle.openPanel();
                                }
                                return true;
                            });
                });
    }

    private ModularPanel makeThrottlePanel(PanelSyncManager syncManager, IPanelHandler syncHandler) {
        StringSyncValue throttleValue = new StringSyncValue(() -> maxAllowedOc+"", str -> {
            try {
                if (str.charAt(str.length() - 1) == '%') {
                    str = str.substring(0, str.length() - 1);
                }

                this.maxAllowedOc = Integer.parseInt(str);
            } catch (NumberFormatException ignored) {

            }
        });
        DoubleSyncValue sliderValue = new DoubleSyncValue(
                () -> (double) getMaxAllowedOc() / 8,
                d -> setMaxAllowedOc((int) (d * 8)));

        return GTGuis.createPopupPanel("boiler_throttle", 116, 53)
                .child(Flow.row()
                        .pos(4, 4)
                        .height(16)
                        .coverChildrenWidth()
                        .child(new ItemDrawable(getStackForm())
                                .asWidget()
                                .size(16)
                                .marginRight(4))
                        .child(IKey.lang("超频次数")
                                .asWidget()
                                .heightRel(1.0f)))
                .child(Flow.row()
                        .top(20)
                        .margin(4, 0)
                        .coverChildrenHeight()
                        .child(new SliderWidget()
                                .background(new Rectangle().setColor(Color.BLACK.brighter(2)).asIcon()
                                        .height(8))
                                .bounds(0, 1)
                                .setAxis(GuiAxis.X)
                                .value(sliderValue)
                                .widthRel(0.7f)
                                .height(20))
                        // todo switch this text field with GTTextFieldWidget in PR #2700
                        .child(new TextFieldWidget()
                                .widthRel(0.3f)
                                .height(20)
                                // TODO proper color
                                .setTextColor(Color.WHITE.darker(1))
                                .setValidator(str -> {
                                    if (str.charAt(str.length() - 1) == '%') {
                                        str = str.substring(0, str.length() - 1);
                                    }

                                    try {
                                        long l = Long.parseLong(str);
                                        if (l < 0) l = 0;
                                        else if (l > 8) l = 8;
                                        return String.valueOf(l);
                                    } catch (NumberFormatException ignored) {
                                        return throttleValue.getValue();
                                    }
                                })
                                .value(throttleValue)
                                .background(GTGuiTextures.DISPLAY)));
    }

    private int getMaxAllowedOc() {
        return maxAllowedOc;
    }

    private void setMaxAllowedOc(int i) {
        maxAllowedOc = i;
    }


    public int calculateStarArray() {
        if (getImportItems() == null) return 0;
        int number = 0;
        var slots = this.getInputInventory().getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack item = this.getInputInventory().getStackInSlot(i);
            if (GTQTMetaItems.ASTRAL_ARRAY.isItemEqual(item)) {
                number += item.getCount();
            }
        }
        return number;
    }

    public void consumeStarArray(int number) {
        var slots = this.getInputInventory().getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack item = this.getInputInventory().getStackInSlot(i);
            if (GTQTMetaItems.ASTRAL_ARRAY.isItemEqual(item)) {
                if (item.getCount() >= number) {
                    this.getInputInventory().extractItem(i, number, false);
                    return;
                } else {
                    this.getInputInventory().extractItem(i, item.getCount(), false);
                }
            }
        }
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        SpacetimeCompressionProperty spacetimeCompressionProps = SpacetimeCompressionProperty.getInstance();

        if (recipe.hasProperty(spacetimeCompressionProps)) {
            if (spaceTimeCompression < recipe.getProperty(spacetimeCompressionProps, 0)) return false;
        }
        //记录
        SuccessChanceProperty successChanceProps = SuccessChanceProperty.getInstance();
        GeneratorProperty generatorProps = GeneratorProperty.getInstance();

        successChance = recipe.getProperty(successChanceProps, 0);
        generator = recipe.getProperty(generatorProps, 0L);
        return super.checkRecipe(recipe, consumeIfSuccess);
    }

    //时间膨胀 每级减少50%
    public double getTimeBound() {
        return Math.pow(0.5, timeAcceleration - 1);
    }

    //成功率
    public int getSuccessChance() {
        return Math.max(0, Math.min(100, successChance - timeAcceleration * 10 + stabilization * 5));
    }

    //发电
    public long getEUOutput() {
        return (long) (generator * Math.pow(0.95, stabilization - 1));
    }

    private void doGenerator() {
        this.energyContainer.addEnergy(this.getEUOutput());
    }

    public int getExtraOutput() {
        return stabilization * 5;
    }

    @Override
    public void addInformation(ItemStack stack,
                               World player,
                               List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.BLINKING_RED + I18n.format("创造一颗星球，然后毁灭他"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("模拟大爆炸奇点：启动量子真空涨落协议，重现宇宙原初时空结构"));
        tooltip.add(I18n.format("注入基础重子物质流（H/He），触发引力坍缩，锻造新生星球胚胎"));
        tooltip.add(I18n.format("随后激活引力解离场，拆解星体，回收其凝聚态宇宙物质精华"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.laser.tooltip"));
        tooltip.add(I18n.format("可使用无线能源/动力仓，直接发电至无线电网"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("这台多方块机器需要3种模块,每种模块有9个等级"));
        tooltip.add(I18n.format("每种模块对多方块机器均有相关影响，详情如下所示："));
        tooltip.add(I18n.format("压缩时空场发生器："));
        tooltip.add(I18n.format("-每等级减少50%%的配方耗时"));
        tooltip.add(I18n.format("-每等级减少10%%的配方成功率"));
        tooltip.add(I18n.format("时间膨胀场发生器："));
        tooltip.add(I18n.format("-本方块的等级决定多方块机器可运行的配方等级"));
        tooltip.add(I18n.format("-每级可使得多方块并行数量+1"));
        tooltip.add(I18n.format("稳定力场发生器："));
        tooltip.add(I18n.format("-每等级增加5%%的配方成功率"));
        tooltip.add(I18n.format("-每等级增加5%%的额外产出率"));
        tooltip.add(I18n.format("-每等级减少5%%的发电量"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("在输入总线内放入星阵可大幅提升设备的并行数量与性能（星阵数量无上限）"));
        tooltip.add(I18n.format("并行指数=Log(星阵数量*4)/Log(2)"));
        tooltip.add(I18n.format("额外并行=Math.pow(2.并行指数)"));
        tooltip.add(I18n.format("与模块升级带来的并行做加法运算，即总并行：Math.pow(2.并行指数)+时间膨胀场发生器等级"));
        tooltip.add(I18n.format("星阵数量>=64时进入无损超频模式"));
        tooltip.add(I18n.format("无损超频状态下每运行一轮消耗Log(星阵数量)/Log(8)数量的星阵(无论是否成功)"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("可在ui内调整设备最大超频次数（最小0，最大8）"));
        tooltip.add(I18n.format("每次超频都使得耗电*4，耗时*0.5，每次超频做乘法运算"));
        tooltip.add(I18n.format("输出产物的几率&数量以及发电量不受超频影响。"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("配方基础成功率已在jei内给出，同一并行批次的配方共享一个成功率"));
        tooltip.add(I18n.format("无论配方成功与否，都会产出jei所示的发电电量"));
        tooltip.add(I18n.format("如果配方失败将输出成功几率×14,400×Math.pow(配方等级,2)L的熔融时空，"));
        tooltip.add(I18n.format("如果机器工作中电量不足则会直接视为运行失败，不产生任何电量与时空，"));
        tooltip.add(I18n.format("如果触发额外产出，同一并行批次的配方均视为可额外产出的对象"));
        tooltip.add(I18n.format("=============================================="));
    }

    private void outputTimeSpace() {
        getOutputFluidInventory().fill(Spacetime.getFluid((int) (144 * successChance * Math.pow(2, spaceTimeCompression))), true);
    }


    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityEyeOfHarmony(metaTileEntityId);
    }

    protected BlockPattern createStructurePattern() {
        FactoryBlockPattern pattern = FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "               A A               ", "            AAAAAAAAA            ", "               A A               ", "            AAAAAAAAA            ", "               A A               ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "               A A               ", "               A A               ", "              BBBBB              ", "             BBABABB             ", "         AAAABAABAABAAAA         ", "             BBBBBBB             ", "         AAAABAABAABAAAA         ", "             BBABABB             ", "              BBBBB              ", "               A A               ", "               A A               ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "               A A               ", "                B                ", "                B                ", "             BBBBBBB             ", "            BB     BB            ", "            B  CCC  B            ", "       AAA  B CDDDC B  AAA       ", "          BBB CDDDC BBB          ", "       AAA  B CDDDC B  AAA       ", "            B  CCC  B            ", "            BB     BB            ", "             BBBBBBB             ", "                B                ", "                B                ", "               A A               ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "                B                ", "                B                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      AA                 AA      ", "        BB             BB        ", "      AA                 AA      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                B                ", "                B                ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "              AAAAA              ", "                B                ", "                D                ", "                D                ", "                                 ", "                                 ", "                                 ", "                                 ", "      A                   A      ", "     AA                   AA     ", "      ABDD             DDBA      ", "     AA                   AA     ", "      A                   A      ", "                                 ", "                                 ", "                                 ", "                                 ", "                D                ", "                D                ", "                B                ", "              AAAAA              ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "                B                ", "             ECCDCCE             ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "       E                 E       ", "       C                 C       ", "    AA C                 C AA    ", "      BD                 DB      ", "    AA C                 C AA    ", "       C                 C       ", "       E                 E       ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "             ECCDCCE             ", "                B                ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "               A A               ", "              AAAAA              ", "                B                ", "                D                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "    A                       A    ", "   AA                       AA   ", "    ABD                   DBA    ", "   AA                       AA   ", "    A                       A    ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                D                ", "                B                ", "              AAAAA              ", "               A A               ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "               A A               ", "               A A               ", "                B                ", "             ECCDCCE             ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "     E                     E     ", "     C                     C     ", "  AA C                     C AA  ", "    BD                     DB    ", "  AA C                     C AA  ", "     C                     C     ", "     E                     E     ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "             ECCDCCE             ", "                B                ", "               A A               ", "               A A               ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "               A A               ", "                B                ", "                D                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  A                           A  ", "   BD                       DB   ", "  A                           A  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                D                ", "                B                ", "               A A               ", "                                 ", "                                 ")
                .aisle("                                 ", "               A A               ", "               A A               ", "                B                ", "                D                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " AA                           AA ", "   BD                       DB   ", " AA                           AA ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                D                ", "                B                ", "               A A               ", "               A A               ", "                                 ")
                .aisle("                                 ", "               A A               ", "                B                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " A                             A ", "  B                           B  ", " A                             A ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                B                ", "               A A               ", "                                 ")
                .aisle("                                 ", "               A A               ", "                B                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " A                             A ", "  B                           B  ", " A                             A ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                B                ", "               A A               ", "                                 ")
                .aisle("             AAAAAAA             ", "               A A               ", "             BBBBBBB             ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  B                           B  ", "  B                           B  ", "AAB                           BAA", "  B                           B  ", "AAB                           BAA", "  B                           B  ", "  B                           B  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "             BBBBBBB             ", "               A A               ", "               A A               ")
                .aisle("            AAFFFFFAA            ", "              BBBBB              ", "            BB     BB            ", "                                 ", "                                 ", "       E                 E       ", "                                 ", "     E                     E     ", "                                 ", "                                 ", "                                 ", "                                 ", "  B                           B  ", "  B                           B  ", " B                             B ", "AB                             BA", " B                             B ", "AB                             BA", " B                             B ", "  B                           B  ", "  B                           B  ", "                                 ", "                                 ", "                                 ", "                                 ", "     E                     E     ", "                                 ", "       E                 E       ", "                                 ", "                                 ", "            BB     BB            ", "              BBBBB              ", "               A A               ")
                .aisle("            AFFFFFFFA            ", "             BBABABB             ", "            B  CCC  B            ", "                                 ", "      A                   A      ", "       C                 C       ", "    A                       A    ", "     C                     C     ", "                                 ", "                                 ", "                                 ", "                                 ", "  B                           B  ", " B                             B ", " B                             B ", "AAC                           CAA", " BC                           CB ", "AAC                           CAA", " B                             B ", " B                             B ", "  B                           B  ", "                                 ", "                                 ", "                                 ", "                                 ", "     C                     C     ", "    A                       A    ", "       C                 C       ", "      A                   A      ", "                                 ", "            B  CCC  B            ", "             BBABABB             ", "               A A               ")
                .aisle("            AFFAAAFFA            ", "         AAAABAABAABAAAA         ", "       AAA  B CDDDC B  AAA       ", "      AA                 AA      ", "     AA                   AA     ", "    AA C                 C AA    ", "   AA                       AA   ", "  AA C                     C AA  ", "  A                           A  ", " AA                           AA ", " A                             A ", " A                             A ", "AAB                           BAA", "AB                             BA", "AAC                           CAA", "AAD                           DAA", "ABD                           DBA", "AAD                           DAA", "AAC                           CAA", "AB                             BA", "AAB                           BAA", " A                             A ", " A                             A ", " AA                           AA ", "  A                           A  ", "  AA C                     C AA  ", "   AA                       AA   ", "    AA C                 C AA    ", "     AA                   AA     ", "      AA                 AA      ", "       AAA  B CDDDC B  AAA       ", "         AAAABAABAABAAAA         ", "            AAAAAAAAA            ")
                .aisle("            AFFAMAFFA            ", "             BBBBBBB             ", "          BBB CDDDC BBB          ", "        BB             BB        ", "      ABDD             DDBA      ", "      BD                 DB      ", "    ABD                   DBA    ", "    BD                     DB    ", "   BD                       DB   ", "   BD                       DB   ", "  B                           B  ", "  B                           B  ", "  B                           B  ", " B                             B ", " BC                           CB ", "ABD                           DBA", " BD                           DB ", "ABD                           DBA", " BC                           CB ", " B                             B ", "  B                           B  ", "  B                           B  ", "  B                           B  ", "   BD                       DB   ", "   BD                       DB   ", "    BD                     DB    ", "    ABD                   DBA    ", "      BD                 DB      ", "      ABDD             DDBA      ", "        BB             BB        ", "          BBB CDDDC BBB          ", "             BBBBBBB             ", "               A A               ")
                .aisle("            AFFAAAFFA            ", "         AAAABAABAABAAAA         ", "       AAA  B CDDDC B  AAA       ", "      AA                 AA      ", "     AA                   AA     ", "    AA C                 C AA    ", "   AA                       AA   ", "  AA C                     C AA  ", "  A                           A  ", " AA                           AA ", " A                             A ", " A                             A ", "AAB                           BAA", "AB                             BA", "AAC                           CAA", "AAD                           DAA", "ABD                           DBA", "AAD                           DAA", "AAC                           CAA", "AB                             BA", "AAB                           BAA", " A                             A ", " A                             A ", " AA                           AA ", "  A                           A  ", "  AA C                     C AA  ", "   AA                       AA   ", "    AA C                 C AA    ", "     AA                   AA     ", "      AA                 AA      ", "       AAA  B CDDDC B  AAA       ", "         AAAABAABAABAAAA         ", "            AAAAAAAAA            ")
                .aisle("            AFFFFFFFA            ", "             BBABABB             ", "            B  CCC  B            ", "                                 ", "      A                   A      ", "       C                 C       ", "    A                       A    ", "     C                     C     ", "                                 ", "                                 ", "                                 ", "                                 ", "  B                           B  ", " B                             B ", " B                             B ", "AAC                           CAA", " BC                           CB ", "AAC                           CAA", " B                             B ", " B                             B ", "  B                           B  ", "                                 ", "                                 ", "                                 ", "                                 ", "     C                     C     ", "    A                       A    ", "       C                 C       ", "      A                   A      ", "                                 ", "            B  CCC  B            ", "             BBABABB             ", "               A A               ")
                .aisle("            AAFFFFFAA            ", "              BBBBB              ", "            BB     BB            ", "                                 ", "                                 ", "       E                 E       ", "                                 ", "     E                     E     ", "                                 ", "                                 ", "                                 ", "                                 ", "  B                           B  ", "  B                           B  ", " B                             B ", "AB                             BA", " B                             B ", "AB                             BA", " B                             B ", "  B                           B  ", "  B                           B  ", "                                 ", "                                 ", "                                 ", "                                 ", "     E                     E     ", "                                 ", "       E                 E       ", "                                 ", "                                 ", "            BB     BB            ", "              BBBBB              ", "               A A               ")
                .aisle("             AAAAAAA             ", "               A A               ", "             BBBBBBB             ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  B                           B  ", "  B                           B  ", "AAB                           BAA", "  B                           B  ", "AAB                           BAA", "  B                           B  ", "  B                           B  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "             BBBBBBB             ", "               A A               ", "               A A               ")
                .aisle("                                 ", "               A A               ", "                B                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " A                             A ", "  B                           B  ", " A                             A ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                B                ", "               A A               ", "                                 ")
                .aisle("                                 ", "               A A               ", "                B                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " A                             A ", "  B                           B  ", " A                             A ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                B                ", "               A A               ", "                                 ")
                .aisle("                                 ", "               A A               ", "               A A               ", "                B                ", "                D                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " AA                           AA ", "   BD                       DB   ", " AA                           AA ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                D                ", "                B                ", "               A A               ", "               A A               ", "                                 ")
                .aisle("                                 ", "                                 ", "               A A               ", "                B                ", "                D                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  A                           A  ", "   BD                       DB   ", "  A                           A  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                D                ", "                B                ", "               A A               ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "               A A               ", "               A A               ", "                B                ", "             ECCDCCE             ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "     E                     E     ", "     C                     C     ", "  AA C                     C AA  ", "    BD                     DB    ", "  AA C                     C AA  ", "     C                     C     ", "     E                     E     ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "             ECCDCCE             ", "                B                ", "               A A               ", "               A A               ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "               A A               ", "              AAAAA              ", "                B                ", "                D                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "    A                       A    ", "   AA                       AA   ", "    ABD                   DBA    ", "   AA                       AA   ", "    A                       A    ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                D                ", "                B                ", "              AAAAA              ", "               A A               ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "                B                ", "             ECCDCCE             ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "       E                 E       ", "       C                 C       ", "    AA C                 C AA    ", "      BD                 DB      ", "    AA C                 C AA    ", "       C                 C       ", "       E                 E       ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "             ECCDCCE             ", "                B                ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "              AAAAA              ", "                B                ", "                D                ", "                D                ", "                                 ", "                                 ", "                                 ", "                                 ", "      A                   A      ", "     AA                   AA     ", "      ABDD             DDBA      ", "     AA                   AA     ", "      A                   A      ", "                                 ", "                                 ", "                                 ", "                                 ", "                D                ", "                D                ", "                B                ", "              AAAAA              ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "                B                ", "                B                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      AA                 AA      ", "        BB             BB        ", "      AA                 AA      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                B                ", "                B                ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "               A A               ", "                B                ", "                B                ", "             BBBBBBB             ", "            BB     BB            ", "            B  CCC  B            ", "       AAA  B CDDDC B  AAA       ", "          BBB CDDDC BBB          ", "       AAA  B CDDDC B  AAA       ", "            B  CCC  B            ", "            BB     BB            ", "             BBBBBBB             ", "                B                ", "                B                ", "               A A               ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "               A A               ", "               A A               ", "              BBBBB              ", "             BBABABB             ", "         AAAABAABAABAAAA         ", "             BBBBBBB             ", "         AAAABAABAABAAAA         ", "             BBABABB             ", "              BBBBB              ", "               A A               ", "               A A               ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "               A A               ", "            AAAAAAAAA            ", "               A A               ", "            AAAAAAAAA            ", "               A A               ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")

                .where('M', this.selfPredicate())
                .where('F', states(getCommonState())
                        .or(autoAbilities()))
                .where('A', states(this.getCasingState()))
                .where('B', states(this.getCasingState1()))

                .where('C', TIME_ACCELERATION.get())
                .where('D', SPACETIME_COMPRESSION.get())
                .where('E', STABILIZATION.get())
                .where(' ', any());
        return pattern.build();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object timeAcceleration = context.get("TimeAccelerationTieredStats");
        Object spaceTimeCompression = context.get("SpacetimeCompressionTieredStats");
        Object stabilization = context.get("StabilizationTieredStats");
        //时间膨胀发生器
        this.timeAcceleration = GTQTUtil.getOrDefault(() -> timeAcceleration instanceof WrappedIntTired,
                () -> ((WrappedIntTired) timeAcceleration).getIntTier(),
                0);
        //压缩时空发生器
        this.spaceTimeCompression = GTQTUtil.getOrDefault(() -> spaceTimeCompression instanceof WrappedIntTired,
                () -> ((WrappedIntTired) spaceTimeCompression).getIntTier(),
                0);
        //稳定力场发生器
        this.stabilization = GTQTUtil.getOrDefault(() -> stabilization instanceof WrappedIntTired,
                () -> ((WrappedIntTired) stabilization).getIntTier(),
                0);
    }

    private IBlockState getCasingState() {
        return GTQTMetaBlocks.blockQuantumCasing.getState(BlockQuantumCasing.CasingType.REINFORCED_SPACE_CASING);
    }

    private IBlockState getCasingState1() {
        return GTQTMetaBlocks.blockQuantumCasing.getState(BlockQuantumCasing.CasingType.REINFORCED_SPACETIME_CASING);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return iMultiblockPart == null ? GTQTTextures.ULTIMATE_HIGH_ENERGY_CASING : Textures.HIGH_POWER_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return GTQTTextures.COLLIDER_OVERLAY;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public boolean shouldShowVoidingModeButton() {
        return false;
    }

    @Override
    public void update() {
        super.update();

    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("successChance", this.successChance);
        data.setLong("generator", this.generator);
        data.setInteger("maxAllowedOc", this.maxAllowedOc);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        this.successChance = data.getInteger("successChance");
        this.generator = data.getLong("generator");
        this.maxAllowedOc = data.getInteger("maxAllowedOc");
        super.readFromNBT(data);
    }

    private class EOHRecipeLogic extends MultiblockRecipeLogic {

        public EOHRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (maxProgress * getTimeBound()));
        }

        protected double getOverclockingDurationFactor() {
            return calculateStarArray() > 64 ? 0.25 : 0.5;
        }

        //并行
        @Override
        public int getParallelLimit() {
            if (calculateStarArray() == 0) return spaceTimeCompression;
            int number = (int) logBase(calculateStarArray() * 4, 2);
            return (int) Math.pow(2, number) + spaceTimeCompression;
        }


        public void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);
                if (++this.progressTime > this.maxProgressTime) {
                    Random random = new Random();
                    if (random.nextInt(100) < getExtraOutput()) {
                        //额外产出
                        this.outputRecipeOutputs();
                    }
                    random = new Random();
                    if (random.nextInt(100) < getSuccessChance()) {
                        //发电
                        doGenerator();
                        this.completeRecipe();
                    } else {
                        //发电
                        doGenerator();
                        //产出时空
                        outputTimeSpace();
                        invalidate();
                    }
                    if (calculateStarArray() >= 64) {
                        consumeStarArray((int) logBase(calculateStarArray(), 8));
                    }
                    this.completeRecipe();
                }

                if (this.hasNotEnoughEnergy && this.getEnergyInputPerSecond() > 19L * this.recipeEUt) {
                    this.hasNotEnoughEnergy = false;
                }
            } else if (this.recipeEUt > 0L) {
                this.hasNotEnoughEnergy = true;
                invalidate();
            }

        }

        @Override
        protected int getNumberOfOCs(long recipeEUt) {
            return Math.min(super.getNumberOfOCs(recipeEUt), maxAllowedOc);
        }
    }
}
