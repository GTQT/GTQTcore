package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import gregtech.client.utils.PipelineUtil;
import gregtech.common.metatileentities.MetaTileEntities;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntitySubstationEnergyHatch;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MetaTileEntityPlusEnergyHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IEnergyContainer> {
    protected final boolean isExportHatch;
    protected final int amperage;
    protected final IEnergyContainer energyContainer;

    public MetaTileEntityPlusEnergyHatch(ResourceLocation metaTileEntityId, int tier, int amperage, boolean isExportHatch) {
        super(metaTileEntityId, tier);
        this.isExportHatch = isExportHatch;
        this.amperage = amperage;
        if (isExportHatch) {
            this.energyContainer = EnergyContainerHandler.emitterContainer(this, GTValues.V[tier] * 64L * (long)amperage, GTValues.V[tier], (long)amperage);
            ((EnergyContainerHandler)this.energyContainer).setSideOutputCondition((s) -> {
                return s == this.getFrontFacing();
            });
        } else {
            this.energyContainer = EnergyContainerHandler.receiverContainer(this, GTValues.V[tier] * 16L * (long)amperage, GTValues.V[tier], (long)amperage);
        }

    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new keqing.gtqtcore.common.metatileentities.multi.multiblockpart.MetaTileEntityPlusEnergyHatch(this.metaTileEntityId, this.getTier(), this.amperage, this.isExportHatch);
    }

    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (this.shouldRenderOverlay()) {
            this.getOverlay().renderSided(this.getFrontFacing(), renderState, translation, PipelineUtil.color(pipeline, GTValues.VC[this.getTier()]));
        }

    }

    public void update() {
        super.update();
        this.checkWeatherOrTerrainExplosion((float)this.getTier(), (double)(this.getTier() * 10), this.energyContainer);
    }

    @Nonnull
    private SimpleOverlayRenderer getOverlay() {
        if (this.isExportHatch) {
            if (this.amperage <= 64) {
                return Textures.ENERGY_OUT_MULTI;
            } else if (this.amperage <= 128) {
                return Textures.ENERGY_OUT_HI;
            } else {
                return this.amperage <= 512 ? Textures.ENERGY_OUT_ULTRA : Textures.ENERGY_OUT_MAX;
            }
        } else if (this.amperage <= 64) {
            return Textures.ENERGY_IN_MULTI;
        } else if (this.amperage <= 128) {
            return Textures.ENERGY_IN_HI;
        } else {
            return this.amperage <= 512 ? Textures.ENERGY_IN_ULTRA : Textures.ENERGY_IN_MAX;
        }
    }

    public MultiblockAbility<IEnergyContainer> getAbility() {
        return this.isExportHatch ? MultiblockAbility.OUTPUT_ENERGY : MultiblockAbility.INPUT_ENERGY;
    }

    public void registerAbilities(List<IEnergyContainer> abilityList) {
        abilityList.add(this.energyContainer);
    }

    protected boolean openGUIOnRightClick() {
        return false;
    }

    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, boolean advanced) {
        String tierName = GTValues.VNF[this.getTier()];
        this.addDescriptorTooltip(stack, world, tooltip, advanced);
        if (this.isExportHatch) {
            tooltip.add(I18n.format("gregtech.universal.tooltip.voltage_out", new Object[]{this.energyContainer.getOutputVoltage(), tierName}));
            tooltip.add(I18n.format("gregtech.universal.tooltip.amperage_out_till", new Object[]{this.energyContainer.getOutputAmperage()}));
        } else {
            tooltip.add(I18n.format("gregtech.universal.tooltip.voltage_in", new Object[]{this.energyContainer.getInputVoltage(), tierName}));
            tooltip.add(I18n.format("gregtech.universal.tooltip.amperage_in_till", new Object[]{this.energyContainer.getInputAmperage()}));
        }

        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_storage_capacity", new Object[]{this.energyContainer.getEnergyCapacity()}));
        tooltip.add(I18n.format("gregtech.universal.enabled", new Object[0]));
    }

    protected void addDescriptorTooltip(ItemStack stack, @Nullable World world, List<String> tooltip, boolean advanced) {
        if (this.isExportHatch) {
            if (this.amperage > 2) {
                tooltip.add(I18n.format("gregtech.machine.energy_hatch.output_hi_amp.tooltip", new Object[0]));
            } else {
                tooltip.add(I18n.format("gregtech.machine.energy_hatch.output.tooltip", new Object[0]));
            }
        } else if (this.amperage > 2) {
            tooltip.add(I18n.format("gregtech.machine.energy_hatch.input_hi_amp.tooltip", new Object[0]));
        } else {
            tooltip.add(I18n.format("gregtech.machine.energy_hatch.input.tooltip", new Object[0]));
        }

    }

    public void addToolUsages(ItemStack stack, @Nullable World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.tool_action.screwdriver.access_covers", new Object[0]));
        tooltip.add(I18n.format("gregtech.tool_action.wrench.set_facing", new Object[0]));
        super.addToolUsages(stack, world, tooltip, advanced);
    }

    public boolean canRenderFrontFaceX() {
        return this.isExportHatch;
    }


    public void doExplosion(float explosionPower) {
        if (this.getController() != null) {
            this.getController().explodeMultiblock(explosionPower);
        } else {
            super.doExplosion(explosionPower);
        }

    }
}

