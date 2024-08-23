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
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.metatileentity.multiblock.RecipeMapSteamMultiblockController;
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
import static keqing.gtqtcore.common.items.GTQTMetaItems.TIME_BOTTLE;

public class TimeBottleBehavior implements IItemBehaviour {
    public TimeBottleBehavior(){}

    private int time;
    private int maxTime=20*3600*8;
    public void onUpdate(ItemStack itemStack, Entity entity) {
        if (entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack invStack = player.inventory.getStackInSlot(i);

                if (invStack.getItem() == TIME_BOTTLE.getMetaItem()) {
                    if (itemStack.hasTagCompound()) {
                        NBTTagCompound compound = itemStack.getTagCompound();
                        time = compound.getInteger("storedTime");
                        if(time<maxTime
                        )time++;
                        compound.setInteger("storedTime", time);
                    } else {
                        NBTTagCompound compound = new NBTTagCompound();
                        compound.setInteger("storedTime", time);
                        itemStack.setTagCompound(compound);
                    }
                }
            }
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

            lines.add(I18n.format("缓存：%s 小时 /%s 分钟/ %s 秒", hours, minutes, seconds));
            lines.add(I18n.format("时间缓存：%s tick", compound.getInteger("storedTime")));
            lines.add(I18n.format("加速时间：%s tick", getRapid(compound.getInteger("storedTime"))));
            lines.add(I18n.format("右键无损加速耗电机器（不耗电），也可加速熔炉"));
            lines.add(I18n.format("潜行右键加速随机刻（作物，树苗等需要光照条件），固定消耗60秒"));
        }
    }
    public int getRapid(int n)
    {
        if(n<1200)return n;
        if(n<4800)return 1200;
        return 4800;
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
                    TileEntity te = world.getTileEntity(pos);
                    if(mte instanceof MultiblockControllerBase){
                        var mcb = ((MultiblockControllerBase) mte);
                        if(mcb.isStructureFormed() && mcb.isValid())
                        {
                            final var inenergy =  mcb.getAbilities(MultiblockAbility.INPUT_ENERGY);
                            if(inenergy.size()>0)
                            {
                                long[] energys = new long[inenergy.size()];
                                for (int j = 0; j < inenergy.size(); j++) {
                                    energys[j] = inenergy.get(j).getEnergyStored();
                                }
                                if(te instanceof ITickable)
                                {
                                    for (int i = 0; i < getRapid(compound.getInteger("storedTime")); i++) {
                                        ((ITickable) te).update();
                                        for (int j = 0; j < inenergy.size(); j++) {
                                            if(inenergy.get(j).getEnergyStored()<energys[j])
                                                inenergy.get(j).addEnergy(energys[j]-inenergy.get(j).getEnergyStored());
                                        }
                                    }
                                    time = compound.getInteger("storedTime");
                                    time -= getRapid(compound.getInteger("storedTime"));
                                    compound.setInteger("storedTime", time);

                                }
                            }else if(mte instanceof RecipeMapSteamMultiblockController)
                            {
                                var smte = ((RecipeMapSteamMultiblockController) mte);
                                final var fin = smte.getSteamFluidTank();
                                int[] energys = new int[fin.getTanks()];
                                for (int j = 0; j < fin.getTanks(); j++) {
                                    energys[j] = fin.getTankAt(j).getFluidAmount();
                                }
                                if(te instanceof ITickable)
                                {
                                    for (int i = 0; i < getRapid(compound.getInteger("storedTime")); i++) {
                                        ((ITickable) te).update();
                                        for (int j = 0; j < fin.getTanks(); j++)
                                        {
                                            if(fin.getTankAt(j).getFluidAmount()<energys[j])
                                            {
                                                fin.getTankAt(j).fill(Materials.Steam.getFluid(energys[j]-fin.getTankAt(j).getFluidAmount()),true);
                                            }

                                        }
                                    }
                                    time = compound.getInteger("storedTime");
                                    time -= getRapid(compound.getInteger("storedTime"));
                                    compound.setInteger("storedTime", time);
                                }
                            }
                            else
                            {
                                for (int i = 0; i < getRapid(compound.getInteger("storedTime")); i++) {
                                    ((ITickable) te).update();
                                }
                                time = compound.getInteger("storedTime");
                                time -= getRapid(compound.getInteger("storedTime"));
                                compound.setInteger("storedTime", time);
                                ((ITickable) te).update();
                            }
                        }
                    }
                  else{
                        for (EnumFacing facing : EnumFacing.VALUES) {
                            if (mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                                IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);
                                cache = container.getEnergyStored();
                            }
                        }
                        if (te instanceof ITickable tickable) {
                            for (int i = 0; i < getRapid(compound.getInteger("storedTime")); i++) {
                                tickable.update();
                                addEnergy(world, pos, cache);
                            }
                            time = compound.getInteger("storedTime");
                            time -= getRapid(compound.getInteger("storedTime"));
                            compound.setInteger("storedTime", time);
                        }
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
        if(time<1200)return false;
        time -= 1200;
        compound.setInteger("storedTime", time);
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block.getTickRandomly()) {
            for(int i=0;i<600;i++) block.randomTick(world, pos.toImmutable(), state, world.rand);
        }
        return true;
    }

}
