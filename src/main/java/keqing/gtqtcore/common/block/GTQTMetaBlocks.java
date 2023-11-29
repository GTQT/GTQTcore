package keqing.gtqtcore.common.block;

import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.common.block.blocks.*;
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
    public static GTQTBlockWireCoil WIRE_COIL;
    public static GTQTQuantumCasing QUANTUM_CASING;
    public static GTQTTurbineCasing TURBINE_CASING;

    private GTQTMetaBlocks() {}

    public static void init() {
        MULTI_CASING = new GTQTMultiblockCasing();
        MULTI_CASING.setRegistryName("multiblock_casing");
        ADV_BLOCK = new GTQTADVBlock();
        ADV_BLOCK.setRegistryName("adv_block");
        ADV_GLASS = new GTQTADVGlass();
        ADV_GLASS.setRegistryName("adv_glass");
        WIRE_COIL = new GTQTBlockWireCoil();
        WIRE_COIL.setRegistryName("wire_coil");
        QUANTUM_CASING = new GTQTQuantumCasing();
        QUANTUM_CASING.setRegistryName("quantum_casing");
        TURBINE_CASING = new GTQTTurbineCasing();
        TURBINE_CASING.setRegistryName("turbine_casing");

    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        registerItemModel(MULTI_CASING);
        registerItemModel(ADV_BLOCK);
        registerItemModel(ADV_GLASS);
        registerItemModel(WIRE_COIL);
        registerItemModel(QUANTUM_CASING);
        registerItemModel(TURBINE_CASING);

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