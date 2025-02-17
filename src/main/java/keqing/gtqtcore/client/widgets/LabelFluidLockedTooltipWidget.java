package keqing.gtqtcore.client.widgets;

import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.util.LocalizationUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import java.util.ArrayList;
import java.util.List;

//gt的ui竟然没有给其他widget加上显示tooltip方法，所以只能自己写一个能显示tooltip的label，用于显示超级缸里的被锁定的流体显示
public class LabelFluidLockedTooltipWidget extends LabelWidget {
    private boolean hideTooltip;
    private FluidStack[] stacks;

    public LabelFluidLockedTooltipWidget setHideTooltip(boolean hideTooltip) {
        this.hideTooltip = hideTooltip;
        return this;
    }

    public LabelFluidLockedTooltipWidget(int xPosition, int yPosition, String text, FluidStack[] stacks, Object... formatting) {
        super(xPosition, yPosition, text, formatting);
        this.stacks = stacks;
    }

    @Override
    public void drawInForeground(int mouseX, int mouseY) {
        if (!this.hideTooltip && !this.gui.isJEIHandled && this.isMouseOverElement(mouseX, mouseY)) {
            List<String> tooltips = new ArrayList();
            tooltips.add(LocalizationUtils.format("gtqtcore.gui.fluid_lock"));
            for (int i = 0; i < this.stacks.length; i++)
            {
                if(this.stacks[i] != null && this.stacks[i].getFluid() != null)
                {
                    tooltips.add( i + 1 + "." + this.stacks[i].getFluid().getLocalizedName(this.stacks[i]));
                }   else
                {
                    tooltips.add( i + 1 + "." + "空");
                }
            }
            this.drawHoveringText(ItemStack.EMPTY, tooltips, 300, mouseX, mouseY);
            GlStateManager.color(1.0F, 1.0F, 1.0F);
        }
    }
}