package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.core;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.damagesources.DamageSources;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.core.advancement.AdvancementTriggers;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockCore;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTIsaCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static gregtech.api.GTValues.IV;
import static gregtech.api.GTValues.VA;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing.TurbineCasingType.IRIDIUM_CASING;

public class MetaTileEntityIndustrialFurnace extends GTQTMultiblockCore {

    public MetaTileEntityIndustrialFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.FURNACE_RECIPES,
                RecipeMaps.ALLOY_SMELTER_RECIPES
        });
    }
    @Override
    public int getMinVa()
    {
        return VA[IV];
    }
    @Override
    public int getCoreNum() {
        return 8;
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityIndustrialFurnace(metaTileEntityId);
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCC", "CMC", "CCC")
                .aisle("CCC", "CGC", "CCC")
                .aisle("PPP", "PSP", "PPP")
                .where('P', states(getCasingState()))
                .where('S', this.selfPredicate())
                .where('C', states(getCasingState())
                        .setMinGlobalLimited(14)
                        .or(autoAbilities()))
                .where('G', states(getSecondCasingState()))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .build();
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(IRIDIUM_CASING);
    }

    private static IBlockState getSecondCasingState() {
        return GTQTMetaBlocks.ISA_CASING.getState(GTQTIsaCasing.CasingType.IRIDIUM_TURBINE);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.IRIDIUM_CASING;
    }

    private boolean onRotorHolderInteract( EntityPlayer player) {

        if (player.isCreative()) return false;

        if (!getWorld().isRemote && this.isActive()) {
            player.attackEntityFrom(DamageSources.getTurbineDamage(), 7);
            AdvancementTriggers.ROTOR_HOLDER_DEATH.trigger((EntityPlayerMP) player);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn,
                                EnumHand hand,
                                EnumFacing facing,
                                CuboidRayTraceResult hitResult) {
        return onRotorHolderInteract(playerIn) || super.onRightClick(playerIn, hand, facing, hitResult);
    }

    @Override
    public boolean onWrenchClick(EntityPlayer playerIn,
                                 EnumHand hand,
                                 EnumFacing facing,
                                 CuboidRayTraceResult hitResult) {
        return onRotorHolderInteract(playerIn) || super.onWrenchClick(playerIn, hand, facing, hitResult);
    }

    @Override
    public boolean onScrewdriverClick(EntityPlayer playerIn,
                                      EnumHand hand,
                                      EnumFacing facing,
                                      CuboidRayTraceResult hitResult) {
        return onRotorHolderInteract(playerIn);
    }

    @Override
    public void onLeftClick(EntityPlayer player,
                            EnumFacing facing,
                            CuboidRayTraceResult hitResult) {
        onRotorHolderInteract(player);
    }

    @Override
    public void update() {
        super.update();
    }


    @SideOnly(Side.CLIENT)

    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ROASTER_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }
}

