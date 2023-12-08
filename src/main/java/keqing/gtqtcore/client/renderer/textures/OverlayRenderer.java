package keqing.gtqtcore.client.renderer.textures;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.resources.ResourceHelper;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.cclop.LightMapOperation;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.BloomEffectUtil;
import gregtech.common.ConfigHolder;
import keqing.gtqtcore.GTQTCore;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;

public class OverlayRenderer implements ICubeRenderer {
    private final String path;

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite sprite;

    @Nullable
    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite spriteEmissive;

    public OverlayRenderer(String path) {
        this.path = path;
        Textures.CUBE_RENDERER_REGISTRY.put(path, this);
        Textures.iconRegisters.add(this);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(TextureMap textureMap) {
        String modID = GTQTCore.MODID;
        String path = this.path;
        String[] split = this.path.split(":");
        if (split.length == 2) {
            modID = split[0];
            path = split[1];
        }
        this.sprite = textureMap.registerSprite(new ResourceLocation(modID, "blocks/" + path));
        String emissive = "blocks/" + path + "_emissive";
        if (ResourceHelper.doResourcepacksHaveTexture(modID, emissive, true)) {
            this.spriteEmissive = textureMap.registerSprite(new ResourceLocation(modID, emissive));
        }
    }

    @SideOnly(Side.CLIENT)
    public void renderOrientedState(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 bounds, EnumFacing frontFacing, boolean isActive, boolean isWorkingEnabled) {
        Textures.renderFace(renderState, translation, pipeline, frontFacing, bounds, this.sprite, BlockRenderLayer.CUTOUT_MIPPED);
        if (this.spriteEmissive != null) {
            if (ConfigHolder.client.machinesEmissiveTextures) {
                IVertexOperation[] lightPipeline = (IVertexOperation[]) ArrayUtils.add(pipeline, new LightMapOperation(240, 240));
                Textures.renderFace(renderState, translation, lightPipeline, frontFacing, bounds, this.spriteEmissive, BloomEffectUtil.getBloomLayer());
            } else {
                Textures.renderFace(renderState, translation, pipeline, frontFacing, bounds, this.spriteEmissive, BlockRenderLayer.CUTOUT_MIPPED);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getParticleSprite() {
        return this.sprite;
    }
}
