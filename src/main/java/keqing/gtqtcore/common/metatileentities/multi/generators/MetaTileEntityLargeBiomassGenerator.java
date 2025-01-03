package keqing.gtqtcore.common.metatileentities.multi.generators;

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
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static gregtech.api.GTValues.LuV;
import static gregtech.api.GTValues.V;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1.CasingType.Inconel625;

public class MetaTileEntityLargeBiomassGenerator extends FuelMultiblockController {

    public MetaTileEntityLargeBiomassGenerator(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.BIOMASS_GENERATOR_RECIPES, LuV);
        this.recipeMapWorkable.setMaximumOverclockVoltage(V[LuV]);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLargeBiomassGenerator(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("FCF", "COC", "FCF")
                .aisle("CCC", "CPC", "CCC")
                .aisle("CCC", "CPC", "CCC")
                .aisle("CCC", "CPC", "CCC")
                .aisle("FCF", "ISI", "FCF")
                .where('S', this.selfPredicate())
                .where('C', states(getCasingState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH)
                                .setExactLimit(1)))
                .where('I', states(getCasingState())
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS)
                                .setMaxGlobalLimited(2)
                                .setPreviewCount(2)))
                .where('F', states(getFrameState()))
                .where('P', states(getBoilerCasingState()))
                .where('O',metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.OUTPUT_ENERGY).stream()
                        .filter(mte -> {
                            IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null);
                            return container != null && container.getOutputVoltage() == GTValues.V[LuV];})
                        .toArray(MetaTileEntity[]::new))
                        .setExactLimit(1)
                        .setPreviewCount(1))
                .build();
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing1.getState(Inconel625);
    }

    private static IBlockState getBoilerCasingState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TITANIUM_PIPE);
    }

    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.NaquadahAlloy).getBlock(Materials.NaquadahAlloy);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.Inconel625;
    }

    @SideOnly(Side.CLIENT)

    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.LARGE_BIO_REACTOR_OVERLAY;
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip,boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("猫猫发电机！放入一个cat，可以产生1eu/t的电（"));
        tooltip.add(I18n.format("gregtech.universal.tooltip.base_production_eut", GTValues.V[LuV]));
        tooltip.add(I18n.format("gtqtcore.machine.large_biomass_generator.tooltip.1"));
    }

}
