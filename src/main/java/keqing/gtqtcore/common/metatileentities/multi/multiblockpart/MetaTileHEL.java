package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.TextFieldWidget2;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.util.GTUtility;
import gregtech.client.particle.GTParticleManager;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;
import keqing.gtqtcore.api.capability.ILaser;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.client.particle.LaserBeamParticle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.List;

import static gregtech.api.GTValues.*;
import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.LASER_INPUT;
import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.LASER_OUTPUT;
import static net.minecraft.util.EnumFacing.DOWN;
import static net.minecraft.util.EnumFacing.UP;

public class MetaTileHEL extends MetaTileEntityMultiblockNotifiablePart implements IMultiblockAbilityPart<ILaser>, ILaser {

    public int Amperage;
    public int Voltage;

    long Laser;//当前的激光
    long MaxLaser;
    long SetLaser;// 设定 的 最大激光强度
    int tier;
    int MaxLength=64;
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("Amperage", Amperage);
        data.setInteger("Voltage", Voltage);
        data.setLong("Laser", Laser);
        data.setLong("SetLaser", SetLaser);
        data.setLong("MaxLaser", MaxLaser);
        data.setInteger("X", TargetPos.getX());
        data.setInteger("Y", TargetPos.getY());
        data.setInteger("Z", TargetPos.getZ());

        data.setBoolean("findTarget",findTarget);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        Amperage = data.getInteger("Amperage");
        Voltage = data.getInteger("Voltage");
        Laser = data.getLong("Laser");
        SetLaser = data.getLong("SetLaser");
        MaxLaser = data.getLong("MaxLaser");

        TargetPos= new BlockPos(data.getInteger("X"), data.getInteger("Y"), data.getInteger("Z"));

        findTarget=data.getBoolean("findTarget");
    }


    BlockPos TargetPos = new BlockPos(0, 0, 0);
    boolean findTarget = false;

    public MetaTileHEL(ResourceLocation metaTileEntityId, int tier, boolean isExportHatch) {
        super(metaTileEntityId, tier, isExportHatch);
        this.tier = tier;
        this.MaxLaser =V[tier] * 4;
        this.initializeInventory();
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileHEL(this.metaTileEntityId, this.getTier(), this.isExportHatch);
    }

    public void update() {
        super.update();
        if(Voltage>=14||Voltage<0)Voltage=0;
        if(Amperage>4||Amperage<0)Amperage=0;

        SetLaser= Amperage*V[Voltage];
        Laser=Math.min(Laser, SetLaser);

        if (!isExportHatch) return;//如果是输入激光端就等着艾草就行了

        //如果是输出端就去找操谁

        final int xDir = this.getFrontFacing().getOpposite().getXOffset();
        final int zDir = this.getFrontFacing().getOpposite().getZOffset();
        if (!findTarget) {
            for (int i = 1; i <= MaxLength; i++) {
                //注意 查找是往反向找！！！！！！！
                if(!findTarget) {
                    if (this.getFrontFacing() == UP) {
                        if (GTUtility.getMetaTileEntity(this.getWorld(), this.getPos().add(0, i, 0)) instanceof MetaTileHEL target) {
                            TargetPos = this.getPos().add(0, i, 0);
                            //只要不是输入端就一直找
                            if (!target.isExportHatch) findTarget = true;
                        }
                    } else if (this.getFrontFacing() == DOWN) {
                        if (GTUtility.getMetaTileEntity(this.getWorld(), this.getPos().add(0, -i, 0)) instanceof MetaTileHEL target) {
                            TargetPos = this.getPos().add(0, -i, 0);
                            //只要不是输入端就一直找
                            if (!target.isExportHatch) findTarget = true;
                        }
                    } else if (GTUtility.getMetaTileEntity(this.getWorld(), this.getPos().add(-xDir * i, 0, -zDir * i)) instanceof MetaTileHEL target) {
                        TargetPos = this.getPos().add(-xDir * i, 0, -zDir * i);
                        //只要不是输入端就一直找
                        if (!target.isExportHatch) findTarget = true;
                    }
                }
            }
        } else {
            if (GTUtility.getMetaTileEntity(this.getWorld(), TargetPos) instanceof MetaTileHEL target) {
                if (target.isExportHatch)//你怎么突然变成输出端了
                {
                    findTarget = false;
                } else {
                    writeCustomData(GregtechDataCodes.UPDATE_PARTICLE, this::writeParticles);
                    target.setLaser(Laser);
                    //目标最大容量就是他的最大额定接收
                }
            } else//我超 找不到了
            {
                findTarget = false;
            }
        }

    }

    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (this.shouldRenderOverlay()) {
            if (this.isExportHatch) {
                Textures.LASER_SOURCE.renderSided(this.getFrontFacing(), renderState, translation, pipeline);
            } else {
                Textures.LASER_TARGET.renderSided(this.getFrontFacing(), renderState, translation, pipeline);
            }
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.defaultBuilder();

        if (isExportHatch) builder.dynamicLabel(7, 10, () -> "激光传输器输出端-等级：" + tier, 0x232323);

        else builder.dynamicLabel(7, 10, () -> "激光传输器输入端-等级：" + tier, 0x232323);
        if(isExportHatch)builder.dynamicLabel(7, 20, () -> "绑定状态：" + findTarget+" "+TargetPos, 0x232323);

        builder.label(7, 32, "折算电压 面向："+this.getFrontFacing());
        builder.widget(new ClickButtonWidget(7, 45, 20, 20, "-", data -> Voltage = --Voltage == -1 ? 0 : Voltage));
        builder.widget(new ImageWidget(29, 45, 118, 20, GuiTextures.DISPLAY));
        builder.widget(new TextFieldWidget2(31, 51, 114, 16, () -> String.valueOf(Voltage), value1 -> {
            if (!value1.isEmpty()) {
                Voltage = Integer.parseInt(value1);
            }
        }).setMaxLength(10).setNumbersOnly(0, tier));
        builder.widget(new ClickButtonWidget(149, 45, 20, 20, "+", data -> {
            if (Voltage <tier) {
                Voltage++;
            }
        }));

        builder.label(7, 70, "折算电流");
        builder.widget(new ClickButtonWidget(7, 80, 20, 20, "-", data -> Amperage = --Amperage == -1 ? 0 : Amperage));
        builder.widget(new ImageWidget(29, 80, 118, 20, GuiTextures.DISPLAY));
        builder.widget(new TextFieldWidget2(31, 86, 114, 16, () -> String.valueOf(Amperage), value2 -> {
            if (!value2.isEmpty()) {
                Amperage = Integer.parseInt(value2);
            }
        }).setMaxLength(10).setNumbersOnly(0, 4));
        builder.widget(new ClickButtonWidget(149, 80, 20, 20, "+", data -> {
            if (Amperage < 4) {
                Amperage++;
            }
        }));

        builder.widget(new ClickButtonWidget(7, 105, 80, 20, "=MAX", data -> {
            Voltage=tier;
            Amperage=4;
        }));
        builder.widget(new ClickButtonWidget(90, 105, 80, 20, "=0", data -> SetLaser = 0));

        builder.widget((new AdvancedTextWidget(7, 130, this::addDisplayText, 2302755)).setMaxWidthLimit(181));

        return builder.build(getHolder(), entityPlayer);
    }
    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentString( "实际 传输激光: " + Laser+" "+VN[GTUtility.getTierByVoltage(Laser)]));
        textList.add(new TextComponentString( "额定 传输激光: " + SetLaser+" "+VN[GTUtility.getTierByVoltage(SetLaser)]));
        textList.add(new TextComponentString( "最大 传输激光: " + MaxLaser+" "+VN[GTUtility.getTierByVoltage(MaxLaser)]));

    }
    @Override
    public MultiblockAbility<ILaser> getAbility() {
        return this.isExportHatch ? LASER_OUTPUT : LASER_INPUT;
    }

    @Override
    public void registerAbilities(List<ILaser> list) {
        list.add(this);
    }
    @Override
    public void setLaser(long i)
    {
        Laser =Math.min(i,this.SetLaser);
    }
    @Override
    public long Laser() {
        return Laser;
    }
    @Override
    public long MaxLaser()
    {
        return MaxLaser;
    }
    @Override
    public long SetLaser() {
        return SetLaser;
    }

    @Override
    public int Voltage() {
        return Voltage;
    }

    @Override
    public int Amperage() {
        return Amperage;
    }
    @Override
    public int tier() {
        return getTier();
    }
    @Override
    public void addVoltage(int i)
    {
        if(i>0) {
            if (Voltage + i <= tier) Voltage += i;
            else Voltage = tier;
        }
        else
        {
            if(Voltage+i>=0)Voltage+=i;
            else Voltage=0;
        }
    }
    @Override
    public void addAmperage(int i)
    {
        if(i>0) {
            if (Amperage + i <= 4) Amperage += i;
            else Amperage = 4;
        }
        else
        {
            if(Amperage+i>=0)Amperage +=i;
            else Amperage=0;
        }
    }

    @Override
    public boolean isExport() {
        return isExportHatch;
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        writeParticles(buf);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        try {
            readParticles(buf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GregtechDataCodes.UPDATE_PARTICLE) {
            try {
                readParticles(buf);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    private void writeParticles(PacketBuffer buf) {
        NBTTagCompound tag = new NBTTagCompound();
        if(findTarget)
        {
            tag.setInteger("Sx",this.getPos().getX());
            tag.setInteger("Sy",this.getPos().getY());
            tag.setInteger("Sz",this.getPos().getZ());
            tag.setInteger("Ex",TargetPos.getX());
            tag.setInteger("Ey",TargetPos.getY());
            tag.setInteger("Ez",TargetPos.getZ());

        }
        buf.writeCompoundTag(tag);
    }
    @SideOnly(Side.CLIENT)
    private void readParticles( PacketBuffer buf) throws IOException {
        NBTTagCompound tag = buf.readCompoundTag();
        if(tag.hasKey("Ex"))
        {
            BlockPos Spos = new BlockPos(tag.getInteger("Sx"),tag.getInteger("Sy"),tag.getInteger("Sz"));
            BlockPos Epos = new BlockPos(tag.getInteger("Ex"),tag.getInteger("Ey"),tag.getInteger("Ez"));
            operateClient(Spos,Epos,20);
        }

    }
    @SideOnly(Side.CLIENT)
    public void operateClient(BlockPos Spos,BlockPos Epos,int age) {
        GTParticleManager.INSTANCE.addEffect(new LaserBeamParticle(this,Spos,Epos,age));
    }
}
