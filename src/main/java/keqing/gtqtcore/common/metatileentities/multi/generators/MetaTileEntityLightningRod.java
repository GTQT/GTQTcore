package keqing.gtqtcore.common.metatileentities.multi.generators;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleSidedCubeRenderer;
import gregtech.client.utils.PipelineUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static gregtech.api.capability.GregtechDataCodes.IS_WORKING;

public class MetaTileEntityLightningRod extends TieredMetaTileEntity {
    private boolean isActive = false;

    public MetaTileEntityLightningRod(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
    }

    protected void reinitializeEnergyContainer() {
        if (isEnergyEmitter()) {
            this.energyContainer =
                    EnergyContainerHandler.emitterContainer(this,
                            getEnergyCapacity(),
                            GTValues.V[getTier()],
                            getMaxInputOutputAmperage());
        }
    }

    @Override
    public void update() {
        super.update();
        if (this.getWorld().isRemote) return;
        World aWorld = this.getWorld();
        Random aXSTR = new Random(); // 替换为Random
        if (!aWorld.isRemote) {
            if (this.energyContainer.getEnergyStored() > 0) {
                this.setActive(true);
                this.energyContainer.removeEnergy(this.energyContainer.getEnergyStored() / 100 + 1);
            } else {
                this.setActive(false);
            }
            if (getOffsetTimer() % 256 == 0 && (aWorld.isThundering() || (aWorld.isRaining() && aXSTR.nextInt(10) == 0))) {
                int aRodValue = 0;
                boolean isRodValid = true;
                int aX = this.getPos().getX();
                int aY = this.getPos().getY();
                int aZ = this.getPos().getZ();
                for (int i = this.getPos().getY() + 1; i < aWorld.getHeight() - 1; i++) {
                    if (isRodValid && this.getWorld().getBlockState(new BlockPos(aX, i, aZ)).getBlock().equals(Blocks.IRON_BARS)) {
                        aRodValue++;
                    } else {
                        isRodValid = false;
                        if (this.getWorld().getBlockState(new BlockPos(aX, i, aZ)).getBlock() != Blocks.AIR) {
                            aRodValue = 0;
                            break;
                        }
                    }
                }
                if (!aWorld.isThundering() && ((aY + aRodValue) < 128)) aRodValue = 0;
                if (aXSTR.nextInt(4 * aWorld.getHeight()) < (aRodValue * (aY + aRodValue))) {
                    this.energyContainer.addEnergy(getEnergyCapacity() - this.energyContainer.getEnergyStored());
                    aWorld.addWeatherEffect(new EntityLightningBolt(aWorld, aX, aY + aRodValue, aZ, false));
                }
            }
        }
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }

    private void setActive(boolean isActive) {
        if (this.isActive != isActive) {
            this.isActive = isActive;
            if (!getWorld().isRemote) {
                writeCustomData(IS_WORKING, w -> w.writeBoolean(isActive));
            }
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == IS_WORKING) {
            this.isActive = buf.readBoolean();
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(isActive);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.isActive = buf.readBoolean();
    }

    @Override
    protected boolean isEnergyEmitter() {
        return true;
    }

    @Override
    public boolean getIsWeatherOrTerrainResistant() {
        return true;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityLightningRod(metaTileEntityId, getTier());
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return false;
    }

    public long getEnergyCapacity() {
        return 500000000;
    }

    protected long getMaxInputOutputAmperage() {
        return 512;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null; //No GUI Machine
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        GTQTTextures.LIGHTNING_ROD_OVERLAY.renderOrientedState(renderState, translation, pipeline, getFrontFacing(), isActive(), isActive());
        for(EnumFacing facing : EnumFacing.HORIZONTALS) Textures.ENERGY_OUT.renderSided(facing, renderState, translation, PipelineUtil.color(pipeline, GTValues.VC[getTier()]));
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected SimpleSidedCubeRenderer getBaseRenderer() {
        return Textures.VOLTAGE_CASINGS[getTier()];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Pair<TextureAtlasSprite, Integer> getParticleTexture() {
        return Pair.of(getBaseRenderer().getParticleSprite(), getPaintingColorForRendering());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        String key = this.metaTileEntityId.getPath().split("\\.")[0];
        String mainKey = String.format("gregtech.machine.%s.tooltip", key);
        if (I18n.hasKey(mainKey)) {
            tooltip.add(1, I18n.format(mainKey));
        }
        tooltip.add(I18n.format("gregtech.universal.tooltip.voltage_out", energyContainer.getOutputVoltage(), GTValues.VNF[getTier()]));
        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_storage_capacity", energyContainer.getEnergyCapacity()));
        tooltip.add(I18n.format("gtqtcore.machine.lightning_rod.usage"));
    }
}