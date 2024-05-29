package keqing.gtqtcore.api.pattern;

import gregtech.api.block.VariantActiveBlock;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.PatternStringError;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.util.BlockInfo;
import gregtech.common.blocks.BlockFireboxCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static keqing.gtqtcore.api.utils.GTQTUniverUtil.getTileEntity;

public class GTQTTraceabilityPredicate {
    public static TraceabilityPredicate optionalStates(String mark, IBlockState... allowedStates) {
        return new TraceabilityPredicate(blockWorldState -> {
            IBlockState state = blockWorldState.getBlockState();
            if (state.getBlock() instanceof VariantActiveBlock) {
                blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>()).add(blockWorldState.getPos());
            }
            if (ArrayUtils.contains(allowedStates, state)) {
                return (blockWorldState.getMatchContext().getOrPut(mark, true));
            }
            return blockWorldState.getMatchContext().get(mark) == null;
        }, getCandidates(allowedStates));
    }
    public static Supplier<TraceabilityPredicate> FIRE_BOX = () -> new TraceabilityPredicate(blockWorldState -> {
        IBlockState blockState = blockWorldState.getBlockState();
        if ((blockState.getBlock() instanceof BlockFireboxCasing BlockFireboxCasing)) {
            BlockFireboxCasing.FireboxCasingType casingType = BlockFireboxCasing.getState(blockState);
            Object currentCasingType = blockWorldState.getMatchContext().getOrPut("CasingType", casingType);
            if (!currentCasingType.toString().equals(casingType.toString())) {
                blockWorldState.setError(new PatternStringError("gtqtcore.machine.pattern.firebox"));
                return false;
            }
            blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>()).add(blockWorldState.getPos());
            return true;
        }
        return false;
    }, () -> ArrayUtils.addAll(Arrays.stream(BlockFireboxCasing.FireboxCasingType.values())
            .map(type -> new BlockInfo(MetaBlocks.BOILER_FIREBOX_CASING.getState(type), null))
            .toArray(BlockInfo[]::new)))
            .addTooltips("gtlitecore.machine.pattern.firebox");
    public static Supplier<TraceabilityPredicate> ROTOR_HOLDER = () -> new TraceabilityPredicate(blockWorldState -> {
        TileEntity tileEntity = blockWorldState.getTileEntity();
        if (tileEntity instanceof IGregTechTileEntity) {
            List<ResourceLocation> list = MultiblockAbility.REGISTRY.get(GTQTMultiblockAbility.REINFORCED_ROTOR_HOLDER_ABILITY).stream()
                    .map(mte -> mte.metaTileEntityId)
                    .collect(Collectors.toList());
            MetaTileEntity mte = ((IGregTechTileEntity)tileEntity).getMetaTileEntity();
            if (list.contains(mte.metaTileEntityId)) {
                int tier = ((ITieredMetaTileEntity) mte).getTier();
                Object currentTier = blockWorldState.getMatchContext().getOrPut("RotorHolderTier", tier);
                if (!currentTier.equals(tier)) {
                    blockWorldState.setError(new PatternStringError("gtlitecore.machine.reinforced_rotor_holder.error"));
                    return false;
                }
                Set<IMultiblockPart> partsFound = blockWorldState.getMatchContext().getOrCreate("MultiblockParts", HashSet::new);
                partsFound.add((IMultiblockPart) mte);
                return true;
            }
        }
        return false;
    }, () -> MultiblockAbility.REGISTRY.get(GTQTMultiblockAbility.REINFORCED_ROTOR_HOLDER_ABILITY).stream()
            .sorted(Comparator.comparingInt(mte -> ((ITieredMetaTileEntity) mte).getTier()))
            .map(mte -> new BlockInfo(MetaBlocks.MACHINE.getDefaultState(), getTileEntity(mte)))
            .toArray(BlockInfo[]::new))
            .addTooltips("gtlitecore.machine.reinforced_rotor_holder.error");

    public static Supplier<BlockInfo[]> getCandidates(IBlockState... allowedStates) {
        return () -> Arrays.stream(allowedStates).map(state -> new BlockInfo(state, null)).toArray(BlockInfo[]::new);
    }

}
