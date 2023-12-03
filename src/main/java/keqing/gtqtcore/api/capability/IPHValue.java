package keqing.gtqtcore.api.capability;

public interface IPHValue {
    double getCurrentPHValue();
    void changeCurrentPHValue(double ph_change);
    void changeCurrentPHValue(double ph_change, double ph_change_limit);
}
