package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.GTValues.HV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.Adamantium;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.SodiumAcetate;

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
                .fluidInputs(Helium.getFluid(1000))
                .circuitMeta(2)
                .blastFurnaceTemp(Titanium.getBlastTemperature() + 200)
                .buildAndRegister();


        //纳米钛酸钡
        CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(VA[HV])
                .fluidInputs(Yisuanbei.getFluid(1000))
                .fluidInputs(Xianhuatai.getFluid(1000))
                .output(dust,NanometerBariumTitanate)
                .buildAndRegister();
        //酰化钛+乙酸钡
        CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(VA[HV])
                .fluidInputs(BariumSulfateSuspension.getFluid(2000))
                .fluidInputs(SodiumAcetate.getFluid(2000))
                .fluidOutputs(Yisuanbei.getFluid(2000))
                .fluidOutputs(SodiumPersulfate.getFluid(1000))
                .buildAndRegister();
        //酰化钛
        //钛锭+氧气 电力高炉 == 氧化钛
        BLAST_RECIPES.recipeBuilder().duration(800).EUt(VA[HV])
                .input(ingot, Titanium,1)
                .output(ingot, Yanghuatai)
                .fluidInputs(Oxygen.getFluid(2000))
                .blastFurnaceTemp(Titanium.getBlastTemperature() + 200)
                .buildAndRegister();
        //氧化钛+硫酸 电力高炉 == 酸性钛
        BLAST_RECIPES.recipeBuilder().duration(800).EUt(VA[HV])
                .input(ingot, Yanghuatai,1)
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs(Suanxingtai.getFluid(1000))
                .blastFurnaceTemp(Titanium.getBlastTemperature() + 200)
                .buildAndRegister();
        //酸性钛+乙烯酮 = 含杂酰化钛
        CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(VA[HV])
                .fluidInputs(Ethenone.getFluid(1000))
                .fluidInputs(Suanxingtai.getFluid(1000))
                .fluidOutputs(Hanzaxianhuatai.getFluid(1000))
                .buildAndRegister();
        //含杂酰化钛 + 氢氧化钠 =酰化钛沉淀
        CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(VA[HV])
                .fluidInputs(Hanzaxianhuatai.getFluid(1000))
                .input(dust,SodiumHydroxide,2)
                .output(dust,Xianhuaitaicd)
                .buildAndRegister();
        //酰化钛沉淀 + 硫酸 = 粗制酰化钛
        CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(VA[HV])
                .input(dust,Xianhuaitaicd)
                .fluidInputs(SulfuricAcid.getFluid(2000))
                .fluidOutputs(Cuzhixianhuatai.getFluid(1000))
                .fluidOutputs(SodiumPersulfate.getFluid(1000))
                .buildAndRegister();
        //蒸馏 酰化钛
        DISTILLATION_RECIPES.recipeBuilder().duration(200).EUt(VA[HV])
                .fluidInputs(Cuzhixianhuatai.getFluid(1000))
                .fluidOutputs(Xianhuatai.getFluid(50))
                .fluidOutputs(Xianhuatai.getFluid(50))
                .fluidOutputs(Xianhuatai.getFluid(50))
                .fluidOutputs(Xianhuatai.getFluid(50))
                .fluidOutputs(Xianhuatai.getFluid(50))
                .fluidOutputs(Xianhuatai.getFluid(50))
                .fluidOutputs(Xianhuatai.getFluid(50))
                .fluidOutputs(Xianhuatai.getFluid(50))
                .fluidOutputs(Xianhuatai.getFluid(50))
                .fluidOutputs(Xianhuatai.getFluid(50))
                .fluidOutputs(Xianhuatai.getFluid(50))
                .fluidOutputs(Xianhuatai.getFluid(50))
                .buildAndRegister();
    }

}
