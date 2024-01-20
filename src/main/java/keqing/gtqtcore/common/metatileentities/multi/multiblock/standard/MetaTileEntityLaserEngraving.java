package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;


import gregtech.api.GTValues;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.capability.impl.ComputationRecipeLogic;
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
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.List;

import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.HydrochloricAcid;

public class MetaTileEntityLaserEngraving extends MultiMapMultiblockController implements IOpticalComputationReceiver {
    private int glass_tier;
    private int laser_tier;
    private int tier;
    private IOpticalComputationProvider computationProvider;
    public MetaTileEntityLaserEngraving(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.LASER_ENGRAVER_RECIPES,
                GTQTcoreRecipeMaps.LASER_ENGRAVING,//普通装配
                GTQTcoreRecipeMaps.CW_LASER_ENGRAVER_RECIPES
        });
        this.recipeMapWorkable = new LaserEngravingWorkableHandler(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLaserEngraving(metaTileEntityId);
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
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("gtqtcore.eleTire",tier, laser_tier, glass_tier));

    }
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXXX", "XXXXXX", "XXGGGX")
                .aisle("XXXXXX", "XXZZZX", "XXGGGX")
                .aisle("XXXXXX", "CSGGGX", "XXGGGX")
                .where('S', selfPredicate())
                .where('C', abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION))
                .where('X', TiredTraceabilityPredicate.CP_CASING.setMinGlobalLimited(24).or(autoAbilities()))
                .where('Z', TiredTraceabilityPredicate.CP_ZW_CASING)
                .where('G', TiredTraceabilityPredicate.CP_LGLASS)
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

        this.laser_tier = GTQTUtil.getOrDefault(() -> laser_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)laser_tier).getIntTier(),
                0);
        this.tier = GTQTUtil.getOrDefault(() -> tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)tier).getIntTier(),
                0);
        this.glass_tier = GTQTUtil.getOrDefault(() -> glass_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)glass_tier).getIntTier(),
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

        private boolean isPrecise() {
            return this.getRecipeMap() == GTQTcoreRecipeMaps.LASER_ENGRAVING;
        }

        public void setMaxProgress(int maxProgress) {
            if (isPrecise()) {
                this.maxProgressTime = maxProgress ;
            } else {
                this.maxProgressTime = maxProgress / 2;
            }
        }

        public long getMaxVoltage() {
            return VA[Math.min(tier,laser_tier*2)];
        }

        @Override
        public int getParallelLimit() {
            if (isPrecise()) {
                return 1;
            } else {
                return glass_tier;
            }
        }
    }
}