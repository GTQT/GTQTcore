package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.ICubeRenderer;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.IPHValue;
import keqing.gtqtcore.api.metaileentity.GTQTNoTierMultiblockController;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.EnzymesReactionProperty;
import keqing.gtqtcore.api.utils.EnzymesUtils;
import keqing.gtqtcore.api.utils.GTQTMathUtil;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class MetaTileEntityEnzymesReaction extends GTQTNoTierMultiblockController implements IPHValue {
    int p;
    int A;
    int B;
    int C;
    int D;
    int E;
    boolean start;
    private int glass_tier;
    private int clean_tier;
    private int tubeTier;
    private double PH = 7;

    public MetaTileEntityEnzymesReaction(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.ENZYMES_REACTION_RECIPES
        });
        this.recipeMapWorkable = new BiologicalReactionLogic(this);

        //setMaxParallel(auto);
        setMaxParallelFlag(true);
        //setTimeReduce(auto);
        setTimeReduceFlag(true);
        setOverclocking(3.0);
    }


    @Override
    public boolean canBeDistinct() {
        return true;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setBoolean("start", start);

        data.setDouble("PH", PH);
        data.setInteger("A", A);
        data.setInteger("B", B);
        data.setInteger("C", C);
        data.setInteger("D", D);
        data.setInteger("E", E);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        start = data.getBoolean("start");

        PH = data.getDouble("PH");
        A = data.getInteger("A");
        B = data.getInteger("B");
        C = data.getInteger("C");
        D = data.getInteger("D");
        E = data.getInteger("E");
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityEnzymesReaction(metaTileEntityId);
    }

    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 18, 9, "", this::clear)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("我不要了！"));
        group.addWidget(new ClickButtonWidget(0, 9, 18, 9, "", this::work)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("开始加工！"));
        return group;
    }

    private void clear(Widget.ClickData clickData) {
        PH = 7D;
        A = B = C = D = E = 0;
    }

    private void work(Widget.ClickData clickData) {
        start = !start;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object glass_tier = context.get("LGLTieredStats");
        Object clean_tier = context.get("ZJTieredStats");
        Object tubeTier = context.get("ChemicalPlantTubeTieredStats");
        this.tubeTier = GTQTUtil.getOrDefault(() -> tubeTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) tubeTier).getIntTier(),
                0);
        this.glass_tier = GTQTUtil.getOrDefault(() -> glass_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) glass_tier).getIntTier(),
                0);
        this.clean_tier = GTQTUtil.getOrDefault(() -> clean_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) clean_tier).getIntTier(),
                0);

        setMaxParallel(1);
        setTimeReduce(((10 - this.clean_tier) / 10.0));
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("JCCCJ", "JCCCJ", "GGGGG", "JCCCJ", "JCCCJ")
                .aisle("JCCCJ", "JPPPJ", "G   G", "JPPPJ", "JCCCJ")
                .aisle("JCCCJ", "JPPPJ", "G   G", "JPPPJ", "JCCCJ")
                .aisle("JCCCJ", "JPPPJ", "G   G", "JPPPJ", "JCCCJ")
                .aisle("JCCCJ", "JCSCJ", "GGGGG", "JCCCJ", "JCCCJ")
                .where('S', selfPredicate())
                .where('J', TiredTraceabilityPredicate.CP_ZJ_CASING.get())
                .where('G', TiredTraceabilityPredicate.CP_LGLASS.get())
                .where('C', states(getCasingState()).setMinGlobalLimited(32)
                        .or(autoAbilities())
                        .or(abilities(GTQTMultiblockAbility.BUFFER_MULTIBLOCK_ABILITY).setExactLimit(1))
                )
                .where('P', TiredTraceabilityPredicate.CP_TUBE.get())
                .where(' ', any())
                .build();
    }

    protected IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing1.getState(BlockMultiblockCasing1.CasingType.Talonite);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.Talonite;
    }


    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);

        textList.add(new TextComponentTranslation("gtqtcore.multiblock.br.3", glass_tier, tubeTier));
        if (PH > 8)
            textList.add(new TextComponentTranslation("Ph:%s 碱性 状态：%s", PH, start));
        if (PH < 8 && PH > 6)
            textList.add(new TextComponentTranslation("Ph:%s 中性 状态：%s", PH, start));
        if (PH < 6)
            textList.add(new TextComponentTranslation("Ph:%s 酸性 状态：%s", PH, start));

        if (getEnzymes() > 0 && getRare(getEnzymes()))
            textList.add(new TextComponentTranslation("%s 组合因子：%s %s %s %s %s 找到配方: " + EnzymesUtils.getName(getEnzymes()), getEnzymes(), A, B, C, D, E));
        else textList.add(new TextComponentTranslation("组合因子：%s %s %s %s %s 找不到可行配方！", A, B, C, D, E));
    }

    //这里注册菌种
    public int getEnzymes() {
        //普适矿处菌种 101
        if (cE(1, 0, 1, 0, 0)) return 101;
        //定向铂系菌种 102
        if (cE(1, 0, 1, 1, 0)) return 102;
        //普适魔性菌种 103
        if (cE(1, 1, 0, 1, 0)) return 103;
        //普适副产菌种 104
        if (cE(1, 1, 0, 1, 1)) return 104;
        //
        //工业合成菌种I 201
        if (cE(2, 1, 1, 3, 1)) return 201;
        //工业还原菌种 202
        if (cE(1, 2, 3, 1, 1)) return 202;
        //工业氧化菌种 203
        if (cE(1, 3, 2, 1, 1)) return 203;
        //工业催化菌种 204
        if (cE(2, 1, 1, 3, 1)) return 204;
        //
        //定向脂肪酶 301
        if (cE(4, 1, 1, 3, 2)) return 301;
        //普适发酵酶 302
        if (cE(2, 4, 2, 3, 1)) return 302;
        //定向发酵酶 303
        if (cE(2, 3, 2, 4, 1)) return 303;
        //
        //活性诱变酶 401
        if (cE(2, 5, 2, 4, 3)) return 401;

        return 0;
    }

    public boolean getRare(int n) {
        if (PH < 4 || PH > 10) return false;
        if (n < 200) return PH <= 6;
        if (n < 300) return PH >= 8;
        if (n < 400) return PH <= 6;
        if (n < 500) return PH >= 8;
        return false;
    }


    public boolean cE(int a, int b, int c, int d, int e) {
        int k = A / a;
        if (k > 0 && A % a == 0 && B == k * b && C == k * c && D == k * d && E == k * e) {
            p = k;
            return true;
        }
        return false;
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        int number = recipe.getProperty(EnzymesReactionProperty.getInstance(), 0);
        if (getEnzymes() == number)
            if (getRare(number))
                return super.checkRecipe(recipe, consumeIfSuccess);
        return false;
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.PH = 7D;
        this.markDirty();
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.biorea.gtqtupdate.1"));
        tooltip.add(I18n.format("gregtech.machine.bioreb.gtqtupdate.2"));
        tooltip.add(I18n.format("gregtech.machine.bioreb.gtqtupdate.3"));
        tooltip.add(I18n.format("gregtech.machine.bioreb.gtqtupdate.4"));
    }

    @Override
    public double getCurrentPHValue() {
        return this.PH;
    }

    @Override
    public void changeCurrentPHValue(double ph_change) {
        this.PH = GTQTMathUtil.clamp(PH + ph_change, 0, 14);
        this.markDirty();
    }

    @Override
    public void changeCurrentPHValue(double ph_change, double ph_change_limit) {
        if (ph_change > 0) {
            double ph = this.PH + ph_change;
            this.PH = Math.min(ph, ph_change_limit);
        } else {
            double ph = this.PH + ph_change;
            this.PH = Math.max(ph, ph_change_limit);
        }
        this.markDirty();
    }

    protected class BiologicalReactionLogic extends GTQTMultiblockLogic {

        FluidStack BIO1 = Enzymesa.getFluid(1000);
        FluidStack BIO2 = Enzymesb.getFluid(1000);
        FluidStack BIO3 = Enzymesc.getFluid(1000);
        FluidStack BIO4 = Enzymesd.getFluid(1000);
        FluidStack BIO5 = Enzymese.getFluid(1000);

        public BiologicalReactionLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public void update() {
            IMultipleTankHandler inputTank = getInputFluidInventory();
            if (BIO1.isFluidStackIdentical(inputTank.drain(BIO1, false))) {
                inputTank.drain(BIO1, true);
                A = A + 1;

            }
            if (BIO2.isFluidStackIdentical(inputTank.drain(BIO2, false))) {
                inputTank.drain(BIO2, true);
                B = B + 1;

            }
            if (BIO3.isFluidStackIdentical(inputTank.drain(BIO3, false))) {
                inputTank.drain(BIO3, true);
                C = C + 1;
            }
            if (BIO4.isFluidStackIdentical(inputTank.drain(BIO4, false))) {
                inputTank.drain(BIO4, true);
                D = D + 1;

            }
            if (BIO5.isFluidStackIdentical(inputTank.drain(BIO5, false))) {
                inputTank.drain(BIO5, true);
                E = E + 1;
            }
            setMaxParallel(Math.max(clean_tier, p));
        }

        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true) && start && getEnzymes() != 0) {
                this.drawEnergy(this.recipeEUt, false);
                if (++this.progressTime > this.maxProgressTime) {
                    this.completeRecipe();
                    changeCurrentPHValue(Math.signum(7 - PH) * 0.125 * p);
                    A = B = C = D = E = 0;
                }
            }
        }
    }
}
