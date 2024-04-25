package keqing.gtqtcore.common.metatileentities.multi.multiblock;


import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.util.GTUtility;
import gregtech.client.particle.VanillaParticleEffects;
import gregtech.client.renderer.CubeRendererState;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.cclop.ColourOperation;
import gregtech.client.renderer.cclop.LightMapOperation;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.BloomEffectUtil;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockSteamCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.capability.impl.NoEnergyMultiblockRecipeLogic;
import keqing.gtqtcore.api.metaileentity.multiblock.NoEnergyMultiblockController;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.overwrite.MetaTileEntityCrackingUnit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class MetaTileEntityBReactor extends NoEnergyMultiblockController {
    private static final TraceabilityPredicate SNOW_PREDICATE = new TraceabilityPredicate(
            bws -> GTUtility.isBlockSnow(bws.getBlockState()));

    public MetaTileEntityBReactor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.PR_MIX);
        this.recipeMapWorkable = new BRLoigc(this);
    }

    private class BRLoigc extends NoEnergyMultiblockRecipeLogic {
        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = maxProgress/4;
        }
        public BRLoigc(NoEnergyMultiblockController tileEntity) {
            super(tileEntity, tileEntity.recipeMap);
        }
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityBReactor(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXXXX", "XXXXXXX", "XXXXXXX")
                .aisle("XXXXXXX", "X&&&&&X", "X#####X")
                .aisle("XXXXXXX", "X&&&&&X", "X#####X")
                .aisle("XXXXXXX", "X&&&&&X", "X#####X")
                .aisle("XXXXXXX", "X&&&&&X", "X#####X")
                .aisle("XXXXXXX", "X&&&&&X", "X#####X")
                .aisle("XXXXXXX", "XXXYXXX", "XXXXXXX")
                .where('X', states(getCasingBState())
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(3)))
                .where('#', air())
                .where('&', air().or(SNOW_PREDICATE)) // this won't stay in the structure, and will be broken while
                // running
                .where('Y', selfPredicate())
                .build();
    }
    private static IBlockState getCasingBState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
    }
    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.CLEAN_STAINLESS_STEEL_CASING;
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("司马光砸不动", new Object[0]));
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
    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.EXTRACTOR_OVERLAY;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public void update() {
        super.update();

        if (this.isActive()) {
            if (getWorld().isRemote) {
                pollutionParticles();
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

}