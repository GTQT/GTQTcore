package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.AbilityInstances;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.util.GTLog;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import gregtech.common.pipelike.optical.tile.TileEntityOpticalPipe;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import java.util.Collection;
import java.util.List;

import static gregtech.api.GTValues.VA;

public class MetaTileEntityKQCCComputationHatch extends MetaTileEntityMultiblockPart implements
        IMultiblockAbilityPart<IOpticalComputationHatch>, IOpticalComputationHatch {
    int tier;
    private final boolean isTransmitter;

    public MetaTileEntityKQCCComputationHatch(ResourceLocation metaTileEntityId,int tier, boolean isTransmitter) {
        super(metaTileEntityId, tier);
        this.isTransmitter = isTransmitter;
        this.tier=tier;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityKQCCComputationHatch(metaTileEntityId, tier, isTransmitter);
    }

    @Override
    public boolean isTransmitter() {
        return this.isTransmitter;
    }

    public int tier() {
        return VA[this.tier]/3;
    }

    @Override
    public int requestCWUt(int cwut, boolean simulate,  Collection<IOpticalComputationProvider> seen) {
        seen.add(this);
        var controller = getController();
        if (controller == null || !controller.isStructureFormed()) return 0;
        if (isTransmitter()) {
            // Ask the Multiblock controller, which *should* be an IOpticalComputationProvider
            if (controller instanceof IOpticalComputationProvider provider) {
                return Math.min(provider.requestCWUt(cwut, simulate, seen), tier());
            } else {
                GTLog.logger.error("Computation Transmission Hatch could not request CWU/t from its controller!");
                return 0;
            }
        } else {
            // Ask the attached Transmitter hatch, if it exists
            IOpticalComputationProvider provider = getOpticalNetProvider();
            if (provider == null) return 0;
            return Math.min(provider.requestCWUt(cwut, simulate, seen), tier());
        }
    }

    @Override
    public int getMaxCWUt( Collection<IOpticalComputationProvider> seen) {
        seen.add(this);
        var controller = getController();
        if (controller == null || !controller.isStructureFormed()) return 0;
        if (isTransmitter()) {
            // Ask the Multiblock controller, which *should* be an IOpticalComputationProvider
            if (controller instanceof IOpticalComputationProvider provider) {
                return Math.min(provider.getMaxCWUt(seen), tier());
            } else {
                GTLog.logger.error("Computation Transmission Hatch could not get maximum CWU/t from its controller!");
                return 0;
            }
        } else {
            // Ask the attached Transmitter hatch, if it exists
            IOpticalComputationProvider provider = getOpticalNetProvider();
            if (provider == null) return 0;
            return Math.min(provider.getMaxCWUt(seen), tier());
        }
    }

    @Override
    public boolean canBridge( Collection<IOpticalComputationProvider> seen) {
        seen.add(this);
        var controller = getController();
        // return true here so that unlinked hatches don't cause problems in multis like the Network Switch
        if (controller == null || !controller.isStructureFormed()) return true;
        if (isTransmitter()) {
            // Ask the Multiblock controller, which *should* be an IOpticalComputationProvider
            if (controller instanceof IOpticalComputationProvider provider) {
                return provider.canBridge(seen);
            } else {
                GTLog.logger.error("Computation Transmission Hatch could not test bridge status of its controller!");
                return false;
            }
        } else {
            // Ask the attached Transmitter hatch, if it exists
            IOpticalComputationProvider provider = getOpticalNetProvider();
            if (provider == null) return true; // nothing found, so don't report a problem, just pass quietly
            return provider.canBridge(seen);
        }
    }


    private IOpticalComputationProvider getOpticalNetProvider() {
        TileEntity tileEntity = getNeighbor(getFrontFacing());
        if (tileEntity == null) return null;

        if (tileEntity instanceof TileEntityOpticalPipe) {
            return tileEntity.getCapability(GregtechTileCapabilities.CABABILITY_COMPUTATION_PROVIDER,
                    getFrontFacing().getOpposite());
        }
        return null;
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return false;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public boolean canPartShare() {
        return false;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (shouldRenderOverlay()) {
            // todo make its own texture
            Textures.OPTICAL_DATA_ACCESS_HATCH.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }

    @Override
    public MultiblockAbility<IOpticalComputationHatch> getAbility() {
        return isTransmitter() ? MultiblockAbility.COMPUTATION_DATA_TRANSMISSION :
                MultiblockAbility.COMPUTATION_DATA_RECEPTION;
    }

    @Override
    public void registerAbilities(AbilityInstances abilityInstances) {
        abilityInstances.add(this);
    }
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (side == getFrontFacing() && capability == GregtechTileCapabilities.CABABILITY_COMPUTATION_PROVIDER) {
            return GregtechTileCapabilities.CABABILITY_COMPUTATION_PROVIDER.cast(this);
        }
        return super.getCapability(capability, side);
    }
    public void addInformation(ItemStack stack,  World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format(this.isTransmitter ? "gregtech.machine.kqcc.export.tooltip" : "gregtech.machine.kqcc.import.tooltip"));
        tooltip.add(I18n.format("gregtech.universal.tooltip.kqcc1", this.tier));
        tooltip.add(I18n.format("gregtech.universal.tooltip.kqcc2", this.tier()));
    }

}