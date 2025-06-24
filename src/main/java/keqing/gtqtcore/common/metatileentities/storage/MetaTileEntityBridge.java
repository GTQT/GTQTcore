package keqing.gtqtcore.common.metatileentities.storage;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.unification.material.Material;
import gregtech.client.renderer.ICubeRenderer;
import keqing.gtqtcore.api.metatileentity.multiblock.logistics.MetaTileEntityDelegator;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;

import java.util.function.Predicate;

/**
 * Copyright (C) SymmetricDevs 2025
 * 由 MeowmelMuku 于 2025 修改。
 * 此文件遵循 GPL-3.0 许可证，详情请见项目根目录的 LICENSE 文件。
 */
public class MetaTileEntityBridge extends MetaTileEntityDelegator {

    protected final ICubeRenderer renderer;

    public MetaTileEntityBridge(ResourceLocation metaTileEntityId, Predicate<Capability<?>> capFilter, ICubeRenderer renderer, Material baseMaterial) {
        this(metaTileEntityId, capFilter, renderer, baseMaterial.getMaterialRGB());
    }

    public MetaTileEntityBridge(ResourceLocation metaTileEntityId, Predicate<Capability<?>> capFilter, ICubeRenderer renderer, int baseColor) {
        super(metaTileEntityId, capFilter, baseColor);
        this.renderer = renderer;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityBridge(metaTileEntityId, capFilter, renderer, baseColor);
    }

    @Override
    public EnumFacing getDelegatingFacing(EnumFacing facing) {
        return facing == null ? null : facing.getOpposite();
    }

    @Override
    public boolean hasFrontFacing() {
        return false;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.renderer.render(renderState, translation, pipeline);
    }
}