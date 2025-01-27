package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.heatSystem;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockBoilerCasing.BoilerCasingType;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.capability.IHeat;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.metaileentity.multiblock.RecipeMapHeatMultiblockController;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class MetaTileEntityDistillationKettle extends RecipeMapHeatMultiblockController {

    public MetaTileEntityDistillationKettle(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.DISTILLATION_KETTLE);
        this.recipeMapWorkable = new HeatRecipeLogic(this, GTQTcoreRecipeMaps.DISTILLATION_KETTLE);
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityDistillationKettle(this.metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" F F ", " F F ", " COC ", " COC ", " COC "," COC ", " COC "," COC ", " COC ", " COC ", " COC ", " FCF ")
                .aisle("F   F", "F   F", "CCCCC", "CPPPC", "C   C","C   C", "CPPPC","C   C", "C   C", "CPPPC", "CCCCC", "FCCCF")
                .aisle("     ", "     ", "CCCCC", "CPPPC", "C E C","C E C", "CPEPC","C E C", "C E C", "CPPPC", "CCECC", "CCMCC")
                .aisle("F   F", "F   F", "CCCCC", "CPPPC", "C   C","C   C", "CPPPC","C   C", "C   C", "CPPPC", "CCCCC", "FCCCF")
                .aisle(" F F ", " F F ", " CCC ", " CSC ", " CCC "," CCC ", " CCC "," CCC ", " CCC ", " CCC ", " CCC ", " FCF ")
                .where('S', selfPredicate())
                .where('C', states(MetaBlocks.METAL_CASING.getState(MetalCasingType.STEEL_SOLID)).setMinGlobalLimited(96)
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(GTQTMultiblockAbility.HEAT_MULTIBLOCK_ABILITY).setExactLimit(1))
                )
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('F', states(MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel)))
                .where('P', states(MetaBlocks.BOILER_CASING.getState(BoilerCasingType.STEEL_PIPE)))
                .where('E', states(MetaBlocks.METAL_CASING.getState(MetalCasingType.STEEL_SOLID)))
                .where('O', states(MetaBlocks.METAL_CASING.getState(MetalCasingType.STEEL_SOLID)).or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(8)))
                .where(' ', any())
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("谁家LV做石化", new Object[0]));
        tooltip.add(I18n.format("gtqt.machine.distillation_kettle.1"));
        tooltip.add(I18n.format("本设备为 热学 设备，在使用时需要 热源仓 而非 能源仓"));
        tooltip.add(I18n.format("关于 热源仓 使用详情查询 热源仓 本身的Tooltips"));
        tooltip.add(I18n.format("热学能源Heat如同电学能源EU一样拥有超频逻辑，更高的温度会带来更快的加工速度"));
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
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
    protected ICubeRenderer getFrontOverlay() {
        return Textures.BLAST_FURNACE_OVERLAY;
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }
}
