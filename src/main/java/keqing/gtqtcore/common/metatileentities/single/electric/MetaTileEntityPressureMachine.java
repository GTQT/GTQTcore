package keqing.gtqtcore.common.metatileentities.single.electric;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.IGuiTexture;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import keqing.gtqtcore.api.GCYSValues;
import keqing.gtqtcore.api.capability.GTQTTileCapabilities;
import keqing.gtqtcore.api.capability.IPressureContainer;
import keqing.gtqtcore.api.capability.IPressureMachine;
import keqing.gtqtcore.api.capability.impl.PressureContainer;
import keqing.gtqtcore.api.capability.impl.PressureSingleRecipeLogic;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.api.utils.NumberFormattingUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.IntSupplier;

import static keqing.gtqtcore.api.GCYSValues.decreaseDetailP;
import static keqing.gtqtcore.api.GCYSValues.increaseDetailP;

public class MetaTileEntityPressureMachine extends SimpleMachineMetaTileEntity implements IPressureMachine {

    private final PressureContainer pressureContainer;
    public int tier;
    boolean isDecrease;

    public MetaTileEntityPressureMachine(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, ICubeRenderer renderer, int tier, boolean isDecrease) {
        super(metaTileEntityId, recipeMap, renderer, tier, true, GTUtility.hvCappedTankSizeFunction);
        this.tier = tier;
        this.isDecrease = isDecrease;
        if (isDecrease)
            this.pressureContainer = new PressureContainer(this, decreaseDetailP[tier], GCYSValues.EARTH_PRESSURE, 1.0);
        else
            this.pressureContainer = new PressureContainer(this, GCYSValues.EARTH_PRESSURE, increaseDetailP[tier], 1.0);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPressureMachine(metaTileEntityId, recipeMap, renderer, tier, isDecrease);
    }

    @Override
    protected PressureSingleRecipeLogic createWorkable(RecipeMap<?> recipeMap) {
        return new PressureSingleRecipeLogic(this, recipeMap, () -> this.energyContainer);
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote && getOffsetTimer() % 20 == 0) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                if (facing == getFrontFacing()) continue;

                TileEntity te = getWorld().getTileEntity(getPos().offset(facing));
                if (te != null) {
                    IPressureContainer container = te.getCapability(GTQTTileCapabilities.CAPABILITY_PRESSURE_CONTAINER, facing.getOpposite());
                    if (container != null) {
                        IPressureContainer.mergeContainers(false, container, pressureContainer);
                    }
                }
            }

            if (!this.pressureContainer.isPressureSafe()) {
                this.pressureContainer.causePressureExplosion(getWorld(), getPos());
            }
        }
    }

    protected ModularUI.Builder createGuiTemplate(EntityPlayer player) {
        RecipeMap<?> workableRecipeMap = this.workable.getRecipeMap();
        int yOffset = 0;
        if (workableRecipeMap.getMaxInputs() >= 6 || workableRecipeMap.getMaxFluidInputs() >= 6 || workableRecipeMap.getMaxOutputs() >= 6 || workableRecipeMap.getMaxFluidOutputs() >= 6) {
            yOffset = 9;
        }

        AbstractRecipeLogic var10001 = this.workable;
        Objects.requireNonNull(var10001);
        ModularUI.Builder var10000 = workableRecipeMap.createUITemplate(var10001::getProgressPercent, this.importItems, this.exportItems, this.importFluids, this.exportFluids, yOffset).widget(new LabelWidget(5, 5, this.getMetaFullName())).widget((new SlotWidget(this.chargerInventory, 0, 79, 62 + yOffset, true, true, false)).setBackgroundTexture(GuiTextures.SLOT, GuiTextures.CHARGER_OVERLAY).setTooltipText("gregtech.gui.charger_slot.tooltip", GTValues.VNF[this.getTier()], GTValues.VNF[this.getTier()]));
        ImageWidget var8 = (new ImageWidget(79, 42 + yOffset, 18, 18, GuiTextures.INDICATOR_NO_ENERGY)).setIgnoreColor(true);
        AbstractRecipeLogic var10002 = this.workable;
        Objects.requireNonNull(var10002);
        ModularUI.Builder builder = var10000.widget(var8.setPredicate(var10002::isHasNotEnoughEnergy)).bindPlayerInventory(player.inventory, GuiTextures.SLOT, yOffset);
        int leftButtonStartX = 7;
        if (this.exportItems.getSlots() > 0) {
            builder.widget((new ToggleButtonWidget(leftButtonStartX, 62 + yOffset, 18, 18, GuiTextures.BUTTON_ITEM_OUTPUT, this::isAutoOutputItems, this::setAutoOutputItems)).setTooltipText("gregtech.gui.item_auto_output.tooltip").shouldUseBaseBackground());
            leftButtonStartX += 18;
        }

        if (this.exportFluids.getTanks() > 0) {
            builder.widget((new ToggleButtonWidget(leftButtonStartX, 62 + yOffset, 18, 18, GuiTextures.BUTTON_FLUID_OUTPUT, this::isAutoOutputFluids, this::setAutoOutputFluids)).setTooltipText("gregtech.gui.fluid_auto_output.tooltip").shouldUseBaseBackground());
            leftButtonStartX += 18;
        }

        int var10004 = 62 + yOffset;
        String[] var10007 = this.workable.getAvailableOverclockingTiers();
        AbstractRecipeLogic var10008 = this.workable;
        Objects.requireNonNull(var10008);
        IntSupplier var9 = var10008::getOverclockTier;
        AbstractRecipeLogic var10009 = this.workable;
        Objects.requireNonNull(var10009);
        builder.widget((new CycleButtonWidget(leftButtonStartX, var10004, 18, 18, var10007, var9, var10009::setOverclockTier)).setTooltipHoverString("gregtech.gui.overclock.description").setButtonTexture(GuiTextures.BUTTON_OVERCLOCK));
        if (this.exportItems.getSlots() + this.exportFluids.getTanks() <= 9) {
            ImageWidget logo = (new ImageWidget(152, 63 + yOffset, 17, 17, GTValues.XMAS.get() ? GuiTextures.GREGTECH_LOGO_XMAS : GuiTextures.GREGTECH_LOGO)).setIgnoreColor(true);
            if (this.circuitInventory != null) {
                SlotWidget circuitSlot = (new GhostCircuitSlotWidget(this.circuitInventory, 0, 124, 62 + yOffset)).setBackgroundTexture(GuiTextures.SLOT, this.getCircuitSlotOverlay());
                builder.widget(circuitSlot.setConsumer(this::getCircuitSlotTooltip)).widget(logo);
            }
        }

        // TODO add tooltip directly to ProgressWidget in CEu
        builder.widget(new ImageWidget(4, 15, 10, 54, GuiTextures.SLOT)
                        .setTooltip(getTooltips()))
                .widget(new ProgressWidget(() -> pressureContainer.getPressurePercent(true), 4, 15, 10, 54)
                        .setProgressBar(GuiTextures.PROGRESS_BAR_BOILER_EMPTY.get(true),
                                GuiTextures.PROGRESS_BAR_BOILER_HEAT, ProgressWidget.MoveType.VERTICAL));

        return builder;
    }

    private String getTooltips() {
        return NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getPressure()) + "Pa / " +
                NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMinPressure()) + "Pa";
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.universal.tooltip.pressure.minimum", NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMinPressure()), GCYSValues.PNF[GTQTUtil.getTierByPressure(pressureContainer.getMinPressure())]));
        tooltip.add(I18n.format("gtqtcore.universal.tooltip.pressure.maximum", NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMaxPressure()), GCYSValues.PNF[GTQTUtil.getTierByPressure(pressureContainer.getMaxPressure())]));
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability.equals(GTQTTileCapabilities.CAPABILITY_PRESSURE_CONTAINER)) {
            return GTQTTileCapabilities.CAPABILITY_PRESSURE_CONTAINER.cast(pressureContainer);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public IPressureContainer getPressureContainer() {
        return this.pressureContainer;
    }

    // the vacuum chamber does not emit particles
    @Override
    public void randomDisplayTick() {/**/}
}
