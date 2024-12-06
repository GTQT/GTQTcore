package keqing.gtqtcore.api.capability;

public interface IBio {
    int getWaterAmount();
    int getBioAmount();
    boolean drainWaterAmount(int amount);
    boolean drainBioAmount(int amount);
}
