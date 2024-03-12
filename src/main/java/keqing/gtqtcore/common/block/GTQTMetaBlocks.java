package keqing.gtqtcore.common.block;

import gregtech.api.block.VariantBlock;
import gregtech.client.model.SimpleStateMapper;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.client.renderer.pipe.PressurePipeRenderer;
import keqing.gtqtcore.common.block.blocks.*;
import keqing.gtqtcore.common.block.wood.BlockPineLeaves;
import keqing.gtqtcore.common.block.wood.BlockPineLog;
import keqing.gtqtcore.common.block.wood.BlockPineSapling;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumMap;

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
    public static GTQTRoad ROAD;
    public static GTQTKQCC KQCC;
    public static GTQTBlockGlassCasing GLASS_CASING;
    public static GTQTStepper STEPPER;
    public static GTQTSpaceElevator SPACE_ELEVATOR;
    public static final BlockPineLeaves PINE_LEAVES = new BlockPineLeaves();
    public static final BlockPineLog PINE_LOG = new BlockPineLog();
    public static final BlockPineSapling PINE_SAPLING = new BlockPineSapling();
    public static BlockCrucible CRUCIBLE;
    public static BlockGCYSMultiblockCasing MULTIBLOCK_CASING;
    public static BlockGCYSMultiblockCasingActive MULTIBLOCK_CASING_ACTIVE;
    public static BlockTransparentCasing TRANSPARENT_CASING;
    public static final EnumMap<GTQTStoneVariantBlock.StoneVariant, GTQTStoneVariantBlock> SUSY_STONE_BLOCKS = new EnumMap<>(GTQTStoneVariantBlock.StoneVariant.class);
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
        SPACE_ELEVATOR = new GTQTSpaceElevator();
        SPACE_ELEVATOR.setRegistryName("space_elevator");
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
        KQCC = new GTQTKQCC();
        KQCC.setRegistryName("computer_competent");
        GLASS_CASING = new GTQTBlockGlassCasing();
        GLASS_CASING.setRegistryName("glass_casing");
        STEPPER = new GTQTStepper();
        STEPPER.setRegistryName("stepper");

        ROAD = new GTQTRoad();
        ROAD.setRegistryName("road");

        CRUCIBLE = new BlockCrucible();
        CRUCIBLE.setRegistryName("crucible");
        MULTIBLOCK_CASING = new BlockGCYSMultiblockCasing();
        MULTIBLOCK_CASING.setRegistryName("multiblock_casing1");
        MULTIBLOCK_CASING_ACTIVE = new BlockGCYSMultiblockCasingActive();
        MULTIBLOCK_CASING_ACTIVE.setRegistryName("multiblock_casing_active");
        TRANSPARENT_CASING = new BlockTransparentCasing();
        TRANSPARENT_CASING.setRegistryName("transparent_casing");


        for (GTQTStoneVariantBlock.StoneVariant shape : GTQTStoneVariantBlock.StoneVariant.values()) {
            SUSY_STONE_BLOCKS.put(shape, new GTQTStoneVariantBlock(shape));
        }
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
        registerItemModel(SPACE_ELEVATOR);
        registerItemModel(TURBINE_CASING);
        registerItemModel(ROAD);
        registerItemModel(TURBINE_CASING1);
        registerItemModel(QUANTUM_CONSTRAINT_CASING);
        registerItemModel(COMPONENT_ASSEMBLY_LINE);
        registerItemModel(ISA_CASING);
        registerItemModel(KQCC);
        registerItemModel(GLASS_CASING);
        registerItemModel(STEPPER);

        registerItemModel(CRUCIBLE);
        registerItemModel(MULTIBLOCK_CASING);
        registerItemModel(MULTIBLOCK_CASING_ACTIVE);
        registerItemModel(TRANSPARENT_CASING);


        for (GTQTStoneVariantBlock block : SUSY_STONE_BLOCKS.values())
            registerItemModel(block);
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