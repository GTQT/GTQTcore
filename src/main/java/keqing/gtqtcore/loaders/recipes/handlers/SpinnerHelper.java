package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.init.Items;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Polybenzimidazole;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.SPINNER_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.Polyetheretherketone;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.wrap;

public class SpinnerHelper {
    public static void init() {
        SPINNER_RECIPES.recipeBuilder()
                .input(Items.STRING)
                .output(wrap, Wool)
                .duration(200)
                .EUt(VA[ULV])
                .buildAndRegister();

        SPINNER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust,Asbestos,4)
                .output(wrap, Asbestos)
                .duration(200)
                .EUt(VA[LV])
                .buildAndRegister();

        registerWrap(Polycaprolactam,3);
        registerWrap(CarbonNanotube,6);

        registerWrap(Polyethylene,1);
        registerWrap(PolyvinylChloride,2);
        registerWrap(Epoxy,3);
        registerWrap(ReinforcedEpoxyResin,4);
        registerWrap(Polytetrafluoroethylene,5);
        registerWrap(Zylon,6);
        registerWrap(Polybenzimidazole,7);
        registerWrap(Polyetheretherketone,8);
        registerWrap(Kevlar,9);
        registerWrap(KaptonK,10);
        registerWrap(KaptonE,11);
        registerWrap(FullerenePolymerMatrix,12);
    }
    public static void registerWrap(Material material,int euTier) {
        SPINNER_RECIPES.recipeBuilder()
                .fluidInputs(material.getFluid(L * 4))
                .output(wrap, material)
                .duration(200)
                .EUt(VA[euTier])
                .buildAndRegister();
    }
}
