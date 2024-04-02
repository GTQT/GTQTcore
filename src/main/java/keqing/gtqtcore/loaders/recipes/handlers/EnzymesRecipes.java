package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.unification.material.Material;
import net.minecraft.item.Item;

import static gregtech.api.GTValues.MV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtechfoodoption.GTFOMaterialHandler.Stearin;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ENZYMES_REACTION_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class EnzymesRecipes {
    public static void init() {
        enzymesmix(Enzymesaa,Iron,101,1);
        enzymesmix(Enzymesab,Gold,102,1);
        enzymesmix(Enzymesac,Mercury,103,1);
        enzymesmix(Enzymesad,Titanium,104,1);

        enzymesmix(Enzymesba,Polyethylene,201,2);
        enzymesmix(Enzymesbb,Oxygen,202,2);
        enzymesmix(Enzymesbc,Hydrogen,203,2);
        enzymesmix(Enzymesbd,Benzene,204,2);

        enzymesmix(Enzymesca,Stearin,301,3);
        enzymesmix(Enzymescb,Biomass,302,3);
        enzymesmix(Enzymescc,Methane,303,3);

        enzymesmix(Enzymesda,Brominatedepoxyresins,401,4);

    }
    public static void enzymesmix(Material material1,Material material2, int rate, int tier)
    {
        ENZYMES_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(material2.getFluid(1000*tier))
                .fluidInputs(EnrichedBacterialSludge.getFluid(100*tier))
                .fluidOutputs(material1.getFluid(10))
                .EUt(VA[MV+tier])
                .duration(120*tier)
                .rate(rate)
                .buildAndRegister();

        ENZYMES_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(material2.getFluid(1000*tier))
                .fluidInputs(RawGrowthMedium.getFluid(25*tier))
                .fluidOutputs(material1.getFluid(10))
                .EUt(VA[MV+tier])
                .duration(120*tier)
                .rate(rate)
                .buildAndRegister();
    }
}
