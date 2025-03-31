package keqing.gtqtcore.common.items.behaviors;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.items.metaitem.stats.IItemDurabilityManager;
import gregtech.common.entities.DynamiteEntity;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static keqing.gtqtcore.common.items.GTQTMetaItems.TIME_BOTTLE;

public class ParticleBehavior implements IItemBehaviour {

    protected final double mass; 	//in MeV/c^2
    protected final double charge;	// in e
    protected final double spin;	// in h bar
    protected final boolean strongInteract;
    protected final boolean weakInteract;
    ItemStack antiparticle;

    // Getter 方法（所有字段均为 final，不生成 setter）
    public double getMass() {
        return mass;
    }

    public ItemStack getAntiparticle() {
        return antiparticle;
    }

    public double getCharge() {
        return charge;
    }

    public double getSpin() {
        return spin;
    }

    public boolean isStrongInteract() {  // boolean 类型推荐用 is 前缀
        return strongInteract;
    }

    public boolean isWeakInteract() {
        return weakInteract;
    }
    
    public void addInformation(ItemStack stack, List<String> lines) {
        lines.add(I18n.format("gtqtcore.hpi.tooltip"));
        lines.add(I18n.format("gtqtcore.hpi.tooltip.mass",
                String.format("%.3e", mass/1000) + " GeV/c²"));  // 转换为GeV单位

        lines.add(I18n.format("gtqtcore.hpi.tooltip.charge",
                (charge == (int) charge) ?
                        String.format("%d e", (int) charge) :  // 整数电荷显示
                        String.format("%.2f e", charge)));     // 小数电荷显示

        lines.add(I18n.format("gtqtcore.hpi.tooltip.spin",
                (spin % 1 == 0) ?
                        String.format("%.0f ħ", spin) :        // 整数自旋
                        String.format("%.1f ħ", spin)));       // 半整数自旋

        lines.add(I18n.format("gtqtcore.hpi.tooltip.strong",
                strongInteract ? "✔" : "✘"));              // 强相互作用状态

        lines.add(I18n.format("gtqtcore.hpi.tooltip.weak",
                weakInteract ? "✔" : "✘"));                // 弱相互作用状态
    }
    public ParticleBehavior(double mass, double charge, double spin, boolean weakInteract, boolean strongInteract,MetaItem<?>.MetaValueItem antiparticle) {
        this.mass = mass;
        this.charge = charge;
        this.spin = spin;
        this.weakInteract = weakInteract;
        this.strongInteract = strongInteract;
        this.antiparticle = antiparticle.getStackForm(1);
    }

    @Nullable
    public static ParticleBehavior getInstanceFor(@Nonnull ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof MetaItem)) return null;

        MetaItem<?>.MetaValueItem valueItem = ((MetaItem<?>) itemStack.getItem()).getItem(itemStack);
        if (valueItem == null) return null;

        for (IItemBehaviour behaviour : valueItem.getBehaviours()) {
            if (behaviour instanceof ParticleBehavior) {
                return (ParticleBehavior) behaviour;
            }
        }

        return null;
    }

}
