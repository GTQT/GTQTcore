package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityEnergyHatch;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class MetaTileEntityAdvancedEnergyHatch extends MetaTileEntityEnergyHatch implements IMultiblockAbilityPart<IEnergyContainer> {

    public MetaTileEntityAdvancedEnergyHatch(ResourceLocation metaTileEntityId,
                                             int tier,
                                             int amperage,
                                             boolean isExport) {
        super(metaTileEntityId, tier, amperage, isExport);
    }

    @Override
    public void getSubItems(CreativeTabs creativeTab, NonNullList<ItemStack> subItems) {
        for (MetaTileEntityEnergyHatch hatch : GTQTMetaTileEntities.INPUT_ENERGY_HATCH_4A) {
            if (hatch != null)
                subItems.add(hatch.getStackForm());
        }

        for (MetaTileEntityEnergyHatch hatch : GTQTMetaTileEntities.INPUT_ENERGY_HATCH_16A) {
            if (hatch != null)
                subItems.add(hatch.getStackForm());
        }

        for (MetaTileEntityEnergyHatch hatch : GTQTMetaTileEntities.OUTPUT_ENERGY_HATCH_4A) {
            if (hatch != null)
                subItems.add(hatch.getStackForm());
        }

        for (MetaTileEntityEnergyHatch hatch : GTQTMetaTileEntities.OUTPUT_ENERGY_HATCH_16A) {
            if (hatch != null)
                subItems.add(hatch.getStackForm());
        }
    }
}