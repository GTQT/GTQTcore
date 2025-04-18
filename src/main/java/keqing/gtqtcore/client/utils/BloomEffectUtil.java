package keqing.gtqtcore.client.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gregtech.client.shader.Shaders;
import gregtech.client.shader.postprocessing.BloomEffect;
import gregtech.client.utils.DepthTextureUtil;
import gregtech.client.utils.RenderUtil;
import gregtech.common.ConfigHolder;
import keqing.gtqtcore.client.render.ICustomRenderFast;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.lwjgl.opengl.GL11.GL_LINEAR;

@SideOnly(Side.CLIENT)
public class BloomEffectUtil {

    public static BlockRenderLayer BLOOM;
    private static Framebuffer BLOOM_FBO;
    private static Map<IBloomRenderFast, List<Consumer<BufferBuilder>>> RENDER_FAST;

    public static void init() {
        BLOOM = EnumHelper.addEnum(BlockRenderLayer.class, "BLOOM", new Class[]{String.class}, "Bloom");
        if (Loader.isModLoaded("nothirium")) {
            try {
                //Nothirium hard copies the BlockRenderLayer enum into a ChunkRenderPass enum. Add our BLOOM layer to that too.
                Class crp = Class.forName("meldexun.nothirium.api.renderer.chunk.ChunkRenderPass", false, Launch.classLoader);
                EnumHelper.addEnum(crp, "BLOOM", new Class[]{});
                Field all = FieldUtils.getField(crp, "ALL", false);
                FieldUtils.removeFinalModifier(all);
                FieldUtils.writeStaticField(all, crp.getEnumConstants());
            } catch (ClassNotFoundException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        RENDER_FAST = Maps.newHashMap();
    }

    public static void requestCustomBloom(IBloomRenderFast handler, Consumer<BufferBuilder> render) {
        RENDER_FAST.computeIfAbsent(handler, (x)->Lists.newLinkedList()).add(render);
    }

    public interface IBloomRenderFast extends ICustomRenderFast {

        /**
         * Custom Bloom Style.
         *
         * @return
         * 0 - Simple Gaussian Blur Bloom
         * <p>
         *  1 - Unity Bloom
         * </p>
         * <p>
         * 2 - Unreal Bloom
         * </p>
         */
        int customBloomStyle();

    }
}