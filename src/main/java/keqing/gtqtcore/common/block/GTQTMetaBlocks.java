package keqing.gtqtcore.common.block;

import gregtech.client.model.SimpleStateMapper;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.client.render.pipe.PressurePipeRenderer;
import keqing.gtqtcore.common.block.blocks.*;
import keqing.gtqtcore.common.block.explosive.BlockSTNT;
import keqing.gtqtcore.common.block.wood.BlockPineLeaves;
import keqing.gtqtcore.common.block.wood.BlockPineLog;
import keqing.gtqtcore.common.block.wood.BlockPineSapling;
import keqing.gtqtcore.common.pipelike.pressure.BlockPressurePipe;
import keqing.gtqtcore.common.pipelike.pressure.PressurePipeType;
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
    public static final BlockPressurePipe[] PRESSURE_PIPES = new BlockPressurePipe[PressurePipeType.values().length];

    public static BlockActiveUniqueCasing blockActiveUniqueCasing;
    public static BlockActiveUniqueCasing1 blockActiveUniqueCasing1;
    public static BlockCleanroomCasing blockCleanroomCasing;
    public static BlockComponentAssemblyLineCasing blockComponentAssemblyLineCasing;
    public static BlockCompressedFusionReactor blockCompressedFusionReactor;
    public static BlockCoolingCoil blockCoolingCoil;
    public static BlockCrucible blockCrucible;
    public static BlockElectrolyticBath blockElectrolyticBath;
    public static BlockElectrolyticMicroscope blockElectrolyticMicroscope;
    public static BlockEnergyCell blockEnergyCell;
    public static BlockEvaporationBed blockEvaporationBed;
    public static BlockGravitonCasing blockGravitonCasing;
    public static BlockIsaCasing blockIsaCasing;
    public static BlockMisc blockMisc;
    public static BlockMultiblockCasing blockMultiblockCasing;
    public static BlockMultiblockCasing1 blockMultiblockCasing1;
    public static BlockMultiblockCasing2 blockMultiblockCasing2;
    public static BlockMultiblockCasing3 blockMultiblockCasing3;
    public static BlockMultiblockCasing4 blockMultiblockCasing4;
    public static BlockMultiblockCasing5 blockMultiblockCasing5;
    public static BlockMultiblockCasing6 blockMultiblockCasing6;
    public static BlockMultiblockCasing7 blockMultiblockCasing7;
    public static BlockFireboxCasing FIREBOX_CASING;
    public static BlockMultiblockCasingActive blockMultiblockCasingActive;
    public static BlockMultiblockGlass blockMultiblockGlass;
    public static BlockMultiblockGlass1 blockMultiblockGlass1;
    public static BlockNuclearCasing blockNuclearCasing;
    public static BlockPCBFactoryCasing blockPCBFactoryCasing;
    public static BlockParticleAcceleratorCasing blockParticleAcceleratorCasing;
    public static BlockQuantumCasing blockQuantumCasing;
    public static BlockQuantumForceTransformerCasing blockQuantumForceTransformerCasing;
    public static BlockStepperCasing blockStepperCasing;
    public static BlockTransparentCasing blockTransparentCasing;
    public static BlockWireCoil blockWireCoil;
    public static BlocksResearchSystem blocksResearchSystem;
    public static BlockSpacetimeCompression blockSpacetimeCompression;
    public static BlockStabilization blockStabilization;
    public static BlockTimeAcceleration blockTimeAcceleration;
    public static BlockSTNT STNT;

    public static final BlockPineLeaves BLOCK_PINE_LEAVES = new BlockPineLeaves();
    public static final BlockPineLog BLOCK_PINE_LOG = new BlockPineLog();
    public static final BlockPineSapling BLOCK_PINE_SAPLING = new BlockPineSapling();

    public static final EnumMap<GTQTStoneVariantBlock.StoneVariant, GTQTStoneVariantBlock> GTQT_STONE_BLOCKS = new EnumMap<>(GTQTStoneVariantBlock.StoneVariant.class);
    private GTQTMetaBlocks() {}

    public static void init() {

        blockActiveUniqueCasing = new BlockActiveUniqueCasing();
        blockActiveUniqueCasing.setRegistryName("active_unique_casing");

        blockActiveUniqueCasing1 = new BlockActiveUniqueCasing1();
        blockActiveUniqueCasing1.setRegistryName("active_unique_casing1");

        blockCleanroomCasing = new BlockCleanroomCasing();
        blockCleanroomCasing.setRegistryName("gtqtcore_cleanroom_casing");

        blockComponentAssemblyLineCasing = new BlockComponentAssemblyLineCasing();
        blockComponentAssemblyLineCasing.setRegistryName("component_assembly_line_casing");

        blockCompressedFusionReactor = new BlockCompressedFusionReactor();
        blockCompressedFusionReactor.setRegistryName("compressed_fusion_reactor");

        blockCoolingCoil = new BlockCoolingCoil();
        blockCoolingCoil.setRegistryName("cooling_coil");

        blockCrucible = new BlockCrucible();
        blockCrucible.setRegistryName("crucible");

        blockElectrolyticBath = new BlockElectrolyticBath();
        blockElectrolyticBath.setRegistryName("electrolytic_bath");

        blockElectrolyticMicroscope = new BlockElectrolyticMicroscope();
        blockElectrolyticMicroscope.setRegistryName("electron_microscope");

        blockEnergyCell = new BlockEnergyCell();
        blockEnergyCell.setRegistryName("energy_cell");

        blockEvaporationBed = new BlockEvaporationBed();
        blockEvaporationBed.setRegistryName("evaporation_bed");

        blockGravitonCasing = new BlockGravitonCasing();
        blockGravitonCasing.setRegistryName("graviton_casing");

        blockIsaCasing = new BlockIsaCasing();
        blockIsaCasing.setRegistryName("isa_casing");

        blockMisc = new BlockMisc();
        blockMisc.setRegistryName("misc");

        blockMultiblockCasing = new BlockMultiblockCasing();
        blockMultiblockCasing.setRegistryName("multiblock_casing");

        blockMultiblockCasing1 = new BlockMultiblockCasing1();
        blockMultiblockCasing1.setRegistryName("multiblock_casing1");

        blockMultiblockCasing2 = new BlockMultiblockCasing2();
        blockMultiblockCasing2.setRegistryName("multiblock_casing2");

        blockMultiblockCasing3 = new BlockMultiblockCasing3();
        blockMultiblockCasing3.setRegistryName("multiblock_casing3");

        blockMultiblockCasing4 = new BlockMultiblockCasing4();
        blockMultiblockCasing4.setRegistryName("multiblock_casing4");

        blockMultiblockCasing5 = new BlockMultiblockCasing5();
        blockMultiblockCasing5.setRegistryName("multiblock_casing5");

        blockMultiblockCasing6 = new BlockMultiblockCasing6();
        blockMultiblockCasing6.setRegistryName("multiblock_casing6");

        blockMultiblockCasing7 = new BlockMultiblockCasing7();
        blockMultiblockCasing7.setRegistryName("multiblock_casing7");

        FIREBOX_CASING = new BlockFireboxCasing();
        FIREBOX_CASING.setRegistryName("firebox_casing");

        blockMultiblockCasingActive = new BlockMultiblockCasingActive();
        blockMultiblockCasingActive.setRegistryName("multiblock_casing_active");

        blockMultiblockGlass = new BlockMultiblockGlass();
        blockMultiblockGlass.setRegistryName("multiblock_glass");

        blockMultiblockGlass1 = new BlockMultiblockGlass1();
        blockMultiblockGlass1.setRegistryName("multiblock_glass1");

        blockNuclearCasing = new BlockNuclearCasing();
        blockNuclearCasing.setRegistryName("nuclear_fusion");

        blockPCBFactoryCasing = new BlockPCBFactoryCasing();
        blockPCBFactoryCasing.setRegistryName("pcb_factory_casing");

        blockParticleAcceleratorCasing = new BlockParticleAcceleratorCasing();
        blockParticleAcceleratorCasing.setRegistryName("particle_accelerator");

        blockQuantumCasing = new BlockQuantumCasing();
        blockQuantumCasing.setRegistryName("quantum_casing");

        blockQuantumForceTransformerCasing = new BlockQuantumForceTransformerCasing();
        blockQuantumForceTransformerCasing.setRegistryName("quantum_force_transformer_casing");

        blockStepperCasing = new BlockStepperCasing();
        blockStepperCasing.setRegistryName("stepper");

        blockTransparentCasing = new BlockTransparentCasing();
        blockTransparentCasing.setRegistryName("transparent_casing");

        blockWireCoil = new BlockWireCoil();
        blockWireCoil.setRegistryName("wire_coil");

        blocksResearchSystem = new BlocksResearchSystem();
        blocksResearchSystem.setRegistryName("computer_competent");

        blockSpacetimeCompression = new BlockSpacetimeCompression();
        blockSpacetimeCompression.setRegistryName("spacetime_compression_field_generator");

        blockStabilization = new BlockStabilization();
        blockStabilization.setRegistryName("stabilization_field_generator");

        blockTimeAcceleration = new BlockTimeAcceleration();
        blockTimeAcceleration.setRegistryName("time_acceleration_field_generator");

        STNT = new BlockSTNT();
        STNT.setRegistryName("stnt").setTranslationKey("stnt");

        for (GTQTStoneVariantBlock.StoneVariant shape : GTQTStoneVariantBlock.StoneVariant.values()) {
            GTQT_STONE_BLOCKS.put(shape, new GTQTStoneVariantBlock(shape));
        }

        for (PressurePipeType type : PressurePipeType.values()) {
            PRESSURE_PIPES[type.ordinal()] = new BlockPressurePipe(type);
            PRESSURE_PIPES[type.ordinal()].setRegistryName(String.format("pressure_pipe_%s", type.name));
        }

    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        blockActiveUniqueCasing.onModelRegister();
        blockActiveUniqueCasing1.onModelRegister();
        registerItemModel(blockCleanroomCasing);
        registerItemModel(blockComponentAssemblyLineCasing);
        registerItemModel(blockCompressedFusionReactor);
        registerItemModel(blockCoolingCoil);
        registerItemModel(blockCrucible);
        registerItemModel(blockElectrolyticBath);
        registerItemModel(blockElectrolyticMicroscope);
        registerItemModel(blockEnergyCell);
        registerItemModel(blockEvaporationBed);
        registerItemModel(blockGravitonCasing);
        registerItemModel(blockIsaCasing);
        registerItemModel(blockMisc);
        registerItemModel(blockMultiblockCasing);
        registerItemModel(blockMultiblockCasing1);
        registerItemModel(blockMultiblockCasing2);
        registerItemModel(blockMultiblockCasing3);
        registerItemModel(blockMultiblockCasing4);
        registerItemModel(blockMultiblockCasing5);
        registerItemModel(blockMultiblockCasing6);
        registerItemModel(blockMultiblockCasing7);
        registerItemModel(FIREBOX_CASING);
        registerItemModel(blockMultiblockCasingActive);
        registerItemModel(blockMultiblockGlass);
        registerItemModel(blockMultiblockGlass1);
        registerItemModel(blockNuclearCasing);
        registerItemModel(blockPCBFactoryCasing);
        registerItemModel(blockParticleAcceleratorCasing);
        registerItemModel(blockQuantumCasing);
        registerItemModel(blockQuantumForceTransformerCasing);
        registerItemModel(blockStepperCasing);
        registerItemModel(blockTransparentCasing);
        registerItemModel(blockWireCoil);
        registerItemModel(blocksResearchSystem);
        registerItemModel(blockSpacetimeCompression);
        registerItemModel(blockStabilization);
        registerItemModel(blockTimeAcceleration);

        registerItemModel(STNT);
        registerItemModel(BLOCK_PINE_LEAVES);
        registerItemModel(BLOCK_PINE_LOG);
        registerItemModel(BLOCK_PINE_SAPLING);
        for (GTQTStoneVariantBlock block : GTQT_STONE_BLOCKS.values())
            registerItemModel(block);

        IStateMapper normalStateMapper = new SimpleStateMapper(PressurePipeRenderer.INSTANCE.getModelLocation());
        for (BlockPressurePipe pipe : PRESSURE_PIPES) {
            ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(pipe), stack -> PressurePipeRenderer.INSTANCE.getModelLocation());
            ModelLoader.setCustomStateMapper(pipe, normalStateMapper);
        }
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