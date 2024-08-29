package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableFluidTank;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiFluidHatch;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;

import java.util.List;

/**
 * <p>
 *     Just a rewrite of {@link MetaTileEntityMultiFluidHatch},
 *     this class does not support UHV+, so i just create a new and init them.
 * </p>
 */
public class MetaTileEntityAdvancedMultiFluidHatch extends MetaTileEntityMultiblockNotifiablePart implements IMultiblockAbilityPart<IFluidTank>, IControllable {

    private static final int BASE_TANK_SIZE = 8000;
    private final int numSlots;
    private final int tankSize;
    private final FluidTankList fluidTankList;
    private boolean workingEnabled = true;

    public MetaTileEntityAdvancedMultiFluidHatch(ResourceLocation metaTileEntityId,
                                                 int tier,
                                                 int numSlots,
                                                 boolean isExport) {
        super(metaTileEntityId, tier, isExport);
        this.numSlots = numSlots;
        this.tankSize = BASE_TANK_SIZE * (1 << Math.min(14, tier)) / (numSlots == 4 ? 4 : 8);

        FluidTank[] fluidsHandlers = new FluidTank[numSlots];
        for (int i = 0; i < fluidsHandlers.length; ++i) {
            fluidsHandlers[i] = new NotifiableFluidTank(this.tankSize, this, isExportHatch);
        }

        this.fluidTankList = new FluidTankList(false, fluidsHandlers);
        this.initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAdvancedMultiFluidHatch(metaTileEntityId, getTier(), numSlots, isExportHatch);
    }

    @Override
    protected void initializeInventory() {
        if (this.fluidTankList != null) {
            super.initializeInventory();
        }
    }

    @Override
    public void update() {
        super.update();
        if (!this.getWorld().isRemote && this.workingEnabled) {
            if (this.isExportHatch) {
                this.pushFluidsIntoNearbyHandlers(this.getFrontFacing());
            } else {
                this.pullFluidsFromNearbyHandlers(this.getFrontFacing());
            }
        }
    }

    @Override
    public void setWorkingEnabled(boolean workingEnabled) {
        this.workingEnabled = workingEnabled;
        World world = this.getWorld();
        if (world != null && !world.isRemote) {
            this.writeCustomData(GregtechDataCodes.WORKING_ENABLED, (buf) -> buf.writeBoolean(workingEnabled));
        }
    }

    @Override
    public boolean isWorkingEnabled() {
        return this.workingEnabled;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        return capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE ? GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this) : super.getCapability(capability, side);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(this.workingEnabled);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.workingEnabled = buf.readBoolean();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GregtechDataCodes.WORKING_ENABLED) {
            this.workingEnabled = buf.readBoolean();
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setBoolean("workingEnabled", this.workingEnabled);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        if (data.hasKey("workingEnabled")) {
            this.workingEnabled = data.getBoolean("workingEnabled");
        }
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (this.shouldRenderOverlay()) {
            SimpleOverlayRenderer renderer = this.numSlots == 4 ? Textures.PIPE_4X_OVERLAY : Textures.PIPE_9X_OVERLAY;
            renderer.renderSided(this.getFrontFacing(), renderState, translation, pipeline);
        }
    }

    @Override
    public void addInformation(ItemStack stack,
                                World player,
                               List<String> tooltip,
                               boolean advanced) {
        tooltip.add(I18n.format(this.isExportHatch ? "gregtech.machine.fluid_hatch.export.tooltip" : "gregtech.machine.fluid_hatch.import.tooltip"));
        tooltip.add(I18n.format("gregtech.universal.tooltip.fluid_storage_capacity_mult", this.numSlots, this.tankSize));
        tooltip.add(I18n.format("gregtech.universal.enabled"));
    }

    @Override
    public void addToolUsages(ItemStack stack,  World world, List<String> tooltip, boolean advanced) {
        super.addToolUsages(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.tool_action.screwdriver.access_covers"));
        tooltip.add(I18n.format("gregtech.tool_action.wrench.set_facing"));
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        return this.isExportHatch ? new FluidTankList(false) : this.fluidTankList;
    }

    @Override
    protected FluidTankList createExportFluidHandler() {
        return this.isExportHatch ? this.fluidTankList : new FluidTankList(false);
    }

    @Override
    public MultiblockAbility<IFluidTank> getAbility() {
        return this.isExportHatch ? MultiblockAbility.EXPORT_FLUIDS : MultiblockAbility.IMPORT_FLUIDS;
    }

    @Override
    public void registerAbilities(List<IFluidTank> abilityList) {
        abilityList.addAll(this.fluidTankList.getFluidTanks());
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        int rowSize = (int) Math.sqrt(this.numSlots);
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 18 + 18 * rowSize + 94).label(10, 5, this.getMetaFullName());

        for(int y = 0; y < rowSize; ++y) {
            for(int x = 0; x < rowSize; ++x) {
                int index = y * rowSize + x;
                builder.widget((new TankWidget(this.fluidTankList.getTankAt(index), 89 - rowSize * 9 + x * 18, 18 + y * 18, 18, 18))
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setContainerClicking(true, !this.isExportHatch)
                        .setAlwaysShowFull(true));
            }
        }

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 7, 18 + 18 * rowSize + 12);
        return builder.build(this.getHolder(), entityPlayer);
    }

}