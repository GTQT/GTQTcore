package keqing.gtqtcore.loaders.tweak.oc;

import gregtech.api.recipes.RecipeMaps;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static keqing.gtqtcore.common.block.blocks.BlocksResearchSystem.CasingType.COMPUTER_VENT;
import static keqing.gtqtcore.loaders.tweak.oc.index.*;

public class OCGTRecipes {
    public static void init() {
        //完成基本部件的合成
        autoRecipe(memory_t1_5,0);
        autoRecipe(memory_t2_5,1);
        autoRecipe(memory_t3_5,2);

        autoRecipe(basic_central_processor,3);
        autoRecipe(advanced_central_processor,4);
        autoRecipe(super_central_processor,5);

        autoRecipe(basic_graphic_card,6);
        autoRecipe(advanced_graphic_card,7);
        autoRecipe(super_graphic_card,8);
    }
    public static void autoRecipe(ItemStack stack,int i) {
        //完成高级部件的合成
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(stack)
                .inputs(GTQTMetaBlocks.blocksResearchSystem.getItemVariant(COMPUTER_VENT))
                .output(GTQTMetaTileEntities.KQCC_HATCH[i])
                .EUt(VA[MV])
                .duration(400)
                .buildAndRegister();

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .input(GTQTMetaTileEntities.KQCC_HATCH[i])
                .outputs(stack)
                .outputs(GTQTMetaBlocks.blocksResearchSystem.getItemVariant(COMPUTER_VENT))
                .EUt(VA[MV])
                .duration(400)
                .buildAndRegister();
    }
}
