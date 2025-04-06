package keqing.gtqtcore.common.metatileentities.single.steam;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.client.renderer.texture.Textures;
import keqing.gtqtcore.api.GCYSValues;
import keqing.gtqtcore.api.capability.GTQTTileCapabilities;
import keqing.gtqtcore.api.capability.IPressureContainer;
import keqing.gtqtcore.api.capability.IPressureMachine;
import keqing.gtqtcore.api.capability.impl.PressureContainer;
import keqing.gtqtcore.api.capability.impl.PressureSteamRecipeLogic;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTLog;
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
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static keqing.gtqtcore.api.GCYSValues.*;
import static keqing.gtqtcore.api.metaileentity.SteamProgressIndicators.COMPRESS;

public class MetaTileEntitySteamVacuumChamber extends SimpleSteamMetaTileEntity implements IPressureMachine {

    private PressureContainer pressureContainer;

    public MetaTileEntitySteamVacuumChamber(ResourceLocation metaTileEntityId, boolean isHighPressure) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES, COMPRESS, Textures.COMPRESSOR_OVERLAY, false, isHighPressure);
        this.workableHandler = new PressureSteamRecipeLogic(this, GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES, isHighPressure, this.steamFluidTank, 1.0);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntitySteamVacuumChamber(metaTileEntityId, isHighPressure);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.pressureContainer = new PressureContainer(this, decreaseDetailP[0], GCYSValues.EARTH_PRESSURE, 1.0);
    }

    @Override
    protected ModularUI.Builder createGuiTemplate(EntityPlayer player) {
        RecipeMap<?> recipeMap = workableHandler.getRecipeMap();
        int yOffset = 0;

        ModularUI.Builder builder = super.createUITemplate(player);
        addRecipeProgressBar(builder, recipeMap, yOffset);
        addInventorySlotGroup(builder, importItems, importFluids, false, yOffset);
        addInventorySlotGroup(builder, exportItems, exportFluids, true, yOffset);

        if (exportItems.getSlots() + exportFluids.getTanks() <= 9) {
            if (this.circuitInventory != null) {
                SlotWidget circuitSlot = new GhostCircuitSlotWidget(circuitInventory, 0, 124, 62 + yOffset)
                        .setBackgroundTexture(GuiTextures.SLOT_STEAM.get(isHighPressure), getCircuitSlotOverlay());
                builder.widget(circuitSlot.setConsumer(this::getCircuitSlotTooltip))
                        .widget(new ClickButtonWidget(115, 62 + yOffset, 9, 9, "", click -> circuitInventory.addCircuitValue(click.isShiftClick ? 5 : 1)).setShouldClientCallback(true).setButtonTexture(GuiTextures.BUTTON_INT_CIRCUIT_PLUS).setDisplayFunction(() -> circuitInventory.hasCircuitValue() && circuitInventory.getCircuitValue() < IntCircuitIngredient.CIRCUIT_MAX))
                        .widget(new ClickButtonWidget(115, 71 + yOffset, 9, 9, "", click -> circuitInventory.addCircuitValue(click.isShiftClick ? -5 : -1)).setShouldClientCallback(true).setButtonTexture(GuiTextures.BUTTON_INT_CIRCUIT_MINUS).setDisplayFunction(() -> circuitInventory.hasCircuitValue() && circuitInventory.getCircuitValue() > IntCircuitIngredient.CIRCUIT_MIN));
            }
        }

        // TODO add tooltip directly to ProgressWidget in CEu
        builder.widget(new ImageWidget(4, 19, 10, 54, GuiTextures.SLOT)
                        .setTooltip(getTooltips()))
                .widget(new ProgressWidget(() -> pressureContainer.getPressurePercent(true), 4, 19, 10, 54)
                        .setProgressBar(GuiTextures.PROGRESS_BAR_BOILER_EMPTY.get(true),
                                GuiTextures.PROGRESS_BAR_BOILER_HEAT, ProgressWidget.MoveType.VERTICAL));

        return builder;
    }

    private String getTooltips() {
        return NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getPressure()) + "Pa / " +
                NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMinPressure()) + "Pa";
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote && getOffsetTimer() % 20 == 0) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                if (facing == getFrontFacing() || facing == this.workableHandler.getVentingSide()) continue;

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