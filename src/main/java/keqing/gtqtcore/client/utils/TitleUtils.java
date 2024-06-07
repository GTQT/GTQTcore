package keqing.gtqtcore.client.utils;

import keqing.gtqtcore.GTQTCore;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.*;


import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class TitleUtils {
    public static final String DEFAULT_TITLE = "GregTech : Quantum Transition 格雷：量子跃迁 | ModPack Ver:" +GTQTCore.PACK+ " - GTQTCore Ver:"+GTQTCore.VERSION;
    public static String currentTitle = null;
    public static String TEST = "本整合包当前处于 内测 状态，不保证游戏完整性，仅供体验！";
    public static void checkTitleState() {
        if (currentTitle == null) {
            return;
        }
    }
    public static void setRandomTitle(String STATUE)
    {
        currentTitle = buildTitle(getrandom(),STATUE);
        Display.setTitle(currentTitle);
    }
    /**
     * 设置一言随机标题，可以在其他线程使用。
     *
     * @param state 当前状态
     */
    public static void setRandomTitleSync(String state) {
        currentTitle = buildTitle(getrandom(),state);
    }

    /**
     * 设置一言随机标题，可以在其他线程使用。
     */
    public static void setRandomTitleSync() {
        currentTitle = buildTitle(getrandom(),null);
    }

    private static String getrandom() {
        Random rand = new Random();
        int randomNum = rand.nextInt(6);
        return switch (randomNum) {
            case 1 -> "知之愈明，则行之愈笃;行之愈笃，则知之益明。——朱熹(宋)";
            case 2 -> "为者常成，行者常至。——《晏子春秋》";
            case 3 -> "为一身谋则愚，而为天下则智。";
            case 4 -> "唯天下至诚为能化。——《论语》";
            case 5 -> "如果永远是晴天，土地也会布满裂痕。";
            default -> "宁死不背理，宁贫不堕志。";
        };
    }

    public static String buildTitle(String RANDOM,String STATUE) {
        if(STATUE!=null)return String.format("%s | 随机废话：%s | 加载状态：%s | %s",DEFAULT_TITLE,RANDOM,STATUE,TEST);
        return String.format("%s | 官方交流QQ群：1073091808 494136307 (QQ)| 随机废话； %s | %s",DEFAULT_TITLE,RANDOM,TEST);
    }
}