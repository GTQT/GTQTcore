package keqing.gtqtcore.client.renderer;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.custom.QuantumStorageRenderer;
import gregtech.common.ConfigHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//处理超级缸内部的多种液体渲染
public class MultiQuantumStorageRenderer extends QuantumStorageRenderer {
    @SideOnly(Side.CLIENT)

    public static void renderMultiTankFluid(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, FluidTank[] tanks, IBlockAccess world, BlockPos pos, EnumFacing frontFacing, Cuboid6 partialFluidBox, double fluidHigh, Cuboid6 gasPartialFluidBox, double gasHigh) {
        partialFluidBox = new Cuboid6(0.06640625, 0.12890625, 0.06640625, 0.93359375, 0.93359375, 0.93359375);
        gasPartialFluidBox = new Cuboid6(0.06640625, 0.12890625, 0.06640625, 0.93359375, 0.83359375, 0.93359375);
        for (int i = 0; i < 4; i++)
        {
            FluidStack stack = tanks[i].getFluid();
            if (stack != null && stack.amount != 0 && ConfigHolder.client.enableFancyChestRender) {
                Fluid fluid = stack.getFluid();
                if (fluid != null) {
                    if (world != null) {
                        renderState.setBrightness(world, pos);
                    }
                    boolean gas = fluid.isGaseous(stack);
                    if (gas) {
                        double gasFillFraction = (double)stack.amount / (double)tanks[i].getCapacity();
                        gasHigh = Math.min(2.5 * gasFillFraction + 0.25, 3.0) / 16.0;
                        gasPartialFluidBox.min.y = gasPartialFluidBox.max.y - gasHigh;
                        renderState.setFluidColour(stack);
                        ResourceLocation fluidStill = fluid.getStill(stack);
                        TextureAtlasSprite fluidStillSprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluidStill.toString());
                        Textures.renderFace(renderState, translation, pipeline, frontFacing, gasPartialFluidBox, fluidStillSprite, BlockRenderLayer.CUTOUT_MIPPED);
                        Textures.renderFace(renderState, translation, pipeline, EnumFacing.DOWN, gasPartialFluidBox, fluidStillSprite, BlockRenderLayer.CUTOUT_MIPPED);
                    } else {
                        double fillFraction = (double)stack.amount / (double)tanks[i].getCapacity();
                        fluidHigh = Math.min(2.5 * fillFraction + 0.25, 13.0) / 16.0;
                        partialFluidBox.max.y = partialFluidBox.min.y + fluidHigh;
                        renderState.setFluidColour(stack);
                        ResourceLocation fluidStill = fluid.getStill(stack);
                        TextureAtlasSprite fluidStillSprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluidStill.toString());
                        Textures.renderFace(renderState, translation, pipeline, frontFacing, partialFluidBox, fluidStillSprite, BlockRenderLayer.CUTOUT_MIPPED);
                        Textures.renderFace(renderState, translation, pipeline, EnumFacing.UP, partialFluidBox, fluidStillSprite, BlockRenderLayer.CUTOUT_MIPPED);
                    }
                    GlStateManager.resetColor();
                    renderState.reset();

                    //堆叠液体效果
                    if (gas) gasPartialFluidBox.max.y = gasPartialFluidBox.min.y;
                    else partialFluidBox.min.y = partialFluidBox.max.y;



                }
            }
        }
    }
}