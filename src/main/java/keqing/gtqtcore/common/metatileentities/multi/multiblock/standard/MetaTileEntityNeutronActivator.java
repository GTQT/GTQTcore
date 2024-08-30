package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.*;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.BlockInfo;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.NeutronActivatorPartProperty;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static gregtech.api.util.RelativeDirection.*;
import static keqing.gtqtcore.common.block.blocks.GTQTADVBlock.CasingType.Inconel792;

public class MetaTileEntityNeutronActivator  extends RecipeMapMultiblockController {
    private int coilHeight;
    protected int heatingCoilLevel;
    double pa;
    double pamax;
    double pamin;
    public MetaTileEntityNeutronActivator(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.NEUTRON_ACTIVATOR);
        this.recipeMapWorkable = new NeutronActivatorLogic(this);
    }
    @Override
    public boolean canBeDistinct() {return true;}
    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        if(recipe.getProperty(NeutronActivatorPartProperty.getInstance(), 0)>=pamin&&recipe.getProperty(NeutronActivatorPartProperty.getInstance(), 0)<=pamax)
        {
            return super.checkRecipe(recipe, consumeIfSuccess);
        }
        return false;
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("gtqtcore.multiblock.na.tooltips1",coilHeight,heatingCoilLevel));
        textList.add(new TextComponentTranslation("gtqtcore.multiblock.na.tooltips2",(int)pamin,(int)pa,(int)pamax));
    }
    protected class NeutronActivatorLogic extends MultiblockRecipeLogic {
        public NeutronActivatorLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity,true);
        }

        int calculateEnergy()
        {
            if(energyContainer.getEnergyStored()>energyContainer.getEnergyCapacity()*0.75)return 4;
            if(energyContainer.getEnergyStored()>energyContainer.getEnergyCapacity()*0.5)return 2;
            return 1;
        }

        int calculatePa()
        {
            if(pa>Math.pow(2,heatingCoilLevel)*0.9*coilHeight&&pa<Math.pow(2,heatingCoilLevel)*1.1*coilHeight)return 4;
            if(pa>Math.pow(2,heatingCoilLevel)*0.8*coilHeight&&pa<Math.pow(2,heatingCoilLevel)*1.2*coilHeight)return 2;
            else return 1;
        }

        @Override
        public int getParallelLimit() {
            return heatingCoilLevel*calculateEnergy()*calculatePa();
        }

        public void update() {
        }
    }
    @Override
    public void update() {
        super.update();
        pamax=(Math.pow(2,heatingCoilLevel))*1.3*coilHeight;
        pamin=(Math.pow(2,heatingCoilLevel))*0.7*coilHeight;
        pa=(((pamax-pamin)*energyContainer.getEnergyStored()/energyContainer.getEnergyCapacity())+pamin);
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityNeutronActivator(metaTileEntityId);

    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        coilHeight = context.getInt("Count");

        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
        } else {
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
        }
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("YYSYY", "YXXXY","YXXXY","YXXXY", "YYYYY")
                .aisle("F   F", " GGG "," GHG "," GGG ", "F   F").setRepeatable(5, 25)
                .aisle("YYYYY", "YXXXY","YXXXY","YXXXY", "YYYYY")
                .where('S', selfPredicate())
                .where('Y', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1)))
                .where('X', states(getCasingState1()))
                .where('G', states(getGlassState()))
                .where('F', states(getFrameState()))
                .where('H', coilPredicate())
                .build();
    }
    private TraceabilityPredicate coilPredicate() {
        return new TraceabilityPredicate((blockWorldState) -> {
            IBlockState blockState = blockWorldState.getBlockState();
            if (blockState.getBlock() instanceof BlockWireCoil) {
                BlockWireCoil blockWireCoil = (BlockWireCoil) blockState.getBlock();
                BlockWireCoil.CoilType coilType = blockWireCoil.getState(blockState);
                Object currentCoilType = blockWorldState.getMatchContext().getOrPut("CoilType", coilType);
                if (!currentCoilType.toString().equals(coilType.getName())) {
                    blockWorldState.setError(new PatternStringError("gregtech.multiblock.pattern.error.coils"));
                    return false;
                } else {
                    blockWorldState.getMatchContext().increment("Count", 1);
                    blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>()).add(blockWorldState.getPos());
                    return true;
                }
            } else {
                return false;
            }
        }, () -> Arrays.stream(BlockWireCoil.CoilType.values()).map((type) -> new BlockInfo(MetaBlocks.WIRE_COIL.getState(type), null)).toArray(BlockInfo[]::new)).addTooltips("gregtech.multiblock.pattern.error.coils");
    }
    private IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.LAMINATED_GLASS);
    }
    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(GTQTMaterials.MARM200Steel).getBlock(GTQTMaterials.MARM200Steel);
    }
    private IBlockState getCasingState() {
        return GTQTMetaBlocks.ADV_BLOCK.getState(Inconel792);
    }
    private IBlockState getCasingState1() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST);
    }
    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.Inconel792;
    }
    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.BURNER_REACTOR_OVERLAY;
    }

    public boolean hasMufflerMechanics() {
        return false;
    }
}
