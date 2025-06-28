package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.value.sync.IntSyncValue;
import com.cleanroommc.modularui.value.sync.LongSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.IProgressBarMultiblock;
import gregtech.api.metatileentity.multiblock.ProgressBarMultiblock;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.metatileentity.multiblock.ui.KeyManager;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIFactory;
import gregtech.api.metatileentity.multiblock.ui.TemplateBarBuilder;
import gregtech.api.metatileentity.multiblock.ui.UISyncer;
import gregtech.api.mui.GTGuiTextures;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.KeyUtil;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.GTQTCoreConfig;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.IBio;
import keqing.gtqtcore.api.metatileentity.GTQTNoTierMultiblockController;
import keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.properties.BioReactorProperty;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.objmodels.ObjModels;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.UnaryOperator;

import static keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility.BIO_MULTIBLOCK_ABILITY;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.BIOLOGICAL_REACTION_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Pyrotheum;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3.CasingType.tumbaga;

//要实现大机器中的渲染需要重写IFastRenderMetaTileEntity 接口，并实现renderMetaTileEntity和getRenderBoundingBox方法
public class MetaTileEntityBiologicalReaction extends GTQTNoTierMultiblockController implements ProgressBarMultiblock, IFastRenderMetaTileEntity {

    int liquid = 0;
    int bio = 0;
    double rate = 0;
    private int glass_tier;
    private int clean_tier;

    public MetaTileEntityBiologicalReaction(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{BIOLOGICAL_REACTION_RECIPES, RecipeMaps.FERMENTING_RECIPES, RecipeMaps.BREWING_RECIPES});
        this.recipeMapWorkable = new BiologicalReactionLogic(this);

        //setMaxParallel(auto);
        setMaxParallelFlag(true);
        //setTimeReduce(auto);
        setTimeReduceFlag(true);
        setOverclocking(3);
    }

    private static IBlockState getTubeState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE);

    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }


    @Override
    public int getProgressBarCount() {
        return 2;
    }

    @Override
    public void registerBars(List<UnaryOperator<TemplateBarBuilder>> bars, PanelSyncManager syncManager) {
        DoubleSyncValue rate = new DoubleSyncValue(this::getRate);
        syncManager.syncValue("rate", rate);
        DoubleSyncValue bio = new DoubleSyncValue(this::getBio);
        syncManager.syncValue("bio", bio);

        bars.add(barTest -> barTest
                .texture(GTGuiTextures.PROGRESS_BAR_FUSION_HEAT)
                .tooltipBuilder(tooltip -> {
                    IKey heatInfo = KeyUtil.string(TextFormatting.AQUA,
                            "%s / %s",
                            rate.getDoubleValue()*100, 100);
                    tooltip.add(KeyUtil.lang(
                            "生物浓度",
                            heatInfo));
                })
                .progress(() -> rate.getDoubleValue() > 0 ?
                        rate.getDoubleValue()*100 /100 : 0));

        bars.add(barTest -> barTest
                .texture(GTGuiTextures.PROGRESS_BAR_LCE_FUEL)
                .tooltipBuilder(tooltip -> {
                    IKey heatInfo = KeyUtil.string(TextFormatting.AQUA,
                            "%s / %s",
                            bio.getDoubleValue(), 4000);
                    tooltip.add(KeyUtil.lang(
                            "生物质储量",
                            heatInfo));
                })
                .progress(() -> bio.getDoubleValue() > 0 ?
                        bio.getDoubleValue() /4000 : 0));
    }

    private double getBio() {
        return bio;
    }

    private double getRate() {
        return rate;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("liquid", liquid);
        data.setInteger("bio", bio);
        data.setDouble("rate", rate);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        liquid = data.getInteger("liquid");
        bio = data.getInteger("bio");
        rate = data.getDouble("rate");
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityBiologicalReaction(metaTileEntityId);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object glass_tier = context.get("LGLTieredStats");
        Object clean_tier = context.get("ZJTieredStats");

        this.glass_tier = GTQTUtil.getOrDefault(() -> glass_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) glass_tier).getIntTier(),
                0);
        this.clean_tier = GTQTUtil.getOrDefault(() -> clean_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) clean_tier).getIntTier(),
                0);

        setMaxParallel((int) Math.min(Math.pow(2, this.clean_tier + this.glass_tier), 64));
        setTimeReduce((100 - this.glass_tier * 5.0) / 100);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("JCCCJ", "JCCCJ", "GGGGG", "GGGGG", "CCCCC")
                .aisle("JCCCJ", "JPPPJ", "G   G", "G   G", "CCCCC")
                .aisle("JCCCJ", "JPPPJ", "G   G", "G   G", "CCCCC")
                .aisle("JCCCJ", "JPPPJ", "G   G", "G   G", "CCCCC")
                .aisle("JCCCJ", "JCSCJ", "GGGGG", "GGGGG", "CCCCC")
                .where('S', selfPredicate())
                .where('J', TiredTraceabilityPredicate.CP_ZJ_CASING.get())
                .where('G', TiredTraceabilityPredicate.CP_LGLASS.get())
                .where('C', states(getCasingState()).setMinGlobalLimited(35)
                        .or(autoAbilities())
                        .or(abilities(BIO_MULTIBLOCK_ABILITY).setExactLimit(1)))
                .where('P', states(getTubeState()))
                .where(' ', any()).build();
    }

    protected IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing3.getState(tumbaga);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.tumbaga;

    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }


    @Override
    public void addCustomData(KeyManager keyManager, UISyncer syncer) {
        super.addCustomData(keyManager, syncer);

        keyManager.add(KeyUtil.lang(TextFormatting.GRAY ,"gtqtcore.multiblock.br.1" , syncer.syncInt(liquid), syncer.syncInt(bio), syncer.syncDouble(rate)));
        keyManager.add(KeyUtil.lang(TextFormatting.GRAY ,"gtqtcore.multiblock.br.3" , syncer.syncInt(glass_tier), syncer.syncInt(clean_tier)));
    }
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("微生物动物园", new Object[0]));
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.biorea.gtqtupdate.1"));
        tooltip.add(I18n.format("gregtech.machine.biorea.gtqtupdate.2"));
        tooltip.add(I18n.format("gregtech.machine.biorea.gtqtupdate.3"));
        tooltip.add(I18n.format("gregtech.machine.biorea.gtqtupdate.4"));
    }

    //渲染模型的位置
    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(double x, double y, double z, float partialTicks) {
        IFastRenderMetaTileEntity.super.renderMetaTileEntity(x, y, z, partialTicks);
        //机器开启才会进行渲染
        if (isActive() && GTQTCoreConfig.OBJRenderSwitch.EnableObj && GTQTCoreConfig.OBJRenderSwitch.EnableObjBiologicalReaction) {
            //这是一些opengl的操作,GlStateManager是mc自身封装的一部分方法  前四条详情请看 https://turou.fun/minecraft/legacy-render-tutor/
            //opengl方法一般需要成对出现，实际上他是一个状态机，改装状态后要还原  一般情况按照我这些去写就OK
            GlStateManager.pushAttrib(); //保存变换前的位置和角度
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            FMLClientHandler.instance().getClient().getTextureManager().bindTexture(ObjModels.test_pic); //自带的材质绑定 需要传递一个ResourceLocation
            GlStateManager.translate(x, y, z);//translate是移动方法 这个移动到xyz是默认的 不要动
            GlStateManager.translate(0.5, 2, 0.5);//这个是模型有偏差进行位置修正的
            float angle = (System.currentTimeMillis() % 3600) / 10.0f; //我写的随时间变化旋转的角度
            GlStateManager.rotate(90, 0F, 1F, 0F);//rotate是旋转模型的方法  DNA的初始位置不太对 我旋转了一下   四个参数为：旋转角度，xyz轴，可以控制模型围绕哪个轴旋转
            GlStateManager.rotate(angle, 0F, 0F, 1F);//我让dna围绕z轴旋转，角度是实时变化的
            GlStateManager.scale(0.1, 0.1, 0.1);
            //ObjModels.Dna_Model.renderAllWithMtl(); //这个是模型加载器的渲染方法  这是带MTL的加载方式
            //ObjModels.Dna_Model1.renderAll(); //这个是模型加载器的渲染方法  这是不带MTL的加载方式
            GlStateManager.popMatrix();//读取变换前的位置和角度(恢复原状) 下面都是还原状态机的语句
            GlStateManager.enableLighting();
            GlStateManager.popAttrib();
            GlStateManager.enableCull();
        }
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        //这个影响模型的可视范围，正常方块都是 1 1 1，长宽高各为1，当这个方块离线玩家视线后，obj模型渲染会停止，所以可以适当放大这个大小能让模型有更多角度的可视
        return new AxisAlignedBB(getPos(), getPos().add(5, 5, 5));
    }

    @Override
    public void updateFormedValid() {
        super.updateFormedValid();
        if (!isStructureFormed()) return;
        bio = Math.min(this.getBiotHatch().getBioAmount(), 4000);
        liquid = Math.min(this.getBiotHatch().getWaterAmount(), 4000);
        if (liquid == 0) rate = 0;
        else rate = bio * 1.0 / 4000;
    }

    public void consume() {
        if (this.getBiotHatch() != null) {
            this.getBiotHatch().drainBioAmount((int) (bio / 8.0));
            this.getBiotHatch().drainWaterAmount((int) (liquid / 8.0));
        }
    }

    public IBio getBiotHatch() {
        List<IBio> abilities = getAbilities(GTQTMultiblockAbility.BIO_MULTIBLOCK_ABILITY);
        if (abilities.isEmpty())
            return null;
        return abilities.get(0);
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe,
                               boolean consumeIfSuccess) {
        if (!super.checkRecipe(recipe, consumeIfSuccess)) return false;
        if (this.getRecipeMap() != BIOLOGICAL_REACTION_RECIPES) return true;
        else return recipe.getProperty(BioReactorProperty.getInstance(), 0) <= rate * 100;
    }

    protected class BiologicalReactionLogic extends GTQTMultiblockLogic {

        public BiologicalReactionLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        private boolean isBioRecipes() {
            return this.getRecipeMap() == BIOLOGICAL_REACTION_RECIPES;
        }


        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);
                if (++this.progressTime > this.maxProgressTime) {
                    this.completeRecipe();
                    if (isBioRecipes()) consume();

                }

                if (this.hasNotEnoughEnergy && this.getEnergyInputPerSecond() > 19L * this.recipeEUt) {
                    this.hasNotEnoughEnergy = false;
                }
            } else if (this.recipeEUt > 0) {
                this.hasNotEnoughEnergy = true;
                this.decreaseProgress();
            }

        }
    }
}
