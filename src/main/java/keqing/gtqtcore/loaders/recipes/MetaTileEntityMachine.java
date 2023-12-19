package keqing.gtqtcore.loaders.recipes;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;

import static gregtech.common.blocks.BlockBoilerCasing.BoilerCasingType.*;
import static gregtech.common.blocks.BlockFireboxCasing.FireboxCasingType.*;
import static gregtech.common.blocks.BlockHermeticCasing.HermeticCasingsType.*;
import static gregtech.common.blocks.BlockMachineCasing.MachineCasingType.*;
import static gregtech.common.blocks.BlockMetalCasing.MetalCasingType.*;
import static gregtech.common.blocks.BlockMultiblockCasing.MultiblockCasingType.*;
import static gregtech.common.blocks.BlockSteamCasing.SteamCasingType.*;
import static gregtech.common.blocks.BlockTurbineCasing.TurbineCasingType.*;
import static gregtech.common.blocks.BlockWarningSign.SignType.*;
import static gregtech.common.blocks.BlockWarningSign1.SignType.*;
import static gregtech.common.blocks.BlockWireCoil.CoilType.CUPRONICKEL;
import static gregtech.loaders.recipe.CraftingComponent.*;
import static gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe;

public class MetaTileEntityMachine {
    public static void init() {

        registerMachineRecipe(GTQTMetaTileEntities.FLUID_EXTRACTOR, "PGP", "EGE", "CMC", 'M', HULL, 'P',  PUMP, 'E', PISTON, 'C',
                CIRCUIT,  'G', GLASS);
        registerMachineRecipe(GTQTMetaTileEntities.FLUID_CANNER, "EGE", "PGP", "CMC", 'M', HULL, 'P',  PUMP, 'E', PISTON, 'C',
                CIRCUIT,  'G', GLASS);
    }
}
