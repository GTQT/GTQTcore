package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.GTQTDataCode;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static gregtech.api.GTValues.*;

public class MetaTileEntityPreciseAssembler extends MultiMapMultiblockController {

    private int CasingTier;
    private int InternalCasingTier;
    private int tier;
    private static boolean init = false;
    private static List<IBlockState> finalListCasing;
    private static List<IBlockState> finalListInternalCasing;

    public MetaTileEntityPreciseAssembler(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.ASSEMBLER_RECIPES,
                GTQTcoreRecipeMaps.PRECISE_ASSEMBLER_RECIPES,//普通装配
                GTQTcoreRecipeMaps.CW_PRECISE_ASSEMBLER_RECIPES
        });
        this.recipeMapWorkable = new PreciseAssemblerRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPreciseAssembler(metaTileEntityId);
    }


    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object CasingTier = context.get("PAC");
        Object InternalCasingTier = context.get("PAI");

        this.CasingTier = GTQTUtil.getOrDefault(() -> CasingTier instanceof IHeatingCoilBlockStats,
                () ->  ((IHeatingCoilBlockStats) CasingTier).getLevel(),
                BlockWireCoil.CoilType.CUPRONICKEL.getLevel());
        this.InternalCasingTier = GTQTUtil.getOrDefault(() -> InternalCasingTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)InternalCasingTier).getIntTier(),
                0);

        this.tier = this.CasingTier = this.InternalCasingTier;

        this.writeCustomData(GTQTDataCode.GTQT_CHANNEL_4, buf -> buf.writeInt(this.CasingTier));
    }

    @Override
    public void update() {
        super.update();

        if (this.getWorld().isRemote && this.CasingTier == 0) {
            this.writeCustomData(GTQTDataCode.GTQT_CHANNEL_7, buf -> {});
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("DDDDDDDDD", "F       F", "F       F", "F       F", "DDDDDDDDD")
                .aisle("CMMMMMMMC", "CGGGGGGGC", "CGGGGGGGC", "CGGGGGGGC", "DDDDDDDDD")
                .aisle("CMMMMMMMC", "C       C", "C       C", "C       C", "DDDDDDDDD")
                .aisle("CMMMMMMMC", "CGGGGGGGC", "CGGGGGGGC", "CGGGGGGGC", "DDDDDDDDD")
                .aisle("DDDDSDDDD", "F       F", "F       F", "F       F", "DDDDDDDDD")
                .where('S', selfPredicate())
                .where('C', TiredTraceabilityPredicate.CP_PA_CASING)
                .where('D', TiredTraceabilityPredicate.CP_PA_CASING
                        .setMinGlobalLimited(42)
                        .or(autoAbilities()))
                .where('F', states(getFrameState()))
                .where('G', states(getGlassState()))
                .where('M', TiredTraceabilityPredicate.CP_PA_INTERNAL_CASING)
                .build();
    }

    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(GTQTMaterials.MARM200Steel).getBlock(GTQTMaterials.MARM200Steel);
    }

    private static IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.LAMINATED_GLASS);
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.recipeMapWorkable.isActive(), this.recipeMapWorkable.isWorkingEnabled());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return switch (this.CasingTier) {
            case (2) -> GTQTTextures.PRECISE_ASSEMBLER_CASING_MK2;
            case (3) -> GTQTTextures.PRECISE_ASSEMBLER_CASING_MK3;
            default -> GTQTTextures.PRECISE_ASSEMBLER_CASING_MK1;
        };
    }



    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GTQTDataCode.GTQT_CHANNEL_4) {
            this.CasingTier = buf.readInt();
        }
        if (dataId == GTQTDataCode.GTQT_CHANNEL_7) {
            this.writeCustomData(GTQTDataCode.GTQT_CHANNEL_4, buf1 -> buf1.writeInt(this.CasingTier));
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.CasingTier);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.CasingTier = buf.readInt();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.machine.precise_assembler.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.precise_assembler.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.precise_assembler.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.machine.precise_assembler.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.machine.precise_assembler.tooltip.5"));
    }

    protected class PreciseAssemblerRecipeLogic extends MultiblockRecipeLogic {
        public PreciseAssemblerRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        /**
         * @return Check if machine in PA
         */
        private boolean isPrecise() {
            return this.getRecipeMap() == GTQTcoreRecipeMaps.PRECISE_ASSEMBLER_RECIPES;
        }

        /**
         * @param maxProgress If machine in common assembler, then get half progress time.
         */
        public void setMaxProgress(int maxProgress) {
            if (isPrecise()) {
                this.maxProgressTime = maxProgress ;
            } else {
                this.maxProgressTime = maxProgress / 2;
            }
        }

        /**
         * @return If machine in PA, then no parallel, if machine in common assembler, then get 2^{CasingTier + 4} (Mk1:32, Mk2:64, Mk3:128) parallel.
         */
        @Override
        public int getParallelLimit() {
            if (isPrecise()) {
                return 1;
            } else {
                return (int) Math.pow(2, CasingTier + 4);
            }
        }
    }
}