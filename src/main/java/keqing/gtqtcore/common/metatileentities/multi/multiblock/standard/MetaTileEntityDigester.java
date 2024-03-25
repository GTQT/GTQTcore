package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.CubeRendererState;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.cclop.ColourOperation;
import gregtech.client.renderer.cclop.LightMapOperation;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.BloomEffectUtil;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.*;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTRecipeMapMultiblockOverwrite;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.Lubricant;

public class MetaTileEntityDigester extends GTQTRecipeMapMultiblockOverwrite {
    private int coilTier;
    protected int heatingCoilLevel;
    protected int heatingCoilDiscount;
    public MetaTileEntityDigester(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.DIGESTER_RECIPES);
        this.recipeMapWorkable = new MetaTileEntityDigesterWorkable(this);
    }
     int ParallelNum;
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("modern", modern);
        return super.writeToNBT(data);
    }
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        modern = data.getInteger("modern");
    }

    @Override
    public void update() {
        super.update();
        if (modern == 0)
        {
            ParallelNum=ParallelNumA;
        }
        if (modern == 1)
        {
            P = (int) ((this.energyContainer.getEnergyStored() + energyContainer.getInputPerSec()) / getMinVa());
            ParallelNum = Math.min(P, ParallelLim);
        }
        if (this.isActive()) {
            if (getWorld().isRemote) {
                pollutionParticles();
            } else {
                damageEntitiesAndBreakSnow();
            }
        }
    }
    public int getMinVa()
    {
        if((Math.min(this.energyContainer.getEnergyCapacity()/32,VA[coilTier])*20)==0)return 1;
        return (int)(Math.min(this.energyContainer.getEnergyCapacity()/32,VA[coilTier]));

    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityDigester(metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            if(modern==0) textList.add(new TextComponentTranslation("gtqtcore.tire1",heatingCoilLevel));
            if(modern==1) textList.add(new TextComponentTranslation("gtqtcore.tire2",heatingCoilLevel));
            textList.add(new TextComponentTranslation("gtqtcore.parr",ParallelNum,ParallelLim));
            textList.add(new TextComponentTranslation("gregtech.multiblock.cracking_unit.energy", 100 - this.coilTier));
        }
        super.addDisplayText(textList);
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.multiblock.hb.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.2",24));
        tooltip.add(I18n.format("gregtech.machine.cracker.gtqtupdate.1"));
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("这是什么，塞进去煮煮", new Object[0]));
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" CCCCC ", " LOOOL ", "  CCC  ", "       ")
                .aisle("CCCCCCC", "LOAAAOL", " HAAAH ", " HLLLH ")
                .aisle("CCCCCCC", "OAAAAAO", "CAAAAAC", " LAAAL ")
                .aisle("CCCCCCC", "OAAAAAO", "CAAAAAC", " LAAAL ")
                .aisle("CCCCCCC", "OAAAAAO", "CAAAAAC", " LAAAL ")
                .aisle("CCCCCCC", "LOAAAOL", " HAAAH ", " HLLLH ")
                .aisle(" CCSCC ", " LOOOL ", "  CCC  ", "       ")
                .where('S', selfPredicate())
                .where('C', states(getCasingAState()).setMinGlobalLimited(52).or(autoAbilities()))
                .where('H', states(getHeatState()))
                .where('O', heatingCoils())
                .where('L', states(getCasingBState()))
                .where('A', air())
                .where(' ', any())
                .build();
    }
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(),
                recipeMapWorkable.isActive(), recipeMapWorkable.isWorkingEnabled());
        if (recipeMapWorkable.isActive() && isStructureFormed()) {
            EnumFacing back = getFrontFacing().getOpposite();
            for(float i=-2;i<=2;i++) {
                for (float j = -2; j <=2; j++) {
                    Matrix4 offset = translation.copy().translate(back.getXOffset() * 3+i, 0.6, back.getZOffset() * 3+j);
                    CubeRendererState op = Textures.RENDER_STATE.get();
                    Textures.RENDER_STATE.set(new CubeRendererState(op.layer, CubeRendererState.PASS_MASK, op.world));
                    Textures.renderFace(renderState, offset,
                            ArrayUtils.addAll(pipeline, new LightMapOperation(240, 240), new ColourOperation(0xFF00FFFF)),
                            EnumFacing.UP, Cuboid6.full, TextureUtils.getBlockTexture("water_still"),
                            BloomEffectUtil.getEffectiveBloomLayer());
                    Textures.RENDER_STATE.set(op);
                }
            }
        }
    }
    private void pollutionParticles() {
        BlockPos pos = this.getPos();
        EnumFacing facing = this.getFrontFacing().getOpposite();
        float xPos = facing.getXOffset() *3 + pos.getX() + 0.5F;
        float yPos = facing.getYOffset()  + pos.getY() + 0.25F;
        float zPos = facing.getZOffset() *3 + pos.getZ() + 0.5F;

        float ySpd = facing.getYOffset() * 0.7F + 0.7F + 0.8F * GTValues.RNG.nextFloat();

        arunMufflerEffect(xPos, yPos, zPos, 0, ySpd, 0);
        arunMufflerEffect(xPos, yPos, zPos, 1F, ySpd, 1F);
        arunMufflerEffect(xPos, yPos, zPos, -1F, ySpd, -1F);
        arunMufflerEffect(xPos, yPos, zPos, +1F, ySpd, -1F);
        arunMufflerEffect(xPos, yPos, zPos, -1F, ySpd, +1F);
    }
    public void arunMufflerEffect(float xPos, float yPos, float zPos, float xSpd, float ySpd, float zSpd) {
        this.getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double)xPos, (double)yPos, (double)zPos, (double)xSpd, (double)ySpd, (double)zSpd, new int[0]);
    }
    private void damageEntitiesAndBreakSnow() {
        BlockPos middlePos = this.getPos();
        middlePos = middlePos.offset(getFrontFacing().getOpposite());
        this.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(middlePos))
                .forEach(entity -> entity.attackEntityFrom(DamageSource.LAVA, 3.0f));

        if (getOffsetTimer() % 10 == 0) {
            IBlockState state = getWorld().getBlockState(middlePos);
            GTUtility.tryBreakSnow(getWorld(), middlePos, state, true);
        }
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.coilTier = ((IHeatingCoilBlockStats)type).getTier();
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
            this.heatingCoilDiscount = ((IHeatingCoilBlockStats) coilType).getEnergyDiscount();
        } else {
            this.coilTier = 0;
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
            this.heatingCoilDiscount = BlockWireCoil.CoilType.CUPRONICKEL.getEnergyDiscount();
        }

        ParallelLim=(int)Math.pow(2, coilTier);
        ParallelNum=ParallelLim;
    }
    private static IBlockState getCasingAState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST);
    }
    private static IBlockState getCasingBState() {
        return MetaBlocks.BOILER_FIREBOX_CASING.getState(BlockFireboxCasing.FireboxCasingType.TUNGSTENSTEEL_FIREBOX);
    }
    private static IBlockState getHeatState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE);
    }

    public static int getMaxParallel(int heatingCoilLevel) {
        return   heatingCoilLevel;
    }
    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.coilTier = -1;
        heatingCoilLevel = 0;
        heatingCoilDiscount = 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.ROBUST_TUNGSTENSTEEL_CASING;
    }
    protected int getCoilTier() {
        return this.coilTier;
    }

    protected class MetaTileEntityDigesterWorkable extends MultiblockRecipeLogic {


        public MetaTileEntityDigesterWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public int getParallelLimit() {
            return ParallelNum;
        }
        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = maxProgress*(100-heatingCoilLevel)/100;
        }

        protected void modifyOverclockPost(int[] resultOverclock, @Nonnull IRecipePropertyStorage storage) {
            super.modifyOverclockPost(resultOverclock, storage);
            int coilTier = ((MetaTileEntityDigester)this.metaTileEntity).getCoilTier();
            if (coilTier > 0) {
                resultOverclock[0] = (int)((double)resultOverclock[0] * (100.0 - (double)coilTier)/100);
                resultOverclock[0] = Math.max(1, resultOverclock[0]);
            }
        }
    }
}
