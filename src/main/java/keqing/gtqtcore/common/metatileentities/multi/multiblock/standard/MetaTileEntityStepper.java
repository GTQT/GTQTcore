package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;


import codechicken.lib.raytracer.CuboidRayTraceResult;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.metatileentity.multiblock.ui.KeyManager;
import gregtech.api.metatileentity.multiblock.ui.UISyncer;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.KeyUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metatileentity.GTQTOCMultiblockController;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.LaserNetProperty;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
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

import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class MetaTileEntityStepper extends GTQTOCMultiblockController {
    int LaserKind;
    int LaserAmount;
    private int glass_tier;
    private int clean_tier;
    private int radio_tier;
    private int laser_tier;
    private int casing_tier;
    private IOpticalComputationProvider computationProvider;
    public MetaTileEntityStepper(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{GTQTcoreRecipeMaps.STEPPER_RECIPES});
        this.recipeMapWorkable = new LaserEngravingWorkableHandler(this);

        setTierFlag(true);
        //setTier(auto);
        setMaxParallel(1);//初始化
        setMaxParallelFlag(true);
        //setMaxVoltage(auto);
        setMaxVoltageFlag(true);
        setTimeReduce(1);//初始化
        setTimeReduceFlag(true);
        setOverclocking(3);
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
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("突破原子极限"));
        tooltip.add(I18n.format("gtqt.machine.stepper.1"));
        tooltip.add(I18n.format("gtqt.machine.stepper.2"));
        tooltip.add(I18n.format("gtqt.machine.stepper.3"));
        tooltip.add(I18n.format("gtqt.machine.stepper.4"));
        tooltip.add(I18n.format("gtqt.machine.stepper.5"));
        tooltip.add(TextFormatting.GOLD + I18n.format("试图切换光刻胶种类时请先退出缓存，否则不会替换已有光刻胶"));
        tooltip.add(TextFormatting.GOLD + I18n.format("使用螺丝刀右键控制器使多方块退出缓存光刻胶"));
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityStepper(metaTileEntityId);
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GTQTValue.UPDATE_TIER11) {
            this.casing_tier = buf.readInt();
        }
        if (dataId == GTQTValue.REQUIRE_DATA_UPDATE11) {
            this.writeCustomData(GTQTValue.UPDATE_TIER11, buf1 -> buf1.writeInt(this.casing_tier));
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("casing_tier", casing_tier);
        data.setInteger("LaserKind", LaserKind);
        data.setInteger("LaserAmount", LaserAmount);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        casing_tier = data.getInteger("casing_tier");
        LaserKind = data.getInteger("LaserKind");
        LaserAmount = data.getInteger("LaserAmount");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.casing_tier);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.casing_tier = buf.readInt();
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        if (recipe.getProperty(LaserNetProperty.getInstance(), 0) <= laser_tier) {
            return super.checkRecipe(recipe, consumeIfSuccess);
        }
        return false;
    }


    @Override
    public void addCustomData(KeyManager keyManager, UISyncer syncer) {
        super.addCustomData(keyManager, syncer);
        if (isStructureFormed()){
            keyManager.add(KeyUtil.lang(TextFormatting.GRAY ,"外壳等级：%s 紫外等级：%s 玻璃等级：%s ", syncer.syncInt(casing_tier), syncer.syncInt(laser_tier), syncer.syncInt(glass_tier)));
            keyManager.add(KeyUtil.lang(TextFormatting.GRAY ,"洁净等级：%s 射频调节器等级：%s", syncer.syncInt(clean_tier), syncer.syncInt(radio_tier)));
            keyManager.add(KeyUtil.lang(TextFormatting.GRAY ,"光刻胶等级：%s 光刻胶储量：%s ", syncer.syncInt(LaserKind), syncer.syncInt(LaserAmount)));
        }
    }
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("JXXXXXX", "JXXXXXX", "JXXGGGX")
                .aisle("JXXXXXX", "JPPZZZX", "JXXGGGX")
                .aisle("JXXXXXX", "JCSGGGX", "JXXGGGX")
                .where('S', selfPredicate())
                .where('C', abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION))
                .where('X', TiredTraceabilityPredicate.CP_CASING.get().setMinGlobalLimited(24)
                        .or(autoAbilities()))
                .where('Z', TiredTraceabilityPredicate.CP_ZW_CASING.get())
                .where('G', TiredTraceabilityPredicate.CP_LGLASS.get())
                .where('J', TiredTraceabilityPredicate.CP_ZJ_CASING.get())
                .where('P', TiredTraceabilityPredicate.CP_TJ_CASING.get())
                .where('#', air())
                .build();
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        switch (this.casing_tier) {
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
        Object laser_tier = context.get("ZWTieredStats");
        Object tier = context.get("ChemicalPlantCasingTieredStats");
        Object glass_tier = context.get("LGLTieredStats");
        Object clean_tier = context.get("ZJTieredStats");
        Object sheping_tier = context.get("TJTieredStats");

        this.laser_tier = GTQTUtil.getOrDefault(() -> laser_tier instanceof WrappedIntTired, () -> ((WrappedIntTired) laser_tier).getIntTier(), 0);
        this.casing_tier = GTQTUtil.getOrDefault(() -> tier instanceof WrappedIntTired, () -> ((WrappedIntTired) tier).getIntTier(), 0);
        this.glass_tier = GTQTUtil.getOrDefault(() -> glass_tier instanceof WrappedIntTired, () -> ((WrappedIntTired) glass_tier).getIntTier(), 0);
        this.clean_tier = GTQTUtil.getOrDefault(() -> clean_tier instanceof WrappedIntTired, () -> ((WrappedIntTired) clean_tier).getIntTier(), 0);
        this.radio_tier = GTQTUtil.getOrDefault(() -> sheping_tier instanceof WrappedIntTired, () -> ((WrappedIntTired) sheping_tier).getIntTier(), 0);

        setTier(Math.min(this.casing_tier, this.glass_tier));
        setMaxVoltage(Math.min(this.casing_tier, this.clean_tier * 2));

        this.writeCustomData(GTQTValue.UPDATE_TIER11, buf -> buf.writeInt(this.casing_tier));
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


    protected class LaserEngravingWorkableHandler extends GTQTOCMultiblockLogic {
        //光刻胶等级
        FluidStack LASER1 = HydrogenSilsesquioxane.getFluid(1000);
        FluidStack LASER2 = Vinylcinnamate.getFluid(1000);
        FluidStack LASER3 = SU8_Photoresist.getFluid(1000);
        FluidStack LASER4 = Xmt.getFluid(1000);
        FluidStack LASER5 = Zrbtmst.getFluid(1000);

        public LaserEngravingWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
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

                            if (LaserAmount >=1000) {
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
            if (LaserKind >= laser_tier) {
                setTimeReduce((100 - (LaserKind + radio_tier) * 5.0) / 100);
                setMaxParallel(4 * clean_tier * laser_tier);
            } else {
                setTimeReduce(1);
                setMaxParallel(clean_tier + laser_tier);
            }
        }
    }
}