package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.stack.UnificationEntry;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.unification.GTQTMaterials.CubicBoronNitride;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Orichalcum;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Polyetheretherketone;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.REINFORCED_ROTOR_HOLDER;

public class ReinforcedRotorHolder {
    public static void init() {

        //  LV
        ModHandler.addShapedRecipe(true, "reinforced_rotor_holder.lv", REINFORCED_ROTOR_HOLDER[0].getStackForm(),
                "LFL", "GHG", "RMR",
                'H', HULL[LV].getStackForm(),
                'M', ELECTRIC_MOTOR_LV,
                'G', new UnificationEntry(gear, CobaltBrass),
                'F', new UnificationEntry(frameGt, Steel),
                'R', new UnificationEntry(rotor, Iron),
                'L', new UnificationEntry(stickLong, Steel));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt, Steel)
                .input(HULL[LV])
                .input(ELECTRIC_MOTOR_LV)
                .input(gear, CobaltBrass, 2)
                .input(rotor, Iron, 2)
                .input(stickLong, Steel, 2)
                .circuitMeta(7)
                .output(REINFORCED_ROTOR_HOLDER[0])
                .EUt(VA[LV])
                .duration(100)
                .buildAndRegister();

        //  MV
        ModHandler.addShapedRecipe(true, "reinforced_rotor_holder.mv", REINFORCED_ROTOR_HOLDER[1].getStackForm(),
                "LFL", "GHG", "RMR",
                'H', HULL[MV].getStackForm(),
                'M', ELECTRIC_MOTOR_MV,
                'G', new UnificationEntry(gear, VanadiumSteel),
                'F', new UnificationEntry(frameGt, Aluminium),
                'R', new UnificationEntry(rotor, Steel),
                'L', new UnificationEntry(stickLong, Aluminium));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt, Aluminium)
                .input(HULL[MV])
                .input(ELECTRIC_MOTOR_MV)
                .input(gear, VanadiumSteel, 2)
                .input(rotor, Steel, 2)
                .input(stickLong, Aluminium, 2)
                .circuitMeta(7)
                .output(REINFORCED_ROTOR_HOLDER[1])
                .EUt(VA[MV])
                .duration(150)
                .buildAndRegister();

        //  HV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, StainlessSteel)
                .input(HULL[HV])
                .input(ELECTRIC_MOTOR_HV)
                .input(gear, BlueSteel, 2)
                .input(rotor, Chrome, 2)
                .input(stickLong, StainlessSteel, 2)
                .circuitMeta(7)
                .output(REINFORCED_ROTOR_HOLDER[2])
                .EUt(VA[HV])
                .duration(200)
                .scannerResearch(b -> b
                        .researchStack(REINFORCED_ROTOR_HOLDER[1].getStackForm())
                        .EUt(VA[IV])
                        .duration(200))
                .buildAndRegister();

        //  EV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Titanium)
                .input(HULL[EV])
                .input(ELECTRIC_MOTOR_EV)
                .input(gear, Ultimet, 2)
                .input(rotor, Staballoy, 2)
                .input(stickLong, Titanium, 2)
                .circuitMeta(7)
                .output(REINFORCED_ROTOR_HOLDER[3])
                .EUt(VA[EV])
                .duration(250)
                .scannerResearch(b -> b
                        .researchStack(REINFORCED_ROTOR_HOLDER[2].getStackForm())
                        .EUt(VA[IV])
                        .duration(200))
                .buildAndRegister();

        //  IV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, TungstenSteel)
                .input(HULL[IV])
                .input(ELECTRIC_MOTOR_IV)
                .input(gear, TungstenCarbide, 2)
                .input(rotor, Inconel792, 2)
                .input(stickLong, TungstenSteel, 2)
                .circuitMeta(7)
                .output(REINFORCED_ROTOR_HOLDER[4])
                .EUt(VA[IV])
                .duration(300)
                .scannerResearch(b -> b
                        .researchStack(REINFORCED_ROTOR_HOLDER[3].getStackForm())
                        .EUt(VA[IV])
                        .duration(200))
                .buildAndRegister();

        //  LuV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, RhodiumPlatedPalladium)
                .input(HULL[LuV])
                .input(ELECTRIC_MOTOR_LuV)
                .input(gear, HSSE, 2)
                .input(rotor, MARM200CeSteel, 2)
                .input(stickLong, RhodiumPlatedPalladium, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 16))
                .fluidInputs(Lubricant.getFluid(4000))
                .output(REINFORCED_ROTOR_HOLDER[5])
                .EUt(VA[LuV])
                .duration(350)
                .scannerResearch(b -> b
                        .researchStack(REINFORCED_ROTOR_HOLDER[4].getStackForm())
                        .EUt(VA[IV])
                        .duration(200))
                .buildAndRegister();

        //  ZPM
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, NaquadahAlloy)
                .input(HULL[ZPM])
                .input(ELECTRIC_MOTOR_ZPM)
                .input(gear, NaquadahAlloy, 2)
                .input(rotor, IncoloyMA813, 2)
                .input(stickLong, NaquadahAlloy, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 16))
                .fluidInputs(Lubricant.getFluid(4000))
                .output(REINFORCED_ROTOR_HOLDER[6])
                .EUt(VA[ZPM])
                .duration(400)
                .stationResearch(b -> b
                        .researchStack(REINFORCED_ROTOR_HOLDER[5].getStackForm())
                        .EUt(VA[LuV])
                        .CWUt(256))
                .buildAndRegister();

        //  UV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Darmstadtium)
                .input(HULL[UV])
                .input(ELECTRIC_MOTOR_UV)
                .input(gear, Duranium, 2)
                .input(rotor, Cinobite, 2)
                .input(stickLong, Darmstadtium, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 16))
                .fluidInputs(Lubricant.getFluid(4000))
                .output(REINFORCED_ROTOR_HOLDER[7])
                .EUt(VA[UV])
                .duration(450)
                .stationResearch(b -> b
                        .researchStack(REINFORCED_ROTOR_HOLDER[6].getStackForm())
                        .EUt(VA[ZPM])
                        .CWUt(512))
                .buildAndRegister();

        //  UHV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Orichalcum)
                .input(HULL[UHV])
                .input(ELECTRIC_MOTOR_UHV)
                .input(gear, CubicBoronNitride, 2)
                .input(rotor, TitanSteel, 2)
                .input(stickLong, Orichalcum, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 16))
                .fluidInputs(Lubricant.getFluid(4000))
                .fluidInputs(Polyetheretherketone.getFluid(L * 4))
                .output(REINFORCED_ROTOR_HOLDER[8])
                .EUt(VA[UHV])
                .duration(500)
                .stationResearch(b -> b
                        .researchStack(REINFORCED_ROTOR_HOLDER[7].getStackForm())
                        .EUt(VA[UV])
                        .CWUt(1024))
                .buildAndRegister();


        //  UEV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Adamantium)
                .input(HULL[UEV])
                .input(ELECTRIC_MOTOR_UEV)
                .input(gear, BlackTitanium, 2)
                .input(rotor, Neutronium, 2)
                .input(stickLong, Adamantium, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 16))
                .fluidInputs(Lubricant.getFluid(4000))
                .fluidInputs(Kevlar.getFluid(L * 4))
                .output(REINFORCED_ROTOR_HOLDER[9])
                .EUt(VA[UEV])
                .duration(550)
                .stationResearch(b -> b
                        .researchStack(REINFORCED_ROTOR_HOLDER[8].getStackForm())
                        .EUt(VA[UHV])
                        .CWUt(256))
                .buildAndRegister();

        //  UIV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Infinity)
                .input(HULL[UIV])
                .input(ELECTRIC_MOTOR_UIV)
                .input(gear, BlackPlutonium, 2)
                .input(rotor, SuperheavyHAlloy, 2)
                .input(stickLong, Infinity, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 16))
                .fluidInputs(Lubricant.getFluid(4000))
                .fluidInputs(Zylon.getFluid(L * 4))
                .output(REINFORCED_ROTOR_HOLDER[10])
                .EUt(VA[UIV])
                .duration(600)
                .stationResearch(b -> b
                        .researchStack(REINFORCED_ROTOR_HOLDER[9].getStackForm())
                        .EUt(VA[UEV])
                        .CWUt(512))
                .buildAndRegister();
        /*
        //  UXV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, CosmicNeutronium)
                .input(HULL[UXV])
                .input(ELECTRIC_MOTOR_UXV)
                .input(gear, Octiron, 2)
                .input(rotor, Arcanium, 2)
                .input(stickLong, CosmicNeutronium, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 16))
                .fluidInputs(Lubricant.getFluid(4000))
                .fluidInputs(FullerenePolymerMatrix.getFluid(L * 4))
                .output(REINFORCED_ROTOR_HOLDER[11])
                .EUt(VA[UXV])
                .duration(650)
                .stationResearch(b -> b
                        .researchStack(REINFORCED_ROTOR_HOLDER[10].getStackForm())
                        .EUt(VA[UIV])
                        .CWUt(1024))
                .buildAndRegister();

        //  OpV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Spacetime)
                .input(HULL[OpV])
                .input(ELECTRIC_MOTOR_OpV)
                .input(gear, Edenium, 2)
                .input(rotor, TranscendentMetal, 2)
                .input(stickLong, Spacetime, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 16))
                .fluidInputs(Lubricant.getFluid(4000))
                .fluidInputs(CosmicFabric.getFluid(L * 4))
                .output(REINFORCED_ROTOR_HOLDER[12])
                .EUt(VA[OpV])
                .duration(700)
                .stationResearch(b -> b
                        .researchStack(REINFORCED_ROTOR_HOLDER[11].getStackForm())
                        .EUt(VA[UXV])
                        .CWUt(2048))
                .buildAndRegister();

         */

    }
}
