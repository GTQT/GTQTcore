package keqing.gtqtcore.mixin.gregtech;

import gregtech.api.GTValues;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleGeneratorMetaTileEntity;
import gregtech.api.metatileentity.WorkableTieredMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.ICubeRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Function;

import static gregtech.api.GTValues.VA;

@Mixin(SimpleGeneratorMetaTileEntity.class)
public abstract class MixinSimpleGeneratorMetaTileEntity extends WorkableTieredMetaTileEntity implements IActiveOutputSide {
    int tier;
    public MixinSimpleGeneratorMetaTileEntity(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, ICubeRenderer renderer, int tier, Function<Integer, Integer> tankScalingFunction, boolean handlesRecipeOutputs) {
        super(metaTileEntityId, recipeMap, renderer, tier, tankScalingFunction, handlesRecipeOutputs);
        this.tier=tier;
    }
    public void addInformation(ItemStack stack,  World player,  List<String> tooltip, boolean advanced) {
        double a=this.energyContainer.getOutputVoltage();
        double i=a/GTValues.V[this.getTier()];
        BigDecimal bigDecimal = new BigDecimal(i);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        String key = this.metaTileEntityId.getPath().split("\\.")[0];
        String mainKey = String.format("gregtech.machine.%s.tooltip", key);
        if (I18n.hasKey(mainKey)) {
            tooltip.add(1, I18n.format(mainKey));
        }
        tooltip.add(I18n.format("gregtech.generator.1",bigDecimal));
        tooltip.add(I18n.format("gregtech.universal.tooltip.voltage_out", this.energyContainer.getOutputVoltage(), GTValues.VNF[this.getTier()]));
        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_storage_capacity", this.energyContainer.getEnergyCapacity()));
        if (this.recipeMap.getMaxFluidInputs() > 0 || this.recipeMap.getMaxFluidOutputs() > 0) {
            tooltip.add(I18n.format("gregtech.universal.tooltip.fluid_storage_capacity", this.getTankScalingFunction().apply(this.getTier())));
        }

    }
}
