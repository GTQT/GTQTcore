package keqing.gtqtcore.loaders;

import static gregtech.api.unification.material.Materials.Duranium;
import static gtqt.api.util.MaterialHelper.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class AddHighTierMaterial {
    //#MaterialHelper
     public static void init() {
         UEV();
         UIV();
         UXV();
         OpV();
         MAX();
    }
    private static void MAX() {
        Plate.add(Magmatter);
    }
    private static void OpV() {
        Plate.add(Eternity);
    }

    private static void UXV() {
        Plate.add(Spacetime);
    }

    private static void UIV() {
        Plate.add(Infinity);
        Pipe.add(Lafium);
        Wire.add(Solarium);
        Cable.add(Solarium);
        Plastic.add(Polyetheretherketone);
    }

    private static void UEV() {
        Plate.add(Adamantium);
        Pipe.add(Duranium);
        Wire.add(PedotTMA);
        Cable.add(PedotTMA);
        Plastic.add(Polyetheretherketone);
    }
}
