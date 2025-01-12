package keqing.gtqtcore.mixin.gregtech;


import gregtech.api.pipenet.block.BlockPipe;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.items.metaitem.stats.IItemBehaviour;

@Mixin(BlockPipe.class)
public class MixinBlockPipe {

    @Inject(method = "onBlockPlacedBy", at = @At("TAIL"))
    private void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack, CallbackInfo ci) {
        IPipeTile<?, ?> pipeTile = ((BlockPipe<?, ?, ?>) (Object) this).getPipeTileEntity(worldIn, pos);
        if (pipeTile != null && placer instanceof EntityPlayer) {
            ItemStack offhand = placer.getHeldItemOffhand();

            for (int i = 0; i < EnumDyeColor.values().length; ++i) {
                if (offhand.isItemEqual(GTQTMetaItems.ENDLESS_SPRAY_CAN_DYES[i].getStackForm())) {
                    ((IItemBehaviour) GTQTMetaItems.ENDLESS_SPRAY_CAN_DYES[i].getBehaviours().get(0)).onItemUse((EntityPlayer) placer, worldIn, pos, EnumHand.OFF_HAND, EnumFacing.UP, 0.0F, 0.0F, 0.0F);
                    break;
                }
            }
        }
    }

}
