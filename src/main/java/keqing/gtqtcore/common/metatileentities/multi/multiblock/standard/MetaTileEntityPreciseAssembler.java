package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.capability.impl.ComputationRecipeLogic;
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
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.ComputationProperty;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.PAProperty;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.Steam;
import static keqing.gtqtcore.api.GTQTAPI.MAP_PA_CASING;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.PRECISE_ASSEMBLER;

public class MetaTileEntityPreciseAssembler extends MultiMapMultiblockController implements IOpticalComputationReceiver {
    private IOpticalComputationProvider computationProvider;
    private int CasingTier;
    private int InternalCasingTier;

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);

        textList.add(new TextComponentTranslation("外壳等级：%s 机器方块等级:%s", CasingTier,InternalCasingTier));
        textList.add(new TextComponentTranslation("配方运行等级:%s", Math.min(InternalCasingTier,CasingTier)));
    }

    public MetaTileEntityPreciseAssembler(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                ASSEMBLER_RECIPES,
                GTQTcoreRecipeMaps.PRECISE_ASSEMBLER_RECIPES
        });
        this.recipeMapWorkable = new PreciseAssemblerRecipeLogic(this);
    }
    @Override
    public boolean canBeDistinct() {return true;}
    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPreciseAssembler(metaTileEntityId);
    }


    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        List<IOpticalComputationHatch> providers = this.getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
        if (providers != null && providers.size() >= 1) {
            this.computationProvider = (IOpticalComputationProvider)providers.get(0);
        }
        Object CasingTier = context.get("PACTieredStats");
        Object InternalCasingTier = context.get("PAITieredStats");
        this.CasingTier = GTQTUtil.getOrDefault(() -> CasingTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)CasingTier).getIntTier(),
                0);
        this.InternalCasingTier = GTQTUtil.getOrDefault(() -> InternalCasingTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)InternalCasingTier).getIntTier(),
                0);

        this.writeCustomData(GTQTValue.UPDATE_TIER9, buf -> buf.writeInt(this.CasingTier));
    }
    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == GTQTValue.UPDATE_TIER9){
            this.CasingTier = buf.readInt();
        }
        if(dataId == GTQTValue.REQUIRE_DATA_UPDATE9){
            this.writeCustomData(GTQTValue.UPDATE_TIER9,buf1 -> buf1.writeInt(this.CasingTier));
        }
    }
    @Override
    public void update() {
        super.update();
    }

    public boolean checkRecipe(@Nonnull Recipe recipe,
                               boolean consumeIfSuccess) {

        if(this.getRecipeMap() == ASSEMBLER_RECIPES)return super.checkRecipe(recipe, consumeIfSuccess);
        return super.checkRecipe(recipe, consumeIfSuccess) && recipe.getProperty(PAProperty.getInstance(), 0) <= CasingTier;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = null;

            builder = MultiblockShapeInfo.builder()
                    .aisle("ETCCCCCCC", "F       F", "F       F", "F       F", "XYZCCCCCC")
                    .aisle("CMMMMMMMC", "CGGGGGGGC", "CGGGGGGGC", "CGGGGGGGC", "CCCCCCCCC")
                    .aisle("CMMMMMMMC", "C       C", "C       C", "C       C", "CCCCOCCCC")
                    .aisle("CMMMMMMMC", "CGGGGGGGC", "CGGGGGGGC", "CGGGGGGGC", "CCCCCCCCC")
                    .aisle("CCCISCCCC", "F       F", "F       F", "F       F", "CCCCCCCCC")
                    .where('S', PRECISE_ASSEMBLER, EnumFacing.SOUTH)
                    .where('I', MetaTileEntities.COMPUTATION_HATCH_RECEIVER, EnumFacing.SOUTH)
                    .where('X', MetaTileEntities.ITEM_IMPORT_BUS[IV], EnumFacing.NORTH)
                    .where('Y', MetaTileEntities.ITEM_EXPORT_BUS[IV], EnumFacing.NORTH)
                    .where('Z', MetaTileEntities.FLUID_IMPORT_HATCH[IV], EnumFacing.NORTH)
                    .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[IV], EnumFacing.NORTH)
                    .where('T', () -> ConfigHolder.machines.enableMaintenance ? MetaTileEntities.MAINTENANCE_HATCH : MetaTileEntities.ENERGY_INPUT_HATCH[LuV], EnumFacing.NORTH)
                    .where('O', MetaTileEntities.MUFFLER_HATCH[LV], EnumFacing.UP)
                    .where('G', getGlassState())
                    .where('M', MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.LuV))
                    .where('F', getFrameState())
                    .where(' ', Blocks.AIR.getDefaultState());
        MultiblockShapeInfo.Builder finalBuilder = builder;
        MAP_PA_CASING.entrySet().stream()
                    .sorted(Comparator.comparingInt(entry -> ((WrappedIntTired) entry.getValue()).getIntTier()))
                    .forEach(entry -> shapeInfo.add(finalBuilder.where('C', entry.getKey()).build()));
        return shapeInfo;
    }
    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("DDDDDDDDD", "F       F", "F       F", "F       F", "DDDDDDDDD")
                .aisle("CMMMMMMMC", "CGGGGGGGC", "CGGGGGGGC", "CGGGGGGGC", "DDDDDDDDD")
                .aisle("CMMMMMMMC", "C       C", "C       C", "C       C", "DDDDODDDD")
                .aisle("CMMMMMMMC", "CGGGGGGGC", "CGGGGGGGC", "CGGGGGGGC", "DDDDDDDDD")
                .aisle("DDDXSDDDD", "F       F", "F       F", "F       F", "DDDDDDDDD")
                .where('S', selfPredicate())
                .where('C', TiredTraceabilityPredicate.CP_PA_CASING.get())
                .where('D', TiredTraceabilityPredicate.CP_PA_CASING.get()
                        .setMinGlobalLimited(42)
                        .or(autoAbilities(true, true, true, true, true, true, false)))
                .where('X', abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION))
                .where('O', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('F', states(getFrameState()))
                .where('G', states(getGlassState()))
                .where('M', TiredTraceabilityPredicate.CP_PA_INTERNAL_CASING.get())
                .build();
    }

    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(GTQTMaterials.MARM200Steel).getBlock(GTQTMaterials.MARM200Steel);
    }

    private static IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.LAMINATED_GLASS);
    }



    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }


    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        switch (this.CasingTier) {
            case (2) -> {
                return GTQTTextures.PRECISE_ASSEMBLER_CASING_MK2;
            }
            case (3) -> {
                return GTQTTextures.PRECISE_ASSEMBLER_CASING_MK3;
            }
            case (4) -> {
                return GTQTTextures.PRECISE_ASSEMBLER_CASING_MK4;
            }
            case (5) -> {
                return GTQTTextures.PRECISE_ASSEMBLER_CASING_MK5;
            }
            case (6) -> {
                return GTQTTextures.PRECISE_ASSEMBLER_CASING_MK6;
            }
            default -> {
                return GTQTTextures.PRECISE_ASSEMBLER_CASING_MK1;
            }
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("CasingTier", CasingTier);
        return super.writeToNBT(data);
    }
   
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        CasingTier = data.getInteger("CasingTier");
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

    protected class PreciseAssemblerRecipeLogic extends  ComputationRecipeLogic {
        public PreciseAssemblerRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity,ComputationType.SPORADIC);
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
                this.maxProgressTime = maxProgress / Math.min(InternalCasingTier,CasingTier);
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
                return (int) Math.pow(2, Math.min(InternalCasingTier,CasingTier) + 4);
            }
        }
    }
}