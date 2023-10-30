package keqing.gtqtcore.common.block;

import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTADVGlass;
import keqing.gtqtcore.common.block.blocks.GTQTMultiblockCasing;
import keqing.gtqtcore.common.block.blocks.GTQTADVBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GTQTMetaBlocks {
    public static GTQTMultiblockCasing MULTI_CASING;
    public static GTQTADVBlock ADV_BLOCK;
    public static GTQTADVGlass ADV_GLASS;

    private GTQTMetaBlocks() {}

    public static void init() {
        MULTI_CASING = new GTQTMultiblockCasing();
        MULTI_CASING.setRegistryName("multiblock_casing");
        ADV_BLOCK = new GTQTADVBlock();
        ADV_BLOCK.setRegistryName("adv_block");
        ADV_GLASS = new GTQTADVGlass();
        ADV_GLASS.setRegistryName("adv_glass");

    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        registerItemModel(MULTI_CASING);
        registerItemModel(ADV_BLOCK);
        registerItemModel(ADV_GLASS);

    }

    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Block block) {

        for (IBlockState state : block.getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),
                    block.getMetaFromState(state),
                    new ModelResourceLocation(block.getRegistryName(),
                            MetaBlocks.statePropertiesToString(state.getProperties())));
        }

    }

}