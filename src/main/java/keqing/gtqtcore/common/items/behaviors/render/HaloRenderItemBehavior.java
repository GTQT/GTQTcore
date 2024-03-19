package keqing.gtqtcore.common.items.behaviors.render;

import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.util.TransformUtils;
import keqing.gtqtcore.api.items.IHaloRenderBehavior;
import keqing.gtqtcore.client.renderer.HaloItemRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Supplier;


@SuppressWarnings("rawtypes")
public class HaloRenderItemBehavior implements IHaloRenderBehavior {

    private final int haloSize;
    private final int haloColour;
    private final Supplier supplier;
    private final boolean drawPulse;

    public HaloRenderItemBehavior(int haloSize,
                                  int haloColour,
                                  Supplier supplier,
                                  boolean drawPulse) {
        this.haloSize = haloSize;
        this.haloColour = haloColour;
        this.supplier = supplier;
        this.drawPulse = drawPulse;
    }

    @Override
    public boolean shouldDrawHalo() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public TextureAtlasSprite getHaloTexture() {
        return (TextureAtlasSprite) supplier.get();
    }

    @Override
    public int getHaloColour() {
        return haloColour;
    }

    @Override
    public int getHaloSize() {
        return haloSize;
    }

    @Override
    public boolean shouldDrawPulse() {
        return drawPulse;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onRendererRegistry(ResourceLocation location) {
        ModelRegistryHelper.register(new ModelResourceLocation(location, "inventory"), new HaloItemRenderer(TransformUtils.DEFAULT_ITEM, modelRegistry -> modelRegistry.getObject(new ModelResourceLocation(location, "inventory"))));
    }
}