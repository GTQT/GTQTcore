package keqing.gtqtcore.api.utils;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.common.items.MetaItems;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.item.ItemStack;

import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class GTQTCPUHelper {
    /*
    Stepper(1,2,INTEGRATED_LOGIC_CIRCUIT_WAFER);
    Stepper(1,2,RANDOM_ACCESS_MEMORY_WAFER);
    Stepper(1,2,CENTRAL_PROCESSING_UNIT_WAFER);
    Stepper(1,2,ULTRA_LOW_POWER_INTEGRATED_CIRCUIT_WAFER);
    Stepper(1,2,LOW_POWER_INTEGRATED_CIRCUIT_WAFER);
    Stepper(1,2,SIMPLE_SYSTEM_ON_CHIP_WAFER);
    Stepper(2,3,NOR_MEMORY_CHIP_WAFER);
    Stepper(2,3,POWER_INTEGRATED_CIRCUIT_WAFER);
    Stepper(2,3,NAND_MEMORY_CHIP_WAFER);
    Stepper(2,3,SYSTEM_ON_CHIP_WAFER);
    Stepper(3,3,ADVANCED_SYSTEM_ON_CHIP_WAFER);
    Stepper(4,3,HIGHLY_ADVANCED_SOC_WAFER);

     */
    public static MetaItem.MetaValueItem []item={
            INTEGRATED_LOGIC_CIRCUIT_WAFER,
            RANDOM_ACCESS_MEMORY_WAFER,
            CENTRAL_PROCESSING_UNIT_WAFER,
            ULTRA_LOW_POWER_INTEGRATED_CIRCUIT_WAFER,
            LOW_POWER_INTEGRATED_CIRCUIT_WAFER,
            SIMPLE_SYSTEM_ON_CHIP_WAFER,
            NANO_CENTRAL_PROCESSING_UNIT_WAFER,
            QUBIT_CENTRAL_PROCESSING_UNIT_WAFER,
            ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT_WAFER,
            HIGH_POWER_INTEGRATED_CIRCUIT_WAFER,
            NOR_MEMORY_CHIP_WAFER,
            POWER_INTEGRATED_CIRCUIT_WAFER,
            NAND_MEMORY_CHIP_WAFER,
            SYSTEM_ON_CHIP_WAFER,
            ADVANCED_SYSTEM_ON_CHIP_WAFER,
            HIGHLY_ADVANCED_SOC_WAFER,
            NANO_POWER_IC_WAFER,
            PICO_POWER_IC_WAFER,
            FEMTO_POWER_IC_WAFER,
    };

    public static MetaItem.MetaValueItem []wafer={SILICON_WAFER,PHOSPHORUS_WAFER,NAQUADAH_WAFER,EUROPIUM_WAFER,AMERICIUM_WAFER,DUBNIUM_WAFER,NEUTRONIUM_WAFER};
    static int [][]kind={{1,2},{1,2},{1,2},{1,2},{1,2},{1,2},{2,2},{2,2},{2,3},{2,3},{2,3},{2,3},{2,3},{2,3},{3,3},{4,3},{4,4},{4,5},{5,5}};

    //public boolean checkWafer(boolean sim) {
    //        var slots = this.getInputInventory().getSlots();
    //        for (int i = 0; i < slots; i++) {
    //            ItemStack item = this.getInputInventory().getStackInSlot(i);
    //            for(int i;i<wafer.list;i++)
    //            checkWafer(itemStack,wafer[i]){
    //                this.getInputInventory().extractItem(i, 1, sim);
    //                wafer[i]++;
    //                return true;
    //            }
    //        }
    //        return false;
    //    }
    public static int getRateFromWaferLevel(int i)
    {
        return (int) Math.pow(2,i+1);
    }
    public static boolean checkWafer(ItemStack itemStack, MetaItem.MetaValueItem item)
    {
        return itemStack.getMetadata() ==item.getMetaValue();
    }
    public static int getRangeFromStatue(int wafer, int level)
    {

        for(int i=0;i<kind.length;i++)
        {
            if(kind[i][0]>wafer||kind[i][1]>level)return i+1;
        }
        return 0;
    }

    public static int returnCycleFromWafer(int amount, int wafer, int level)
    {
        return amount/getRangeFromStatue(wafer,level);
    }
    public static int returnExtraAmount(int amount, int wafer, int level)
    {
        return amount%getRangeFromStatue(wafer,level);
    }
    //每个晶圆的数量               CPU序号  晶圆总数       晶圆等级   光刻胶等级
    //retrun itemstack时使用此函数作为数量
    public static int getPreAmount(int number, int amount, int wafer, int level)
    {
        return returnCycleFromWafer(amount,wafer,level)+(returnExtraAmount(amount,wafer,level)>number?1:0);
    }
    //for(int number:item.lenth) GTTransferUtils.insertItem(this.outputInventory, getStack(xxx), false);
    public static ItemStack getStack(int number, int amount, int wafer, int level)
    {
        if(number>=getRangeFromStatue(wafer,level))return ItemStack.EMPTY;
        if(getPreAmount(number,amount,wafer,level)==0)return ItemStack.EMPTY;
        return new ItemStack(item[number].getMetaItem(), getPreAmount(number,amount*getRateFromWaferLevel(wafer),wafer,level), item[number].getMetaValue());
    }

    //先把输入总线的晶圆全部转化为虚拟晶圆，然后分别输出不同等级晶圆的产品
    //必须在外部循环CPU
    //独立time计时
    //update if(wafer1>0)for(int number:item.length) GTTransferUtils.insertItem(this.outputInventory, getStack(number,wafer1.num,wafer1.level,光刻胶等级), false);
}
