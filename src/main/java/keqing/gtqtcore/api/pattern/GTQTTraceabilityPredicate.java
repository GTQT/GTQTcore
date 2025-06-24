package keqing.gtqtcore.api.pattern;

import gregtech.api.block.VariantActiveBlock;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.PatternStringError;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.util.BlockInfo;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.utils.GTQTUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static keqing.gtqtcore.api.utils.GTQTUniversUtil.getTileEntity;

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
    public static TraceabilityPredicate optionalAbilities(String mark, MultiblockAbility<?>... allowedAbilities) {
        return new TraceabilityPredicate(blockWorldState -> {
            TileEntity tileEntity = blockWorldState.getTileEntity();
            if (tileEntity instanceof IGregTechTileEntity) {
                MetaTileEntity metaTileEntity = ((IGregTechTileEntity) tileEntity).getMetaTileEntity();
                if (metaTileEntity instanceof IMultiblockAbilityPart<?>
                        && ArrayUtils.contains(allowedAbilities, ((IMultiblockAbilityPart<?>) metaTileEntity).getAbility())) {
                    Set<IMultiblockPart> partsFound = blockWorldState.getMatchContext().getOrCreate("MultiblockParts", HashSet::new);
                    partsFound.add((IMultiblockPart) metaTileEntity);
                    return (blockWorldState.getMatchContext().getOrPut(mark, true));
                }
            }
            return blockWorldState.getMatchContext().get(mark) == null;
        }, GTQTUtil.getCandidates(Arrays.stream(allowedAbilities).flatMap(ability -> MultiblockAbility.REGISTRY.get(ability).stream()).toArray(MetaTileEntity[]::new)));
    }

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
                    blockWorldState.setError(new PatternStringError("gtqtcore.machine.reinforced_rotor_holder.error"));
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
            .addTooltips("gtqtcore.machine.reinforced_rotor_holder.error");

    public static Supplier<BlockInfo[]> getCandidates(IBlockState... allowedStates) {
        return () -> Arrays.stream(allowedStates).map(state -> new BlockInfo(state, null)).toArray(BlockInfo[]::new);
    }

}
