package keqing.gtqtcore.common.items.tool;

import gregtech.api.items.toolitem.ToolHelper;
import gregtech.api.items.toolitem.behavior.IToolBehavior;
import gregtech.common.items.tool.TorchPlaceBehavior;
import ibxm.Player;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.minecraft.block.Block.spawnAsEntity;
import static net.minecraft.enchantment.Enchantment.getEnchantmentByID;
import static net.minecraft.init.Enchantments.SILK_TOUCH;

public class VAJRABehavior implements IToolBehavior {
    public static final VAJRABehavior INSTANCE = new VAJRABehavior();

    Boolean model=true;
    Boolean explosive=false;
    int i;
    protected VAJRABehavior() {

    }

    public  EnumActionResult onItemUse( EntityPlayer player,  World world,  BlockPos pos, EnumHand hand,  EnumFacing facing, float hitX, float hitY, float hitZ) {

        return EnumActionResult.SUCCESS;
    }

    public void addBehaviorNBT( ItemStack stack,  NBTTagCompound tag) {
        tag.setBoolean("Vajra", true);
        tag.setBoolean("model", model);
    }
    public void hitEntity( ItemStack stack,  EntityLivingBase target,  EntityLivingBase attacker) {
        if(explosive)
        {
            target.world.createExplosion((Entity)null, target.posX, target.posY, target.posZ, 3, true);
        }
    }
    public void onBlockStartBreak( ItemStack stack, BlockPos pos,  EntityPlayer player) {
        if(explosive)
        {
            player.world.createExplosion((Entity)null, pos.getX(), pos.getY(), pos.getZ(), 3, true);
        }
    }
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if(player.isSneaking()) {
            i++;
            if (i == 6) {
                explosive = false;
                model = true;
                player.sendMessage(new TextComponentTranslation("精准模式"));
                i = 0;
            }
            if (i == 2) {
                model = false;
                player.sendMessage(new TextComponentTranslation("常规模式"));
            }
            if (i == 4) {
                model = false;
                explosive = true;
                player.sendMessage(new TextComponentTranslation("爆炸模式"));
            }
        }
        if (model) {
            addEnchantmentById(player.getHeldItemMainhand(),33,1);

        }
        if(!model)
        {
            removeEnchantmentById(player);
        }
        return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    public void addInformation( ItemStack stack,  World world,  List<String> tooltip,  ITooltipFlag flag) {
        tooltip.add(I18n.format("item.gt.tool.behavior.vajra", new Object[0]));
        tooltip.add(I18n.format("精准：%s", model));
        tooltip.add(I18n.format("爆炸：%s", explosive));
    }

    public void addEnchantmentById(ItemStack stack, int enchantId, int level) {

        // 获取或创建ItemStack的NBT标签
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            stack.setTagCompound(nbt);
        }

        // 检查是否已存在"ench"列表
        NBTTagList enchantments = nbt.getTagList("ench", 10);
        if (enchantments == null) {
            enchantments = new NBTTagList();
        }

        NBTTagCompound enchantmentCompound = new NBTTagCompound();
        enchantmentCompound.setShort("id", (short) enchantId);
        enchantmentCompound.setShort("lvl", (short) level);

        enchantments.appendTag(enchantmentCompound);

        nbt.setTag("ench", enchantments);
    }

    public void removeEnchantmentById(EntityPlayer player) {
        // 获取ItemStack的NBT标签
        NBTTagCompound nbt = player.getHeldItemMainhand().getTagCompound();
        // 如果NBT标签存在且包含"ench"
        if (nbt.hasKey("ench"))
        {
            NBTTagList enchantments = nbt.getTagList("ench",10);

            // 创建一个新的NBT列表来存储剩余的附魔
            NBTTagList newEnchantments = new NBTTagList();
            for (int i = 0; i < enchantments.tagCount(); i++) {
                NBTTagCompound enchantmentCompound = enchantments.getCompoundTagAt(i);
                // 检查当前附魔的ID是否不是要移除的
                if (getEnchantmentByID(enchantmentCompound.getShort("id")) != SILK_TOUCH) {
                    // 如果不是，添加到新的列表中
                    newEnchantments.appendTag(enchantmentCompound);
                }
            }
            // 用新的列表替换旧的"ench"列表
            player.getHeldItemMainhand().getTagCompound().removeTag("ench");
            player.getHeldItemMainhand().getTagCompound().setTag("ench", newEnchantments);
        }
    }
}
