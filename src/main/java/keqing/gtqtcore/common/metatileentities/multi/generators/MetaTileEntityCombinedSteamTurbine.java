package keqing.gtqtcore.common.metatileentities.multi.generators;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockTurbineCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static gregtech.api.GTValues.LuV;

import static gregtech.api.GTValues.EV;

public class MetaTileEntityCombinedSteamTurbine extends MetaTileEntityLargeTurbine {

    private final boolean isSupercritical;

    public MetaTileEntityCombinedSteamTurbine(ResourceLocation metaTileEntityId, boolean isSupercritical) {
        super(metaTileEntityId,
              isSupercritical ? GTQTcoreRecipeMaps.SUPERCRITICAL_STEAM_TURBINE_RECIPES : GTQTcoreRecipeMaps.HIGH_PRESSURE_STEAM_TURBINE_RECIPES,
              isSupercritical ? LuV : EV);
        this.isSupercritical = isSupercritical;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCombinedSteamTurbine(metaTileEntityId, isSupercritical);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCC", "CHHC", "CCCC")
                .aisle("CHHC", "RGGR", "CHHC")
                .aisle("CCCC", "CSHC", "CCCC")
                .where('S', selfPredicate())
                .where('C', states(getCasingState()))
                .where('G', states(getSecondCasingState()))
                .where('R', metaTileEntities(MultiblockAbility.REGISTRY.get(GTQTMultiblockAbility.REINFORCED_ROTOR_HOLDER_ABILITY).stream()
                        .filter(mte -> (mte instanceof ITieredMetaTileEntity) && (((ITieredMetaTileEntity) mte).getTier() >= tier))
                        .toArray(MetaTileEntity[]::new))
                        .addTooltips("gregtech.multiblock.pattern.clear_amount_3")
                        .addTooltip("gregtech.multiblock.pattern.error.limited.1", GTValues.VN[tier])
                        .setExactLimit(1)
                        .or(abilities(MultiblockAbility.OUTPUT_ENERGY)).setExactLimit(1))
                .where('H', states(getCasingState())
                        .or(autoAbilities(false, true, false, false, true, true, true)))
                .build();
    }

    private IBlockState getCasingState() {
        return isSupercritical ?
               GTQTMetaBlocks.blockMultiblockCasing1.getState(BlockMultiblockCasing1.CasingType.MaragingSteel250) :
               MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TITANIUM_TURBINE_CASING);
    }

    private IBlockState getSecondCasingState() {
        return isSupercritical ?
               MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TUNGSTENSTEEL_GEARBOX) :
               MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TITANIUM_GEARBOX);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return isSupercritical ? GTQTTextures.MaragingSteel250 : Textures.STABLE_TITANIUM_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.LARGE_STEAM_TURBINE_OVERLAY;
    }
}
