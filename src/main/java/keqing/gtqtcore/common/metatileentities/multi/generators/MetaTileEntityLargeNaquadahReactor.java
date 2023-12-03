package keqing.gtqtcore.common.metatileentities.multi.generators;

import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.FuelMultiblockController;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.blocks.BlockMultiblockCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.GTValues.UV;
import static gregtech.api.GTValues.V;
import static gregtech.common.blocks.MetaBlocks.FRAMES;

public class MetaTileEntityLargeNaquadahReactor extends FuelMultiblockController {

    public MetaTileEntityLargeNaquadahReactor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.NAQUADAH_REACTOR_RECIPES, UV);
        this.recipeMapWorkable.setMaximumOverclockVoltage(V[UV]);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLargeNaquadahReactor(metaTileEntityId);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" CCC ", " CUC ", " CCC ", "  C  ", "  C  ", " CCC ", " CUC ", " CCC ")
                .aisle("CCCCC", "CP PC", "CG GC", " P P ", " P P ", "CG GC", "CP PC", " CCC ")
                .aisle("CCCCC", "U F U", "C F C", "C F C", "C F C", "C F C", "U F U", " COC ")
                .aisle("CCCCC", "CP PC", "CG GC", " P P ", " P P ", "CG GC", "CP PC", " CCC ")
                .aisle(" CCC ", " CSC ", " CCC ", "  C  ", "  C  ", " CCC ", " CUC ", " CCC ")
                .where('S', selfPredicate())
                .where('C', states(getCasingState())
                        .setMinGlobalLimited(70)
                        .or(autoAbilities(false, true, false, false, true, false, false))
                        .or(metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.OUTPUT_ENERGY).stream()
                                .filter(mte -> {
                                    IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null);
                                    return container != null && container.getOutputVoltage() == GTValues.V[UV]
                                                             && container.getOutputAmperage() == 2;
                                })
                                .toArray(MetaTileEntity[]::new)).setExactLimit(1).setPreviewCount(1)))
                .where('U', states(getUniqueCasingState()))
                .where('G', states(getGearBoxCasingState()))
                .where('F', states(getFrameState()))
                .where('P', states(getPipeCasingState()))
                .where('O', states(getCasingState())
                        .or(abilities(MultiblockAbility.MUFFLER_HATCH).setExactLimit(1).setPreviewCount(1)))
                .build();
    }

    private IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_TURBINE_CASING);
    }

    private IBlockState getUniqueCasingState() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT);
    }

    private IBlockState getGearBoxCasingState() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING);
    }

    private IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.NaquadahAlloy).getBlock(Materials.NaquadahAlloy);
    }

    private IBlockState getPipeCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_MACHINE_CASING);
    }

    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.NQ_CASING;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.machine.large_naquadah_reactor.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.large_naquadah_reactor.tooltip.2"));
    }

    public void runMufflerEffect(float xPos, float yPos, float zPos, float xSpd, float ySpd, float zSpd) {
        this.getWorld().spawnParticle(EnumParticleTypes.SPELL_WITCH, (double)xPos, (double)yPos, (double)zPos, (double)xSpd, (double)ySpd, (double)zSpd, new int[0]);
    }
}