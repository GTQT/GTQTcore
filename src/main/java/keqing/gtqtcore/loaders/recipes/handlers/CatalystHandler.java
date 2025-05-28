package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.loaders.recipes.chain.RubbersChain;
import net.minecraftforge.fluids.FluidStack;

import static gregtechfoodoption.GTFOMaterialHandler.Blood;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.BetaPinene;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Polyetheretherketone;

import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.swarm;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.wrap;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;

public class CatalystHandler {
    public static void init()
    {
        catalystBed();
        catalystFrame();
        catalystBase();
    }

    private static void catalystBase() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate,Steel,8)
                .input(wireFine,Copper,4)
                .input(screw,Tin,6)
                .output(CATALYST_BASE)
                .duration(6000)
                .EUt(VA[LV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(CATALYST_BASE)
                .input(dust, Infinity, 1)
                .input(dust, Naquadria, 10)
                .output(CATALYST_INFINITY_MUTATION)
                .duration(100)
                .EUt(VA[UHV])
                .buildAndRegister();

        //  Green Metal Catalyst
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(24)
                .input(CATALYST_BASE, 10)
                .input(dust, Aluminium, 4)
                .input(dust, Silver, 4)
                .output(CATALYST_GREEN_METAL, 10)
                .EUt(VA[LV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  Red Metal Catalyst
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(25)
                .input(CATALYST_BASE, 10)
                .input(dust, Iron, 2)
                .input(dust, Copper, 2)
                .output(CATALYST_RED_METAL, 10)
                .EUt(VA[LV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  Yellow Metal Catalyst
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(22)
                .input(CATALYST_BASE, 10)
                .input(dust, Tungsten, 4)
                .input(dust, Nickel, 4)
                .output(CATALYST_YELLOW_METAL, 10)
                .EUt(VA[EV])
                .duration(MINUTE)
                .buildAndRegister();

        //  Blue Metal Catalyst
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(18)
                .input(CATALYST_BASE, 10)
                .input(dust, Cobalt, 3)
                .input(dust, Titanium, 3)
                .output(CATALYST_BLUE_METAL, 10)
                .EUt(VA[HV])
                .duration(40 * SECOND)
                .buildAndRegister();

        //  Orange Metal Catalyst
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(28)
                .input(CATALYST_BASE, 10)
                .input(dust, Vanadium, 5)
                .input(dust, Palladium, 5)
                .output(CATALYST_ORANGE_METAL, 10)
                .EUt(VA[HV])
                .duration(40 * SECOND)
                .buildAndRegister();

        //  Purple Metal Catalyst
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(16)
                .input(CATALYST_BASE, 10)
                .input(dust, Iridium, 6)
                .input(dust, Ruthenium, 6)
                .output(CATALYST_PURPLE_METAL, 10)
                .EUt(VA[IV])
                .duration(2 * MINUTE)
                .buildAndRegister();

        //  Brown Metal Catalyst
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(14)
                .input(CATALYST_BASE, 10)
                .input(dust, Nickel, 4)
                .input(dust, Aluminium, 4)
                .output(CATALYST_BROWN_METAL, 10)
                .EUt(VA[LV])
                .duration(15 * SECOND)
                .buildAndRegister();

        //  Pink Metal Catalyst
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(12)
                .input(CATALYST_BASE, 10)
                .input(dust, Platinum, 4)
                .input(dust, Rhodium, 4)
                .output(CATALYST_PINK_METAL, 10)
                .EUt(VA[EV])
                .duration(MINUTE / 2)
                .buildAndRegister();

        //  Formaldehyde Catalyst
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(13)
                .input(CATALYST_BASE, 4)
                .input(dust, Iron, 8)
                .input(dust, Vanadium)
                .output(CATALYST_FORMALDEHYDE, 4)
                .EUt(VHA[HV])
                .duration(MINUTE / 2)
                .buildAndRegister();

        //  Solid Acid Catalyst
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(14)
                .input(CATALYST_BASE, 5)
                .input(dust, Lapis, 2)
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .output(CATALYST_SOLID_ACID, 5)
                .EUt(VA[EV])
                .duration(MINUTE / 2)
                .buildAndRegister();

        //CATALYST_PLATINUM_GROUP
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(10)
                .input(CATALYST_BASE, 5)
                .input(dust, RhodiumPlatedPalladium, 64)
                .input(dust, Osmiridium, 64)
                .input(swarm, Carbon, 64)
                .fluidInputs(Hypogen.getFluid(360))
                .output(CATALYST_PLATINUM_GROUP, 1)
                .EUt(VA[UEV])
                .duration(MINUTE)
                .buildAndRegister();

        //CATALYST_PLASTIC_POLYMER
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(10)
                .input(CATALYST_BASE, 1)
                .input(dust, Polytetrafluoroethylene, 64)
                .input(dust, Polyetheretherketone, 64)
                .input(swarm, Carbon, 64)
                .fluidInputs(Hypogen.getFluid(360))
                .output(CATALYST_PLASTIC_POLYMER, 1)
                .EUt(VA[UEV])
                .duration(MINUTE)
                .buildAndRegister();

        //CATALYST_RUBBER_POLYMER
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(10)
                .input(CATALYST_BASE, 1)
                .input(dust, SiliconeRubber, 64)
                .input(dust, StyreneButadieneRubber, 64)
                .input(swarm, Carbon, 64)
                .fluidInputs(Hypogen.getFluid(360))
                .output(CATALYST_RUBBER_POLYMER, 1)
                .EUt(VA[UEV])
                .duration(MINUTE)
                .buildAndRegister();

        //CATALYST_RARE_EARTH
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(10)
                .input(CATALYST_BASE, 1)
                .input(dust, Samarium, 64)
                .input(dust, Gadolinium, 64)
                .input(swarm, Silver, 1)
                .fluidInputs(Hypogen.getFluid(9216))
                .output(CATALYST_RARE_EARTH, 1)
                .EUt(VA[UEV])
                .duration(MINUTE)
                .buildAndRegister();

        //CATALYST_NAQUADAH
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(10)
                .input(CATALYST_BASE, 1)
                .input(dust, Naquadah, 64)
                .input(dust, Adamantium, 64)
                .input(swarm, Silver, 1)
                .fluidInputs(Hypogen.getFluid(9216))
                .output(CATALYST_RARE_EARTH, 1)
                .EUt(VA[UEV])
                .duration(MINUTE)
                .buildAndRegister();

        //CATALYST_RAW_INTELLIGENCE
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(10)
                .input(CATALYST_BASE, 1)
                .input(STEM_CELLS, 64)
                .input(swarm, Gold, 1)
                .fluidInputs(Spacetime.getFluid(9216))
                .output(CATALYST_RAW_INTELLIGENCE, 1)
                .EUt(VA[UEV])
                .duration(MINUTE)
                .buildAndRegister();
    }

    private static void catalystFrame() {

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .duration(600).EUt(VA[EV])
                .input(frameGt, StainlessSteel, 8)
                .input(OrePrefix.foil, Titanium, 4)
                .input(wireFine, Platinum, 2)
                .fluidInputs(Biomass.getFluid(200))
                .fluidInputs(Zylon.getFluid(144))
                .fluidInputs(StyreneButadieneRubber.getFluid(144))
                .output(CATALYST_FRAMEWORK_BLANK,8)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //催化剂框架 使用激光模板
        PRECISION_SPINNING.recipeBuilder()
                .duration(400).EUt(VA[EV]).CWUt(256)
                .input(CATALYST_FRAMEWORK_BLANK)
                .input(OrePrefix.foil, Platinum, 4)
                .input(circuit, MarkerMaterials.Tier.HV, 1)
                .fluidInputs(Enzymesbc.getFluid(200))
                .fluidInputs(Zylon.getFluid(144))
                .fluidInputs(StyreneButadieneRubber.getFluid(144))
                .output(CATALYST_FRAMEWORK_I)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .duration(400).EUt(VA[EV]).CWUt(256)
                .input(CATALYST_FRAMEWORK_BLANK)
                .input(OrePrefix.foil, Palladium, 4)
                .input(circuit, MarkerMaterials.Tier.HV, 1)
                .fluidInputs(Enzymesbb.getFluid(200))
                .fluidInputs(Zylon.getFluid(144))
                .fluidInputs(StyreneButadieneRubber.getFluid(144))
                .output(CATALYST_FRAMEWORK_II)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .duration(400).EUt(VA[EV]).CWUt(256)
                .input(CATALYST_FRAMEWORK_BLANK)
                .input(OrePrefix.foil, NiobiumTitanium, 4)
                .input(circuit, MarkerMaterials.Tier.HV, 1)
                .fluidInputs(Enzymesba.getFluid(200))
                .fluidInputs(Zylon.getFluid(144))
                .fluidInputs(StyreneButadieneRubber.getFluid(144))
                .output(CATALYST_FRAMEWORK_III)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .duration(400).EUt(VA[EV]).CWUt(256)
                .input(CATALYST_FRAMEWORK_BLANK)
                .input(OrePrefix.foil, NanometerBariumTitanate, 4)
                .input(circuit, MarkerMaterials.Tier.HV, 1)
                .fluidInputs(Enzymesca.getFluid(200))
                .fluidInputs(Zylon.getFluid(144))
                .fluidInputs(Mutagen.getFluid(144))
                .output(CATALYST_FRAMEWORK_IV)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .duration(400).EUt(VA[EV]).CWUt(256)
                .input(CATALYST_FRAMEWORK_BLANK)
                .input(OrePrefix.foil, Osmium, 4)
                .input(circuit, MarkerMaterials.Tier.HV, 1)
                .fluidInputs(Enzymesbd.getFluid(200))
                .fluidInputs(Zylon.getFluid(144))
                .fluidInputs(Mutagen.getFluid(144))
                .output(CATALYST_FRAMEWORK_V)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .duration(400).EUt(VA[EV]).CWUt(256)
                .input(CATALYST_FRAMEWORK_BLANK)
                .input(OrePrefix.foil, Iridium, 4)
                .input(circuit, MarkerMaterials.Tier.HV, 1)
                .fluidInputs(Enzymesab.getFluid(200))
                .fluidInputs(Zylon.getFluid(144))
                .fluidInputs(Mutagen.getFluid(144))
                .output(CATALYST_FRAMEWORK_VI)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }
    private static void catalystBed() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt, Steel, 1)
                .input(wrap, Polyethylene, 4)
                .output(CATALYST_BED_BASE)
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        // 沸石催化剂
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(CATALYST_BED_BASE)
                .input(stick, Steel, 4)
                .input(ring, Copper, 4)
                .input(dust, Zeolite, 16)
                .output(CATALYST_GAS)
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        // 氧化钴催化剂
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(CATALYST_BED_BASE)
                .input(stick, Steel, 4)
                .input(ring, Cobalt, 4)
                .input(dust, CobaltOxide, 16)
                .output(CATALYST_CO)
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        // 铁基催化剂
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(CATALYST_BED_BASE)
                .input(stick, Steel, 4)
                .input(ring, Copper, 4)
                .input(dust, Iron, 16)
                .output(CATALYST_CU)
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        // 铜锌催化剂
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(CATALYST_BED_BASE)
                .input(stick, Steel, 4)
                .input(ring, Copper, 4)
                .input(dust, ZincOxide, 16)
                .output(CATALYST_ZN)
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        // 哈伯催化剂
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(CATALYST_BED_BASE)
                .input(stick, StainlessSteel, 4)
                .input(ring, Steel, 4)
                .input(dust, Chrome, 16)
                .output(CATALYST_CR)
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        // 铬基催化剂
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(CATALYST_BED_BASE)
                .input(stick, Aluminium, 4)
                .input(ring, Steel, 4)
                .input(dust, Chrome, 16)
                .output(CATALYST_NB)
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        // 齐格勒催化剂
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(CATALYST_BED_BASE)
                .input(stick, Steel, 4)
                .input(ring, Copper, 4)
                .input(dust, Zirconium, 16)
                .output(CATALYST_ZR)
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        // 白云石催化剂
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(CATALYST_BED_BASE)
                .input(stick, Steel, 4)
                .input(ring, Copper, 4)
                .input(dust, Dolomite, 16)
                .output(CATALYST_RH)
                .EUt(180)
                .duration(120)
                .buildAndRegister();
    }
}
