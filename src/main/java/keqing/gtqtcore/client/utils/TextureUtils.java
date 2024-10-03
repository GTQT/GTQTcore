package keqing.gtqtcore.client.utils;

import keqing.gtqtcore.GTQTCore;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static keqing.gtqtcore.GTQTCore.MODID;

public class TextureUtils {
    private static InputStream getIcon(final int size) {
        return GTQTCore.class.getResourceAsStream("/assets/" + MODID + "/icons/" + "icon_" + size + ".png");
    }

    public static ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException
    {
        BufferedImage bufferedimage = ImageIO.read(imageStream);
        int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), (int[])null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);

        for (int i : aint)
        {
            bytebuffer.putInt(i << 8 | i >> 24 & 255);
        }

        bytebuffer.flip();
        return bytebuffer;
    }
    public static void setWindowIcon() {
        InputStream icon16 = null;
        InputStream icon32 = null;
        InputStream icon48 = null;
        InputStream icon128 = null;
        InputStream icon256 = null;

        try {

            icon16 = getIcon(16);
            icon32 = getIcon(32);
            icon48 = getIcon(48);
            icon128 = getIcon(128);
            icon256 = getIcon(256);

            Display.setIcon(new ByteBuffer[]{readImageToBuffer(icon16),readImageToBuffer(icon32),readImageToBuffer(icon48),readImageToBuffer(icon128), readImageToBuffer(icon256)});
        } catch (IOException | NullPointerException ignored) {
        } finally {
            IOUtils.closeQuietly(icon16);
            IOUtils.closeQuietly(icon32);
            IOUtils.closeQuietly(icon48);
            IOUtils.closeQuietly(icon128);
            IOUtils.closeQuietly(icon256);
        }
    }
}
