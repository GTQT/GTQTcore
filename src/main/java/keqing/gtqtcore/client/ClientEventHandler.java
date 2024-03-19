package keqing.gtqtcore.client;


import keqing.gtqtcore.client.shader.CosmicShaderHelper;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class ClientEventHandler {

    public static FloatBuffer cosmicUVs = BufferUtils.createFloatBuffer(4 * 10);

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            cosmicUVs = BufferUtils.createFloatBuffer(4 * GTQTTextures.COSMIC.length);
            TextureAtlasSprite icon;
            for (TextureAtlasSprite cosmicIcon : GTQTTextures.COSMIC) {
                icon = cosmicIcon;
                cosmicUVs.put(icon.getMinU());
                cosmicUVs.put(icon.getMinV());
                cosmicUVs.put(icon.getMaxU());
                cosmicUVs.put(icon.getMaxV());
            }
            cosmicUVs.flip();
        }
    }

    @SubscribeEvent
    public void onDrawScreenPre(GuiScreenEvent.DrawScreenEvent.Pre event) {
        CosmicShaderHelper.inventoryRender = true;
    }

    @SubscribeEvent
    public void drawScreenPost(GuiScreenEvent.DrawScreenEvent.Post event) {
        CosmicShaderHelper.inventoryRender = false;
    }
}
