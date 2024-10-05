package keqing.gtqtcore.common.metatileentities.single.electric;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOrientedCubeRenderer;
import gregtech.client.renderer.texture.custom.FireboxActiveRenderer;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityFusionReactor;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.huge.MetaTileEntityHugeFusionReactor;
import keqing.gtqtcore.common.metatileentities.multi.multiblockpart.MetaTileEntityMicrowaveEnergyReceiver;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

import static gregtech.api.GTValues.V;
import static gregtech.api.GTValues.VN;

public class MetaTileLaserBooster extends MetaTileEntity{
    private final int tier;
    private boolean outputLaser;
    private boolean findMachine;
    private long LaserStoreMax;
    private long LaserStore;
    protected ICubeRenderer hatchTexture = null;
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setLong("LaserStoreMax", LaserStoreMax);
        data.setLong("LaserStore", LaserStore);
        data.setBoolean("outputLaser", outputLaser);
        data.setBoolean("findMachine", findMachine);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        LaserStoreMax = data.getLong("LaserStoreMax");
        LaserStore = data.getLong("LaserStore");
        outputLaser = data.getBoolean("outputLaser");
        findMachine = data.getBoolean("findMachine");
    }

    public MetaTileLaserBooster(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId);
        this.tier=tier;
        this.LaserStoreMax=V[tier]*32*10;
    }
    @Override
    public void update() {
        super.update();
        final int xDir = this.getFrontFacing().getOpposite().getXOffset();
        final int zDir = this.getFrontFacing().getOpposite().getZOffset();

        try {
            MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), this.getPos().add(xDir, 0, zDir));
            if (mte instanceof MetaTileEntityFusionReactor || mte instanceof MetaTileEntityHugeFusionReactor) {
                findMachine = true;
                processEnergyTransfer(mte);
            } else {
                findMachine = false;
            }
        } catch (NullPointerException e) {
            findMachine = false;
        }
    }

    private void processEnergyTransfer(MetaTileEntity mte) {
        if (outputLaser) {
            IEnergyContainer energyContainer = getEnergyContainer(mte);
            if (energyContainer != null) {
                long availableCapacity = energyContainer.getEnergyCapacity() - energyContainer.getEnergyStored();
                if (availableCapacity > LaserStore) {
                    energyContainer.addEnergy(LaserStore);
                    LaserStore = 0;
                } else {
                    energyContainer.addEnergy(availableCapacity);
                    LaserStore -= availableCapacity;
                }
            }
        }
    }

    private IEnergyContainer getEnergyContainer(MetaTileEntity mte) {
        if (mte instanceof MetaTileEntityFusionReactor) {
            return ((MetaTileEntityFusionReactor) mte).getEnergyContainer();
        } else if (mte instanceof MetaTileEntityHugeFusionReactor) {
            return ((MetaTileEntityHugeFusionReactor) mte).getEnergyContainer();
        }
        return null;
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileLaserBooster(metaTileEntityId,tier);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.defaultBuilder();
        builder.dynamicLabel(7, 10, () -> "激光点火器-等级：" + tier, 0x232323);

        builder.widget((new AdvancedTextWidget(7, 30, this::addDisplayText, 2302755)).setMaxWidthLimit(181));

        builder.widget(new ClickButtonWidget(7, 90, 80, 20, "=ON", data -> outputLaser=true));
        builder.widget(new ClickButtonWidget(90, 90, 80, 20, "=OFF", data -> outputLaser=false));

        return builder.build(getHolder(), entityPlayer);
    }
    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentString("缓存激光: " + LaserStore + " " + VN[GTUtility.getTierByVoltage(LaserStore)]));
        textList.add(new TextComponentString("最大缓存: " + LaserStoreMax + " " + VN[GTUtility.getTierByVoltage(LaserStoreMax)]));
        textList.add(new TextComponentString("输出开关: " + outputLaser));
        textList.add(new TextComponentString("聚变对象: " + findMachine));

    }
    public void addLaser(long amount)
    {
        if(LaserStore+amount<LaserStoreMax)LaserStore+=Math.min(amount,V[tier]*16);
        else LaserStore=LaserStoreMax;
    }

    public Pair<TextureAtlasSprite, Integer> getParticleTexture() {
        return Pair.of(this.getBaseTexture().getParticleSprite(), this.getPaintingColorForRendering());
    }

    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        ICubeRenderer baseTexture = this.getBaseTexture();
        pipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering())));
        if (!(baseTexture instanceof FireboxActiveRenderer) && !(baseTexture instanceof SimpleOrientedCubeRenderer)) {
            baseTexture.render(renderState, translation, pipeline);
        } else {
            baseTexture.renderOriented(renderState, translation, pipeline, this.getFrontFacing());
        }
        Textures.LASER_TARGET.renderSided(this.getFrontFacing(), renderState, translation, pipeline);

    }
    public ICubeRenderer getBaseTexture() {
        return Textures.VOLTAGE_CASINGS[this.tier];
    }
    public boolean shouldRenderOverlay() {
        return true;
    }

    public boolean isValidFrontFacing(EnumFacing facing) {
        return true;
    }

    public int getTier() {
        return this.tier;
    }
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("将本设备的背面对准 核聚变控制器（MK I-MK VI）"));
        tooltip.add(I18n.format("使用高能激光对本设备进行充能，充能通量上限：Math.min(amount,V[tier]*16)"));
        tooltip.add(I18n.format("蓄能上限：V[tier]*16*500"));
        tooltip.add(I18n.format("使用功能按钮可以瞬间将缓存的能量提供给核聚变作为开机热量"));
    }
}
