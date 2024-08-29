package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntitySubstationEnergyHatch;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class MetaTileEntityAdvancedSubstationEnergyHatch extends MetaTileEntitySubstationEnergyHatch {

    public MetaTileEntityAdvancedSubstationEnergyHatch(ResourceLocation metaTileEntityId,
                                                       int tier,
                                                       int amperage,
                                                       boolean isExport) {
        super(metaTileEntityId, tier, amperage, isExport);
    }

    @Override
    public void getSubItems(CreativeTabs creativeTab, NonNullList<ItemStack> subItems) {
        for (MetaTileEntitySubstationEnergyHatch hatch : GTQTMetaTileEntities.SUBSTATION_INPUT_ENERGY_HATCH) {
            if (hatch != null)
                subItems.add(hatch.getStackForm());
        }

        for (MetaTileEntitySubstationEnergyHatch hatch : GTQTMetaTileEntities.SUBSTATION_OUTPUT_ENERGY_HATCH) {
            if (hatch != null)
                subItems.add(hatch.getStackForm());
        }
    }
}
