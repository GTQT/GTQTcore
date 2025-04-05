package keqing.gtqtcore.loaders.recipes.circuits;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.Kovar;
import static keqing.gtqtcore.common.items.GTQTMetaItems.VACUUM_TUBE_COMPONENTS;

public class PrimitiveCircuits {

    private static final int MINUTE = 1200;
    private static final int SECOND = 20;
    public static void init() {
        ModHandler.addShapelessRecipe("dust_kovar", OreDictUnifier.get(OrePrefix.dust, Kovar, 6),
                OreDictUnifier.get(OrePrefix.dust, Iron),
                OreDictUnifier.get(OrePrefix.dust, Iron),
                OreDictUnifier.get(OrePrefix.dust, Iron),
                OreDictUnifier.get(OrePrefix.dust, Iron),
                OreDictUnifier.get(OrePrefix.dust, Nickel),
                OreDictUnifier.get(OrePrefix.dust, Nickel),
                OreDictUnifier.get(OrePrefix.dust, Cobalt)
        );

        MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Iron, 4)
                .input(OrePrefix.dust, Nickel, 2)
                .input(OrePrefix.dust, Cobalt)
                .notConsumable(new IntCircuitIngredient(4))
                .output(OrePrefix.dust, Kovar, 7)
                .duration(160).EUt(VA[ULV]).buildAndRegister();

        //普通硅保护气配方
        BLAST_RECIPES.recipeBuilder()
                .duration(800)
                .EUt(120)
                .blastFurnaceTemp(1800)
                .input(dust, Silicon)
                .fluidInputs(Nitrogen.getFluid(1000))
                .circuitMeta(2)
                .output(ingotHot, Silicon, 1)
                .buildAndRegister();

        Recipes();

        //  ULV Glue recipe
        VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .input(STICKY_RESIN, 4)
                .notConsumable(stickLong, Iron)
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(Glue.getFluid(200))
                .EUt((int) V[ULV])
                .duration(MINUTE / 2)
                .buildAndRegister();
    }
    private static void Recipes() {

        //  ULV Glue recipe
        VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .input(STICKY_RESIN, 4)
                .notConsumable(stickLong, Iron)
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(Glue.getFluid(200))
                .EUt((int) V[ULV])
                .duration(MINUTE / 2)
                .buildAndRegister();

        //  Glass Tube -> Vacuum Tube Component
        ModHandler.addShapedRecipe(true, "vacuum_tube_component", VACUUM_TUBE_COMPONENTS.getStackForm(4),
                "FGF", "WWW",
                'F', new UnificationEntry(foil, Tin),
                'W', new UnificationEntry(wireGtSingle, Lead),
                'G', GLASS_TUBE.getStackForm());

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(GLASS_TUBE)
                .input(wireFine, Lead, 2)
                .input(foil, Tin)
                .output(VACUUM_TUBE_COMPONENTS, 2)
                .EUt(VA[ULV])
                .duration(3 * SECOND)
                .buildAndRegister();

        //  Vacuum Tube
        VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .input(VACUUM_TUBE_COMPONENTS)
                .input(ring, Steel, 2)
                .input(wireFine, Copper, 4)
                .fluidInputs(Glue.getFluid(200))
                .output(VACUUM_TUBE, 2)
                .EUt((int) V[ULV])
                .pressure(13E-5)
                .duration(10 * SECOND)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(VACUUM_TUBE_COMPONENTS)
                .input(ring, Steel, 2)
                .input(wireFine, Copper, 4)
                .output(VACUUM_TUBE, 4)
                .fluidInputs(Glue.getFluid(100))
                .EUt(VA[LV])
                .duration(6 * SECOND)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(VACUUM_TUBE_COMPONENTS)
                .input(ring, Steel, 2)
                .input(wireFine, AnnealedCopper, 4)
                .output(VACUUM_TUBE, 4)
                .fluidInputs(Glue.getFluid(100))
                .EUt(VA[LV])
                .duration(6 * SECOND)
                .buildAndRegister();
    }
}
