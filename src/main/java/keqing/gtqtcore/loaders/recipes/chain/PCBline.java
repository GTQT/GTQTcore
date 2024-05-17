package keqing.gtqtcore.loaders.recipes.chain;

import com.google.common.collect.HashBiMap;
import gregtech.api.unification.material.Material;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.PCB_FACTORY_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.Polyetheretherketone;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.swarm;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class PCBline {
    private static final HashBiMap<Material, Integer> plasticTiers = HashBiMap.create();
    public static int plasticTier = 0;

    public static void init() {

        //  Add Plastics to {@link #plasticTiers},
        //  used to auto-generated all PCB Factory recipes.
        addPlasticTier(Polyethylene, 1);
        addPlasticTier(PolyvinylChloride, 2);
        addPlasticTier(Polytetrafluoroethylene, 3);
        addPlasticTier(Epoxy, 4);
        addPlasticTier(ReinforcedEpoxyResin, 5);
        addPlasticTier(Polybenzimidazole, 6);
        addPlasticTier(KaptonK, 7);
        addPlasticTier(KaptonE, 8);
        addPlasticTier(Polyetheretherketone, 9);
        addPlasticTier(Kevlar, 10);
        addPlasticTier(Zylon, 11);
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