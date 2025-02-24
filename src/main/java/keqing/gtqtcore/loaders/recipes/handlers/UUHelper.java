package keqing.gtqtcore.loaders.recipes.handlers;

import com.google.common.collect.ImmutableList;
import gregtech.api.GregTechAPI;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.common.blocks.BlockFusionCasing;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

import static gregicality.multiblocks.api.unification.GCYMMaterials.Zeron100;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.cableGtQuadruple;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.items.MetaItems.FIELD_GENERATOR_UV;
import static gregtech.loaders.recipe.CraftingComponent.*;
import static gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.PRECISE_ASSEMBLER_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.KaptonK;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Stellite;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.swarm;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.block.GTQTMetaBlocks.blockMultiblockCasing;
import static keqing.gtqtcore.common.block.GTQTMetaBlocks.blockMultiblockGlass;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockGlass.CasingType.COPY_GALSS;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockGlass.CasingType.UU_GALSS;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;

public class UUHelper {
    public static void init() {
        casingRecipes();
        UUUtils();
        initRecycleRecipe();
    }
    public static void initRecycleRecipe(){
        for (Item item : ForgeRegistries.ITEMS) {
            GTQTcoreRecipeMaps.RECYCLE_RECIPE.recipeBuilder()
                    .input(item,1)
                    .chancedOutput(GTQTMetaItems.SCRAP,2500,500)
                    .EUt(30)
                    .duration(100)
                    .buildAndRegister();
        }
    }
    private static void UUUtils() {
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .fluidInputs(UUMatter.getFluid(1000))
                .output(GTQTMetaItems.UU_MATER)
                .EUt(16)
                .duration(10)
                .buildAndRegister();

        EXTRACTOR_RECIPES.recipeBuilder()
                .fluidOutputs(UUMatter.getFluid(1000))
                .input(GTQTMetaItems.UU_MATER)
                .EUt(16)
                .duration(10)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, Zylon)
                .input(dust, Silver)
                .fluidInputs(UltraGlue.getFluid(144))
                .output(GTQTMetaItems.CD_ROM)
                .EUt(VA[IV])
                .duration(200)
                .buildAndRegister();

        UU_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidOutputs(Materials.UUMatter.getFluid(1))
                .EUt(256)
                .duration(3200)
                .buildAndRegister();

        UU_RECIPES.recipeBuilder()
                .input(GTQTMetaItems.SCRAP)
                .fluidOutputs(Materials.UUMatter.getFluid(1))
                .EUt(256)
                .duration(1600)
                .buildAndRegister();

        FLUID_EXTRACTOR_RECIPES.recipeBuilder()
                .input(GTQTMetaItems.NEUTRON)
                .fluidOutputs(NeutronFlux.getFluid(1000))
                .EUt(VA[IV])
                .duration(200)
                .buildAndRegister();

        FLUID_EXTRACTOR_RECIPES.recipeBuilder()
                .input(GTQTMetaItems.PROTON)
                .fluidOutputs(ProtonFlux.getFluid(1000))
                .EUt(VA[IV])
                .duration(200)
                .buildAndRegister();


        //扫描和复制配方
        for (Material material : GregTechAPI.materialManager.getRegisteredMaterials()) {
            ItemStack is = GTQTMetaItems.CD_ROM.getStackForm();
            NBTTagCompound compound = new NBTTagCompound();
            compound.setString("Name", GTQTUtil.getName(material));
            is.setTagCompound(compound);
            int mass =0;
            int Neutrons =0;
            int Protons =0;

            if (material.getMaterialComponents().isEmpty() || material.getMaterialComponents().size() > 15)
                continue;

            // compute outputs
            for (MaterialStack component : material.getMaterialComponents()) {
                mass+= (int) (component.amount*component.material.getMass());
                Neutrons+= (int) (component.amount*component.material.getNeutrons());
                Protons+= (int) (component.amount*component.material.getProtons());
            }

            var buid = SCANNER_RECIPES.recipeBuilder()
                    .input(GTQTMetaItems.CD_ROM)
                    .outputs(is)
                    .duration(GTQTUtil.baseTime * mass)
                    .EUt(30);

            var copybuild = COPY_RECIPES.recipeBuilder()
                    .notConsumable(is)
                    .fluidInputs(Materials.UUMatter.getFluid(mass))
                    .duration(GTQTUtil.baseTime * mass)
                    .EUt(30);

            if(Neutrons!=0)copybuild.fluidInputs(NeutronFlux.getFluid(Neutrons));
            if(Protons!=0)copybuild .fluidInputs(ProtonFlux.getFluid(Protons));

            if (material.hasProperty(PropertyKey.DUST)) {
                buid.input(dust, material, 1);
                copybuild.output(dust, material, 1);
            } else if (material.hasFluid()) {
                buid.fluidInputs(material.getFluid(144));
                copybuild.fluidOutputs(material.getFluid(144));
            } else
                continue;
            buid.buildAndRegister();
            copybuild.buildAndRegister();
            SCANNER_RECIPES.recipeBuilder()
                    .input(GTQTMetaItems.CD_ROM)
                    .notConsumable(is)
                    .outputs(is)
                    .duration(100)
                    .EUt(30)
                    .buildAndRegister();
        }
    }

    private static void casingRecipes() {
        //多方块外壳配方
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.FUSION_CASING_MK2, 4))
                .input(CIRCUIT_GOOD_III, 4)
                .input(FIELD_GENERATOR_UV, 16)
                .input(frameGt, Tritanium, 8)
                .input(circuit, MarkerMaterials.Tier.UV, 4)
                .input(plateDouble, HG1223, 16)
                .input(plateDouble, Staballoy, 16)
                .input(plate, Zeron100, 16)
                .input(gearSmall, Stellite, 16)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(UUMatter.getFluid(4000))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .outputs(blockMultiblockCasing.getItemVariant(ELEMENT_CONSTRAINS_MACHINE_CASING, 4))
                .stationResearch(b -> b
                        .researchStack(blockMultiblockCasing.getItemVariant(RESONATOR_CASING))
                        .CWUt(CWT[ZPM])
                        .EUt(VA[UV]))
                .EUt(VA[UV])
                .duration(1200)
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III, 1)
                .input(frameGt, Inconel690, 2)
                .input(FIELD_GENERATOR_UV, 4)
                .input(plate, Zeron100, 8)
                .fluidInputs(UUMatter.getFluid(1000))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Naquadria.getFluid(L * 4))
                .outputs(blockMultiblockCasing.getItemVariant(RESONATOR_CASING))
                .EUt(VA[ZPM])
                .duration(600)
                .Tier(3)
                .CWUt(CWT[ZPM])
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III, 1)
                .input(frameGt, Inconel690, 2)
                .input(EMITTER_ZPM, 4)
                .input(plate, Zeron100, 8)
                .fluidInputs(UUMatter.getFluid(1000))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Naquadria.getFluid(L * 4))
                .outputs(blockMultiblockCasing.getItemVariant(BUNCHER_CASING))
                .EUt(VA[ZPM])
                .duration(600)
                .Tier(3)
                .CWUt(CWT[ZPM])
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III, 1)
                .input(VOLTAGE_COIL_ZPM, 8)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 2)
                .input(plate, Zeron100, 8)
                .fluidInputs(UUMatter.getFluid(1000))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Naquadria.getFluid(L * 4))
                .outputs(blockMultiblockCasing.getItemVariant(HIGH_VOLTAGE_CAPACITOR_BLOCK_CASING))
                .EUt(VA[ZPM])
                .duration(600)
                .Tier(3)
                .CWUt(CWT[ZPM])
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III, 1)
                .input(frameGt, Inconel690, 2)
                .input(stick, Americium, 2)
                .input(screw, Tritanium, 2)
                .fluidInputs(UUMatter.getFluid(1000))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Naquadria.getFluid(L * 4))
                .outputs(blockMultiblockCasing.getItemVariant(MASS_GENERATION_CASING))
                .EUt(VA[ZPM])
                .duration(600)
                .Tier(3)
                .CWUt(CWT[ZPM])
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III, 1)
                .input(frameGt, Inconel690, 2)
                .input(stick, Pikyonium64B, 2)
                .input(plate, Zeron100, 2)
                .fluidInputs(UUMatter.getFluid(1000))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Naquadria.getFluid(L * 4))
                .outputs(blockMultiblockCasing.getItemVariant(MASS_GENERATION_COIL_CASING))
                .EUt(VA[ZPM])
                .duration(600)
                .Tier(3)
                .CWUt(CWT[ZPM])
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockGlassCasing.CasingType.FUSION_GLASS, 8))
                .input(CIRCUIT_GOOD_III, 4)
                .input(FIELD_GENERATOR_UV, 16)
                .input(frameGt, HMS1J22Alloy, 8)
                .input(circuit, MarkerMaterials.Tier.UHV, 2)
                .input(circuit, MarkerMaterials.Tier.UV, 4)
                .input(circuit, MarkerMaterials.Tier.ZPM, 8)
                .input(VOLTAGE_COIL_ZPM, 16)
                .input(plateDouble, HG1223, 16)
                .input(plateDouble, Staballoy, 16)
                .input(gear, MaragingSteel250, 16)
                .input(gearSmall, Stellite, 16)
                .input(cableGtQuadruple, VanadiumGallium, 64)
                .input(cableGtQuadruple, VanadiumGallium, 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(UUMatter.getFluid(4000))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .outputs(blockMultiblockGlass.getItemVariant(UU_GALSS, 8))
                .stationResearch(b -> b
                        .researchStack(MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockGlassCasing.CasingType.FUSION_GLASS))
                        .CWUt(CWT[ZPM])
                        .EUt(VA[UV]))
                .EUt(VA[UV])
                .duration(1200)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockGlassCasing.CasingType.FUSION_GLASS, 8))
                .input(CIRCUIT_GOOD_III, 4)
                .input(FIELD_GENERATOR_UV, 16)
                .input(frameGt, Tritanium, 8)
                .input(circuit, MarkerMaterials.Tier.UHV, 2)
                .input(circuit, MarkerMaterials.Tier.UV, 4)
                .input(circuit, MarkerMaterials.Tier.ZPM, 8)
                .input(VOLTAGE_COIL_ZPM, 16)
                .input(plate, Zeron100, 16)
                .input(plateDouble, Staballoy, 16)
                .input(gearSmall, Americium, 16)
                .input(gearSmall, Stellite, 16)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(UUMatter.getFluid(4000))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .outputs(blockMultiblockGlass.getItemVariant(COPY_GALSS, 8))
                .stationResearch(b -> b
                        .researchStack(MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockGlassCasing.CasingType.FUSION_GLASS))
                        .CWUt(CWT[ZPM])
                        .EUt(VA[UV]))
                .EUt(VA[UV])
                .duration(1200)
                .buildAndRegister();
        //多方块控制器配方
        //大UU
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(UU_PRODUCTER[8].getStackForm(4))
                .input(CIRCUIT_GOOD_III, 8)
                .input(ELECTRIC_PUMP_UV, 32)
                .input(FIELD_GENERATOR_UV, 32)
                .input(frameGt, HMS1J22Alloy, 16)
                .input(circuit, MarkerMaterials.Tier.UHV, 4)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(plateDouble, HG1223, 4)
                .input(plateDouble, Staballoy, 4)
                .input(gear, MaragingSteel250, 4)
                .input(gearSmall, Stellite, 16)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(UUMatter.getFluid(4000))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .outputs(LARGE_UU_PRODUCTER.getStackForm())
                .stationResearch(b -> b
                        .researchStack(DISK_30.getStackForm())
                        .CWUt(CWT[ZPM])
                        .EUt(VA[UV]))
                .EUt(VA[UV])
                .duration(1200)
                .buildAndRegister();

        //大复制
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(DUPLICATOR[8].getStackForm(4))
                .input(CIRCUIT_GOOD_III, 8)
                .input(ELECTRIC_PUMP_UV, 32)
                .input(FIELD_GENERATOR_UV, 32)
                .input(frameGt, Pikyonium64B, 16)
                .input(circuit, MarkerMaterials.Tier.UHV, 4)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(plate, Zeron100, 4)
                .input(plateDouble, Staballoy, 4)
                .input(screw, Naquadria, 4)
                .input(gearSmall, Stellite, 16)
                .input(cableGtQuadruple, VanadiumGallium, 64)
                .input(cableGtQuadruple, VanadiumGallium, 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(UUMatter.getFluid(4000))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .outputs(LARGE_ELEMENT_DUPLICATOR.getStackForm())
                .stationResearch(b -> b
                        .researchStack(DISK_30.getStackForm())
                        .CWUt(CWT[ZPM])
                        .EUt(VA[UV]))
                .EUt(VA[UV])
                .duration(1200)
                .buildAndRegister();

        //机器配方
        registerMachineRecipe(DUPLICATOR, "PGP", "CMC", "PFP",
                'M', HULL,
                'P', EMITTER,
                'F', CABLE_HEX,
                'C', CIRCUIT,
                'G', NEUTRON);

        registerMachineRecipe(UU_PRODUCTER, "CGC", "FMF", "CGC",
                'M', HULL,
                'F', CABLE_HEX,
                'C', CIRCUIT,
                'G', PROTON);

        registerMachineRecipe(RECYCLE, "CGC", "FMF", "CGC",
                'M', HULL,
                'F', CABLE_HEX,
                'C', CIRCUIT,
                'G', MOTOR);
    }
}
