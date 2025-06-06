package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.EMITTER_UV;
import static gregtech.common.items.MetaItems.SENSOR_UV;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.swarm;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.SWARM_HATCH;

public class WrapSwarmHandler {
    public static void init() {
        HatchRecipes();
        WrapRecipes();
    }

    private static void WrapRecipes() {
        registerWrapRecipes(WRAP_NANO_SWARM_CARBON, Carbon);
        registerWrapRecipes(WRAP_NANO_SWARM_GOLD, Gold);
        registerWrapRecipes(WRAP_NANO_SWARM_PLATINUM, Platinum);
        registerWrapRecipes(WRAP_NANO_SWARM_IRIDIUM, Iridium);
        registerWrapRecipes(WRAP_NANO_SWARM_SAMARIUM, Samarium);
        registerWrapRecipes(WRAP_NANO_SWARM_NAQUADAH, Naquadah);
        registerWrapRecipes(WRAP_NANO_SWARM_DURANIUM, Duranium);
        registerWrapRecipes(WRAP_NANO_SWARM_ADAMANTIUM, Adamantium);
        registerWrapRecipes(WRAP_NANO_SWARM_ORICHALCUM, Orichalcum);
        registerWrapRecipes(WRAP_NANO_SWARM_NEUTRONIUM, Neutronium);
    }

    private static void registerWrapRecipes(MetaItem<?>.MetaValueItem wrap, Material material) {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III, 8)
                .input(circuit, MarkerMaterials.Tier.UHV, 16)
                .input(circuit, MarkerMaterials.Tier.UV, 32)
                .input(circuit, MarkerMaterials.Tier.ZPM, 64)
                .input(swarm, material, 1)
                .input(screw, Neutronium, 4)
                .input(NANO_POWER_IC, 64)
                .input(NANO_POWER_IC, 64)
                .input(wireGtSingle, UHVSuperconductor, 64)
                .input(wireGtSingle, UHVSuperconductor, 64)
                .fluidInputs(UUMatter.getFluid(4000))
                .fluidInputs(MutantActiveSolder.getFluid(L * 16))
                .output(wrap)
                .EUt(VA[UHV])
                .duration(400)
                .stationResearch(b -> b
                        .researchStack(DISK_36)
                        .EUt(VA[UHV])
                        .CWUt(CWT[UV]))
                .buildAndRegister();
    }

    private static void HatchRecipes() {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[UHV])
                .input(SENSOR_UV, 8)
                .input(EMITTER_UV, 8)
                .input(circuit, MarkerMaterials.Tier.UHV, 16)
                .input(gear, CubicBoronNitride, 2)
                .input(screw, TitanSteel, 2)
                .input(stickLong, Orichalcum, 2)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(MutantActiveSolder.getFluid(L * 16))
                .fluidInputs(NiobiumTitanium.getFluid(L * 16))
                .fluidInputs(Naquadria.getFluid(L * 4))
                .output(SWARM_HATCH)
                .EUt(VA[UHV])
                .duration(250)
                .stationResearch(b -> b
                        .researchStack(DISK_36)
                        .EUt(VA[UHV])
                        .CWUt(CWT[UV]))
                .buildAndRegister();
    }
}
