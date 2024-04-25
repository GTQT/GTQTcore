package keqing.gtqtcore.common.metatileentities.multi.generators;


import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.MultiblockFuelRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
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
import java.util.Random;

import static gregtech.api.GTValues.*;

public class MetaTileEntitySolarPlate extends MultiblockWithDisplayBase implements  IProgressBarMultiblock {

    private IEnergyContainer energyContainer;
    public MetaTileEntitySolarPlate(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {
        isWorkingEnabled=checkNaturalLighting()&&clear();
        if(isWorkingEnabled) this.energyContainer.addEnergy(geteu());
    }
    private long geteu()
    {
        Random rand = new Random();
        int randomNum = rand.nextInt(40);
        return ((long) VA[tier + 3] * 2 *(randomNum+80)/100);
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntitySolarPlate(metaTileEntityId);
    }


    @Override
    public int getNumProgressBars() {
        return 1;
    }


    @Override
    public double getFillPercentage(int index) {
        return  (double)geteu()/(VA[tier + 3] * 2.4);
    }

    @Override
    public TextureArea getProgressBarTexture(int index) {
        return GuiTextures.PROGRESS_BAR_HPCA_COMPUTATION;
    }

    @Override
    public void addBarHoverText(List<ITextComponent> hoverList, int index) {
        ITextComponent cwutInfo = TextComponentUtil.stringWithColor(
                TextFormatting.AQUA,
                geteu()+ " / " + VA[tier + 3] * 2.4 + " EU/t");
        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "gregtech.multiblock.wps.computation",
                cwutInfo));
    }



    int tier;
    private boolean isWorkingEnabled;
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
                        .or(abilities(MultiblockAbility.OUTPUT_ENERGY).setMaxGlobalLimited(4)))
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
        textList.add(new TextComponentTranslation("gtqtcore.tire", tier-3));
    }
    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.OUTPUT_ENERGY));
        Object tier = context.get("SPTiredStats");
        this.tier = GTQTUtil.getOrDefault(() -> tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)tier).getIntTier(),
                0);
        this.writeCustomData(GTQTValue.UPDATE_TIER, buf -> buf.writeInt(this.tier));
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
    private boolean isWorkingEnabled() {
        return isWorkingEnabled;
    }
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.isActive(),
                this.isWorkingEnabled());
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

}
