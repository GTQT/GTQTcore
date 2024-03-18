package keqing.gtqtcore.loaders.recipes.circuits;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.CSilicon;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.electrode;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.items.MetaItems.GLASS_TUBE;
import static gregtech.common.items.MetaItems.VACUUM_TUBE;

public class PrimitiveCircuits {

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

        ModHandler.addShapedRecipe("vacuum_tube_components", VACUUM_TUBE_COMPONENTS.getStackForm(),
                "RGR", "WWW",
                'R', new UnificationEntry(OrePrefix.stick, Steel),
                'G', new UnificationEntry(OrePrefix.dust, Glowstone),
                'W', new UnificationEntry(wireGtSingle, Copper));

        ModHandler.addShapedRecipe("vacuum_tube_components_foil", VACUUM_TUBE_COMPONENTS.getStackForm(),
                "RGR", "WWW",
                'R', new UnificationEntry(OrePrefix.stick, Steel),
                'G', new UnificationEntry(OrePrefix.foil, Gold),
                'W', new UnificationEntry(wireGtSingle, Copper));
        //普通硅保护气配方
        BLAST_RECIPES.recipeBuilder()
                .duration(800)
                .EUt(120)
                .blastFurnaceTemp(1800)
                .input(dust,Silicon)
                .fluidInputs(Nitrogen.getFluid(1000))
                .circuitMeta(2)
                .output(ingotHot, Silicon, 1)
                .buildAndRegister();
        //太阳能硅保护气配方
        BLAST_RECIPES.recipeBuilder()
                .duration(800)
                .EUt(120)
                .blastFurnaceTemp(1800)
                .input(dust,CSilicon)
                .fluidInputs(Nitrogen.getFluid(1000))
                .circuitMeta(2)
                .output(ingotHot, CSilicon, 1)
                .buildAndRegister();


        for (Material copper : new Material[]{Copper, AnnealedCopper}) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(OrePrefix.foil, Gold)
                    .input(OrePrefix.bolt, Steel, 2)
                    .input(wireGtSingle, copper, 2)
                    .circuitMeta(1)
                    .output(VACUUM_TUBE_COMPONENTS, copper == Copper ? 2 : 4)
                    .duration(120).EUt(VA[ULV]).buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(OrePrefix.foil, Gold)
                    .input(OrePrefix.bolt, Steel)
                    .input(wireGtSingle, copper, 2)
                    .fluidInputs(RedAlloy.getFluid(L / 8))
                    .output(VACUUM_TUBE_COMPONENTS, copper == Copper ? 4 : 6)
                    .duration(120).EUt(VA[ULV]).buildAndRegister();
        }


    }
}
