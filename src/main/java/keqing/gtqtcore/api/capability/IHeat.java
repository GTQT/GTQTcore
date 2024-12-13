package keqing.gtqtcore.api.capability;

public interface IHeat {
    double getHeat();
    double setHeat(int temp);
    void balanceHeat(int outsideTemp,int tick);
}
