package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IMultiblockController;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.damagesources.DamageSources;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneVariantBlock;
import gregtech.core.advancement.AdvancementTriggers;
import keqing.gtqtcore.api.capability.IBall;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.GrindBallTierProperty;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTIsaCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class MetaTileEntityGantryCrane extends RecipeMapMultiblockController {

    public MetaTileEntityGantryCrane(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.GANTRY_CRANE);
    }

    private static IBlockState getCasingAState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }
    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "        A   A   A       ", "                        ", "                        ")
                .aisle("BBBBB               BBBB", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "        A   A   A       ", "                        ", "                        ")
                .aisle("BBBBB               BBBB", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " ACA A A A A A A A A ACA", " A A    A   A   A    A A", " A A                 A A", "                        ")
                .aisle("BBBBB               BBBB", "  C                   C ", "  C                   C ", "  C                   C ", "  C                   C ", "  C                   C ", "  C                   C ", "  C                   C ", "  C                   C ", "AACAAAAAAAAAAAAAAAAAAACA", "CCCCCCCCCCCCCCCCCCCCCCCC", "     A AAA AAA AAA A    ", "       A A A A A A      ", "                        ")
                .aisle("BBBBB               BBBB", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " AAA                 AAA", " ACA                 ACA", " A A    A   A   A    A A", " A A                 A A", "                        ")
                .aisle("BBBBB               BBBB", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "  A                   A ", "  C                   C ", "        A   A   A       ", "                        ", "                        ")
                .aisle("BBBBB               BBBB", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "  A                   A ", "  C                   C ", "        A   A   A       ", "                        ", "                        ")
                .aisle("BBBBB               BBBB", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "  A                   A ", "  C   AAAAAAAAAAAAA   C ", "        CCCCCCCCC       ", "                        ", "                        ")
                .aisle("BBBBB               BBBB", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "  A                   A ", "  C                   C ", "        A   A   A       ", "                        ", "                        ")
                .aisle("BBBBB               BBBB", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "  A                   A ", "  C                   C ", "        A   A   A       ", "                        ", "                        ")
                .aisle("BBBBB               BBBB", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " AAA                 AAA", " ACA                 ACA", " A A    A   A   A    A A", " A A                 A A", "                        ")
                .aisle("BBBBB               BBBB", "  C                   C ", "  S                   C ", "  C                   C ", "  C                   C ", "  C                   C ", "  C                   C ", "  C                   C ", "  C                   C ", "AACAAAAAAAAAAAAAAAAAAACA", "CCCCCCCCCCCCCCCCCCCCCCCC", "     A AAA AAA AAA A    ", "       A A A A A A      ", "                        ")
                .aisle("BBBBB               BBBB", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " A A                 A A", " ACA A A A A A A A A ACA", " A A    A   A   A    A A", " A A                 A A", "                        ")
                .aisle("BBBBB               BBBB", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "        A   A   A       ", "                        ", "                        ")
                .aisle("                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "        A   A   A       ", "                        ", "                        ")
                .aisle("                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ", "                        ")
                .where('S', selfPredicate())
                .where('B', states(MetaBlocks.STONE_BLOCKS.get(StoneVariantBlock.StoneVariant.SMOOTH).getState(StoneVariantBlock.StoneType.CONCRETE_LIGHT)))
                .where('C', states(getCasingAState()).setMinGlobalLimited(355)
                        .or(abilities(MultiblockAbility.MUFFLER_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2).setPreviewCount(1)))
                .where('A',  states(MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel)))
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityGantryCrane(metaTileEntityId);
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }

}