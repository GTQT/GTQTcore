package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.GTValues;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.*;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.StoneVariantBlock;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.MDProperties;
import keqing.gtqtcore.api.recipes.properties.PAPartProperty;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import gregtech.api.unification.material.Materials;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;

public class MetaTileEntityMiningDrill extends RecipeMapMultiblockController {
    int tier;
    int casing;
    int tube;
    int drilla;
    int naijiu;

    public MetaTileEntityMiningDrill(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.MINING_DRILL_RECIPES );
        this.recipeMapWorkable = new IndustrialDrillWorkableHandler(this, true);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityMiningDrill(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("               ", "     DDDDD     ", "     DDDDD     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "   DDDDDDDDD   ", "   DDDDDDDDD   ", "    BB   BB    ", "    BB   BB    ", "    BB   BB    ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "  DDDDDDDDDDD  ", "  DDDDDDDDDDD  ", "    BB   BB    ", "    BB   BB    ", "    BB   BB    ", "     BB BB     ", "     BB BB     ", "     BB BB     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", " DDDDDDDDDDDDD ", " DDDDDDDDDDDDD ", "               ", "               ", "               ", "     BB BB     ", "     BB BB     ", "     BB BB     ", "     BB BB     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", " DDDDDDDDDDDDD ", " DDDDDDDDDDDDD ", " BB         BB ", " BB         BB ", " BB         BB ", "               ", "       E       ", "     BBEBB     ", "     AAEAA     ", "       E       ", "       E       ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "DDDDDD   DDDDDD", "DDDDDD   DDDDDD", " BB         BB ", " BB         BB ", " BB         BB ", "  BB       BB  ", "  BB   E   BB  ", "  BBBAAAAABBB  ", "   BAAACAAAB   ", "     GGCGG     ", "     BGEGB     ", "     BGAGB     ", "     B   B     ", "     B   B     ", "     BB  B     ", "     B B B     ", "     B  BB     ", "     B   B     ")
                .aisle("               ", "DDDDD  F  DDDDD", "DDDDD FFF DDDDD", "       A       ", "      BAB      ", "      BAB      ", "  BB  BAB  BB  ", "  BB  BAB  BB  ", "  BBBAAAAABBB  ", "   BAACCCAAB   ", "     GCCCG     ", "     GCCCG     ", "     GGCGG     ", "               ", "               ", "         B     ", "      ACA      ", "     BAAA      ", "               ")
                .aisle("       F       ", "DDDDD FFF DDDDD", "DDDDD FFF DDDDD", "      ACA      ", "      ACA      ", "      ACA      ", "      ACA      ", "    EEACAEE    ", "    EAACAAE    ", "    ECCCCCE    ", "    ECCCCCE    ", "    EECCCEE    ", "     AGGGA     ", "       C       ", "       C       ", "       C       ", "     BCCCB     ", "      AAA      ", "       A       ")
                .aisle("               ", "DDDDD  F  DDDDD", "DDDDD FFF DDDDD", "       A       ", "      BSB      ", "      BAB      ", "  BB  BAB  BB  ", "  BB  BAB  BB  ", "  BBBAAAAABBB  ", "   BAACCCAAB   ", "     GCCCG     ", "     GCCCG     ", "     GGCGG     ", "               ", "               ", "     B         ", "      ACA      ", "      AAAB     ", "               ")
                .aisle("               ", "DDDDDD   DDDDDD", "DDDDDD   DDDDDD", " BB         BB ", " BB         BB ", " BB         BB ", "  BB       BB  ", "  BB   E   BB  ", "  BBBAAAAABBB  ", "   BAAACAAAB   ", "     GGCGG     ", "     BGEGB     ", "     BGAGB     ", "     B   B     ", "     B   B     ", "     B  BB     ", "     B B B     ", "     BB  B     ", "     B   B     ")
                .aisle("               ", " DDDDDDDDDDDDD ", " DDDDDDDDDDDDD ", " BB         BB ", " BB         BB ", " BB         BB ", "               ", "       E       ", "     BBEBB     ", "     AAEAA     ", "       E       ", "       E       ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", " DDDDDDDDDDDDD ", " DDDDDDDDDDDDD ", "               ", "               ", "               ", "     BB BB     ", "     BB BB     ", "     BB BB     ", "     BB BB     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "  DDDDDDDDDDD  ", "  DDDDDDDDDDD  ", "    BB   BB    ", "    BB   BB    ", "    BB   BB    ", "     BB BB     ", "     BB BB     ", "     BB BB     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "   DDDDDDDDD   ", "   DDDDDDDDD   ", "    BB   BB    ", "    BB   BB    ", "    BB   BB    ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "     DDDDD     ", "     DDDDD     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .where('S', selfPredicate())
                .where('A', TiredTraceabilityPredicate.CP_CASING)
                .where('B', states(MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel)).setMinGlobalLimited(270)
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2)))
                .where('C', states(MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STEEL_GEARBOX)))
                .where('D', states(MetaBlocks.STONE_BLOCKS.get(StoneVariantBlock.StoneVariant.SMOOTH).getState(StoneVariantBlock.StoneType.CONCRETE_LIGHT)))
                .where('E', TiredTraceabilityPredicate.CP_TUBE)
                .where('F', TiredTraceabilityPredicate.CP_DRI_CASING)
                .where('G', states(MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING)))
                .where(' ', any())
                .build();
    }


    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if(casing!=tube)
            textList.add(new TextComponentTranslation("gtqtcore.equal", casing,tube));
        textList.add(new TextComponentTranslation("gtqtcore.md",tier, drilla, naijiu));

    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        switch (this.casing) {
            case (2) -> {
                return Textures.SOLID_STEEL_CASING;
            }
            case (3) -> {
                return Textures.FROST_PROOF_CASING;
            }
            case (4) -> {
                return Textures.CLEAN_STAINLESS_STEEL_CASING;
            }
            case (5) -> {
                return Textures.STABLE_TITANIUM_CASING;
            }
            case (6) -> {
                return Textures.ROBUST_TUNGSTENSTEEL_CASING;
            }
            case (7) -> {
                return GTQTTextures.PD_CASING;
            }
            case (8) -> {
                return GTQTTextures.NQ_CASING;
            }
            case (9) -> {
                return GTQTTextures.ST_CASING;
            }
            case (10) -> {
                return GTQTTextures.AD_CASING;
            }
            default -> {
                return Textures.BRONZE_PLATED_BRICKS;
            }
        }
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }


    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        //this.inputInventory = new NotifiableItemStackHandler(this,1, this, false);
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == GTQTValue.UPDATE_TIER){
            this.casing = buf.readInt();
        }
        if(dataId == GTQTValue.REQUIRE_DATA_UPDATE){
            this.writeCustomData(GTQTValue.UPDATE_TIER,buf1 -> buf1.writeInt(this.casing));
        }
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object drill = context.get("DriTiredStats");
        Object casing = context.get("ChemicalPlantCasingTiredStats");
        Object tubeTier = context.get("ChemicalPlantTubeTiredStats");
        this.casing = GTQTUtil.getOrDefault(() -> casing instanceof WrappedIntTired,
                () -> ((WrappedIntTired)casing).getIntTier(),
                0);

        this.drilla = GTQTUtil.getOrDefault(() -> drill instanceof WrappedIntTired,
                () -> ((WrappedIntTired)drill).getIntTier(),
                0);

        this.tube = GTQTUtil.getOrDefault(() -> tubeTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)tubeTier).getIntTier(),
                0);


        this.tier = Math.min(this.casing,this.tube);

        this.writeCustomData(GTQTValue.UPDATE_TIER, buf -> buf.writeInt(this.tier));
        naijiu=drilla*15*7500;
    }

   @Override
  public void invalidateStructure() {
       super.invalidateStructure();
}

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        int number=recipe.getProperty(MDProperties.getInstance(), 0);
        if(number<=drilla)
            return super.checkRecipe(recipe, consumeIfSuccess);
        else return false;
   }


    @Override
    public boolean canBeDistinct() {
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("无中生有", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.mdi.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.mdi.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.mdi.tooltip.3"));
    }
    protected class IndustrialDrillWorkableHandler extends MultiblockRecipeLogic {

        public IndustrialDrillWorkableHandler(RecipeMapMultiblockController tileEntity, boolean hasPerfectOC) {
            super(tileEntity, hasPerfectOC);
        }

        @Override
        public MetaTileEntityMiningDrill getMetaTileEntity() {
            return (MetaTileEntityMiningDrill) super.getMetaTileEntity();
        }

        @Override
        public void update() {
            final int xDir = this.metaTileEntity.getFrontFacing().getOpposite().getXOffset();
            final int zDir = this.metaTileEntity.getFrontFacing().getOpposite().getZOffset();

            if(naijiu<-1) {
                for (int h = -4; h <-1; h++) for (int i=-1;i<=1;i++) for (int j=-1;j<=1;j++)
                {
                    BlockPos waterCheckPos = this.metaTileEntity.getPos().add(xDir+i , h, zDir+j );
                    this.metaTileEntity.getWorld().setBlockState(
                            waterCheckPos,
                            Blocks.AIR.getDefaultState());
                }
                naijiu=0;
            }


        }
        public long getMaxVoltage() {
            return Math.min(super.getMaxVoltage(), VA[tier]);
        }

        @Override
        public int getParallelLimit() {
            return (int) Math.pow(2, tier);
        }

        protected void updateRecipeProgress() {
            if (canRecipeProgress) {
                    naijiu--;
                    if (++progressTime > maxProgressTime) {
                        completeRecipe();
                    }

            }
        }
    }
}