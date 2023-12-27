package keqing.gtqtcore.common.items.behaviors;



import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.items.gui.ItemUIFactory;
import gregtech.api.items.gui.PlayerInventoryHolder;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import keqing.gtqtcore.api.pattern.utils.JsonBlockPattern;
import keqing.gtqtcore.api.utils.GTQTLog;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureWriteBehavior implements IItemBehaviour, ItemUIFactory {

    public static final StructureWriteBehavior INSTANCE = new StructureWriteBehavior();

    protected StructureWriteBehavior() {/**/}

    @Override
    public ModularUI createUI(PlayerInventoryHolder playerInventoryHolder, EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 120)
                .image(10, 8, 156, 50, GuiTextures.DISPLAY)
                .dynamicLabel(15, 13, () -> {
                    int x = 0;
                    int y = 0;
                    int z = 0;
                    if (getPos(playerInventoryHolder.getCurrentItem()) != null) {
                        BlockPos[] blockPos = getPos(playerInventoryHolder.getCurrentItem());
                        x = 1 + blockPos[1].getX() - blockPos[0].getX();
                        y = 1 + blockPos[1].getY() - blockPos[0].getY();
                        z = 1 + blockPos[1].getZ() - blockPos[0].getZ();
                    }
                    return I18n.format("metaitem.debug.structure_writer.structural_scale", x, y, z);
                }, 0xFAF9F6)
                .widget(new ClickButtonWidget(10, 68, 77, 20, I18n.format("metaitem.debug.structure_writer.export_to_log"), clickData -> exportLog(playerInventoryHolder)))
                .widget(new ClickButtonWidget(90, 68, 77, 20, I18n.format("metaitem.debug.structure_writer.export_to_json"), clickData -> exportLog(playerInventoryHolder)))
                .widget(new ClickButtonWidget(10, 91, 77, 20, I18n.format("metaitem.debug.structure_writer.rotate_along_y_axis"), clickData -> exportLog(playerInventoryHolder)))
                .widget(new ClickButtonWidget(90, 91, 77, 20, I18n.format("metaitem.debug.structure_writer.ex"), clickData -> exportLog(playerInventoryHolder)))
                .build(playerInventoryHolder, entityPlayer);
    }

    private void exportLog(PlayerInventoryHolder playerInventoryHolder) {
        if (getPos(playerInventoryHolder.getCurrentItem()) != null && !playerInventoryHolder.player.world.isRemote) {
            BlockPos[] blockPos = getPos(playerInventoryHolder.getCurrentItem());
            StringBuilder builder = new StringBuilder();
            JsonBlockPattern blockPattern = new JsonBlockPattern(playerInventoryHolder.player.world, blockPos[0].getX(), blockPos[0].getY(), blockPos[0].getZ(), blockPos[1].getX(), blockPos[1].getY(), blockPos[1].getZ());
            for (int i = 0; i < blockPattern.pattern.length; i++) {
                String[] strings = blockPattern.pattern[i];
                builder.append(".aisle(");
                for (String string : strings) {
                    builder.append(String.format("\"%s\", ", string));
                }
                builder.append(")\n");
            }
            GTQTLog.logger.info(builder.toString());
        }
    }

    public static boolean isItemStructureWriter(ItemStack stack) {
        if (stack.isEmpty()) return false;

        if (stack.getItem() instanceof MetaItem<?> metaItem) {
            MetaItem<?>.MetaValueItem valueItem = metaItem.getItem(stack);
            return valueItem != null && valueItem.getBehaviours().contains(INSTANCE);
        }
        return false;
    }

    public static BlockPos[] getPos(ItemStack stack) {
        NBTTagCompound tag = stack.getOrCreateSubCompound("blueprint");
        if (!tag.hasKey("minX")) return null;
        return new BlockPos[]{
                new BlockPos(tag.getInteger("minX"), tag.getInteger("minY"), tag.getInteger("minZ")),
                new BlockPos(tag.getInteger("maxX"), tag.getInteger("maxY"), tag.getInteger("maxZ"))
        };
    }

    public static void addPos(ItemStack stack, BlockPos pos) {
        NBTTagCompound tag = stack.getOrCreateSubCompound("blueprint");
        if (!tag.hasKey("minX") || tag.getInteger("minX") > pos.getX()) {
            tag.setInteger("minX", pos.getX());
        }
        if (!tag.hasKey("maxX") || tag.getInteger("maxX") < pos.getX()) {
            tag.setInteger("maxX", pos.getX());
        }

        if (!tag.hasKey("minY") || tag.getInteger("minY") > pos.getY()) {
            tag.setInteger("minY", pos.getY());
        }
        if (!tag.hasKey("maxY") || tag.getInteger("maxY") < pos.getY()) {
            tag.setInteger("maxY", pos.getY());
        }

        if (!tag.hasKey("minZ") || tag.getInteger("minZ") > pos.getZ()) {
            tag.setInteger("minZ", pos.getZ());
        }
        if (!tag.hasKey("maxZ") || tag.getInteger("maxZ") < pos.getZ()) {
            tag.setInteger("maxZ", pos.getZ());
        }
    }

    public static void removePos(ItemStack stack) {
        NBTTagCompound tag = stack.getOrCreateSubCompound("blueprint");
        tag.removeTag("minX");
        tag.removeTag("maxX");
        tag.removeTag("minY");
        tag.removeTag("maxY");
        tag.removeTag("minZ");
        tag.removeTag("maxZ");
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!player.isSneaking()) {
            addPos(stack, pos);
        } else {
            removePos(stack);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            removePos(stack);
        } else {
            if (!worldIn.isRemote) {
                PlayerInventoryHolder holder = new PlayerInventoryHolder(player, hand);
                holder.openUI();
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
