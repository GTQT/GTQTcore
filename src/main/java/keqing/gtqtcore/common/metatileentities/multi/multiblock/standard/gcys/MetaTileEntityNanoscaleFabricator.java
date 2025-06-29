package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;

import gregicality.multiblocks.api.capability.impl.GCYMMultiblockRecipeLogic;
import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.metatileentity.multiblock.ui.KeyManager;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIBuilder;
import gregtech.api.metatileentity.multiblock.ui.UISyncer;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.properties.impl.TemperatureProperty;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.GTUtility;
import gregtech.api.util.KeyUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityItemBus;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockCrucible;
import keqing.gtqtcore.common.block.blocks.BlockPCBFactoryCasing;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MetaTileEntityNanoscaleFabricator extends GCYMRecipeMapMultiblockController {

    private int temperature;

    public MetaTileEntityNanoscaleFabricator(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.MOLECULAR_BEAM_RECIPES);
        this.recipeMapWorkable = new NanoscaleFabricatorWorkableHandler(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityNanoscaleFabricator(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("   TTT   ", "   TIT   ", "   TCT   ", "         ")
                .aisle("  XXXXX  ", "  XX#XX  ", "  XXXXX  ", "  XXXXX  ")
                .aisle(" XXXXXXX ", " X#####X ", " X#####X ", " XXGGGXX ")
                .aisle("TXXTTTXXT", "TX#####XT", "TX#####XT", " XGGGGGX ")
                .aisle("TXXTITXXT", "I###A###I", "CX#####XC", " XGGGGGX ")
                .aisle("TXXTTTXXT", "TX#####XT", "TX#####XT", " XGGGGGX ")
                .aisle(" XXXXXXX ", " X#####X ", " X#####X ", " XXGGGXX ")
                .aisle("  XXXXX  ", "  XX#XX  ", "  XXXXX  ", "  XXXXX  ")
                .aisle("   TST   ", "   TIT   ", "   TCT   ", "         ")
                .where('S', selfPredicate())
                .where('X', states(GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.ENGRAVER_CASING)).setMinGlobalLimited(84)
                        .or(autoAbilities(true, true, false, true, true, true, true)))
                .where('T', states(getNonconductingState()).setMinGlobalLimited(36))
                .where('G', states(MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.LAMINATED_GLASS)))
                .where('I', metaTileEntities(MetaTileEntities.ITEM_IMPORT_BUS[GTValues.ULV]).or(states(GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.NONCONDUCTING_CASING))))
                .where('C', states(getNonconductingState()).or(cruciblePredicate()))
                .where('A', states(GTQTMetaBlocks.blockPCBFactoryCasing.getState(BlockPCBFactoryCasing.PCBFactoryCasingType.ADVANCED_SUBSTRATE_CASING)))
                .where('#', air())
                .where(' ', any())
                .build();
    }

    private IBlockState getNonconductingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.NONCONDUCTING_CASING);
    }

    @Nonnull
    private TraceabilityPredicate cruciblePredicate() {
        return new TraceabilityPredicate(blockWorldState -> {
            IBlockState state = blockWorldState.getBlockState();
            Block block = state.getBlock();
            if (block instanceof BlockCrucible) {
                int storedTemperature = blockWorldState.getMatchContext().getOrPut("Temperature", 0);
                blockWorldState.getMatchContext().set("Temperature", ((BlockCrucible) block).getState(state).getTemperature() + storedTemperature);

                int storedCrucibleAmount = blockWorldState.getMatchContext().getOrPut("CrucibleAmount", 0);
                blockWorldState.getMatchContext().set("CrucibleAmount", 1 + storedCrucibleAmount);
                return true;
            }
            return false;
        }, () -> Arrays.stream(BlockCrucible.CrucibleType.values())
                .sorted(Comparator.comparingInt(BlockCrucible.CrucibleType::getTemperature))
                .map(type -> new BlockInfo(GTQTMetaBlocks.blockCrucible.getState(type), null))
                .toArray(BlockInfo[]::new));
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        int crucibleAmount = context.getInt("CrucibleAmount");
        if (crucibleAmount != 0) this.temperature = context.getInt("Temperature") / crucibleAmount;
        else this.temperature = 0;
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.temperature = 0;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        if (iMultiblockPart instanceof MetaTileEntityItemBus && ((MetaTileEntityItemBus) iMultiblockPart).getExportItems().getSlots() == 0) {
            return GCYMTextures.NONCONDUCTING_CASING;
        }

        return GCYMTextures.ENGRAVER_CASING;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.NANOSCALE_FABRICATOR_OVERLAY;
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
            var heatString = KeyUtil.number(TextFormatting.RED,
                    syncer.syncInt(getCurrentTemperature()), "K");

            keyManager.add(KeyUtil.lang(TextFormatting.GRAY,
                    "gregtech.multiblock.blast_furnace.max_temperature", heatString));
        }
    }

    private int getCurrentTemperature() {
        return temperature;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.machine.nanoscale_fabricator.tooltip.1"));
    }

    private class NanoscaleFabricatorWorkableHandler extends GCYMMultiblockRecipeLogic {

        public NanoscaleFabricatorWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public boolean checkRecipe(@Nonnull Recipe recipe) {
            int delta = temperature - recipe.getProperty(TemperatureProperty.getInstance(), 0);
            return delta >= 0 && delta <= 250;
        }
    }
}
