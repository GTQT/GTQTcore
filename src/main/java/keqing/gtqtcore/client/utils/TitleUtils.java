package keqing.gtqtcore.client.utils;

import keqing.gtqtcore.GTQTCore;
import keqing.gtqtcore.api.utils.GTQTLog;
import net.minecraftforge.fml.common.API;
import org.lwjgl.opengl.Display;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class TitleUtils {
    public static final String DEFAULT_TITLE = "GregTech : Quantum Transition 格雷：量子跃迁 | ModPack Ver:" +GTQTCore.PACK+ " - GTQTCore Ver:"+GTQTCore.VERSION;
    public static String currentTitle = null;
    public static void setRandomTitle(String STATUE)
    {
        currentTitle = buildTitle(getrandom(),STATUE);
        Display.setTitle(currentTitle);
    }

    private static String getrandom() {
        Random rand = new Random();
        int randomNum = rand.nextInt(10);
        return switch (randomNum) {
            case 1 -> "风不来，树不动;船不摇，水不浑。";
            case 2 -> "人生两件事：走路与识人。道曲难行，路直景稀。";
            case 3 -> "供人以鱼，只解一餐;授人以渔，终身受用。";
            case 4 -> "烈阳抚，残尘雨。陌离去，饮一壶。论刀驽，落断瀑。剑情絮，水梦烛。问其意，意中寻。";
            case 5 -> "人经不住千言，树经不住千斧。早上好！";
            case 6 -> "随时光而去的是红颜和青丝，与日俱增的是希望和信心。";
            case 7 -> "莫将分付东邻子,回首长安佳丽地。";
            case 8 -> "残巻生前能伴我，锦衣死后又归谁？";
            case 9 -> "悄悄地你走了，正如你悄悄的来。";
            default -> "适度游戏，保持锻炼，珍爱身体，拒绝熬夜，别让生命戛然而止。";
        };
    }

    public static String buildTitle(String RANDOM,String STATUE) {
        if(STATUE!=null)return String.format("%s | 随机废话：%s | 加载状态：%s",DEFAULT_TITLE,RANDOM,STATUE);
        return String.format("%s | GTQT官方交流QQ群：494136307 | 随机废话； %s ",DEFAULT_TITLE,RANDOM);
    }


}