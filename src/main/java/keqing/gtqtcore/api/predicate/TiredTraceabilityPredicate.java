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
import keqing.gtqtcore.common.block.blocks.GTQTADVGlass;
import keqing.gtqtcore.common.block.blocks.GTQTMultiblockCasing;
import keqing.gtqtcore.common.block.blocks.GTQTQuantumForceTransformerCasing;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
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
        MAP_CP_CASING = new Object2ObjectOpenHashMap<>();
        MAP_CP_TUBE = new Object2ObjectOpenHashMap<>();
        MAP_CP_BEAM = new Object2ObjectOpenHashMap<>();
        MAP_QFT_MANIPULATOR = new Object2ObjectOpenHashMap<>();
        MAP_QFT_SHIELDING_CORE = new Object2ObjectOpenHashMap<>();
        MAP_QFT_GLASS = new Object2ObjectOpenHashMap<>();

        for (BlockMachineCasing.MachineCasingType type : Arrays.stream(BlockMachineCasing.MachineCasingType.values()).filter((c)-> c.ordinal()<10).collect(Collectors.toList())) {
            TiredTraceabilityPredicate.MAP_MACHINE_CASING.put(MetaBlocks.MACHINE_CASING.getState(type),new WrappedIntTired(type,type.ordinal()));
        }

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

        TiredTraceabilityPredicate.MAP_CP_TUBE.put(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.BRONZE_PIPE),
                new WrappedIntTired(BlockBoilerCasing.BoilerCasingType.BRONZE_PIPE,1));
        TiredTraceabilityPredicate.MAP_CP_TUBE.put(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE),
                new WrappedIntTired(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE,2));
        TiredTraceabilityPredicate.MAP_CP_TUBE.put(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TITANIUM_PIPE),
                new WrappedIntTired(BlockBoilerCasing.BoilerCasingType.BRONZE_PIPE,5));
        TiredTraceabilityPredicate.MAP_CP_TUBE.put(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE),
                new WrappedIntTired(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE,6));
        TiredTraceabilityPredicate.MAP_CP_TUBE.put(GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.PD_MACHINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing.TurbineCasingType.PD_MACHINE_CASING,7));
        TiredTraceabilityPredicate.MAP_CP_TUBE.put(GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_MACHINE_CASING),
                new WrappedIntTired(GTQTTurbineCasing.TurbineCasingType.NQ_MACHINE_CASING,8));


        TiredTraceabilityPredicate.MAP_GLASS.put(MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS),
                new WrappedIntTired(BlockGlassCasing.CasingType.FUSION_GLASS,1));

        TiredTraceabilityPredicate.MAP_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_IV),
                new WrappedIntTired(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_IV,2));

        TiredTraceabilityPredicate.MAP_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_V),
                new WrappedIntTired(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_V,3));

        TiredTraceabilityPredicate.MAP_GLASS.put(GTQTMetaBlocks.ADV_GLASS.getState(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_VI),
                new WrappedIntTired(GTQTADVGlass.CasingType.TECH_FUSION_GLASS_VI,4));

        TiredTraceabilityPredicate.MAP_CP_BEAM.put(GTQTMetaBlocks.MULTI_CASING.getState(GTQTMultiblockCasing.CasingType.BEAM_CORE_0),
                new WrappedIntTired(GTQTMultiblockCasing.CasingType.BEAM_CORE_0,1));

        TiredTraceabilityPredicate.MAP_CP_BEAM.put(GTQTMetaBlocks.MULTI_CASING.getState(GTQTMultiblockCasing.CasingType.BEAM_CORE_1),
                new WrappedIntTired(GTQTMultiblockCasing.CasingType.BEAM_CORE_1,2));

        TiredTraceabilityPredicate.MAP_CP_BEAM.put(GTQTMetaBlocks.MULTI_CASING.getState(GTQTMultiblockCasing.CasingType.BEAM_CORE_2),
                new WrappedIntTired(GTQTMultiblockCasing.CasingType.BEAM_CORE_2,3));

        TiredTraceabilityPredicate.MAP_CP_BEAM.put(GTQTMetaBlocks.MULTI_CASING.getState(GTQTMultiblockCasing.CasingType.BEAM_CORE_3),
                new WrappedIntTired(GTQTMultiblockCasing.CasingType.BEAM_CORE_3,4));

        TiredTraceabilityPredicate.MAP_CP_BEAM.put(GTQTMetaBlocks.MULTI_CASING.getState(GTQTMultiblockCasing.CasingType.BEAM_CORE_4),
                new WrappedIntTired(GTQTMultiblockCasing.CasingType.BEAM_CORE_4,5));



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


    }
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_QFT_GLASS;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_QFT_SHIELDING_CORE;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_QFT_MANIPULATOR;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_ESSENTIA_CELLS;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_MACHINE_CASING;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_GLASS;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_CP_CASING;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_CP_TUBE;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_CP_BEAM;


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