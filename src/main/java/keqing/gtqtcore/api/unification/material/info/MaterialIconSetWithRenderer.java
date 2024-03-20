package keqing.gtqtcore.api.unification.material.info;

import gregtech.api.unification.material.info.MaterialIconSet;
import keqing.gtqtcore.api.items.IItemRenderer;
import keqing.gtqtcore.api.items.IItemRendererManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MaterialIconSetWithRenderer extends MaterialIconSet implements IItemRenderer, IItemRendererManager {

    private IItemRendererManager rendererManager;

    public MaterialIconSetWithRenderer(@Nonnull String name,
                                       IItemRendererManager rendererManager) {
        super(name);
        this.rendererManager = rendererManager;
    }

    public MaterialIconSetWithRenderer(@Nonnull String name,
                                       @Nonnull MaterialIconSet parentIconset,
                                       IItemRendererManager rendererManager) {
        super(name, parentIconset);
        this.rendererManager = rendererManager;
    }

    public MaterialIconSetWithRenderer(@Nonnull String name,
                                       @Nullable MaterialIconSet parentIconset,
                                       boolean isRootIconset,
                                       IItemRendererManager rendererManager) {
        super(name, parentIconset, isRootIconset);
        this.rendererManager = rendererManager;
    }

    @Override
    public IItemRendererManager getRendererManager() {
        return rendererManager;
    }

    @Override
    public void onRendererRegistry(ResourceLocation location) {
        rendererManager.onRendererRegistry(location);
    }
}