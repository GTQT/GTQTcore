package keqing.gtqtcore.loaders.recipes.chain;

import gregicality.science.api.recipes.GCYSRecipeMaps;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;

import static gregicality.science.api.unification.materials.GCYSMaterials.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtechfoodoption.GTFOMaterialHandler.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.electrode;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
public class CPULine {
    public static void init() {
        Pre();          //基板
        Silicon();      //晶圆
    }
    private static void Silicon()
    {
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Copper)
                .fluidInputs(Chlorine.getFluid(2000))
                .output(dust,CopperCl)
                .duration(200)
                .EUt(30)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust,CopperCl)
                .fluidInputs(Trichlorosilane.getFluid(4000))
                .fluidOutputs(Silane.getFluid(1000))
                .fluidOutputs(SiliconTetrachloride.getFluid(3000))
                .duration(200)
                .EUt(30)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(SiliconTetrachloride.getFluid(1000))
                .input(dust,Sodium,4)
                .fluidOutputs(CSilicon.getFluid(1000))
                .output(dust,Salt,4)
                .duration(200)
                .EUt(30)
                .buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
                .fluidInputs(SiliconTetrachloride.getFluid(1000))
                .fluidOutputs(Chlorine.getFluid(4000))
                .fluidOutputs(CSilicon.getFluid(1000))
                .duration(200)
                .EUt(30)
                .buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
                .fluidInputs(Silane.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(4000))
                .fluidOutputs(CSilicon.getFluid(1000))
                .duration(200)
                .EUt(30)
                .buildAndRegister();

        GCYSRecipeMaps.CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(480)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,GalliumArsenide,1)
                .input(dust,Boron,1)
                .output(SILICON_BOULE)
                .buildAndRegister();

        GCYSRecipeMaps.CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(1960)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,GalliumArsenide,2)
                .input(dust,Phosphorus,1)
                .output(PHOSPHORUS_BOULE)
                .buildAndRegister();

        GCYSRecipeMaps.CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(7960)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,GalliumArsenide,4)
                .input(dust,Naquadah,1)
                .output(NAQUADAH_BOULE)
                .buildAndRegister();

        GCYSRecipeMaps.CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(30720)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,GalliumArsenide,8)
                .input(dust,Neutronium,1)
                .output(NEUTRONIUM_BOULE)
                .buildAndRegister();

        GCYSRecipeMaps.CZPULLER_RECIPES.recipeBuilder()
                .duration(2500)
                .EUt(480)
                .fluidInputs(Helium.getFluid(4000))
                .input(block,CSilicon,32)
                .input(dust,IndiumGalliumPhosphide,1)
                .input(dust,Boron,1)
                .output(SILICON_BOULE,2)
                .buildAndRegister();

        GCYSRecipeMaps.CZPULLER_RECIPES.recipeBuilder()
                .duration(2500)
                .EUt(1960)
                .fluidInputs(Helium.getFluid(4000))
                .input(block,CSilicon,32)
                .input(dust,IndiumGalliumPhosphide,2)
                .input(dust,Phosphorus,1)
                .output(PHOSPHORUS_BOULE,2)
                .buildAndRegister();

        GCYSRecipeMaps.CZPULLER_RECIPES.recipeBuilder()
                .duration(2500)
                .EUt(7680)
                .fluidInputs(Helium.getFluid(4000))
                .input(block,CSilicon,32)
                .input(dust,IndiumGalliumPhosphide,4)
                .input(dust,Naquadah,1)
                .output(NAQUADAH_BOULE,2)
                .buildAndRegister();

        GCYSRecipeMaps.CZPULLER_RECIPES.recipeBuilder()
                .duration(2500)
                .EUt(30720)
                .fluidInputs(Helium.getFluid(4000))
                .input(block,CSilicon,32)
                .input(dust,IndiumGalliumPhosphide,8)
                .input(dust,Neutronium,1)
                .output(NEUTRONIUM_BOULE,2)
                .buildAndRegister();
    }
    private static void Pre()
    {
        //石墨电极线
        //石墨+沥青=浸渍石墨
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(2000)
                .EUt(8)
                .input(stick,Graphite,16)
                .fluidInputs(HighlyPurifiedCoalTar.getFluid(100))
                .output(IMPREGNATED_GRAPHITE_RODS)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .input(IMPREGNATED_GRAPHITE_RODS)
                .input(dust,Graphite,16)
                .fluidInputs(Asphalt.getFluid(2000))
                .output(IMPREGNATED_GRAPHITE_RODSA)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .duration(2000)
                .EUt(120)
                .blastFurnaceTemp(1800)
                .input(IMPREGNATED_GRAPHITE_RODSA)
                .input(dust,Diamond)
                .fluidInputs(Nitrogen.getFluid(1000))
                .output(electrode,Graphite)
                .buildAndRegister();

        //酚醛基板
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .circuitMeta(1)
                .fluidInputs(Phenol.getFluid(1000))
                .fluidInputs(Formaldehyde.getFluid(1000))
                .fluidOutputs(Phenolic.getFluid(2000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .circuitMeta(1)
                .notConsumable(dust,Silver,16)
                .fluidInputs(Methanol.getFluid(1000))
                .fluidOutputs(Formaldehyde.getFluid(100))
                .fluidOutputs(Hydrogen.getFluid(200))
                .buildAndRegister();

        //酚醛基板简化版
        //防腐木板+胶水（浸渍基板）+酚醛
        CUTTER_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .input(plank, TreatedWood)
                .output(plate,TreatedWood)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .input(plate,TreatedWood,4)
                .input(plate,Copper,2)
                .fluidInputs(RedAlloy.getFluid(576))
                .fluidInputs(Glue.getFluid(400))
                .output(IMPREGNATED_SUBSTRATE,4)
                .buildAndRegister();

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .input(IMPREGNATED_SUBSTRATE)
                .fluidInputs(Phenolic.getFluid(250))
                .output(MetaItems.PHENOLIC_BOARD)
                .buildAndRegister();



        //这里是MV阶段的热固化树脂+玻璃纤维=塑料基板的产线
        //这里是玻璃纤维
        MIXER_RECIPES.recipeBuilder()
                .duration(2000)
                .EUt(120)
                .input(dust,QuartzSand)
                .input(dust,Quicklime)
                .input(dust,Uvarovite)
                .input(dust,Pyrope)
                .output(dust,Fiberglass,4)
                .buildAndRegister();

        FLUID_EXTRACTOR_RECIPES.recipeBuilder()
                .duration(20)
                .EUt(120)
                .fluidInputs(Fiberglass.getFluid(288))
                .output(wireFine,Fiberglass,16)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(120)
                .input(wireFine,Fiberglass,4)
                .input(foil,Copper,2)
                .fluidInputs(Epoxy.getFluid(576))
                .fluidInputs(Polyethylene.getFluid(400))
                .output(IMPREGNATED_PLASTIC_SUBSTRATE,4)
                .buildAndRegister();

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(120)
                .input(IMPREGNATED_PLASTIC_SUBSTRATE)
                .fluidInputs(SulfuricAcid.getFluid(250))
                .output(PLASTIC_BOARD)
                .buildAndRegister();
    }
}
