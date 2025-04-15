package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static keqing.gtqtcore.api.GTQTValue.gtqtcoreId;
import static keqing.gtqtcore.api.unification.material.info.GTQTMaterialIconSet.CUSTOM_MHCSM;

public class UnknownCompositionMaterials {

    //无组分材料
    //无任何前置的材料可以写在这里

    private static int startId = 8000;
    private static final int END_ID = startId + 2000;

    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static void init() {
        //8000-8100 100位艾萨
        //铁系泡沫
        GTQTMaterials.IronFront = new Material.Builder(getMaterialsId(), gtqtcoreId("iron_front")).fluid().color(0xD6D6D6).build().setFormula("Front");
        GTQTMaterials.BandedIronFront = new Material.Builder(getMaterialsId(), gtqtcoreId("banded_iron_front")).fluid().color(0xDAA520).build().setFormula("Front");
        GTQTMaterials.BrownLimoniteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("brown_limonite_front")).fluid().color(0xD2691E).build().setFormula("Front");
        GTQTMaterials.YellowLimoniteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("yellow_limonite_front")).fluid().color(0xCDCD00).build().setFormula("Front");
        GTQTMaterials.ChromiteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("chromite_front")).fluid().color(0x8E8E38).build().setFormula("Front");
        GTQTMaterials.IlmeniteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("ilmenite_front")).fluid().color(0x8B814C).build().setFormula("Front");
        GTQTMaterials.MagnetiteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("magnetite_front")).fluid().color(0x8B7500).build().setFormula("Front");
        GTQTMaterials.PyriteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("pyrite_front")).fluid().color(0x8B5A2B).build().setFormula("Front");
        GTQTMaterials.TantaliteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("tantalite_front")).fluid().color(0x8DEEEE).build().setFormula("Front");
        GTQTMaterials.CopperFront = new Material.Builder(getMaterialsId(), gtqtcoreId("copper_front")).fluid().color(0xD2691E).build().setFormula("Front");
        GTQTMaterials.TetrahedriteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("tetrahedrite_front")).fluid().color(0x8B2323).build().setFormula("Front");
        GTQTMaterials.ChalcociteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("chalocite_front")).fluid().color(0x595959).build().setFormula("Front");
        GTQTMaterials.AluminiumFront = new Material.Builder(getMaterialsId(), gtqtcoreId("aluminium_front")).fluid().color(0x1E90FF).build().setFormula("Front");
        GTQTMaterials.BauxiteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("bauxite_front")).fluid().color(0x8B4726).build().setFormula("Front");
        GTQTMaterials.AlmandineFront = new Material.Builder(getMaterialsId(), gtqtcoreId("almandine_front")).fluid().color(0xD70505).build().setFormula("Front");
        GTQTMaterials.PentlanditeFront = new Material.Builder(getMaterialsId(), gtqtcoreId("pentlandite_front")).fluid().color(0x8c800a).build().setFormula("Front");
        GTQTMaterials.ChalcopyriteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("chalcopyrite_front")).fluid().color(0x896826).build().setFormula("Front");
        GTQTMaterials.GrossularFront = new Material.Builder(getMaterialsId(), gtqtcoreId("grossular_front")).fluid().color(0xab5860).build().setFormula("Front");
        GTQTMaterials.MonaziteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("monazite_front")).fluid().color(0x2e3f2e).build().setFormula("Front");
        GTQTMaterials.NickelFront = new Material.Builder(getMaterialsId(), gtqtcoreId("nickel_front")).fluid().color(0xABABD5).build().setFormula("Front");
        GTQTMaterials.PlatinumFront = new Material.Builder(getMaterialsId(), gtqtcoreId("platinum_front")).fluid().color(0xe2e2b2).build().setFormula("Front");
        GTQTMaterials.PyropeFront = new Material.Builder(getMaterialsId(), gtqtcoreId("pyrope_front")).fluid().color(0x682E57).build().setFormula("Front");
        GTQTMaterials.RedstoneFront = new Material.Builder(getMaterialsId(), gtqtcoreId("redstone_front")).fluid().color(0xAC0505).build().setFormula("Front");
        GTQTMaterials.SpessartineFront = new Material.Builder(getMaterialsId(), gtqtcoreId("spessartine_front")).fluid().color(0XDF5A5A).build().setFormula("Front");
        GTQTMaterials.SphaleriteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("sphalerite_front")).fluid().color(0xD9D9D9).build().setFormula("Front");
        GTQTMaterials.TinFront = new Material.Builder(getMaterialsId(), gtqtcoreId("tin_front")).fluid().color(0xFFFFFF).build().setFormula("Front");
        GTQTMaterials.CassiteriteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("cassiterite_front")).fluid().color(0xFFFFFF).build().setFormula("Front");
        GTQTMaterials.GoldFront = new Material.Builder(getMaterialsId(), gtqtcoreId("gold_front")).fluid().color(0xEEEE00).build().setFormula("Front");
        GTQTMaterials.PreciousFront = new Material.Builder(getMaterialsId(), gtqtcoreId("precious_front")).fluid().color(0xCDAD00).build().setFormula("Front");
        GTQTMaterials.LeanGoldFront = new Material.Builder(getMaterialsId(), gtqtcoreId("lean_gold_front")).fluid().color(0xDAA520).build().setFormula("Front");
        GTQTMaterials.RichGoldFront = new Material.Builder(getMaterialsId(), gtqtcoreId("rich_gold_front")).fluid().color(0xD2691E).build().setFormula("Front");
        GTQTMaterials.LeadFront = new Material.Builder(getMaterialsId(), gtqtcoreId("lead_front")).fluid().color(0x9A32CD).build().setFormula("Front");
        GTQTMaterials.GalenaFront = new Material.Builder(getMaterialsId(), gtqtcoreId("galena_front")).fluid().color(0x9B30FF).build().setFormula("Front");
        GTQTMaterials.WulfeniteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("wulfenite_front")).fluid().color(0x8B8B00).build().setFormula("Front");
        GTQTMaterials.CrocoiteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("crocoite_front")).fluid().color(0x8B8B7A).build().setFormula("Front");
        GTQTMaterials.StibniteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("stibnite_front")).fluid().color(0x303030).build().setFormula("Front");
        GTQTMaterials.ScheeliteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("scheelite_front")).fluid().color(0xB8860B).build().setFormula("Front");
        GTQTMaterials.TungstateFront = new Material.Builder(getMaterialsId(), gtqtcoreId("tungstate_front")).fluid().color(0x303030).build().setFormula("Front");
        GTQTMaterials.UraniniteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("uraninite_front")).fluid().color(0x303030).build().setFormula("Front");
        GTQTMaterials.PitchblendeFront = new Material.Builder(getMaterialsId(), gtqtcoreId("pitchblende_front")).fluid().color(0xBCEE68).build().setFormula("Front");
        GTQTMaterials.NaquadahOxideFront = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadahoxide_front")).fluid().color(0xCD853F).build().setFormula("Front");
        GTQTMaterials.BorniteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("bornite_front")).fluid().color(0xCD853F).build().setFormula("Front");
        GTQTMaterials.BastnasiteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("bastnasite_front")).fluid().color(0x8B4726).build().setFormula("Front");

        //
        startId = 8100;
        //

        GTQTMaterials.RareEarthChloridesSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("rare_earth_chlorides_solution"))
                .fluid()
                .color(0x838367)
                .flags(DISABLE_DECOMPOSITION)
                .components(RareEarth, 1, Chlorine, 1, Water, 1)
                .build();

        GTQTMaterials.LeachedTurpentine = new Material.Builder(getMaterialsId(), gtqtcoreId("leached_turpentine"))
                .fluid()
                .color(0x330D16)
                .flags(DISABLE_DECOMPOSITION)
                .components(GTQTMaterials.Turpentine, 1, RareEarth, 1)
                .build();

        GTQTMaterials.SteamCrackedTurpentine = new Material.Builder(getMaterialsId(), gtqtcoreId("steamcracked_turpentine")).fluid().color(0x634D56).build();

        GTQTMaterials.BZMedium = new Material.Builder(getMaterialsId(), gtqtcoreId("bz_medium")).fluid().color(0xA2FD35).build(); //TODO "The Belousov-Zhabotinsky Reaction" tooltip

        GTQTMaterials.MutatedLivingSolder = new Material.Builder(getMaterialsId(), gtqtcoreId("mutated_living_solder"))
                .fluid()
                .color(0x936D9B)
                .iconSet(DULL)
                .build()
                .setFormula("?Sn?Bi?", true);

        GTQTMaterials.RichNitrogenMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("rich_nitrogen_mixture")).gas().color(0x6891D8).build();

        GTQTMaterials.RichAmmoniaMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("rich_ammonia_mixture")).fluid().color(0x708ACD).build();

        GTQTMaterials.Brine = new Material.Builder(getMaterialsId(), gtqtcoreId("brine")).fluid().color(0xFCFC8A).build();

        GTQTMaterials.ChlorinatedBrine = new Material.Builder(getMaterialsId(), gtqtcoreId("chlorinated_brine")).fluid().color(0xFAFC8A).build();

        GTQTMaterials.ChalcogenAnodeMud = new Material.Builder(getMaterialsId(), gtqtcoreId("chalcogen_anode_mud")).dust().color(0x8A3324).iconSet(FINE).build();

        GTQTMaterials.MethylamineMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("methylamine_mixture")).fluid().color(0xAA4400).build();

        GTQTMaterials.EDP = new Material.Builder(getMaterialsId(), gtqtcoreId("edp")).fluid().color(0xFBFF17).build();

        GTQTMaterials.PhosphoreneSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("phosphorene_solution")).fluid().color(0x465966).build();

        GTQTMaterials.SodioIndene = new Material.Builder(getMaterialsId(), gtqtcoreId("sodio_indene")).fluid().color(0x1D1C24).build();

        GTQTMaterials.SteamCrackedSodioIndene = new Material.Builder(getMaterialsId(), gtqtcoreId("steam_cracked_sodio_indene"))
                .fluid()
                .color(0x1C1A29).build();

        GTQTMaterials.MolybdenumFlue = new Material.Builder(getMaterialsId(), gtqtcoreId("molybdenum_flue")).gas().color(0x39194A).build();

        GTQTMaterials.TraceRheniumFlue = new Material.Builder(getMaterialsId(), gtqtcoreId("trace_rhenium_flue")).gas().color(0x96D6D5).build();

        GTQTMaterials.FracturingFluid = new Material.Builder(getMaterialsId(), gtqtcoreId("fracturing_fluid")).fluid().color(0x96D6D5).build();

        GTQTMaterials.BedrockSmoke = new Material.Builder(getMaterialsId(), gtqtcoreId("bedrock_smoke")).gas().color(0x525252).build();

        GTQTMaterials.Bedrock = new Material.Builder(getMaterialsId(), gtqtcoreId("bedrock"))
                .dust()
                .liquid()
                .color(0x404040)
                .iconSet(ROUGH)
                .flags(GENERATE_PLATE)
                .build();

        GTQTMaterials.BedrockSootSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("bedrock_soot_solution")).fluid().color(0x1E2430).build();

        GTQTMaterials.CleanBedrockSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("clean_bedrock_solution")).fluid().color(0xA89F9E).build();

        GTQTMaterials.HeavyBedrockSmoke = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_bedrock_smoke")).gas().color(0x242222).build();

        GTQTMaterials.MediumBedrockSmoke = new Material.Builder(getMaterialsId(), gtqtcoreId("medium_bedrock_smoke")).gas().color(0x2E2C2C).build();

        GTQTMaterials.LightBedrockSmoke = new Material.Builder(getMaterialsId(), gtqtcoreId("light_bedrock_smoke")).gas().color(0x363333).build();

        GTQTMaterials.UltralightBedrockSmoke = new Material.Builder(getMaterialsId(), gtqtcoreId("ultralight_bedrock_smoke")).gas().color(0x403D3D).build();

        GTQTMaterials.HeavyTaraniumGas = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_taranium_gas")).gas().color(0x262626).build();

        GTQTMaterials.MediumTaraniumGas = new Material.Builder(getMaterialsId(), gtqtcoreId("medium_taranium_gas")).gas().color(0x313131).build();

        GTQTMaterials.LightTaraniumGas = new Material.Builder(getMaterialsId(), gtqtcoreId("light_taranium_gas")).gas().color(0x404040).build();

        GTQTMaterials.BedrockGas = new Material.Builder(getMaterialsId(), gtqtcoreId("bedrock_gas")).gas().color(0x575757).build();

        GTQTMaterials.CrackedHeavyTaranium = new Material.Builder(getMaterialsId(), gtqtcoreId("cracked_heavy_taranium")).fluid().color(0x1F2B2E).build();

        GTQTMaterials.CrackedMediumTaranium = new Material.Builder(getMaterialsId(), gtqtcoreId("cracked_medium_taranium")).fluid().color(0x29393D).build();

        GTQTMaterials.CrackedLightTaranium = new Material.Builder(getMaterialsId(), gtqtcoreId("cracked_light_taranium")).fluid().color(0x374C52).build();

        GTQTMaterials.EnrichedBedrockSootSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("enriched_bedrock_soot_solution")).fluid().color(0x280C26).build();

        GTQTMaterials.CleanEnrichedBedrockSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("clean_enriched_bedrock_solution")).fluid().color(0x828C8C).build();

        GTQTMaterials.HeavyEnrichedBedrockSmoke = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_enriched_bedrock_smoke")).gas().color(0x1A2222).build();

        GTQTMaterials.MediumEnrichedBedrockSmoke = new Material.Builder(getMaterialsId(), gtqtcoreId("medium_enriched_bedrock_smoke")).gas().color(0x1E2C2C).build();

        GTQTMaterials.LightEnrichedBedrockSmoke = new Material.Builder(getMaterialsId(), gtqtcoreId("light_enriched_bedrock_smoke")).gas().color(0x163333).build();

        GTQTMaterials.HeavyEnrichedTaraniumGas = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_enriched_taranium_gas")).gas().color(0x1F2626).build();

        GTQTMaterials.MediumEnrichedTaraniumGas = new Material.Builder(getMaterialsId(), gtqtcoreId("medium_enriched_taranium_gas")).gas().color(0x1F3131).build();

        GTQTMaterials.LightEnrichedTaraniumGas = new Material.Builder(getMaterialsId(), gtqtcoreId("light_enriched_taranium_gas")).gas().color(0x1F4040).build();

        GTQTMaterials.CrackedHeavyEnrichedTaranium = new Material.Builder(getMaterialsId(), gtqtcoreId("cracked_heavy_enriched_taranium")).fluid().color(0x2E1F2E).build();

        GTQTMaterials.CrackedMediumEnrichedTaranium = new Material.Builder(getMaterialsId(), gtqtcoreId("cracked_medium_enriched_taranium")).fluid().color(0x29393D).build();

        GTQTMaterials.CrackedLightEnrichedTaranium = new Material.Builder(getMaterialsId(), gtqtcoreId("cracked_light_enriched_taranium")).fluid().color(0x374C52).build();

        GTQTMaterials.EnergeticNaquadria = new Material.Builder(getMaterialsId(), gtqtcoreId("energetic_naquadria")).fluid().color(0x202020).build();

        GTQTMaterials.LightHyperFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("light_hyper_fuel")).fluid().color(0x8C148C).build();

        GTQTMaterials.MediumHyperFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("medium_hyper_fuel")).fluid().color(0xDC0A0A).build();

        GTQTMaterials.HeavyHyperFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_hyper_fuel")).fluid().color(0x1E5064).build();

        GTQTMaterials.StellarMaterialResidueA = (new Material.Builder(getMaterialsId(), gtqtcoreId("stellar_material_residue_a"))).fluid().plasma().color(14601000).build().setFormula("ST-α", true);
        GTQTMaterials.StellarMaterialResidueB = (new Material.Builder(getMaterialsId(), gtqtcoreId("stellar_material_residue_b"))).fluid().plasma().color(14600000).build().setFormula("ST-β", true);
        GTQTMaterials.StellarMaterialResidueC = (new Material.Builder(getMaterialsId(), gtqtcoreId("stellar_material_residue_c"))).fluid().plasma().color(14639000).build().setFormula("ST-γ", true);
        GTQTMaterials.StellarMaterial = (new Material.Builder(getMaterialsId(), gtqtcoreId("stellar_material"))).fluid().plasma().color(14638000).build().setFormula("ST-ST", true);

        GTQTMaterials.LightNaquadahFuel = (new Material.Builder(getMaterialsId(), gtqtcoreId("light_naquadah_fuel"))).fluid().color(14638000).build().setFormula("NQ", true);
        GTQTMaterials.MediumNaquadahFuel = (new Material.Builder(getMaterialsId(), gtqtcoreId("medium_naquadah_fuel"))).fluid().color(14638000).build().setFormula("-NQ-", true);
        GTQTMaterials.HeavyNaquadahFuel = (new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_naquadah_fuel"))).fluid().color(14638000).build().setFormula("+NQ+", true);

        GTQTMaterials.MagnetoHydrodynamicallyConstrainedStarMatter = new Material.Builder(getMaterialsId(), gtqtcoreId("magneto_hydrodynamically_constrained_star_matter"))
                .ingot()
                .liquid(new FluidBuilder().temperature(6000000))
                .iconSet(CUSTOM_MHCSM)
                .flags(NO_SMELTING, GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR)
                .build();
    }
}
