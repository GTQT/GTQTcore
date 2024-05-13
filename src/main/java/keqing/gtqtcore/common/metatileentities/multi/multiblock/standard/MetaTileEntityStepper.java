package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;


import gregtech.api.capability.IMultipleTankHandler;
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
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.KQNetProperty;
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

public class MetaTileEntityStepper extends MultiMapMultiblockController implements IOpticalComputationReceiver {
    private int glass_tier;
    private int clean_tier;
    private int sheping_tier;
    private int laser_tier;
    private int tier;
    private int minvisa;

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gtqt.machine.stepper.1"));
        tooltip.add(I18n.format("gtqt.machine.stepper.2"));
        tooltip.add(I18n.format("gtqt.machine.stepper.3"));
        tooltip.add(I18n.format("gtqt.machine.stepper.4"));
        tooltip.add(I18n.format("gtqt.machine.stepper.5"));
    }
    private IOpticalComputationProvider computationProvider;
    public MetaTileEntityStepper(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.STEPPER_RECIPES
        });
        this.recipeMapWorkable = new LaserEngravingWorkableHandler(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityStepper(metaTileEntityId);
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
        return super.writeToNBT(data);
    }
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        tier = data.getInteger("tier");
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

    }
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("JXXXXXX", "JXXXXXX", "JXXGGGX")
                .aisle("JXXXXXX", "JZZPPPX", "JXXGGGX")
                .aisle("JXXXXXX", "JCSGGGX", "JXXGGGX")
                .where('S', selfPredicate())
                .where('C', abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION))
                .where('X', TiredTraceabilityPredicate.CP_CASING.setMinGlobalLimited(24).or(autoAbilities()))
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

        public void setMaxProgress(int maxProgress) {
            if (isPrecise()) {
                this.maxProgressTime = maxProgress*(100-glass_tier)/100;
            } else {
                this.maxProgressTime = maxProgress;
            }
        }

        public long getMaxVoltage() {
            return VA[Math.min(tier,clean_tier*2)];
        }

        @Override
        public int getParallelLimit() {
            if (isPrecise()) {
                return clean_tier*sheping_tier;
            } else {
                return clean_tier+sheping_tier;
            }
        }
    }
}