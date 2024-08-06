package keqing.gtqtcore.client.utils;

import keqing.gtqtcore.GTQTCore;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.*;


import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class TitleUtils {
    public static final String DEFAULT_TITLE = "GregTech : Quantum Transition| ModPack Ver:" +GTQTCore.PACK+ " - GTQTCore Ver:"+GTQTCore.VERSION;
    public static String currentTitle = null;
    public static String TEST = "This integration package is currently in beta testing mode!";
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
            case 1->"The clearer you know, the more you practice; The more diligently one acts, the more enlightened one becomes—— Zhu Xi (Song Dynasty) ";
            case 2->"The doer always succeeds, the traveler always arrives—— Yanzi Chunqiu ";
            case 3->"If you plan for yourself, you will be foolish, but if you plan for the world, you will be wise. ";
            case 4->"Only sincerity in the world can be transformed—— The Analects ";
            case 5->" it's always sunny, the land will also be filled with cracks. ";
            default ->"Better die than betray reason, better be poor than lose heart. ";
        };
    }

    public static String buildTitle(String RANDOM,String STATUE) {
        if(STATUE!=null)return String.format("%s | Loading State：%s | %s",DEFAULT_TITLE,STATUE,TEST);
        return String.format("%s | QQ group：1073091808 494136307 | %s",DEFAULT_TITLE,TEST);
    }
}