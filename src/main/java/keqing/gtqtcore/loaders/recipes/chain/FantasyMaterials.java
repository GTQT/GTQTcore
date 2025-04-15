package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.TD_PRINT_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.ingotHot;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static supercritical.api.unification.material.SCMaterials.Zircon;

public class FantasyMaterials {

    public static void init() {
        adamantium();
        orichalcum();
        vibranium();
        MetastableOganessonChain();
    }
    private static void MetastableOganessonChain() {
        GTRecipeHandler.removeRecipesByInputs(TD_PRINT_RECIPES,
                new ItemStack[]{SHAPE_MOLD_INGOT.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(144)});

        GTRecipeHandler.removeRecipesByInputs(TD_PRINT_RECIPES,
                new ItemStack[]{SHAPE_MOLD_BLOCK.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(1296)});

        GTRecipeHandler.removeRecipesByInputs(TD_PRINT_RECIPES,
                new ItemStack[]{SHAPE_MOLD_NUGGET.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(144)});

        GTRecipeHandler.removeRecipesByInputs(TD_PRINT_RECIPES,
                new ItemStack[]{SHAPE_MOLD_PLATE.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(144)});

        /*
        GTRecipeHandler.removeRecipesByInputs(TD_PRINT_RECIPES,
                new ItemStack[]{SHAPE_MOLD_ROD_LONG.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(144)});

        GTRecipeHandler.removeRecipesByInputs(TD_PRINT_RECIPES,
                new ItemStack[]{SHAPE_MOLD_RING.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(36)});

        GTRecipeHandler.removeRecipesByInputs(TD_PRINT_RECIPES,
                new ItemStack[]{SHAPE_MOLD_ROD.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(72)});

        GTRecipeHandler.removeRecipesByInputs(TD_PRINT_RECIPES,
                new ItemStack[]{SHAPE_MOLD_BOLT.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(18)});

        GTRecipeHandler.removeRecipesByInputs(TD_PRINT_RECIPES,
                new ItemStack[]{SHAPE_MOLD_SCREW.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(18)});

        GTRecipeHandler.removeRecipesByInputs(TD_PRINT_RECIPES,
                new ItemStack[]{SHAPE_MOLD_ROUND.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(18)});

         */
        /////////////////////////////////////////////////////////////////////

        GTRecipeHandler.removeRecipesByInputs(FLUID_SOLIDFICATION_RECIPES,
                new ItemStack[]{SHAPE_MOLD_INGOT.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(144)});

        GTRecipeHandler.removeRecipesByInputs(FLUID_SOLIDFICATION_RECIPES,
                new ItemStack[]{SHAPE_MOLD_BLOCK.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(1296)});

        GTRecipeHandler.removeRecipesByInputs(FLUID_SOLIDFICATION_RECIPES,
                new ItemStack[]{SHAPE_MOLD_NUGGET.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(144)});

        GTRecipeHandler.removeRecipesByInputs(FLUID_SOLIDFICATION_RECIPES,
                new ItemStack[]{SHAPE_MOLD_PLATE.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(144)});

        /*
        GTRecipeHandler.removeRecipesByInputs(FLUID_SOLIDFICATION_RECIPES,
                new ItemStack[]{SHAPE_MOLD_ROD_LONG.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(144)});

        GTRecipeHandler.removeRecipesByInputs(FLUID_SOLIDFICATION_RECIPES,
                new ItemStack[]{SHAPE_MOLD_RING.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(36)});

        GTRecipeHandler.removeRecipesByInputs(FLUID_SOLIDFICATION_RECIPES,
                new ItemStack[]{SHAPE_MOLD_ROD.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(72)});

        GTRecipeHandler.removeRecipesByInputs(FLUID_SOLIDFICATION_RECIPES,
                new ItemStack[]{SHAPE_MOLD_BOLT.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(18)});

        GTRecipeHandler.removeRecipesByInputs(FLUID_SOLIDFICATION_RECIPES,
                new ItemStack[]{SHAPE_MOLD_SCREW.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(18)});

        GTRecipeHandler.removeRecipesByInputs(FLUID_SOLIDFICATION_RECIPES,
                new ItemStack[]{SHAPE_MOLD_ROUND.getStackForm()},
                new FluidStack[]{MetastableOganesson.getFluid(18)});

         */

        //  Metastable Oganesson liquid -> Metastable Oganesson hot ingot
        VACUUM_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_MOLD_INGOT)
                .fluidInputs(MetastableOganesson.getFluid(L))
                .fluidInputs(Helium.getFluid(FluidStorageKeys.LIQUID, 500))
                .output(ingotHot, MetastableOganesson)
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.GAS, 500))
                .EUt(VA[UV])
                .duration(100)
                .buildAndRegister();
    }

    private static void adamantium() {
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(BedrockGas.getFluid(1000))
                .output(dust, Bedrock)
                .fluidOutputs(Helium3.getFluid(20))
                .duration(100).EUt(1024).buildAndRegister();

        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder()
                .input(dust, Bedrock)
                .chancedOutput(dust, Slate, 7000, 0)
                .chancedOutput(dust, Adamantium, 3000, 500)
                .chancedOutput(dust, Monazite, 2, 3000, 0)
                .chancedOutput(dust, Zircon, 3, 3000, 0)
                .chancedOutput(dust, Graphite, 3, 3000, 0)
                .duration(120).EUt(VA[IV]).buildAndRegister();

        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, Adamantium, 4)
                .fluidInputs(Naquadah.getFluid(L * 4))
                .output(dust, Naquadah)
                .output(dust, Uranium238)
                .fluidOutputs(AdamantiumUnstable.getFluid(L * 4))
                .duration(800).EUt(VA[LuV]).buildAndRegister();

        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Europium.getFluid(16))
                .fluidInputs(AdamantiumUnstable.getFluid(16))
                .fluidOutputs(Adamantium.getPlasma(16))
                .EUToStart(300_000_000)
                .duration(32).EUt(VA[LuV]).buildAndRegister();

        // remove adamantium dust -> hot ingot
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(dust, Adamantium), IntCircuitIngredient.getIntegratedCircuit(1));
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, new ItemStack[]{OreDictUnifier.get(dust, Adamantium), IntCircuitIngredient.getIntegratedCircuit(2)}, new FluidStack[]{Argon.getFluid(50)});

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .notConsumable(MetaItems.SHAPE_MOLD_INGOT)
                .fluidInputs(Adamantium.getPlasma(L))
                .output(ingotHot, Adamantium)
                .duration(200).EUt(VA[IV]).buildAndRegister();
    }

    private static void orichalcum() {
        // Remove CEu Neutronium
        GTRecipeHandler.removeRecipesByInputs(FUSION_RECIPES, Americium.getFluid(128), Naquadria.getFluid(128));

        // replace with orichalcum
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Americium.getFluid(128))
                .fluidInputs(Naquadria.getFluid(128))
                .fluidOutputs(Orichalcum.getFluid(32))
                .EUToStart(600_000_000)
                .duration(200).EUt(VA[LuV] * 3).buildAndRegister();

        AUTOCLAVE_RECIPES.recipeBuilder()
                .input(dust, Orichalcum)
                .fluidInputs(Helium.getPlasma(125))
                .output(dust, OrichalcumEnergized)
                .duration(200).EUt(VA[ZPM]).buildAndRegister();

        // remove orichalcum dust -> hot ingot
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(dust, Orichalcum), IntCircuitIngredient.getIntegratedCircuit(1));
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, new ItemStack[]{OreDictUnifier.get(dust, Orichalcum), IntCircuitIngredient.getIntegratedCircuit(2)}, new FluidStack[]{Argon.getFluid(50)});

        BLAST_RECIPES.recipeBuilder()
                .input(dust, OrichalcumEnergized)
                .notConsumable(new IntCircuitIngredient(1))
                .output(ingotHot, Orichalcum)
                .blastFurnaceTemp(9000)
                .duration(2000).EUt(VA[UV]).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust, OrichalcumEnergized)
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(Argon.getFluid(50))
                .output(ingotHot, Orichalcum)
                .blastFurnaceTemp(9000)
                .duration(1000).EUt(VA[UV]).buildAndRegister();
    }

    private static void vibranium() {

        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder()
                .input(dust, DeepIron, 4)
                .output(dust, Iron, 2)
                .output(dust, Trinium)
                .output(dust, Indium)
                .duration(600).EUt(VA[IV]).buildAndRegister();

        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, AdamantiumEnriched, 4)
                .fluidInputs(NaquadahEnriched.getFluid(L * 4))
                .output(dust, NaquadahEnriched, 2)
                .output(dust, Plutonium239)
                .fluidOutputs(VibraniumUnstable.getFluid(L * 4))
                .duration(1600).EUt(VA[ZPM]).buildAndRegister();

        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Orichalcum.getFluid(L))
                .fluidInputs(VibraniumUnstable.getFluid(L))
                .fluidOutputs(Vibranium.getPlasma(L))
                .EUToStart(620_000_000)
                .duration(64).EUt(VA[ZPM] * 2).buildAndRegister();

        // remove vibranium dust -> hot ingot
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(dust, Vibranium), IntCircuitIngredient.getIntegratedCircuit(1));
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, new ItemStack[]{OreDictUnifier.get(dust, Vibranium), IntCircuitIngredient.getIntegratedCircuit(2)}, new FluidStack[]{Argon.getFluid(50)});

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .notConsumable(MetaItems.SHAPE_MOLD_INGOT)
                .fluidInputs(Vibranium.getPlasma(L))
                .output(ingotHot, Vibranium)
                .duration(400).EUt(500_000).buildAndRegister();
    }
}
