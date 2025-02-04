package keqing.gtqtcore.integration.append.tile;


import appeng.api.parts.IPartModel;
import appeng.core.sync.GuiBridge;
import appeng.items.parts.PartModels;
import appeng.parts.PartModel;
import appeng.parts.reporting.AbstractPartEncoder;
import appeng.tile.inventory.AppEngInternalInventory;
import javax.annotation.Nonnull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class PartExpandedProcessingPatternTerminal extends AbstractPartEncoder {
    @PartModels
    public static final ResourceLocation MODEL_OFF = new ResourceLocation("appliedenergistics2", "part/expanded_processing_pattern_terminal_off");
    @PartModels
    public static final ResourceLocation MODEL_ON = new ResourceLocation("appliedenergistics2", "part/expanded_processing_pattern_terminal_on");
    public static final IPartModel MODELS_OFF;
    public static final IPartModel MODELS_ON;
    public static final IPartModel MODELS_HAS_CHANNEL;

    public PartExpandedProcessingPatternTerminal(ItemStack is) {
        super(is);
        this.crafting = new AppEngInternalInventory(this, 20);
        this.output = new AppEngInternalInventory(this, 6);
        this.pattern = new AppEngInternalInventory(this, 2);
        this.craftingMode = false;
    }

    public boolean isCraftingRecipe() {
        return false;
    }

    public void setCraftingRecipe(boolean craftingMode) {
    }

    public GuiBridge getGuiBridge() {
        return GuiBridge.GUI_EXPANDED_PROCESSING_PATTERN_TERMINAL;
    }

    @Nonnull
    public IPartModel getStaticModels() {
        return this.selectModel(MODELS_OFF, MODELS_ON, MODELS_HAS_CHANNEL);
    }

    static {
        MODELS_OFF = new PartModel(new ResourceLocation[]{MODEL_BASE, MODEL_OFF, MODEL_STATUS_OFF});
        MODELS_ON = new PartModel(new ResourceLocation[]{MODEL_BASE, MODEL_ON, MODEL_STATUS_ON});
        MODELS_HAS_CHANNEL = new PartModel(new ResourceLocation[]{MODEL_BASE, MODEL_ON, MODEL_STATUS_HAS_CHANNEL});
    }
}
