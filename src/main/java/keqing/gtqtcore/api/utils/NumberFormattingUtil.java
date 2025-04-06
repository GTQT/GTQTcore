package keqing.gtqtcore.api.utils;

import gregtech.api.util.TextFormattingUtil;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class NumberFormattingUtil {

    private static final NavigableMap<Double, String> suffixesByPower = new TreeMap<>();

    static {
        suffixesByPower.put(0.000_000_000_000_000_000_000_000_000_001D, "q");
        suffixesByPower.put(0.000_000_000_000_000_000_000_000_001D, "r");
        suffixesByPower.put(0.000_000_000_000_000_000_000_001D, "y");
        suffixesByPower.put(0.000_000_000_000_000_000_001D, "z");
        suffixesByPower.put(0.000_000_000_000_000_001D, "a");
        suffixesByPower.put(0.000_000_000_000_001D, "f");
        suffixesByPower.put(0.000_000_000_001D, "p");
        suffixesByPower.put(0.000_000_001D, "n");
        suffixesByPower.put(0.000_001D, "u");
        suffixesByPower.put(0.001D, "m");
        suffixesByPower.put(1.0D, "");
        suffixesByPower.put(1_000D, "k");
        suffixesByPower.put(1_000_000D, "M");
        suffixesByPower.put(1_000_000_000D, "G");
        suffixesByPower.put(1_000_000_000_000D, "T");
        suffixesByPower.put(1_000_000_000_000_000D, "P");
        suffixesByPower.put(1_000_000_000_000_000_000D, "E");
        suffixesByPower.put(1_000_000_000_000_000_000_000D, "Z");
        suffixesByPower.put(1_000_000_000_000_000_000_000_000D, "Y");
        suffixesByPower.put(1_000_000_000_000_000_000_000_000_000D, "R");
        suffixesByPower.put(1_000_000_000_000_000_000_000_000_000_000D, "Q");
    }

    @Nonnull
    public static String formatDoubleToCompactString(double value, int precision) {
        //Double.MIN_VALUE == -Double.MIN_VALUE so we need an adjustment here
        if (value == Double.MIN_VALUE) return formatDoubleToCompactString(Double.MIN_VALUE + 1, precision);

        Map.Entry<Double, String> e = suffixesByPower.floorEntry(value);
        double divideBy = e.getKey();
        String suffix = e.getValue();

        double truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10D) != (truncated / 10);
        return hasDecimal ? TextFormattingUtil.formatNumbers(truncated / 10D) + suffix : TextFormattingUtil.formatNumbers(truncated / 10) + suffix;
    }

    @Nonnull
    public static String formatDoubleToCompactString(double value) {
        return formatDoubleToCompactString(value, 3);
    }

    public static int[] scaleDoublesToInts(double value1, double value2) {
        // 计算放大倍率
        int scaleFactor = calculateScaleFactor(value1, value2);
        // 放大并转换为 int
        int scaledValue1 = (int) (value1 * scaleFactor);
        int scaledValue2 = (int) (value2 * scaleFactor);
        return new int[]{scaledValue1, scaledValue2};
    }

    private static int calculateScaleFactor(double value1, double value2) {
        // 获取两个值的小数位数
        int decimalPlaces1 = getDecimalPlaces(value1);
        int decimalPlaces2 = getDecimalPlaces(value2);
        // 取较大的小数位数作为放大倍率的依据
        int maxDecimalPlaces = Math.max(decimalPlaces1, decimalPlaces2);
        // 计算放大倍率（10^maxDecimalPlaces）
        return (int) Math.pow(10, maxDecimalPlaces);
    }

    private static int getDecimalPlaces(double value) {
        String str = Double.toString(value);
        int decimalIndex = str.indexOf('.');
        if (decimalIndex == -1) {
            return 0; // 没有小数部分
        }
        return str.length() - decimalIndex - 1; // 计算小数位数
    }
}