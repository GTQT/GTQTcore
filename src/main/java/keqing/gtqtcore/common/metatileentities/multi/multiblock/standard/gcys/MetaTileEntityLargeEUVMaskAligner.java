package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.gui.Widget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.metatileentity.multiblock.ui.KeyManager;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIBuilder;
import gregtech.api.metatileentity.multiblock.ui.UISyncer;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.api.util.KeyUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.impl.GCYMComputationRecipeLogic;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.LaserNetProperty;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate.CP_PAF_CASING;
import static keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate.CP_PAV_CASING;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class MetaTileEntityLargeEUVMaskAligner extends GCYMRecipeMapMultiblockController implements IOpticalComputationReceiver {
    int LaserKind;
    int LaserAmount;
    private int PAF;
    private int PAV;

    private IOpticalComputationProvider computationProvider;

    public MetaTileEntityLargeEUVMaskAligner(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{GTQTcoreRecipeMaps.STEPPER_RECIPES});
        this.recipeMapWorkable = new LaserEngravingWorkableHandler(this);
    }

    private static IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.NQ_TURBINE_CASING);
    }

    @Override
    public boolean onScrewdriverClick(EntityPlayer playerIn,
                                      EnumHand hand,
                                      EnumFacing facing,
                                      CuboidRayTraceResult hitResult) {
        return outputlaser();
    }

    private boolean outputlaser() {
        if (LaserKind == 1) this.getOutputFluidInventory().fill(HydrogenSilsesquioxane.getFluid(LaserAmount), true);
        if (LaserKind == 2) this.getOutputFluidInventory().fill(Vinylcinnamate.getFluid(LaserAmount), true);
        if (LaserKind == 3) this.getOutputFluidInventory().fill(SU8_Photoresist.getFluid(LaserAmount), true);
        if (LaserKind == 4) this.getOutputFluidInventory().fill(Xmt.getFluid(LaserAmount), true);
        if (LaserKind == 5) this.getOutputFluidInventory().fill(Zrbtmst.getFluid(LaserAmount), true);
        LaserKind = 0;
        LaserAmount = 0;
        return true;
    }


    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, boolean advanced) {
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("谁把我的粒子加速器拆了"));
        tooltip.add(I18n.format("提供不同等级的光刻胶（HSQ Vinylcinnamate SU8_Photoresist Xmt Zrbtmst）获取不同程度的耗时减免"));
        tooltip.add(I18n.format("该耗时减免覆盖GCYM的的耗时减免逻辑"));
        tooltip.add(I18n.format("每工作一轮消耗1000mb光刻胶（无视配方并行数量）"));
        tooltip.add(I18n.format("升级束流器与电磁轭以提升紫外发射等级（加法计算）"));
        super.addInformation(stack, world, tooltip, advanced);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLargeEUVMaskAligner(metaTileEntityId);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("LaserKind", LaserKind);
        data.setInteger("LaserAmount", LaserAmount);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        LaserKind = data.getInteger("LaserKind");
        LaserAmount = data.getInteger("LaserAmount");
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        if (recipe.getProperty(LaserNetProperty.getInstance(), 0) <= (PAF + PAV)) {
            return super.checkRecipe(recipe, consumeIfSuccess);
        }
        return false;
    }

    @Override
    protected void configureDisplayText(MultiblockUIBuilder builder) {
        builder.setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(this.getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addCustom(this::addHeatCapacity)
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgress(), recipeMapWorkable.getMaxProgress())
                .addRecipeOutputLine(recipeMapWorkable);
    }

    private void addHeatCapacity(KeyManager keyManager, UISyncer syncer) {
        if (isStructureFormed()) {
            keyManager.add(KeyUtil.lang(TextFormatting.GRAY,
                    "束流器等级：%s 电磁轭等级：%s", syncer.syncInt(PAF), syncer.syncInt(PAV)));
            keyManager.add(KeyUtil.lang(TextFormatting.GRAY,
                    "光刻胶等级：%s 光刻胶储量：%s", syncer.syncInt(LaserKind), syncer.syncInt(LaserAmount)));
        }
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("    CCC  ", "    CGC  ", "    CGC  ", "    CGC  ", "    CGC  ", "    CCC  ")
                .aisle("   CCCCC ", "   CX#XC ", "   C###C ", "   C###C ", "   CX#XC ", "   CCCCC ")
                .aisle("CCCCCCCCC", "CCCX###XC", "CCC#####C", "  C#####C", "  CX###XC", "  CCCCCCC")
                .aisle("CCCCCCCCC", "CCC##Q##G", "CCC##Q##G", "  C##Q##G", "  C##Q##G", "  CCCCCCC")
                .aisle("CCCCCCCCC", "CSCX###XC", "CCC#####C", "  C#####C", "  CX###XC", "  CCCCCCC")
                .aisle("   CCCCC ", "   CX#XC ", "   C###C ", "   C###C ", "   CX#XC ", "   CCCCC ")
                .aisle("    CCC  ", "    CGC  ", "    CGC  ", "    CGC  ", "    CGC  ", "    CCC  ")
                .where('S', this.selfPredicate())
                .where('C', states(getCasingState()).setMinGlobalLimited(79)
                        .or(autoAbilities())
                        .or(abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1))
                )
                .where('Q', CP_PAV_CASING.get())
                .where('X', CP_PAF_CASING.get())
                .where('G', states(getGlassState()))
                .where('#', air())
                .where(' ', any())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.NQ_CASING;
    }


    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
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

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        List<IOpticalComputationHatch> providers = this.getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
        if (providers != null && providers.size() >= 1) {
            this.computationProvider = providers.get(0);
        }

        Object PAF = context.get("PAFTieredStats");
        this.PAF = GTQTUtil.getOrDefault(() -> PAF instanceof WrappedIntTired,
                () -> ((WrappedIntTired) PAF).getIntTier(), 0);

        Object PAV = context.get("PAVTieredStats");
        this.PAV = GTQTUtil.getOrDefault(() -> PAV instanceof WrappedIntTired,
                () -> ((WrappedIntTired) PAV).getIntTier(), 0);
    }


    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }


    protected class LaserEngravingWorkableHandler extends GCYMComputationRecipeLogic {
        //光刻胶等级
        FluidStack LASER1 = HydrogenSilsesquioxane.getFluid(1000);
        FluidStack LASER2 = Vinylcinnamate.getFluid(1000);
        FluidStack LASER3 = SU8_Photoresist.getFluid(1000);
        FluidStack LASER4 = Xmt.getFluid(1000);
        FluidStack LASER5 = Zrbtmst.getFluid(1000);

        public LaserEngravingWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity, ComputationType.SPORADIC);
        }

        public void addLaserAmount(int n) {
            if (LaserKind == n) LaserAmount += 1000;
            if (LaserKind == 0 || LaserAmount == 0) {
                LaserAmount = 1000;
                LaserKind = n;
            }
        }

        @Override
        protected void updateRecipeProgress() {
            if (this.recipeCWUt == 0) {
                super.updateRecipeProgress();
            } else {
                if (LaserAmount < 1000) return;

                if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
                    this.drawEnergy(this.recipeEUt, false);

                    IOpticalComputationProvider provider = this.getComputationProvider();
                    int availableCWUt = provider.requestCWUt(Integer.MAX_VALUE, true);
                    if (availableCWUt >= this.recipeCWUt) {
                        this.hasNotEnoughComputation = false;
                        if (this.isDurationTotalCWU) {
                            this.currentDrawnCWUt = provider.requestCWUt(availableCWUt, false);
                            this.progressTime += this.currentDrawnCWUt;
                        } else {
                            provider.requestCWUt(this.recipeCWUt, false);
                            ++this.progressTime;
                        }
                        if (this.progressTime > this.maxProgressTime) {

                            if (LaserAmount >= 1000) {
                                LaserAmount -= 1000;
                                this.completeRecipe();
                            }
                        }
                    } else {
                        this.currentDrawnCWUt = 0;
                        this.hasNotEnoughComputation = true;
                        if (this.type == ComputationType.STEADY) {
                            this.decreaseProgress();
                        }
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

        public void setMaxProgress(int maxProgress) {
            if (LaserKind == 0) this.maxProgressTime = maxProgress;
            else this.maxProgressTime = maxProgress / LaserKind;
        }

        @Override
        public void update() {
            super.update();
            IMultipleTankHandler inputTank = getInputFluidInventory();
            if (LaserAmount < 64000) {
                if (LASER1.isFluidStackIdentical(inputTank.drain(LASER1, false))) {
                    inputTank.drain(LASER1, true);
                    addLaserAmount(1);
                }
                if (LASER2.isFluidStackIdentical(inputTank.drain(LASER2, false))) {
                    inputTank.drain(LASER2, true);
                    addLaserAmount(2);
                }
                if (LASER3.isFluidStackIdentical(inputTank.drain(LASER3, false))) {
                    inputTank.drain(LASER3, true);
                    addLaserAmount(3);
                }
                if (LASER4.isFluidStackIdentical(inputTank.drain(LASER4, false))) {
                    inputTank.drain(LASER4, true);
                    addLaserAmount(4);
                }
                if (LASER5.isFluidStackIdentical(inputTank.drain(LASER5, false))) {
                    inputTank.drain(LASER5, true);
                    addLaserAmount(5);
                }
            }
        }
    }
}