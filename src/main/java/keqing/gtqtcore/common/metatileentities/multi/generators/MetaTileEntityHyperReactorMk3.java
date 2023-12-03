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
import gregtech.common.blocks.BlockFusionCasing;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.GTValues.UIV;
import static gregtech.api.GTValues.V;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing.TurbineCasingType.HYPER_CASING;

public class MetaTileEntityHyperReactorMk3 extends FuelMultiblockController {

    public MetaTileEntityHyperReactorMk3(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.HYPER_REACTOR_MK3_RECIPES, UIV);
        this.recipeMapWorkable.setMaximumOverclockVoltage(V[UIV]);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityHyperReactorMk3(metaTileEntityId);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("    CCCCCCC    ", "    CCCCCCC    ", "    CCCCCCC    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    CCCCCCC    ", "    CCCCCCC    ", "    CCCCCCC    ")
                .aisle("   CCCCCCCCC   ", "   VOoooooOV   ", "   COoooooOC   ", "   FUUUUUUUF   ", "   F       F   ", "   F       F   ", "   F       F   ", "   F       F   ", "   F       F   ", "   F       F   ", "   F       F   ", "   F       F   ", "   F       F   ", "   F       F   ", "   F       F   ", "   FUUUUUUUF   ", "   CCCCCCCCC   ", "   VCCCCCCCV   ", "   CCCCCCCCC   ")
                .aisle("  CCCCCCCCCCC  ", "  VOoooooooOV  ", "  COoooooooOC  ", "  FUoooooooUF  ", "  F         F  ", "  F         F  ", "  F         F  ", "  F         F  ", "  F         F  ", "  F         F  ", "  F         F  ", "  F         F  ", "  F         F  ", "  F         F  ", "  F         F  ", "  FUoooooooUF  ", "  CCCCCCCCCCC  ", "  VCCCCCCCCCV  ", "  CCCCCCCCCCC  ")
                .aisle(" CCCCCCCCCCCCC ", " VOoooooooooOV ", " COoooooooooOC ", " FUoooooooooUF ", " F           F ", " F           F ", " F           F ", " F           F ", " F           F ", " F           F ", " F           F ", " F           F ", " F           F ", " F           F ", " F           F ", " FUoooooooooUF ", " CCCCCCCCCCCCC ", " VCCCCCCCCCCCV ", " CCCCCCCCCCCCC ")
                .aisle("CCCCCCCCCCCCCCC", "COoooooooooooOC", "COoooooooooooOC", "GUooOOOoOOOooUG", "G             G", "G             G", "G             G", "G   OOO OOO   G", "G             G", "G             G", "G             G", "G   OOO OOO   G", "G             G", "G             G", "G             G", "GUooOOOoOOOooUG", "CCCCCCCCCCCCCCC", "CCCCCCCCCCCCCCC", "CCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCC", "COoooooooooooOC", "COoooooooooooOC", "GUooOOOoOOOooUG", "G    H   H    G", "G    H   H    G", "G    H   H    G", "G   OOO OOO   G", "G    H   H    G", "G    H   H    G", "G    H   H    G", "G   OOO OOO   G", "G    H   H    G", "G    H   H    G", "G    H   H    G", "GUooOOOoOOOooUG", "CCCCCCCCCCCCCCC", "CCCCCCCCCCCCCCC", "CCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCC", "COoooooooooooOC", "COoooooooooooOC", "GUooOOOoOOOooUG", "G             G", "G             G", "G             G", "G   OOO OOO   G", "G             G", "G             G", "G             G", "G   OOO OOO   G", "G             G", "G             G", "G             G", "GUooOOOoOOOooUG", "CCCCCCCCCCCCCCC", "CCCCCCCCCCCCCCC", "CCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCC", "COoooooooooooOC", "COoooooooooooOC", "GUoooooooooooUG", "G             G", "G             G", "G             G", "G             G", "G             G", "G             G", "G             G", "G             G", "G             G", "G             G", "G             G", "GUoooooooooooUG", "CCCCCCCCCCCCCCC", "CCCCCCCCCCCCCCC", "CCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCC", "COoooooooooooOC", "COoooooooooooOC", "GUooOOOoOOOooUG", "G             G", "G             G", "G             G", "G   OOO OOO   G", "G             G", "G             G", "G             G", "G   OOO OOO   G", "G             G", "G             G", "G             G", "GUooOOOoOOOooUG", "CCCCCCCCCCCCCCC", "CCCCCCCCCCCCCCC", "CCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCC", "COoooooooooooOC", "COoooooooooooOC", "GUooOOOoOOOooUG", "G    H   H    G", "G    H   H    G", "G    H   H    G", "G   OOO OOO   G", "G    H   H    G", "G    H   H    G", "G    H   H    G", "G   OOO OOO   G", "G    H   H    G", "G    H   H    G", "G    H   H    G", "GUooOOOoOOOooUG", "CCCCCCCCCCCCCCC", "CCCCCCCCCCCCCCC", "CCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCC", "COoooooooooooOC", "COoooooooooooOC", "GUooOOOoOOOooUG", "G             G", "G             G", "G             G", "G   OOO OOO   G", "G             G", "G             G", "G             G", "G   OOO OOO   G", "G             G", "G             G", "G             G", "GUooOOOoOOOooUG", "CCCCCCCCCCCCCCC", "CCCCCCCCCCCCCCC", "CCCCCCCCCCCCCCC")
                .aisle(" CCCCCCCCCCCCC ", " VOoooooooooOV ", " COoooooooooOC ", " FUoooooooooUF ", " F           F ", " F           F ", " F           F ", " F           F ", " F           F ", " F           F ", " F           F ", " F           F ", " F           F ", " F           F ", " F           F ", " FUoooooooooUF ", " CCCCCCCCCCCCC ", " VCCCCCCCCCCCV ", " CCCCCCCCCCCCC ")
                .aisle("  CCCCCCCCCCC  ", "  VOoooooooOV  ", "  COoooooooOC  ", "  FUoooooooUF  ", "  F         F  ", "  F         F  ", "  F         F  ", "  F         F  ", "  F         F  ", "  F         F  ", "  F         F  ", "  F         F  ", "  F         F  ", "  F         F  ", "  F         F  ", "  FUoooooooUF  ", "  CCCCCCCCCCC  ", "  VCCCCCCCCCV  ", "  CCCCCCCCCCC  ")
                .aisle("   CCCCCCCCC   ", "   VOoooooOV   ", "   COoooooOC   ", "   FUUUUUUUF   ", "   F       F   ", "   F       F   ", "   F       F   ", "   F       F   ", "   F       F   ", "   F       F   ", "   F       F   ", "   F       F   ", "   F       F   ", "   F       F   ", "   F       F   ", "   FUUUUUUUF   ", "   CCCCCCCCC   ", "   VCCCCCCCV   ", "   CCCCCCCCC   ")
                .aisle("    CCCCCCC    ", "    CCCSCCC    ", "    CCCCCCC    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    GGGGGGG    ", "    CCCCCCC    ", "    CCCCCCC    ", "    CCCCCCC    ")
                .where('S', selfPredicate())
                .where('C', states(getCasingState())
                        .setMinGlobalLimited(790)
                        .or(autoAbilities(false, true, false, false, true, false, false))
                        .or(metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.OUTPUT_ENERGY).stream()
                                .filter(mte -> {
                                    IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null);
                                    return container != null && container.getOutputVoltage() == GTValues.V[UIV]
                                            && container.getOutputAmperage() == 2;
                                })
                                .toArray(MetaTileEntity[]::new)).setExactLimit(1).setPreviewCount(1)))
                .where('V', states(getUniqueCasingState()))
                .where('O', states(getSecondCasingState()))
                .where('o', states(getThirdCasingState()))
                .where('G', states(getGlassState()))
                .where('F', states(getFrameState()))
                .where('U', states(getSecondUniqueCasingState()))
                .where('H', states(GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.HYPER_CIRE_MK3)))
                .build();
    }

    private IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(HYPER_CASING);
    }

    private IBlockState getUniqueCasingState() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT);
    }

    private IBlockState getSecondCasingState() {
        return MetaBlocks.FUSION_CASING.getState(BlockFusionCasing.CasingType.FUSION_COIL);
    }

    private IBlockState getThirdCasingState() {
        return MetaBlocks.FUSION_CASING.getState(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL);
    }

    private IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS);
    }

    private IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.NaquadahAlloy).getBlock(Materials.NaquadahAlloy);}

    private IBlockState getSecondUniqueCasingState() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.ELECTROLYTIC_CELL);
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.HYPER_CASING;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.machine.hyper_reactor_mk3.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.hyper_reactor_mk3.tooltip.2"));
    }
}