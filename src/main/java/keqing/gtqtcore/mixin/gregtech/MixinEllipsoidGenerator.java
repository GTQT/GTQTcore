package keqing.gtqtcore.mixin.gregtech;

import gregtech.api.worldgen.shape.EllipsoidGenerator;
import gregtech.api.worldgen.shape.IBlockGeneratorAccess;
import gregtech.api.worldgen.shape.ShapeGenerator;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Random;

@Mixin(EllipsoidGenerator.class)
public abstract class MixinEllipsoidGenerator extends ShapeGenerator {
    @Shadow
    public int getYRadius() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Vec3i getMaxSize() {
        return new Vec3i(24 * 2, 24, 24 * 2);
    }

    @Override
    public void generate(Random gridRandom, IBlockGeneratorAccess blockAccess) {
        Random rand = new Random();
        int shapeType = rand.nextInt(2); // 随机选择矿脉形态类型：0-原椭圆，1-竖向，2-蛇形，3-螺旋

        switch (shapeType) {
            case 0:
                generateEllipsoidVariant$gtqtcore(rand, blockAccess); // 原有椭圆或倾斜椭圆
                break;
            case 1:
                generateSpiralVein$gtqtcore(blockAccess); // 螺旋矿脉
                break;
        }
    }

    // 原有椭圆/倾斜椭圆生成逻辑
    @Unique
    private void generateEllipsoidVariant$gtqtcore(Random rand, IBlockGeneratorAccess blockAccess) {
        int yMax = Math.min(24, getYRadius());
        int randomNum = rand.nextInt(5);
        int randomHig = rand.nextInt(15);
        int h = (randomHig + 1) / 4;
        int p;

        for (int x = -24; x <= 24; x++) {
            for (int z = -24; z <= 24; z++) {
                switch (randomNum) {
                    case 1 -> p = (x / 3) + (z / 3);
                    case 2 -> p = (x / 3) - (z / 3);
                    case 3 -> p = -(x / 3) + (z / 3);
                    case 4 -> p = -(x / 3) - (z / 3);
                    default -> p = 0;
                }
                for (int y = p * h - yMax; y <= p * h + yMax; y++) {
                    generateBlock(x, y, z, blockAccess);
                }
            }
        }
    }

    // 螺旋矿脉（Y轴螺旋上升）
    @Unique
    private void generateSpiralVein$gtqtcore(IBlockGeneratorAccess blockAccess) {
        int radius = 18; // 螺旋半径
        int thickness = 8; // 截面厚度

        for (int y = -36; y <= 36; y++) {
            double angle = y * Math.PI / 12; // 每层Y对应旋转角度
            int xCenter = (int) (radius * Math.cos(angle));
            int zCenter = (int) (radius * Math.sin(angle));

            // 在中心点周围生成方块
            for (int dx = -thickness; dx <= thickness; dx++) {
                for (int dz = -thickness; dz <= thickness; dz++) {
                    int x = xCenter + dx;
                    int z = zCenter + dz;
                    if (x >= -24 && x <= 24 && z >= -24 && z <= 24) {
                        generateBlock(x, y, z, blockAccess);
                    }
                }
            }
        }
    }

    @Shadow
    public void generateBlock(int x, int y, int z, IBlockGeneratorAccess blockAccess) {
        blockAccess.generateBlock(x, y, z);
    }
}