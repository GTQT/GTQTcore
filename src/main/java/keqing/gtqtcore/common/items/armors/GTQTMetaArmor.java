package keqing.gtqtcore.common.items.armors;

import gregtech.api.GregTechAPI;
import gregtech.api.items.armor.ArmorMetaItem;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;

public class GTQTMetaArmor  extends ArmorMetaItem<ArmorMetaItem<?>.ArmorMetaValueItem> {
    public GTQTMetaArmor() {
        this.setRegistryName("gtqt_armor");
        setCreativeTab(GregTechAPI.TAB_GREGTECH_TOOLS);
    }
    @Override
    public void registerSubItems() {
        GTQTMetaItems.PISTON_BOOTS = addItem(1, "piston_boots")
                .setArmorLogic(new PistonBoots(EntityEquipmentSlot.FEET, 1024, 80_000L, 1))
                .setRarity(EnumRarity.COMMON);
    }
}