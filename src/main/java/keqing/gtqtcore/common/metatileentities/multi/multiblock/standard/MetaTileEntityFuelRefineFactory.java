package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.metaileentity.GTQTNoTierMultiblockController;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static gregtech.api.unification.material.Materials.Naquadah;

public class MetaTileEntityFuelRefineFactory extends GTQTNoTierMultiblockController {

    public MetaTileEntityFuelRefineFactory(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.FUEL_REFINE_FACTORY_RECIPES
        });
        setMaxParallel(64);
        setMaxParallelFlag(true);
        //setTimeReduce(glassTire);
        setTimeReduceFlag(false);

        setOverclocking(4);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.TALONITE_CASING);
    }

    private static IBlockState getPipeCasingState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.POLYTETRAFLUOROETHYLENE_PIPE);
    }

    private static IBlockState getUniqueCasingState() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT);
    }

    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Naquadah).getBlock(Naquadah);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityFuelRefineFactory(metaTileEntityId);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("               ", "      XXX      ", "               ", "               ", "               ", "               ", "               ", "      XXX      ", "               ", "               ", "               ", "               ", "               ", "      XXX      ", "               ")
                .aisle("      XXX      ", "    XX   XX    ", "      XXX      ", "               ", "               ", "               ", "      XXX      ", "    XX   XX    ", "      XXX      ", "               ", "               ", "               ", "      XXX      ", "    XX   XX    ", "      XXX      ")
                .aisle("    XX   XX    ", "   X  XXX  X   ", "    XX   XX    ", "               ", "               ", "               ", "    XX   XX    ", "   X  XXX  X   ", "    XX   XX    ", "               ", "               ", "               ", "    XX   XX    ", "   X  XXX  X   ", "    XX   XX    ")
                .aisle("   X       X   ", "  XPXXFPFXXPX  ", "   X  F F  X   ", "      F F      ", "      F F      ", "      F F      ", "   X  F F  X   ", "  XPXXFPFXXPX  ", "   X  F F  X   ", "      F F      ", "      F F      ", "      F F      ", "   X  F F  X   ", "  XPXXFPFXXPX  ", "   X       X   ")
                .aisle("  X         X  ", " X X   U   X X ", "  X         X  ", "               ", "               ", "               ", "  X         X  ", " X X   U   X X ", "  X         X  ", "               ", "               ", "               ", "  X         X  ", " X X   U   X X ", "  X         X  ")
                .aisle("  X         X  ", " X X   U   X X ", "  X         X  ", "               ", "               ", "               ", "  X         X  ", " X X   U   X X ", "  X         X  ", "               ", "               ", "               ", "  X         X  ", " X X   U   X X ", "  X         X  ")
                .aisle(" X    XXX    X ", "X XF  FPF  FX X", " X F  F F  F X ", "   F  F F  F   ", "   F  F F  F   ", "   F  F F  F   ", " X F  F F  F X ", "X XF  FPF  FX X", " X F  F F  F X ", "   F  F F  F   ", "   F  F F  F   ", "   F  F F  F   ", " X F  F F  F X ", "X XF  FPF  FX X", " X    XXX    X ")
                .aisle(" X    XXX    X ", "X XPUUP PUUPX X", " X           X ", "               ", "               ", "               ", " X           X ", "X XPUUP PUUPX X", " X           X ", "               ", "               ", "               ", " X           X ", "X XPUUP PUUPX X", " X    XMX    X ")
                .aisle(" X    XXX    X ", "X XF  FPF  FX X", " X F  F F  F X ", "   F  F F  F   ", "   F  F F  F   ", "   F  F F  F   ", " X F  F F  F X ", "X XF  FPF  FX X", " X F  F F  F X ", "   F  F F  F   ", "   F  F F  F   ", "   F  F F  F   ", " X F  F F  F X ", "X XF  FPF  FX X", " X    XXX    X ")
                .aisle("  X         X  ", " X X   U   X X ", "  X         X  ", "               ", "               ", "               ", "  X         X  ", " X X   U   X X ", "  X         X  ", "               ", "               ", "               ", "  X         X  ", " X X   U   X X ", "  X         X  ")
                .aisle("  X         X  ", " X X   U   X X ", "  X         X  ", "               ", "               ", "               ", "  X         X  ", " X X   U   X X ", "  X         X  ", "               ", "               ", "               ", "  X         X  ", " X X   U   X X ", "  X         X  ")
                .aisle("   X       X   ", "  XPXXFPFXXPX  ", "   X  F F  X   ", "      F F      ", "      F F      ", "      F F      ", "   X  F F  X   ", "  XPXXFPFXXPX  ", "   X  F F  X   ", "      F F      ", "      F F      ", "      F F      ", "   X  F F  X   ", "  XPXXFPFXXPX  ", "   X       X   ")
                .aisle("    XX   XX    ", "   X  XXX  X   ", "    XX   XX    ", "               ", "               ", "               ", "    XX   XX    ", "   X  XXX  X   ", "    XX   XX    ", "               ", "               ", "               ", "    XX   XX    ", "   X  XXX  X   ", "    XX   XX    ")
                .aisle("      XXX      ", "    XX   XX    ", "      XXX      ", "               ", "               ", "               ", "      XXX      ", "    XX   XX    ", "      XXX      ", "               ", "               ", "               ", "      XXX      ", "    XX   XX    ", "      XXX      ")
                .aisle("               ", "      XSX      ", "               ", "               ", "               ", "               ", "               ", "      XXX      ", "               ", "               ", "               ", "               ", "               ", "      XXX      ", "               ")
                .where('S', selfPredicate())
                .where('X', states(getCasingState())
                        .setMinGlobalLimited(387)
                        .or(autoAbilities(true, true, true, true, true, true, false)))
                .where('P', states(getPipeCasingState()))
                .where('U', states(getUniqueCasingState()))
                .where('F', states(getFrameState()))
                .where('M', states(getCasingState())
                        .or(abilities(MultiblockAbility.MUFFLER_HATCH)
                                .setExactLimit(1)
                                .setPreviewCount(1)))
                .where(' ', any())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.POLYBENZIMIDAZOLE_PIPE;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }
}
