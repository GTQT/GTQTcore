package keqing.gtqtcore.common.items.behaviors.render;

import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.util.TransformUtils;
import keqing.gtqtcore.api.items.ICosmicRenderBehavior;
import keqing.gtqtcore.client.render.CosmicItemRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.function.Supplier;


@SuppressWarnings("rawtypes")
public class CosmicRenderItemBehavior implements ICosmicRenderBehavior {

    private final Supplier supplier;
    private final int maskOpacity;

    public CosmicRenderItemBehavior(Supplier<TextureAtlasSprite> supplier,
                                    int maskOpacity) {
        this.supplier = supplier;
        this.maskOpacity = maskOpacity;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public TextureAtlasSprite getMaskTexture(ItemStack stack,
                                             @Nullable EntityLivingBase player) {
        return (TextureAtlasSprite) supplier.get();
    }

    @Override
    public float getMaskOpacity(ItemStack stack,
                                @Nullable EntityLivingBase player) {
        return maskOpacity;
    }

    @Override
    public void onRendererRegistry(ResourceLocation location) {
        ModelRegistryHelper.register(new ModelResourceLocation(location, "inventory"), new CosmicItemRenderer(TransformUtils.DEFAULT_ITEM, modelRegistry -> modelRegistry.getObject(new ModelResourceLocation(location, "inventory"))));
    }
}