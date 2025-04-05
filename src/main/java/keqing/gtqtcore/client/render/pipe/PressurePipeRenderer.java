package keqing.gtqtcore.client.render.pipe;

import codechicken.lib.vec.uv.IconTransformation;
import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.block.IPipeType;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.unification.material.Material;
import gregtech.client.renderer.pipe.PipeRenderer;
import keqing.gtqtcore.GTQTCore;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class PressurePipeRenderer extends PipeRenderer {

    public static final PressurePipeRenderer INSTANCE = new PressurePipeRenderer();

    private PressurePipeRenderer() {
        super("pressure_pipe", new ResourceLocation(GTQTCore.MODID, "pressure_pipe"));
    }

    @Override
    public void registerIcons(TextureMap textureMap) {
    }

    @Override
    public void buildRenderer(PipeRenderContext pipeRenderContext, BlockPipe<?, ?, ?> blockPipe, @Nullable IPipeTile<?, ?> iPipeTile, IPipeType<?> iPipeType, @Nullable Material material) {
        pipeRenderContext.addOpenFaceRender(new IconTransformation(GTQTTextures.PRESSURE_PIPE_OPEN))
                .addSideRender(new IconTransformation(GTQTTextures.PRESSURE_PIPE_SIDE));
    }

    @Override
    public TextureAtlasSprite getParticleTexture(IPipeType<?> iPipeType, @Nullable Material material) {
        return GTQTTextures.PRESSURE_PIPE_SIDE;
    }
}