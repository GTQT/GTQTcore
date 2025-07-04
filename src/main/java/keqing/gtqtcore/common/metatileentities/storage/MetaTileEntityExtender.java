package keqing.gtqtcore.common.metatileentities.storage;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.unification.material.Material;
import keqing.gtqtcore.api.metatileentity.multiblock.logistics.MetaTileEntityDelegator;
import keqing.gtqtcore.client.render.ExtenderRender;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;
import java.util.function.Predicate;

import static gregtech.api.capability.GregtechDataCodes.UPDATE_OUTPUT_FACING;

/**
 * Copyright (C) SymmetricDevs 2025
 * 由 MeowmelMuku 于 2025 修改。
 * 此文件遵循 GPL-3.0 许可证，详情请见项目根目录的 LICENSE 文件。
 */
public class MetaTileEntityExtender extends MetaTileEntityDelegator {

    protected final ExtenderRender renderer;
    protected EnumFacing inputFacing;

    public MetaTileEntityExtender(ResourceLocation metaTileEntityId, Predicate<Capability<?>> capFilter, ExtenderRender renderer, Material baseMaterial) {
        this(metaTileEntityId, capFilter, renderer, baseMaterial.getMaterialRGB());
    }

    public MetaTileEntityExtender(ResourceLocation metaTileEntityId, Predicate<Capability<?>> capFilter, ExtenderRender renderer, int baseColor) {
        super(metaTileEntityId, capFilter, baseColor);
        this.renderer = renderer;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityExtender(metaTileEntityId, capFilter, renderer, baseColor);
    }

    @Override
    public EnumFacing getDelegatingFacing(EnumFacing facing) {
        return facing == getFrontFacing() ? inputFacing : getFrontFacing();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.renderer.render(renderState, translation, pipeline, getFrontFacing(), inputFacing);
    }

    @Override
    public boolean onWrenchClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                 CuboidRayTraceResult hitResult) {
        if (!playerIn.isSneaking()) {
            if (getInputFacing() == facing || facing == getFrontFacing()) return false;
            if (!getWorld().isRemote) {
                setInputFacing(facing);
            }
            return true;
        }
        return super.onWrenchClick(playerIn, hand, facing, hitResult);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("InputFacing", getInputFacing().getIndex());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.inputFacing = EnumFacing.VALUES[data.getInteger("InputFacing")];
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeByte(getInputFacing().getIndex());
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.inputFacing = EnumFacing.VALUES[buf.readByte()];
    }


    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == UPDATE_OUTPUT_FACING) {
            this.inputFacing = EnumFacing.VALUES[buf.readByte()];
            scheduleRenderUpdate();
        }
    }

    @Override
    public boolean isValidFrontFacing(EnumFacing facing) {
        return facing != getFrontFacing() && facing != inputFacing;
    }

    @Override
    public void setFrontFacing(EnumFacing frontFacing) {
        super.setFrontFacing(frontFacing);
        if (this.inputFacing == null) {
            // set initial input facing as opposite to output (front)
            setInputFacing(frontFacing.getOpposite());
        }
    }

    public EnumFacing getInputFacing() {
        return inputFacing == null ? EnumFacing.SOUTH : inputFacing;
    }

    public void setInputFacing(EnumFacing inputFacing) {
        this.inputFacing = inputFacing;
        if (!getWorld().isRemote) {
            notifyBlockUpdate();
            writeCustomData(UPDATE_OUTPUT_FACING, buf -> buf.writeByte(inputFacing.getIndex()));
            markDirty();
        }
    }

    @Override
    public boolean needsSneakToRotate() {
        return true;
    }

    @Override
    public void addToolUsages(ItemStack stack, World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.tool_action.wrench.set_facing"));
        super.addToolUsages(stack, world, tooltip, advanced);
    }
}