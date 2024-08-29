package keqing.gtqtcore;

import net.minecraftforge.common.config.Config;

import static keqing.gtqtcore.GTQTCore.MODID;

@Config(modid = MODID)
public class GTQTCoreConfig {

    @Config.Comment("Config options for GTQTCore")
    public static MachineSwitch MachineSwitch = new MachineSwitch();

    public static class MachineSwitch {
        @Config.Comment("终局设备开关，包括尼戴，神锻。注意，严重影响加载时间！！！")
        @Config.RequiresMcRestart
        @Config.Name("End game machine switch")
        public boolean EndGameSwitch = true;

        @Config.Comment("后期设备开关，包括鸿蒙之眼，等离子锻炉，移相器（暂未实装实际功能）")
        @Config.RequiresMcRestart
        @Config.Name("Last game machine switch")
        public boolean LastSwitch = true;

        @Config.Comment("巨型设备开关，包括巨型高炉，巨型真空冷冻机（因为长得丑还没用，所以暂时关闭）")
        @Config.RequiresMcRestart
        @Config.Name("Huge Machine Switch")
        public boolean HugeSwitch = false;

        @Config.Comment("UHV+ IO开关（ceu 2.9即将内置，所以关闭）")
        @Config.RequiresMcRestart
        @Config.Name("UHV+ IO Switch")
        public boolean UHVIOSwitch = false;

        @Config.Comment("OBJ模型渲染开启")
        @Config.RequiresMcRestart
        @Config.Name("Enable obj Model")
        public boolean EnableObj = false;

        @Config.Comment("生物反应室OBJ模型渲染开启")
        @Config.RequiresMcRestart
        @Config.Name("Enable obj Model BiologicalReaction")
        public boolean EnableObjBiologicalReaction = false;
    }

}