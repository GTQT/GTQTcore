package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;


import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.capability.impl.ComputationRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.LASERNetProperty;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static gregtech.api.GTValues.VA;

public class MetaTileEntityNanoCoating extends MultiMapMultiblockController implements IOpticalComputationReceiver {
    private int glass_tier;
    private int clean_tier;
    private int sheping_tier;
    private int laser_tier;
    private int tier;
    private int minvisa;
    private boolean visa;


    //设备：纳米喷涂器 机制（同光刻 追加效能模式，模式下并行*2 耗时/2 限制电压为正常的-2）
    // 配方：3D打印
    // 精密喷涂（用于加工透镜玻璃 或者其他需要覆膜的精密材料 算力配方）
    // 精密喷丝（编制滤网 催化剂框架 激光型算力配方）

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gtqt.machine.stepper.1"));
        tooltip.add(I18n.format("gtqt.machine.stepper.2"));
        tooltip.add(I18n.format("gtqt.machine.stepper.3"));
        tooltip.add(I18n.format("gtqt.machine.stepper.4"));
        tooltip.add(I18n.format("gtqt.machine.stepper.5"));
    }
    private IOpticalComputationProvider computationProvider;
    public MetaTileEntityNanoCoating(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.TD_PRINT_RECIPES,
                GTQTcoreRecipeMaps.AUTO_CHISEL_RECIPES,
                GTQTcoreRecipeMaps.PRECISION_SPRAYING,
                GTQTcoreRecipeMaps.PRECISION_SPINNING
        });
        this.recipeMapWorkable = new LaserEngravingWorkableHandler(this);
    }

    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 18, "", this::decrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("效能模式"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 18, "", this::incrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("正常模式"));
        return group;
    }

    private void incrementThreshold(Widget.ClickData clickData) {
            this.visa = true;
    }

    private void decrementThreshold(Widget.ClickData clickData) {
            this.visa = false;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityNanoCoating(metaTileEntityId);
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == GTQTValue.UPDATE_TIER){
            this.tier = buf.readInt();
        }
        if(dataId == GTQTValue.REQUIRE_DATA_UPDATE){
            this.writeCustomData(GTQTValue.UPDATE_TIER,buf1 -> buf1.writeInt(this.tier));
        }
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("tier", tier);
        data.setBoolean("visa", visa);
        return super.writeToNBT(data);
    }
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        tier = data.getInteger("tier");
        visa = data.getBoolean("visa");
    }
    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.tier);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.tier = buf.readInt();
    }
    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        if(recipe.getProperty(LASERNetProperty.getInstance(), 0)>=minvisa)
        {
            return super.checkRecipe(recipe, consumeIfSuccess);
        }
        return false;
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("gtqtcore.eleTire2",tier, laser_tier, glass_tier));
        textList.add(new TextComponentTranslation("gtqtcore.eleTire1",clean_tier, sheping_tier, minvisa));
        textList.add(new TextComponentTranslation("效能模式 ：%s",visa));

    }
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("JXXXXXXXXJ", "JXXGGGGXXJ","JXXGGGGXXJ", "JXXGGGGXXJ")
                .aisle("JXXXXXXXXJ", "JZZPPPPZZJ","JZZ####ZZJ", "JXXGGGGXXJ")
                .aisle("JXXXXXXXXJ", "JZZPPPPZZJ","JZZ####ZZJ", "JXXGGGGXXJ")
                .aisle("JXXXXXXXXJ", "JCSGGGGXXJ","JXXGGGGXXJ", "JXXGGGGXXJ")
                .where('S', selfPredicate())
                .where('C', abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION))
                .where('X', TiredTraceabilityPredicate.CP_CASING.setMinGlobalLimited(40).or(autoAbilities()))
                .where('Z', TiredTraceabilityPredicate.CP_ZW_CASING)
                .where('G', TiredTraceabilityPredicate.CP_LGLASS)
                .where('J', TiredTraceabilityPredicate.CP_ZJ_CASING)
                .where('P', TiredTraceabilityPredicate.CP_TJ_CASING)
                .where('#', air())
                .build();
    }
    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        switch (this.tier) {
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
    public String[] getDescription() {
        return new String[]{I18n.format("gtqt.tooltip.update")};
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
            this.computationProvider = (IOpticalComputationProvider)providers.get(0);
        }
        Object laser_tier = context.get("ZWTiredStats");
        Object tier = context.get("ChemicalPlantCasingTiredStats");
        Object glass_tier = context.get("LGLTiredStats");
        Object clean_tier = context.get("ZJTiredStats");
        Object sheping_tier = context.get("TJTiredStats");

        this.laser_tier = GTQTUtil.getOrDefault(() -> laser_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)laser_tier).getIntTier(),
                0);
        this.tier = GTQTUtil.getOrDefault(() -> tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)tier).getIntTier(),
                0);
        this.glass_tier = GTQTUtil.getOrDefault(() -> glass_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)glass_tier).getIntTier(),
                0);
        this.clean_tier = GTQTUtil.getOrDefault(() -> clean_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)clean_tier).getIntTier(),
                0);
        this.sheping_tier = GTQTUtil.getOrDefault(() -> sheping_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)sheping_tier).getIntTier(),
                0);
        this.writeCustomData(GTQTValue.UPDATE_TIER,buf -> buf.writeInt(this.tier));
    }


    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    protected int getLaserTier() {
        return this.laser_tier;
    }
    protected int getGlass_tier() {
        return this.glass_tier;
    }
    protected int clean_tier() {
        return this.clean_tier;
    }
    protected int sheping_tier() {
        return this.sheping_tier;
    }
    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }


    protected class LaserEngravingWorkableHandler extends ComputationRecipeLogic {
        public LaserEngravingWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity,ComputationType.SPORADIC);
        }

        public long getMaxVoltage() {
            if(visa)return VA[Math.min(tier-3,clean_tier*2-3)];
            else return VA[Math.min(tier,clean_tier*2)];
        }

        @Override
        public void update() {
            super.update();
            switch (laser_tier) {
                case 1 -> {
                    minvisa = 600;
                }
                case 2 -> {
                    minvisa = 200;
                }
                case 3 -> {
                    minvisa = 80;
                }
                case 4 -> {
                    minvisa = 20;
                }
                case 5 -> {
                    minvisa = 10;
                }
            }
        }
        private boolean isPrecise() {
            return sheping_tier == laser_tier;
        }

        int pre;
        public void setMaxProgress(int maxProgress) {
            if(visa)
                this.pre = maxProgress/2;
            else
                this.pre = maxProgress;

            if (isPrecise()) {
                this.maxProgressTime = pre*(100-glass_tier)/100;
            } else {
                this.maxProgressTime = pre;
            }
        }

        @Override
        public int getParallelLimit() {
            if(visa)
                return clean_tier*sheping_tier*4;
            if (isPrecise()) {
                return clean_tier*sheping_tier;
            } else {
                return clean_tier+sheping_tier;
            }
        }
    }
}