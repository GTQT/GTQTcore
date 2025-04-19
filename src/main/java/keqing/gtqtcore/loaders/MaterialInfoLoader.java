package keqing.gtqtcore.loaders;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.common.metatileentities.MetaTileEntities;

import static gregtech.api.GTValues.M;
import static keqing.gtqtcore.api.unification.MaterialHelper.*;

public class MaterialInfoLoader {
    public static void loader() {

        for(int i=0;i<=9;i++) {
            OreDictUnifier.registerOre(MetaTileEntities.HULL[i].getStackForm(), new ItemMaterialInfo(
                    new MaterialStack(Plate[i], M * 8), // plate
                    new MaterialStack(SecondPlate[i], M * 2), // single cable
                    new MaterialStack(Cable[i], M), // single cable
                    new MaterialStack(Glue[i], M * 2))); // plate
        }
    }

}
