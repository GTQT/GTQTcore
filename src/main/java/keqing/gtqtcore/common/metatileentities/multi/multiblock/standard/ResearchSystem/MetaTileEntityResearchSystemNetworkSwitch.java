package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.ResearchSystem;

import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityDataBank;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static gregtech.api.GTValues.VA;

public class MetaTileEntityResearchSystemNetworkSwitch extends MetaTileEntityDataBank implements IOpticalComputationProvider {
    private static final int EUT_PER_HATCH = VA[GTValues.LV];
    private final MultipleComputationHandler computationHandler = new MultipleComputationHandler();
    int tire;
    private int casing_tier;
    private IEnergyContainer energyContainer;
    private boolean hasNotEnoughEnergy;

    public MetaTileEntityResearchSystemNetworkSwitch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.energyContainer = new EnergyContainerList(new ArrayList<>());
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityResearchSystemNetworkSwitch(metaTileEntityId);

    }

    @Override
    protected int calculateEnergyUsage() {
        int receivers = getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION).size();
        int transmitters = getAbilities(MultiblockAbility.COMPUTATION_DATA_TRANSMISSION).size();
        return VA[GTValues.LV] * (receivers + transmitters);
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GTQTValue.UPDATE_TIER13) {
            this.casing_tier = buf.readInt();
        }
        if (dataId == GTQTValue.REQUIRE_DATA_UPDATE13) {
            this.writeCustomData(GTQTValue.UPDATE_TIER13, buf1 -> buf1.writeInt(this.casing_tier));
        }
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
        computationHandler.onStructureForm(
                getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION),
                getAbilities(MultiblockAbility.COMPUTATION_DATA_TRANSMISSION));
        Object casing_tier = context.get("ChemicalPlantCasingTieredStats");
        Object laser_tier = context.get("ZWTieredStats");
        this.casing_tier = GTQTUtil.getOrDefault(() -> casing_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) casing_tier).getIntTier(),
                0);
        int laser_tier1 = GTQTUtil.getOrDefault(() -> laser_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) laser_tier).getIntTier(),
                0);

        this.writeCustomData(GTQTValue.UPDATE_TIER13, buf -> buf.writeInt(this.casing_tier));
        this.tire = Math.min(this.casing_tier, laser_tier1 * 2);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        computationHandler.reset();
    }

    @Override
    public void update() {
        super.update();
        consumeEnergy();
    }

    @Override
    protected int getEnergyUsage() {
        return isStructureFormed() ? computationHandler.getEUt() : 0;
    }

    private void consumeEnergy() {
        int energyToConsume = getEnergyUsage();
        boolean hasMaintenance = ConfigHolder.machines.enableMaintenance && hasMaintenanceMechanics();
        if (hasMaintenance) {
            // 10% more energy per maintenance problem
            energyToConsume += getNumMaintenanceProblems() * energyToConsume / 10;
        }

        if (this.hasNotEnoughEnergy && energyContainer.getInputPerSec() > 19L * energyToConsume) {
            this.hasNotEnoughEnergy = false;
        }

        if (this.energyContainer.getEnergyStored() >= energyToConsume) {
            if (!hasNotEnoughEnergy) {
                long consumed = this.energyContainer.removeEnergy(energyToConsume);
                if (consumed != -energyToConsume) {
                    this.hasNotEnoughEnergy = true;
                }
            }
        } else {
            this.hasNotEnoughEnergy = true;
        }
    }

    public int vacwu() {
        if (tire <= 2) return 0;
        return VA[this.tire] * 2;
    }

    @Override
    public int requestCWUt(int cwut, boolean simulate, Collection<IOpticalComputationProvider> seen) {
        seen.add(this);
        return isActive() ? computationHandler.requestCWUt(cwut, simulate, seen) : 0;
    }

    @Override
    public int getMaxCWUt(Collection<IOpticalComputationProvider> seen) {
        seen.add(this);
        if (computationHandler.getMaxCWUt(seen) > vacwu())
            return Math.min(1024, isWorkingEnabled() ? computationHandler.getMaxCWUt(seen) : 0);
        else return 0;
    }

    // allows chaining Network Switches together
    @Override
    public boolean canBridge(Collection<IOpticalComputationProvider> seen) {
        seen.add(this);
        return true;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "XAX", "XXX")
                .aisle("XXX", "XSX", "XXX")
                .where('S', selfPredicate())
                .where('A', TiredTraceabilityPredicate.CP_ZW_CASING.get())
                .where('X', TiredTraceabilityPredicate.CP_CASING.get()
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1, 1))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setMinGlobalLimited(1, 1))
                        .or(abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION).setMinGlobalLimited(1, 2))
                        .or(abilities(MultiblockAbility.COMPUTATION_DATA_TRANSMISSION).setMinGlobalLimited(1, 1)))
                .build();
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.casing_tier);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.casing_tier = buf.readInt();
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        switch (this.casing_tier) {
            case (2) -> {
                return Textures.SOLID_STEEL_CASING;
            }
            case (3) -> {
                return Textures.FROST_PROOF_CASING;
            }
            case (4) -> {
                return Textures.CLEAN_STAINLESS_STEEL_CASING;
            }
            case (5) -> {
                return Textures.STABLE_TITANIUM_CASING;
            }
            case (6) -> {
                return Textures.ROBUST_TUNGSTENSTEEL_CASING;
            }
            case (7) -> {
                return GTQTTextures.PD_CASING;
            }
            case (8) -> {
                return GTQTTextures.NQ_CASING;
            }
            case (9) -> {
                return GTQTTextures.ST_CASING;
            }
            case (10) -> {
                return GTQTTextures.AD_CASING;
            }
            default -> {
                return Textures.BRONZE_PLATED_BRICKS;
            }
        }
    }

    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("gtqt.tooltip.update")};
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.NETWORK_SWITCH_OVERLAY;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip,
                               boolean advanced) {
        tooltip.add(I18n.format("gregtech.machine.network_switch.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.network_switch.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.network_switch.tooltip.3"));
        tooltip.add(I18n.format("gregtech.machine.network_switch.tooltip.5"));
        tooltip.add(I18n.format("gtqtcore.machine.network_switch.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.network_switch.tooltip.4",
                TextFormattingUtil.formatNumbers(EUT_PER_HATCH)));
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .setWorkingStatusKeys(
                        "gregtech.multiblock.idling",
                        "gregtech.multiblock.idling",
                        "gregtech.multiblock.data_bank.providing")
                .addEnergyUsageExactLine(getEnergyUsage())
                .addComputationUsageLine(computationHandler.getMaxCWUtForDisplay())
                .addWorkingStatusLine();
        textList.add(new TextComponentTranslation("gregtech.multiblock.kqns.tier", tire));
        textList.add(new TextComponentTranslation("gregtech.multiblock.kqns.max", computationHandler.getMaxCWUtForDisplay(), vacwu()));
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (isStructureFormed() && computationHandler.hasNonBridgingConnections()) {
            textList.add(TextComponentUtil.translationWithColor(
                    TextFormatting.YELLOW,
                    "gregtech.multiblock.computation.non_bridging.detailed"));
        }
    }

    /**
     * Handles computation load across multiple receivers and to multiple transmitters.
     */
    private static class MultipleComputationHandler implements IOpticalComputationProvider,
            IOpticalComputationReceiver {

        // providers in the NS provide distributable computation to the NS
        private final Set<IOpticalComputationHatch> providers = new ObjectOpenHashSet<>();
        // transmitters in the NS give computation to other multis
        private final Set<IOpticalComputationHatch> transmitters = new ObjectOpenHashSet<>();

        private int EUt;

        private void onStructureForm(Collection<IOpticalComputationHatch> providers,
                                     Collection<IOpticalComputationHatch> transmitters) {
            reset();
            this.providers.addAll(providers);
            this.transmitters.addAll(transmitters);
            this.EUt = (providers.size() + transmitters.size()) * EUT_PER_HATCH;
        }

        private void reset() {
            providers.clear();
            transmitters.clear();
            EUt = 0;
        }

        @Override
        public int requestCWUt(int cwut, boolean simulate, Collection<IOpticalComputationProvider> seen) {
            if (seen.contains(this)) return 0;
            // The max CWU/t that this Network Switch can provide, combining all its inputs.
            seen.add(this);
            Collection<IOpticalComputationProvider> bridgeSeen = new ArrayList<>(seen);
            int allocatedCWUt = 0;
            for (var provider : providers) {
                if (!provider.canBridge(bridgeSeen)) continue;
                int allocated = provider.requestCWUt(cwut, simulate, seen);
                allocatedCWUt += allocated;
                cwut -= allocated;
                if (cwut == 0) break;
            }
            return allocatedCWUt;
        }

        public int getMaxCWUtForDisplay() {
            Collection<IOpticalComputationProvider> seen = new ArrayList<>();
            // The max CWU/t that this Network Switch can provide, combining all its inputs.
            seen.add(this);
            Collection<IOpticalComputationProvider> bridgeSeen = new ArrayList<>(seen);
            int maximumCWUt = 0;
            for (var provider : providers) {
                if (!provider.canBridge(bridgeSeen)) continue;
                maximumCWUt += provider.getMaxCWUt(seen);
            }
            return maximumCWUt;
        }

        public int getMaxCWUt(Collection<IOpticalComputationProvider> seen) {
            if (seen.contains(this)) return 0;
            // The max CWU/t that this Network Switch can provide, combining all its inputs.
            seen.add(this);
            Collection<IOpticalComputationProvider> bridgeSeen = new ArrayList<>(seen);
            int maximumCWUt = 0;
            for (var provider : providers) {
                if (!provider.canBridge(bridgeSeen)) continue;
                maximumCWUt += provider.getMaxCWUt(seen);
            }
            return maximumCWUt;
        }

        @Override
        public boolean canBridge(Collection<IOpticalComputationProvider> seen) {
            if (seen.contains(this)) return false;
            seen.add(this);
            for (var provider : providers) {
                if (provider.canBridge(seen)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * The EU/t cost of this Network Switch given the attached providers and transmitters.
         */
        private int getEUt() {
            return EUt;
        }

        /**
         * Test if any of the provider hatches do not allow bridging
         */
        private boolean hasNonBridgingConnections() {
            Collection<IOpticalComputationProvider> seen = new ArrayList<>();
            for (var provider : providers) {
                if (!provider.canBridge(seen)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public IOpticalComputationProvider getComputationProvider() {
            return this;
        }
    }
}