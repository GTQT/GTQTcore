package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.cover.CoverableView;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.*;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.covers.CoverMicrowaveEnergyReceiver;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.metatileentities.multi.multiblockpart.MetaTileEntityMicrowaveEnergyReceiver;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static gregtech.api.GTValues.V;
import static gregtech.api.GTValues.VA;
import static gregtech.api.util.RelativeDirection.*;
import static keqing.gtqtcore.common.block.blocks.GTQTPowerSupply.SupplyType.POWER_SUPPLY_BASIC;

public class MetaTileEntityMicrowaveEnergyReceiverControl extends MetaTileEntityBaseWithControl {
    int coilHeight;
    int heatingCoilLevel;
    int x;
    int y;
    int z;
    long euStore;
    int op;
    int range;

    int maxLength=10;

    int [][] io=new int[64][5];
    int circuit;
    //分别为 启动？ 坐标（三位） 等级
    public MetaTileEntityMicrowaveEnergyReceiverControl(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("在输入总线放置绑定微波仓（覆盖板）的数据卡来将其存入系统对其供能，绑定的微波仓需要在多方块的供能范围内，否则不会存入系统"));
        tooltip.add(I18n.format("升级结构来获得更大的供能范围与缓存电量,最大容量为 V[Math.min(heatingCoilLevel,9)]*320*coilHeight"));
        tooltip.add(I18n.format("使用操作按钮配合加减按钮完成不同功能操作"));
        tooltip.add(I18n.format("最多管理：%s 个设备,升级线圈获得更多的管理容量",64));
    }
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setLong("euStore", euStore);
        data.setInteger("op", op);
        data.setInteger("circuit", circuit);
        data.setInteger("range", range);
        for(int i=0;i<64;i++) data.setIntArray("io"+i,io[i]);

        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        euStore = data.getLong("euStore");
        op = data.getInteger("op");
        circuit = data.getInteger("circuit");
        range = data.getInteger("range");
        for(int i=0;i<64;i++) io[i]=data.getIntArray("io"+i);
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "存储电量：%s/%s |设备上限：%s", euStore,maxStore(),maxLength));
                        if(op==0)tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "序号操作 高度 %s/等级 %s/范围半径 %s", coilHeight,heatingCoilLevel,range));
                        if(op==1)tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "电压操作 高度 %s/等级 %s/范围半径 %s", coilHeight,heatingCoilLevel,range));
                        if(op==2)tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "电流操作 高度 %s/等级 %s/范围半径 %s", coilHeight,heatingCoilLevel,range));
                        if(op==3)tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "供电操作 高度 %s/等级 %s/范围半径 %s", coilHeight,heatingCoilLevel,range));
                        if(op==4)tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "终端开关 高度 %s/等级 %s/范围半径 %s", coilHeight,heatingCoilLevel,range));
                        if(op==5)tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "全清操作 高度 %s/等级 %s/范围半径 %s", coilHeight,heatingCoilLevel,range));
                        for (int i = -1; i <= 1; i++) {

                            if (i == 0) {

                                {
                                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "序号：%s|供电：%s/开机：%s/x：%s y：%s z：%s", circuit + 1,this.io[circuit][0] == 1,getStatue(circuit),  this.io[circuit][1], this.io[circuit][2],this.io[circuit][3]));
                                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "V：%s/A：%s/T：%s/E：%s", getAmperageVoltage(1,circuit),getAmperageVoltage(0,circuit),this.io[circuit][4],getEU(circuit)));
                                }
                            } else if (circuit + i >= 0 && circuit + i < maxLength) {
                                 {
                                     tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "序号：%s|供电：%s/开机：%s/x：%s y：%s z：%s", circuit+ i + 1, this.io[circuit+i][0] == 1,getStatue(circuit+ i ), this.io[circuit+ i][1], this.io[circuit+ i][2],this.io[circuit+ i][3]));
                                     tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "V：%s/A：%s/T：%s/E：%s", getAmperageVoltage(1,circuit+i),getAmperageVoltage(0,circuit+i),this.io[circuit+ i][4],getEU(circuit+i)));
                                }
                            }
                        }
                    }
                });
    }
    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 9, "", this::decrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("序列向后"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 9, "", this::incrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("序列向前"));
        group.addWidget(new ClickButtonWidget(0, 9, 9, 9, "", this::back)
                .setButtonTexture(GuiTextures.BUTTON_CLEAR_GRID)
                .setTooltipText("操作符"));
        group.addWidget(new ClickButtonWidget(9, 9, 9, 9, "", this::stop)
                .setButtonTexture(GuiTextures.BUTTON_LOCK)
                .setTooltipText("清空单位"));
        return group;
    }

    private void incrementThreshold(Widget.ClickData clickData) {
        if(op==0)this.circuit = MathHelper.clamp(circuit + 1, 0, maxLength-1);
        if(op==1)setVA(1,0,circuit);
        if(op==2)setVA(0,1,circuit);
        if(op==3)io[circuit][0]=1;
        if(op==4)setStatue(true,circuit);
    }

    private void decrementThreshold(Widget.ClickData clickData) {
        if(op==0)this.circuit = MathHelper.clamp(circuit - 1, 0, maxLength-1);
        if(op==1)setVA(-1,0,circuit);
        if(op==2)setVA(0,-1,circuit);
        if(op==3)io[circuit][0]=0;
        if(op==4)setStatue(false,circuit);
    }

    private void back(Widget.ClickData clickData) {
        op++;
        if(op==6)op=0;
    }

    private void stop(Widget.ClickData clickData) {
        if(op==5)clean(true,0);
        else clean(false,circuit);
    }
    public long maxStore()
    {
        return V[Math.min(heatingCoilLevel,9)]*320*coilHeight;
    }
    @Override
    protected void updateFormedValid() {

        if (this.energyContainer != null && this.energyContainer.getEnergyStored() > 0 && euStore < maxStore()) {
            if(euStore+this.energyContainer.getEnergyStored()> maxStore())
            {
                euStore=maxStore();
                this.energyContainer.removeEnergy(maxStore()-euStore);
            }
            else
            {
                euStore = euStore + Math.max((int) this.energyContainer.getEnergyStored(), 0);
                this.energyContainer.removeEnergy(this.energyContainer.getEnergyStored());
            }
        }

        //更新
        if(checkLoacl(true)) for(int i=0;i<maxLength;i++)
        {
            if(io[i][0]==0&&getDistance(x,y,z)<range)
            {
                io[i][0]=1;
                io[i][1]=x;
                io[i][2]=y;
                io[i][3]=z;
                io[i][4]=getTier(i);
                checkLoacl(false);
                GTTransferUtils.insertItem(this.outputInventory, setCard(), false);
                x=0;y=0;z=0;
            }

        }
        for(int i=0;i<maxLength;i++)
        {
            if(io[i][0]==1)
            {
                if(!checkLoacl(i))
                {
                    clean(false,i);
                }
            }
        }
        //供电
        //if电多
        for(int i=0;i<maxLength;i++)
        {
            if(io[i][0]==1)
            {
                addEnergy(i,V[io[i][4]]);
            }
        }
    }
    public ItemStack setCard() {
        ItemStack card = new ItemStack(GTQTMetaItems.POS_BINDING_CARD.getMetaItem(), 1, 417);
        NBTTagCompound nodeTagCompound = new NBTTagCompound();
        nodeTagCompound.setInteger("x", x);
        nodeTagCompound.setInteger("y", y);
        nodeTagCompound.setInteger("z", z);
        card.setTagCompound(nodeTagCompound);
        return card;
    }
    //对 x y z +eu
    public void addEnergy(int point,long eu) {
        if (GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(io[point][1],io[point][2],io[point][3])) instanceof MetaTileEntity) {
            MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(io[point][1],io[point][2],io[point][3]));
            if(hasCover(mte)&&getStatue(point))
            {
                for (EnumFacing facing : EnumFacing.VALUES) {
                    if (mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                        IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);
                        int v=getAmperageVoltage(1,point);
                        int a=getAmperageVoltage(0,point);

                        if (container.getEnergyCapacity() - container.getEnergyStored() < (long) v *a) {
                            container.addEnergy(container.getEnergyCapacity() - container.getEnergyStored());
                            euStore -= (int) (container.getEnergyCapacity() - container.getEnergyStored());
                            return;
                        } else if (euStore > v*a) {
                            container.addEnergy((long) v *a);
                            euStore -= v*a;
                        } else {
                            container.addEnergy(euStore);
                            euStore = 0;
                        }
                    }
                }
            }
            else {
                for (EnumFacing facing : EnumFacing.VALUES) {
                    if (mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                        IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);

                        if (container.getEnergyCapacity() - container.getEnergyStored() < eu * 16L) {
                            container.addEnergy(container.getEnergyCapacity() - container.getEnergyStored());
                            euStore -= (int) (container.getEnergyCapacity() - container.getEnergyStored());
                            return;
                        } else if (euStore > eu * 16) {
                            container.addEnergy(eu * 16L);
                            euStore -= (int) (eu * 16);
                        } else {
                            container.addEnergy(euStore);
                            euStore = 0;
                        }
                    }
                }
            }
        }
    }
    //1为+1 -1为-1
    public void setVA(int v,int a,int point)
    {
        MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(io[point][1],io[point][2],io[point][3]));
        if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver) {
            ((MetaTileEntityMicrowaveEnergyReceiver) mte).setAmperageVoltage(v,a);
            return;
        }
        if(hasCover(mte))
            for (EnumFacing facing : EnumFacing.VALUES) {
                CoverableView coverable = mte.getCapability(GregtechTileCapabilities.CAPABILITY_COVER_HOLDER, facing);
                if (coverable.getCoverAtSide(facing) instanceof CoverMicrowaveEnergyReceiver) {
                    ((CoverMicrowaveEnergyReceiver) coverable.getCoverAtSide(facing)).setAmperageVoltage(v,a);
                    return;
                }
            }

    }

    public int getEU(int point)
    {
        if (GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(io[point][1],io[point][2],io[point][3])) instanceof MetaTileEntity) {
            MetaTileEntity mte = (MetaTileEntity) GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(io[point][1], io[point][2], io[point][3]));
            for (EnumFacing facing : EnumFacing.VALUES) {
                if (mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                    IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);
                    return (int) container.getEnergyStored();
                }
            }
        }
        return 0;
    }
    public void setStatue(boolean statue,int point)
    {
        MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(io[point][1],io[point][2],io[point][3]));
        if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver) {
            ((MetaTileEntityMicrowaveEnergyReceiver) mte).setActive(statue);
            return;
        }
        if(hasCover(mte))
            for (EnumFacing facing : EnumFacing.VALUES) {
                CoverableView coverable = mte.getCapability(GregtechTileCapabilities.CAPABILITY_COVER_HOLDER, facing);
                if (coverable.getCoverAtSide(facing) instanceof CoverMicrowaveEnergyReceiver) {
                    ((CoverMicrowaveEnergyReceiver) coverable.getCoverAtSide(facing)).setActive(statue);
                    return;
                }
            }
    }
    public boolean getStatue(int point)
    {
        MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(io[point][1],io[point][2],io[point][3]));
        if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver) {
            return ((MetaTileEntityMicrowaveEnergyReceiver) mte).active;
        }
        if(hasCover(mte))
            for (EnumFacing facing : EnumFacing.VALUES) {
                CoverableView coverable = mte.getCapability(GregtechTileCapabilities.CAPABILITY_COVER_HOLDER, facing);
                if (coverable.getCoverAtSide(facing) instanceof CoverMicrowaveEnergyReceiver) {
                    return ((CoverMicrowaveEnergyReceiver) coverable.getCoverAtSide(facing)).active;
                }
            }
        return false;
    }
    public int getAmperageVoltage(int num,int point) {
        MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(io[point][1],io[point][2],io[point][3]));
        if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver) {
            if(num==1) return ((MetaTileEntityMicrowaveEnergyReceiver) mte).Voltage;
            else return ((MetaTileEntityMicrowaveEnergyReceiver) mte).Amperage;
        }
        if(hasCover(mte))
            for (EnumFacing facing : EnumFacing.VALUES) {
                CoverableView coverable = mte.getCapability(GregtechTileCapabilities.CAPABILITY_COVER_HOLDER, facing);
                if (coverable.getCoverAtSide(facing) instanceof CoverMicrowaveEnergyReceiver) {
                    if(num==1) return ((CoverMicrowaveEnergyReceiver) coverable.getCoverAtSide(facing)).Voltage;
                    else return ((CoverMicrowaveEnergyReceiver) coverable.getCoverAtSide(facing)).Amperage;
                }

            }
        return 0;
    }
    public int getTier(int point) {
        MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(io[point][1],io[point][2],io[point][3]));
        if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver) {
            return ((MetaTileEntityMicrowaveEnergyReceiver) mte).getTier();
        }
        if(hasCover(mte))
            for (EnumFacing facing : EnumFacing.VALUES) {
                CoverableView coverable = mte.getCapability(GregtechTileCapabilities.CAPABILITY_COVER_HOLDER, facing);
                if (coverable.getCoverAtSide(facing) instanceof CoverMicrowaveEnergyReceiver) {
                    return ((CoverMicrowaveEnergyReceiver) coverable.getCoverAtSide(facing)).tier;
                }

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
        if(all)for(int i=0;i<maxLength;i++)
        {
            io[i][0]=1;
            io[i][1]=0;
            io[i][2]=0;
            io[i][3]=0;
            io[i][4]=0;
            x=0;y=0;z=0;
        }
        else
        {
            io[point][0]=0;
            io[point][1]=0;
            io[point][2]=0;
            io[point][3]=0;
            io[point][4]=0;
            x=0;y=0;z=0;
        }
    }
    public boolean checkLoacl(int point)
    {
        MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(io[point][1],io[point][2],io[point][3]));
        if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver) {
            return true;
        }
        return hasCover(mte);
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

                    MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(x,y,z));
                    if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver) {
                        this.getInputInventory().extractItem(i, 1, sim);
                        return true;
                    }
                    if(hasCover(mte))
                    {
                        this.getInputInventory().extractItem(i, 1, sim);
                        return true;
                    }
                }

            }
        }
        return false;
    }

    private boolean hasCover(MetaTileEntity te)
    {
        if(te==null)return false;
        for (EnumFacing facing : EnumFacing.VALUES) {
            CoverableView coverable = te.getCapability(GregtechTileCapabilities.CAPABILITY_COVER_HOLDER, facing);
            if(coverable.getCoverAtSide(facing) instanceof CoverMicrowaveEnergyReceiver)
                return true;
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
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("CCSCC", "CCCCC","CCCCC","CCCCC", "CCCCC")
                .aisle("C   C", "  H  "," HHH ","  H  ", "C   C").setRepeatable(5, 25)
                .aisle("CCCCC", "CCCCC","CCCCC","CCCCC", "CCCCC")
                .where('S', selfPredicate())
                .where('C', states(getCasingAState())
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2)))
                .where('H', coilPredicate())
                .build();
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        coilHeight = context.getInt("Count")/5;
        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
        } else {
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
        }
        range=coilHeight*heatingCoilLevel;
        maxLength=Math.min(heatingCoilLevel,16)*4;
    }
    private TraceabilityPredicate coilPredicate() {
        return new TraceabilityPredicate((blockWorldState) -> {
            IBlockState blockState = blockWorldState.getBlockState();
            if (blockState.getBlock() instanceof BlockWireCoil) {
                BlockWireCoil blockWireCoil = (BlockWireCoil) blockState.getBlock();
                BlockWireCoil.CoilType coilType = blockWireCoil.getState(blockState);
                Object currentCoilType = blockWorldState.getMatchContext().getOrPut("CoilType", coilType);
                if (!currentCoilType.toString().equals(coilType.getName())) {
                    blockWorldState.setError(new PatternStringError("gregtech.multiblock.pattern.error.coils"));
                    return false;
                } else {
                    blockWorldState.getMatchContext().increment("Count", 1);
                    blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>()).add(blockWorldState.getPos());
                    return true;
                }
            } else {
                return false;
            }
        }, () -> Arrays.stream(BlockWireCoil.CoilType.values()).map((type) -> new BlockInfo(MetaBlocks.WIRE_COIL.getState(type), null)).toArray(BlockInfo[]::new)).addTooltips("gregtech.multiblock.pattern.error.coils");
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
    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), true,
                isStructureFormed());
    }
    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }
}
