package keqing.gtqtcore.client.textures.custom;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils.IIconRegister;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.texture.IconRegistrar;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class IsaMillRenderer implements IIconRegister, IconRegistrar {
    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite baseBackgroundSprite;
    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite activeBladeSprite;

    public IsaMillRenderer() {
        Textures.iconRegisters.add(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(TextureMap textureMap) {
        this.baseBackgroundSprite = textureMap.registerSprite(new ResourceLocation(GTValues.MODID, "blocks/overlay/isa_mill/base_background"));
        this.activeBladeSprite = textureMap.registerSprite(new ResourceLocation(GTValues.MODID, "blocks/overlay/isa_mill/rotor_spinning"));
    }

    @SideOnly(Side.CLIENT)
    public void renderSided(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, EnumFacing side, boolean hasBase, boolean isActive) {
        Matrix4 cornerOffset = null;
        switch (side.getAxis()) {
            case X:
                cornerOffset = translation.copy().translate(0.01 * side.getXOffset(), -1.0, -1.0);
                cornerOffset.scale(1.0, 3.0, 3.0);
                break;
            case Z:
                cornerOffset = translation.copy().translate(-1.0, -1.0, 0.01 * side.getZOffset());
                cornerOffset.scale(3.0, 3.0, 1.0);
                break;
            case Y:
                cornerOffset = translation.copy().translate(-1.0, 0.01 * side.getYOffset(), -1.0);
                cornerOffset.scale(3.0, 1.0, 3.0);
                break;
        }
        if(hasBase && !isActive) {
            Textures.renderFace(renderState, cornerOffset, /*ArrayUtils.addAll(pipeline, new LightMapOperation(240, 240))*/pipeline, side, Cuboid6.full, baseBackgroundSprite, BlockRenderLayer.CUTOUT_MIPPED);
        }
        if (isActive) {
            Textures.renderFace(renderState, cornerOffset, pipeline, side, Cuboid6.full, activeBladeSprite, BlockRenderLayer.CUTOUT_MIPPED);
        }
    }
}