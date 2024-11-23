package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static java.util.Calendar.SECOND;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.DRAGON_CELL;
import static keqing.gtqtcore.common.items.GTQTMetaItems.PRE_DRAGON_CELL;
import static net.minecraft.init.Blocks.DRAGON_EGG;
import static net.minecraft.init.Items.BONE;
import static net.minecraft.init.Items.EGG;

public class DragonChain {

    public static void init() {
        BIO_CENTRIFUGE.recipeBuilder()
                .input(EGG)
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidInputs(Ethanesulphonate.getFluid(200))
                .chancedOutput(PRE_DRAGON_CELL,1000,0)
                .duration(4000).EUt(VA[HV]).buildAndRegister();

        GENE_MUTAGENESIS.recipeBuilder()
                .inputs(new ItemStack(Blocks.DRAGON_EGG))
                .output(PRE_DRAGON_CELL,64)
                .output(PRE_DRAGON_CELL,64)
                .fluidOutputs(DragonBreath.getFluid(400))
                .fluidInputs(DistilledWater.getFluid(16000))
                .fluidInputs(Rnzymes.getFluid(4000))
                .fluidInputs(Ethanesulphonate.getFluid(200))
                .EUt(VA[IV])
                .rate(80)
                .duration(1200 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .input(PRE_DRAGON_CELL)
                .input(dust,Uranium238)
                .input(dust,Endstone,4)
                .fluidInputs(Mutagen.getFluid(1000))
                .chancedOutput(DRAGON_CELL,100,0)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .input(PRE_DRAGON_CELL)
                .input(dust,Thorium)
                .input(dust,Endstone,4)
                .fluidInputs(Mutagen.getFluid(1000))
                .chancedOutput(DRAGON_CELL,400,0)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .input(PRE_DRAGON_CELL)
                .input(dust,Plutonium241)
                .input(dust,Endstone,4)
                .fluidInputs(Mutagen.getFluid(1000))
                .chancedOutput(DRAGON_CELL,1600,0)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .input(DRAGON_CELL,32)
                .input(dust,Meat,64)
                .input(BONE,64)
                .fluidInputs(DNAe.getFluid(1000))
                .fluidInputs(Enzymesda.getFluid(1000))
                .fluidOutputs(DragonBreath.getFluid(200))
                .outputs(new ItemStack(DRAGON_EGG))
                .rate(60)
                .duration(8000).EUt(VA[HV]).buildAndRegister();


        //  Compatibility of Vanilla Dragon Breath (bottle?)
        EXTRACTOR_RECIPES.recipeBuilder()
                .input(Items.DRAGON_BREATH)
                .output(Items.GLASS_BOTTLE)
                .fluidOutputs(DragonBreath.getFluid(100))
                .EUt(VA[HV])
                .duration(2 * SECOND + 10)
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(Items.GLASS_BOTTLE)
                .fluidInputs(DragonBreath.getFluid(100))
                .output(Items.DRAGON_BREATH)
                .EUt(VA[ULV])
                .duration(5 * SECOND)
                .buildAndRegister();

        //  Dragon breath -> Concentrate Dragon Breath
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .notConsumable(new ItemStack(DRAGON_EGG))
                .fluidInputs(DragonBreath.getFluid(200))
                .fluidOutputs(ConcentrateDragonBreath.getFluid(100))
                .EUt(VA[IV])
                .duration(10 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  TODO rebalanced it.
        SONICATION_RECIPES.recipeBuilder()
                .fluidInputs(ConcentrateDragonBreath.getFluid(2000))
                .fluidInputs(Radon.getFluid(1000))
                .chancedOutput(dust, Endstone, 2000, 0)
                .fluidOutputs(DragonBlood.getFluid(1000))
                .EUt(VA[ZPM])
                .duration(20 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }
}