package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.IProgressBarMultiblock;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metaileentity.GTQTRecipeMapMultiblockController;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.BRProperty;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class MetaTileEntityEnzymesReaction extends GTQTRecipeMapMultiblockController implements IProgressBarMultiblock {
    public MetaTileEntityEnzymesReaction(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[] {
                GTQTcoreRecipeMaps.ENZYMES_REACTION_RECIPES
        });
        this.recipeMapWorkable = new BiologicalReactionLogic(this);
    }
    private int glass_tier;
    private int clean_tier;
    private int tubeTier;

    int bio=0;
    double rate=0;


    int water;
    int s;
    int j;
    int A;
    int B;
    int C;
    int D;
    int E;
    boolean start;
    @Override
    public int getNumProgressBars() {
        return 2;
    }
    @Override
    public double getFillPercentage(int index) {
            return index == 0 ?  (rate-4)/6 :
                    bio / 3200.0;
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
                    4+" / " + rate + " / " + 10 + "PH");
        } else {
            cwutInfo = TextComponentUtil.stringWithColor(
                    TextFormatting.AQUA,
                    bio + " / " + 3200 + " P");
        }
        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "gregtech.multiblock.pb.computation",
                cwutInfo));
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setBoolean("start", start);

        data.setInteger("water", water);
        data.setInteger("s", s);
        data.setInteger("j", j);

        data.setInteger("bio", bio);
        data.setDouble("rate", rate);
        data.setInteger("A", A);
        data.setInteger("B", B);
        data.setInteger("C", C);
        data.setInteger("D", D);
        data.setInteger("E", E);
        return super.writeToNBT(data);
    }
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        start = data.getBoolean("start");

        water = data.getInteger("water");
        s = data.getInteger("s");
        j = data.getInteger("j");

        bio = data.getInteger("bio");
        rate = data.getDouble("rate");
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
        bio=0;
        rate=0;
        water=0;
        s=0;
        j=0;
        A=B=C=D=E=0;
    }
    private void work(Widget.ClickData clickData) {
        start=!start;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object glass_tier = context.get("LGLTiredStats");
        Object clean_tier = context.get("ZJTiredStats");
        Object tubeTier = context.get("ChemicalPlantTubeTiredStats");
        this.tubeTier = GTQTUtil.getOrDefault(() -> tubeTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)tubeTier).getIntTier(),
                0);
        this.glass_tier = GTQTUtil.getOrDefault(() -> glass_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)glass_tier).getIntTier(),
                0);
        this.clean_tier = GTQTUtil.getOrDefault(() -> clean_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)clean_tier).getIntTier(),
                0);
    }
    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("JCCCJ","JCCCJ", "GGGGG", "JCCCJ","JCCCJ")
                .aisle("JCCCJ","JPPPJ", "G   G", "JPPPJ","JCCCJ")
                .aisle("JCCCJ","JPPPJ", "G   G", "JPPPJ","JCCCJ")
                .aisle("JCCCJ","JPPPJ", "G   G", "JPPPJ","JCCCJ")
                .aisle("JCCCJ","JCSCJ", "GGGGG", "JCCCJ","JCCCJ")
                .where('S', selfPredicate())
                .where('J', TiredTraceabilityPredicate.CP_ZJ_CASING)
                .where('G', TiredTraceabilityPredicate.CP_LGLASS)
                .where('C', states(getCasingState()).setMinGlobalLimited(32).or(autoAbilities()))
                .where('P', TiredTraceabilityPredicate.CP_TUBE)
                .where(' ', any())
                .build();
    }

    private static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.CLEAN_STAINLESS_STEEL_CASING;
    }
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);

        textList.add(new TextComponentTranslation("gtqtcore.multiblock.br.3",glass_tier,tubeTier));
        if(rate>8)
            textList.add(new TextComponentTranslation("Rate:%s Ph:%s 碱性 状态：%s",bio,(int)rate,start));
        if(rate<8&&rate>6)
            textList.add(new TextComponentTranslation("Rate:%s Ph:%s 中性 状态：%s",bio,(int)rate,start));
        if(rate<6)
            textList.add(new TextComponentTranslation("Rate:%s Ph:%s 酸性 状态：%s",bio,(int)rate,start));

        if(getEnzymes()>0&&getRare(getEnzymes()))
        textList.add(new TextComponentTranslation("%s 组合因子：%s %s %s %s %s 找到配方: "+getName(getEnzymes()),getEnzymes(),A,B,C,D,E));
        else textList.add(new TextComponentTranslation("组合因子：%s %s %s %s %s 找不到可行配方！",A,B,C,D,E));
    }

    //这里注册菌种
    public int getEnzymes()
    {
        //普适矿处菌种 101
        if(cE(1,0,1,0,0))return 101;
        //定向铂系菌种 102
        if(cE(1,0,1,1,0))return 102;
        //普适魔性菌种 103
        if(cE(1,1,0,1,0))return 103;
        //普适副产菌种 104
        if(cE(1,1,0,1,1))return 104;
        //
        //工业合成菌种I 201
        if(cE(2,1,1,3,1))return 201;
        //工业还原菌种 202
        if(cE(1,2,3,1,1))return 202;
        //工业氧化菌种 203
        if(cE(1,3,2,1,1))return 203;
        //工业催化菌种 204
        if(cE(2,1,1,3,1))return 204;
        //
        //定向脂肪酶 301
        if(cE(4,1,1,3,2))return 301;
        //普适发酵酶 302
        if(cE(2,4,2,3,1))return 302;
        //定向发酵酶 303
        if(cE(2,3,2,4,1))return 303;
        //
        //活性诱变酶 401
        if(cE(2,5,2,4,3))return 401;

        return 0;
    }
    public  boolean getRare(int n)
    {
        if(n<200)return rate<=6;
        if(n<300)return rate>=8;
        if(n<400)return rate<=6;
        if(n<500)return rate>=8;
        return false;
    }

    public String getName(int n)
    {
        //普适矿处菌种 101
        //定向铂系菌种 102
        //普适魔性菌种 103
        //普适副产菌种 104
        //
        //工业合成菌种I 201
        //工业还原菌种 202
        //工业氧化菌种 203
        //工业催化菌种 204
        //
        //定向脂肪酶 301
        //普适发酵酶 302
        //定向发酵酶 303
        //
        //活性诱变酶 401
        switch (n) {
            case 101 -> {
                return "普适矿处菌种";
            }
            case 102 -> {
                return "定向铂系菌种";
            }
            case 103 -> {
                return "普适魔性菌种";
            }
            case 104 -> {
                return "普适副产菌种";
            }
            case 201 -> {
                return "工业合成菌种I";
            }
            case 202 -> {
                return "工业还原菌种";
            }
            case 203 -> {
                return "工业氧化菌种";
            }
            case 204 -> {
                return "工业催化菌种";
            }
            case 301 -> {
                return "定向脂肪酶";
            }
            case 302 -> {
                return "普适发酵酶";
            }
            case 303 -> {
                return "定向发酵酶";
            }
            case 401 -> {
                return "活性诱变酶";
            }
            default -> {
                return null;
            }
        }
    }
    public boolean cE(int a,int b,int c,int d,int e)
    {
        if(A==a)if(B==b)if(C==c)if(D==d)if(E==e)return true;
        return false;
    }
    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        int number=recipe.getProperty(BRProperty.getInstance(), 0);
        if(getEnzymes()==number)
            if(getRare(number))
                return super.checkRecipe(recipe, consumeIfSuccess);
        return false;
    }
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.biorea.gtqtupdate.1"));
        tooltip.add(I18n.format("gregtech.machine.bioreb.gtqtupdate.2"));
        tooltip.add(I18n.format("gregtech.machine.bioreb.gtqtupdate.3"));
        tooltip.add(I18n.format("gregtech.machine.bioreb.gtqtupdate.4"));
    }
    protected class BiologicalReactionLogic extends MultiblockRecipeLogic {

        public BiologicalReactionLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity,true);
        }
        FluidStack WATER = Water.getFluid(100);
        FluidStack S = HydrochloricAcid.getFluid(100);
        FluidStack J = SodiumHydroxide.getFluid(100);
        FluidStack BIO1 = Enzymesa.getFluid(1000);
        FluidStack BIO2 = Enzymesb .getFluid(1000);
        FluidStack BIO3 = Enzymesc.getFluid(1000);
        FluidStack BIO4 = Enzymesd.getFluid(1000);
        FluidStack BIO5 = Enzymese.getFluid(1000);

        FluidStack BIOE = Rnzymes.getFluid(10);
        @Override
        public void update() {
            IMultipleTankHandler inputTank = getInputFluidInventory();

            if(bio<3000) {
                if (BIOE.isFluidStackIdentical(inputTank.drain(BIOE, false))) {
                    inputTank.drain(BIOE, true);
                    bio=bio+10;
                }
            }

            if (WATER.isFluidStackIdentical(inputTank.drain(WATER, false))) {
                inputTank.drain(WATER, true);
                water++;
            }
            if (S.isFluidStackIdentical(inputTank.drain(S, false))) {
                inputTank.drain(S, true);
                s++;
            }
            if (J.isFluidStackIdentical(inputTank.drain(J, false))) {
                inputTank.drain(J, true);
                j++;
            }

            rate=(float)(4*s+10*j+7*water)/(s+j+water);

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

        }

        protected int getp()
        {
            if(bio>2600)return 2;
            if(bio>2200)return 4;
            if(bio>1800)return 8;
            if(bio>1400)return 4;
            if(bio>800)return 2;
            if(bio>400)return 1;
            return 1;
        }
        @Override
        public int getParallelLimit() {
            return getp()*clean_tier;
        }
        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = (maxProgress*(100-clean_tier*getp())/100);
        }

        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)&&start&&rate>600) {
                this.drawEnergy(this.recipeEUt, false);
                if (++this.progressTime > this.maxProgressTime) {
                    this.completeRecipe();
                    bio=bio*tubeTier/12;
                    s= (int) (s*0.6);
                    j= (int) (j*0.6);
                    A=B=C=D=E=0;
                }

                if (this.hasNotEnoughEnergy && this.getEnergyInputPerSecond() > 19L * (long)this.recipeEUt) {
                    this.hasNotEnoughEnergy = false;
                }
            } else if (this.recipeEUt > 0) {
                this.hasNotEnoughEnergy = true;
                this.decreaseProgress();
            }

        }
    }
}
