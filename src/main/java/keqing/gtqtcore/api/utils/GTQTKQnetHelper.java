package keqing.gtqtcore.api.utils;

public class GTQTKQnetHelper {
    public static String getInfo(int kind) {

        //0 普通
        if (kind == 0) return"无需设备";
        //1 物理-透射电镜 OK
        if (kind == 1) return"物理-大型电子透镜";
        //2 物理-粒子加速器 OK
        if (kind == 2) return"物理-粒子加速器";
        //3 物理-量子计算机
        if (kind == 3) return"物理-高性能计算阵列";

        //11 生物-细菌培养缸
        if (kind == 11) return"生物-细菌培养缸";
        //12 生物-基因诱变室
        if (kind == 12) return"生物-基因诱变室";

        //21 光学-质谱仪
        if (kind == 21) return"光学-质谱仪";

        //设备升级区 OK
        //30 设备-辅助计算机I
        if (kind == 30) return"设备-辅助计算机I";
        //31 设备-辅助计算机II
        if (kind == 31) return"设备-辅助计算机II";
        //32 设备-辅助计算机III null
        if (kind == 32) return"设备-辅助计算机III";

        //33 设备-辅助数据库
        if (kind == 33) return"设备-辅助数据库I";
        //34 设备-辅助数据库
        if (kind == 34) return"设备-辅助数据库II";
        //34 设备-辅助数据库
        if (kind == 35) return"设备-辅助数据库III";
        return "null";
    }
}
