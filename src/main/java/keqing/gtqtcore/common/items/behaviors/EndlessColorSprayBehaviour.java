package keqing.gtqtcore.common.items.behaviors;

import appeng.api.util.AEColor;
import appeng.tile.networking.TileCableBus;
import com.google.common.collect.UnmodifiableIterator;
import gregtech.api.items.metaitem.stats.IItemDurabilityManager;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.util.GradientUtil;
import gregtech.api.util.Mods;
import gregtech.common.items.behaviors.AbstractUsableBehaviour;
import gregtech.core.sound.GTSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class EndlessColorSprayBehaviour extends AbstractUsableBehaviour implements IItemDurabilityManager {
    private final ItemStack empty;
    private final EnumDyeColor color;
    private final Pair<Color, Color> durabilityBarColors;

    public EndlessColorSprayBehaviour(ItemStack empty, int totalUses, int color) {
        super(totalUses);
        this.empty = empty;
        EnumDyeColor[] colors = EnumDyeColor.values();
        this.color = color < colors.length && color >= 0 ? colors[color] : null;
        int colorValue = this.color == null ? 9868950 : this.color.getColorValue();
        this.durabilityBarColors = GradientUtil.getGradient(colorValue, 10);
    }

    public ActionResult<ItemStack> onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (!player.canPlayerEdit(pos, facing, stack)) {
            return ActionResult.newResult(EnumActionResult.FAIL, player.getHeldItem(hand));
        } else if (!this.tryPaintBlock(player, world, pos, facing)) {
            return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(hand));
        } else {
            this.useItemDurability(player, hand, stack, this.empty.copy());
            world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, GTSoundEvents.SPRAY_CAN_TOOL, SoundCategory.PLAYERS, 1.0F, 1.0F);
            return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        }
    }

    private boolean tryPaintBlock(EntityPlayer player, World world, BlockPos pos, EnumFacing side) {
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (this.color == null) {
            return tryStripBlockColor(player, world, pos, block, side);
        } else {
            return block.recolorBlock(world, pos, side, this.color) || this.tryPaintSpecialBlock(player, world, pos, block);
        }
    }

    private boolean tryPaintSpecialBlock(EntityPlayer player, World world, BlockPos pos, Block block) {
        IBlockState newBlockState;
        if (block == Blocks.GLASS) {
            newBlockState = Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, this.color);
            world.setBlockState(pos, newBlockState);
            return true;
        } else if (block == Blocks.GLASS_PANE) {
            newBlockState = Blocks.STAINED_GLASS_PANE.getDefaultState().withProperty(BlockStainedGlassPane.COLOR, this.color);
            world.setBlockState(pos, newBlockState);
            return true;
        } else if (block == Blocks.HARDENED_CLAY) {
            newBlockState = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, this.color);
            world.setBlockState(pos, newBlockState);
            return true;
        } else {
            if (Mods.AppliedEnergistics2.isModLoaded()) {
                TileEntity te = world.getTileEntity(pos);
                if (te instanceof TileCableBus) {
                    TileCableBus cable = (TileCableBus)te;
                    if (cable.getColor().ordinal() != this.color.ordinal()) {
                        cable.recolourBlock((EnumFacing)null, AEColor.values()[this.color.ordinal()], player);
                        return true;
                    }
                }
            }

            return false;
        }
    }

    private static boolean tryStripBlockColor(EntityPlayer player, World world, BlockPos pos, Block block, EnumFacing side) {
        if (block == Blocks.STAINED_GLASS) {
            world.setBlockState(pos, Blocks.GLASS.getDefaultState());
            return true;
        } else if (block == Blocks.STAINED_GLASS_PANE) {
            world.setBlockState(pos, Blocks.GLASS_PANE.getDefaultState());
            return true;
        } else if (block == Blocks.STAINED_HARDENED_CLAY) {
            world.setBlockState(pos, Blocks.HARDENED_CLAY.getDefaultState());
            return true;
        } else {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof IGregTechTileEntity) {
                MetaTileEntity mte = ((IGregTechTileEntity)te).getMetaTileEntity();
                if (mte != null) {
                    if (mte.isPainted()) {
                        mte.setPaintingColor(-1);
                        return true;
                    }

                    return false;
                }
            }

            if (te instanceof IPipeTile) {
                IPipeTile<?, ?> pipe = (IPipeTile)te;
                if (pipe.isPainted()) {
                    pipe.setPaintingColor(-1);
                    return true;
                } else {
                    return false;
                }
            } else if (Mods.AppliedEnergistics2.isModLoaded() && te instanceof TileCableBus) {
                TileCableBus cable = (TileCableBus)te;
                if (cable.getColor() != AEColor.TRANSPARENT) {
                    cable.recolourBlock(null, AEColor.TRANSPARENT, player);
                    return true;
                } else {
                    return false;
                }
            } else {
                IBlockState state = world.getBlockState(pos);
                UnmodifiableIterator var7 = state.getProperties().keySet().iterator();

                IProperty prop;
                do {
                    if (!var7.hasNext()) {
                        return false;
                    }

                    prop = (IProperty)var7.next();
                } while(!prop.getName().equals("color") || prop.getValueClass() != EnumDyeColor.class);

                IBlockState defaultState = block.getDefaultState();
                EnumDyeColor defaultColor = EnumDyeColor.WHITE;

                try {
                    defaultColor = (EnumDyeColor)defaultState.getValue(prop);
                } catch (IllegalArgumentException var12) {
                }

                block.recolorBlock(world, pos, side, defaultColor);
                return true;
            }
        }
    }

    public void addInformation(ItemStack itemStack, List<String> lines) {
        int remainingUses = this.getUsesLeft(itemStack);
        if (this.color != null) {
            lines.add(I18n.format("behaviour.paintspray." + this.color.getTranslationKey() + ".tooltip"));
        } else {
            lines.add(I18n.format("behaviour.paintspray.solvent.tooltip"));
        }

        lines.add(I18n.format("剩余次数是无限的！"));
        lines.add(I18n.format("behaviour.paintspray.offhand"));
    }

    public double getDurabilityForDisplay(ItemStack itemStack) {
        return (double)this.getUsesLeft(itemStack) / (double)this.totalUses;
    }

    public @Nullable Pair<Color, Color> getDurabilityColorsForDisplay(ItemStack itemStack) {
        return this.durabilityBarColors;
    }

    public boolean doDamagedStateColors(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean useItemDurability(EntityPlayer player, EnumHand hand, ItemStack stack, ItemStack replacementStack) {
        return true;
    }
}