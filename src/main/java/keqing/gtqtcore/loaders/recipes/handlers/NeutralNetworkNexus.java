package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import net.minecraftforge.fluids.FluidStack;

import static gregicality.multiblocks.api.unification.GCYMMaterials.TitaniumCarbide;
import static gregicality.multiblocks.api.unification.GCYMMaterials.TitaniumTungstenCarbide;
import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.MAX;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.UUMatter;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.lens;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.items.MetaItems.CARBON_MESH;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.*;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.api.utils.GTQTUtil.VZ;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.NEUTRAL_NETWORK_NEXUS;

public class NeutralNetworkNexus {
    public static void init() {
        Casing();
    }

    private static void Casing() {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CONVEYOR_MODULE_ZPM, 16)
                .input(ROBOT_ARM_ZPM, 16)
                .input(circuit, MarkerMaterials.Tier.UV, 4)
                .input(circuit, MarkerMaterials.Tier.ZPM, 8)
                .input(stickLong,CarbonNanotube,64)
                .input(STEM_CELLS,32)
                .input(screw,Naquadria,16)
                .input(wireFine,Samarium,16)
                .output(swarm, Carbon)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .fluidInputs(UUMatter.getFluid(400))
                .stationResearch(b -> b
                        .researchStack(DISK_18.getStackForm())
                        .CWUt(CWT[IV])
                        .EUt(VA[LuV]))
                .EUt(VA[ZPM])
                .duration(1200)
                .buildAndRegister();

        //  Neutral Network Nexus
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[ZPM],16)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 16)
                .input(circuit, MarkerMaterials.Tier.LuV, 16)
                .input(CONVEYOR_MODULE_ZPM, 64)
                .input(FIELD_GENERATOR_ZPM, 64)
                .input(swarm, Carbon,16)
                .input(CIRCUIT_GOOD_III)
                .input(gear, Inconel625, 4)
                .input(gearSmall, Tantalloy61, 16)
                .input(cableGtQuadruple, VanadiumGallium, 64)
                .input(cableGtQuadruple, VanadiumGallium, 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .fluidInputs(SolderingAlloy.getFluid(L * 32))
                .output(NEUTRAL_NETWORK_NEXUS)
                .stationResearch(b -> b
                        .researchStack(DISK_18.getStackForm())
                        .CWUt(CWT[IV])
                        .EUt(VA[LuV]))
                .EUt(VA[ZPM])
                .duration(1200)
                .buildAndRegister();
    }
}
