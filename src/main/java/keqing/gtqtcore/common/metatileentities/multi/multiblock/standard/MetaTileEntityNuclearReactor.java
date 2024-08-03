package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

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
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.NuclearProperties;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTNuclearFusion;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

import static gregtech.api.unification.material.Materials.Water;

public class MetaTileEntityNuclearReactor extends RecipeMapMultiblockController implements IProgressBarMultiblock {
    double heat;
    int rate=1;
    double cold=1;
    boolean open;
    boolean care;
    double temp;
    double addtemp;
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setDouble("heat", heat);
        data.setInteger("rate", rate);
        data.setDouble("temp", temp);
        data.setDouble("addtemp", addtemp);
        data.setDouble("cold", cold);
        data.setBoolean("open", open);
        data.setBoolean("care", care);
        return super.writeToNBT(data);
    }
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        heat = data.getDouble("heat");
        rate = data.getInteger("rate");
        temp = data.getDouble("temp");
        addtemp = data.getDouble("addtemp");
        cold = data.getDouble("cold");
        open = data.getBoolean("open");
        care = data.getBoolean("care");
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityNuclearReactor(this.metaTileEntityId);
    }
    public MetaTileEntityNuclearReactor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.NUCLEAR_RECIPES);
    }
    public boolean fillTanks(FluidStack stack, boolean simulate) {
        return GTTransferUtils.addFluidsToFluidHandler(getOutputFluidInventory(), simulate, Collections.singletonList(stack));
    }
    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        temp=recipe.getProperty(NuclearProperties.getInstance(), 0);
        return super.checkRecipe(recipe, consumeIfSuccess);
    }
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("加入核燃料反应时，积热会根据燃料的升温率缓慢上升"));
        tooltip.add(I18n.format("反应时额外获得一个积热倍率的时间递增函数，如果不加以干预，温度会上升越来越快"));
        tooltip.add(I18n.format("积热达到500K时加入水开始继续辐射热交换生产高压蒸汽，积热每上升1000K高压蒸汽输出加倍一次"));
        tooltip.add(I18n.format("调节冷却流量阀门控制换热比例(基础送水速度4000mb/t)，单位时间内进行热交换的流体更多，同时积热加速降温(最高高压蒸汽产出5+5mb/t)"));
        tooltip.add(I18n.format("当多方块进入生产超频（降温率大于升温率）,高压蒸汽生产额外获得五倍输出"));
        tooltip.add(I18n.format("当多方块温度到达5000K时会触发特大范围熔毁爆炸"));
        tooltip.add(I18n.format("超频模式：立刻进入深度反应，积热升温倍率提高四倍，燃料反应时间不变，但此时强制停机模式失效"));
        tooltip.add(I18n.format("高温停机模式：到达预警（5500K）临界温度时发生一次小爆炸强制停机"));
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentTranslation("=========核电控制台========="));
        if(open&&care)textList.add(new TextComponentTranslation("超频：%s | 高温强制停机失效！！！"));
        else textList.add(new TextComponentTranslation("超频：%s | 高温强制停机：%s", open,care));
        textList.add(new TextComponentTranslation("积热：%s", heat));
        textList.add(new TextComponentTranslation("升温率：%s 降温率：%s ", temp,cold));
        textList.add(new TextComponentTranslation("积热升温倍率：%s", addtemp));
        if(temp-cold>0) textList.add(new TextComponentTranslation("生产超频倍率（失效）：%s",1));
        else textList.add(new TextComponentTranslation("生产超频倍率（工作）：%s",1+heat*((cold/temp)/100)));
        textList.add(new TextComponentTranslation("============================="));
        textList.add(new TextComponentTranslation("安全生产 人民放心 安全工作 重于泰山"));
    }
    FluidStack WATER_STACK = Water.getFluid(1000*4*rate);
    FluidStack HOT_STACK1 = GTQTMaterials.HighPressureSteam.getFluid(1);
    FluidStack HOT_STACK2 = GTQTMaterials.HighPressureSteam.getFluid(2);
    @Override
    public void update() {
        super.update();
        IMultipleTankHandler inputTank = getInputFluidInventory();
        if(recipeMapWorkable.isWorking()) {
            heat +=temp*addtemp;
            addtemp+=0.001;
            if(open)heat +=temp*addtemp*4;
        }
        else if(addtemp>0)addtemp-=0.005;
        //逻辑：反应升温 需要熔盐
        if (WATER_STACK.isFluidStackIdentical(inputTank.drain(WATER_STACK, false))&&heat>500) {
            inputTank.drain(WATER_STACK, true);
            if(heat>1000)fillTanks(HOT_STACK1,false);
            if(heat>3000)fillTanks(HOT_STACK1,false);
            if(heat>5000)fillTanks(HOT_STACK1,false);
            if(temp-cold<=0)fillTanks(HOT_STACK2,false);
            cold=rate;
            heat-=heat*((cold/temp)/100);
            addtemp-=rate*0.0002;
        }
        if(heat>6000)doExplosion1((float) (temp*3));
        if(!open&&care&&heat>5500)doExplosion2(1);
    }
    public void doExplosion1(float explosionPower) {
        this.setExploded();
        this.getWorld().setBlockToAir(this.getPos());
        for(int i=-3;i<3;i++)
            for(int j=-3;j<3;j++)
                for(int k=-3;k<3;k++)
        this.getWorld().createExplosion((Entity)null, (double)this.getPos().getX() + 0.5+5*i, (double)this.getPos().getY() + 0.5+5*k, (double)this.getPos().getZ() + 0.5+5*j, explosionPower, true);
    }
    public void doExplosion2(float explosionPower) {
        this.setExploded();
        this.getWorld().setBlockToAir(this.getPos());
        this.getWorld().createExplosion((Entity)null, (double)this.getPos().getX() + 4.5, (double)this.getPos().getY() + 0.5, (double)this.getPos().getZ() + 4.5, explosionPower, true);
    }
    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (heat>4000) {
            textList.add(new TextComponentTranslation("高温提醒！"));
        }
        if (heat>5000) {
            textList.add(new TextComponentTranslation("高温警告！"));
        }
        if(open&&care){
            textList.add(new TextComponentTranslation("保险模式失效！！！"));
        }
    }
    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 9, "", this::decrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("减少流量"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 9, "", this::incrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("增大流量"));
        group.addWidget(new ClickButtonWidget(0, 9, 9, 9, "", this::workfast)
                .setButtonTexture(GuiTextures.BUTTON_CLEAR_GRID)
                .setTooltipText("超频"));
        group.addWidget(new ClickButtonWidget(9, 9, 9, 9, "", this::autocare)
                .setButtonTexture(GuiTextures.BUTTON_LOCK)
                .setTooltipText("高温强制停机"));
        return group;
    }
    private void autocare(Widget.ClickData clickData) {
        care=!care;

    }
    private void workfast(Widget.ClickData clickData) {
        open=!open;

    }
    private void decrementThreshold(Widget.ClickData clickData) {
        this.rate = MathHelper.clamp(rate-1, 1, 10);

    }
    private void incrementThreshold(Widget.ClickData clickData) {

        this.rate = MathHelper.clamp(rate+1, 1, 10);

    }
    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("AOAAAOA", "A  A  A", "A BBB A", "AABBBAA", "A BBB A", "A  A  A", "AIAAAIA")
                .aisle("O  A  O", "   B   ", "  BBB  ", "ABBBBBA", "  BBB  ", "   B   ", "I  A  I")
                .aisle("A BBB A", "  BBB  ", "BB   BB", "BB   BB", "BB   BB", "  BBB  ", "A BBB A")
                .aisle("AABBBAA", "ABBBBBA", "BB   BB", "BB   BB", "BB   BB", "ABBBBBA", "AABBBAA")
                .aisle("A BBB A", "  BBB  ", "BB   BB", "BB   BB", "BB   BB", "  BBB  ", "A BBB A")
                .aisle("O  A  O", "   B   ", "  BBB  ", "ABBBBBA", "  BBB  ", "   B   ", "I  A  I")
                .aisle("AOAAAOA", "A  A  A", "A BBB A", "AABSBAA", "A BBB A", "A  A  A", "AIAAAIA")
                .where('S', selfPredicate())
                .where('A', states(getCasingState1()))
                .where('B', states(getCasingState2()).setMinGlobalLimited(100)
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxLayerLimited(2).setMinGlobalLimited(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxLayerLimited(2).setMinGlobalLimited(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMaxLayerLimited(2).setMinGlobalLimited(1))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1)))
                .where('I', states(getCasingState1()).or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxLayerLimited(8).setMinGlobalLimited(1)))
                .where('O', states(getCasingState1()).or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxLayerLimited(8).setMinGlobalLimited(1)))
                .where(' ', any())
                .build();
    }

    private static IBlockState getCasingState1() {
        return GTQTMetaBlocks.NUCLEAR_FUSION.getState(GTQTNuclearFusion.CasingType.NUCLEAR_FUSION_FRAME);
    }

    private static IBlockState getCasingState2() {
        return GTQTMetaBlocks.NUCLEAR_FUSION.getState(GTQTNuclearFusion.CasingType.NUCLEAR_FUSION_CASING);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.NUCLEAR_FUSION_CASING;
    }

    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public int getNumProgressBars() {
        return 1;
    }


    @Override
    public double getFillPercentage(int index) {
        return  (double) heat /6000;
    }

    @Override
    public TextureArea getProgressBarTexture(int index) {
        return GuiTextures.PROGRESS_BAR_HPCA_COMPUTATION;
    }

    @Override
    public void addBarHoverText(List<ITextComponent> hoverList, int index) {
        ITextComponent cwutInfo = TextComponentUtil.stringWithColor(
                TextFormatting.AQUA,
                heat+ " / " + 6000 + " K");
        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "临界温度 %s",
                cwutInfo));
    }

}
