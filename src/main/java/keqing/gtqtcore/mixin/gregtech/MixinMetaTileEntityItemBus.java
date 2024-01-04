package keqing.gtqtcore.mixin.gregtech;

import gregtech.api.capability.IControllable;
import gregtech.api.capability.IGhostSlotConfigurable;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityItemBus;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MetaTileEntityItemBus.class)
public abstract class MixinMetaTileEntityItemBus extends MetaTileEntityMultiblockNotifiablePart implements IMultiblockAbilityPart<IItemHandlerModifiable>, IControllable, IGhostSlotConfigurable {


    public MixinMetaTileEntityItemBus(ResourceLocation metaTileEntityId,
                                      int tier,
                                      boolean isExport) {
        super(metaTileEntityId, tier, isExport);
    }

    private int getInventorySize() {
        int sizeRoot = 1 + Math.min(14, this.getTier());
        return sizeRoot * sizeRoot;
    }
}
