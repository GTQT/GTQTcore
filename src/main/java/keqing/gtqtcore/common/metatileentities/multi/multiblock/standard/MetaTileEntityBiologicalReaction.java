package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.GTQTCoreConfig;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.IBio;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.properties.BRProperty;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.objmodels.ObjModels;
import keqing.gtqtcore.client.textures.GTQTTextures;
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
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.BIO_MULTIBLOCK_ABILITY;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.BIOLOGICAL_REACTION_RECIPES;

//要实现大机器中的渲染需要重写IFastRenderMetaTileEntity 接口，并实现renderMetaTileEntity和getRenderBoundingBox方法
public class MetaTileEntityBiologicalReaction extends MultiMapMultiblockController implements IProgressBarMultiblock, IFastRenderMetaTileEntity {
    int liquid = 0;
    int bio = 0;
    double rate = 0;
    private int glass_tier;
    private int clean_tier;

    public MetaTileEntityBiologicalReaction(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{BIOLOGICAL_REACTION_RECIPES, RecipeMaps.FERMENTING_RECIPES, RecipeMaps.BREWING_RECIPES});
        this.recipeMapWorkable = new BiologicalReactionLogic(this);
    }

    private static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);

    }

    private static IBlockState getTubeState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE);

    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 18, 18, "", this::clear).setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS).setTooltipText("我不要了！"));
        return group;
    }


    @Override
    public int getNumProgressBars() {
        return 2;
    }

    @Override
    public double getFillPercentage(int index) {
        if (bio * 1.0 / liquid <= 1) return index == 0 ? rate : bio * 1.0 / liquid;
        else return index == 0 ? rate : 1;
    }

    @Override
    public TextureArea getProgressBarTexture(int index) {
        return GuiTextures.PROGRESS_BAR_LCE_FUEL;
    }

    @Override
    public void addBarHoverText(List<ITextComponent> hoverList, int index) {
        ITextComponent cwutInfo;
        if (index == 0) {
            cwutInfo = TextComponentUtil.stringWithColor(TextFormatting.AQUA, rate * 100 + " / " + 100 + "R");
        } else {
            cwutInfo = TextComponentUtil.stringWithColor(TextFormatting.AQUA, bio + " / " + 4000 + " P");
        }
        hoverList.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "gregtech.multiblock.pb.computation", cwutInfo));
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

    private void clear(Widget.ClickData clickData) {
        this.getBiotHatch().drainBioAmount(bio);
        this.getBiotHatch().drainWaterAmount(liquid);
        rate = 0;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object glass_tier = context.get("LGLTieredStats");
        Object clean_tier = context.get("ZJTieredStats");
        this.glass_tier = GTQTUtil.getOrDefault(() -> glass_tier instanceof WrappedIntTired, () -> ((WrappedIntTired) glass_tier).getIntTier(), 0);
        this.clean_tier = GTQTUtil.getOrDefault(() -> clean_tier instanceof WrappedIntTired, () -> ((WrappedIntTired) clean_tier).getIntTier(), 0);
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
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.MUFFLER_HATCH).setExactLimit(1))
                        .or(abilities(BIO_MULTIBLOCK_ABILITY).setExactLimit(1)))
                .where('P', states(getTubeState()))
                .where(' ', any()).build();
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
        textList.add(new TextComponentTranslation("gtqtcore.multiblock.br.1", liquid, bio, rate));
        textList.add(new TextComponentTranslation("gtqtcore.multiblock.br.3", glass_tier, clean_tier));
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
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
        if (!isStructureFormed()) return;
        bio = Math.min(this.getBiotHatch().getBioAmount(), 4000);
        liquid = Math.min(this.getBiotHatch().getWaterAmount(), 4000);
        if (liquid == 0) rate = 0;
        else rate = bio * 1.0 / 4000;
        super.updateFormedValid();
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
        else return recipe.getProperty(BRProperty.getInstance(), 0) <= rate * 100;
    }

    protected class BiologicalReactionLogic extends MultiblockRecipeLogic {

        public BiologicalReactionLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity, true);
        }

        private boolean isBioRecipes() {
            return this.getRecipeMap() == BIOLOGICAL_REACTION_RECIPES;
        }

        /**
         * @param maxProgress If machine in common assembler, then get half progress time.
         */
        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = maxProgress / glass_tier;
        }

        /**
         * @return If machine in PA, then no parallel, if machine in common assembler, then get 2^{CasingTier + 4} (Mk1:32, Mk2:64, Mk3:128) parallel.
         */
        @Override
        public int getParallelLimit() {
            if (isBioRecipes()) {
                return (int) Math.pow(2, clean_tier);
            } else {
                return (int) Math.pow(2, Math.min(glass_tier, clean_tier) + 4);
            }
        }

        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);
                if (++this.progressTime > this.maxProgressTime) {
                    this.completeRecipe();
                    if (isBioRecipes()) consume();

                }

                if (this.hasNotEnoughEnergy && this.getEnergyInputPerSecond() > 19L * (long) this.recipeEUt) {
                    this.hasNotEnoughEnergy = false;
                }
            } else if (this.recipeEUt > 0) {
                this.hasNotEnoughEnergy = true;
                this.decreaseProgress();
            }

        }
    }
}
