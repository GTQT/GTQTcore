package keqing.gtqtcore.common.block.explosive;

import gregtech.common.blocks.explosive.BlockGTExplosive;
import gregtech.common.entities.EntityGTExplosive;
import gregtech.common.entities.ITNTEntity;

import keqing.gtqtcore.common.entities.STNTEntity;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BlockSTNT extends BlockGTExplosive {

    public BlockSTNT() {
        super(Material.TNT, true, true, 40);
        setHardness(0);
        setSoundType(SoundType.PLANT);
    }

    @Override
    protected EntityGTExplosive createEntity(World world, BlockPos pos, EntityLivingBase exploder) {
        float x = pos.getX() + 0.5F, y = pos.getY(), z = pos.getZ() + 0.5F;
        return new STNTEntity(world, x, y, z, exploder);
    }

    @Override
    public void addInformation( ItemStack stack,  World world,  List<String> tooltip,
                                ITooltipFlag flag) {
        tooltip.add(I18n.format("tile.stnt.drops_tooltip"));
        super.addInformation(stack, world, tooltip, flag);
    }
}