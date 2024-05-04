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
import com.elytradev.architecture.common.block.BlockSawbench;
import com.elytradev.architecture.common.tile.TileSawbench;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Fixes the Particle Texture of the Sawbench.
 *
 * @author IntegerLimit
 */
@Mixin(value = BlockSawbench.class, remap = false)
public class MixinBlockSawbench extends BlockArchitecture<TileSawbench> {

    /**
     * Default Ignored Constructor
     */
    public MixinBlockSawbench(Material material) {
        super(material);
    }

    @Override
    public IBlockState getParticleState(IBlockAccess world, BlockPos pos) {
        return Blocks.IRON_BLOCK.getDefaultState();
    }
}