package keqing.gtqtcore.api.pattern.utils;

import gregtech.api.util.RelativeDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JsonBlockPattern {
    public RelativeDirection[] structureDir;
    public String[][] pattern;
    public int[][] aisleRepetitions;
    public Map<Character, Set<String>> symbolMap;

    public JsonBlockPattern() {
        symbolMap = new HashMap<>();
        structureDir = new RelativeDirection[] {RelativeDirection.LEFT, RelativeDirection.UP, RelativeDirection.FRONT};
        symbolMap.computeIfAbsent(' ', key -> new HashSet<>()).add("any"); // any
        symbolMap.computeIfAbsent('#', key -> new HashSet<>()).add("air"); // air
        symbolMap.computeIfAbsent('@', key -> new HashSet<>()).add("controller"); // controller
    }

    public JsonBlockPattern(World world,/* BlockPos controllerPos, EnumFacing facing, */int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this();
        pattern = new String[1 + maxX - minX][1 + maxY - minY];
//        if (facing == EnumFacing.WEST) {
//            structureDir = new RelativeDirection[] {RelativeDirection.LEFT, RelativeDirection.UP, RelativeDirection.BACK};
//        } else if (facing == EnumFacing.EAST) {
//            structureDir = new RelativeDirection[] {RelativeDirection.RIGHT, RelativeDirection.UP, RelativeDirection.FRONT};
//        } else if (facing == EnumFacing.NORTH) {
//            structureDir = new RelativeDirection[] {RelativeDirection.BACK, RelativeDirection.UP, RelativeDirection.RIGHT};
//        } else if (facing == EnumFacing.SOUTH) {
//            structureDir = new RelativeDirection[] {RelativeDirection.FRONT, RelativeDirection.UP, RelativeDirection.LEFT};
//        }
        aisleRepetitions = new int[pattern.length][2];
        for (int[] aisleRepetition : aisleRepetitions) {
            aisleRepetition[0] = 1;
            aisleRepetition[1] = 1;
        }

        Map<IBlockState, Character> map = new HashMap<>();
        map.put(Blocks.AIR.getDefaultState(), ' ');

        char c = 'A'; // auto

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                StringBuilder builder = new StringBuilder();
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
//                    if (controllerPos.equals(pos)) {
//                        builder.append('@'); // controller
//                    } else {
                        IBlockState state = world.getBlockState(pos);
                        if (!map.containsKey(state)) {
                            map.put(state, c);
                            String name = String.valueOf(c);
                            symbolMap.computeIfAbsent(c, key -> new HashSet<>()).add(name); // any
                            c++;
                        }
                        builder.append(map.get(state));
//                    }
                }
                pattern[x - minX][y - minY] = builder.toString();
            }
        }
    }

    public BlockPos getActualPosOffset(int x, int y, int z, EnumFacing facing) {
        int[] c0 = new int[]{x, y, z}, c1 = new int[3];
        remapping(c0, c1, facing);
        return new BlockPos(c1[0], c1[1], c1[2]);
    }

    public int[] getActualPatternOffset(BlockPos pos, EnumFacing facing) {
        int[] c0 = new int[]{pos.getX(), pos.getY(), pos.getZ()}, c1 = new int[3];
        remapping(c0, c1, facing);
        return c1;
    }

    public void remapping(int[] c0, int[] c1, EnumFacing facing){
        for (int i = 0; i < 3; i++) {
            EnumFacing realFacing = structureDir[i].getActualFacing(facing);
            if (realFacing == EnumFacing.UP) {
                c1[1] = c0[i];
            } else if (realFacing == EnumFacing.DOWN) {
                c1[1] = -c0[i];
            } else if (realFacing == EnumFacing.WEST) {
                c1[0] = -c0[i];
            } else if (realFacing == EnumFacing.EAST) {
                c1[0] = c0[i];
            } else if (realFacing == EnumFacing.NORTH) {
                c1[2] = -c0[i];
            } else if (realFacing == EnumFacing.SOUTH) {
                c1[2] = c0[i];
            }
        }
    }

    public void cleanUp() {
        Set<Character> usedChar = new HashSet<>();
        Set<String> usedPredicate = new HashSet<>();
        for (String[] strings : pattern) {
            for (String string : strings) {
                for (char c : string.toCharArray()) {
                    usedChar.add(c);
                }
            }
        }
        symbolMap.entrySet().removeIf(entry -> !usedChar.contains(entry.getKey()));
        symbolMap.forEach((symbol, predicates) -> usedPredicate.addAll(predicates));
    }

//    public String toJson() {
//        return Multiblocked.GSON.toJson(this);
//    }

    public int[] getCenterOffset() {
        int[] centerOffset = new int[3];
        for (int i = 0; i < pattern.length; i++) {
            for (int j = 0; j < pattern[0].length; j++) {
                for (int k = 0; k < pattern[0][0].length(); k++) {
                    if (pattern[i][j].charAt(k) == '@') {
                        centerOffset = new int[]{i, j, k};
                        break;
                    }
                }
            }
        }
        return centerOffset;
    }

    public JsonBlockPattern copy() {
        JsonBlockPattern newPattern = new JsonBlockPattern();
        System.arraycopy(this.structureDir, 0, newPattern.structureDir, 0, this.structureDir.length);

        newPattern.pattern = new String[pattern.length][pattern[0].length];
        for (int i = 0; i < pattern.length; i++) {
            System.arraycopy(pattern[i], 0, newPattern.pattern[i], 0, pattern[i].length);
        }

        newPattern.aisleRepetitions = new int[aisleRepetitions.length][2];
        for (int i = 0; i < aisleRepetitions.length; i++) {
            System.arraycopy(aisleRepetitions[i], 0, newPattern.aisleRepetitions[i], 0, aisleRepetitions[i].length);
        }

        symbolMap.forEach((k, v) -> newPattern.symbolMap.put(k, new HashSet<>(v)));

        return newPattern;
    }
}
