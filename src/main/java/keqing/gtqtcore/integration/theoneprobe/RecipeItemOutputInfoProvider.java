package keqing.gtqtcore.integration.theoneprobe;

import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IWorkable;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.integration.theoneprobe.provider.CapabilityInfoProvider;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Item Output Info Provider
 *
 * @author vfyjxf
 *
 * <p>
 *     Integrated from <a href="https://github.com/vfyjxf/GregicProbe">Gregic Probe</a> for GTCE,
 *     this mod use MIT License and is stop updating now (so we integrated it in gtlitecore).
 * </p>
 */
public class RecipeItemOutputInfoProvider extends CapabilityInfoProvider<IWorkable> {

    @Override
    protected @Nonnull Capability<IWorkable> getCapability() {
        return GregtechTileCapabilities.CAPABILITY_WORKABLE;
    }

    protected void addProbeInfo(IWorkable capability, IProbeInfo probeInfo, EntityPlayer entityPlayer, TileEntity tileEntity, IProbeHitData iProbeHitData) {
        if (capability.getProgress() > 0 && capability instanceof AbstractRecipeLogic&&entityPlayer.isSneaking()) {
            IProbeInfo horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
            List<ItemStack> itemOutputs = new ArrayList((Collection)ObfuscationReflectionHelper.getPrivateValue(AbstractRecipeLogic.class, (AbstractRecipeLogic)capability, "itemOutputs"));
            if (!itemOutputs.isEmpty()) {
                horizontalPane.text(TextStyleClass.INFO + "{*gtqt.top.item_outputs*}");
                Iterator var8 = itemOutputs.iterator();

                while(var8.hasNext()) {
                    ItemStack itemOutput = (ItemStack)var8.next();
                    if (itemOutput != null) {
                        IProbeInfo horizontal = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));

                        horizontal.text(TextStyleClass.INFO + itemOutput.getDisplayName()+this.FormatFluidAmount(itemOutput));
                    }
                }
            }
        }

    }
    private String FormatFluidAmount(ItemStack itemOutput) {
        return itemOutput.getCount() >= 1 ? " * "+ TextFormatting.BLUE+itemOutput.getCount()  : "";
    }
    @Override
    public String getID() {
        return "gtqt:recipe_info_item_output";
    }
}

