package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.updateSystem;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metatileentity.GTQTRecipeMapMultiblockController;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static gregtech.api.recipes.RecipeMaps.VACUUM_RECIPES;

//冰箱
public class MetaTileEntityVacuumFreezer extends GTQTRecipeMapMultiblockController {
    private int tier;

    public MetaTileEntityVacuumFreezer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                VACUUM_RECIPES});

        setTierFlag(true);
        //setTier(auto);
        setMaxParallel(64);
        setMaxParallelFlag(true);
        //setMaxVoltage(auto);
        setMaxVoltageFlag(true);
        //setTimeReduce(none);
        setTimeReduceFlag(false);
        setOverclocking(3);
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
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityVacuumFreezer(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "X#X", "XXX")
                .aisle("XXX", "XSX", "XXX")
                .where('S', selfPredicate())
                .where('X', TiredTraceabilityPredicate.CP_CASING.get().setMinGlobalLimited(14).or(autoAbilities()))
                .where('#', air())
                .build();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GTQTValue.UPDATE_TIER24) {
            this.tier = buf.readInt();
        }
        if (dataId == GTQTValue.REQUIRE_DATA_UPDATE24) {
            this.writeCustomData(GTQTValue.UPDATE_TIER24, buf1 -> buf1.writeInt(this.tier));
        }
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(recipeMapWorkable.getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        int processingSpeed = tier == 0 ? 75 : 50 * (tier + 1);
                        ITextComponent speedIncrease = TextComponentUtil.stringWithColor(
                                getSpeedColor(processingSpeed),
                                processingSpeed + "%");

                        ITextComponent base = TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gregtech.multiblock.pyrolyse_oven.speed",
                                speedIncrease);

                        ITextComponent hover = TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gregtech.multiblock.pyrolyse_oven.speed_hover");

                        tl.add(TextComponentUtil.setHover(base, hover));
                    }
                })
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgressPercent());
    }

    private TextFormatting getSpeedColor(int speed) {
        if (speed < 100) {
            return TextFormatting.RED;
        } else if (speed == 100) {
            return TextFormatting.GRAY;
        } else if (speed < 250) {
            return TextFormatting.GREEN;
        } else {
            return TextFormatting.LIGHT_PURPLE;
        }
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
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object tier = context.get("ChemicalPlantCasingTieredStats");
        this.tier = GTQTUtil.getOrDefault(() -> tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) tier).getIntTier(),
                0);

        setTier(this.tier);
        setMaxVoltage(this.tier);

        this.writeCustomData(GTQTValue.UPDATE_TIER24, buf -> buf.writeInt(this.tier));
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(MetalCasingType.ALUMINIUM_FROSTPROOF);
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }
}