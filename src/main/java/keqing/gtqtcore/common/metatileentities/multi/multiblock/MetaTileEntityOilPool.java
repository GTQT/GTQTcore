package keqing.gtqtcore.common.metatileentities.multi.multiblock;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.particle.VanillaParticleEffects;
import gregtech.client.renderer.CubeRendererState;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.cclop.ColourOperation;
import gregtech.client.renderer.cclop.LightMapOperation;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.BloomEffectUtil;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.capability.impl.NoEnergyMultiblockRecipeLogic;
import keqing.gtqtcore.api.metaileentity.multiblock.NoEnergyMultiblockController;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing1;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;
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
import java.util.List;

import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Demulsifier;
//预沉降池
public class MetaTileEntityOilPool extends NoEnergyMultiblockController {

    float poruji; //破乳剂
    float amount;  //水量
    float rate=0;   //破乳度
    double sperate;//盐度
    //需要加水将盐度下降到10%，根据加水量添加破乳剂，反应片刻后破乳度达到80%排放
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setDouble("sperate", sperate);
        data.setDouble("poruji", poruji);
        data.setDouble("amount", amount);
        return super.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        sperate = data.getInteger("sperate");
        poruji = data.getInteger("poruji");
        amount = data.getInteger("amount");
        super.readFromNBT(data);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeDouble(sperate);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        sperate = buf.readDouble();
    }


    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 18, 18, "", this::clear)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("我不要了！"));

        return group;
    }

    private void clear(Widget.ClickData clickData) {
        amount=0;
        rate=0;
    }
    protected class AKLogic extends NoEnergyMultiblockRecipeLogic {

        FluidStack WATER = Water.getFluid(20);

        FluidStack PORUJI = Demulsifier.getFluid(1);
        @Override
        public void update() {
            super.update();
            IMultipleTankHandler inputTank = getInputFluidInventory();
            if (WATER.isFluidStackIdentical(inputTank.drain(WATER, false))) {
                if(amount<10000) {
                    inputTank.drain(WATER, true);
                    amount = amount + 20;
                }
            }
            if (poruji<10000&&PORUJI.isFluidStackIdentical(inputTank.drain(PORUJI, false))) {
                inputTank.drain(PORUJI, true);
                poruji = poruji + 20;
            }

        }
        public void setMaxProgress(int maxProgress) {
        }
        protected void updateRecipeProgress() {
            sperate=80;
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);
                sperate=(1600/(2000+amount))*100;
                if(sperate<=20&&poruji>1&&rate<1000) {
                    rate=rate+3*(6000/amount);
                    poruji=poruji-1;
                    if(rate>=800)
                    {
                        if (++progressTime > maxProgressTime)
                        {
                            completeRecipe();
                            rate=0;
                            if(amount>2000) amount=amount-2000;
                            else amount=0;
                        }
                    }
                }
            }
        }
        public AKLogic(NoEnergyMultiblockController tileEntity) {
            super(tileEntity, tileEntity.recipeMap);
        }
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(),
                recipeMapWorkable.isActive(), recipeMapWorkable.isWorkingEnabled());
        if (recipeMapWorkable.isActive() && isStructureFormed()) {
            EnumFacing back = getFrontFacing().getOpposite();
            for(float i=-1;i<=1;i++) {
                for (float j = -1; j <=1; j++) {
                    Matrix4 offset = translation.copy().translate(back.getXOffset() * 2+i, -0.3, back.getZOffset() * 2+j);
                    CubeRendererState op = Textures.RENDER_STATE.get();
                    Textures.RENDER_STATE.set(new CubeRendererState(op.layer, CubeRendererState.PASS_MASK, op.world));
                    Textures.renderFace(renderState, offset,
                            ArrayUtils.addAll(pipeline, new LightMapOperation(240, 240), new ColourOperation(0xFFFFFFFF)),
                            EnumFacing.UP, Cuboid6.full, TextureUtils.getBlockTexture("water_still"),
                            BloomEffectUtil.getEffectiveBloomLayer());
                    Textures.RENDER_STATE.set(op);
                }
            }
        }
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.op.sperate",sperate));
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.op.rate", rate));
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.op.amount",amount ));
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.op.poruji", poruji));
            if (getInputFluidInventory() != null) {
                FluidStack STACK = getInputFluidInventory().drain(Water.getFluid(Integer.MAX_VALUE), false);
                int liquidOxygenAmount = STACK == null ? 0 : STACK.amount;
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.op.amount1", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
            }
            if (getInputFluidInventory() != null) {
                FluidStack STACK = getInputFluidInventory().drain(Demulsifier.getFluid(Integer.MAX_VALUE), false);
                int liquidOxygenAmount = STACK == null ? 0 : STACK.amount;
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.op.amount2", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
            }

        }
    }

    public MetaTileEntityOilPool(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.OIL_POOL);
        this.recipeMapWorkable = new MetaTileEntityOilPool.AKLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityOilPool(metaTileEntityId);
    }
    @Override
    public void update() {
        super.update();

        if (this.isActive()) {
            if (getWorld().isRemote) {
                VanillaParticleEffects.PBF_SMOKE.runEffect(this);
            } else {
                damageEntitiesAndBreakSnow();
            }
        }
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

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick() {
        if (this.isActive()) {
            VanillaParticleEffects.defaultFrontEffect(this, 0.3F, EnumParticleTypes.SMOKE_LARGE,
                    EnumParticleTypes.FLAME);
            if (ConfigHolder.machines.machineSounds && GTValues.RNG.nextDouble() < 0.1) {
                BlockPos pos = getPos();
                getWorld().playSound(pos.getX()*2 + 0.5F, pos.getY()*2 + 0.5F, pos.getZ() + 0.5F,
                        SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }
        }
    }

    private static final TraceabilityPredicate SNOW_PREDICATE = new TraceabilityPredicate(
            bws -> GTUtility.isBlockSnow(bws.getBlockState()));
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("F###F","XXXXX", "XXXXX")
                .aisle("###XU","XXXXX", "I###X")
                .aisle("#XXXU","XXXXX", "I#&#X")
                .aisle("###XU","XXXXX", "I###X")
                .aisle("F###F","XXXXX", "XXYXX")
                .where('F', states(getFrameState()))
                .where('X', states(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.GALVANIZE_STEEL_CASING))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS)))
                .where('U', states(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.GALVANIZE_STEEL_CASING))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1)))
                .where('I', states(GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.GALVANIZE_STEEL_CASING))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1)))
                .where('#', air())
                // running
                .where('&', air().or(SNOW_PREDICATE)) // this won't stay in the structure, and will be broken while
                .where('Y', selfPredicate())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.GALVANIZE_STEEL_CASING;
    }
    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Steel).getBlock(Steel);
    }
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("沉淀，那场大雨毁了我的大学梦", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.opi.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.opi.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.opi.tooltip.3"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.PRIMITIVE_BLAST_FURNACE_OVERLAY;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }


}