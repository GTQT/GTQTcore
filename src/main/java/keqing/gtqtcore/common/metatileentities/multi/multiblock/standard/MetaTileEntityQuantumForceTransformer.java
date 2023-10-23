package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;


import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.util.RelativeDirection.*;

public class MetaTileEntityQuantumForceTransformer extends RecipeMapMultiblockController {

    protected int heatingCoilLevel;
    protected int heatingCoilDiscount;

    public MetaTileEntityQuantumForceTransformer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.VACUUM_RECIPES);
        this.recipeMapWorkable = new MetaTileEntityQuantumForceTransformer.MetaTileEntityQuantumForceTransformerWorkable(this);
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityQuantumForceTransformer(this.metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.md.level", heatingCoilLevel));
        }
        super.addDisplayText(textList);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.2", 256));
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.1"));
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("极度冰寒", new Object[0]));
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
            this.heatingCoilDiscount = ((IHeatingCoilBlockStats) coilType).getEnergyDiscount();
        } else {
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
            this.heatingCoilDiscount = BlockWireCoil.CoilType.CUPRONICKEL.getEnergyDiscount();
        }
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.heatingCoilLevel = 0;
        this.heatingCoilDiscount = 0;
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("      A A      " ,"      A A      " ,"      A A      " ,"   A BA AB A   " ,"   ABBAAABBA   " ,"   BBBAAABBB   " ,"   BBBBABBBB   " ,"      BAB      " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               ")
                .aisle("               " ,"               " ,"               " ,"  A         A  " ,"  A         A  " ,"  B         B  " ,"  BAAAAAAAAAB  " ,"   AAABBBAAA   " ,"      BAB      " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               ")
                .aisle("               " ,"               " ,"               " ," A           A " ," A           A " ," B           B " ," BAA       AAB " ,"  AA       AA  " ,"    AA   AA    " ,"      BAB      " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               ")
                .aisle("A             A" ,"A             A" ,"A             A" ,"A             A" ,"A             A" ,"B             B" ,"BA          AAB" ," AA         AA " ,"   AA     AA   " ,"     BAAAB     " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               ")
                .aisle("      H~H      " ,"      EEE      " ,"      EEE      " ,"      EEE      " ,"B     DDD     B" ,"B     EEE     B" ,"BA    DDD    AB" ," A    EEE    A " ,"  AA  EEE  AA  " ,"    BAEEEAB    " ,"      DDD      " ,"      EEE      " ,"      EEE      " ,"      EEE      " ,"      DDD      " ,"      EEE      " ,"      DDD      " ,"      EEE      " ,"      EEE      " ,"      EEE      " ,"      TTT      ")
                .aisle("     HHHHH     " ,"     ECCCE     " ,"     ECCCE     " ,"B    ECCCE    B" ,"B    D   D    B" ,"B    ECCCE    B" ,"BA   D   D   AB" ," A   ECCCE   A " ,"  A  ECCCE  A  " ,"   BAECCCEAB   " ,"     D   D     " ,"     ECCCE     " ,"     ECCCE     " ,"     ECCCE     " ,"     D   D     " ,"     ECCCE     " ,"     D   D     " ,"     ECCCE     " ,"     ECCCE     " ,"     ECCCE     " ,"     TTTTT     ")
                .aisle("    HHHHHHH    " ,"    EC   CE    " ,"    EC   CE    " ,"A   EC   CE   A" ,"A   D     D   A" ,"A   EC   CE   A" ,"BA  D     D  AB" ,"BB  EC   CE  BB" ," B  EC   CE  B " ,"  BAEC   CEAB  " ,"    D     D    " ,"    EC   CE    " ,"    EC   CE    " ,"    EC   CE    " ,"    D     D    " ,"    EC   CE    " ,"    D     D    " ,"    EC   CE    " ,"    EC   CE    " ,"    ECCCCCE    " ,"    TTTTTTT    ")
                .aisle("    HHHHHHH    " ,"    EC   CE    " ,"    EC   CE    " ,"    EC   CE    " ,"A   D     D   A" ,"A   EC   CE   A" ,"AA  D     D  AA" ,"AB  EC   CE  BA" ," A  EC   CE  A " ,"  AAEC   CEAA  " ,"    D     D    " ,"    EC   CE    " ,"    EC   CE    " ,"    EC   CE    " ,"    D     D    " ,"    EC   CE    " ,"    D     D    " ,"    EC   CE    " ,"    EC   CE    " ,"    ECCCCCE    " ,"    TTTTTTT    ")
                .aisle("    HHHHHHH    " ,"    EC   CE    " ,"    EC   CE    " ,"A   EC   CE   A" ,"A   D     D   A" ,"A   EC   CE   A" ,"BA  D     D  AB" ,"BB  EC   CE  BB" ," B  EC   CE  B " ,"  BAEC   CEAB  " ,"    D     D    " ,"    EC   CE    " ,"    EC   CE    " ,"    EC   CE    " ,"    D     D    " ,"    EC   CE    " ,"    D     D    " ,"    EC   CE    " ,"    EC   CE    " ,"    ECCCCCE    " ,"    TTTTTTT    ")
                .aisle("     HHHHH     " ,"     ECCCE     " ,"     ECCCE     " ,"B    ECCCE    B" ,"B    D   D    B" ,"B    ECCCE    B" ,"BA   D   D   AB" ," A   ECCCE   A " ,"  A  ECCCE  A  " ,"   BAECCCEAB   " ,"     D   D     " ,"     ECCCE     " ,"     ECCCE     " ,"     ECCCE     " ,"     D   D     " ,"     ECCCE     " ,"     D   D     " ,"     ECCCE     " ,"     ECCCE     " ,"     ECCCE     " ,"     TTTTT     ")
                .aisle("      HHH      " ,"      EEE      " ,"      EEE      " ,"      EEE      " ,"B     DDD     B" ,"B     EEE     B" ,"BA    DDD    AB" ," A    EEE    A " ,"  AA  EEE  AA  " ,"    BAEEEAB    " ,"      DDD      " ,"      EEE      " ,"      EEE      " ,"      EEE      " ,"      DDD      " ,"      EEE      " ,"      DDD      " ,"      EEE      " ,"      EEE      " ,"      EEE      " ,"      TTT      ")
                .aisle("A             A" ,"A             A" ,"A             A" ,"A             A" ,"A             A" ,"B             B" ,"BA          AAB" ," AA         AA " ,"   AA     AA   " ,"     BAAAB     " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               ")
                .aisle("               " ,"               " ,"               " ," A           A " ," A           A " ," B           B " ," BAA       AAB " ,"  AA       AA  " ,"    AA   AA    " ,"      BAB      " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               ")
                .aisle("               " ,"               " ,"               " ,"  A         A  " ,"  A         A  " ,"  B         B  " ,"  BAAAAAAAAAB  " ,"   AAABBBAAA   " ,"      BAB      " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               ")
                .aisle("      A A      " ,"      A A      " ,"      A A      " ,"   A BA AB A   " ,"   ABBAAABBA   " ,"   BBBAAABBB   " ,"   BBBBABBBB   " ,"      BAB      " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               " ,"               ")
                .where('~', selfPredicate())
                .where('E', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2).setPreviewCount(1)))
                .where('A', states(getCasingState()).or(autoAbilities(true, false)))
                .where('D', states(getCasingState()))
                .where('T', states(getCasingState()))
                .where('B', states(getCasingState()))
                .where('H', states(getCasingState()))
                .where(' ', any())
                .where('C', heatingCoils())
                .build();
    }

    @Override
    protected boolean allowSameFluidFillForOutputs() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.FROST_PROOF_CASING;
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(MetalCasingType.ALUMINIUM_FROSTPROOF);
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.VACUUM_FREEZER_OVERLAY;
    }

    @Override
    public int getFluidOutputLimit() {
        return getOutputFluidInventory().getTanks();
    }

    public static int getMaxParallel(int heatingCoilLevel) {
        return 16 * heatingCoilLevel;
    }

    protected class MetaTileEntityQuantumForceTransformerWorkable extends MultiblockRecipeLogic {

        public MetaTileEntityQuantumForceTransformerWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);

        }

        @Override
        public int getParallelLimit() {
            return getMaxParallel(heatingCoilLevel);
        }
    }
}