package keqing.gtqtcore.api.capability;

public interface ILaser {
    long Laser();
    long SetLaser();
    long MaxLaser();

    void setLaser(long laser);
    int tier();
    boolean isExport();

    int Voltage();
    int Amperage();

    void addVoltage(int i);
    void addAmperage(int i);
}
