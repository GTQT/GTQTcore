package keqing.gtqtcore.common.metatileentities.multi.generators;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3;
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

import static gregtech.api.GTValues.LuV;
import static gregtech.api.GTValues.V;

public class MetaTileEntityLargeRocketEngine extends FuelMultiblockController {

    public MetaTileEntityLargeRocketEngine(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.ROCKET, LuV);
        this.recipeMapWorkable.setMaximumOverclockVoltage(V[LuV]);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLargeRocketEngine(metaTileEntityId);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCC", "CXC", "CCC")
                .aisle("CCC", "oPo", "CCC")
                .aisle("CCC", "oPo", "CCC")
                .aisle("CCC", "oPo", "CCC")
                .aisle("CCC", "oPo", "CCC")
                .aisle("CCC", "oPo", "CCC")
                .aisle("CCC", "oPo", "CCC")
                .aisle("CCC", "oPo", "CCC")
                .aisle("CCC", "oPo", "CCC")
                .aisle("CCC", "CSC", "CCC")
                .where('S', this.selfPredicate())
                .where('C', states(getCasingState())
                        .setMinGlobalLimited(28)
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS)
                                .setPreviewCount(2))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH)
                                .setExactLimit(1)))
                .where('P', states(getBoilerCasingState()))
                .where('X', metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.OUTPUT_ENERGY).stream()
                        .filter(mte -> {
                            IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null);
                            return container != null && container.getOutputVoltage() == V[LuV];})
                        .toArray(MetaTileEntity[]::new))
                        .setExactLimit(1)
                        .setPreviewCount(1))
                .where('o', abilities(MultiblockAbility.MUFFLER_HATCH))
                .build();
    }

    public static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing3.getState(BlockMultiblockCasing3.CasingType.NITINOL_MACHINE_CASING);
    }

    public static IBlockState getBoilerCasingState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TITANIUM_PIPE);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.NITINOL_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.LARGE_ROCKET_ENGINE_OVERLAY;
    }

    @Override
    public void addInformation(ItemStack stack,
                               @Nullable World player,
                               @Nonnull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("并不会飞上天空！！", new Object[0]));
        tooltip.add(I18n.format("gregtech.universal.tooltip.base_production_eut", GTValues.V[LuV]));
    }
}