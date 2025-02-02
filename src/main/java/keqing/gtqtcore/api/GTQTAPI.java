package keqing.gtqtcore.api;

import gregtech.common.blocks.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import keqing.gtqtcore.api.blocks.IBlockTier;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metaileentity.multiblock.ICellData;
import keqing.gtqtcore.api.module.IModuleManager;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.*;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockGlass1;
import net.minecraft.block.state.IBlockState;

import static keqing.gtqtcore.common.block.GTQTMetaBlocks.*;
import static keqing.gtqtcore.common.block.blocks.BlockGravitonCasing.GravitonCasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockGravitonCasing.GravitonCasingType.CENTRAL_GRAVITON_FLOW_MODULATOR;
import static keqing.gtqtcore.common.block.blocks.BlockCompressedFusionReactor.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockCompressedFusionReactor.CasingType.FUSION_COIL_MKIV;
import static keqing.gtqtcore.common.block.blocks.BlockElectrolyticMicroscope.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockQuantumCasing.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockQuantumCasing.CasingType.ULTIMATE_HIGH_ENERGY_CASING;

public class GTQTAPI {
    public static final Object2ObjectOpenHashMap<IBlockState, IBlockTier> MAP_QFT_GLASS= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_DRI_CASING= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_QFT_SHIELDING_CORE= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_QFT_MANIPULATOR= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_MACHINE_CASING= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_GLASS= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_LGLASS= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_CP_CASING= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_CP_TUBE= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_CP_BEAM= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_CAL_CASING= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_PA_CASING= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_SP_CASING= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_PA_INTERNAL_CASING= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_DC_CASING= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_ND_CASING= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_FU_CASING= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState, ICellData> MAP_ES_CELLS = new Object2ObjectOpenHashMap<>();
    //电子透镜
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_LS_CASING= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_SS_CASING= new Object2ObjectOpenHashMap<>();
    //光刻系统
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_ZW_CASING= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_TJ_CASING= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_ZJ_CASING= new Object2ObjectOpenHashMap<>();
    //计算机

    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_PAF_CASING= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_PAE_CASING= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_PAV_CASING= new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState,IBlockTier> MAP_FUSION_COIL= new Object2ObjectOpenHashMap<>();

    public static void init() {
        //  Energy Cell Init
        for (BlockEnergyCell.CellTier tier : BlockEnergyCell.CellTier.values()) {
            MAP_ES_CELLS.put(GTQTMetaBlocks.blockEnergyCell.getState(tier), tier);
        }

        MAP_LS_CASING.put(GTQTMetaBlocks.blockElectrolyticMicroscope.getState(LENS_I),
                new WrappedIntTired(LENS_I, 1));
        MAP_LS_CASING.put(GTQTMetaBlocks.blockElectrolyticMicroscope.getState(LENS_II),
                new WrappedIntTired(LENS_II, 2));
        MAP_LS_CASING.put(GTQTMetaBlocks.blockElectrolyticMicroscope.getState(LENS_III),
                new WrappedIntTired(LENS_III, 3));
        MAP_LS_CASING.put(GTQTMetaBlocks.blockElectrolyticMicroscope.getState(LENS_IV),
                new WrappedIntTired(LENS_IV, 4));
        MAP_LS_CASING.put(GTQTMetaBlocks.blockElectrolyticMicroscope.getState(LENS_V),
                new WrappedIntTired(LENS_V, 5));

        MAP_SS_CASING.put(GTQTMetaBlocks.blockElectrolyticMicroscope.getState(SOURSE_I),
                new WrappedIntTired(SOURSE_I, 1));
        MAP_SS_CASING.put(GTQTMetaBlocks.blockElectrolyticMicroscope.getState(SOURSE_II),
                new WrappedIntTired(SOURSE_II, 2));
        MAP_SS_CASING.put(GTQTMetaBlocks.blockElectrolyticMicroscope.getState(SOURSE_III),
                new WrappedIntTired(SOURSE_III, 3));
        MAP_SS_CASING.put(GTQTMetaBlocks.blockElectrolyticMicroscope.getState(SOURSE_IV),
                new WrappedIntTired(SOURSE_IV, 4));
        MAP_SS_CASING.put(GTQTMetaBlocks.blockElectrolyticMicroscope.getState(SOURSE_V),
                new WrappedIntTired(SOURSE_V, 5));

        //  Fusion Coil
        MAP_FUSION_COIL.put(MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL),
                new WrappedIntTired(gregtech.common.blocks.BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL, 1));
        MAP_FUSION_COIL.put(MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_COIL),
                new WrappedIntTired(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_COIL, 2));

        MAP_FUSION_COIL.put(GTQTMetaBlocks.blockCompressedFusionReactor.getState(FUSION_COIL_MKII),
                new WrappedIntTired(FUSION_COIL_MKII, 3));
        MAP_FUSION_COIL.put(GTQTMetaBlocks.blockCompressedFusionReactor.getState(FUSION_COIL_MKIII),
                new WrappedIntTired(FUSION_COIL_MKIII, 4));
        MAP_FUSION_COIL.put(GTQTMetaBlocks.blockCompressedFusionReactor.getState(FUSION_COIL_MKIV),
                new WrappedIntTired(FUSION_COIL_MKIV, 5));

        MAP_DC_CASING.put(blockQuantumCasing.getState(HIGH_ENERGY_CASING),
                new WrappedIntTired(HIGH_ENERGY_CASING, 1));
        MAP_DC_CASING.put(blockQuantumCasing.getState(ADVANCED_HIGH_ENERGY_CASING),
                new WrappedIntTired(ADVANCED_HIGH_ENERGY_CASING, 2));
        MAP_DC_CASING.put(blockQuantumCasing.getState(ULTIMATE_HIGH_ENERGY_CASING),
                new WrappedIntTired(ULTIMATE_HIGH_ENERGY_CASING, 3));

        MAP_ND_CASING.put(blockGravitonCasing.getState(REMOTE_GRAVITON_FLOW_MODULATOR),
                new WrappedIntTired(REMOTE_GRAVITON_FLOW_MODULATOR, 1));
        MAP_ND_CASING.put(blockGravitonCasing.getState(MEDIAL_GRAVITON_FLOW_MODULATOR),
                new WrappedIntTired(MEDIAL_GRAVITON_FLOW_MODULATOR, 2));
        MAP_ND_CASING.put(blockGravitonCasing.getState(CENTRAL_GRAVITON_FLOW_MODULATOR),
                new WrappedIntTired(CENTRAL_GRAVITON_FLOW_MODULATOR, 3));

        MAP_FU_CASING.put(blockMultiblockCasing.getState(MACHINE_CASING_FUSION),
                new WrappedIntTired(MACHINE_CASING_FUSION, 1));
        MAP_FU_CASING.put(blockMultiblockCasing.getState(MACHINE_CASING_FUSION_2),
                new WrappedIntTired(MACHINE_CASING_FUSION_2, 2));
        MAP_FU_CASING.put(blockMultiblockCasing.getState(MACHINE_CASING_FUSION_3),
                new WrappedIntTired(MACHINE_CASING_FUSION_3, 3));

        MAP_PA_CASING.put(GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK1),
                new WrappedIntTired(BlockMultiblockCasing5.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK1, 1));
        MAP_PA_CASING.put(GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK2),
                new WrappedIntTired(BlockMultiblockCasing5.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK2, 2));
        MAP_PA_CASING.put(GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK3),
                new WrappedIntTired(BlockMultiblockCasing5.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK3, 3));
        MAP_PA_CASING.put(GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK4),
                new WrappedIntTired(BlockMultiblockCasing5.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK4, 4));
        MAP_PA_CASING.put(GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK5),
                new WrappedIntTired(BlockMultiblockCasing5.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK5, 5));
        MAP_PA_CASING.put(GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK6),
                new WrappedIntTired(BlockMultiblockCasing5.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK6, 6));

        MAP_PA_INTERNAL_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.LuV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.LuV, 1));
        MAP_PA_INTERNAL_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.ZPM),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.ZPM, 2));
        MAP_PA_INTERNAL_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.UV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.UV, 3));

        MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.LV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.LV,1));
        MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.MV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.MV,2));
        MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.HV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.HV,3));
        MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.EV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.EV,4));
        MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.IV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.IV,5));
        MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.LuV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.LuV,6));
        MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.ZPM),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.ZPM,7));
        MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.UV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.UV,8));
        MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.UHV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.UHV,9));
        MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.UEV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.UEV,10));
        MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.UIV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.UIV,11));
        MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.UXV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.UXV,12));
        MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.OpV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.OpV,13));
        MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.MAX),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.MAX,14));


        MAP_CP_CASING.put(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.BRONZE_BRICKS),
                new WrappedIntTired(BlockMetalCasing.MetalCasingType.BRONZE_BRICKS,1));
        MAP_CP_CASING.put(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID),
                new WrappedIntTired(BlockMetalCasing.MetalCasingType.STEEL_SOLID,2));
        MAP_CP_CASING.put(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.ALUMINIUM_FROSTPROOF),
                new WrappedIntTired(BlockMetalCasing.MetalCasingType.ALUMINIUM_FROSTPROOF,3));
        MAP_CP_CASING.put(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN),
                new WrappedIntTired(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN,4));
        MAP_CP_CASING.put(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TITANIUM_STABLE),
                new WrappedIntTired(BlockMetalCasing.MetalCasingType.TITANIUM_STABLE,5));
        MAP_CP_CASING.put(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST),
                new WrappedIntTired(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST,6));
        MAP_CP_CASING.put(GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.PD_TURBINE_CASING),
                new WrappedIntTired(BlockMultiblockCasing4.TurbineCasingType.PD_TURBINE_CASING,7));
        MAP_CP_CASING.put(GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.NQ_TURBINE_CASING),
                new WrappedIntTired(BlockMultiblockCasing4.TurbineCasingType.NQ_TURBINE_CASING,8));
        MAP_CP_CASING.put(GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.ST_TURBINE_CASING),
                new WrappedIntTired(BlockMultiblockCasing5.TurbineCasingType.ST_TURBINE_CASING,9));
        MAP_CP_CASING.put(GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.AD_TURBINE_CASING),
                new WrappedIntTired(BlockMultiblockCasing5.TurbineCasingType.AD_TURBINE_CASING,10));

        MAP_CP_TUBE.put(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.BRONZE_PIPE),
                new WrappedIntTired(BlockBoilerCasing.BoilerCasingType.BRONZE_PIPE,1));
        MAP_CP_TUBE.put(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE),
                new WrappedIntTired(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE,2));
        MAP_CP_TUBE.put(GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.AL_TURBINE_CASING),
                new WrappedIntTired(BlockMultiblockCasing5.TurbineCasingType.AL_TURBINE_CASING,3));
        MAP_CP_TUBE.put(GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.SA_TURBINE_CASING),
                new WrappedIntTired(BlockMultiblockCasing5.TurbineCasingType.SA_TURBINE_CASING,4));
        MAP_CP_TUBE.put(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TITANIUM_PIPE),
                new WrappedIntTired(BlockBoilerCasing.BoilerCasingType.BRONZE_PIPE,5));
        MAP_CP_TUBE.put(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE),
                new WrappedIntTired(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE,6));
        MAP_CP_TUBE.put(GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.PD_MACHINE_CASING),
                new WrappedIntTired(BlockMultiblockCasing4.TurbineCasingType.PD_MACHINE_CASING,7));
        MAP_CP_TUBE.put(GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.NQ_MACHINE_CASING),
                new WrappedIntTired(BlockMultiblockCasing4.TurbineCasingType.NQ_MACHINE_CASING,8));
        MAP_CP_TUBE.put(GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.ST_MACHINE_CASING),
                new WrappedIntTired(BlockMultiblockCasing5.TurbineCasingType.ST_MACHINE_CASING,9));
        MAP_CP_TUBE.put(GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.AD_MACHINE_CASING),
                new WrappedIntTired(BlockMultiblockCasing5.TurbineCasingType.AD_MACHINE_CASING,10));


        MAP_GLASS.put(MetaBlocks.TRANSPARENT_CASING.getState(gregtech.common.blocks.BlockGlassCasing.CasingType.FUSION_GLASS),
                new WrappedIntTired(gregtech.common.blocks.BlockGlassCasing.CasingType.FUSION_GLASS,1));

        MAP_GLASS.put(GTQTMetaBlocks.blockMultiblockGlass.getState(BlockMultiblockGlass.CasingType.TECH_FUSION_GLASS_IV),
                new WrappedIntTired(BlockMultiblockGlass.CasingType.TECH_FUSION_GLASS_IV,2));

        MAP_GLASS.put(GTQTMetaBlocks.blockMultiblockGlass.getState(BlockMultiblockGlass.CasingType.TECH_FUSION_GLASS_V),
                new WrappedIntTired(BlockMultiblockGlass.CasingType.TECH_FUSION_GLASS_V,3));

        MAP_GLASS.put(GTQTMetaBlocks.blockMultiblockGlass.getState(BlockMultiblockGlass.CasingType.TECH_FUSION_GLASS_VI),
                new WrappedIntTired(BlockMultiblockGlass.CasingType.TECH_FUSION_GLASS_VI,4));

        //  MAP_QFT_MANIPULATOR Init
        MAP_QFT_MANIPULATOR.put(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getState(BlockQuantumForceTransformerCasing.CasingType.NEUTRON_PULSE_MANIPULATOR_CASING),
                new WrappedIntTired(BlockQuantumForceTransformerCasing.CasingType.NEUTRON_PULSE_MANIPULATOR_CASING, 1));
        MAP_QFT_MANIPULATOR.put(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getState(BlockQuantumForceTransformerCasing.CasingType.COSMIC_FABRIC_MANIPULATOR_CASING),
                new WrappedIntTired(BlockQuantumForceTransformerCasing.CasingType.COSMIC_FABRIC_MANIPULATOR_CASING, 2));
        MAP_QFT_MANIPULATOR.put(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getState(BlockQuantumForceTransformerCasing.CasingType.INFINITY_INFUSED_MANIPULATOR_CASING),
                new WrappedIntTired(BlockQuantumForceTransformerCasing.CasingType.INFINITY_INFUSED_MANIPULATOR_CASING, 3));
        MAP_QFT_MANIPULATOR.put(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getState(BlockQuantumForceTransformerCasing.CasingType.SUPRACAUSAL_CONTINUUM_MANIPULATOR_CASING),
                new WrappedIntTired(BlockQuantumForceTransformerCasing.CasingType.SUPRACAUSAL_CONTINUUM_MANIPULATOR_CASING, 4));

        //  MAP_QFT_SHIELDING_CORE Init
        MAP_QFT_SHIELDING_CORE.put(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getState(BlockQuantumForceTransformerCasing.CasingType.NEUTRON_SHIELDING_CORE_CASING),
                new WrappedIntTired(BlockQuantumForceTransformerCasing.CasingType.NEUTRON_SHIELDING_CORE_CASING, 1));
        MAP_QFT_SHIELDING_CORE.put(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getState(BlockQuantumForceTransformerCasing.CasingType.COSMIC_FABRIC_SHIELDING_CORE_CASING),
                new WrappedIntTired(BlockQuantumForceTransformerCasing.CasingType.COSMIC_FABRIC_SHIELDING_CORE_CASING, 2));
        MAP_QFT_SHIELDING_CORE.put(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getState(BlockQuantumForceTransformerCasing.CasingType.INFINITY_INFUSED_SHIELDING_CORE_CASING),
                new WrappedIntTired(BlockQuantumForceTransformerCasing.CasingType.INFINITY_INFUSED_SHIELDING_CORE_CASING, 3));
        MAP_QFT_SHIELDING_CORE.put(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getState(BlockQuantumForceTransformerCasing.CasingType.SUPRACAUSAL_CONTINUUM_SHIELDING_CORE_CASING),
                new WrappedIntTired(BlockQuantumForceTransformerCasing.CasingType.SUPRACAUSAL_CONTINUUM_SHIELDING_CORE_CASING, 4));

        //  MAP_QFT_GLASS Init
        MAP_QFT_GLASS.put(GTQTMetaBlocks.blockMultiblockGlass.getState(BlockMultiblockGlass.CasingType.ADV_MACHINE_GLASS),
                new WrappedIntTired(BlockMultiblockGlass.CasingType.ADV_MACHINE_GLASS, 1));
        MAP_QFT_GLASS.put(GTQTMetaBlocks.blockMultiblockGlass.getState(BlockMultiblockGlass.CasingType.ADV_MACHINE_GLASS_B),
                new WrappedIntTired(BlockMultiblockGlass.CasingType.ADV_MACHINE_GLASS_B, 2));
        MAP_QFT_GLASS.put(GTQTMetaBlocks.blockMultiblockGlass.getState(BlockMultiblockGlass.CasingType.ADV_MACHINE_GLASS_G),
                new WrappedIntTired(BlockMultiblockGlass.CasingType.ADV_MACHINE_GLASS_G, 3));
        MAP_QFT_GLASS.put(GTQTMetaBlocks.blockMultiblockGlass.getState(BlockMultiblockGlass.CasingType.ADV_MACHINE_GLASS_O),
                new WrappedIntTired(BlockMultiblockGlass.CasingType.ADV_MACHINE_GLASS_O, 4));
         MAP_QFT_GLASS.put(GTQTMetaBlocks.blockMultiblockGlass.getState(BlockMultiblockGlass.CasingType.ADV_MACHINE_GLASS_P),
                new WrappedIntTired(BlockMultiblockGlass.CasingType.ADV_MACHINE_GLASS_P, 5));
        MAP_QFT_GLASS.put(GTQTMetaBlocks.blockMultiblockGlass.getState(BlockMultiblockGlass.CasingType.ADV_MACHINE_GLASS_R),
                new WrappedIntTired(BlockMultiblockGlass.CasingType.ADV_MACHINE_GLASS_R, 6));
        //lglass
        MAP_LGLASS.put(blockMultiblockGlass1.getState(BlockMultiblockGlass1.CasingType.SILICATE_GLASS),
                new WrappedIntTired(BlockMultiblockGlass1.CasingType.SILICATE_GLASS, 1));
        MAP_LGLASS.put(GTQTMetaBlocks.blockMultiblockGlass1.getState(BlockMultiblockGlass1.CasingType.THY_SILICATE_GLASS),
                new WrappedIntTired(BlockMultiblockGlass1.CasingType.THY_SILICATE_GLASS, 2));
        MAP_LGLASS.put(GTQTMetaBlocks.blockMultiblockGlass1.getState(BlockMultiblockGlass1.CasingType.TI_BORON_SILICATE_GLASS),
                new WrappedIntTired(BlockMultiblockGlass1.CasingType.TI_BORON_SILICATE_GLASS, 3));
        MAP_LGLASS.put(GTQTMetaBlocks.blockMultiblockGlass1.getState(BlockMultiblockGlass1.CasingType.W_BORON_SILICATE_GLASS),
                new WrappedIntTired(BlockMultiblockGlass1.CasingType.W_BORON_SILICATE_GLASS, 4));
        MAP_LGLASS.put(GTQTMetaBlocks.blockMultiblockGlass1.getState(BlockMultiblockGlass1.CasingType.OSMIR_BORON_SILICATE_GLASS),
                new WrappedIntTired(BlockMultiblockGlass1.CasingType.OSMIR_BORON_SILICATE_GLASS, 5));
        MAP_LGLASS.put(GTQTMetaBlocks.blockMultiblockGlass1.getState(BlockMultiblockGlass1.CasingType.NAQ_BORON_SILICATE_GLASS),
                new WrappedIntTired(BlockMultiblockGlass1.CasingType.NAQ_BORON_SILICATE_GLASS, 6));
        MAP_LGLASS.put(GTQTMetaBlocks.blockMultiblockGlass1.getState(BlockMultiblockGlass1.CasingType.FORCE_FIELD_CONSTRAINED_GLASS),
                new WrappedIntTired(BlockMultiblockGlass1.CasingType.FORCE_FIELD_CONSTRAINED_GLASS, 7));
        MAP_LGLASS.put(GTQTMetaBlocks.blockMultiblockGlass1.getState(BlockMultiblockGlass1.CasingType.COSMIC_MICROWAVE_BACKGROUND_RADIATION_ABSORPTION_GLASS),
                new WrappedIntTired(BlockMultiblockGlass1.CasingType.COSMIC_MICROWAVE_BACKGROUND_RADIATION_ABSORPTION_GLASS, 8));
        MAP_LGLASS.put(GTQTMetaBlocks.blockMultiblockGlass1.getState(BlockMultiblockGlass1.CasingType.SPACETIME_SUPERCONDENSER_GLASS),
                new WrappedIntTired(BlockMultiblockGlass1.CasingType.SPACETIME_SUPERCONDENSER_GLASS, 9));
        MAP_LGLASS.put(GTQTMetaBlocks.blockMultiblockGlass1.getState(BlockMultiblockGlass1.CasingType.SUPRACAUSAL_LIGHT_CONE_GLASS),
                new WrappedIntTired(BlockMultiblockGlass1.CasingType.SUPRACAUSAL_LIGHT_CONE_GLASS, 10));

        MAP_CAL_CASING.put(GTQTMetaBlocks.blockComponentAssemblyLineCasing.getState(BlockComponentAssemblyLineCasing.CasingTier.LV),
                new WrappedIntTired(BlockComponentAssemblyLineCasing.CasingTier.LV, 1));
        MAP_CAL_CASING.put(GTQTMetaBlocks.blockComponentAssemblyLineCasing.getState(BlockComponentAssemblyLineCasing.CasingTier.MV),
                new WrappedIntTired(BlockComponentAssemblyLineCasing.CasingTier.MV, 2));
        MAP_CAL_CASING.put(GTQTMetaBlocks.blockComponentAssemblyLineCasing.getState(BlockComponentAssemblyLineCasing.CasingTier.HV),
                new WrappedIntTired(BlockComponentAssemblyLineCasing.CasingTier.HV, 3));
        MAP_CAL_CASING.put(GTQTMetaBlocks.blockComponentAssemblyLineCasing.getState(BlockComponentAssemblyLineCasing.CasingTier.EV),
                new WrappedIntTired(BlockComponentAssemblyLineCasing.CasingTier.EV, 4));
        MAP_CAL_CASING.put(GTQTMetaBlocks.blockComponentAssemblyLineCasing.getState(BlockComponentAssemblyLineCasing.CasingTier.IV),
                new WrappedIntTired(BlockComponentAssemblyLineCasing.CasingTier.IV, 5));
        MAP_CAL_CASING.put(GTQTMetaBlocks.blockComponentAssemblyLineCasing.getState(BlockComponentAssemblyLineCasing.CasingTier.LuV),
                new WrappedIntTired(BlockComponentAssemblyLineCasing.CasingTier.LuV, 6));
        MAP_CAL_CASING.put(GTQTMetaBlocks.blockComponentAssemblyLineCasing.getState(BlockComponentAssemblyLineCasing.CasingTier.ZPM),
                new WrappedIntTired(BlockComponentAssemblyLineCasing.CasingTier.ZPM, 7));
        MAP_CAL_CASING.put(GTQTMetaBlocks.blockComponentAssemblyLineCasing.getState(BlockComponentAssemblyLineCasing.CasingTier.UV),
                new WrappedIntTired(BlockComponentAssemblyLineCasing.CasingTier.UV, 8));
        MAP_CAL_CASING.put(GTQTMetaBlocks.blockComponentAssemblyLineCasing.getState(BlockComponentAssemblyLineCasing.CasingTier.UHV),
                new WrappedIntTired(BlockComponentAssemblyLineCasing.CasingTier.UHV, 9));
        MAP_CAL_CASING.put(GTQTMetaBlocks.blockComponentAssemblyLineCasing.getState(BlockComponentAssemblyLineCasing.CasingTier.UEV),
                new WrappedIntTired(BlockComponentAssemblyLineCasing.CasingTier.UEV, 10));
        MAP_CAL_CASING.put(GTQTMetaBlocks.blockComponentAssemblyLineCasing.getState(BlockComponentAssemblyLineCasing.CasingTier.UIV),
                new WrappedIntTired(BlockComponentAssemblyLineCasing.CasingTier.UIV, 11));
        MAP_CAL_CASING.put(GTQTMetaBlocks.blockComponentAssemblyLineCasing.getState(BlockComponentAssemblyLineCasing.CasingTier.UXV),
                new WrappedIntTired(BlockComponentAssemblyLineCasing.CasingTier.UXV, 12));
        MAP_CAL_CASING.put(GTQTMetaBlocks.blockComponentAssemblyLineCasing.getState(BlockComponentAssemblyLineCasing.CasingTier.OpV),
                new WrappedIntTired(BlockComponentAssemblyLineCasing.CasingTier.OpV, 13));
        MAP_CAL_CASING.put(GTQTMetaBlocks.blockComponentAssemblyLineCasing.getState(BlockComponentAssemblyLineCasing.CasingTier.MAX),
                new WrappedIntTired(BlockComponentAssemblyLineCasing.CasingTier.MAX, 14));

        MAP_DRI_CASING.put(GTQTMetaBlocks.blockElectrolyticBath.getState(BlockElectrolyticBath.CasingType.DRILL_HEAD_LV),
                new WrappedIntTired(BlockElectrolyticBath.CasingType.DRILL_HEAD_LV, 1));
        MAP_DRI_CASING.put(GTQTMetaBlocks.blockElectrolyticBath.getState(BlockElectrolyticBath.CasingType.DRILL_HEAD_MV),
                new WrappedIntTired(BlockElectrolyticBath.CasingType.DRILL_HEAD_MV, 2));
        MAP_DRI_CASING.put(GTQTMetaBlocks.blockElectrolyticBath.getState(BlockElectrolyticBath.CasingType.DRILL_HEAD_HV),
                new WrappedIntTired(BlockElectrolyticBath.CasingType.DRILL_HEAD_HV, 3));
        MAP_DRI_CASING.put(GTQTMetaBlocks.blockElectrolyticBath.getState(BlockElectrolyticBath.CasingType.DRILL_HEAD_EV),
                new WrappedIntTired(BlockElectrolyticBath.CasingType.DRILL_HEAD_EV, 4));


        MAP_SP_CASING.put(GTQTMetaBlocks.blockElectrolyticBath.getState(BlockElectrolyticBath.CasingType.SOLAR_PLATE_LV),
                new WrappedIntTired(BlockElectrolyticBath.CasingType.SOLAR_PLATE_LV, 1));
        MAP_SP_CASING.put(GTQTMetaBlocks.blockElectrolyticBath.getState(BlockElectrolyticBath.CasingType.SOLAR_PLATE_MV),
                new WrappedIntTired(BlockElectrolyticBath.CasingType.SOLAR_PLATE_MV, 2));
        MAP_SP_CASING.put(GTQTMetaBlocks.blockElectrolyticBath.getState(BlockElectrolyticBath.CasingType.SOLAR_PLATE_HV),
                new WrappedIntTired(BlockElectrolyticBath.CasingType.SOLAR_PLATE_MV, 3));

        MAP_ZW_CASING.put(GTQTMetaBlocks.blockStepperCasing.getState(BlockStepperCasing.CasingType.LASER_MKI),
                new WrappedIntTired(BlockStepperCasing.CasingType.LASER_MKI, 1));
        MAP_TJ_CASING.put(GTQTMetaBlocks.blockStepperCasing.getState(BlockStepperCasing.CasingType.F_LASER_MKI),
                new WrappedIntTired(BlockStepperCasing.CasingType.F_LASER_MKI, 1));
        MAP_ZJ_CASING.put(GTQTMetaBlocks.blockStepperCasing.getState(BlockStepperCasing.CasingType.CLEAN_MKI),
                new WrappedIntTired(BlockStepperCasing.CasingType.CLEAN_MKI, 1));

        MAP_ZW_CASING.put(GTQTMetaBlocks.blockStepperCasing.getState(BlockStepperCasing.CasingType.LASER_MKII),
                new WrappedIntTired(BlockStepperCasing.CasingType.LASER_MKII, 2));
        MAP_TJ_CASING.put(GTQTMetaBlocks.blockStepperCasing.getState(BlockStepperCasing.CasingType.F_LASER_MKII),
                new WrappedIntTired(BlockStepperCasing.CasingType.F_LASER_MKII, 2));
        MAP_ZJ_CASING.put(GTQTMetaBlocks.blockStepperCasing.getState(BlockStepperCasing.CasingType.CLEAN_MKII),
                new WrappedIntTired(BlockStepperCasing.CasingType.CLEAN_MKII, 2));

        MAP_ZW_CASING.put(GTQTMetaBlocks.blockStepperCasing.getState(BlockStepperCasing.CasingType.LASER_MKIII),
                new WrappedIntTired(BlockStepperCasing.CasingType.LASER_MKIII, 3));
        MAP_TJ_CASING.put(GTQTMetaBlocks.blockStepperCasing.getState(BlockStepperCasing.CasingType.F_LASER_MKIII),
                new WrappedIntTired(BlockStepperCasing.CasingType.F_LASER_MKIII, 3));
        MAP_ZJ_CASING.put(GTQTMetaBlocks.blockStepperCasing.getState(BlockStepperCasing.CasingType.CLEAN_MKIII),
                new WrappedIntTired(BlockStepperCasing.CasingType.CLEAN_MKIII, 3));

        MAP_ZW_CASING.put(GTQTMetaBlocks.blockStepperCasing.getState(BlockStepperCasing.CasingType.LASER_MKIV),
                new WrappedIntTired(BlockStepperCasing.CasingType.LASER_MKIV, 4));
        MAP_TJ_CASING.put(GTQTMetaBlocks.blockStepperCasing.getState(BlockStepperCasing.CasingType.F_LASER_MKIV),
                new WrappedIntTired(BlockStepperCasing.CasingType.F_LASER_MKIV, 4));
        MAP_ZJ_CASING.put(GTQTMetaBlocks.blockStepperCasing.getState(BlockStepperCasing.CasingType.CLEAN_MKIV),
                new WrappedIntTired(BlockStepperCasing.CasingType.CLEAN_MKIV, 4));

        MAP_ZW_CASING.put(GTQTMetaBlocks.blockStepperCasing.getState(BlockStepperCasing.CasingType.LASER_MKV),
                new WrappedIntTired(BlockStepperCasing.CasingType.LASER_MKV, 5));
        MAP_TJ_CASING.put(GTQTMetaBlocks.blockStepperCasing.getState(BlockStepperCasing.CasingType.F_LASER_MKV),
                new WrappedIntTired(BlockStepperCasing.CasingType.F_LASER_MKV, 5));
        MAP_ZJ_CASING.put(GTQTMetaBlocks.blockStepperCasing.getState(BlockStepperCasing.CasingType.CLEAN_MKV),
                new WrappedIntTired(BlockStepperCasing.CasingType.CLEAN_MKV, 5));




        MAP_PAF_CASING.put(GTQTMetaBlocks.blockParticleAcceleratorCasing.getState(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_FIRM_MKI),
                new WrappedIntTired(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_FIRM_MKI, 1));
        MAP_PAE_CASING.put(GTQTMetaBlocks.blockParticleAcceleratorCasing.getState(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_ELECTROMAGNET_MKI),
                new WrappedIntTired(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_ELECTROMAGNET_MKI, 1));
        MAP_PAV_CASING.put(GTQTMetaBlocks.blockParticleAcceleratorCasing.getState(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_ELECTROMAGNETV_MKI),
                new WrappedIntTired(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_ELECTROMAGNETV_MKI, 1));

        MAP_PAF_CASING.put(GTQTMetaBlocks.blockParticleAcceleratorCasing.getState(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_FIRM_MKII),
                new WrappedIntTired(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_FIRM_MKII, 2));
        MAP_PAE_CASING.put(GTQTMetaBlocks.blockParticleAcceleratorCasing.getState(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_ELECTROMAGNET_MKII),
                new WrappedIntTired(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_ELECTROMAGNET_MKI, 2));
        MAP_PAV_CASING.put(GTQTMetaBlocks.blockParticleAcceleratorCasing.getState(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_ELECTROMAGNETV_MKII),
                new WrappedIntTired(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_ELECTROMAGNETV_MKII, 2));

        MAP_PAF_CASING.put(GTQTMetaBlocks.blockParticleAcceleratorCasing.getState(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_FIRM_MKIII),
                new WrappedIntTired(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_FIRM_MKIII, 3));
        MAP_PAE_CASING.put(GTQTMetaBlocks.blockParticleAcceleratorCasing.getState(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_ELECTROMAGNET_MKIII),
                new WrappedIntTired(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_ELECTROMAGNET_MKIII, 3));
        MAP_PAV_CASING.put(GTQTMetaBlocks.blockParticleAcceleratorCasing.getState(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_ELECTROMAGNETV_MKIII),
                new WrappedIntTired(BlockParticleAcceleratorCasing.MachineType.ACCELERATOR_ELECTROMAGNETV_MKIII, 3));
    }
}
