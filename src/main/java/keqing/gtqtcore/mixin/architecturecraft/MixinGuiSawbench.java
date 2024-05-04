package keqing.gtqtcore.mixin.architecturecraft;

/*
 * Copyright (c) 2023 Nomifactory-CEu
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 2.1 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

import com.elytradev.architecture.client.gui.GuiSawbench;
import com.elytradev.architecture.legacy.base.BaseGui;
import net.minecraft.inventory.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Changes the Gui Textures and Colors Used.
 *
 * @author IntegerLimit
 */
@Mixin(value = GuiSawbench.class, remap = false)
public class MixinGuiSawbench extends BaseGui.Screen {

    @Unique
    private static final String ORIGINAL_GUI_PATH = "gui/gui_sawbench.png";

    @Unique
    private static final String NEW_GUI_PATH = "gui/new_gui_sawbench.png";

    @Unique
    private static final String ORIGINAL_GUI_BG_PATH = "gui/shapemenu_bg.png";

    @Unique
    private static final String NEW_GUI_BG_PATH = "gui/new_shapemenu_bg.png";

    @Unique
    private static final String ORIGINAL_GUI_ITEMS_PATH = "gui/shapemenu_items.png";

    @Unique
    private static final String NEW_GUI_ITEMS_PATH = "gui/new_shapemenu_items.png";

    /**
     * Default Ignored Constructor.
     */
    public MixinGuiSawbench(Container container, int width, int height) {
        super(container, width, height);
    }

    @Redirect(method = "drawBackgroundLayer",
            at = @At(value = "INVOKE",
                    target = "Lcom/elytradev/architecture/client/gui/GuiSawbench;bindTexture(Ljava/lang/String;II)V"))
    public void bindNewGuiTexture(GuiSawbench instance, String texture, int u, int v) {
        if (texture.equals(ORIGINAL_GUI_PATH)) {
            instance.bindTexture(NEW_GUI_PATH, u, v);
            return;
        }
        instance.bindTexture(texture, u, v);
    }

    @Redirect(method = "drawPageMenu",
            at = @At(value = "INVOKE", target = "Lcom/elytradev/architecture/client/gui/GuiSawbench;setColor(DDD)V"))
    public void setHighlightColor(GuiSawbench instance, double r, double g, double b) {
        instance.setColor(0.0, 0.98, 0.94);
    }

    @Inject(method = "drawPageMenu",
            at = @At(value = "INVOKE",
                    target = "Lcom/elytradev/architecture/client/gui/GuiSawbench;gRestore()V",
                    shift = At.Shift.AFTER))
    public void setNewTextColor(CallbackInfo ci) {
        gSave();
        setTextColor(0, 0, 0);
    }

    @Inject(method = "drawPageMenu", at = @At(value = "TAIL"))
    public void restorePrevious(CallbackInfo ci) {
        gRestore();
    }

    @Redirect(method = "drawShapeMenu",
            at = @At(value = "INVOKE",
                    target = "Lcom/elytradev/architecture/client/gui/GuiSawbench;bindTexture(Ljava/lang/String;II)V"))
    public void bindNewGuiShapeTexture(GuiSawbench instance, String texture, int u, int v) {
        if (texture.equals(ORIGINAL_GUI_BG_PATH)) {
            instance.bindTexture(NEW_GUI_BG_PATH, u, v);
        }
        if (texture.equals(ORIGINAL_GUI_ITEMS_PATH)) {
            instance.bindTexture(NEW_GUI_ITEMS_PATH, u, v);
            return;
        }
        instance.bindTexture(texture, u, v);
    }
}