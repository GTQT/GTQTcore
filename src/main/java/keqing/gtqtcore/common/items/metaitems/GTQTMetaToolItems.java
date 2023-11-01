package keqing.gtqtcore.common.items.metaitems;

import gregtech.api.GTValues;
import gregtech.api.items.toolitem.IGTTool;
import gregtech.api.items.toolitem.ItemGTTool;
import gregtech.api.items.toolitem.ToolOreDict;
import gregtech.common.items.tool.BlockRotatingBehavior;
import gregtech.common.items.tool.EntityDamageBehavior;
import keqing.gtqtcore.GTQTCore;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.init.SoundEvents;

import static gregtech.common.items.ToolItems.register;

public class GTQTMetaToolItems {
    public static IGTTool Choocher;
    public static IGTTool Jinitaimei;
    public static IGTTool Choocher_HV;
    public static IGTTool Jinitaimei_HV;

    public static void init() {
        Choocher = register(ItemGTTool.Builder.of(GTQTCore.MODID, "choocher")
                .toolStats(b -> b.blockBreaking()
                        .crafting()
                        .damagePerCraftingAction(2)
                        .attackDamage(1.0F)
                        .attackSpeed(-2.8F)
                        .behaviors(new EntityDamageBehavior(2.0F, EntityGolem.class), BlockRotatingBehavior.INSTANCE))
                .oreDict(ToolOreDict.toolHammer)
                .secondaryOreDicts(ToolOreDict.toolWrench)
                .sound(SoundEvents.BLOCK_ANVIL_LAND)
                .symbol('t')
                .toolClasses("hammer", "wrench"));

        Jinitaimei = register(ItemGTTool.Builder.of(GTQTCore.MODID, "jinitaimei")
                .toolStats(b -> b.blockBreaking()
                        .crafting()
                        .damagePerCraftingAction(2)
                        .attackDamage(1.0F)
                        .attackSpeed(-2.8F)
                        .behaviors(new EntityDamageBehavior(2.0F, EntityGolem.class), BlockRotatingBehavior.INSTANCE))
                .oreDict(ToolOreDict.toolWireCutter)
                .secondaryOreDicts(ToolOreDict.toolScrewdriver)
                .sound(SoundEvents.BLOCK_ANVIL_LAND)
                .symbol('a')
                .toolClasses("wirecutter", "screwdriver"));

        Choocher_HV = register(ItemGTTool.Builder.of(GTQTCore.MODID, "choocher_hv")
                .toolStats(b -> b.blockBreaking()
                        .crafting()
                        .damagePerCraftingAction(2)
                        .attackDamage(1.0F)
                        .attackSpeed(-2.8F)
                        .behaviors(new EntityDamageBehavior(2.0F, EntityGolem.class), BlockRotatingBehavior.INSTANCE))
                .oreDict(ToolOreDict.toolHammer)
                .secondaryOreDicts(ToolOreDict.toolWrench)
                .sound(SoundEvents.BLOCK_ANVIL_LAND)
                .electric(GTValues.HV)
                .toolClasses("hammer", "wrench"));

        Jinitaimei_HV = register(ItemGTTool.Builder.of(GTQTCore.MODID, "jinitaimei_hv")
                .toolStats(b -> b.blockBreaking()
                        .crafting()
                        .damagePerCraftingAction(2)
                        .attackDamage(1.0F)
                        .attackSpeed(-2.8F)
                        .behaviors(new EntityDamageBehavior(2.0F, EntityGolem.class), BlockRotatingBehavior.INSTANCE))
                .oreDict(ToolOreDict.toolWireCutter)
                .secondaryOreDicts(ToolOreDict.toolScrewdriver)
                .sound(SoundEvents.BLOCK_ANVIL_LAND)
                .electric(GTValues.HV)
                .toolClasses("wirecutter", "screwdriver"));

    }

}