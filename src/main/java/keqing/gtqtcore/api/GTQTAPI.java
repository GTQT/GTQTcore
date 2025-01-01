package keqing.gtqtcore.api;

import gregtech.common.blocks.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import keqing.gtqtcore.api.blocks.IBlockTier;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metaileentity.multiblock.ICellData;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.*;
import net.minecraft.block.state.IBlockState;

import static keqing.gtqtcore.common.block.GTQTMetaBlocks.*;
import static keqing.gtqtcore.common.block.blocks.BlockGravitonCasing.GravitonCasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockGravitonCasing.GravitonCasingType.CENTRAL_GRAVITON_FLOW_MODULATOR;
import static keqing.gtqtcore.common.block.blocks.GTQTCompressedFusionReactor.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.GTQTCompressedFusionReactor.CasingType.FUSION_COIL_MKIV;
import static keqing.gtqtcore.common.block.blocks.GTQTElectronMicroscope.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.GTQTHCasing.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.GTQTQuantumCasing.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.GTQTQuantumCasing.CasingType.ULTIMATE_HIGH_ENERGY_CASING;

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
            MAP_ES_CELLS.put(GTQTMetaBlocks.ENERGY_CELL.getState(tier), tier);
        }

        MAP_LS_CASING.put(GTQTMetaBlocks.ELECTRON_MICROSCOPE.getState(LENS_I),
                new WrappedIntTired(LENS_I, 1));
        MAP_LS_CASING.put(GTQTMetaBlocks.ELECTRON_MICROSCOPE.getState(LENS_II),
                new WrappedIntTired(LENS_II, 2));
        MAP_LS_CASING.put(GTQTMetaBlocks.ELECTRON_MICROSCOPE.getState(LENS_III),
                new WrappedIntTired(LENS_III, 3));
        MAP_LS_CASING.put(GTQTMetaBlocks.ELECTRON_MICROSCOPE.getState(LENS_IV),
                new WrappedIntTired(LENS_IV, 4));
        MAP_LS_CASING.put(GTQTMetaBlocks.ELECTRON_MICROSCOPE.getState(LENS_V),
                new WrappedIntTired(LENS_V, 5));

        MAP_SS_CASING.put(GTQTMetaBlocks.ELECTRON_MICROSCOPE.getState(SOURSE_I),
                new WrappedIntTired(SOURSE_I, 1));
        MAP_SS_CASING.put(GTQTMetaBlocks.ELECTRON_MICROSCOPE.getState(SOURSE_II),
                new WrappedIntTired(SOURSE_II, 2));
        MAP_SS_CASING.put(GTQTMetaBlocks.ELECTRON_MICROSCOPE.getState(SOURSE_III),
                new WrappedIntTired(SOURSE_III, 3));
        MAP_SS_CASING.put(GTQTMetaBlocks.ELECTRON_MICROSCOPE.getState(SOURSE_IV),
                new WrappedIntTired(SOURSE_IV, 4));
        MAP_SS_CASING.put(GTQTMetaBlocks.ELECTRON_MICROSCOPE.getState(SOURSE_V),
                new WrappedIntTired(SOURSE_V, 5));

        //  Fusion Coil
        MAP_FUSION_COIL.put(MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL),
                new WrappedIntTired(gregtech.common.blocks.BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL, 1));
        MAP_FUSION_COIL.put(MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_COIL),
                new WrappedIntTired(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_COIL, 2));

        MAP_FUSION_COIL.put(GTQTMetaBlocks.COMPRESSED_FUSION_REACTOR.getState(FUSION_COIL_MKII),
                new WrappedIntTired(FUSION_COIL_MKII, 3));
        MAP_FUSION_COIL.put(GTQTMetaBlocks.COMPRESSED_FUSION_REACTOR.getState(FUSION_COIL_MKIII),
                new WrappedIntTired(FUSION_COIL_MKIII, 4));
        MAP_FUSION_COIL.put(GTQTMetaBlocks.COMPRESSED_FUSION_REACTOR.getState(FUSION_COIL_MKIV),
                new WrappedIntTired(FUSION_COIL_MKIV, 5));

        MAP_DC_CASING.put(QUANTUM_CASING.getState(HIGH_ENERGY_CASING),
                new WrappedIntTired(HIGH_ENERGY_CASING, 1));
        MAP_DC_CASING.put(QUANTUM_CASING.getState(ADVANCED_HIGH_ENERGY_CASING),
                new WrappedIntTired(ADVANCED_HIGH_ENERGY_CASING, 2));
        MAP_DC_CASING.put(QUANTUM_CASING.getState(ULTIMATE_HIGH_ENERGY_CASING),
                new WrappedIntTired(ULTIMATE_HIGH_ENERGY_CASING, 3));

        MAP_ND_CASING.put(GRAVITON_CASING.getState(REMOTE_GRAVITON_FLOW_MODULATOR),
                new WrappedIntTired(REMOTE_GRAVITON_FLOW_MODULATOR, 1));
        MAP_ND_CASING.put(GRAVITON_CASING.getState(MEDIAL_GRAVITON_FLOW_MODULATOR),
                new WrappedIntTired(MEDIAL_GRAVITON_FLOW_MODULATOR, 2));
        MAP_ND_CASING.put(GRAVITON_CASING.getState(CENTRAL_GRAVITON_FLOW_MODULATOR),
                new WrappedIntTired(CENTRAL_GRAVITON_FLOW_MODULATOR, 3));

        MAP_FU_CASING.put(H_CASING.getState(MACHINE_CASING_FUSION),
                new WrappedIntTired(MACHINE_CASING_FUSION, 1));
        MAP_FU_CASING.put(H_CASING.getState(MACHINE_CASING_FUSION_2),
                new WrappedIntTired(MACHINE_CASING_FUSION_2, 2));
        MAP_FU_CASING.put(H_CASING.getState(MACHINE_CASING_FUSION_3),
                new WrappedIntTired(MACHINE_CASING_FUSION_3, 3));

        MAP_PA_CASING.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK1),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK1, 1));
        MAP_PA_CASING.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK2),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK2, 2));
        MAP_PA_CASING.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK3),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK3, 3));
        MAP_PA_CASING.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK4),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK4, 4));
        MAP_PA_CASING.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK5),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK5, 5));
        MAP_PA_CASING.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK6),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK6, 6));

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
        MAP_CP_CASING.put(GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.PD_TURBINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing.TurbineCasingType.PD_TURBINE_CASING,7));
        MAP_CP_CASING.put(GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_TURBINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing.TurbineCasingType.NQ_TURBINE_CASING,8));
        MAP_CP_CASING.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.ST_TURBINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.ST_TURBINE_CASING,9));
        MAP_CP_CASING.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.AD_TURBINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.AD_TURBINE_CASING,10));

        MAP_CP_TUBE.put(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.BRONZE_PIPE),
                new WrappedIntTired(BlockBoilerCasing.BoilerCasingType.BRONZE_PIPE,1));
        MAP_CP_TUBE.put(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE),
                new WrappedIntTired(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE,2));
        MAP_CP_TUBE.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.AL_TURBINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.AL_TURBINE_CASING,3));
        MAP_CP_TUBE.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.SA_TURBINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.SA_TURBINE_CASING,4));
        MAP_CP_TUBE.put(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TITANIUM_PIPE),
                new WrappedIntTired(BlockBoilerCasing.BoilerCasingType.BRONZE_PIPE,5));
        MAP_CP_TUBE.put(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE),
                new WrappedIntTired(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE,6));
        MAP_CP_TUBE.put(GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.PD_MACHINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing.TurbineCasingType.PD_MACHINE_CASING,7));
        MAP_CP_TUBE.put(GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_MACHINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing.TurbineCasingType.NQ_MACHINE_CASING,8));
        MAP_CP_TUBE.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.ST_MACHINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.ST_MACHINE_CASING,9));
        MAP_CP_TUBE.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.AD_MACHINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.AD_MACHINE_CASING,10));


        MAP_GLASS.put(MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS),
                new WrappedIntTired(BlockGlassCasing.CasingType.FUSION_GLASS,1));

        MAP_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_IV),
                new WrappedIntTired(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_IV,2));

        MAP_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_V),
                new WrappedIntTired(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_V,3));

        MAP_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_VI),
                new WrappedIntTired(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_VI,4));

        //  MAP_QFT_MANIPULATOR Init
        MAP_QFT_MANIPULATOR.put(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.NEUTRON_PULSE_MANIPULATOR_CASING),
                new WrappedIntTired(GTQTQuantumForceTransformerCasing.CasingType.NEUTRON_PULSE_MANIPULATOR_CASING, 1));
        MAP_QFT_MANIPULATOR.put(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.COSMIC_FABRIC_MANIPULATOR_CASING),
                new WrappedIntTired(GTQTQuantumForceTransformerCasing.CasingType.COSMIC_FABRIC_MANIPULATOR_CASING, 2));
        MAP_QFT_MANIPULATOR.put(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.INFINITY_INFUSED_MANIPULATOR_CASING),
                new WrappedIntTired(GTQTQuantumForceTransformerCasing.CasingType.INFINITY_INFUSED_MANIPULATOR_CASING, 3));
        MAP_QFT_MANIPULATOR.put(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.SUPRACAUSAL_CONTINUUM_MANIPULATOR_CASING),
                new WrappedIntTired(GTQTQuantumForceTransformerCasing.CasingType.SUPRACAUSAL_CONTINUUM_MANIPULATOR_CASING, 4));

        //  MAP_QFT_SHIELDING_CORE Init
        MAP_QFT_SHIELDING_CORE.put(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.NEUTRON_SHIELDING_CORE_CASING),
                new WrappedIntTired(GTQTQuantumForceTransformerCasing.CasingType.NEUTRON_SHIELDING_CORE_CASING, 1));
        MAP_QFT_SHIELDING_CORE.put(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.COSMIC_FABRIC_SHIELDING_CORE_CASING),
                new WrappedIntTired(GTQTQuantumForceTransformerCasing.CasingType.COSMIC_FABRIC_SHIELDING_CORE_CASING, 2));
        MAP_QFT_SHIELDING_CORE.put(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.INFINITY_INFUSED_SHIELDING_CORE_CASING),
                new WrappedIntTired(GTQTQuantumForceTransformerCasing.CasingType.INFINITY_INFUSED_SHIELDING_CORE_CASING, 3));
        MAP_QFT_SHIELDING_CORE.put(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.SUPRACAUSAL_CONTINUUM_SHIELDING_CORE_CASING),
                new WrappedIntTired(GTQTQuantumForceTransformerCasing.CasingType.SUPRACAUSAL_CONTINUUM_SHIELDING_CORE_CASING, 4));

        //  MAP_QFT_GLASS Init
        MAP_QFT_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS),
                new WrappedIntTired(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS, 1));
        MAP_QFT_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_B),
                new WrappedIntTired(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_B, 2));
        MAP_QFT_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_G),
                new WrappedIntTired(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_G, 3));
        MAP_QFT_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_O),
                new WrappedIntTired(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_O, 4));
         MAP_QFT_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_P),
                new WrappedIntTired(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_P, 5));
        MAP_QFT_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_R),
                new WrappedIntTired(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_R, 6));
        //lglass
        MAP_LGLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.SILICATE_GLASS),
                new WrappedIntTired(GTQTADVGlass.CasingType.SILICATE_GLASS, 1));
        MAP_LGLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.THY_SILICATE_GLASS),
                new WrappedIntTired(GTQTADVGlass.CasingType.THY_SILICATE_GLASS, 2));
        MAP_LGLASS.put(GTQTMetaBlocks.GLASS_CASING.getState(GTQTBlockGlassCasing.CasingType.TI_BORON_SILICATE_GLASS),
                new WrappedIntTired(GTQTBlockGlassCasing.CasingType.TI_BORON_SILICATE_GLASS, 3));
        MAP_LGLASS.put(GTQTMetaBlocks.GLASS_CASING.getState(GTQTBlockGlassCasing.CasingType.W_BORON_SILICATE_GLASS),
                new WrappedIntTired(GTQTBlockGlassCasing.CasingType.W_BORON_SILICATE_GLASS, 4));
        MAP_LGLASS.put(GTQTMetaBlocks.GLASS_CASING.getState(GTQTBlockGlassCasing.CasingType.OSMIR_BORON_SILICATE_GLASS),
                new WrappedIntTired(GTQTBlockGlassCasing.CasingType.OSMIR_BORON_SILICATE_GLASS, 5));
        MAP_LGLASS.put(GTQTMetaBlocks.GLASS_CASING.getState(GTQTBlockGlassCasing.CasingType.NAQ_BORON_SILICATE_GLASS),
                new WrappedIntTired(GTQTBlockGlassCasing.CasingType.NAQ_BORON_SILICATE_GLASS, 6));
        MAP_LGLASS.put(GTQTMetaBlocks.GLASS_CASING.getState(GTQTBlockGlassCasing.CasingType.FORCE_FIELD_CONSTRAINED_GLASS),
                new WrappedIntTired(GTQTBlockGlassCasing.CasingType.FORCE_FIELD_CONSTRAINED_GLASS, 7));
        MAP_LGLASS.put(GTQTMetaBlocks.GLASS_CASING.getState(GTQTBlockGlassCasing.CasingType.COSMIC_MICROWAVE_BACKGROUND_RADIATION_ABSORPTION_GLASS),
                new WrappedIntTired(GTQTBlockGlassCasing.CasingType.COSMIC_MICROWAVE_BACKGROUND_RADIATION_ABSORPTION_GLASS, 8));
        MAP_LGLASS.put(GTQTMetaBlocks.GLASS_CASING.getState(GTQTBlockGlassCasing.CasingType.SPACETIME_SUPERCONDENSER_GLASS),
                new WrappedIntTired(GTQTBlockGlassCasing.CasingType.SPACETIME_SUPERCONDENSER_GLASS, 9));
        MAP_LGLASS.put(GTQTMetaBlocks.GLASS_CASING.getState(GTQTBlockGlassCasing.CasingType.SUPRACAUSAL_LIGHT_CONE_GLASS),
                new WrappedIntTired(GTQTBlockGlassCasing.CasingType.SUPRACAUSAL_LIGHT_CONE_GLASS, 10));

        MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.LV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.LV, 1));
        MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.MV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.MV, 2));
        MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.HV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.HV, 3));
        MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.EV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.EV, 4));
        MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.IV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.IV, 5));
        MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.LuV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.LuV, 6));
        MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.ZPM),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.ZPM, 7));
        MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.UV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.UV, 8));
        MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.UHV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.UHV, 9));
        MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.UEV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.UEV, 10));
        MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.UIV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.UIV, 11));
        MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.UXV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.UXV, 12));
        MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.OpV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.OpV, 13));
        MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.MAX),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.MAX, 14));

        MAP_DRI_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.DRILL_HEAD_LV),
                new WrappedIntTired(GTQTElectrobath.CasingType.DRILL_HEAD_LV, 1));
        MAP_DRI_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.DRILL_HEAD_MV),
                new WrappedIntTired(GTQTElectrobath.CasingType.DRILL_HEAD_MV, 2));
        MAP_DRI_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.DRILL_HEAD_HV),
                new WrappedIntTired(GTQTElectrobath.CasingType.DRILL_HEAD_HV, 3));
        MAP_DRI_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.DRILL_HEAD_EV),
                new WrappedIntTired(GTQTElectrobath.CasingType.DRILL_HEAD_EV, 4));


        MAP_SP_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.SOLAR_PLATE_LV),
                new WrappedIntTired(GTQTElectrobath.CasingType.SOLAR_PLATE_LV, 1));
        MAP_SP_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.SOLAR_PLATE_MV),
                new WrappedIntTired(GTQTElectrobath.CasingType.SOLAR_PLATE_MV, 2));
        MAP_SP_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.SOLAR_PLATE_HV),
                new WrappedIntTired(GTQTElectrobath.CasingType.SOLAR_PLATE_MV, 3));

        MAP_ZW_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.LASER_MKI),
                new WrappedIntTired(GTQTStepper.CasingType.LASER_MKI, 1));
        MAP_TJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.F_LASER_MKI),
                new WrappedIntTired(GTQTStepper.CasingType.F_LASER_MKI, 1));
        MAP_ZJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.CLEAN_MKI),
                new WrappedIntTired(GTQTStepper.CasingType.CLEAN_MKI, 1));

        MAP_ZW_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.LASER_MKII),
                new WrappedIntTired(GTQTStepper.CasingType.LASER_MKII, 2));
        MAP_TJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.F_LASER_MKII),
                new WrappedIntTired(GTQTStepper.CasingType.F_LASER_MKII, 2));
        MAP_ZJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.CLEAN_MKII),
                new WrappedIntTired(GTQTStepper.CasingType.CLEAN_MKII, 2));

        MAP_ZW_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.LASER_MKIII),
                new WrappedIntTired(GTQTStepper.CasingType.LASER_MKIII, 3));
        MAP_TJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.F_LASER_MKIII),
                new WrappedIntTired(GTQTStepper.CasingType.F_LASER_MKIII, 3));
        MAP_ZJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.CLEAN_MKIII),
                new WrappedIntTired(GTQTStepper.CasingType.CLEAN_MKIII, 3));

        MAP_ZW_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.LASER_MKIV),
                new WrappedIntTired(GTQTStepper.CasingType.LASER_MKIV, 4));
        MAP_TJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.F_LASER_MKIV),
                new WrappedIntTired(GTQTStepper.CasingType.F_LASER_MKIV, 4));
        MAP_ZJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.CLEAN_MKIV),
                new WrappedIntTired(GTQTStepper.CasingType.CLEAN_MKIV, 4));

        MAP_ZW_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.LASER_MKV),
                new WrappedIntTired(GTQTStepper.CasingType.LASER_MKV, 5));
        MAP_TJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.F_LASER_MKV),
                new WrappedIntTired(GTQTStepper.CasingType.F_LASER_MKV, 5));
        MAP_ZJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.CLEAN_MKV),
                new WrappedIntTired(GTQTStepper.CasingType.CLEAN_MKV, 5));




        MAP_PAF_CASING.put(GTQTMetaBlocks.PARTICLE_ACCELERATOR.getState(GTQTParticleAccelerator.MachineType.ACCELERATOR_FIRM_MKI),
                new WrappedIntTired(GTQTParticleAccelerator.MachineType.ACCELERATOR_FIRM_MKI, 1));
        MAP_PAE_CASING.put(GTQTMetaBlocks.PARTICLE_ACCELERATOR.getState(GTQTParticleAccelerator.MachineType.ACCELERATOR_ELECTROMAGNET_MKI),
                new WrappedIntTired(GTQTParticleAccelerator.MachineType.ACCELERATOR_ELECTROMAGNET_MKI, 1));
        MAP_PAV_CASING.put(GTQTMetaBlocks.PARTICLE_ACCELERATOR.getState(GTQTParticleAccelerator.MachineType.ACCELERATOR_ELECTROMAGNETV_MKI),
                new WrappedIntTired(GTQTParticleAccelerator.MachineType.ACCELERATOR_ELECTROMAGNETV_MKI, 1));

        MAP_PAF_CASING.put(GTQTMetaBlocks.PARTICLE_ACCELERATOR.getState(GTQTParticleAccelerator.MachineType.ACCELERATOR_FIRM_MKII),
                new WrappedIntTired(GTQTParticleAccelerator.MachineType.ACCELERATOR_FIRM_MKII, 2));
        MAP_PAE_CASING.put(GTQTMetaBlocks.PARTICLE_ACCELERATOR.getState(GTQTParticleAccelerator.MachineType.ACCELERATOR_ELECTROMAGNET_MKII),
                new WrappedIntTired(GTQTParticleAccelerator.MachineType.ACCELERATOR_ELECTROMAGNET_MKI, 2));
        MAP_PAV_CASING.put(GTQTMetaBlocks.PARTICLE_ACCELERATOR.getState(GTQTParticleAccelerator.MachineType.ACCELERATOR_ELECTROMAGNETV_MKII),
                new WrappedIntTired(GTQTParticleAccelerator.MachineType.ACCELERATOR_ELECTROMAGNETV_MKII, 2));

        MAP_PAF_CASING.put(GTQTMetaBlocks.PARTICLE_ACCELERATOR.getState(GTQTParticleAccelerator.MachineType.ACCELERATOR_FIRM_MKIII),
                new WrappedIntTired(GTQTParticleAccelerator.MachineType.ACCELERATOR_FIRM_MKIII, 3));
        MAP_PAE_CASING.put(GTQTMetaBlocks.PARTICLE_ACCELERATOR.getState(GTQTParticleAccelerator.MachineType.ACCELERATOR_ELECTROMAGNET_MKIII),
                new WrappedIntTired(GTQTParticleAccelerator.MachineType.ACCELERATOR_ELECTROMAGNET_MKIII, 3));
        MAP_PAV_CASING.put(GTQTMetaBlocks.PARTICLE_ACCELERATOR.getState(GTQTParticleAccelerator.MachineType.ACCELERATOR_ELECTROMAGNETV_MKIII),
                new WrappedIntTired(GTQTParticleAccelerator.MachineType.ACCELERATOR_ELECTROMAGNETV_MKIII, 3));
    }
}
