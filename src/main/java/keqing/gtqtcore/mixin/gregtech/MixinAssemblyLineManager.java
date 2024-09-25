package keqing.gtqtcore.mixin.gregtech;

import gregtech.api.util.AssemblyLineManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AssemblyLineManager.class)
public abstract class MixinAssemblyLineManager {

    @ModifyConstant(method = "getDefaultResearchStationItem", constant = @Constant(intValue = 32))
    private static int modifyThreshold(int original) {
        return 256; // 将阈值改为 512
    }

}
