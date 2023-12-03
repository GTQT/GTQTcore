package keqing.gtqtcore.client.renderer.handler;

/*
  Original class written by covers1624 for Avaritia.
 */

import codechicken.lib.colour.Colour;
import gregtech.api.items.metaitem.MetaItem;
import keqing.gtqtcore.api.items.metaitem.IRenderer;
import keqing.gtqtcore.api.items.metaitem.stats.renderer.IHaloRenderBehavior;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.model.IModelState;

import java.util.Random;

import static keqing.gtqtcore.client.renderer.handler.WrappedItemRenderer.renderModel;

public class HaloItemRenderer extends WrappedItemRenderer{

    private Random randy = new Random();

    public HaloItemRenderer(IModelState state, IBakedModel model) {
        super(state, model);
    }

    public HaloItemRenderer(IModelState state, IWrappedModelGetter getter) {
        super(state, getter);
    }

    @Override
    public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType transformType) {

        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();
        if (transformType == ItemCameraTransforms.TransformType.GUI) {
            MetaItem<?>.MetaValueItem valueItem = ((MetaItem<?>)stack.getItem()).getItem(stack);
            IHaloRenderBehavior hri = null;
            if (valueItem != null) {
                hri = (IHaloRenderBehavior) ((IRenderer)valueItem).getRendererManager();
            }

            if (hri != null) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

                GlStateManager.disableAlpha();

                if (hri.shouldDrawHalo()) {
                    Colour.glColourARGB(hri.getHaloColour());
                    TextureAtlasSprite sprite = hri.getHaloTexture();

                    double spread = hri.getHaloSize() / 16D;
                    double min = 0D - spread;
                    double max = 1D + spread;

                    float minU = sprite.getMinU();
                    float maxU = sprite.getMaxU();
                    float minV = sprite.getMinV();
                    float maxV = sprite.getMaxV();

                    buffer.begin(0x07, DefaultVertexFormats.POSITION_TEX);
                    buffer.pos(max, max, 0).tex(maxU, minV).endVertex();
                    buffer.pos(min, max, 0).tex(minU, minV).endVertex();
                    buffer.pos(min, min, 0).tex(minU, maxV).endVertex();
                    buffer.pos(max, min, 0).tex(maxU, maxV).endVertex();

                    tess.draw();
                }

                if (hri.shouldDrawPulse()) {
                    GlStateManager.pushMatrix();
                    double scale = randy.nextDouble() * 0.15 + 0.95;
                    double trans = (1 - scale) / 2;
                    GlStateManager.translate(trans, trans, 0);
                    GlStateManager.scale(scale, scale, 1.0001);

                    renderModel(wrapped, stack, 0.6F);

                    GlStateManager.popMatrix();
                }
                renderModel(wrapped, stack);

                GlStateManager.enableAlpha();
                GlStateManager.enableDepth();
                GlStateManager.enableRescaleNormal();

                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }else {
                renderModel(wrapped, stack);
            }

        }else {
            renderModel(wrapped, stack);
        }
    }
}