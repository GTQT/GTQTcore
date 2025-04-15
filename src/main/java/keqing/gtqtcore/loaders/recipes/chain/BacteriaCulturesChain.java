package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Milk;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.common.items.MetaItems.PETRI_DISH;
import static gregtech.common.items.MetaItems.SHAPE_MOLD_CYLINDER;
import static gregtechfoodoption.GTFOMaterialHandler.Blood;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Polyetheretherketone;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class BacteriaCulturesChain {
    public static void init() {

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(160).EUt(480)
                .fluidInputs(Polystyrene.getFluid(L / 4))
                .notConsumable(SHAPE_MOLD_CYLINDER.getStackForm())
                .outputs(PETRI_DISH.getStackForm())
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder().EUt(7680).duration(400)
                .input(wireFine, Naquadah)
                .fluidInputs(Polyetheretherketone.getFluid(72))
                .inputs(STERILIZED_PETRI_DISH.getStackForm())
                .outputs(ELECTRICALLY_WIRED_PETRI_DISH.getStackForm())
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .buildAndRegister();

        AUTOCLAVE_RECIPES.recipeBuilder()
                .inputs(PETRI_DISH.getStackForm())
                .fluidInputs(Steam.getFluid(1000))
                .outputs(STERILIZED_PETRI_DISH.getStackForm())
                .cleanroom(CleanroomType.CLEANROOM)
                .EUt(7680)
                .duration(25)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder().EUt(480).duration(400)
                .inputs(STERILIZED_PETRI_DISH.getStackForm())
                .input(dust, Agar)
                .fluidInputs(BacterialGrowthMedium.getFluid(1000))
                .outputs(CLEAN_CULTURE.getStackForm())
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder().EUt(7680).duration(2400)
                .input(GREEN_ALGAE)
                .inputs(CLEAN_CULTURE.getStackForm())
                .fluidInputs(BacterialGrowthMedium.getFluid(1000))
                .outputs(SHEWANELLA_CULTURE.getStackForm())
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder().EUt(7680).duration(2400)
                .inputs(new ItemStack(Blocks.DIRT))
                .inputs(CLEAN_CULTURE.getStackForm())
                .fluidInputs(BacterialGrowthMedium.getFluid(1000))
                .outputs(BREVIBACTERIUM_CULTURE.getStackForm())
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder().EUt(7680).duration(2400)
                .inputs(new ItemStack(Blocks.DIRT, 1, 1))
                .inputs(CLEAN_CULTURE.getStackForm())
                .fluidInputs(BacterialGrowthMedium.getFluid(1000))
                .outputs(CUPRIVADUS_CULTURE.getStackForm())
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder().EUt(7680).duration(2400)
                .inputs(new ItemStack(Items.BEEF))
                .inputs(CLEAN_CULTURE.getStackForm())
                .fluidInputs(BacterialGrowthMedium.getFluid(1000))
                .outputs(ESCHERICHIA_CULTURE.getStackForm())
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder().EUt(7680).duration(2400)
                .fluidInputs(Milk.getFluid(1000))
                .inputs(CLEAN_CULTURE.getStackForm())
                .fluidInputs(BacterialGrowthMedium.getFluid(1000))
                .outputs(BIFIDOBACTERIUM_CULTURE.getStackForm())
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder().EUt(7680).duration(2400)
                .inputs(new ItemStack(Items.ROTTEN_FLESH))
                .inputs(CLEAN_CULTURE.getStackForm())
                .fluidInputs(BacterialGrowthMedium.getFluid(1000))
                .outputs(STREPTOCOCCUS_CULTURE.getStackForm())
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder().EUt(7680).duration(200)
                .inputs(SHEWANELLA_CULTURE.getStackForm())
                .fluidInputs(BacterialGrowthMedium.getFluid(1000))
                .output(dust, Shewanella)
                .fluidOutputs(DepletedGrowthMedium.getFluid(1000))
                .outputs(CONTAMINATED_PETRI_DISH.getStackForm())
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();


        BIOLOGICAL_REACTION_RECIPES.recipeBuilder().EUt(7680).duration(200)
                .inputs(STREPTOCOCCUS_CULTURE.getStackForm())
                .fluidInputs(BacterialGrowthMedium.getFluid(1000))
                .output(dust, StreptococcusPyogenes)
                .fluidOutputs(DepletedGrowthMedium.getFluid(1000))
                .outputs(CONTAMINATED_PETRI_DISH.getStackForm())
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder().EUt(7680).duration(200)
                .inputs(BIFIDOBACTERIUM_CULTURE.getStackForm())
                .fluidInputs(BacterialGrowthMedium.getFluid(1000))
                .output(dust, BifidobacteriumBreve)
                .fluidOutputs(DepletedGrowthMedium.getFluid(1000))
                .outputs(CONTAMINATED_PETRI_DISH.getStackForm())
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder().EUt(7680).duration(200)
                .inputs(ESCHERICHIA_CULTURE.getStackForm())
                .fluidInputs(BacterialGrowthMedium.getFluid(1000))
                .output(dust, EschericiaColi)
                .fluidOutputs(DepletedGrowthMedium.getFluid(1000))
                .outputs(CONTAMINATED_PETRI_DISH.getStackForm())
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder().EUt(7680).duration(200)
                .inputs(BREVIBACTERIUM_CULTURE.getStackForm())
                .fluidInputs(BacterialGrowthMedium.getFluid(1000))
                .output(dust, BrevibacteriumFlavium)
                .fluidOutputs(DepletedGrowthMedium.getFluid(1000))
                .outputs(CONTAMINATED_PETRI_DISH.getStackForm())
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder().EUt(7680).duration(200)
                .inputs(CUPRIVADUS_CULTURE.getStackForm())
                .fluidInputs(BacterialGrowthMedium.getFluid(1000))
                .output(dust, CupriavidusNecator)
                .fluidOutputs(DepletedGrowthMedium.getFluid(1000))
                .outputs(CONTAMINATED_PETRI_DISH.getStackForm())
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        copy(Shewanella);
        copy(BrevibacteriumFlavium);
        copy(EschericiaColi);
        copy(StreptococcusPyogenes);
        copy(BifidobacteriumBreve);
        copy(CupriavidusNecator);


        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(Blood.getFluid(1000))
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidOutputs(BacterialGrowthMedium.getFluid(2000))
                .EUt(7680)
                .duration(400).cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder().EUt(7680).duration(400)
                .Catalyst(CATALYST_FRAMEWORK_IV.getStackForm())
                .recipeLevel(4)
                .fluidInputs(BacterialGrowthMedium.getFluid(10))
                .fluidOutputs(DepletedGrowthMedium.getFluid(10))
                .fluidInputs(Blood.getFluid(50))
                .fluidOutputs(Blood.getFluid(500))
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_IV.getStackForm())
                .recipeLevel(4)
                .fluidInputs(DragonBlood.getFluid(10))
                .fluidInputs(DistilledWater.getFluid(5000))
                .fluidOutputs(BacterialGrowthMedium.getFluid(10000))
                .EUt(VA[5])
                .duration(200)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(DragonBlood.getFluid(10))
                .input(dust,Meat,8)
                .fluidInputs(DistilledWater.getFluid(5000))
                .fluidOutputs(BacterialGrowthMedium.getFluid(10000))
                .rate(10)
                .EUt(VA[4])
                .duration(200)
                .buildAndRegister();

        FERMENTING_RECIPES.recipeBuilder()
                .fluidInputs(DepletedGrowthMedium.getFluid(1000))
                .fluidOutputs(FermentedBiomass.getFluid(1000))
                .EUt(30)
                .duration(400)
                .buildAndRegister();

    }
    public static void copy(Material material)
    {
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder().EUt(7680).duration(400)
                .notConsumable(dust, material)
                .chancedOutput(dust, material,5000,0)
                .chancedOutput(dust, material,5000,0)
                .chancedOutput(dust, material,5000,0)
                .fluidInputs(BacterialGrowthMedium.getFluid(250))
                .fluidOutputs(DepletedGrowthMedium.getFluid(250))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }
}
