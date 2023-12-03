package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
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

import static gregtech.api.unification.material.Materials.NaquadahAlloy;

public class MetaTileEntityFuelRefineFactory extends RecipeMapMultiblockController {

    public MetaTileEntityFuelRefineFactory(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.FUEL_REFINE_FACTORY_RECIPES);
        this.recipeMapWorkable = new MultiblockRecipeLogic(this, true);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityFuelRefineFactory(metaTileEntityId);
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

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.TALONITE_CASING);
    }

    private static IBlockState getPipeCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.POLYBENZIMIDAZOLE_PIPE);
    }

    private static IBlockState getUniqueCasingState() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT);
    }

    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(NaquadahAlloy).getBlock(NaquadahAlloy);
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
    public void addInformation(@Nonnull ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.perfect_oc"));
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }
}
