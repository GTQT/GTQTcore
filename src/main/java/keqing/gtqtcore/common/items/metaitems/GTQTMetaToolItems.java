package keqing.gtqtcore.common.items.metaitems;

import gregtech.api.GTValues;
import gregtech.api.items.toolitem.*;
import gregtech.common.items.ToolItems;
import gregtech.common.items.tool.*;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.GTQTCore;
import keqing.gtqtcore.api.items.toolitem.GTQTToolClasses;
import keqing.gtqtcore.common.items.tool.VAJRABehavior;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;

import static gregtech.common.items.ToolItems.register;

public class GTQTMetaToolItems {
    public static  IGTTool VAJRA ;

    public static IGTTool BENDING_CYLINDER;
    public static IGTTool SMALL_BENDING_CYLINDER;
    public static IGTTool UNIVERSAL_SPADE;
    public static IGTTool SOLDERING_IRON_LV;
    public static IGTTool SOLDERING_IRON_HV;
    public static IGTTool SOLDERING_IRON_IV;

    public static void init() {
        VAJRA = register(ItemGTTool.Builder.of(GTQTCore.MODID, "vajra")
                .toolStats(b -> b.blockBreaking().crafting().damagePerCraftingAction(2)
                        .attackDamage(10.0F).attackSpeed(5.6F)
                        .behaviors(TreeFellingBehavior.INSTANCE)
                        .behaviors(TorchPlaceBehavior.INSTANCE)
                        .behaviors(VAJRABehavior.INSTANCE)
                        .efficiencyMultiplier(10F))
                .sound(SoundEvents.BLOCK_ANVIL_LAND)
                .electric(GTValues.IV)
                .toolClasses(ToolClasses.WIRE_CUTTER,ToolClasses.WRENCH,ToolClasses.PICKAXE, ToolClasses.AXE,ToolClasses.SWORD,ToolClasses.SHOVEL,ToolClasses.HOE));

        BENDING_CYLINDER = ToolItems.register(ItemGTTool.Builder.of(GTQTCore.MODID, "bending_cylinder")
                .toolStats(b -> b.crafting().cannotAttack().attackSpeed(-2.8F))
                .oreDict("toolBendingCylinder")
                .secondaryOreDicts("craftingToolBendingCylinder")
                .toolClasses(GTQTToolClasses.BENDING_CYLINDER)
        );

        SMALL_BENDING_CYLINDER = ToolItems.register(ItemGTTool.Builder.of(GTQTCore.MODID, "small_bending_cylinder")
                .toolStats(b -> b.crafting().cannotAttack().attackSpeed(-1.8F))
                .oreDict("toolSmallBendingCylinder")
                .secondaryOreDicts("craftingToolSmallBendingCylinder")
                .toolClasses(GTQTToolClasses.SMALL_BENDING_CYLINDER)
        );

        UNIVERSAL_SPADE = ToolItems.register(ItemGTTool.Builder.of(GTQTCore.MODID, "universal_spade")
                .toolStats(b -> b.blockBreaking().crafting().sneakBypassUse()
                        .attackDamage(3.0F).attackSpeed(-2.4F)
                        .behaviors(GrassPathBehavior.INSTANCE, RotateRailBehavior.INSTANCE))
                .sound(SoundEvents.ENTITY_ITEM_BREAK)
                .oreDict(ToolOreDict.toolShovel)
                .secondaryOreDicts(ToolOreDict.toolCrowbar.toString(), ToolOreDict.toolSpade.toString(), ToolOreDict.toolSaw.toString(), "craftingToolSaw")
                .toolClasses(ToolClasses.CROWBAR, ToolClasses.SHOVEL)
        );

        SOLDERING_IRON_LV = ToolItems.register(ItemGTTool.Builder.of(GTQTCore.MODID, "soldering_iron_lv")
                .toolStats(b -> b.crafting().attacking()
                        .attackDamage(8.0F).attackSpeed(-2.4F)
                        .behaviors(new EntityDamageBehavior(3.0F, EntityGolem.class))
                        .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_LV)
                        .defaultEnchantment(Enchantments.FIRE_ASPECT, 2)
                )
                .sound(GTSoundEvents.WRENCH_TOOL, true)
                .oreDict("toolSolderingIron")
                .secondaryOreDicts("craftingToolSolderingIron")
                .toolClasses(GTQTToolClasses.SOLDERING_IRON)
                .electric(GTValues.LV));

        SOLDERING_IRON_HV = ToolItems.register(ItemGTTool.Builder.of(GTQTCore.MODID, "soldering_iron_hv")
                .toolStats(b -> b.crafting().attacking()
                        .attackDamage(8.0F).attackSpeed(-2.4F)
                        .behaviors(new EntityDamageBehavior(3.0F, EntityGolem.class))
                        .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_HV)
                        .defaultEnchantment(Enchantments.FIRE_ASPECT, 2)
                )
                .sound(GTSoundEvents.WRENCH_TOOL, true)
                .oreDict("toolSolderingIron")
                .secondaryOreDicts("craftingToolSolderingIron")
                .toolClasses(GTQTToolClasses.SOLDERING_IRON)
                .electric(GTValues.HV));

        SOLDERING_IRON_IV = ToolItems.register(ItemGTTool.Builder.of(GTQTCore.MODID, "soldering_iron_iv")
                .toolStats(b -> b.crafting().attacking()
                        .attackDamage(8.0F).attackSpeed(-2.4F)
                        .behaviors(new EntityDamageBehavior(3.0F, EntityGolem.class))
                        .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_IV)
                        .defaultEnchantment(Enchantments.FIRE_ASPECT, 2)
                )
                .sound(GTSoundEvents.WRENCH_TOOL, false)
                .oreDict("toolSolderingIron")
                .secondaryOreDicts("craftingToolSolderingIron")
                .toolClasses(GTQTToolClasses.SOLDERING_IRON)
                .electric(GTValues.IV));
    }

}