package keqing.gtqtcore.client.utils;

import gregtech.common.ConfigHolder;
import keqing.gtqtcore.GTQTCore;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.*;


import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class TitleUtils {
    public static final String DEFAULT_TITLE = "GregTech : Quantum Transition| ModPack Ver:" +GTQTCore.PACK+ " - GTQTCore Ver:"+GTQTCore.VERSION;
    public static String currentTitle = null;

    public static void setRandomTitle(String STATUE)
    {
        currentTitle = buildTitle(STATUE);
        Display.setTitle(currentTitle);
    }
    /**
     * 设置一言随机标题，可以在其他线程使用。
     *
     * @param state 当前状态
     */
    public static void setRandomTitleSync(String state) {
        currentTitle = buildTitle(state);
    }

    /**
     * 设置一言随机标题，可以在其他线程使用。
     */
    public static void setRandomTitleSync() {
        currentTitle = buildTitle(null);
    }



    public static String buildTitle(String STATUE) {
        if(STATUE!=null)return String.format("%s | Loading State：%s | %s",DEFAULT_TITLE,STATUE,getMode());
        return String.format("%s | %s",DEFAULT_TITLE,getMode());
    }


    private static String getMode() {
        //if(ConfigHolder.expertMode.expertModeEnable)return "难度模式：专家";
        //if(ConfigHolder.easyMode.easyModeEnable)return "难度模式：简单";
        return "难度模式：标准";
    }
}