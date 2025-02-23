package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.unification.OreDictUnifier;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CHEMICAL_PLANT;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.BariumOxide;
import static keqing.gtqtcore.api.unification.TJMaterials.SodiumAcetate;
import static keqing.gtqtcore.common.items.GTQTMetaItems.CATALYST_FRAMEWORK_I;
import static keqing.gtqtcore.common.items.GTQTMetaItems.CATALYST_FRAMEWORK_II;

public class TitanateChain {
    public static void init() {
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, new ItemStack[]{OreDictUnifier.get(dust, Magnesium,2)}, new FluidStack[]{TitaniumTetrachloride.getFluid(1000)});
        //四氯化钛-》海绵钛
        BLAST_RECIPES.recipeBuilder().duration(200).EUt(VA[HV])
                .input(dust, Magnesium, 2)
                .fluidInputs(TitaniumTetrachloride.getFluid(1000))
                .output(ingot, Htitanate)
                .output(dust, MagnesiumChloride, 6)
                .blastFurnaceTemp(Titanium.getBlastTemperature() + 200)
                .buildAndRegister();

        //海绵钛-》热钛
        BLAST_RECIPES.recipeBuilder().duration(2000).EUt(VA[HV])
                .input(ingot, Htitanate,2)
                .output(ingotHot, Titanium)
                .circuitMeta(1)
                .blastFurnaceTemp(Titanium.getBlastTemperature() + 200)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder().duration(1200).EUt(VA[HV])
                .input(ingot, Htitanate,2)
                .output(ingotHot, Titanium)
                .fluidInputs(Helium.getFluid(100))
                .circuitMeta(2)
                .blastFurnaceTemp(Titanium.getBlastTemperature() + 200)
                .buildAndRegister();


        //纳米钛酸钡
        CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(VA[HV])
                .fluidInputs(BariumAcetate.getFluid(1000))
                .fluidInputs(TitaniumAcylate.getFluid(1000))
                .output(dust,NanometerBariumTitanate)
                .buildAndRegister();

        //硫酸钡浊液
        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_I.getStackForm())
                .duration(400).EUt(VA[IV])
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .input(dust, BariumOxide)
                .fluidOutputs(BariumSulfateSuspension.getFluid(1000))
                .buildAndRegister();

        //酰化钛+乙酸钡
        CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(VA[HV])
                .fluidInputs(BariumSulfateSuspension.getFluid(2000))
                .fluidInputs(SodiumAcetate.getFluid(2000))
                .fluidOutputs(BariumAcetate.getFluid(2000))
                .fluidOutputs(SodiumPersulfate.getFluid(1000))
                .buildAndRegister();
        //酰化钛
        //钛锭+氧气 电力高炉 == 氧化钛
        BLAST_RECIPES.recipeBuilder().duration(800).EUt(VA[HV])
                .input(ingot, Titanium,1)
                .output(ingot, TitaniumOxide)
                .fluidInputs(Oxygen.getFluid(2000))
                .blastFurnaceTemp(Titanium.getBlastTemperature() + 200)
                .buildAndRegister();
        //氧化钛+硫酸 电力高炉 == 酸性钛
        BLAST_RECIPES.recipeBuilder().duration(800).EUt(VA[HV])
                .input(ingot, TitaniumOxide,1)
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs(AcidicTitanium.getFluid(1000))
                .blastFurnaceTemp(Titanium.getBlastTemperature() + 200)
                .buildAndRegister();
        //酸性钛+乙烯酮 = 含杂酰化钛
        CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(VA[HV])
                .fluidInputs(Ethenone.getFluid(1000))
                .fluidInputs(AcidicTitanium.getFluid(1000))
                .fluidOutputs(ImpureTitaniumAcylate.getFluid(1000))
                .buildAndRegister();
        //含杂酰化钛 + 氢氧化钠 =酰化钛沉淀
        CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(VA[HV])
                .fluidInputs(ImpureTitaniumAcylate.getFluid(1000))
                .input(dust,SodiumHydroxide,2)
                .output(dust, TitaniumAcylatePrecipitation)
                .buildAndRegister();
        //酰化钛沉淀 + 硫酸 = 粗制酰化钛
        CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(VA[HV])
                .input(dust, TitaniumAcylatePrecipitation)
                .fluidInputs(SulfuricAcid.getFluid(2000))
                .fluidOutputs(CrudeTitaniumAcylate.getFluid(1000))
                .fluidOutputs(SodiumPersulfate.getFluid(1000))
                .buildAndRegister();
        //蒸馏 酰化钛
        DISTILLATION_RECIPES.recipeBuilder().duration(200).EUt(VA[HV])
                .fluidInputs(CrudeTitaniumAcylate.getFluid(1000))
                .fluidOutputs(TitaniumAcylate.getFluid(50))
                .fluidOutputs(TitaniumAcylate.getFluid(50))
                .fluidOutputs(TitaniumAcylate.getFluid(50))
                .fluidOutputs(TitaniumAcylate.getFluid(50))
                .fluidOutputs(TitaniumAcylate.getFluid(50))
                .fluidOutputs(TitaniumAcylate.getFluid(50))
                .fluidOutputs(TitaniumAcylate.getFluid(50))
                .fluidOutputs(TitaniumAcylate.getFluid(50))
                .fluidOutputs(TitaniumAcylate.getFluid(50))
                .fluidOutputs(TitaniumAcylate.getFluid(50))
                .fluidOutputs(TitaniumAcylate.getFluid(50))
                .fluidOutputs(TitaniumAcylate.getFluid(50))
                .buildAndRegister();
    }

}
