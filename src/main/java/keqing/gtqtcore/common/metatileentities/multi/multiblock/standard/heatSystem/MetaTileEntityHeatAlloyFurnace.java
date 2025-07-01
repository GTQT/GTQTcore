package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.heatSystem;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockFireboxCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.capability.IHeat;
import keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.metatileentity.multiblock.RecipeMapHeatMultiblockController;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class MetaTileEntityHeatAlloyFurnace extends RecipeMapHeatMultiblockController {

    public MetaTileEntityHeatAlloyFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.ALLOY_SMELTER_RECIPES);
        this.recipeMapWorkable.setParallelLimit(32);
    }

    private static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("并非炼丹炉", new Object[0]));
        tooltip.add(I18n.format("拥有32并行，熔炉配方默认最低温度500k"));
        tooltip.add(I18n.format("本设备为 热学 设备，在使用时需要 热源仓 而非 能源仓"));
        tooltip.add(I18n.format("关于 热源仓 使用详情查询 热源仓 本身的Tooltips"));
        tooltip.add(I18n.format("热学能源Heat如同电学能源EU一样拥有超频逻辑，更高的温度会带来更快的加工速度"));
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityHeatAlloyFurnace(metaTileEntityId);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        setTier(getHeatHatch().getTier());
    }

    @Override
    public void updateFormedValid() {
        super.updateFormedValid();
        setHeat((int) getHeatHatch().getHeat());
        setTier(getHeatHatch().getTier());
    }

    public IHeat getHeatHatch() {
        List<IHeat> abilities = getAbilities(GTQTMultiblockAbility.HEAT_MULTIBLOCK_ABILITY);
        if (abilities.isEmpty())
            return null;
        return abilities.get(0);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("BMB", "CCC", " C ")
                .aisle("BCB", "C#C", " C ")
                .aisle("BBB", "CSC", " C ")
                .where('S', selfPredicate())
                .where('B', states(getBoilderState()))
                .where('C', states(getCasingState()).setMinGlobalLimited(14)
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(GTQTMultiblockAbility.HEAT_MULTIBLOCK_ABILITY).setExactLimit(1))
                )
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('#', any())
                .build();
    }

    private IBlockState getBoilderState() {
        return MetaBlocks.BOILER_FIREBOX_CASING.getState(BlockFireboxCasing.FireboxCasingType.STEEL_FIREBOX);
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }
}
