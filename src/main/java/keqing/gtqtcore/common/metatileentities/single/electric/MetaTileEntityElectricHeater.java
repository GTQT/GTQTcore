package keqing.gtqtcore.common.metatileentities.single.electric;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import keqing.gtqtcore.common.metatileentities.multi.multiblockpart.MetaTileEntityHeatHatch;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import static gregtech.api.GTValues.VA;

public class MetaTileEntityElectricHeater extends TieredMetaTileEntity {
    int tier;

    public MetaTileEntityElectricHeater(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.tier = tier;
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityElectricHeater(metaTileEntityId, getTier());
    }
    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }
    private BlockPos getBackPos(BlockPos pos, EnumFacing facing) {
        return pos.offset(facing.getOpposite());
    }
    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote) {
            BlockPos backPos = getBackPos(this.getPos(), this.getFrontFacing());
            MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), backPos);
            if (mte instanceof MetaTileEntityHeatHatch) {
                if (energyContainer.getEnergyStored() > VA[tier]) {
                    energyContainer.changeEnergy(-VA[tier]);

                    ((MetaTileEntityHeatHatch) mte).balanceHeat(500 * tier, 1);
                }
            }
        }
    }
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("接触热源温度：%s 接触率：%s", 500 * tier, 1));
    }
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.HPCA_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }
    @Override
    protected boolean isEnergyEmitter() {
        return false;
    }
}
