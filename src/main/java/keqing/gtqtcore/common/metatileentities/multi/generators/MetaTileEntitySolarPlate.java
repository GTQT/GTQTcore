package keqing.gtqtcore.common.metatileentities.multi.generators;


import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.MultiblockFuelRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.FuelMultiblockController;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTElectrobath;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import java.util.List;

import static gregtech.api.GTValues.*;

public class MetaTileEntitySolarPlate extends FuelMultiblockController {

    public MetaTileEntitySolarPlate(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.SOLAR_PLATE, HV);
        this.recipeMapWorkable = new SolarPlateWorkableHandler(this);
        this.recipeMapWorkable.setMaximumOverclockVoltage(V[HV]);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntitySolarPlate(metaTileEntityId);
    }
    int tier;



    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCCC")
                .aisle("CSSSSC")
                .aisle("CSSSSC")
                .aisle("CSSSSC")
                .aisle("CSSSSC")
                .aisle("XCCCCC")
                .where('X', selfPredicate())
                .where('S', TiredTraceabilityPredicate.CP_SP_CASING)
                .where('C', states(GTQTMetaBlocks.ELECTROBATH.getState(GTQTElectrobath.CasingType.SOLAR_PLATE_CASING))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.OUTPUT_ENERGY).stream()
                                .filter(mte -> {
                                    IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null);
                                    return     container.getOutputVoltage() == GTValues.V[LV]
                                            || container.getOutputVoltage() == GTValues.V[MV]
                                            || container.getOutputVoltage() == GTValues.V[HV];

                                })
                                .toArray(MetaTileEntity[]::new)).setExactLimit(1).setPreviewCount(1)))
                .build();
    }
    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (!checkNaturalLighting()) {
            textList.add(new TextComponentTranslation("现在是晚上！"));
        }
        if (!clear()) {
            textList.add(new TextComponentTranslation("被阻挡！"));
        }
    }


    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("古埃及掌管太阳的神", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.spa.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.spa.tooltip.2"));
    }
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("gtqtcore.tire", tier));
    }
    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object tier = context.get("SPTiredStats");
        this.tier = GTQTUtil.getOrDefault(() -> tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)tier).getIntTier(),
                0);
        this.writeCustomData(GTQTValue.UPDATE_TIER, buf -> buf.writeInt(this.tier));
    }
    public long getMaxVoltage() {
        return  VA[tier];
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.SOLAR_PLATE_CASING;
    }

    public boolean checkNaturalLighting() {

        if (!this.getWorld().isDaytime())
            return false;
        for (BlockPos pos : BlockPos.getAllInBox(this.getPos().up(8).offset(this.frontFacing.rotateY(), 3),
                this.getPos().up(8).offset(this.getFrontFacing().rotateYCCW(), 3).offset(this.getFrontFacing().getOpposite(), 6))) {
            if (!this.getWorld().canSeeSky(pos.up())) {
                return false;
            }
        }
        return true;
    }
    public boolean clear()
    {
        int aX = this.getPos().getX();
        int aY = this.getPos().getY();
        int aZ = this.getPos().getZ();
        for(int i=1;i<10;i++)
            for(int x=-5;x<=5;x++)
                for(int y=-5;y<=5;y++)
            if (this.getWorld().getBlockState(new BlockPos(aX+x, aY+i, aZ+y)).getBlock() != Blocks.AIR) return false;
        return true;
    }

    public  class  SolarPlateWorkableHandler extends MultiblockFuelRecipeLogic {



        public SolarPlateWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public long getMaxVoltage() {
                return VA[tier];
        }
        @Override
        protected void updateRecipeProgress() {
            if (canRecipeProgress) {
                if(checkNaturalLighting()&&clear()) {
                    drawEnergy(recipeEUt, false);
                    if (++progressTime > maxProgressTime) {
                        completeRecipe();
                    }
                }
            }
        }

    }
}
