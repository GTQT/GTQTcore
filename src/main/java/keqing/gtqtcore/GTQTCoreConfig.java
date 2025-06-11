package keqing.gtqtcore;

import net.minecraftforge.common.config.Config;

import static keqing.gtqtcore.GTQTCore.MODID;

@Config(modid = MODID)
public class GTQTCoreConfig {

    @Config.Comment("Config options for GTQTCore")
    public static MachineSwitch MachineSwitch = new MachineSwitch();
    public static WorldGenSwitch WorldGenSwitch = new WorldGenSwitch();
    public static OBJRenderSwitch OBJRenderSwitch = new OBJRenderSwitch();
    public static DifficultySwitch difficultySwitch = new DifficultySwitch();
    public static DebugSwitch debugSwitch = new DebugSwitch();
    public static class DebugSwitch {
        @Config.Comment({ "Debug Switch" })
        public boolean debugSwitch = false;

        @Config.Comment({ "Print Cable Info In Logs" })
        public boolean cableDebug = false;

        @Config.Comment({ "Print Pipe Info In Logs" })
        public boolean pipeDebug = false;

        @Config.Comment({ "Print Rotor Info In Logs" })
        public boolean rotorDebug = false;

        @Config.Comment({ "Print Ore Info In Logs" })
        public boolean oreDebug = false;

        @Config.Comment({ "Crash to see Log" })
        public boolean crash = false;
    }
    public static class DifficultySwitch {
        @Config.Comment({ "Allows HotIngot to be cooled by throwing it into water.", "Default: true" })
        public boolean easyCooling = true;

        @Config.Comment({ "In cauldron, allow only 3 items to be cleaned in 1B.", "Default: false" })
        public boolean hardCleaning = false;

        @Config.Comment({ "Allows cleaning by water instead of a cauldron.", "Default: true" })
        public boolean easyCleaning = true;

        @Config.Comment({ "时间之瓶最大时间储量（单位: 小时）", "Default: 8" })
        public int TimeBottleStoreMaxHour = 8;
    }
    public static class WorldGenSwitch {
        @Config.Comment("Chance of generating abandoned base in chunk = 1 / 100 * THIS_VALUE. 0 disables abandoned base generation. Default: 5 ,0 to close"  )
        public int abandonedBaseRarity = 10;

    }
    public static class OBJRenderSwitch {
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

        @Config.Comment("天基折射棱镜中央控制系统OBJ模型渲染开启")
        @Config.RequiresMcRestart
        @Config.Name("Enable obj Model SBPRC")
        public boolean EnableObjSBPRC = true;

        @Config.Comment("风力发电机OBJ模型渲染开启")
        @Config.RequiresMcRestart
        @Config.Name("Enable obj Model Wind")
        public boolean EnableObjSWind = true;
    }
    public static class MachineSwitch {
        @Config.Comment("终局设备开关，包括尼戴，神锻。注意，严重影响加载时间！！！")
        @Config.RequiresMcRestart
        @Config.Name("End game machine switch")
        public boolean EndGameSwitch = false;

        @Config.Comment("后期设备开关，包括鸿蒙之眼，等离子锻炉，移相器（暂未实装实际功能）")
        @Config.RequiresMcRestart
        @Config.Name("Last game machine switch")
        public boolean LastSwitch = false;

        @Config.Name("Energy Infuser Settings")
        public EnergyInfuserSettings energyInfuserSettings = new EnergyInfuserSettings();

        public static class EnergyInfuserSettings {

            @Config.Name("Max repaired durability per operation")
            public int maxRepairedDamagePerOperation = 1000;

            @Config.Name("Used EU per durability point")
            public int usedEUPerDurability = 1000;

            @Config.Name("Used UU Matter per durability point")
            public int usedUUMatterPerDurability = 1;
        }
    }
}