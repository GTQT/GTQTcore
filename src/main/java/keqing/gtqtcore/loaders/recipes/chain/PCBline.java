package keqing.gtqtcore.loaders.recipes.chain;

import com.google.common.collect.HashBiMap;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.ConfigHolder;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockGCYSMultiblockCasing;
import keqing.gtqtcore.common.block.blocks.BlockPCBFactoryCasing;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static gregicality.multiblocks.api.unification.GCYMMaterials.Trinaquadalloy;
import static gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities.MEGA_VACUUM_FREEZER;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.PCB_FACTORY_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.Polyetheretherketone;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.swarm;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.block.blocks.GTQTParticleAccelerator.MachineType.ACCELERATOR_FIRM_MKI;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;

public class PCBline {
    private static final HashBiMap<Material, Integer> plasticTiers = HashBiMap.create();
    public static int plasticTier = 0;


    public static void common()
    {
        //1级外壳
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[5])
                .input(CIRCUIT_GOOD_I)
                .input(circuit, MarkerMaterials.Tier.LuV, 1)
                .input(circuit, MarkerMaterials.Tier.IV, 2)
                .input(circuit, MarkerMaterials.Tier.EV, 4)
                .input(CONVEYOR_MODULE_IV,2)
                .input(ELECTRIC_PUMP_IV, 2)
                .input(plate, Naquadah, 4)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(gear,HSSS,4)
                .input(stickLong,Samarium,2)
                .input(wireGtSingle, IVSuperconductor, 8)
                .outputs(GTQTMetaBlocks.PCB_FACTORY_CASING.getItemVariant(BlockPCBFactoryCasing.PCBFactoryCasingType.BASIC_PHOTOLITHOGRAPHIC_FRAMEWORK_CASING, ConfigHolder.recipes.casingsPerCraft))
                .stationResearch(b -> b
                        .researchStack(NANO_COATING.getStackForm())
                        .CWUt(CWT[EV])
                        .EUt(VA[IV]))
                .fluidInputs(Polybenzimidazole.getFluid(L*4))
                .fluidInputs(Ruridit.getFluid(L*4))
                .fluidInputs(Kevlar.getFluid(L*4))
                .fluidInputs(Zylon.getFluid(L * 8))
                .duration(400).EUt(VA[IV]).buildAndRegister();

        //2级外壳
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[6])
                .input(CIRCUIT_GOOD_II)
                .input(circuit, MarkerMaterials.Tier.ZPM, 1)
                .input(circuit, MarkerMaterials.Tier.LuV, 2)
                .input(circuit, MarkerMaterials.Tier.IV, 4)
                .input(CONVEYOR_MODULE_LuV,8)
                .input(ELECTRIC_PUMP_LuV, 8)
                .input(plate, Trinaquadalloy, 4)
                .input(plate, Europium, 4)
                .input(gear,Pikyonium64B,4)
                .input(stickLong,Naquadah,2)
                .input(wireGtSingle, LuVSuperconductor, 8)
                .outputs(GTQTMetaBlocks.PCB_FACTORY_CASING.getItemVariant(BlockPCBFactoryCasing.PCBFactoryCasingType.MOLD_PRINTING_ASSEMBLY_FRAMEWORK_CASING, ConfigHolder.recipes.casingsPerCraft))
                .stationResearch(b -> b
                        .researchStack(PHOTOLITHOGRAPHY_FACTORY.getStackForm())
                        .CWUt(CWT[IV])
                        .EUt(VA[LuV]))
                .fluidInputs(Polybenzimidazole.getFluid(L*4))
                .fluidInputs(Ruridit.getFluid(L*4))
                .fluidInputs(Kevlar.getFluid(L*4))
                .fluidInputs(Zylon.getFluid(L * 8))
                .duration(400).EUt(VA[LuV]).buildAndRegister();
        //三级
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[7])
                .input(CIRCUIT_GOOD_II)
                .input(circuit, MarkerMaterials.Tier.UV, 1)
                .input(circuit, MarkerMaterials.Tier.ZPM, 2)
                .input(circuit, MarkerMaterials.Tier.LuV, 4)
                .input(CONVEYOR_MODULE_ZPM,2)
                .input(ELECTRIC_PUMP_ZPM, 2)
                .input(plate, Duranium, 4)
                .input(plate, Americium, 4)
                .input(gear,Tritanium,4)
                .input(stickLong,Darmstadtium,2)
                .input(wireGtSingle, ZPMSuperconductor, 8)
                .outputs(GTQTMetaBlocks.PCB_FACTORY_CASING.getItemVariant(BlockPCBFactoryCasing.PCBFactoryCasingType.RADIATION_PROOF_SCAN_FRAMEWORK_CASING, ConfigHolder.recipes.casingsPerCraft))
                .stationResearch(b -> b
                        .researchStack(LASER_ENV.getStackForm())
                        .CWUt(CWT[LuV])
                        .EUt(VA[ZPM]))
                .fluidInputs(Polybenzimidazole.getFluid(L*4))
                .fluidInputs(Ruridit.getFluid(L*4))
                .fluidInputs(Kevlar.getFluid(L*4))
                .fluidInputs(Zylon.getFluid(L * 8))
                .duration(400).EUt(VA[ZPM]).buildAndRegister();

        //4级  Biological Sterile Machine Casing (Bio Chamber Casing)
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CHEMICAL_REACTOR[8])
                .input(frameGt, Polybenzimidazole,2)
                .input(CIRCUIT_GOOD_I)
                .input(circuit, MarkerMaterials.Tier.UHV, 1)
                .input(circuit, MarkerMaterials.Tier.UV, 2)
                .input(circuit, MarkerMaterials.Tier.ZPM, 4)
                .input(PETRI_DISH, 4)
                .input(ELECTRIC_PUMP_UV, 2)
                .input(plate, Trinaquadalloy, 4)
                .input(plate, Europium, 4)
                .input(gear,Pikyonium64B,4)
                .input(plate, KaptonK, 12)
                .input(wireGtSingle, UVSuperconductor, 8)
                .outputs(GTQTMetaBlocks.PCB_FACTORY_CASING.getItemVariant(BlockPCBFactoryCasing.PCBFactoryCasingType.BIOLOGICAL_STERILE_MACHINE_CASING, ConfigHolder.recipes.casingsPerCraft))
                .stationResearch(b -> b
                        .researchStack(GENE_MUTAGENESIS.getStackForm())
                        .CWUt(CWT[ZPM])
                        .EUt(VA[UV]))
                .fluidInputs(Mutagen.getFluid(L*4))
                .fluidInputs(Ruridit.getFluid(L*4))
                .fluidInputs(Kevlar.getFluid(L*4))
                .fluidInputs(Zylon.getFluid(L * 8))
                .duration(400).EUt(VA[UV]).buildAndRegister();

        //5级  Infinity Cooling Casing (Thermosink Casing)
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[9])
                .input(frameGt, VoidMetal,2)
                .input(CIRCUIT_GOOD_V)
                .input(circuit, MarkerMaterials.Tier.UEV, 1)
                .input(circuit, MarkerMaterials.Tier.UHV, 2)
                .input(circuit, MarkerMaterials.Tier.UV, 4)
                .input(CONVEYOR_MODULE_UHV,2)
                .input(ELECTRIC_PUMP_UHV, 2)
                .input(rotor, Infinity, 2)
                .input(plate, Draconium, 12)
                .input(gear,Pikyonium64B,4)
                .input(stickLong,Naquadah,2)
                .input(wireGtSingle, UHVSuperconductor, 8)
                .outputs(GTQTMetaBlocks.PCB_FACTORY_CASING.getItemVariant(BlockPCBFactoryCasing.PCBFactoryCasingType.INFINITY_COOLED_MACHINE_CASING, ConfigHolder.recipes.casingsPerCraft))
                .stationResearch(b -> b
                        .researchStack(MEGA_VACUUM_FREEZER.getStackForm())
                        .CWUt(CWT[UV])
                        .EUt(VA[UHV]))
                .fluidInputs(GelidCryotheum.getFluid(L*4))
                .fluidInputs(Ruridit.getFluid(L*4))
                .fluidInputs(Kevlar.getFluid(L*4))
                .fluidInputs(Zylon.getFluid(L * 8))
                .duration(400).EUt(VA[UHV]).buildAndRegister();

        // Substrates
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Naquadah)
                .input(CIRCUIT_GOOD_I)
                .input(circuit, MarkerMaterials.Tier.LuV, 1)
                .input(circuit, MarkerMaterials.Tier.IV, 2)
                .input(circuit, MarkerMaterials.Tier.EV, 4)
                .input(stick, RedSteel, 2)
                .input(plate, BlueSteel, 12)
                .outputs(GTQTMetaBlocks.PCB_FACTORY_CASING.getItemVariant(BlockPCBFactoryCasing.PCBFactoryCasingType.SUBSTRATE_CASING, ConfigHolder.recipes.casingsPerCraft))
                .stationResearch(b -> b
                        .researchStack(CHEMICAL_PLANT.getStackForm())
                        .CWUt(CWT[IV])
                        .EUt(VA[IV]))
                .fluidInputs(Polybenzimidazole.getFluid(L*4))
                .fluidInputs(Ruridit.getFluid(L*4))
                .fluidInputs(Kevlar.getFluid(L*4))
                .fluidInputs(Zylon.getFluid(L * 8))
                .duration(400).EUt(VA[IV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, NaquadahAlloy)
                .input(CIRCUIT_GOOD_II)
                .input(circuit, MarkerMaterials.Tier.ZPM, 1)
                .input(circuit, MarkerMaterials.Tier.LuV, 2)
                .input(circuit, MarkerMaterials.Tier.IV, 4)
                .input(stick, Duranium, 2)
                .input(plate, Ruridit, 12)
                .outputs(GTQTMetaBlocks.PCB_FACTORY_CASING.getItemVariant(BlockPCBFactoryCasing.PCBFactoryCasingType.ADVANCED_SUBSTRATE_CASING, ConfigHolder.recipes.casingsPerCraft))
                .stationResearch(b -> b
                        .researchStack(HUGE_CHEMICAL_REACTOR.getStackForm())
                        .CWUt(CWT[LuV])
                        .EUt(VA[LuV]))
                .fluidInputs(Polybenzimidazole.getFluid(L*4))
                .fluidInputs(Ruridit.getFluid(L*4))
                .fluidInputs(Kevlar.getFluid(L*4))
                .fluidInputs(Zylon.getFluid(L * 8))
                .duration(400).EUt(VA[LuV]).buildAndRegister();

        //  PCB Factory
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_ASSEMBLER[6], 32)
                .input(ASSEMBLER[6], 32)
                .input(FLUID_SOLIDIFIER[6], 32)
                .input(MetaTileEntities.LARGE_CHEMICAL_REACTOR, 32)
                .input(frameGt, RhodiumPlatedPalladium, 64)
                .input(CIRCUIT_GOOD_II, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 8)
                .input(circuit, MarkerMaterials.Tier.LuV, 16)
                .input(circuit, MarkerMaterials.Tier.IV, 32)
                .input(ROBOT_ARM_LuV, 8)
                .input(CONVEYOR_MODULE_LuV, 8)
                .input(plate, Osmiridium, 16)
                .input(gear, Ruridit, 16)
                .input(gear, NaquadahAlloy, 16)
                .input(cableGtSingle, NiobiumTitanium, 64)
                .input(cableGtSingle, NiobiumTitanium, 64)
                .fluidInputs(UltraGlue.getFluid(L * 40))
                .fluidInputs(Polybenzimidazole.getFluid(L * 40))
                .fluidInputs(PCBCoolant.getFluid(4000))
                .fluidInputs(NaquadahEnriched.getFluid(L * 12))
                .output(PCB_FACTORY)
                .stationResearch(b -> b
                        .researchStack(DISK_22.getStackForm())
                        .EUt(VA[LuV])
                        .CWUt(CWT[LuV]))
                .EUt(VA[ZPM])
                .duration(600)
                .buildAndRegister();
    }
    public static void init() {
        common();
        //  Add Plastics to {@link #plasticTiers},
        //  used to auto-generated all PCB Factory recipes.
        addPlasticTier(Polyethylene, 1);
        addPlasticTier(PolyvinylChloride, 2);
        addPlasticTier(Polytetrafluoroethylene, 3);
        addPlasticTier(Epoxy, 4);
        addPlasticTier(ReinforcedEpoxyResin, 5);
        addPlasticTier(Zylon, 6);
        addPlasticTier(Polybenzimidazole, 7);
        addPlasticTier(Polyetheretherketone, 8);
        addPlasticTier(Kevlar, 9);
        addPlasticTier(KaptonK, 10);
        addPlasticTier(KaptonE, 11);
        addPlasticTier(FullerenePolymerMatrix, 12);

        //  Plastic Circuit Board (T1)
        for (int tier = 1; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * Math.sqrt(Math.pow(2, tier - 1)));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(PLASTIC_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(PLASTIC_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, AnnealedCopper, (int) (16 * (Math.sqrt(tier))))
                    .input(foil, Copper, (int) (16 * Math.sqrt(tier)))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * Math.sqrt(tier))))
                    .fluidInputs(Iron3Chloride.getFluid((int) (250 * Math.sqrt(tier))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier] * 3 / 4)
                    .duration((int) Math.ceil(600 / Math.sqrt(Math.pow(1.5, tier - 1.5))))
                    .tier(1)
                    .buildAndRegister();
        }

        //  Plastic Circuit Board (T2)
        for (int tier = 1; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * Math.sqrt(Math.pow(2, tier - 0.5)));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(PLASTIC_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(PLASTIC_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(2)
                    .notConsumable(swarm, Silver)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, AnnealedCopper, (int) (16 * (Math.sqrt(tier))))
                    .input(foil, Copper, (int) (16 * Math.sqrt(tier)))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * Math.sqrt(tier))))
                    .fluidInputs(Iron3Chloride.getFluid((int) (250 * Math.sqrt(tier))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier + 1] * 3 / 4)
                    .duration((int) Math.ceil(600 / Math.sqrt(Math.pow(1.5, tier - 1.5))))
                    .tier(2)
                    .buildAndRegister();
        }

        //  Plastic Circuit Board (T3)
        for (int tier = 1; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * Math.sqrt(Math.pow(2, tier)));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(PLASTIC_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(PLASTIC_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(3)
                    .notConsumable(swarm, Gold)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, AnnealedCopper, (int) (16 * (Math.sqrt(tier))))
                    .input(foil, Copper, (int) (16 * Math.sqrt(tier)))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * Math.sqrt(tier))))
                    .fluidInputs(Iron3Chloride.getFluid((int) (250 * Math.sqrt(tier))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier + 1] * 3 / 4)
                    .duration((int) Math.ceil(600 / Math.sqrt(Math.pow(1.5, tier - 1.5))))
                    .tier(3)
                    .buildAndRegister();
        }

        //  Advanced Circuit Board (T1)
        for (int tier = 2; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 2))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(ADVANCED_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(ADVANCED_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Gold, (int) (16 * (Math.sqrt(tier - 1))))
                    .input(foil, Electrum, (int) (16 * (Math.sqrt(tier - 1))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * (Math.sqrt(tier - 1)))))
                    .fluidInputs(Iron3Chloride.getFluid((int) (500 * (Math.sqrt(tier - 1)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier] * 3 / 4)
                    .duration((int) Math.ceil(600 / Math.sqrt(Math.pow(1.5, tier - 2.5))))
                    .tier(1)
                    .buildAndRegister();
        }

        //  Advanced Circuit Board (T2)
        for (int tier = 2; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * Math.sqrt(Math.pow(2, tier - 1.5)));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(ADVANCED_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(ADVANCED_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(2)
                    .notConsumable(swarm, Silver)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Gold, (int) (16 * (Math.sqrt(tier - 1))))
                    .input(foil, Electrum, (int) (16 * (Math.sqrt(tier - 1))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * (Math.sqrt(tier - 1)))))
                    .fluidInputs(Iron3Chloride.getFluid((int) (500 * (Math.sqrt(tier - 1)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier + 1] * 3 / 4)
                    .duration((int) Math.ceil(500 / Math.sqrt(Math.pow(1.5, tier - 2.5))))
                    .tier(2)
                    .buildAndRegister();
        }

        //  Advanced Circuit Board (T3)
        for (int tier = 2; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * Math.sqrt(Math.pow(2, tier - 1)));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(ADVANCED_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(ADVANCED_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(3)
                    .notConsumable(swarm, Gold)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Gold, (int) (16 * (Math.sqrt(tier - 1))))
                    .input(foil, Electrum, (int) (16 * Math.sqrt(tier - 1)))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * (Math.sqrt(tier - 1)))))
                    .fluidInputs(Iron3Chloride.getFluid((int) (500 * (Math.sqrt(tier - 1)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier + 1] * 3 / 4)
                    .duration((int) Math.ceil(400 / Math.sqrt(Math.pow(1.5, tier - 2.5))))
                    .tier(3)
                    .buildAndRegister();
        }

        //  Extreme Circuit Board (T1)
        for (int tier = 3; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 3))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(EXTREME_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(EXTREME_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Aluminium, (int) (16 * (Math.sqrt(tier - 2))))
                    .input(foil, Bronze, (int) (16 * (Math.sqrt(tier - 2))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * (Math.sqrt(tier - 2)))))
                    .fluidInputs(Iron3Chloride.getFluid((int) (1000 * (Math.sqrt(tier - 2)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier] * 3 / 4)
                    .duration((int) Math.ceil(600 / Math.sqrt(Math.pow(1.5, tier - 3.5))))
                    .tier(1)
                    .buildAndRegister();
        }

        //  Extreme Circuit Board (T2)
        for (int tier = 3; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 2.5))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(EXTREME_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(EXTREME_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(2)
                    .notConsumable(swarm, Silver)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Aluminium, (int) (16 * (Math.sqrt(tier - 2))))
                    .input(foil, Bronze, (int) (16 * (Math.sqrt(tier - 2))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * (Math.sqrt(tier - 2)))))
                    .fluidInputs(Iron3Chloride.getFluid((int) (1000 * (Math.sqrt(tier - 2)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier + 1] * 3 / 4)
                    .duration((int) Math.ceil(500 / Math.sqrt(Math.pow(1.5, tier - 3.5))))
                    .tier(2)
                    .buildAndRegister();
        }

        //  Extreme Circuit Board (T3)
        for (int tier = 3; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 2))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(EXTREME_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(EXTREME_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(3)
                    .notConsumable(swarm, Gold)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Aluminium, (int) (16 * (Math.sqrt(tier - 2))))
                    .input(foil, Bronze, (int) (16 * (Math.sqrt(tier - 2))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * (Math.sqrt(tier - 2)))))
                    .fluidInputs(Iron3Chloride.getFluid((int) (1000 * (Math.sqrt(tier - 2)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier + 1] * 3 / 4)
                    .duration((int) Math.ceil(400 / Math.sqrt(Math.pow(1.5, tier - 3.5))))
                    .tier(3)
                    .buildAndRegister();
        }

        //  Elite Circuit Board (T1)
        for (int tier = 4; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 4))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(ELITE_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(ELITE_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Palladium, (int) (16 * (Math.sqrt(tier - 3))))
                    .input(foil, Platinum, (int) (16 * (Math.sqrt(tier - 3))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * (Math.sqrt(tier - 3)))))
                    .fluidInputs(Iron3Chloride.getFluid((int) (2000 * (Math.sqrt(tier - 3)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier] * 3 / 4)
                    .duration((int) Math.ceil(600 / Math.sqrt(Math.pow(1.5, tier - 4.5))))
                    .tier(1)
                    .buildAndRegister();
        }

        //  Elite Circuit Board (T2)
        for (int tier = 4; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 3.5))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(ELITE_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(ELITE_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(2)
                    .notConsumable(swarm, Silver)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Palladium, (int) (16 * (Math.sqrt(tier - 3))))
                    .input(foil, Platinum, (int) (16 * (Math.sqrt(tier - 3))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * (Math.sqrt(tier - 3)))))
                    .fluidInputs(Iron3Chloride.getFluid((int) (2000 * (Math.sqrt(tier - 3)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier + 1] * 3 / 4)
                    .duration((int) Math.ceil(500 / Math.sqrt(Math.pow(1.5, tier - 4.5))))
                    .tier(2)
                    .buildAndRegister();
        }

        //  Elite Circuit Board (T3)
        for (int tier = 4; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * Math.sqrt(Math.pow(2, tier - 3)));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(ELITE_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(ELITE_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(3)
                    .notConsumable(swarm, Gold)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Palladium, (int) (16 * (Math.sqrt(tier - 3))))
                    .input(foil, Platinum, (int) (16 * (Math.sqrt(tier - 3))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * (Math.sqrt(tier - 3)))))
                    .fluidInputs(Iron3Chloride.getFluid((int) (2000 * (Math.sqrt(tier - 3)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier + 1] * 3 / 4)
                    .duration((int) Math.ceil(400 / Math.sqrt(Math.pow(1.5, tier - 4.5))))
                    .tier(3)
                    .buildAndRegister();
        }

        //  Wetware Circuit Board (T1)
        for (int tier = 5; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 5))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(WETWARE_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(WETWARE_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, NiobiumTitanium, (int) (16 * Math.sqrt(tier - 4)))
                    .input(foil, VanadiumGallium, (int) (16 * Math.sqrt(tier - 4)))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * Math.sqrt(tier - 4))))
                    .fluidInputs(Iron3Chloride.getFluid((int) (4000 * Math.sqrt(tier - 4))))
                    .fluidInputs(SterileGrowthMedium.getFluid((int) (2000 * Math.sqrt(tier - 4))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier] * 3 / 4)
                    .duration((int) Math.ceil(600 / Math.sqrt(Math.pow(1.5, tier - 5.5))))
                    .tier(1)
                    .isBioUpgrade(1)
                    .buildAndRegister();
        }

        //  Wetware Circuit Board (T2)
        for (int tier = 5; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 4.5))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(WETWARE_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(WETWARE_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(2)
                    .notConsumable(swarm, Silver)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, NiobiumTitanium, (int) (16 * (Math.sqrt(tier - 4))))
                    .input(foil, VanadiumGallium, (int) (16 * (Math.sqrt(tier - 4))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * (Math.sqrt(tier - 4)))))
                    .fluidInputs(Iron3Chloride.getFluid((int) (4000 * (Math.sqrt(tier - 4)))))
                    .fluidInputs(SterileGrowthMedium.getFluid((int) (2000 * Math.sqrt(tier - 4))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier + 1] * 3 / 4)
                    .duration((int) (Math.ceil(500 / Math.sqrt(Math.pow(1.5, tier - 5.5)))))
                    .tier(2)
                    .isBioUpgrade(1)
                    .buildAndRegister();
        }

        //  Wetware Circuit Board (T3)
        for (int tier = 5; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 4))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(WETWARE_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(WETWARE_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(3)
                    .notConsumable(swarm, Gold)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, NiobiumTitanium, (int) (16 * (Math.sqrt(tier - 4))))
                    .input(foil, VanadiumGallium, (int) (16 * (Math.sqrt(tier - 4))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * (Math.sqrt(tier - 4)))))
                    .fluidInputs(Iron3Chloride.getFluid((int) (4000 * (Math.sqrt(tier - 4)))))
                    .fluidInputs(SterileGrowthMedium.getFluid((int) (2000 * (Math.sqrt(tier - 4)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier + 1] * 3 / 4)
                    .duration((int) Math.ceil(400 / Math.sqrt(Math.pow(1.5, tier - 5.5))))
                    .tier(3)
                    .isBioUpgrade(1)
                    .buildAndRegister();
        }

        //  Gooware Circuit Board (T1)
        for (int tier = 6; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 6))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(GOOWARE_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(GOOWARE_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Europium, (int) (16 * (Math.sqrt(tier - 5))))
                    .input(foil, YttriumBariumCuprate, (int) (16 * (Math.sqrt(tier - 5))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * (Math.sqrt(tier - 5)))))
                    .fluidInputs(EDP.getFluid((int) (6000 * (Math.sqrt(tier - 5)))))
                    .fluidInputs(SterileGrowthMedium.getFluid((int) (4000 * (Math.sqrt(tier - 5)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier] * 3 / 4)
                    .duration((int) Math.ceil(600 / Math.sqrt(Math.pow(1.5, tier - 5.5))))
                    .tier(1)
                    .isBioUpgrade(1)
                    .buildAndRegister();
        }

        //  Gooware Circuit Board (T2)
        for (int tier = 6; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 5.5))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(GOOWARE_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(GOOWARE_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(2)
                    .notConsumable(swarm, Silver)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Europium, (int) (16 * (Math.sqrt(tier - 5))))
                    .input(foil, YttriumBariumCuprate, (int) (16 * (Math.sqrt(tier - 5))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * (Math.sqrt(tier - 5)))))
                    .fluidInputs(EDP.getFluid((int) (6000 * (Math.sqrt(tier - 5)))))
                    .fluidInputs(SterileGrowthMedium.getFluid((int) (4000 * (Math.sqrt(tier - 5)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier + 1] * 3 / 4)
                    .duration((int) Math.ceil(500 / Math.sqrt(Math.pow(1.5, tier - 6.5))))
                    .tier(2)
                    .isBioUpgrade(1)
                    .buildAndRegister();
        }

        //  Gooware Circuit Board (T3)
        for (int tier = 6; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 5))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(GOOWARE_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(GOOWARE_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(3)
                    .notConsumable(swarm, Gold)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Europium, (int) (16 * (Math.sqrt(tier - 5))))
                    .input(foil, YttriumBariumCuprate, (int) (16 * (Math.sqrt(tier - 5))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * (Math.sqrt(tier - 5)))))
                    .fluidInputs(EDP.getFluid((int) (6000 * (Math.sqrt(tier - 5)))))
                    .fluidInputs(SterileGrowthMedium.getFluid((int) (4000 * (Math.sqrt(tier - 5)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier + 1] * 3 / 4)
                    .duration((int) Math.ceil(400 / Math.sqrt(Math.pow(1.5, tier - 6.5))))
                    .tier(3)
                    .isBioUpgrade(1)
                    .buildAndRegister();
        }

        //  Optical Circuit Board (T1)
        for (int tier = 7; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 7))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(OPTICAL_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(OPTICAL_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Americium, (int) (16 * (Math.sqrt(tier - 6))))
                    .input(foil, GalliumNitride, (int) (16 * (Math.sqrt(tier - 6))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * (Math.sqrt(tier - 6)))))
                    .fluidInputs(EDP.getFluid((int) (8000 * (Math.sqrt(tier - 6)))))
                    .fluidInputs(ElectrolyteReflectorMixture.getFluid((int) (6000 * (Math.sqrt(tier - 6)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier] * 3 / 4)
                    .duration((int) Math.ceil(600 / Math.sqrt(Math.pow(1.5, tier - 5.5))))
                    .tier(1)
                    .isBioUpgrade(1)
                    .buildAndRegister();
        }

        //  Optical Circuit Board (T2)
        for (int tier = 7; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 6.5))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(OPTICAL_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(OPTICAL_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(2)
                    .notConsumable(swarm ,Silver)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Americium, (int) (16 * (Math.sqrt(tier - 6))))
                    .input(foil, GalliumNitride, (int) (16 * (Math.sqrt(tier - 6))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * (Math.sqrt(tier - 6)))))
                    .fluidInputs(EDP.getFluid((int) (8000 * (Math.sqrt(tier - 6)))))
                    .fluidInputs(ElectrolyteReflectorMixture.getFluid((int) (6000 * (Math.sqrt(tier - 6)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier + 1] * 3 / 4)
                    .duration((int) Math.ceil(500 / Math.sqrt(Math.pow(1.5, tier - 6.5))))
                    .tier(2)
                    .isBioUpgrade(1)
                    .buildAndRegister();
        }

        //  Optical Circuit Board (T3)
        for (int tier = 7; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 6))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(OPTICAL_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(OPTICAL_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(3)
                    .notConsumable(swarm, Gold)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Americium, (int) (16 * (Math.sqrt(tier - 6))))
                    .input(foil, GalliumNitride, (int) (16 * (Math.sqrt(tier - 6))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * Math.sqrt(tier - 6))))
                    .fluidInputs(EDP.getFluid((int) (8000 * (Math.sqrt(tier - 6)))))
                    .fluidInputs(ElectrolyteReflectorMixture.getFluid((int) (6000 * (Math.sqrt(tier - 6)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier + 1] * 3 / 4)
                    .duration((int) Math.ceil(400 / Math.sqrt(Math.pow(1.5, tier - 6.5))))
                    .tier(3)
                    .isBioUpgrade(1)
                    .buildAndRegister();
        }

        //  Spintronic Circuit Board (T1)
        for (int tier = 8; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 8))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(SPINTRONIC_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(SPINTRONIC_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Fullerene, (int) (16 * (Math.sqrt(tier - 7))))
                    .input(foil, Phosphorene, (int) (16 * (Math.sqrt(tier - 7))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * Math.sqrt(tier - 7))))
                    .fluidInputs(EDP.getFluid((int) (10000 * (Math.sqrt(tier - 7)))))
                    .fluidInputs(CarbonNanotube.getFluid((int) (8000 * (Math.sqrt(tier - 7)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier] * 3 / 4)
                    .duration((int) Math.ceil(600 / Math.sqrt(Math.pow(1.5, tier - 6.5))))
                    .tier(1)
                    .isBioUpgrade(1)
                    .buildAndRegister();
        }

        //  Spintronic Circuit Board (T2)
        for (int tier = 8; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 7.5))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(SPINTRONIC_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(SPINTRONIC_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(2)
                    .notConsumable(swarm, Silver)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Fullerene, (int) (16 * (Math.sqrt(tier - 7))))
                    .input(foil, Phosphorene, (int) (16 * (Math.sqrt(tier - 7))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * Math.sqrt(tier - 7))))
                    .fluidInputs(EDP.getFluid((int) (10000 * Math.sqrt(tier - 7))))
                    .fluidInputs(CarbonNanotube.getFluid((int) (8000 * (Math.sqrt(tier - 7)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier + 1] * 3 / 4)
                    .duration((int) Math.ceil(500 / Math.sqrt(Math.pow(1.5, tier - 6.5))))
                    .tier(2)
                    .isBioUpgrade(1)
                    .buildAndRegister();
        }

        //  Spintronic Circuit Board (T3)
        for (int tier = 8; tier <= plasticTier; tier++) {
            int boardAmount = (int) Math.ceil(8 * (Math.sqrt(Math.pow(2, tier - 7))));
            List<ItemStack> boards = new ArrayList<>();
            for (int i = boardAmount; i > 64; i -= 64) {
                boards.add(SPINTRONIC_CIRCUIT_BOARD.getStackForm(64));
                boardAmount -= 64;
            }
            boards.add(SPINTRONIC_CIRCUIT_BOARD.getStackForm(boardAmount));
            PCB_FACTORY_RECIPES.recipeBuilder()
                    .circuitMeta(3)
                    .notConsumable(swarm, Gold)
                    .input(plate, plasticTiers.inverse().get(tier))
                    .input(foil, Fullerene, (int) (16 * (Math.sqrt(tier - 7))))
                    .input(foil, Phosphorene, (int) (16 * (Math.sqrt(tier - 7))))
                    .fluidInputs(SulfuricAcid.getFluid((int) (500 * Math.sqrt(tier - 7))))
                    .fluidInputs(EDP.getFluid((int) (10000 * Math.sqrt(tier - 7))))
                    .fluidInputs(CarbonNanotube.getFluid((int) (8000 * (Math.sqrt(tier - 7)))))
                    .outputs(boards.toArray(new ItemStack[0]))
                    .EUt(VA[tier + 1] * 3 / 4)
                    .duration((int) Math.ceil(400 / Math.sqrt(Math.pow(1.5, tier - 6.5))))
                    .tier(3)
                    .isBioUpgrade(1)
                    .buildAndRegister();
        }
    }

    private static void addPlasticTier(Material material, int tier) {
        plasticTiers.put(material, tier);
        plasticTier++;
    }
}