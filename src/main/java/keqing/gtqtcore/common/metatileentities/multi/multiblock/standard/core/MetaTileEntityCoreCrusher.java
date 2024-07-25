package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.core;

import gregtech.api.GTValues;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.util.GTTransferUtils;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockCore;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.MetaTileEntityStewStoolStove;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBaseWithControl;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

import static gregtech.api.GTValues.*;

public class MetaTileEntityCoreCrusher extends GTQTMultiblockCore {
    public MetaTileEntityCoreCrusher(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[] {
                RecipeMaps.MACERATOR_RECIPES,
                RecipeMaps.FORGE_HAMMER_RECIPES
        });
    }
    @Override
    public int getMinVa()
    {
        return VA[IV];
    }
    @Override
    public int getCoreNum() {
        return 8;
    }

    @Nonnull
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CMC", "CCC", "CCC", "CCC", "CCC", "CCC")
                .aisle("CCC", "CXC", "CXC", "CXC", "CXC", "CCC")
                .aisle("COC", "CCC", "CCC", "CCC", "CCC", "CCC")
                .where('O', this.selfPredicate())
                .where('C', states(this.getCasingState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                )
                .where('X', heatingCoils())
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .build();
    }

    private IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.MACERATOR_CASING);
    }

    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.MACERATOR_CASING;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCoreCrusher(this.metaTileEntityId);
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        return new LinkedList<>();
    }
}
