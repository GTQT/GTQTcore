package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.Bronze;
import static gregtech.api.unification.material.Materials.WroughtIron;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gtqt.api.util.MaterialHelper.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.motor_stick;
import static keqing.gtqtcore.common.block.GTQTMetaBlocks.PRESSURE_PIPES;

public class PressurePipeHandler {
    public static void init() {
        ItemStack pipe0 = new ItemStack(PRESSURE_PIPES[0], 2);
        ModHandler.addShapedRecipe("pressure_pipes_0", pipe0,
                "ShS", "PPP", "SfS",
                'P', new UnificationEntry(pipeNormalFluid, Bronze),
                'S', new UnificationEntry(screw, WroughtIron));

        ItemStack pipe7 = new ItemStack(PRESSURE_PIPES[7], 2);
        ModHandler.addShapedRecipe("pressure_pipes_7", pipe7,
                "SfS", "PPP", "ShS",
                'P', new UnificationEntry(pipeNormalFluid, Bronze),
                'S', new UnificationEntry(screw, WroughtIron));


        for(int i=0;i<7;i++) {
            ItemStack vacuumPipe = new ItemStack(PRESSURE_PIPES[i], 2);
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(OrePrefix.pipeNormalFluid,Pipe.get(i))
                    .input(OrePrefix.screw, Plate.get(i),4)
                    .circuitMeta(15)
                    .fluidInputs(Plastic.get(i).getFluid(L * 4))
                    .outputs(vacuumPipe)
                    .EUt(VA[i])
                    .duration(200)
                    .buildAndRegister();

            ItemStack pressurePipe = new ItemStack(PRESSURE_PIPES[7+i], 2);
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(OrePrefix.pipeNormalFluid,Pipe.get(i))
                    .input(OrePrefix.screw, Plate.get(i),4)
                    .circuitMeta(16)
                    .fluidInputs(Plastic.get(i).getFluid(L * 4))
                    .outputs(pressurePipe)
                    .EUt(VA[i])
                    .duration(200)
                    .buildAndRegister();
        }
    }
}
