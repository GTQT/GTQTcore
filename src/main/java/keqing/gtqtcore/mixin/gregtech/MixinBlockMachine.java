package keqing.gtqtcore.mixin.gregtech;

import gregtech.api.GregTechAPI;
import gregtech.api.block.machines.BlockMachine;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.common.items.MetaItems;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
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

@Mixin(value = BlockMachine.class, remap = false)
public class MixinBlockMachine {

    @Inject(method = "onBlockPlacedBy", at = @At(value = "INVOKE", target = "Lgregtech/api/metatileentity/MetaTileEntity;onPlacement()V", shift = At.Shift.BEFORE))
    private void onBlockPlacedBy(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityLivingBase placer, ItemStack stack, CallbackInfo ci) {
        IGregTechTileEntity holder = (IGregTechTileEntity) worldIn.getTileEntity(pos);
        MetaTileEntity sampleMetaTileEntity = (MetaTileEntity) GregTechAPI.MTE_REGISTRY.getObjectById(stack.getItemDamage());
        if (holder != null && sampleMetaTileEntity != null) {
            if (placer instanceof EntityPlayer) {
                ItemStack offhand = placer.getHeldItemOffhand();

                for (int i = 0; i < EnumDyeColor.values().length; ++i) {
                    if (offhand.isItemEqual(MetaItems.SPRAY_CAN_DYES[i].getStackForm())) {
                        ((IItemBehaviour) MetaItems.SPRAY_CAN_DYES[i].getBehaviours().get(0)).onItemUse((EntityPlayer) placer, worldIn, pos, EnumHand.OFF_HAND, EnumFacing.UP, 0.0F, 0.0F, 0.0F);
                        break;
                    }
                    if (offhand.isItemEqual(GTQTMetaItems.ENDLESS_SPRAY_CAN_DYES[i].getStackForm())) {
                        ((IItemBehaviour) GTQTMetaItems.ENDLESS_SPRAY_CAN_DYES[i].getBehaviours().get(0)).onItemUse((EntityPlayer) placer, worldIn, pos, EnumHand.OFF_HAND, EnumFacing.UP, 0.0F, 0.0F, 0.0F);
                        break;
                    }
                }
            }
        }
    }
}