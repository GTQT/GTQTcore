package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityElectricBlastFurnace;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.metatileentities.multi.multiblockpart.MetaTileEntityMicrowaveEnergyReceiver;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

import java.util.Collections;
import java.util.List;

import static keqing.gtqtcore.common.block.blocks.GTQTPowerSupply.SupplyType.POWER_SUPPLY_BASIC;

public class MetaTileEntityMicrowaveEnergyReceiverControl extends MetaTileEntityBaseWithControl {

    int x;
    int y;
    int z;
    int point;
    int statue;
    int [][] io=new int[7][10];
    //分别为 启动？ 坐标（三位） 电压 电流 距离
    public MetaTileEntityMicrowaveEnergyReceiverControl(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {

        //常态刷新
        for(int i=0;i<10;i++)
        {
            if(io[i][0]==1)
            {
                io[i][4]=getAmperageVoltage(1,i);
                io[i][5]=getAmperageVoltage(0,i);
            }
        }
        //更新
        if(checkLoacl(true)) for(int i=0;i<10;i++)
        {
            if(io[i][0]==0)
            {
                io[i][0]=1;
                io[i][1]=x;
                io[i][2]=y;
                io[i][3]=z;
                io[i][6]=getDistance(x,y,z);
                x=0;y=0;z=0;
            }
        }
        //供电
        //if电多
        for(int i=0;i<10;i++)
        {
            if(io[i][0]==1)
            {
                //addEnergy(i,eu);
            }
        }
    }
    //对 x y z +eu
    public void addEnergy(int point,int eu) {
        if (GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(io[point][1],io[point][2],io[point][3])) instanceof MetaTileEntity) {
            MetaTileEntity mte = (MetaTileEntity) GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(io[point][1],io[point][2],io[point][3]));
            for (EnumFacing facing : EnumFacing.VALUES) {
                if (mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                    IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);
                    //container.addEnergy(eu);
                    //EUstore-=eu;
                }
            }
        }
    }
    //1为+1 0为-1
    public void setVA(int v,int a,int point)
    {
        MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(io[point][1],io[point][2],io[point][3]));
        if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver) {
            ((MetaTileEntityMicrowaveEnergyReceiver) mte).setAmperageVoltage(v,a);
        }
    }
    public int getAmperageVoltage(int num,int point) {
        MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(io[point][1],io[point][2],io[point][3]));
        if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver) {
            if(num==1) return ((MetaTileEntityMicrowaveEnergyReceiver) mte).Voltage;
            else return ((MetaTileEntityMicrowaveEnergyReceiver) mte).Amperage;
        }
        return 0;
    }
    //距离计算
    public int getDistance(int x,int y,int z)
    {
        double dx=x-this.getPos().getX();
        double dy=y-this.getPos().getY();
        double dz=z-this.getPos().getZ();
        return (int)Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    //清空 all为所有 否则需要指定point
    public void clean(boolean all,int point)
    {
        if(all)for(int i=0;i<10;i++)
        {
            io[i][0]=1;
            io[i][1]=0;
            io[i][2]=0;
            io[i][3]=0;
            x=0;y=0;z=0;
        }
        else
        {
            io[point][0]=0;
            io[point][1]=0;
            io[point][2]=0;
            io[point][3]=0;
            x=0;y=0;z=0;
        }
    }
    //通过物品获取坐标
    public boolean checkLoacl(boolean sim) {
        var slots = this.getInputInventory().getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack item = this.getInputInventory().getStackInSlot(i);
            if (item.getItem() == GTQTMetaItems.GTQT_META_ITEM && item.getMetadata() == GTQTMetaItems.POS_BINDING_CARD.getMetaValue()) {
                NBTTagCompound compound = item.getTagCompound();
                if (compound != null && compound.hasKey("x") && compound.hasKey("y") && compound.hasKey("z")) {
                    x = compound.getInteger("x");
                    y = compound.getInteger("y");
                    z = compound.getInteger("z");
                }
                this.getInputInventory().extractItem(i, 1, sim);
                return true;
            }
        }
        return false;
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityMicrowaveEnergyReceiverControl(metaTileEntityId);
    }

    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCCCCCCCC")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("CCCCCSCCCCC")
                .where('S', selfPredicate())
                .where(' ', any())
                .where('C', states(getCasingAState())
                        .or(abilities(MultiblockAbility.OUTPUT_ENERGY).setMaxLayerLimited(4).setMinGlobalLimited(0))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMaxLayerLimited(4).setMinGlobalLimited(0)))
                .build();
    }
    private IBlockState getCasingAState() {
        return GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BASIC);
    }
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.IRIDIUM_CASING;
    }
    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }
    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.isActive(),
                true);
    }
}
