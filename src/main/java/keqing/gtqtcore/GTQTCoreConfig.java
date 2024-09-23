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
        public boolean EndGameSwitch = false;

        @Config.Comment("后期设备开关，包括鸿蒙之眼，等离子锻炉，移相器（暂未实装实际功能）")
        @Config.RequiresMcRestart
        @Config.Name("Last game machine switch")
        public boolean LastSwitch = true;

        @Config.Comment("巨型设备开关，包括巨型高炉，巨型真空冷冻机（因为长得丑还没用，所以暂时关闭）")
        @Config.RequiresMcRestart
        @Config.Name("Huge Machine Switch")
        public boolean HugeSwitch = false;

        @Config.Comment("多线程设备NBT存储，由于转存NBT存在问题，所以关闭")
        @Config.RequiresMcRestart
        @Config.Name("Core Machine NBT Store Switch")
        public boolean CoreMachineNBTStoreSwitch = false;

        @Config.Comment("OBJ模型渲染开启")
        @Config.RequiresMcRestart
        @Config.Name("Enable obj Model")
        public boolean EnableObj = true;

        @Config.Comment("生物反应室OBJ模型渲染开启")
        @Config.RequiresMcRestart
        @Config.Name("Enable obj Model Biological Reaction")
        public boolean EnableObjBiologicalReaction = true;

        @Config.Comment("原始林场OBJ模型渲染开启")
        @Config.RequiresMcRestart
        @Config.Name("Enable obj Model Primitive Tree Farmer")
        public boolean EnableObjPrimitiveTreeFarmer = true;
    }

}