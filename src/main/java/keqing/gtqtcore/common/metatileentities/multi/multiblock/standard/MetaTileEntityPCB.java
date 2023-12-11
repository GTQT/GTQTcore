package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.GTValues;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.impl.HeatingCoilRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class MetaTileEntityPCB extends RecipeMapMultiblockController {

    public MetaTileEntityPCB(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.BLAST_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPCB(metaTileEntityId);
    }




    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle (" AAA ",
                        " AAA ",
                        " AAA ",
                        " AAA ",
                        " AAA ",
                        "     ",
                        "     ",
                        "     ",
                        "     ",
                        " AAA ",
                        " AAA ",
                        " A A ",
                        " A A ",
                        " A A ",
                        " A A ",
                        " AAA ",
                        " AAA ",
                        " AAA "
                )
                .aisle (
                        "AAAAA",
                        "AD DA",
                        "AD DA",
                        "AD DA",
                        "AAAAA",
                        " FFF ",
                        " FFF ",
                        " FFF ",
                        " FFF ",
                        "AAAAA",
                        "AAGAA",
                        "A G A",
                        "A G A",
                        "A G A",
                        "A G A",
                        "A   A",
                        "AAAAA",
                        "AAAAA"
                )
                .aisle (
                        "AAAAA",
                        "A   A",
                        "A   A",
                        "A   A",
                        "AAAAA",
                        " HFH ",
                        " HFH ",
                        " HFH ",
                        " HFH ",
                        "AAAAA",
                        " GGG ",
                        "A   A",
                        "A   A",
                        "A   A",
                        "A   A",
                        "A   A",
                        "AAAAA",
                        "AAAAA"
                )
                .aisle (
                        "AAAAA",
                        "A   A",
                        "A   A",
                        "A   A",
                        "AAAAA",
                        " JFJ ",
                        " JFJ ",
                        " JFJ ",
                        " JFJ ",
                        "AAAAA",
                        " GGG ",
                        "AAAAA",
                        "AAAAA",
                        "AAAAA",
                        "AAAAA",
                        "AAAAA",
                        "AAAAA",
                        "AAAAA"
                )
                .aisle (
                        "AAAAA",
                        "A   A",
                        "A   A",
                        "A   A",
                        "A   A",
                        "A   A",
                        "A   A",
                        "A   A",
                        "A   A",
                        "AAAAA",
                        " GGG ",
                        "AAAAA",
                        " AAA ",
                        " AAA ",
                        " AAA ",
                        " AAA ",
                        " AAA ",
                        " AAA "
                )
                .aisle (
                        "AAAAA",
                        "AD DA",
                        "AD DA",
                        "AD DA",
                        "AD DA",
                        "AD DA",
                        "AD DA",
                        "AD DA",
                        "AD DA",
                        "AAAAA",
                        " GGG ",
                        "AAAAA",
                        "     ",
                        "     ",
                        "     ",
                        "     ",
                        "     ",
                        "     "
                )
                .aisle (
                        " AAA ",
                        " MCA ",
                        " AAA ",
                        " AAA ",
                        " AAA ",
                        " AAA ",
                        " AAA ",
                        " AAA ",
                        " AAA ",
                        " AAA ",
                        " AAA ",
                        " AAA ",
                        "     ",
                        "     ",
                        "     ",
                        "     ",
                        "     ",
                        "     "
                )
                .where('C', selfPredicate())
                .where('A', states(getCasingState()).setMinGlobalLimited(280)
                        .or(autoAbilities(true, true, true, true, true, true, false)))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('D', states(getCasingState1()))
		    	.where('F', states(getCasingState1()))
		    	.where('G', states(getCasingState1()))
			    .where('H', states(getCasingState1()))
			    .where('J', states(getCasingState1()))
                .where(' ', air())
                .build();
    }

    protected IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.BRICK);
    }

    protected IBlockState getCasingState1() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_MACHINE_CASING);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.BRICK;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }







    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }


}