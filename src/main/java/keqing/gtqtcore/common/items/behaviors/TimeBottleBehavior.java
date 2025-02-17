package keqing.gtqtcore.common.items.behaviors;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.items.gui.ItemUIFactory;
import gregtech.api.items.gui.PlayerInventoryHolder;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.metatileentity.multiblock.RecipeMapSteamMultiblockController;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import keqing.gtqtcore.GTQTCoreConfig;
import keqing.gtqtcore.api.utils.GTQTDateHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

import static keqing.gtqtcore.common.items.GTQTMetaItems.TIME_BOTTLE;

public class TimeBottleBehavior implements IItemBehaviour, ItemUIFactory {
    private final int maxTime = 20 * 3600 * GTQTCoreConfig.difficultySwitch.TimeBottleStoreMaxHour;
    private int time;
    private int accelerateTime;
    private boolean model;

    public TimeBottleBehavior() {
    }

    public void writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("model", model);
        compound.setInteger("time", time);
        compound.setInteger("accelerateTime", accelerateTime);
    }

    public void readFromNBT(NBTTagCompound compound) {
        model = compound.getBoolean("model");
        time = compound.getInteger("time");
        accelerateTime = compound.getInteger("accelerateTime");
    }

    public void onUpdate(ItemStack itemStack, Entity entity) {
        if (!entity.world.isRemote) {
            if (entity instanceof EntityPlayer player) {
                for (int i = 0; i < player.inventory.getSizeInventory() && i < 48; i++) {
                    ItemStack invStack = player.inventory.getStackInSlot(i);
                    if (invStack.getItem() == TIME_BOTTLE.getMetaItem()) {
                        if (time < maxTime) time++;
                    }
                }
                if (!itemStack.hasTagCompound()) {
                    NBTTagCompound compound = new NBTTagCompound();
                    compound.setInteger("storedTime", time);
                    itemStack.setTagCompound(compound);
                }
            }
        }
    }

    public void addInformation(ItemStack stack, List<String> lines) {

        lines.add("存储时间："+GTQTDateHelper.getTimeFromTicks(time));
        lines.add("加速时间："+GTQTDateHelper.getTimeFromSecond(accelerateTime));
        lines.add(I18n.format("潜行右键打开GUI，可进行模式切换，调整加速时间等操作"));
        lines.add(I18n.format("右键可对机器（包括单方块，多方块），熔炉，作物，树苗等可加速方块进行加速"));
        lines.add(I18n.format("设备模式->无损加速耗电机器（不耗电），也可加速熔炉"));
        lines.add(I18n.format("随机刻模式->加速随机刻方块（如果是作物，树苗等需要光照条件），固定消耗60秒"));

    }

    public int getRapid() {
        return 1200*accelerateTime;
    }
    public int countRapid(int time) {
        if (time < 1200) return 0;
        if (time < 3600) return 60;
        if (time < 7200) return 120;
        if (time < 14400) return 180;
        if (time < 28800) return 240;
        if (time < 43200) return 300;
        return 300;
    }

    public void addEnergy(World world, BlockPos pos, long cache) {
        MetaTileEntity mte = GTUtility.getMetaTileEntity(world, pos);
        for (EnumFacing facing : EnumFacing.VALUES) {
            if (mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                IEnergyContainer container1 = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);
                if (cache > container1.getEnergyStored())
                    container1.changeEnergy(cache - container1.getEnergyStored());
            }
        }

    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.hasTagCompound()) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("storedTime", 0);
            stack.setTagCompound(compound);
        }
        if (time > 0) {
            if (!player.isSneaking()) {
                //随机刻模式////////////////////////////////////////////////////
                if (!model) {
                    handleRandomTickMode(world, pos);
                }
                //设备模式////////////////////////////////////////////////////
                else {
                    //GT模式////////////////////////////////////////////////////
                    if (GTUtility.getMetaTileEntity(world, pos) instanceof MetaTileEntity) {
                        long cache = 0;
                        MetaTileEntity mte = GTUtility.getMetaTileEntity(world, pos);
                        TileEntity te = world.getTileEntity(pos);
                        if (mte instanceof MultiblockControllerBase mcb) {
                            if (mcb.isStructureFormed() && mcb.isValid()) {
                                final var inenergy = mcb.getAbilities(MultiblockAbility.INPUT_ENERGY);
                                if (inenergy.size() > 0) {
                                    long[] energys = new long[inenergy.size()];
                                    for (int j = 0; j < inenergy.size(); j++) {
                                        energys[j] = inenergy.get(j).getEnergyStored();
                                    }
                                    if (te instanceof ITickable) {
                                        for (int i = 0; i < getRapid(); i++) {
                                            ((ITickable) te).update();
                                            for (int j = 0; j < inenergy.size(); j++) {
                                                if (inenergy.get(j).getEnergyStored() < energys[j])
                                                    inenergy.get(j).addEnergy(energys[j] - inenergy.get(j).getEnergyStored());
                                            }
                                        }
                                        time -= getRapid();

                                    }
                                } else if (mte instanceof RecipeMapSteamMultiblockController smte) {
                                    final var fin = smte.getSteamFluidTank();
                                    int[] energys = new int[fin.getTanks()];
                                    for (int j = 0; j < fin.getTanks(); j++) {
                                        energys[j] = fin.getTankAt(j).getFluidAmount();
                                    }
                                    if (te instanceof ITickable) {
                                        for (int i = 0; i < time; i++) {
                                            ((ITickable) te).update();
                                            for (int j = 0; j < fin.getTanks(); j++) {
                                                if (fin.getTankAt(j).getFluidAmount() < energys[j]) {
                                                    fin.getTankAt(j).fill(Materials.Steam.getFluid(energys[j] - fin.getTankAt(j).getFluidAmount()), true);
                                                }

                                            }
                                        }
                                        time -= getRapid();
                                    }
                                } else {
                                    for (int i = 0; i < getRapid(); i++) {
                                        ((ITickable) te).update();
                                    }
                                    time -= getRapid();
                                    ((ITickable) te).update();
                                }
                            }
                        } else {
                            for (EnumFacing facing : EnumFacing.VALUES) {
                                if (mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                                    IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);
                                    cache = container.getEnergyStored();
                                }
                            }
                            if (te instanceof ITickable tickable) {
                                for (int i = 0; i < getRapid(); i++) {
                                    tickable.update();
                                    addEnergy(world, pos, cache);
                                }
                                time -= getRapid();
                            }
                        }
                    }
                    //原版模式////////////////////////////////////////////////////
                    else {
                        TileEntity te = world.getTileEntity(pos);
                        if (te instanceof ITickable tickable) {
                            for (int i = 0; i < getRapid(); i++) {
                                tickable.update();
                            }
                            time -= getRapid();
                        }
                    }
                }
            } else {
                if (!world.isRemote) {
                    PlayerInventoryHolder holder = new PlayerInventoryHolder(player, hand);
                    holder.openUI();
                }
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ModularUI createUI(PlayerInventoryHolder playerInventoryHolder, EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 120)
                .image(10, 8, 156, 50, GuiTextures.DISPLAY)
                .dynamicLabel(15, 13, () -> "存储时长" + GTQTDateHelper.getTimeFromTicks(time), 0xFAF9F6)
                .dynamicLabel(15, 23, () -> "加速时长" + GTQTDateHelper.getTimeFromSecond(accelerateTime), 0xFAF9F6)
                .dynamicLabel(15, 33, () -> "模式"+ (model ? "设备模式" : "随机刻模式"), 0xFAF9F6)
                .widget(new ClickButtonWidget(10, 68, 77, 20, "模式切换", clickData -> model = !model))
                .widget(new ClickButtonWidget(90, 68, 77, 20, I18n.format("推荐加速时长"), clickData -> accelerateTime = countRapid(time)))
                .widget(new ClickButtonWidget(10, 91, 77, 20, I18n.format("加速时长++"), clickData -> accelerateTime = MathHelper.clamp(accelerateTime + 1, 0, time/1200)))
                .widget(new ClickButtonWidget(90, 91, 77, 20, I18n.format("加速时长--"), clickData -> accelerateTime = MathHelper.clamp(accelerateTime - 1, 0, time/1200)))
                .build(playerInventoryHolder, entityPlayer);
    }

    private void handleRandomTickMode(World world, BlockPos pos) {
        if (time < 1200) return;
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block.getTickRandomly()) {
            for (int i = 0; i < 600; i++) block.randomTick(world, pos.toImmutable(), state, world.rand);
        }
        time-=1200;
    }
}
