package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.overwriteMultiblocks;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class MetaTileEntityLargeGrind extends RecipeMapMultiblockController {
    private int casingTier;
    private int tubeTier;
    private int tier;

    public MetaTileEntityLargeGrind(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.MACERATOR_RECIPES);
        this.recipeMapWorkable = new LargeGrindReactorLogic(this);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLargeGrind(metaTileEntityId);
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.cracker.gtqtupdate.1"));
        tooltip.add(I18n.format("gregtech.machine.cracker.gtqtupdate.2"));
        tooltip.add(I18n.format("gtqtcore.machine.parallel.pow.machineTier",2,32));
        tooltip.add(I18n.format("gtqtcore.machine.voltage.num",8));
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "XPX", "XXX")
                .aisle("XXX", "XSX", "XXX")
                .where('S', selfPredicate())
                .where('X', TiredTraceabilityPredicate.CP_CASING.get().setMinGlobalLimited(18)
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2).setPreviewCount(2)))
                .where('P', TiredTraceabilityPredicate.CP_TUBE.get())
                .where('#', any())
                .build();
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("gtqtcore.casingTire", casingTier));
        textList.add(new TextComponentTranslation("gtqtcore.tubeTire", tubeTier));
        if (casingTier != tubeTier)
            textList.add(new TextComponentTranslation("gtqtcore.equal", casingTier, tubeTier));
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        switch (this.casingTier) {
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
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object casingTier = context.get("ChemicalPlantCasingTieredStats");
        Object tubeTier = context.get("ChemicalPlantTubeTieredStats");
        this.casingTier = GTQTUtil.getOrDefault(() -> casingTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) casingTier).getIntTier(),
                0);
        this.tubeTier = GTQTUtil.getOrDefault(() -> tubeTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) tubeTier).getIntTier(),
                0);
        this.tier = Math.min(this.casingTier, this.tubeTier);

        this.writeCustomData(GTQTValue.UPDATE_TIER19, buf -> buf.writeInt(this.casingTier));
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.casingTier);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.casingTier = buf.readInt();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GTQTValue.UPDATE_TIER19) {
            this.casingTier = buf.readInt();
        }
        if (dataId == GTQTValue.REQUIRE_DATA_UPDATE19) {
            this.writeCustomData(GTQTValue.UPDATE_TIER19, buf1 -> buf1.writeInt(this.casingTier));
        }
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }


    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("gtqt.tooltip.update")};
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }

    protected class LargeGrindReactorLogic extends MultiblockRecipeLogic {


        public LargeGrindReactorLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity, true);
        }

        public long getMaxVoltage() {
            return 8;
        }

        @Override
        public int getParallelLimit() {
            return Math.min((int) Math.pow(2, tier-1), 32);
        }

        @Override
        protected long getMaxParallelVoltage() {
            return super.getMaxVoltage();
        }
    }
}