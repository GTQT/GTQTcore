package keqing.gtqtcore.api;

import static net.minecraft.util.text.TextFormatting.*;

public class GCYSValues {
    static int SECOND=20;
    static int MINUTE=1200;
    /**
     * Earth's Atmospheric Pressure at Sea Level
     * Anything smaller than this counts as a Vacuum
     */
    public static final double EARTH_PRESSURE = 101_325;

    /**
     * Earth's Temperature in Kelvin
     */
    public static final int EARTH_TEMPERATURE = 298;

    /**
     * The main pressure thresholds
     */
    public static int getPressureChange(int tier,boolean isIncrease)
    {
        if(isIncrease)return (int) Math.pow(10, tier+3);
        return -(int) Math.pow(10, tier+3);
    }
    public static final double[] P = new double[]{
            1E-9,
            1E-7,
            1E-5,
            1E-3,
            1E-1,
            1E1,
            1E3,
            101_325,//1E5
            1E7,
            1E9,
            1E11,
            1E13,
            1E15,
            1E17,
            1E19
    };
    public static final double[] increaseP = new double[]{
            1E7,//ULV-LV
            1E9,//MV-HV
            1E11,//EV-IV
            1E13,//LuV-ZPM
            1E15,//UV-UHV
            1E17,//UHV-UEV
            1E19//UIV-MAX
    };
    public static final double[] decreaseP = new double[]{
            1E3,//ULV-LV
            1E1,//MV-HV
            1E-1,//EV-IV
            1E-3,//LuV-ZPM
            1E-5,//UV-UHV
            1E-7,//UHV-UEV
            1E-9//UIV-MAX
    };

    public static final double[] increaseDetailP = new double[]{
            1E6,
            1E7,
            1E8,
            1E9,
            1E10,
            1E11,
            1E12,
            1E13,
            1E14,
            1E15,
            1E16,
            1E17,
            1E18,
            1E19
    };

    public static final double[] decreaseDetailP = new double[]{
            1E4,
            1E3,
            1E2,
            1E1,
            1E0,
            1E-1,
            1E-2,
            1E-3,
            1E-4,
            1E-5,
            1E-6,
            1E-7,
            1E-8,
            1E-9,
    };

    public static final double[] increaseRecipesPressure = new double[]{
            8.0E5,
            8.1E6,
            8.2E7,
            8.3E8,
            8.4E9,
            8.5E10,
            8.6E11,
            8.7E12,
            8.8E13,
            8.9E14,
            9.0E15,
            9.1E16,
            9.2E17,
            9.3E18
    };
    public static final double[] decreaseRecipesPressure = new double[]{
            2.0E4,
            1.9E3,
            1.8E2,
            1.7E1,
            1.6E0,
            1.5E-1,
            1.4E-2,
            1.3E-3,
            1.2E-4,
            1.1E-5,
            1.0E-6,
            0.9E-7,
            0.8E-8,
            0.7E-9,
    };

    public static final int IVV = 0;
    public static final int CSV = 1;
    public static final int EHV = 2;
    public static final int UHV = 3;
    public static final int HV = 4;
    public static final int MV = 5;
    public static final int LV = 6;
    public static final int EAP = 7;
    public static final int LP = 8;
    public static final int MP = 9;
    public static final int HP = 10;
    public static final int UHP = 11;
    public static final int EDP = 12;
    public static final int WDP = 13;
    public static final int NSP = 14;

    /**
     * The short names of each pressure threshold
     */
    public static final String[] PN = new String[]{
            "IVV",
            "CSV",
            "EHV",
            "UHV",
            "HV",
            "MV",
            "LV",
            "EAP",
            "LP",
            "MP",
            "HP",
            "UHP",
            "EDP",
            "WDP",
            "NSP"
    };

    /**
     * The decorated names of each pressure threshold
     */
    public static final String[] PNF = new String[]{
            BLUE + "IVV",
            DARK_GREEN + "CSV",
            DARK_RED + "EHV",
            WHITE + "UHV",
            DARK_BLUE + "HV",
            GOLD + "MV",
            GRAY + "LV",
            DARK_GRAY + "EAP",
            AQUA + "LP",
            DARK_PURPLE + "MP",
            LIGHT_PURPLE + "HP",
            DARK_AQUA + "UHP",
            GREEN + "EDP",
            YELLOW + "WDP",
            RED + "NSP"
    };

    /**
     * The full names of each pressure threshold
     */
    public static final String[] PRESSURE_NAMES = new String[]{
            "Intergalactic Void Vacuum",
            "Close-Space Vacuum",
            "Extremely High Vacuum",
            "Ultra High Vacuum",
            "High Vacuum",
            "Medium Vacuum",
            "Low Vacuum",
            "Earth Atmospheric Pressure",
            "Low Pressure",
            "Medium Pressure",
            "High Pressure",
            "Ultra High Pressure",
            "Electron Degeneracy Pressure",
            "White Dwarf Pressure",
            "Neutron Star Pressure"
    };
}
