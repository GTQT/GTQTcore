package keqing.gtqtcore.mixin.multiblocks;

import gregicality.multiblocks.api.capability.IParallelMultiblock;
import gregicality.multiblocks.api.capability.impl.GCYMMultiblockRecipeLogic;
import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregicality.multiblocks.common.GCYMConfigHolder;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(GCYMRecipeMapMultiblockController.class)
public abstract  class mixinGCYMRecipeMapMultiblockController extends MultiMapMultiblockController implements IParallelMultiblock {
    public mixinGCYMRecipeMapMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMaps) {
        super(metaTileEntityId, recipeMaps);
        this.recipeMapWorkable = new GCYMMultiblockRecipeLogic(this);
    }
    public boolean isTiered() {
        return GCYMConfigHolder.globalMultiblocks.enableTieredCasings;
    }
    @Override
    public void addInformation(ItemStack stack,  World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqt.tooltip.tiered_hatch_enabled"));
        if (isParallel())
            tooltip.add(I18n.format("gcym.tooltip.parallel_enabled"));
        if (GCYMConfigHolder.globalMultiblocks.enableTieredCasings && isTiered())
            tooltip.add(I18n.format("gcym.tooltip.tiered_hatch_enabled"));
    }
    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("gtqtcore.gcymmultiblock.description")};
    }
}
