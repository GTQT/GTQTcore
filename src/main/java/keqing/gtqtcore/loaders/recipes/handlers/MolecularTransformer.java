package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.ingredients.nbtmatch.NBTCondition;
import gregtech.api.recipes.ingredients.nbtmatch.NBTMatcher;
import gregtech.api.unification.material.MarkerMaterial;
import gregtech.api.unification.material.MarkerMaterials;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Osmium;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.common.blocks.MetaBlocks.OPTICAL_PIPES;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.items.MetaItems.ELECTRIC_MOTOR_EV;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gregtech.loaders.recipe.CraftingComponent.FIELD_GENERATOR;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.MOLECULAR_TRANSFORMER_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.Polyetheretherketone;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.block.blocks.GTQTKQCC.CasingType.ADV_COMPUTER_CASING;
import static keqing.gtqtcore.common.items.GTQTMetaItems.GENERAL_CIRCUIT_UV;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;
import keqing.gtqtcore.api.unification.MaterialHelper;
import keqing.gtqtcore.common.block.blocks.GTQTBlockGlassCasing;
import keqing.gtqtcore.common.block.blocks.GTQTElectronMicroscope;

public class MolecularTransformer {

    public static void assembler(int tier,GTQTElectronMicroscope.CasingType A,GTQTElectronMicroscope.CasingType B)
    {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(HULL[3+tier])
                .input(frameGt, MaterialHelper.Plate[3+tier],8)
                .input(stickLong, MaterialHelper.Wire[3+tier],32)
                .input(circuit, MarkerMaterial.create(GTValues.VN[4+tier].toLowerCase()), 8)
                .input(wireGtSingle, MaterialHelper.Superconductor[2+tier],64)
                .circuitMeta(5)
                .EUt(VA[3+tier])
                .outputs(GTQTMetaBlocks.ELECTRON_MICROSCOPE.getItemVariant(A))
                .duration(800)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(HULL[3+tier])
                .input(frameGt, MaterialHelper.Plate[3+tier],8)
                .inputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(MaterialHelper.Glass[tier],8))
                .input(ring, MaterialHelper.Plate[3+tier],8)
                .input(circuit, MarkerMaterial.create(GTValues.VN[4+tier].toLowerCase()), 8)
                .circuitMeta(5)
                .EUt(VA[3+tier])
                .outputs(GTQTMetaBlocks.ELECTRON_MICROSCOPE.getItemVariant(B))
                .duration(800)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }
    public static void init() {

        assembler(1, GTQTElectronMicroscope.CasingType.SOURSE_I, GTQTElectronMicroscope.CasingType.LENS_I);
        assembler(2, GTQTElectronMicroscope.CasingType.SOURSE_II, GTQTElectronMicroscope.CasingType.LENS_II);
        assembler(3, GTQTElectronMicroscope.CasingType.SOURSE_III, GTQTElectronMicroscope.CasingType.LENS_III);
        assembler(4, GTQTElectronMicroscope.CasingType.SOURSE_IV, GTQTElectronMicroscope.CasingType.LENS_IV);
        assembler(5, GTQTElectronMicroscope.CasingType.SOURSE_V, GTQTElectronMicroscope.CasingType.LENS_V);

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[4], 4)
                .input(circuit,MarkerMaterials.Tier.IV , 16)
                .input(SENSOR_EV, 8)
                .input(FIELD_GENERATOR_EV, 8)
                .input(plate, NanometerBariumTitanate, 64)
                .input(gear, HSSE, 12)
                .input(stick, HSSG, 12)
                .input(spring, Titanium, 12)
                .input(wireFine, MercuryBariumCalciumCuprate, 32)
                .input(wireFine, MercuryBariumCalciumCuprate, 32)
                .fluidInputs(Polyethylene.getFluid(16000))
                .fluidInputs(Polytetrafluoroethylene.getFluid(8000))
                .fluidInputs(ReinforcedEpoxyResin.getFluid(4000))
                .fluidInputs(Polybenzimidazole.getFluid(1000))
                .outputs(ELECTRON_MICROSCOPE.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(ULTRAVIOLET_LAMP_CHAMBER[4].getStackForm())
                        .duration(12000)
                        .EUt(VA[HV]))
                .duration(400).EUt(VA[EV]).buildAndRegister();

        //  Coal -> Diamond
        MOLECULAR_TRANSFORMER_RECIPES.recipeBuilder()
                .input(gem, Coal)
                .output(gem, Diamond)
                .EUt(VA[HV])
                .CWUt(VA[HV])
                .Tier(1)
                .duration(3000)
                .buildAndRegister();

        //  Cu -> Au
        MOLECULAR_TRANSFORMER_RECIPES.recipeBuilder()
                .input(ingot, Copper)
                .output(ingot, Gold)
                .EUt(VA[EV])
                .CWUt(VA[EV])
                .Tier(2)
                .duration(6000)
                .buildAndRegister();

        //  Fe -> Co
        MOLECULAR_TRANSFORMER_RECIPES.recipeBuilder()
                .input(ingot, Iron)
                .output(ingot, Cobalt)
                .EUt(VA[EV])
                .CWUt(VA[EV])
                .Tier(2)
                .duration(6000)
                .buildAndRegister();

        //  Co -> Cu
        MOLECULAR_TRANSFORMER_RECIPES.recipeBuilder()
                .input(ingot, Cobalt)
                .output(ingot, Copper)
                .EUt(VA[EV])
                .CWUt(VA[EV])
                .Tier(2)
                .duration(6000)
                .buildAndRegister();

        //  Ni -> Cu
        MOLECULAR_TRANSFORMER_RECIPES.recipeBuilder()
                .input(ingot, Nickel)
                .output(ingot, Copper)
                .EUt(VA[EV])
                .CWUt(VA[EV])
                .Tier(2)
                .duration(6000)
                .buildAndRegister();

        //  Cu -> Zn
        MOLECULAR_TRANSFORMER_RECIPES.recipeBuilder()
                .input(ingot, Copper)
                .output(ingot, Zinc)
                .EUt(VA[EV])
                .CWUt(VA[EV])
                .Tier(2)
                .duration(6000)
                .buildAndRegister();

        //  Cu -> As
        MOLECULAR_TRANSFORMER_RECIPES.recipeBuilder()
                .input(ingot, Zinc)
                .output(ingot, Arsenic)
                .EUt(VA[EV])
                .CWUt(VA[EV])
                .Tier(2)
                .duration(6000)
                .buildAndRegister();

        //  Sn -> Ag
        MOLECULAR_TRANSFORMER_RECIPES.recipeBuilder()
                .input(ingot, Tin)
                .output(ingot, Silver)
                .EUt(VA[EV])
                .CWUt(VA[EV])
                .Tier(2)
                .duration(6000)
                .buildAndRegister();

        //  Graphite -> Graphene
        MOLECULAR_TRANSFORMER_RECIPES.recipeBuilder()
                .input(dust, Graphite)
                .output(dust, Graphene)
                .EUt(VA[EV])
                .CWUt(VA[EV])
                .Tier(2)
                .duration(6000)
                .buildAndRegister();

        //  Cr -> Ti
        MOLECULAR_TRANSFORMER_RECIPES.recipeBuilder()
                .input(ingot, Chrome)
                .output(ingot, Titanium)
                .EUt(VA[ZPM])
                .CWUt(VA[ZPM])
                .Tier(3)
                .duration(12000)
                .buildAndRegister();

        //  Sn -> Id
        MOLECULAR_TRANSFORMER_RECIPES.recipeBuilder()
                .input(ingot, Tin)
                .output(ingot, Iodine)
                .EUt(VA[ZPM])
                .CWUt(VA[ZPM])
                .Tier(3)
                .duration(12000)
                .buildAndRegister();

        //  Au -> Pt
        MOLECULAR_TRANSFORMER_RECIPES.recipeBuilder()
                .input(ingot, Gold)
                .output(ingot, Platinum)
                .EUt(VA[ZPM])
                .CWUt(VA[ZPM])
                .Tier(3)
                .duration(12000)
                .buildAndRegister();

        //  Pb -> Pd
        MOLECULAR_TRANSFORMER_RECIPES.recipeBuilder()
                .input(ingot, Lead)
                .output(ingot, Palladium)
                .EUt(VA[ZPM])
                .CWUt(VA[ZPM])
                .Tier(3)
                .duration(12000)
                .buildAndRegister();

        //  Fe -> Ir
        MOLECULAR_TRANSFORMER_RECIPES.recipeBuilder()
                .input(ingot, Iron)
                .output(ingot, Iridium)
                .EUt(VA[UV])
                .CWUt(VA[UV])
                .Tier(4)
                .duration(24000)
                .buildAndRegister();

        //  Al -> Os
        MOLECULAR_TRANSFORMER_RECIPES.recipeBuilder()
                .input(ingot, Aluminium)
                .output(ingot, Osmium)
                .EUt(VA[UV])
                .CWUt(VA[UV])
                .Tier(4)
                .duration(24000)
                .buildAndRegister();

    }
}
