package keqing.gtqtcore.common.metatileentities.multi.multiblock.primitive;


import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.CubeRendererState;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.cclop.ColourOperation;
import gregtech.client.renderer.cclop.LightMapOperation;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.BloomEffectUtil;
import gregtech.client.utils.TooltipHelper;
import keqing.gtqtcore.api.capability.impl.NoEnergyMultiblockRecipeLogic;
import keqing.gtqtcore.api.metaileentity.multiblock.NoEnergyMultiblockController;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing6;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class MetaTileEntityPrimitiveReactor extends NoEnergyMultiblockController {

    private static final TraceabilityPredicate SNOW_PREDICATE = new TraceabilityPredicate(
            bws -> GTUtility.isBlockSnow(bws.getBlockState()));
    int size;

    public MetaTileEntityPrimitiveReactor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.PR_MIX);
        this.recipeMapWorkable = new PrimitiveReactorRecipeLogic(this, recipeMap);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("size", size);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        size = data.getInteger("size");
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPrimitiveReactor(metaTileEntityId);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.size = context.getOrDefault("length", 1);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("BBB", "XXX", "XXX")
                .aisle("BBB", "X&X", "X#X").setRepeatable(1, 8)
                .aisle("BBB", "XYX", "XXX")
                .where('B', states(GTQTMetaBlocks.blockMultiblockCasing6.getState(BlockMultiblockCasing6.CasingType.REINFORCED_TREATED_WOOD_BOTTOM)))
                .where('X', states(GTQTMetaBlocks.blockMultiblockCasing6.getState(BlockMultiblockCasing6.CasingType.REINFORCED_TREATED_WOOD_WALL))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setPreviewCount(1).setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setPreviewCount(1).setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setPreviewCount(1).setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setPreviewCount(1).setMaxGlobalLimited(3)))
                .where('#', air())
                .where('&', air().or(SNOW_PREDICATE)) // this won't stay in the structure, and will be broken while
                // running
                .where('Y', selfPredicate())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.REINFORCED_TREATED_WOOD_WALL;
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("村里的大缸（远离司马光）", new Object[0]));
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(),
                recipeMapWorkable.isActive(), recipeMapWorkable.isWorkingEnabled());
        if (recipeMapWorkable.isActive() && isStructureFormed()) {
            EnumFacing back = getFrontFacing().getOpposite();
            for (int i = 1; i <= size; i++) {
                Matrix4 offset = translation.copy().translate(back.getXOffset() * size, -0.3, back.getZOffset() * size);
                CubeRendererState op = Textures.RENDER_STATE.get();
                Textures.RENDER_STATE.set(new CubeRendererState(op.layer, CubeRendererState.PASS_MASK, op.world));
                Textures.renderFace(renderState, offset,
                        ArrayUtils.addAll(pipeline, new LightMapOperation(240, 240), new ColourOperation(0xFFFFFFFF)),
                        EnumFacing.UP, Cuboid6.full, TextureUtils.getBlockTexture("lava_still"),
                        BloomEffectUtil.getEffectiveBloomLayer());
                Textures.RENDER_STATE.set(op);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.PRIMITIVE_PUMP_OVERLAY;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    public class PrimitiveReactorRecipeLogic extends NoEnergyMultiblockRecipeLogic {

        public PrimitiveReactorRecipeLogic(NoEnergyMultiblockController tileEntity, RecipeMap<?> recipeMap) {
            super(tileEntity, recipeMap);
        }

        @Override
        public int getParallelLimit() {
            return size;
        }
    }
}