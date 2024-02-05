package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.PCB;
import static keqing.gtqtcore.api.unification.GCYSMaterials.KaptonE;

public class PCBline {

    public static void init() {
        BasicCircuitBoard();
        GoodCircuitBoard();
        PlasticCircuitBoard();
        AdvancedCircuitBoard();
    }
    //Polyethylene
    //PolyvinylChloride
    //Epoxy
    //ReinforcedEpoxyResin
    //Polytetrafluoroethylene
    //Polybenzimidazole
    //KaptonK
    //KaptonE
    private static void BasicCircuitBoard() {
        PCB.recipeBuilder()
                .input(plate, KaptonE)
                .input(foil, Gold, 42)
                .input(foil, Electrum, 42)
                .fluidInputs(SulfuricAcid.getFluid(1322))
                .fluidInputs(Iron3Chloride.getFluid(1322))
                .output(ADVANCED_CIRCUIT_BOARD, 64)
                .part(2)
                .EUt(368640)
                .duration(197)
                .buildAndRegister();
    }

    private static void GoodCircuitBoard() {
    }

    private static void PlasticCircuitBoard() {


    }

    private static void AdvancedCircuitBoard() {
        
    }
}