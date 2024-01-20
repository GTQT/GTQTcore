package keqing.gtqtcore.mixin.gregtech;

import gregtech.api.GTValues;
import gregtech.common.blocks.BlockGlassCasing;
import keqing.gtqtcore.api.blocks.ITierGlassBlockState;
import net.minecraft.util.IStringSerializable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockGlassCasing.CasingType.class)
public abstract class MixinGlassCasingType implements IStringSerializable, ITierGlassBlockState {

    @Override
    public boolean isOpticalGlass() {
        return false;
    }

    public int getGlassTier() {
        return switch (getName()) {
            case ("fusion_glass") -> GTValues.UV;
            case ("laminated_glass") -> GTValues.IV;
            case ("tempered_glass") -> GTValues.MV;
            default -> GTValues.HV;
        };
    }
}