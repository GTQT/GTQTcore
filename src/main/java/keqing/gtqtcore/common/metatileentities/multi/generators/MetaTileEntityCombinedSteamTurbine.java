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
import gregtech.common.metatileentities.multi.electric.generator.MetaTileEntityLargeTurbine;
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

    public MetaTileEntityCombinedSteamTurbine(ResourceLocation metaTileEntityId, boolean isSupercritical,IBlockState casingState, IBlockState gearboxState, ICubeRenderer casingRenderer,
                                              boolean hasMufflerHatch, ICubeRenderer frontOverlay) {
        super(metaTileEntityId,
              isSupercritical ? GTQTcoreRecipeMaps.SUPERCRITICAL_STEAM_TURBINE_RECIPES : GTQTcoreRecipeMaps.HIGH_PRESSURE_STEAM_TURBINE_RECIPES,
              isSupercritical ? LuV : EV,casingState, gearboxState, casingRenderer, hasMufflerHatch, frontOverlay);
        this.isSupercritical = isSupercritical;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCombinedSteamTurbine(metaTileEntityId, isSupercritical,casingState, gearboxState,
                casingRenderer, hasMufflerHatch, frontOverlay);
    }
}
