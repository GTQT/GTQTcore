package keqing.gtqtcore.api.capability;

public interface IHeat {
    int getTier();
    double getHeat();
    double setHeat(int temp);
    void balanceHeat(int outsideTemp,int tick);
}
