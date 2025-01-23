package keqing.gtqtcore.common.metatileentities.multi.generators.generatorRework;

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
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Material;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

import static gregtech.api.GTValues.*;
import static gregtech.common.blocks.MetaBlocks.FRAMES;

public class MetaTileEntityLargeBasicGenerator extends FuelMultiblockController {
    public final IBlockState casing;
    public final IBlockState boiler;
    public final Material frame;
    RecipeMap<?> recipeMap;
    public final int tier;

    public MetaTileEntityLargeBasicGenerator(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, int tier, IBlockState casing, IBlockState boiler, Material frame) {
        super(metaTileEntityId, recipeMap, tier);
        this.recipeMapWorkable.setMaximumOverclockVoltage(V[tier]);
        this.recipeMap = recipeMap;
        this.casing = casing;
        this.boiler = boiler;
        this.frame = frame;
        this.tier = tier;
    }

    private IBlockState getCasingState() {
        return casing;
    }

    private IBlockState getBoilerCasingState() {
        return boiler;
    }

    private IBlockState getFrameState() {
        return FRAMES.get(frame).getBlock(frame);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLargeBasicGenerator(metaTileEntityId, recipeMap, tier, casing, boiler, frame);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("F F", "CCC", "COC", "CCC")
                .aisle("FFF", "CCC", "CPC", "CCC")
                .aisle("F F", "CCC", "CPI", "CCC")
                .aisle("F F", "CCC", "CPI", "CCC")
                .aisle("FFF", "CCC", "CPI", "CCC")
                .aisle("F F", "CCC", "CSC", "CCC")
                .where('S', this.selfPredicate())
                .where(' ', any())
                .where('C', states(getCasingState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH)
                                .setExactLimit(1)))
                .where('I', states(getCasingState())
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS)
                                .setMaxGlobalLimited(2)
                                .setMinGlobalLimited(1)))
                .where('F', states(getFrameState()))
                .where('P', states(getBoilerCasingState()))
                .where('O', metaTileEntities(Arrays.stream(MetaTileEntities.ENERGY_OUTPUT_HATCH)
                        .filter(mte -> mte != null && mte.getTier() <= V[tier])
                        .toArray(MetaTileEntity[]::new))
                        .setMinGlobalLimited(1)
                        .setPreviewCount(16))
                .build();
    }
    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        switch (tier) {
            case (2) -> {
                return Textures.FROST_PROOF_CASING;
            }
            case (3) -> {
                return Textures.CLEAN_STAINLESS_STEEL_CASING;
            }
            default -> {
                return Textures.SOLID_STEEL_CASING;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.LARGE_BIO_REACTOR_OVERLAY;
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("可使用多A能源仓，但是等级必须与多方块等级相同或更低"));
        tooltip.add(I18n.format("gregtech.universal.tooltip.base_production_eut", GTValues.V[tier]));
    }
}
