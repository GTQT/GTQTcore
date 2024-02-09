package keqing.gtqtcore.mixin.gregtech;

import gregtech.api.capability.IControllable;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.machines.IResearchRecipeMap;
import gregtech.api.util.AssemblyLineManager;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityDataBank;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityDataAccessHatch;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityFluidHatch;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.IFluidTank;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Mixin(MetaTileEntityDataAccessHatch.class)
public abstract class MixinMetaTileEntityDataAccessHatch extends MetaTileEntityMultiblockNotifiablePart implements IMultiblockAbilityPart<IFluidTank>, IControllable {
    private final Set<Recipe> recipes;
    private final boolean isCreative;
    public MixinMetaTileEntityDataAccessHatch(ResourceLocation metaTileEntityId, int tier, boolean isCreative) {
        super(metaTileEntityId, tier, false);
        this.isCreative = isCreative;
        this.recipes = (Set)(isCreative ? Collections.emptySet() : new ObjectOpenHashSet());
        this.rebuildData(this.getController() instanceof MetaTileEntityDataBank);
    }
    private void rebuildData(boolean isDataBank) {
        if (!this.isCreative && this.getWorld() != null && !this.getWorld().isRemote) {
            this.recipes.clear();

            for(int i = 0; i < this.importItems.getSlots(); ++i) {
                ItemStack stack = this.importItems.getStackInSlot(i);
                String researchId = AssemblyLineManager.readResearchId(stack);
                boolean isValid = AssemblyLineManager.isStackDataItem(stack, isDataBank);
                if (researchId != null && isValid) {
                    Collection<Recipe> collection = ((IResearchRecipeMap) RecipeMaps.ASSEMBLY_LINE_RECIPES).getDataStickEntry(researchId);
                    if (collection != null) {
                        this.recipes.addAll(collection);
                    }
                }
            }

        }
    }

    protected int getInventorySize() {
        switch (this.getTier())
        {
            case (2) -> {
                return 4;
            }
            case (4) -> {
                return 9;
            }
            case (6) -> {
                return 16;
            }
            case (8) -> {
                return 25;
            }
            default -> {return 1;}
        }
    }
}
