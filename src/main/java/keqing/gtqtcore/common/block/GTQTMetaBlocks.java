package keqing.gtqtcore.common.block;

import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.common.block.blocks.*;
import keqing.gtqtcore.common.block.wood.BlockPineLeaves;
import keqing.gtqtcore.common.block.wood.BlockPineLog;
import keqing.gtqtcore.common.block.wood.BlockPineSapling;
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
    public static GTQTTurbineCasing1 TURBINE_CASING1;
    public static GTQTQuantumForceTransformerCasing QUANTUM_CONSTRAINT_CASING;
    public static GTQTBlockComponentAssemblyLineCasing COMPONENT_ASSEMBLY_LINE;
    public static GTQTIsaCasing ISA_CASING;

    public static GTQTElectrobath ELECTROBATH;
    public static final BlockPineLeaves PINE_LEAVES = new BlockPineLeaves();
    public static final BlockPineLog PINE_LOG = new BlockPineLog();
    public static final BlockPineSapling PINE_SAPLING = new BlockPineSapling();
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
        TURBINE_CASING1 = new GTQTTurbineCasing1();
        TURBINE_CASING1.setRegistryName("turbine_casing1");
        QUANTUM_CONSTRAINT_CASING = new GTQTQuantumForceTransformerCasing();
        QUANTUM_CONSTRAINT_CASING.setRegistryName("quantum_force_transformer_casing");
        COMPONENT_ASSEMBLY_LINE = new GTQTBlockComponentAssemblyLineCasing();
        COMPONENT_ASSEMBLY_LINE.setRegistryName("component_assembly_line_casing");
        ISA_CASING = new GTQTIsaCasing();
        ISA_CASING.setRegistryName("isa_casing");
        ELECTROBATH = new GTQTElectrobath();
        ELECTROBATH.setRegistryName("electrobath");
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        registerItemModel(PINE_LEAVES);
        registerItemModel(PINE_LOG);
        registerItemModel(PINE_SAPLING);
        registerItemModel(ELECTROBATH);
        registerItemModel(MULTI_CASING);
        registerItemModel(ADV_BLOCK);
        registerItemModel(ADV_GLASS);
        registerItemModel(WIRE_COIL);
        registerItemModel(QUANTUM_CASING);
        registerItemModel(TURBINE_CASING);
        registerItemModel(TURBINE_CASING1);
        registerItemModel(QUANTUM_CONSTRAINT_CASING);
        registerItemModel(COMPONENT_ASSEMBLY_LINE);
        registerItemModel(ISA_CASING);
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