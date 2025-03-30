package keqing.gtqtcore.common.covers;


import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.ItemHandlerDelegate;
import gregtech.api.cover.CoverBase;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.cover.CoverableView;
import gregtech.client.renderer.texture.Textures;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class CoverOneStackFilter extends CoverBase {


    protected ItemHandlerOneStackLimited itemHandler;

    public CoverOneStackFilter(CoverDefinition definition, CoverableView coverableView, EnumFacing attachedSide) {
        super(definition, coverableView, attachedSide);
    }

    private static boolean areItemStacksEqualIgnoreAmount(ItemStack s1, ItemStack s2) {
        var s1c = s1.copy();
        var s2c = s2.copy();
        s1c.setCount(1);
        s2c.setCount(1);
        return ItemStack.areItemStacksEqual(s1c, s2c);
    }

    @Override
    public boolean canAttach(CoverableView coverable, EnumFacing side) {
        return coverable.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, getAttachedSide()) != null;
    }

    @Override
    public void renderCover(CCRenderState renderState, Matrix4 translation,
                            IVertexOperation[] pipeline, Cuboid6 plateBox,
                            BlockRenderLayer layer) {
        GTQTTextures.ONE_STACK_FILTER_OVERLAY.renderSided(getAttachedSide(), plateBox, renderState, pipeline, translation);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, T defaultValue) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (defaultValue == null) {
                return null;
            }
            IItemHandler delegate = (IItemHandler) defaultValue;
            if (itemHandler == null || itemHandler.delegate != delegate) {
                itemHandler = new ItemHandlerOneStackLimited(delegate);
            }
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
        }
        return defaultValue;
    }

    protected static class ItemHandlerOneStackLimited extends ItemHandlerDelegate {

        public ItemHandlerOneStackLimited(IItemHandler delegate) {
            super(delegate);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            for (int i = 0; i < getSlots(); i++) {
                if (areItemStacksEqualIgnoreAmount(stack, getStackInSlot(i)) && i != slot) {
                    return stack;
                }
            }
            return super.insertItem(slot, stack, simulate);
        }
    }
}