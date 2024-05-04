package keqing.gtqtcore.mixin.architecturecraft;

/*
 * Copyright (c) 2023 Nomifactory-CEu
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 2.1 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

import com.elytradev.architecture.common.block.BlockArchitecture;
import com.elytradev.architecture.common.block.BlockShape;
import com.elytradev.architecture.common.tile.TileShape;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;

/**
 * Improves Determining Whether the Shape can be Harvested, and the Hardness of the Shape.
 *
 * @author IntegerLimit
 */
@Mixin(value = BlockShape.class, remap = false)
public class MixinBlockShape extends BlockArchitecture<TileShape> {

    /**
     * Default Ignored Constructor
     */
    public MixinBlockShape(Material material) {
        super(material);
    }

    @Inject(method = "acBlockStrength",
            at = @At(value = "INVOKE",
                    target = "Lcom/elytradev/architecture/common/block/BlockShape;acCanHarvestBlock(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/entity/player/EntityPlayer;)Z"),
            cancellable = true)
    private static void baseBlockStrength(IBlockState state, EntityPlayer player, World world, BlockPos pos, CallbackInfoReturnable<Float> cir, @Local(ordinal = 1) float strength) {
        cir.setReturnValue(state.getBlock().canHarvestBlock(world, pos, player) ? strength / 100.0F : strength / 30.0F);
    }

    /**
     * Modified from {@link ForgeHooks#canHarvestBlock(Block, EntityPlayer, IBlockAccess, BlockPos)}
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public boolean canHarvestBlock(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player) {
        var te = TileShape.get(world, pos);
        if (te == null) return super.canHarvestBlock(world, pos, player);

        var baseState = te.getBaseBlockState();
        var baseBlock = baseState.getBlock();

        if (baseState.getMaterial().isToolNotRequired()) {
            return true;
        }

        ItemStack stack = player.getHeldItemMainhand();
        String tool = baseBlock.getHarvestTool(baseState);
        if (stack.isEmpty() || tool == null)
            return player.canHarvestBlock(baseState);

        int toolLevel = stack.getItem().getHarvestLevel(stack, tool, player, baseState);
        if (toolLevel < 0)
            return player.canHarvestBlock(baseState);

        return toolLevel >= baseBlock.getHarvestLevel(baseState);
    }

}