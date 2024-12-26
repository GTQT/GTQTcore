package keqing.gtqtcore.api.capability;

public interface IPowerSupply {
    String getType();
    int getLevel();
    long getBatteryStore();

}
