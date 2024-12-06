package keqing.gtqtcore.api.utils;

public class EnzymesUtils {
    public static String getName(int n)
    {
        //普适矿处菌种 101
        //定向铂系菌种 102
        //普适魔性菌种 103
        //普适副产菌种 104
        //
        //工业合成菌种I 201
        //工业还原菌种 202
        //工业氧化菌种 203
        //工业催化菌种 204
        //
        //定向脂肪酶 301
        //普适发酵酶 302
        //定向发酵酶 303
        //
        //活性诱变酶 401
        switch (n) {
            case 101 -> {
                return "普适矿处菌种";
            }
            case 102 -> {
                return "定向铂系菌种";
            }
            case 103 -> {
                return "普适魔性菌种";
            }
            case 104 -> {
                return "普适副产菌种";
            }
            case 201 -> {
                return "工业合成菌种I";
            }
            case 202 -> {
                return "工业还原菌种";
            }
            case 203 -> {
                return "工业氧化菌种";
            }
            case 204 -> {
                return "工业催化菌种";
            }
            case 301 -> {
                return "定向脂肪酶";
            }
            case 302 -> {
                return "普适发酵酶";
            }
            case 303 -> {
                return "定向发酵酶";
            }
            case 401 -> {
                return "活性诱变酶";
            }
            default -> {
                return null;
            }
        }
    }
}
