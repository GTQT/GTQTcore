package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.metatileentity.multiblock.CleanroomType;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtechfoodoption.GTFOMaterialHandler.AceticAnhydride;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CHEMICAL_PLANT;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.*;

public class ZylonChain {
    public static void init() {
        //  Au-Pd-C Catalyst
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Gold)
                .input(dust, PalladiumOnCarbon)
                .output(dust, AuPdCCatalyst, 2)
                .EUt(VA[EV])
                .duration(850)
                .buildAndRegister();

        //  Dibromomethylbenzene (IV): Para-Xylene + Bromine + Oxygen -> Dibromomethylbenzene + Water
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(ParaXylene.getFluid(1000))
                .fluidInputs(Bromine.getFluid(2000))
                .fluidInputs(Oxygen.getFluid(2000))
                .fluidOutputs(Dibromomethylbenzene.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .EUt(VA[IV])
                .duration(430)
                .buildAndRegister();

        //  Dibromomethylbenzene (ZPM): Toluene + Bromine -> Dibromomethylbenzene
        CHEMICAL_PLANT.recipeBuilder()
                .fluidInputs(Toluene.getFluid(1000))
                .fluidInputs(Bromine.getFluid(2000))
                .fluidOutputs(Dibromomethylbenzene.getFluid(1000))
                .EUt(VA[ZPM])
                .duration(215)
                .buildAndRegister();

        //  Dibromomethylbenzene + Sulfuric Acid -> Terephthalaldehyde + Bromine + Hydrogen Sulfide + Hydrogen Peroxide
        CHEMICAL_PLANT.recipeBuilder()
                .fluidInputs(Dibromomethylbenzene.getFluid(1000))
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .output(dust, Terephthalaldehyde, 16)
                .fluidOutputs(Bromine.getFluid(2000))
                .fluidOutputs(HydrogenSulfide.getFluid(1000))
                .fluidOutputs(HydrogenPeroxide.getFluid(1000))
                .EUt(VA[LuV])
                .duration(120)
                .buildAndRegister();

        //  Isochloropropane
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Propene.getFluid(1000))
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(Isochloropropane.getFluid(1000))
                .EUt(VA[HV])
                .duration(110)
                .buildAndRegister();

        //  Dinitrodipropanyloxybenzene
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SodiumOxide, 3)
                .fluidInputs(Resorcinol.getFluid(1000))
                .fluidInputs(Isochloropropane.getFluid(1000))
                .fluidInputs(AceticAnhydride.getFluid(1000))
                .fluidInputs(NitricAcid.getFluid(2000))
                .fluidInputs(Propene.getFluid(1000))
                .output(dust, Salt, 2)
                .fluidOutputs(Dinitrodipropanyloxybenzene.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .fluidOutputs(AceticAcid.getFluid(1000))
                .fluidOutputs(SodiumAcetate.getFluid(1000))
                .EUt(VA[UV])
                .duration(500)
                .buildAndRegister();

        //  Prezylon
        CHEMICAL_PLANT.recipeBuilder()
                .input(dust, Terephthalaldehyde, 16)
                .notConsumable(dust, AuPdCCatalyst)
                .fluidInputs(Dinitrodipropanyloxybenzene.getFluid(1000))
                .output(dust, PreZylon)
                .fluidOutputs(Oxygen.getFluid(6000))
                .EUt(VA[UHV])
                .duration(50)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Prezylon -> Zylon + Propane
        BLAST_RECIPES.recipeBuilder()
                .input(dust, PreZylon)
                .output(dust, Zylon)
                .fluidOutputs(Propane.getFluid(2000))
                .EUt(VA[HV])
                .duration(16000)
                .blastFurnaceTemp(10000)
                .buildAndRegister();
    }
}
