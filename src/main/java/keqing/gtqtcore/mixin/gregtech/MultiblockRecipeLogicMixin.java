package keqing.gtqtcore.mixin.gregtech;

import keqing.gtqtcore.GTQTCoreConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.RecipeMap;

/**
 * Part of <a href="https://github.com/GregTechCEu/GregTech/pull/2646">GTCEu #2646</a> impl.
 * <p>
 * Also cancels the muffler hatch operations.
 */
@Mixin(value = MultiblockRecipeLogic.class, remap = false)
public abstract class MultiblockRecipeLogicMixin extends AbstractRecipeLogic {

    /**
     * Mandatory Ignored Constructor
     */
    public MultiblockRecipeLogicMixin(MetaTileEntity tileEntity, RecipeMap<?> recipeMap) {
        super(tileEntity, recipeMap);
    }

    @Inject(method = "performMufflerOperations", at = @At("HEAD"), cancellable = true)
    private void skipMufflerOperations(CallbackInfo ci) {
        if (GTQTCoreConfig.MachineSwitch.enableDummyMufflers) {
            ci.cancel();
        }
    }
}