package keqing.gtqtcore.common.metatileentities.storage;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.*;
import gregtech.api.capability.impl.FilteredItemHandler;
import gregtech.api.capability.impl.FluidHandlerProxy;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.cover.CoverRayTracer;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.IGuiTexture;
import gregtech.api.gui.widgets.*;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import keqing.gtqtcore.client.renderer.MultiQuantumStorageRenderer;
import keqing.gtqtcore.client.widgets.LabelFluidLockedTooltipWidget;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

//四重超级缸，超级屎山

public class MetaTileEntityMultiQuantumTank extends MetaTileEntity implements ITieredMetaTileEntity, IActiveOutputSide, IFastRenderMetaTileEntity{
    private final int tier;
    private final int maxFluidCapacity;
    //占位，防止初始化时候崩溃
    protected FluidTank fluidTankA;
    //直接堆四个tank
    protected MuitlQuantumFluidTank[] fluidTanks = new MuitlQuantumFluidTank[4];
    private boolean autoOutputFluids;
    private @Nullable EnumFacing outputFacing;
    private boolean allowInputFromOutputSide = false;
    protected IFluidHandler outputFluidInventory;
    //用于更新客户端的渲染状态
    protected @Nullable FluidStack[] previousFluids = new FluidStack[4];
    //储存被锁定的液体
    protected FluidStack[] lockedFluids = new FluidStack[4];
    //    protected boolean locked;
    protected boolean voiding;
    protected IFluidHandler[] fluidInventorys;
    private String lockedFluidtext;

    public MetaTileEntityMultiQuantumTank(ResourceLocation metaTileEntityId, int tier, int maxFluidCapacity) {
        super(metaTileEntityId);
        this.tier = tier;
        this.maxFluidCapacity = maxFluidCapacity;
        this.initializeInventory();

    }

    public int getTier() {
        return tier;
    }

    //初始化
    @Override
    protected void initializeInventory() {
        super.initializeInventory();

        this.fluidTanks = new MuitlQuantumFluidTank[4];
        for (int i = 0; i < this.fluidTanks.length; i++)
        {
            this.fluidTanks[i] = new MuitlQuantumFluidTank(maxFluidCapacity, i);
        }

        this.fluidInventorys = this.fluidTanks;

        this.importFluids = new FluidTankList(false, this.fluidTanks);
        this.exportFluids = new FluidTankList(false, this.fluidTanks);
        this.outputFluidInventory = new FluidHandlerProxy(new FluidTankList(false), exportFluids);

    }

    @Override
    public void update() {
        super.update();
        EnumFacing currentOutputFacing = this.getOutputFacing();
        if (!this.getWorld().isRemote) {
            //与其他液体储罐交互以及处理容器输入
            this.fillContainerFromInternalTank();
            this.fillInternalTankFromFluidContainer();
            if (this.isAutoOutputFluids()) {
                this.pushFluidsIntoNearbyHandlers(new EnumFacing[]{currentOutputFacing});
            }

            //更新客户端渲染
            updateFluidMethod(this.fluidTanks, 0);
            updateFluidMethod(this.fluidTanks, 1);
            updateFluidMethod(this.fluidTanks, 2);
            updateFluidMethod(this.fluidTanks, 3);
        }

    }

    //流体渲染更新
    private void updateFluidMethod(FluidTank[] tanks, int index)
    {
        FluidStack currentFluidA = tanks[index].getFluid();
        if(this.previousFluids[index] == null)
        {
            if(currentFluidA != null)
            {
                this.updatePreviousFluid(currentFluidA, index);
            }
        } else if (currentFluidA == null)
        {
            this.updatePreviousFluid(null, index);
        } else if (this.previousFluids[index].getFluid().equals(currentFluidA.getFluid()) && this.previousFluids[index].amount != currentFluidA.amount)
        {
            int currentFill = MathHelper.floor(16.0F * (float)currentFluidA.amount / (float)tanks[0].getCapacity());
            int previousFill = MathHelper.floor(16.0F * (float)this.previousFluids[index].amount / (float)tanks[0].getCapacity());
            this.previousFluids[index].amount = currentFluidA.amount;
            this.writeCustomData(GregtechDataCodes.UPDATE_FLUID_AMOUNT, (buf) -> {

                buf.writeInt(index);
                buf.writeCompoundTag(currentFluidA.writeToNBT(new NBTTagCompound()));
                buf.writeBoolean(currentFill != previousFill);
//                buf.writeString(this.getLockedFluidtext());
            });
        }
    }

    //废弃
    private String getLockedFluidtext()
    {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < this.fluidTanks.length; i++) {
            if (this.fluidTanks[i].getLockedFluid() == null)
            {
                text.append(i).append(".").append("EMPTY").append(";");
            }   else {
                text.append(i).append(".").append(this.fluidTanks[i].getLockedFluid().getLocalizedName()).append(";");
            }
        }
        return text.toString();
    }


    protected void updatePreviousFluid(FluidStack currentFluid, int index) {
        this.previousFluids[index] = currentFluid == null ? null : currentFluid.copy();
        this.writeCustomData(GregtechDataCodes.UPDATE_FLUID, (buf) -> {
//            buf.writeString(this.getLockedFluidtext());
            buf.writeInt(index);
//            buf.writeInt(currentFluid.amount);
            buf.writeCompoundTag(currentFluid == null ? null : currentFluid.writeToNBT(new NBTTagCompound()));
//            buf.writeString(this.getLockedFluidtext());
        });
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("FluidInventoryA", this.fluidTanks[0].writeToNBT(new NBTTagCompound()));
        data.setTag("FluidInventoryB", this.fluidTanks[1].writeToNBT(new NBTTagCompound()));
        data.setTag("FluidInventoryC", this.fluidTanks[2].writeToNBT(new NBTTagCompound()));
        data.setTag("FluidInventoryD", this.fluidTanks[3].writeToNBT(new NBTTagCompound()));

        data.setBoolean("AutoOutputFluids", this.autoOutputFluids);
        data.setInteger("OutputFacing", this.getOutputFacing().getIndex());
        data.setBoolean("IsVoiding", this.voiding);

        data.setBoolean("AIsLocked", this.fluidTanks[0].getIsLocked());
        data.setBoolean("BIsLocked", this.fluidTanks[1].getIsLocked());
        data.setBoolean("CIsLocked", this.fluidTanks[2].getIsLocked());
        data.setBoolean("DIsLocked", this.fluidTanks[3].getIsLocked());

        for (int i = 0; i < this.fluidTanks.length; i++)
        {
            if (this.fluidTanks[i].getIsLocked() && this.fluidTanks[i].getLockedFluid() != null) {
                data.setTag("LockedFluid" + i, this.fluidTanks[i].getLockedFluid().writeToNBT(new NBTTagCompound()));
            }
        }

        data.setBoolean("AllowInputFromOutputSideF", this.allowInputFromOutputSide);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        if (data.hasKey("ContainerInventory")) {
            legacyTankItemHandlerNBTReading(this, data.getCompoundTag("ContainerInventory"), 0, 1);
        }

        this.fluidTanks[0].readFromNBT(data.getCompoundTag("FluidInventoryA"));
        this.fluidTanks[1].readFromNBT(data.getCompoundTag("FluidInventoryB"));
        this.fluidTanks[2].readFromNBT(data.getCompoundTag("FluidInventoryC"));
        this.fluidTanks[3].readFromNBT(data.getCompoundTag("FluidInventoryD"));

        this.autoOutputFluids = data.getBoolean("AutoOutputFluids");
        this.outputFacing = EnumFacing.VALUES[data.getInteger("OutputFacing")];
        this.voiding = data.getBoolean("IsVoiding") || data.getBoolean("IsPartiallyVoiding");

//        this.locked = data.getBoolean("IsLocked");
        this.fluidTanks[0].setIsLocked(data.getBoolean("AIsLocked"));
        this.fluidTanks[1].setIsLocked(data.getBoolean("BIsLocked"));
        this.fluidTanks[2].setIsLocked(data.getBoolean("CIsLocked"));
        this.fluidTanks[3].setIsLocked(data.getBoolean("DIsLocked"));

        for (int i = 0; i < this.fluidTanks.length; i++)
        {
            this.fluidTanks[i].setLockedFluid(this.fluidTanks[i].getIsLocked() ? FluidStack.loadFluidStackFromNBT(data.getCompoundTag("LockedFluid" + i)) : null);
        }
        this.allowInputFromOutputSide = data.getBoolean("AllowInputFromOutputSideF");

    }

    //疑似处理超级缸内部的物品存储
    public static void legacyTankItemHandlerNBTReading(MetaTileEntity mte, NBTTagCompound nbt, int inputSlot, int outputSlot) {
        if (mte != null && nbt != null) {
            NBTTagList items = nbt.getTagList("Items", 10);
            if (mte.getExportItems().getSlots() >= 1 && mte.getImportItems().getSlots() >= 1 && inputSlot >= 0 && outputSlot >= 0 && inputSlot != outputSlot) {
                for(int i = 0; i < items.tagCount(); ++i) {
                    NBTTagCompound itemTags = items.getCompoundTagAt(i);
                    int slot = itemTags.getInteger("Slot");
                    if (slot == inputSlot) {
                        mte.getImportItems().setStackInSlot(0, new ItemStack(itemTags));
                    } else if (slot == outputSlot) {
                        mte.getExportItems().setStackInSlot(0, new ItemStack(itemTags));
                    }
                }
            }
        }
    }

    //物品状态的超级缸的处理
    @Override
    public void initFromItemStackData(NBTTagCompound tag) {
        super.initFromItemStackData(tag);
        for (int i = 0; i < this.fluidTanks.length; i++){
            this.readItemStackMethod(tag, i);
        }

        if (tag.getBoolean("IsVoiding") || tag.getBoolean("IsPartialVoiding")) {
            this.setVoiding(true);
        }
    }

    private void readItemStackMethod(NBTTagCompound tag, int index)
    {
        if (tag.hasKey("Fluid" + index, 10)) {
            this.fluidTanks[index].setFluid(FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Fluid" + index)));
        }

        this.fluidTanks[index].setIsLocked(tag.getBoolean("Locked" + index));
        this.fluidTanks[index].setLockedFluid(FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("LockedFluid" + index)));
    }

    @Override
    public void writeItemStackData(NBTTagCompound tag) {
        super.writeItemStackData(tag);
        for (int i = 0; i < this.fluidTanks.length; i++)
        {
            this.writeItemStackMethod(this.fluidTanks[i].getFluid(), tag, i);
        }

        if (this.voiding) {
            tag.setBoolean("IsVoiding", true);
        }
    }

    private void writeItemStackMethod(FluidStack stack, NBTTagCompound tag, int index)
    {
        if (stack != null && stack.amount > 0) {
            tag.setTag("Fluid" + index, stack.writeToNBT(new NBTTagCompound()));
        }

        if (this.fluidTanks[index].getIsLocked()) {
            tag.setBoolean("Locked" + index, this.fluidTanks[index].getIsLocked());
            tag.setTag("LockedFluid" + index, this.fluidTanks[index].getLockedFluid().writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMultiQuantumTank(this.metaTileEntityId, this.tier, this.maxFluidCapacity);
    }

    //创建接口，先传入占位符，后传入tanks，防止初始化崩溃
    protected FluidTankList createImportFluidHandler() {
        return new FluidTankList(false, this.fluidTankA);
    }

    protected FluidTankList createExportFluidHandler() {
        return new FluidTankList(false, this.fluidTankA);
    }

    //超级缸的物品输入槽逻辑，但无法实现根据槽的位置来输出特定的液体
    protected IItemHandlerModifiable createImportItemHandler() {
        return (new FilteredItemHandler(this, 4)).setFillPredicate(FilteredItemHandler.getCapabilityFilter(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY));
    }

    protected IItemHandlerModifiable createExportItemHandler() {
        return new GTItemStackHandler(this, 4);
    }

    //超级缸的客户端液体渲染
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        Textures.QUANTUM_STORAGE_RENDERER.renderMachine(renderState, translation, (IVertexOperation[]) ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering()))), this);
        Textures.QUANTUM_TANK_OVERLAY.renderSided(EnumFacing.UP, renderState, translation, pipeline);
        if (this.outputFacing != null) {
            Textures.PIPE_OUT_OVERLAY.renderSided(this.outputFacing, renderState, translation, pipeline);
            if (this.isAutoOutputFluids()) {
                Textures.FLUID_OUTPUT_OVERLAY.renderSided(this.outputFacing, renderState, translation, pipeline);
            }
        }

        Cuboid6 partialFluidBox = new Cuboid6();
        partialFluidBox = new Cuboid6(0.06640625, 0.12890625, 0.06640625, 0.93359375, 0.93359375, 0.93359375);
        double high = 0;
        Cuboid6 gasPartialFluidBox = new Cuboid6();
        gasPartialFluidBox = new Cuboid6(0.06640625, 0.12890625, 0.06640625, 0.93359375, 0.93359375, 0.93359375);
        double gasHigh = 0;

        MultiQuantumStorageRenderer.renderMultiTankFluid(renderState, translation, pipeline, this.fluidTanks, this.getWorld(), this.getPos(), this.getFrontFacing(), partialFluidBox, high, gasPartialFluidBox, gasHigh);
    }

    //超级缸的客户端数字渲染
    @Override
    public void renderMetaTileEntity(double x, double y, double z, float partialTicks) {
        if (this.TankIsEmpty(0)) {
            MultiQuantumStorageRenderer.renderTankAmount(x, y - 0.15, z, this.getFrontFacing(), (long)this.fluidTanks[0].getFluid().amount);
        }
        if (this.TankIsEmpty(1)) {
            MultiQuantumStorageRenderer.renderTankAmount(x, y, z, this.getFrontFacing(), (long)this.fluidTanks[1].getFluid().amount);
        }
        if (this.TankIsEmpty(2)) {
            MultiQuantumStorageRenderer.renderTankAmount(x, y + 0.15, z, this.getFrontFacing(), (long)this.fluidTanks[2].getFluid().amount);
        }
        if (this.TankIsEmpty(3)) {
            MultiQuantumStorageRenderer.renderTankAmount(x, y + 0.3, z, this.getFrontFacing(), (long)this.fluidTanks[3].getFluid().amount);
        }
    }

    //检测超级缸的tank是否为空
    private boolean TankIsEmpty(int index)
    {
        if (this.fluidTanks[index].getFluid() == null) {
            return false;
        }
        if (this.fluidTanks[index] != null && this.fluidTanks[index].getFluid().amount == 0) {
            return false;
        }
        return true;
    }

    @Override
    public Pair<TextureAtlasSprite, Integer> getParticleTexture() {
        return Pair.of(Textures.VOLTAGE_CASINGS[this.tier].getParticleSprite(), this.getPaintingColorForRendering());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.machine.multi_quantum_tank.tooltip", new Object[0]));
        tooltip.add(I18n.format("gregtech.universal.tooltip.fluid_storage_capacity", new Object[]{this.maxFluidCapacity}));
        NBTTagCompound tag = stack.getTagCompound();

        if (tag != null) {
            this.informationFluidShow(tag, tooltip);

            if (tag.getBoolean("IsVoiding") || tag.getBoolean("IsPartialVoiding")) {
                tooltip.add(I18n.format("gregtech.machine.quantum_tank.tooltip.voiding_enabled", new Object[0]));
            }
        }

    }

    private void informationFluidShow(NBTTagCompound tag, List<String> tooltip)
    {
        for (int i = 0; i < this.fluidTanks.length; i++) {
            if (tag.hasKey("Fluid" + i, 10)) {
                FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Fluid" + i));
                if (fluidStack != null) {
                    tooltip.add(I18n.format("gregtech.universal.tooltip.fluid_stored", new Object[]{fluidStack.getLocalizedName(), fluidStack.amount}));
                }
            }
        }
    }

    @Override
    public void addToolUsages(ItemStack stack, @Nullable World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.tool_action.screwdriver.auto_output_covers", new Object[0]));
        tooltip.add(I18n.format("gregtech.tool_action.wrench.set_facing", new Object[0]));
        super.addToolUsages(stack, world, tooltip, advanced);
    }

    //究极堆叠
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        TankWidget tankWidgetA = this.createTankWidget(this.fluidTanks[0], 0, 9, 43, 18, 18);
        TankWidget tankWidgetB = this.createTankWidget(this.fluidTanks[1], 1, 28, 43, 18, 18);
        TankWidget tankWidgetC = this.createTankWidget(this.fluidTanks[2], 2, 47, 43, 18, 18);
        TankWidget tankWidgetD = this.createTankWidget(this.fluidTanks[3], 3, 67, 43, 18, 18);

        FluidContainerSlotWidget containerSlotWidgetA = this.createContainerSlot(0, 90, 17);
        FluidContainerSlotWidget containerSlotWidgetB = this.createContainerSlot(1, 110, 17);
        FluidContainerSlotWidget containerSlotWidgetC = this.createContainerSlot(2, 130, 17);
        FluidContainerSlotWidget containerSlotWidgetD = this.createContainerSlot(3, 150, 17);

        SlotWidget slotWidgetA = this.createSlot(0, 90, 44);
        SlotWidget slotWidgetB = this.createSlot(1, 110, 44);
        SlotWidget slotWidgetC = this.createSlot(2, 130, 44);
        SlotWidget slotWidgetD = this.createSlot(3, 150, 44);

        ToggleButtonWidget LockA = new ToggleButtonWidget(90, 64, 18, 18, GuiTextures.BUTTON_LOCK, this::AisLocked, this::setALocked)
                .setTooltipText("gregtech.gui.fluid_lock.tooltip", new Object[0]).shouldUseBaseBackground();
        ToggleButtonWidget LockB = new ToggleButtonWidget(110, 64, 18, 18, GuiTextures.BUTTON_LOCK, this::BisLocked, this::setBLocked)
                .setTooltipText("gregtech.gui.fluid_lock.tooltip", new Object[0]).shouldUseBaseBackground();
        ToggleButtonWidget LockC = new ToggleButtonWidget(130, 64, 18, 18, GuiTextures.BUTTON_LOCK, this::CisLocked, this::setCLocked)
                .setTooltipText("gregtech.gui.fluid_lock.tooltip", new Object[0]).shouldUseBaseBackground();
        ToggleButtonWidget LockD = new ToggleButtonWidget(150, 64, 18, 18, GuiTextures.BUTTON_LOCK, this::DisLocked, this::setDLocked)
                .setTooltipText("gregtech.gui.fluid_lock.tooltip", new Object[0]).shouldUseBaseBackground();

        FluidStack[] lockedFluidStacks = new FluidStack[4];
        for (int i = 0; i < this.fluidTanks.length; i++)
        {
            lockedFluidStacks[i] = this.fluidTanks[i].getLockedFluid();
        }

        return ModularUI.defaultBuilder()
                .widget(new LabelWidget(6, 6, this.getMetaFullName(), 16777215))
                .widget(new ImageWidget(7, 16, 81, 46, GuiTextures.DISPLAY))
                .widget(new LabelFluidLockedTooltipWidget(11, 20, "Locked Fluid", this.lockedFluids))
                .widget(tankWidgetA)
                .widget(tankWidgetB)
                .widget(tankWidgetC)
                .widget(tankWidgetD)
                .widget(containerSlotWidgetA)
                .widget(containerSlotWidgetB)
                .widget(containerSlotWidgetC)
                .widget(containerSlotWidgetD)
                .widget(slotWidgetA)
                .widget(slotWidgetB)
                .widget(slotWidgetC)
                .widget(slotWidgetD)
                .widget((new ToggleButtonWidget(7, 64, 18, 18, GuiTextures.BUTTON_FLUID_OUTPUT, this::isAutoOutputFluids, this::setAutoOutputFluids)).setTooltipText("gregtech.gui.fluid_auto_output.tooltip", new Object[0]).shouldUseBaseBackground())
                .widget(LockA)
                .widget(LockB)
                .widget(LockC)
                .widget(LockD)

                .widget((new ToggleButtonWidget(25, 64, 18, 18, GuiTextures.BUTTON_FLUID_VOID, this::isVoiding, this::setVoiding)).setTooltipText("gregtech.gui.fluid_voiding.tooltip", new Object[0]).shouldUseBaseBackground()).bindPlayerInventory(entityPlayer.inventory)
                .build(this.getHolder(), entityPlayer);
    }

    //ui的液体渲染
    private TankWidget createTankWidget(FluidTank tank, int lockedFluidIndex, int x, int y, int width, int height)
    {
        TankWidget tankWidget = new PhantomTankWidget(tank, x, y, width, height,
                () -> this.fluidTanks[lockedFluidIndex].getLockedFluid(),
                f -> {
                    if (tank.getFluidAmount() == 0) {
                        if (f == null) {
                            switch (lockedFluidIndex){
                                case 0:
                                    this.setALocked(false);
                                    break;
                                case 1:
                                    this.setBLocked(false);
                                    break;
                                case 2:
                                    this.setCLocked(false);
                                    break;
                                case 3:
                                    this.setDLocked(false);
                                    break;
                            }
                            this.fluidTanks[lockedFluidIndex].setLockedFluid(null);
                        } else {
                            switch (lockedFluidIndex){
                                case 0:
                                    this.setALocked(true);
                                    break;
                                case 1:
                                    this.setBLocked(true);
                                    break;
                                case 2:
                                    this.setCLocked(true);
                                    break;
                                case 3:
                                    this.setDLocked(true);
                                    break;
                            }
                            this.fluidTanks[lockedFluidIndex].setLockedFluid(f.copy());
                            this.fluidTanks[lockedFluidIndex].getLockedFluid().amount = 1;
                        }

                    }
                }).setAlwaysShowFull(true).setDrawHoveringText(false);
        return tankWidget;
    }

    //ui的物品输入槽渲染
    private FluidContainerSlotWidget createContainerSlot(int slot, int x, int y)
    {
        FluidContainerSlotWidget containerSlotWidget = new FluidContainerSlotWidget(this.importItems, slot, x, y, false);
        containerSlotWidget.setBackgroundTexture(new IGuiTexture[]{GuiTextures.SLOT, GuiTextures.IN_SLOT_OVERLAY});
        return containerSlotWidget;
    }

    //ui的物品输出槽渲染
    private SlotWidget createSlot(int slot, int x, int y)
    {
        SlotWidget slotWidget = new SlotWidget(this.exportItems, slot, x, y, true, false);
        slotWidget.setBackgroundTexture(new IGuiTexture[]{GuiTextures.SLOT, GuiTextures.OUT_SLOT_OVERLAY});
        return slotWidget;
    }


    public EnumFacing getOutputFacing() {
        return this.outputFacing == null ? this.frontFacing.getOpposite() : this.outputFacing;
    }

    @Override
    public void setFrontFacing(EnumFacing frontFacing) {
        if (frontFacing == EnumFacing.UP) {
            if (this.outputFacing != null && this.outputFacing != EnumFacing.DOWN) {
                super.setFrontFacing(this.outputFacing.getOpposite());
            } else {
                super.setFrontFacing(EnumFacing.NORTH);
            }
        } else {
            super.setFrontFacing(frontFacing);
        }

        if (this.outputFacing == null) {
            this.setOutputFacing(frontFacing.getOpposite());
        }

    }

    @Override
    public boolean isAutoOutputItems() {
        return false;
    }

    @Override
    public boolean isAutoOutputFluids() {
        return this.autoOutputFluids;
    }

    @Override
    public boolean isAllowInputFromOutputSideItems() {
        return false;
    }

    @Override
    public boolean isAllowInputFromOutputSideFluids() {
        return this.allowInputFromOutputSide;
    }

    //客户端接收包，更新渲染状态
    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GregtechDataCodes.UPDATE_OUTPUT_FACING) {
            this.outputFacing = EnumFacing.VALUES[buf.readByte()];
            this.scheduleRenderUpdate();
        } else if (dataId == GregtechDataCodes.UPDATE_AUTO_OUTPUT_FLUIDS) {
            this.autoOutputFluids = buf.readBoolean();
            this.scheduleRenderUpdate();
        } else if (dataId == GregtechDataCodes.UPDATE_FLUID) {
            try {
                int index = buf.readInt();
                switch (index){
                    case 0:
                        this.fluidTanks[0].setFluid(FluidStack.loadFluidStackFromNBT(buf.readCompoundTag()));
//                        if(this.fluidTanks[0].getFluid() != null) this.fluidTanks[0].getFluid().amount = buf.readInt();
                        break;
                    case 1:
                        this.fluidTanks[1].setFluid(FluidStack.loadFluidStackFromNBT(buf.readCompoundTag()));
//                        if(this.fluidTanks[1].getFluid() != null) this.fluidTanks[0].getFluid().amount = buf.readInt();
                        break;
                    case 2:
                        this.fluidTanks[2].setFluid(FluidStack.loadFluidStackFromNBT(buf.readCompoundTag()));
//                        if(this.fluidTanks[2].getFluid() != null) this.fluidTanks[0].getFluid().amount = buf.readInt();
                        break;
                    case 3:
                        this.fluidTanks[3].setFluid(FluidStack.loadFluidStackFromNBT(buf.readCompoundTag()));
//                        if(this.fluidTanks[3].getFluid() != null) this.fluidTanks[0].getFluid().amount = buf.readInt();
                        break;
                }
//                this.lockedFluidtext = buf.readString(100);

            } catch (IOException var6) {
                GTLog.logger.warn("Failed to load fluid from NBT in a quantum tank at {} on a routine fluid update", this.getPos());
            }

            this.scheduleRenderUpdate();
        } else if (dataId == GregtechDataCodes.UPDATE_FLUID_AMOUNT) {
            int index = buf.readInt();
            FluidStack stack = null;
            try {
                stack = FluidStack.loadFluidStackFromNBT(buf.readCompoundTag());
//                this.lockedFluidtext = buf.readString(100);
            } catch (IOException var6) {
                GTLog.logger.warn("Failed to load fluid from NBT in a quantum tank at {} on a routine fluid update", this.getPos());
            }
            boolean updateRendering = buf.readBoolean();
//            FluidStack stack = this.fluidTanks[0].getFluid();
            if (stack != null) {
                this.fluidTanks[index].setFluid(stack);
//                stack.amount = Math.min(amount, this.fluidTanks[0].getCapacity());
                if (updateRendering) {
                    this.scheduleRenderUpdate();
                }
            }
        } else if (dataId == GregtechDataCodes.UPDATE_IS_VOIDING) {
            this.setVoiding(buf.readBoolean());
        } else if (dataId == GregtechDataCodes.UPDATE_LOCKED_STATE) {
            try {
                int index = buf.readInt();
                switch (index){
                    case 0:
                        this.lockedFluids[0] = FluidStack.loadFluidStackFromNBT(buf.readCompoundTag());
                        break;
                    case 1:
                        this.lockedFluids[1] = FluidStack.loadFluidStackFromNBT(buf.readCompoundTag());
                        break;
                    case 2:
                        this.lockedFluids[2] = FluidStack.loadFluidStackFromNBT(buf.readCompoundTag());
                        break;
                    case 3:
                        this.lockedFluids[3] = FluidStack.loadFluidStackFromNBT(buf.readCompoundTag());
                        break;
                }
            } catch (IOException var6) {
                GTLog.logger.warn("Failed to load fluid from NBT in a quantum tank at {} on a routine fluid update", this.getPos());
            }
        }

    }

    @Override
    public boolean isValidFrontFacing(EnumFacing facing) {
        return super.isValidFrontFacing(facing) && facing != this.outputFacing;
    }

    //可能是初始化时候发包
    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeByte(this.getOutputFacing().getIndex());
        buf.writeBoolean(this.autoOutputFluids);
//        buf.writeBoolean(this.locked);
//        buf.writeCompoundTag(this.fluidTank.getFluid() == null ? null : this.fluidTank.getFluid().writeToNBT(new NBTTagCompound()));
        buf.writeCompoundTag(this.fluidTanks[0].getFluid() == null ? null : this.fluidTanks[0].getFluid().writeToNBT(new NBTTagCompound()));
        buf.writeCompoundTag(this.fluidTanks[1].getFluid() == null ? null : this.fluidTanks[1].getFluid().writeToNBT(new NBTTagCompound()));
        buf.writeCompoundTag(this.fluidTanks[2].getFluid() == null ? null : this.fluidTanks[2].getFluid().writeToNBT(new NBTTagCompound()));
        buf.writeCompoundTag(this.fluidTanks[3].getFluid() == null ? null : this.fluidTanks[3].getFluid().writeToNBT(new NBTTagCompound()));
        buf.writeBoolean(this.voiding);
    }

    //可能是初始化时候接受包
    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.outputFacing = EnumFacing.VALUES[buf.readByte()];
        if (this.frontFacing == EnumFacing.UP) {
            if (this.outputFacing != EnumFacing.DOWN) {
                this.frontFacing = this.outputFacing.getOpposite();
            } else {
                this.frontFacing = EnumFacing.NORTH;
            }
        }

        this.autoOutputFluids = buf.readBoolean();
//        this.locked = buf.readBoolean();

        try {
            this.fluidTanks[0].setFluid(FluidStack.loadFluidStackFromNBT(buf.readCompoundTag()));
            this.fluidTanks[1].setFluid(FluidStack.loadFluidStackFromNBT(buf.readCompoundTag()));
            this.fluidTanks[2].setFluid(FluidStack.loadFluidStackFromNBT(buf.readCompoundTag()));
            this.fluidTanks[3].setFluid(FluidStack.loadFluidStackFromNBT(buf.readCompoundTag()));
        } catch (IOException var3) {
            GTLog.logger.warn("Failed to load fluid from NBT in a quantum tank at " + this.getPos() + " on initial server/client sync");
        }

        this.voiding = buf.readBoolean();
    }


    public void setOutputFacing(EnumFacing outputFacing) {
        this.outputFacing = outputFacing;
        if (!this.getWorld().isRemote) {
            this.notifyBlockUpdate();
            this.writeCustomData(GregtechDataCodes.UPDATE_OUTPUT_FACING, (buf) -> {
                buf.writeByte(outputFacing.getIndex());
            });
            this.markDirty();
        }

    }

    //handler能传入tankList！
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE) {
            return side == this.getOutputFacing() ? GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this) : null;
        } else if (capability != CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return super.getCapability(capability, side);
        } else {
            IFluidHandler fluidTank = new FluidTankList(false, this.fluidTanks);
            IFluidHandler fluidHandler = side == this.getOutputFacing() && !this.isAllowInputFromOutputSideFluids() ? this.outputFluidInventory : fluidTank;
            if (fluidHandler.getTankProperties().length > 0){
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidHandler);
            }
            return null;
        }
    }

//    @Override
//    public ICapabilityProvider initItemStackCapabilities(ItemStack itemStack) {
//        return new GTFluidHandlerItemStack(itemStack, this.maxFluidCapacity);
//    }

    @Override
    public boolean onWrenchClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if (!playerIn.isSneaking()) {
            if (this.getOutputFacing() != facing && this.getFrontFacing() != facing) {
                if (!this.getWorld().isRemote) {
                    this.setOutputFacing(facing);
                }

                return true;
            } else {
                return false;
            }
        } else {
            return super.onWrenchClick(playerIn, hand, facing, hitResult);
        }
    }

    @Override
    public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        EnumFacing hitFacing = CoverRayTracer.determineGridSideHit(hitResult);
        if (facing == this.getOutputFacing() || hitFacing == this.getOutputFacing() && playerIn.isSneaking()) {
            if (!this.getWorld().isRemote) {
                if (this.isAllowInputFromOutputSideFluids()) {
                    this.setAllowInputFromOutputSide(false);
                    playerIn.sendStatusMessage(new TextComponentTranslation("gregtech.machine.basic.input_from_output_side.disallow", new Object[0]), true);
                } else {
                    this.setAllowInputFromOutputSide(true);
                    playerIn.sendStatusMessage(new TextComponentTranslation("gregtech.machine.basic.input_from_output_side.allow", new Object[0]), true);
                }
            }

            return true;
        } else {
            return super.onScrewdriverClick(playerIn, hand, facing, hitResult);
        }
    }


    public void setAllowInputFromOutputSide(boolean allowInputFromOutputSide) {
        if (this.allowInputFromOutputSide != allowInputFromOutputSide) {
            this.allowInputFromOutputSide = allowInputFromOutputSide;
            if (!this.getWorld().isRemote) {
                this.markDirty();
            }

        }
    }

    public void setAutoOutputFluids(boolean autoOutputFluids) {
        if (this.autoOutputFluids != autoOutputFluids) {
            this.autoOutputFluids = autoOutputFluids;
            if (!this.getWorld().isRemote) {
                this.writeCustomData(GregtechDataCodes.UPDATE_AUTO_OUTPUT_FLUIDS, (buf) -> {
                    buf.writeBoolean(autoOutputFluids);
                });
                this.markDirty();
            }

        }
    }

    //处理锁定之类的，大屎山
    protected boolean AisLocked() {
        return this.fluidTanks[0].isLocked;
    }

    protected boolean BisLocked() {
        return this.fluidTanks[1].isLocked;
    }

    protected boolean CisLocked() {
        return this.fluidTanks[2].isLocked;
    }
    protected boolean DisLocked() {
        return this.fluidTanks[3].isLocked;
    }

    //点击锁定按钮时候发包，更新客户端的tooltip上的锁定状态
    protected void setALocked(boolean locked) {
        this.setIsLocked(locked, this.fluidTanks[0]);
        this.writeLockedFluids(0);
        this.markDirty();
    }

    protected void setBLocked(boolean locked) {
        this.setIsLocked(locked, this.fluidTanks[1]);
        this.writeLockedFluids(1);
        this.markDirty();
    }
    protected void setCLocked(boolean locked) {
        this.setIsLocked(locked, this.fluidTanks[2]);
        this.writeLockedFluids(2);
        this.markDirty();
    }
    protected void setDLocked(boolean locked) {
        this.setIsLocked(locked, this.fluidTanks[3]);
        this.writeLockedFluids(3);
        this.markDirty();
    }

    //发包
    private void writeLockedFluids(int index)
    {
        this.writeCustomData(GregtechDataCodes.UPDATE_LOCKED_STATE, (buf ->
        {
            buf.writeInt(index);
            buf.writeCompoundTag(this.fluidTanks[index].getLockedFluid() == null ? null : this.fluidTanks[index].getLockedFluid().writeToNBT(new NBTTagCompound()));
        }));
    }

    protected boolean isVoiding() {
        return this.voiding;
    }

    protected void setVoiding(boolean isPartialVoid) {
        this.voiding = isPartialVoid;
        if (!this.getWorld().isRemote) {
            this.writeCustomData(GregtechDataCodes.UPDATE_IS_VOIDING, (buf) -> {
                buf.writeBoolean(this.voiding);
            });
            this.markDirty();
        }

    }

    @Override
    public ItemStack getPickItem(EntityPlayer player) {
        if (!player.isCreative()) {
            return super.getPickItem(player);
        } else {
            ItemStack baseItemStack = this.getStackForm();
            NBTTagCompound tag = new NBTTagCompound();
            this.writeItemStackData(tag);
            if (!tag.isEmpty()) {
                baseItemStack.setTagCompound(tag);
            }

            return baseItemStack;
        }
    }

    @Override
    public boolean needsSneakToRotate() {
        return true;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(this.getPos());
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getLightOpacity() {
        return 0;
    }

    //设置某个tank的锁定状态
    public void setIsLocked(boolean locked, MuitlQuantumFluidTank tank)
    {
        if (tank.getIsLocked() == locked) return;
        tank.setIsLocked(locked);
        if (!getWorld().isRemote) {
            markDirty();
        }
        if (tank.getIsLocked() && tank.getFluid() != null) {
            tank.setLockedFluid(tank.getFluid().copy());
            tank.getLockedFluid().amount = 1;
            return;
        }
        tank.setLockedFluid(null);
    }

    //专门写了处理锁定逻辑的tank
    public class MuitlQuantumFluidTank extends FluidTank implements IFilteredFluidContainer, IFilter<FluidStack> {

        public int lockId;
        public boolean isLocked;
        public FluidStack lockedFluid;

        public MuitlQuantumFluidTank(int capacity, int lockId) {
            super(capacity);
            this.lockId = lockId;
        }

        @Override
        public int fillInternal(FluidStack resource, boolean doFill) {
            int accepted = super.fillInternal(resource, doFill);

            // if we couldn't accept "resource", and "resource" is not the same as the stored fluid.
            if (accepted == 0 && !resource.isFluidEqual(getFluid())) {
                return 0;
            }

            if (doFill && this.isLocked && this.lockedFluid == null) {
                this.lockedFluid = resource.copy();
                this.lockedFluid.amount = 1;
            }
            return voiding ? resource.amount : accepted;
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return test(fluid);
        }

        @Override
        public IFilter<FluidStack> getFilter() {
            return this;
        }

        @Override
        public boolean test(FluidStack fluidStack) {
            return !this.isLocked || this.lockedFluid == null || fluidStack.isFluidEqual(this.lockedFluid);
        }

        @Override
        public int getPriority() {
            return !this.isLocked || this.lockedFluid == null ? IFilter.noPriority() : IFilter.whitelistPriority(1);
        }

        public int getLockId()
        {
            return this.lockId;
        }

        public boolean getIsLocked()
        {
            return this.isLocked;
        }

        public void setIsLocked(boolean locked)
        {
            this.isLocked = locked;
        }

        public FluidStack getLockedFluid()
        {
            return this.lockedFluid;
        }

        public void setLockedFluid(FluidStack stack)
        {
            this.lockedFluid = stack;
        }
    }

}