package keqing.gtqtcore.common.items.behaviors;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IWorkable;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.pipenet.tile.TileEntityPipeBase;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import keqing.gtqtcore.common.items.GTQTMetaItems;
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
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;

import static gregtech.api.GTValues.VA;

public class TimeBottleBehavior implements IItemBehaviour {
    public TimeBottleBehavior(){}

    private int time;
    private int successLimit=16000;
    public void onUpdate(ItemStack itemStack, Entity entity) {
        if (itemStack.hasTagCompound()) {
            NBTTagCompound compound = itemStack.getTagCompound();
            time = compound.getInteger("storedTime");
            time++;
            compound.setInteger("storedTime", time);
        }
        else
        {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("storedTime", time);
            itemStack.setTagCompound(compound);
        }
    }
    public void addInformation(ItemStack stack, List<String> lines) {
        if (stack.hasTagCompound()) {
            NBTTagCompound compound = stack.getTagCompound();

            int storedTime = compound.getInteger("storedTime");

            int storedSeconds = storedTime / 20;

            int hours = storedSeconds / 3600;
            int minutes = (storedSeconds % 3600) / 60;
            int seconds = storedSeconds % 60;

            lines.add(I18n.format("缓存：%s小时 %s分钟 %s秒", hours, minutes, seconds));
            lines.add(I18n.format("时间缓存：%s tick", compound.getInteger("storedTime")));
            lines.add(I18n.format("加速时间：%s tick", getRapid(compound.getInteger("storedTime"))));
            lines.add(I18n.format("右键无损加速耗电机器，也可加速熔炉"));
            lines.add(I18n.format("潜行右键加速随机刻（大树这种）"));
        }
    }
    public int getRapid(int n)
    {
        if(n<1200)return n;
        if(n<4800)return 1200;
        return n/4;
    }

    public void addEnergy(World world, BlockPos pos,long cache)
    {
            MetaTileEntity mte = (MetaTileEntity) GTUtility.getMetaTileEntity(world, pos);
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

        NBTTagCompound compound = stack.getTagCompound();
        if(time>0) {
            if (!player.isSneaking()) {
                if (GTUtility.getMetaTileEntity(world, pos) instanceof MetaTileEntity) {
                    long cache = 0;
                    MetaTileEntity mte = (MetaTileEntity) GTUtility.getMetaTileEntity(world, pos);
                    for (EnumFacing facing : EnumFacing.VALUES) {
                        if (mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                            IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);
                            cache = container.getEnergyStored();
                        }
                    }
                    TileEntity te = world.getTileEntity(pos);
                    if (te instanceof ITickable tickable) {
                        for (int i = 0; i < getRapid(compound.getInteger("storedTime")); i++) {
                            tickable.update();
                            addEnergy(world, pos, cache);
                        }
                        time = compound.getInteger("storedTime");
                        time -= getRapid(compound.getInteger("storedTime"));
                        compound.setInteger("storedTime", time);
                    }
                } else {
                    TileEntity te = world.getTileEntity(pos);
                    if (te instanceof ITickable tickable) {
                        for (int i = 0; i < getRapid(compound.getInteger("storedTime")); i++) {
                            tickable.update();
                        }
                        time = compound.getInteger("storedTime");
                        time -= getRapid(compound.getInteger("storedTime"));
                        compound.setInteger("storedTime", time);
                    }
                }
            }
            else handleRandomTickMode(world,pos,compound);
        }
        return EnumActionResult.SUCCESS;
    }
    private boolean handleRandomTickMode(World world, BlockPos pos,NBTTagCompound compound) {
        time = compound.getInteger("storedTime");
        if(time<1024)return false;
        time -= 1024;
        compound.setInteger("storedTime", time);

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block.getTickRandomly()) {
            block.randomTick(world, pos, state, world.rand);
        }
        return true;
    }

}
