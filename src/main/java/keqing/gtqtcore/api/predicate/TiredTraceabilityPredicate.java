package keqing.gtqtcore.api.predicate;

import gregtech.api.block.VariantActiveBlock;
import gregtech.api.pattern.BlockWorldState;
import gregtech.api.pattern.PatternStringError;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.util.BlockInfo;
import gregtech.common.blocks.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import keqing.gtqtcore.api.blocks.ITired;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.*;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TiredTraceabilityPredicate extends TraceabilityPredicate {


    static {
        MAP_ESSENTIA_CELLS = new Object2ObjectOpenHashMap<>();
        MAP_MACHINE_CASING = new Object2ObjectOpenHashMap<>();
        MAP_GLASS = new Object2ObjectOpenHashMap<>();
        MAP_LGLASS = new Object2ObjectOpenHashMap<>();
        MAP_CP_CASING = new Object2ObjectOpenHashMap<>();
        MAP_CP_TUBE = new Object2ObjectOpenHashMap<>();
        MAP_CP_BEAM = new Object2ObjectOpenHashMap<>();
        MAP_QFT_MANIPULATOR = new Object2ObjectOpenHashMap<>();
        MAP_QFT_SHIELDING_CORE = new Object2ObjectOpenHashMap<>();
        MAP_QFT_GLASS = new Object2ObjectOpenHashMap<>();
        MAP_CAL_CASING = new Object2ObjectOpenHashMap<>();
        MAP_ELE_CASING = new Object2ObjectOpenHashMap<>();
        MAP_SP_CASING = new Object2ObjectOpenHashMap<>();
        MAP_DRI_CASING = new Object2ObjectOpenHashMap<>();
        MAP_PA_CASING = new Object2ObjectOpenHashMap<>();
        MAP_ZW_CASING = new Object2ObjectOpenHashMap<>();
        MAP_TJ_CASING = new Object2ObjectOpenHashMap<>();
        MAP_ZJ_CASING = new Object2ObjectOpenHashMap<>();

        MAP_PA_INTERNAL_CASING = new Object2ObjectOpenHashMap<>();

        //计算机
        MAP_CPU_CASING = new Object2ObjectOpenHashMap<>();
        MAP_GPU_CASING = new Object2ObjectOpenHashMap<>();
        MAP_RAM_CASING = new Object2ObjectOpenHashMap<>();

        TiredTraceabilityPredicate.MAP_PA_CASING.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK1),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK1, 1));
        TiredTraceabilityPredicate.MAP_PA_CASING.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK2),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK2, 2));
        TiredTraceabilityPredicate.MAP_PA_CASING.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK3),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK3, 3));

        TiredTraceabilityPredicate.MAP_PA_INTERNAL_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.LuV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.LuV, 1));
        TiredTraceabilityPredicate.MAP_PA_INTERNAL_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.ZPM),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.ZPM, 2));
        TiredTraceabilityPredicate.MAP_PA_INTERNAL_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.UV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.UV, 3));


        TiredTraceabilityPredicate.MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.LV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.LV,1));
        TiredTraceabilityPredicate.MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.MV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.MV,2));
        TiredTraceabilityPredicate.MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.HV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.HV,3));
        TiredTraceabilityPredicate.MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.EV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.EV,4));
        TiredTraceabilityPredicate.MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.IV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.IV,5));
        TiredTraceabilityPredicate.MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.LuV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.LuV,6));
        TiredTraceabilityPredicate.MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.ZPM),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.ZPM,7));
        TiredTraceabilityPredicate.MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.UV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.UV,8));
        TiredTraceabilityPredicate.MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.UHV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.UHV,9));
        TiredTraceabilityPredicate.MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.UEV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.UEV,10));
        TiredTraceabilityPredicate.MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.UIV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.UIV,11));
        TiredTraceabilityPredicate.MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.UXV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.UXV,12));
        TiredTraceabilityPredicate.MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.OpV),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.OpV,13));
        TiredTraceabilityPredicate.MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.MAX),
                new WrappedIntTired(BlockMachineCasing.MachineCasingType.MAX,14));


        TiredTraceabilityPredicate.MAP_CP_CASING.put(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.BRONZE_BRICKS),
                new WrappedIntTired(BlockMetalCasing.MetalCasingType.BRONZE_BRICKS,1));
        TiredTraceabilityPredicate.MAP_CP_CASING.put(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID),
                new WrappedIntTired(BlockMetalCasing.MetalCasingType.STEEL_SOLID,2));
        TiredTraceabilityPredicate.MAP_CP_CASING.put(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.ALUMINIUM_FROSTPROOF),
                new WrappedIntTired(BlockMetalCasing.MetalCasingType.ALUMINIUM_FROSTPROOF,3));
        TiredTraceabilityPredicate.MAP_CP_CASING.put(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN),
                new WrappedIntTired(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN,4));
        TiredTraceabilityPredicate.MAP_CP_CASING.put(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TITANIUM_STABLE),
                new WrappedIntTired(BlockMetalCasing.MetalCasingType.TITANIUM_STABLE,5));
        TiredTraceabilityPredicate.MAP_CP_CASING.put(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST),
                new WrappedIntTired(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST,6));
        TiredTraceabilityPredicate.MAP_CP_CASING.put(GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.PD_TURBINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing.TurbineCasingType.PD_TURBINE_CASING,7));
        TiredTraceabilityPredicate.MAP_CP_CASING.put(GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_TURBINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing.TurbineCasingType.NQ_TURBINE_CASING,8));
        TiredTraceabilityPredicate.MAP_CP_CASING.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.ST_TURBINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.ST_TURBINE_CASING,9));
        TiredTraceabilityPredicate.MAP_CP_CASING.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.AD_TURBINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.AD_TURBINE_CASING,10));

        TiredTraceabilityPredicate.MAP_CP_TUBE.put(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.BRONZE_PIPE),
                new WrappedIntTired(BlockBoilerCasing.BoilerCasingType.BRONZE_PIPE,1));
        TiredTraceabilityPredicate.MAP_CP_TUBE.put(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE),
                new WrappedIntTired(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE,2));
        TiredTraceabilityPredicate.MAP_CP_TUBE.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.AL_TURBINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.AL_TURBINE_CASING,3));
        TiredTraceabilityPredicate.MAP_CP_TUBE.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.SA_TURBINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.SA_TURBINE_CASING,4));
        TiredTraceabilityPredicate.MAP_CP_TUBE.put(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TITANIUM_PIPE),
                new WrappedIntTired(BlockBoilerCasing.BoilerCasingType.BRONZE_PIPE,5));
        TiredTraceabilityPredicate.MAP_CP_TUBE.put(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE),
                new WrappedIntTired(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE,6));
        TiredTraceabilityPredicate.MAP_CP_TUBE.put(GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.PD_MACHINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing.TurbineCasingType.PD_MACHINE_CASING,7));
        TiredTraceabilityPredicate.MAP_CP_TUBE.put(GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_MACHINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing.TurbineCasingType.NQ_MACHINE_CASING,8));
        TiredTraceabilityPredicate.MAP_CP_TUBE.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.ST_MACHINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.ST_MACHINE_CASING,9));
        TiredTraceabilityPredicate.MAP_CP_TUBE.put(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.AD_MACHINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing1.TurbineCasingType.AD_MACHINE_CASING,10));


        TiredTraceabilityPredicate.MAP_GLASS.put(MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS),
                new WrappedIntTired(BlockGlassCasing.CasingType.FUSION_GLASS,1));

        TiredTraceabilityPredicate.MAP_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_IV),
                new WrappedIntTired(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_IV,2));

        TiredTraceabilityPredicate.MAP_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_V),
                new WrappedIntTired(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_V,3));

        TiredTraceabilityPredicate.MAP_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_VI),
                new WrappedIntTired(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_VI,4));

        //  MAP_QFT_MANIPULATOR Init
        TiredTraceabilityPredicate.MAP_QFT_MANIPULATOR.put(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.NEUTRON_PULSE_MANIPULATOR_CASING),
                new WrappedIntTired(GTQTQuantumForceTransformerCasing.CasingType.NEUTRON_PULSE_MANIPULATOR_CASING, 1));
        TiredTraceabilityPredicate.MAP_QFT_MANIPULATOR.put(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.COSMIC_FABRIC_MANIPULATOR_CASING),
                new WrappedIntTired(GTQTQuantumForceTransformerCasing.CasingType.COSMIC_FABRIC_MANIPULATOR_CASING, 2));
        TiredTraceabilityPredicate.MAP_QFT_MANIPULATOR.put(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.INFINITY_INFUSED_MANIPULATOR_CASING),
                new WrappedIntTired(GTQTQuantumForceTransformerCasing.CasingType.INFINITY_INFUSED_MANIPULATOR_CASING, 3));
        TiredTraceabilityPredicate.MAP_QFT_MANIPULATOR.put(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.SUPRACAUSAL_CONTINUUM_MANIPULATOR_CASING),
                new WrappedIntTired(GTQTQuantumForceTransformerCasing.CasingType.SUPRACAUSAL_CONTINUUM_MANIPULATOR_CASING, 4));

        //  MAP_QFT_SHIELDING_CORE Init
        TiredTraceabilityPredicate.MAP_QFT_SHIELDING_CORE.put(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.NEUTRON_SHIELDING_CORE_CASING),
                new WrappedIntTired(GTQTQuantumForceTransformerCasing.CasingType.NEUTRON_SHIELDING_CORE_CASING, 1));
        TiredTraceabilityPredicate.MAP_QFT_SHIELDING_CORE.put(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.COSMIC_FABRIC_SHIELDING_CORE_CASING),
                new WrappedIntTired(GTQTQuantumForceTransformerCasing.CasingType.COSMIC_FABRIC_SHIELDING_CORE_CASING, 2));
        TiredTraceabilityPredicate.MAP_QFT_SHIELDING_CORE.put(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.INFINITY_INFUSED_SHIELDING_CORE_CASING),
                new WrappedIntTired(GTQTQuantumForceTransformerCasing.CasingType.INFINITY_INFUSED_SHIELDING_CORE_CASING, 3));
        TiredTraceabilityPredicate.MAP_QFT_SHIELDING_CORE.put(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING.getState(GTQTQuantumForceTransformerCasing.CasingType.SUPRACAUSAL_CONTINUUM_SHIELDING_CORE_CASING),
                new WrappedIntTired(GTQTQuantumForceTransformerCasing.CasingType.SUPRACAUSAL_CONTINUUM_SHIELDING_CORE_CASING, 4));

        //  MAP_QFT_GLASS Init
        TiredTraceabilityPredicate.MAP_QFT_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS),
                new WrappedIntTired(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS, 1));
        TiredTraceabilityPredicate.MAP_QFT_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_B),
                new WrappedIntTired(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_B, 2));
        TiredTraceabilityPredicate.MAP_QFT_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_G),
                new WrappedIntTired(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_G, 3));
        TiredTraceabilityPredicate.MAP_QFT_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_O),
                new WrappedIntTired(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_O, 4));
        TiredTraceabilityPredicate. MAP_QFT_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_P),
                new WrappedIntTired(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_P, 5));
        TiredTraceabilityPredicate.MAP_QFT_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_R),
                new WrappedIntTired(GTQTADVGlass.CasingType.ADV_MACHINE_GLASS_R, 6));
        //lglass
        TiredTraceabilityPredicate.MAP_LGLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.SILICATE_GLASS),
                new WrappedIntTired(GTQTADVGlass.CasingType.SILICATE_GLASS, 1));
        TiredTraceabilityPredicate.MAP_LGLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.THY_SILICATE_GLASS),
                new WrappedIntTired(GTQTADVGlass.CasingType.THY_SILICATE_GLASS, 2));
        TiredTraceabilityPredicate.MAP_LGLASS.put(GTQTMetaBlocks.GLASS_CASING.getState(GTQTBlockGlassCasing.CasingType.TI_BORON_SILICATE_GLASS),
                new WrappedIntTired(GTQTBlockGlassCasing.CasingType.TI_BORON_SILICATE_GLASS, 3));
        TiredTraceabilityPredicate.MAP_LGLASS.put(GTQTMetaBlocks.GLASS_CASING.getState(GTQTBlockGlassCasing.CasingType.W_BORON_SILICATE_GLASS),
                new WrappedIntTired(GTQTBlockGlassCasing.CasingType.W_BORON_SILICATE_GLASS, 4));
        TiredTraceabilityPredicate.MAP_LGLASS.put(GTQTMetaBlocks.GLASS_CASING.getState(GTQTBlockGlassCasing.CasingType.OSMIR_BORON_SILICATE_GLASS),
                new WrappedIntTired(GTQTBlockGlassCasing.CasingType.OSMIR_BORON_SILICATE_GLASS, 5));
        TiredTraceabilityPredicate.MAP_LGLASS.put(GTQTMetaBlocks.GLASS_CASING.getState(GTQTBlockGlassCasing.CasingType.NAQ_BORON_SILICATE_GLASS),
                new WrappedIntTired(GTQTBlockGlassCasing.CasingType.NAQ_BORON_SILICATE_GLASS, 6));
        TiredTraceabilityPredicate.MAP_LGLASS.put(GTQTMetaBlocks.GLASS_CASING.getState(GTQTBlockGlassCasing.CasingType.FORCE_FIELD_CONSTRAINED_GLASS),
                new WrappedIntTired(GTQTBlockGlassCasing.CasingType.FORCE_FIELD_CONSTRAINED_GLASS, 7));
        TiredTraceabilityPredicate.MAP_LGLASS.put(GTQTMetaBlocks.GLASS_CASING.getState(GTQTBlockGlassCasing.CasingType.COSMIC_MICROWAVE_BACKGROUND_RADIATION_ABSORPTION_GLASS),
                new WrappedIntTired(GTQTBlockGlassCasing.CasingType.COSMIC_MICROWAVE_BACKGROUND_RADIATION_ABSORPTION_GLASS, 8));
        TiredTraceabilityPredicate.MAP_LGLASS.put(GTQTMetaBlocks.GLASS_CASING.getState(GTQTBlockGlassCasing.CasingType.SPACETIME_SUPERCONDENSER_GLASS),
                new WrappedIntTired(GTQTBlockGlassCasing.CasingType.SPACETIME_SUPERCONDENSER_GLASS, 9));
        TiredTraceabilityPredicate.MAP_LGLASS.put(GTQTMetaBlocks.GLASS_CASING.getState(GTQTBlockGlassCasing.CasingType.SUPRACAUSAL_LIGHT_CONE_GLASS),
                new WrappedIntTired(GTQTBlockGlassCasing.CasingType.SUPRACAUSAL_LIGHT_CONE_GLASS, 10));

        TiredTraceabilityPredicate.MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.LV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.LV, 1));
        TiredTraceabilityPredicate.MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.MV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.MV, 2));
        TiredTraceabilityPredicate.MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.HV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.HV, 3));
        TiredTraceabilityPredicate.MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.EV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.EV, 4));
        TiredTraceabilityPredicate.MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.IV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.IV, 5));
        TiredTraceabilityPredicate.MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.LuV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.LuV, 6));
        TiredTraceabilityPredicate.MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.ZPM),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.ZPM, 7));
        TiredTraceabilityPredicate.MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.UV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.UV, 8));
        TiredTraceabilityPredicate.MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.UHV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.UHV, 9));
        TiredTraceabilityPredicate.MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.UEV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.UEV, 10));
        TiredTraceabilityPredicate.MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.UIV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.UIV, 11));
        TiredTraceabilityPredicate.MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.UXV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.UXV, 12));
        TiredTraceabilityPredicate.MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.OpV),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.OpV, 13));
        TiredTraceabilityPredicate.MAP_CAL_CASING.put(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE.getState(GTQTBlockComponentAssemblyLineCasing.CasingTier.MAX),
                new WrappedIntTired(GTQTBlockComponentAssemblyLineCasing.CasingTier.MAX, 14));


        TiredTraceabilityPredicate.MAP_ELE_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.I_ELECTROBATH),
                new WrappedIntTired(GTQTElectrobath.CasingType.I_ELECTROBATH, 1));
        TiredTraceabilityPredicate.MAP_ELE_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.II_ELECTROBATH),
                new WrappedIntTired(GTQTElectrobath.CasingType.II_ELECTROBATH, 2));
        TiredTraceabilityPredicate.MAP_ELE_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.III_ELECTROBATH),
                new WrappedIntTired(GTQTElectrobath.CasingType.III_ELECTROBATH, 3));
        TiredTraceabilityPredicate.MAP_ELE_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.IV_ELECTROBATH),
                new WrappedIntTired(GTQTElectrobath.CasingType.IV_ELECTROBATH, 4));
        TiredTraceabilityPredicate.MAP_ELE_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.V_ELECTROBATH),
                new WrappedIntTired(GTQTElectrobath.CasingType.V_ELECTROBATH, 5));

        TiredTraceabilityPredicate.MAP_DRI_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.DRILL_HEAD_LV),
                new WrappedIntTired(GTQTElectrobath.CasingType.DRILL_HEAD_LV, 1));
        TiredTraceabilityPredicate.MAP_DRI_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.DRILL_HEAD_MV),
                new WrappedIntTired(GTQTElectrobath.CasingType.DRILL_HEAD_MV, 2));
        TiredTraceabilityPredicate.MAP_DRI_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.DRILL_HEAD_HV),
                new WrappedIntTired(GTQTElectrobath.CasingType.DRILL_HEAD_HV, 3));
        TiredTraceabilityPredicate.MAP_DRI_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.DRILL_HEAD_EV),
                new WrappedIntTired(GTQTElectrobath.CasingType.DRILL_HEAD_EV, 4));


        TiredTraceabilityPredicate.MAP_SP_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.SOLAR_PLATE_LV),
                new WrappedIntTired(GTQTElectrobath.CasingType.SOLAR_PLATE_LV, 1));
        TiredTraceabilityPredicate.MAP_SP_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.SOLAR_PLATE_MV),
                new WrappedIntTired(GTQTElectrobath.CasingType.SOLAR_PLATE_MV, 2));
        TiredTraceabilityPredicate.MAP_SP_CASING.put(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.SOLAR_PLATE_HV),
                new WrappedIntTired(GTQTElectrobath.CasingType.SOLAR_PLATE_MV, 3));

        TiredTraceabilityPredicate.MAP_CPU_CASING.put(GTQTMetaBlocks.KQCC.getState(GTQTKQCC.CasingType.COMPUTER_VENT),
                new WrappedIntTired(GTQTKQCC.CasingType.COMPUTER_VENT, 0));
        TiredTraceabilityPredicate.MAP_CPU_CASING.put(GTQTMetaBlocks.KQCC.getState(GTQTKQCC.CasingType.COMPUTER_CPUI),
                new WrappedIntTired(GTQTKQCC.CasingType.COMPUTER_CPUI, 1));
        TiredTraceabilityPredicate.MAP_CPU_CASING.put(GTQTMetaBlocks.KQCC.getState(GTQTKQCC.CasingType.COMPUTER_CPUII),
                new WrappedIntTired(GTQTKQCC.CasingType.COMPUTER_CPUII, 2));
        TiredTraceabilityPredicate.MAP_CPU_CASING.put(GTQTMetaBlocks.KQCC.getState(GTQTKQCC.CasingType.COMPUTER_CPUIII),
                new WrappedIntTired(GTQTKQCC.CasingType.COMPUTER_CPUIII, 3));

        TiredTraceabilityPredicate.MAP_GPU_CASING.put(GTQTMetaBlocks.KQCC.getState(GTQTKQCC.CasingType.COMPUTER_VENT),
                new WrappedIntTired(GTQTKQCC.CasingType.COMPUTER_VENT, 0));
        TiredTraceabilityPredicate.MAP_GPU_CASING.put(GTQTMetaBlocks.KQCC.getState(GTQTKQCC.CasingType.COMPUTER_GPUI),
                new WrappedIntTired(GTQTKQCC.CasingType.COMPUTER_CPUI, 1));
        TiredTraceabilityPredicate.MAP_GPU_CASING.put(GTQTMetaBlocks.KQCC.getState(GTQTKQCC.CasingType.COMPUTER_GPUII),
                new WrappedIntTired(GTQTKQCC.CasingType.COMPUTER_CPUII, 2));
        TiredTraceabilityPredicate.MAP_GPU_CASING.put(GTQTMetaBlocks.KQCC.getState(GTQTKQCC.CasingType.COMPUTER_GPUIII),
                new WrappedIntTired(GTQTKQCC.CasingType.COMPUTER_CPUIII, 3));

        TiredTraceabilityPredicate.MAP_RAM_CASING.put(GTQTMetaBlocks.KQCC.getState(GTQTKQCC.CasingType.COMPUTER_VENT),
                new WrappedIntTired(GTQTKQCC.CasingType.COMPUTER_VENT, 0));
        TiredTraceabilityPredicate.MAP_RAM_CASING.put(GTQTMetaBlocks.KQCC.getState(GTQTKQCC.CasingType.COMPUTER_RAMI),
                new WrappedIntTired(GTQTKQCC.CasingType.COMPUTER_CPUI, 1));
        TiredTraceabilityPredicate.MAP_RAM_CASING.put(GTQTMetaBlocks.KQCC.getState(GTQTKQCC.CasingType.COMPUTER_RAMII),
                new WrappedIntTired(GTQTKQCC.CasingType.COMPUTER_CPUII, 2));
        TiredTraceabilityPredicate.MAP_RAM_CASING.put(GTQTMetaBlocks.KQCC.getState(GTQTKQCC.CasingType.COMPUTER_RAMIII),
                new WrappedIntTired(GTQTKQCC.CasingType.COMPUTER_CPUIII, 3));

        TiredTraceabilityPredicate.MAP_ZW_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.LASER_MKI),
                new WrappedIntTired(GTQTStepper.CasingType.LASER_MKI, 1));
        TiredTraceabilityPredicate.MAP_TJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.F_LASER_MKI),
                new WrappedIntTired(GTQTStepper.CasingType.F_LASER_MKI, 1));
        TiredTraceabilityPredicate.MAP_ZJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.CLEAN_MKI),
                new WrappedIntTired(GTQTStepper.CasingType.CLEAN_MKI, 1));

        TiredTraceabilityPredicate.MAP_ZW_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.LASER_MKII),
                new WrappedIntTired(GTQTStepper.CasingType.LASER_MKII, 2));
        TiredTraceabilityPredicate.MAP_TJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.F_LASER_MKII),
                new WrappedIntTired(GTQTStepper.CasingType.F_LASER_MKII, 2));
        TiredTraceabilityPredicate.MAP_ZJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.CLEAN_MKII),
                new WrappedIntTired(GTQTStepper.CasingType.CLEAN_MKII, 2));

        TiredTraceabilityPredicate.MAP_ZW_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.LASER_MKIII),
                new WrappedIntTired(GTQTStepper.CasingType.LASER_MKIII, 3));
        TiredTraceabilityPredicate.MAP_TJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.F_LASER_MKIII),
                new WrappedIntTired(GTQTStepper.CasingType.F_LASER_MKIII, 3));
        TiredTraceabilityPredicate.MAP_ZJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.CLEAN_MKIII),
                new WrappedIntTired(GTQTStepper.CasingType.CLEAN_MKIII, 3));

        TiredTraceabilityPredicate.MAP_ZW_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.LASER_MKIV),
                new WrappedIntTired(GTQTStepper.CasingType.LASER_MKIV, 4));
        TiredTraceabilityPredicate.MAP_TJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.F_LASER_MKIV),
                new WrappedIntTired(GTQTStepper.CasingType.F_LASER_MKIV, 4));
        TiredTraceabilityPredicate.MAP_ZJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.CLEAN_MKIV),
                new WrappedIntTired(GTQTStepper.CasingType.CLEAN_MKIV, 4));

        TiredTraceabilityPredicate.MAP_ZW_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.LASER_MKV),
                new WrappedIntTired(GTQTStepper.CasingType.LASER_MKV, 5));
        TiredTraceabilityPredicate.MAP_TJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.F_LASER_MKV),
                new WrappedIntTired(GTQTStepper.CasingType.F_LASER_MKV, 5));
        TiredTraceabilityPredicate.MAP_ZJ_CASING.put(GTQTMetaBlocks.STEPPER.getState(GTQTStepper.CasingType.CLEAN_MKV),
                new WrappedIntTired(GTQTStepper.CasingType.CLEAN_MKV, 5));
    }
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_QFT_GLASS;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_DRI_CASING;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_QFT_SHIELDING_CORE;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_QFT_MANIPULATOR;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_ESSENTIA_CELLS;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_MACHINE_CASING;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_GLASS;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_LGLASS;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_CP_CASING;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_CP_TUBE;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_CP_BEAM;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_CAL_CASING;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_ELE_CASING;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_PA_CASING;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_SP_CASING;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_PA_INTERNAL_CASING;
    //光刻系统
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_ZW_CASING;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_TJ_CASING;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_ZJ_CASING;
    //计算机
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_CPU_CASING;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_GPU_CASING;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_RAM_CASING;

    public static TraceabilityPredicate CP_CPU_CASING1 = new TiredTraceabilityPredicate(MAP_CPU_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_CPU_CASING.get(s)).getIntTier()),"CPU1",null);
    public static TraceabilityPredicate CP_GPU_CASING1 = new TiredTraceabilityPredicate(MAP_GPU_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_GPU_CASING.get(s)).getIntTier()),"GPU1",null);
    public static TraceabilityPredicate CP_RAM_CASING1 = new TiredTraceabilityPredicate(MAP_RAM_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_RAM_CASING.get(s)).getIntTier()),"RAM1",null);


    public static TraceabilityPredicate CP_CPU_CASING2 = new TiredTraceabilityPredicate(MAP_CPU_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_CPU_CASING.get(s)).getIntTier()),"CPU2",null);
    public static TraceabilityPredicate CP_GPU_CASING2 = new TiredTraceabilityPredicate(MAP_GPU_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_GPU_CASING.get(s)).getIntTier()),"GPU2",null);
    public static TraceabilityPredicate CP_RAM_CASING2 = new TiredTraceabilityPredicate(MAP_RAM_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_RAM_CASING.get(s)).getIntTier()),"RAM2",null);


    public static TraceabilityPredicate CP_CPU_CASING3 = new TiredTraceabilityPredicate(MAP_CPU_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_CPU_CASING.get(s)).getIntTier()),"CPU3",null);
    public static TraceabilityPredicate CP_GPU_CASING3 = new TiredTraceabilityPredicate(MAP_GPU_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_GPU_CASING.get(s)).getIntTier()),"GPU3",null);
    public static TraceabilityPredicate CP_RAM_CASING3 = new TiredTraceabilityPredicate(MAP_RAM_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_RAM_CASING.get(s)).getIntTier()),"RAM3",null);


    public static TraceabilityPredicate CP_CPU_CASING4 = new TiredTraceabilityPredicate(MAP_CPU_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_CPU_CASING.get(s)).getIntTier()),"CPU4",null);
    public static TraceabilityPredicate CP_GPU_CASING4 = new TiredTraceabilityPredicate(MAP_GPU_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_GPU_CASING.get(s)).getIntTier()),"GPU4",null);
    public static TraceabilityPredicate CP_RAM_CASING4 = new TiredTraceabilityPredicate(MAP_RAM_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_RAM_CASING.get(s)).getIntTier()),"RAM4",null);

    public static TraceabilityPredicate CP_ZW_CASING = new TiredTraceabilityPredicate(MAP_ZW_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_ZW_CASING.get(s)).getIntTier()),"ZW",null);
    public static TraceabilityPredicate CP_TJ_CASING = new TiredTraceabilityPredicate(MAP_TJ_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_TJ_CASING.get(s)).getIntTier()),"TJ",null);
    public static TraceabilityPredicate CP_ZJ_CASING = new TiredTraceabilityPredicate(MAP_ZJ_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_ZJ_CASING.get(s)).getIntTier()),"ZJ",null);


    public static TraceabilityPredicate CP_LGLASS = new TiredTraceabilityPredicate(MAP_LGLASS,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_LGLASS.get(s)).getIntTier()),"LGL",null);
    public static TraceabilityPredicate CP_PA_CASING = new TiredTraceabilityPredicate(MAP_PA_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_PA_CASING.get(s)).getIntTier()),"PAC",null);

    public static TraceabilityPredicate CP_PA_INTERNAL_CASING =new TiredTraceabilityPredicate(MAP_PA_INTERNAL_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_PA_INTERNAL_CASING.get(s)).getIntTier()), "PAI", null);

    public static TraceabilityPredicate CP_SP_CASING =new TiredTraceabilityPredicate(MAP_SP_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_SP_CASING.get(s)).getIntTier()), "SP", null);
    public static TraceabilityPredicate CP_ELE_CASING = new TiredTraceabilityPredicate(MAP_ELE_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_ELE_CASING.get(s)).getIntTier()),"Ele",null);
    public static TraceabilityPredicate CP_DRI_CASING = new TiredTraceabilityPredicate(MAP_DRI_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_DRI_CASING.get(s)).getIntTier()),"Dri",null);
    public static TraceabilityPredicate MACHINE_CASINGS = new TiredTraceabilityPredicate(MAP_MACHINE_CASING,"MachineCasingType",null);

    public static TraceabilityPredicate ESSENTIA_CELLS = new TiredTraceabilityPredicate(MAP_ESSENTIA_CELLS,"ESS_CELL","gc.multiblock.pattern.error.essentia")
            .addTooltips("gc.multiblock.pattern.error.essentia");

    public static TraceabilityPredicate CP_CASING = new TiredTraceabilityPredicate(MAP_CP_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_CP_CASING.get(s)).getIntTier()),"ChemicalPlantCasing",null);
    public static TraceabilityPredicate CP_TUBE = new TiredTraceabilityPredicate(MAP_CP_TUBE,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_CP_TUBE.get(s)).getIntTier()),"ChemicalPlantTube",null);

    public static TraceabilityPredicate CP_GLASS = new TiredTraceabilityPredicate(MAP_GLASS,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_GLASS.get(s)).getIntTier()),"Glass",null);

    public static TraceabilityPredicate CP_BEAM = new TiredTraceabilityPredicate(MAP_CP_BEAM,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_CP_BEAM.get(s)).getIntTier()),"Beam",null);

    public static TraceabilityPredicate QFT_MANIPULATOR = new TiredTraceabilityPredicate(MAP_QFT_MANIPULATOR,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_QFT_MANIPULATOR.get(s)).getIntTier()),"Manipulatpr",null);
    public static TraceabilityPredicate QFT_SHIELDING_CORE = new TiredTraceabilityPredicate(MAP_QFT_SHIELDING_CORE,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_QFT_SHIELDING_CORE.get(s)).getIntTier()),"Core",null);
    public static TraceabilityPredicate QFT_GLASS = new TiredTraceabilityPredicate(MAP_QFT_GLASS,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_QFT_GLASS.get(s)).getIntTier()),"QFTGlass",null);

    public static TraceabilityPredicate CAL_CASING = new TiredTraceabilityPredicate(MAP_CAL_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_CAL_CASING.get(s)).getIntTier()),"CALCasing",null);
    private final Object2ObjectOpenHashMap<IBlockState, ITired> map;
    private final String name;

    private final String errorKey;

    private Supplier<BlockInfo[]> candidatesCache;

    private final Comparator<IBlockState> comparator;


    public TiredTraceabilityPredicate(Object2ObjectOpenHashMap<IBlockState, ITired> map, String name, @Nullable String errorKey){
        this(map,Comparator.comparing((s) -> map.get(s).getName()),name,errorKey);
    }

    public TiredTraceabilityPredicate(Object2ObjectOpenHashMap<IBlockState, ITired> map,Comparator<IBlockState> comparator,String name,@Nullable String errorKey){
        super();
        this.map = map;
        this.name = name;
        if(errorKey == null){
            this.errorKey = "gregtech.multiblock.pattern.error.casing";
        }
        else{
            this.errorKey = errorKey;
        }
        this.common.add(new SimplePredicate(predicate(), candidates()));
        this.comparator = comparator;

    }

    private Predicate<BlockWorldState> predicate(){
        return  (blockWorldState) -> {
            IBlockState blockState = blockWorldState.getBlockState();
            if (map.containsKey(blockState)) {
                ITired stats = map.get(blockState);
                Object tier = stats.getTire();
                Object current = blockWorldState.getMatchContext().getOrPut(name, tier);
                if (!current.equals(tier)) {
                    blockWorldState.setError(new PatternStringError(errorKey));
                    return false;
                } else {
                    blockWorldState.getMatchContext().getOrPut(name+"TiredStats",stats);
                    if(blockState.getBlock() instanceof VariantActiveBlock){
                        (blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>())).add(blockWorldState.getPos());
                    }
                    return true;
                }
            } else {
                return false;
            }
        };
    }

    private Supplier<BlockInfo[]> candidates(){
        if(candidatesCache == null) {
            candidatesCache = () -> map.keySet().stream()
                    .sorted(comparator)
                    .map(type -> new BlockInfo(type, null,map.get(type).getInfo()))
                    .toArray(BlockInfo[]::new);
        }
        return candidatesCache;
    }
}