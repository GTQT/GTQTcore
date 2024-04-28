package keqing.gtqtcore.mixin.gregtech;

import gregtech.api.worldgen.shape.EllipsoidGenerator;
import gregtech.api.worldgen.shape.IBlockGeneratorAccess;
import gregtech.api.worldgen.shape.ShapeGenerator;
import gregtech.api.worldgen.shape.SphereGenerator;
import gregtech.client.renderer.texture.Textures;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(SphereGenerator.class)
public abstract class MixinSphereGenerator extends ShapeGenerator {

    @Override
    public void generate(Random gridRandom, IBlockGeneratorAccess relativeBlockAccess) {
        Random rand = new Random();
        int randomNum = rand.nextInt(5);
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
                for (int y = p-24; y <= p+24; y++) {
                    relativeBlockAccess.generateBlock(x, y, z);
                }
                for (int y = p+40; y <= p+42; y++) {
                    relativeBlockAccess.generateBlock(x, y, z);
                }
            }
        }
    }
    public void generateBlock(int x, int y, int z, IBlockGeneratorAccess blockAccess) {
        blockAccess.generateBlock(x, y, z);
    }
}