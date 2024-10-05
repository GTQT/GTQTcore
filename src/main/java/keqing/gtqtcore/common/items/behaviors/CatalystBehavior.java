package keqing.gtqtcore.common.items.behaviors;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.stats.IItemDurabilityManager;
import gregtech.api.items.metaitem.stats.IItemMaxStackSizeProvider;
import gregtech.common.items.behaviors.AbstractMaterialPartBehavior;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CatalystBehavior extends AbstractMaterialPartBehavior implements IItemMaxStackSizeProvider {

    private boolean isCatalystBed;
    private int tier;

    public CatalystBehavior() {}

    public CatalystBehavior(boolean isCatalystBed) {
        this.isCatalystBed = isCatalystBed;
    }

    public CatalystBehavior(boolean isCatalystBed, int tier) {
        this.isCatalystBed = isCatalystBed;
        this.tier = tier;
    }

    public void applyCatalystDamage(ItemStack stack, int damageApplied) {
        int catalystDurability = this.getPartMaxDurability(stack);
        int resultDamage = getPartDamage(stack) + damageApplied;
        if (resultDamage >= catalystDurability)
            stack.shrink(1);
        else
            this.setPartDamage(stack, resultDamage);
    }

    public static CatalystBehavior getInstanceFor( ItemStack stack) {
        if (!(stack.getItem() instanceof MetaItem))
            return null;

        MetaItem<?>.MetaValueItem valueItem = ((MetaItem<?>) stack.getItem()).getItem(stack);
        if (valueItem == null)
            return null;

        IItemDurabilityManager durabilityManager = valueItem.getDurabilityManager();

        if (!(durabilityManager instanceof CatalystBehavior))
            return null;
        return (CatalystBehavior) durabilityManager;
    }

    /**
     * Get Max Durability of Part.
     *
     * <p>
     *     In {@code gtlitecore}, catalyst has two form: one is Common Catalyst,
     *     max durability of each Common Catalyst is 50; another one is Catalyst
     *     Bed, max durability of each Catalyst Bed is more higher than Common
     *     Catalyst, and you can see it as below:
     *     <ul>
     *         <li>Smaller than or equal to UHV -> {@code 50 * (tier + 1)};</li>
     *         <li>Bigger than UHV -> {@code 100 * (tier + 1)};</li>
     *     </ul>
     * </p>
     *
     * @param stack  Part.
     * @return       Max Durability of {@code stack}.
     */
    @Override
    public int getPartMaxDurability(ItemStack stack) {
        if (isCatalystBed) {
            if (tier <= 9) {
                return 50 * (tier + 1);
            } else {
                return 100 * (tier + 1);
            }
        } else {
            return 50;
        }
    }

    @Override
    public void addInformation(ItemStack stack, List<String> lines) {
        int maxDurability = getPartMaxDurability(stack);
        int damage = getPartDamage(stack);
        if (isCatalystBed) {
            lines.add(I18n.format("metaitem.tool.tooltip.catalyst_bed"));
        } else {
            lines.add(I18n.format("metaitem.tool.tooltip.catalyst"));
        }
        lines.add(I18n.format("metaitem.tool.tooltip.durability", maxDurability - damage, maxDurability));
    }

    @Override
    public int getMaxStackSize(ItemStack itemStack, int defaultValue) {
        if (isCatalystBed) {
            return 1;
        } else {
            return 16;
        }
    }
}
