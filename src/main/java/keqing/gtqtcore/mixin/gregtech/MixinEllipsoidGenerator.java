package keqing.gtqtcore.mixin.gregtech;

import gregtech.api.block.VariantActiveBlock;
import gregtech.api.block.VariantItemBlock;
import gregtech.api.worldgen.shape.EllipsoidGenerator;
import gregtech.api.worldgen.shape.IBlockGeneratorAccess;
import gregtech.api.worldgen.shape.ShapeGenerator;
import gregtech.common.blocks.BlockGlassCasing;
import keqing.gtqtcore.api.blocks.ITierGlassBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

@Mixin(EllipsoidGenerator.class)
public abstract class MixinEllipsoidGenerator extends ShapeGenerator {

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
        int yMax = Math.min(24, getYRadius());
        int randomNum = rand.nextInt(5);
        int randomHig = rand.nextInt(15);
        int h=(randomHig+1)/4;
        int p;

        for (int x = -24; x <= 24; x++) {
            for (int z = -24; z <= 24; z++) {
                switch (randomNum) {
                    case (1) -> p = (x / 3) + (z / 3);
                    case (2) -> p = (x / 3)  - (z / 3);
                    case (3) -> p = -(x / 3) + (z / 3);
                    case (4) -> p = -(x / 3) - (z / 3);
                    default -> p=0;
                }
                for (int y = p*h-yMax; y <= p*h+yMax; y++) {
                    generateBlock(x, y, z, blockAccess);
                }
                for (int y = p*h+22; y <= p*h+24; y++) {
                    generateBlock(x, y, z, blockAccess);
                }
            }
        }
    }
    public void generateBlock(int x, int y, int z, IBlockGeneratorAccess blockAccess) {

        blockAccess.generateBlock(x, y, z);
    }
}