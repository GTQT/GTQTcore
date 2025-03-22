package keqing.gtqtcore.common.items.behaviors;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.stats.IItemDurabilityManager;
import gregtech.api.items.metaitem.stats.IItemMaxStackSizeProvider;
import gregtech.api.unification.material.Material;
import gregtech.common.items.behaviors.AbstractMaterialPartBehavior;
import keqing.gtqtcore.api.utils.GTQTDateHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class WrapSwarmBehavior extends AbstractMaterialPartBehavior implements IItemMaxStackSizeProvider {

    int MaxDurability;
    int WrapSwarmTier;
    Material material;
    public WrapSwarmBehavior(int Durability, int WrapSwarmTier, Material material) {
        this.MaxDurability=Durability;
        this.material=material;
        this.WrapSwarmTier=WrapSwarmTier;
    }

    public double getDurabilityPercent(ItemStack itemStack) {
        return 1- (double) getPartDamage(itemStack) / getPartMaxDurability(itemStack);
    }
    
    public int getWrapSwarmTier()
    {
        return WrapSwarmTier;
    }
    public void applyDamage(ItemStack itemStack, int damageApplied) {
        int Durability = getPartMaxDurability(itemStack);
        int resultDamage = getPartDamage(itemStack) + damageApplied;
        if (resultDamage >= Durability) {
            itemStack.shrink(1);
        } else {
            setPartDamage(itemStack, resultDamage);
        }
    }

    public void addInformation(ItemStack stack, List<String> lines) {
        int maxDurability = getPartMaxDurability(stack);
        int damage = getPartDamage(stack);
        lines.add(I18n.format("metaitem.tool.tooltip.durability", maxDurability - damage, maxDurability));
        lines.add(I18n.format("metaitem.tool.tooltip.primary_material", material.getLocalizedName()));
    }
    @Override
    public int getPartMaxDurability(ItemStack itemStack) {
        return MaxDurability;
    }

    @Override
    public int getMaxStackSize(ItemStack itemStack, int i) {
        return 1;
    }

    public Material getMaterial() {
        return material;
    }
    @Nullable
    public static WrapSwarmBehavior getInstanceFor(@Nonnull ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof MetaItem)) return null;

        MetaItem<?>.MetaValueItem valueItem = ((MetaItem<?>) itemStack.getItem()).getItem(itemStack);
        if (valueItem == null) return null;

        IItemDurabilityManager durabilityManager = valueItem.getDurabilityManager();
        if (!(durabilityManager instanceof WrapSwarmBehavior)) return null;

        return (WrapSwarmBehavior) durabilityManager;
    }
}
