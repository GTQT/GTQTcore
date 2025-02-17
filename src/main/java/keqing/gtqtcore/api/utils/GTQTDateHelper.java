package keqing.gtqtcore.api.utils;

public class GTQTDateHelper {
    public static String getTimeFromTicks(long ticks) {
        // 将 ticks 转换成秒
        long seconds = ticks / 20;

        // 计算分钟和小时
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;

        // 构造时间字符串
        return String.format("%d小时 %d分钟 %d秒", hours, minutes, remainingSeconds);
    }
    public static String getTimeFromSecond(int seconds) {
        // 计算分钟和小时
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int remainingSeconds = seconds % 60;

        // 构造时间字符串
        return String.format("%d小时 %d分钟 %d秒", hours, minutes, remainingSeconds);
    }
}
