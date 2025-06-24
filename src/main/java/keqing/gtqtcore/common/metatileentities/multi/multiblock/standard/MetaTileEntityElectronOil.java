package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockBoilerCasing.BoilerCasingType;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.metaileentity.GTQTNoTierMultiblockController;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;

//电催破乳
public class MetaTileEntityElectronOil extends GTQTNoTierMultiblockController {

    public MetaTileEntityElectronOil(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.ELEOIL
        });
        setMaxParallel(16);
        setMaxParallelFlag(true);
        setTimeReduce(0.8);
        setTimeReduceFlag(true);
        setOverclocking(4);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("石油滋生者", new Object[0]));

        tooltip.add(I18n.format("默认耗时减免20%%"));
        tooltip.add(I18n.format("gtqtcore.machine.parallel.num", 16));
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityElectronOil(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCC", "CCC", "ACA")
                .aisle("CCC", "CDC", "ACA")
                .aisle("CCC", "CDC", "ACA")
                .aisle("CCC", "CDC", "ACA")
                .aisle("CCC", "CDC", "ACA")
                .aisle("CCC", "CDC", "ACA")
                .aisle("CCC", "CDC", "ACA")
                .aisle("CCC", "CDC", "ACA")
                .aisle("CCC", "CSC", "ACA")
                .where('S', selfPredicate())
                .where('A', states(MetaBlocks.FRAMES.get(Materials.StainlessSteel).getBlock(Materials.StainlessSteel)))
                .where('C', states(MetaBlocks.METAL_CASING.getState(MetalCasingType.STAINLESS_CLEAN))
                        .or(autoAbilities())
                )
                .where('D', states(MetaBlocks.BOILER_CASING.getState(BoilerCasingType.POLYTETRAFLUOROETHYLENE_PIPE)))
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.CLEAN_STAINLESS_STEEL_CASING;
    }
}