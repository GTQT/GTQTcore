package keqing.gtqtcore.common.items;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.items.metaitem.ElectricStats;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.StandardMetaItem;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static keqing.gtqtcore.common.CommonProxy.GTQTCore_TO;

public class GTQTBattery extends StandardMetaItem {
    public GTQTBattery() {
        this.setRegistryName("gtqt_battery");
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
    }

    @Override
    public void registerSubItems() {
        GTQTMetaItems.BATTERY_NIMH = addItem(1, "nickel.metal.hydride.battery").addComponents(ElectricStats.createRechargeableBattery(7200000, GTValues.EV)).setModelAmount(8).setCreativeTabs(GTQTCore_TO);

        GTQTMetaItems.BATTERY_SMALL_LITHIUM_ION = addItem(2, "small.lithium.ion.battery").addComponents(ElectricStats.createRechargeableBattery(28800000, GTValues.IV)).setModelAmount(8).setCreativeTabs(GTQTCore_TO);
        GTQTMetaItems.BATTERY_MEDIUM_LITHIUM_ION = addItem(3, "medium.lithium.ion.battery").addComponents(ElectricStats.createRechargeableBattery(115200000, GTValues.LuV)).setModelAmount(8).setCreativeTabs(GTQTCore_TO);
        GTQTMetaItems.BATTERY_LARGE_LITHIUM_ION = addItem(4, "large.lithium.ion.battery").addComponents(ElectricStats.createRechargeableBattery(460800000, GTValues.ZPM)).setModelAmount(8).setCreativeTabs(GTQTCore_TO);

        GTQTMetaItems.BATTERY_SMALL_LIS = addItem(5, "small.lithium.sulfide.battery").addComponents(ElectricStats.createRechargeableBattery(1843200000, GTValues.UV)).setModelAmount(8).setCreativeTabs(GTQTCore_TO);
        GTQTMetaItems.BATTERY_MEDIUM_LIS = addItem(6, "medium.lithium.sulfide.battery").addComponents(ElectricStats.createRechargeableBattery(7372800000L, GTValues.UHV)).setModelAmount(8).setCreativeTabs(GTQTCore_TO);
        GTQTMetaItems.BATTERY_LARGE_LIS = addItem(7, "large.lithium.sulfide.battery").addComponents(ElectricStats.createRechargeableBattery(29491200000L, GTValues.UEV)).setModelAmount(8).setCreativeTabs(GTQTCore_TO);

        GTQTMetaItems.BATTERY_SMALL_FLUORIDE = addItem(8, "small.fluoride.battery").addComponents(ElectricStats.createRechargeableBattery(117964800000L, GTValues.UIV)).setModelAmount(8).setCreativeTabs(GTQTCore_TO);
        GTQTMetaItems.BATTERY_MEDIUM_FLUORIDE = addItem(9, "medium.fluoride.battery").addComponents(ElectricStats.createRechargeableBattery(471859200000L, GTValues.UXV)).setModelAmount(8).setCreativeTabs(GTQTCore_TO);
        GTQTMetaItems.BATTERY_LARGE_FLUORIDE = addItem(10, "large.fluoride.battery").addComponents(ElectricStats.createRechargeableBattery(1887436800000L, GTValues.OpV)).setModelAmount(8).setCreativeTabs(GTQTCore_TO);

        GTQTMetaItems.ULTIMATE_BATTERY_MK2 = this.addItem(11, "battery.uev.ultimate")
                .addComponents(ElectricStats.createRechargeableBattery(Long.MAX_VALUE, GTValues.UEV))
                .setUnificationData(OrePrefix.battery, MarkerMaterials.Tier.UEV)
                .setModelAmount(8)
                .setCreativeTabs(GTQTCore_TO);

        GTQTMetaItems.ULTIMATE_BATTERY_MK3 = this.addItem(12, "battery.uiv.ultimate")
                .addComponents(ElectricStats.createRechargeableBattery(Long.MAX_VALUE, GTValues.UIV))
                .setUnificationData(OrePrefix.battery, MarkerMaterials.Tier.UIV)
                .setModelAmount(8)
                .setCreativeTabs(GTQTCore_TO);

        GTQTMetaItems.ULTIMATE_BATTERY_MK4 = this.addItem(13, "battery.uxv.ultimate")
                .addComponents(ElectricStats.createRechargeableBattery(Long.MAX_VALUE, GTValues.UXV))
                .setUnificationData(OrePrefix.battery, MarkerMaterials.Tier.UXV)
                .setModelAmount(8)
                .setCreativeTabs(GTQTCore_TO);

        GTQTMetaItems.ULTIMATE_BATTERY_MK5 = this.addItem(14, "battery.opv.ultimate")
                .addComponents(ElectricStats.createRechargeableBattery(Long.MAX_VALUE, GTValues.OpV))
                .setUnificationData(OrePrefix.battery, MarkerMaterials.Tier.OpV)
                .setModelAmount(8)
                .setCreativeTabs(GTQTCore_TO);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> lines, ITooltipFlag tooltipFlag) {
        MetaItem<?>.MetaValueItem item = getItem(itemStack);
        if (item == null) return;
        String unlocalizedTooltip = "metaitem." + item.unlocalizedName + ".tooltip";
        if (I18n.hasKey(unlocalizedTooltip)) {
            lines.addAll(Arrays.asList(I18n.format(unlocalizedTooltip).split("/n")));
        }

        IElectricItem electricItem = itemStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        if (electricItem != null) {
            lines.add(I18n.format("metaitem.generic.electric_item.tooltip",
                    electricItem.getCharge(),
                    electricItem.getMaxCharge(),
                    GTValues.VN[electricItem.getTier()]));
        }

        IFluidHandlerItem fluidHandler = ItemHandlerHelper.copyStackWithSize(itemStack, 1)
                .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if (fluidHandler != null) {
            IFluidTankProperties fluidTankProperties = fluidHandler.getTankProperties()[0];
            FluidStack fluid = fluidTankProperties.getContents();
            if (fluid != null) {
                lines.add(I18n.format("metaitem.generic.fluid_container.tooltip",
                        fluid.amount,
                        fluidTankProperties.getCapacity(),
                        fluid.getLocalizedName()));
            } else lines.add(I18n.format("metaitem.generic.fluid_container.tooltip_empty"));
        }

        for (IItemBehaviour behaviour : getBehaviours(itemStack)) {
            behaviour.addInformation(itemStack, lines);
        }
    }
}
