package keqing.gtqtcore.common.worldgen;

import gregtech.api.GTValues;
import keqing.gtqtcore.GTQTCoreConfig;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import static keqing.gtqtcore.GTQTCoreConfig.WorldGenSwitch;

public class WorldGenAbandonedBase implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (GTQTCoreConfig.WorldGenSwitch.abandonedBaseRarity == 0) return;

        if (GTQTCoreConfig.WorldGenSwitch.abandonedBaseRarity*100 == 0 || world.getWorldType() == WorldType.FLAT || world.provider.getDimensionType() != DimensionType.OVERWORLD || !world.getWorldInfo().isMapFeaturesEnabled()) {
            return; //do not generate in flat worlds, or in non-surface worlds
        }
        BlockPos randomPos = new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8);

        if (random.nextInt(WorldGenSwitch.abandonedBaseRarity*100) == 0) {
            int variantNumber = random.nextInt(7);


            Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
            ResourceLocation templateId = new ResourceLocation(GTValues.MODID, "abandoned_base/abandoned_base_1_" + variantNumber);
            Template template = TemplateManager.getBuiltinTemplate(world, templateId);
            BlockPos originPos = template.getZeroPositionWithTransform(randomPos, Mirror.NONE, rotation);
            originPos = TemplateManager.calculateAverageGroundLevel(world, originPos, template.getSize());
            if (variantNumber == 6)
                template.addBlocksToWorld(world, originPos.add(0, -26, 0), new PlacementSettings().setRotation(rotation));
            else template.addBlocksToWorld(world, originPos, new PlacementSettings().setRotation(rotation));

        }
    }
}