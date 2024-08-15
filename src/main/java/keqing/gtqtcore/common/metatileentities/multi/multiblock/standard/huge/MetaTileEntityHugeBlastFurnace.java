package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.huge;

import gregicality.multiblocks.api.recipes.GCYMRecipeMaps;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.capability.impl.HeatingCoilRecipeLogic;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTRecipeMapMultiblockOverwrite;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static gregtech.api.GTValues.VA;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.HUGE_ALLOY_BLAST_FURANCE;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.HUGE_BLAST_FURANCE;

public class MetaTileEntityHugeBlastFurnace extends GTQTRecipeMapMultiblockOverwrite implements IHeatingCoil {

    private int blastFurnaceTemperature;
    protected int heatingCoilLevel;
    protected int coilTier;
    protected int glassTire;
    public MetaTileEntityHugeBlastFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.BLAST_RECIPES);
        this.recipeMapWorkable = new MetaTileEntityHugeBlastFurnacerWorkable(this);
        this.recipeMapWorkable = new HeatingCoilRecipeLogic(this);
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityHugeBlastFurnace(this.metaTileEntityId);
    }
    int ParallelNum=1;
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
            P = (int) ((this.energyContainer.getEnergyStored() + energyContainer.getInputPerSec())/(getMinVa()==0?1:getMinVa()));
            ParallelNum = Math.min(P, ParallelLim);
        }
    }
    public int getMinVa()
    {
        if((Math.min(this.energyContainer.getEnergyCapacity()/32,VA[Math.min(coilTier,9)])*20)==0)return 1;
        return (int)(Math.min(this.energyContainer.getEnergyCapacity()/32,VA[Math.min(coilTier,9)]));

    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        ITextComponent heatString = TextComponentUtil.stringWithColor(TextFormatting.RED, TextFormattingUtil.formatNumbers(this.blastFurnaceTemperature) + "K");
        textList.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "gregtech.multiblock.blast_furnace.max_temperature", heatString));
        if(modern==0) textList.add(new TextComponentTranslation("gtqtcore.tire1",coilTier));
        if(modern==1) textList.add(new TextComponentTranslation("gtqtcore.tire2",coilTier));
        textList.add(new TextComponentTranslation("gtqtcore.parr",ParallelNum,ParallelLim));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("最强冶炼王", new Object[0]));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.3"));
        tooltip.add(I18n.format("gregtech.machine.cracker.gtqtupdate.1"));
        tooltip.add(I18n.format("gregtech.machine.cracker.gtqtupdate.2"));
        tooltip.add(I18n.format("gtqtcore.multiblock.hb.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.2",1024));
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object glassTire = context.get("GlassTieredStats");
        this.glassTire = GTQTUtil.getOrDefault(() -> glassTire instanceof WrappedIntTired,
                () -> ((WrappedIntTired)glassTire).getIntTier(),
                0);

        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.blastFurnaceTemperature = ((IHeatingCoilBlockStats) coilType).getCoilTemperature();
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
            this.coilTier = ((IHeatingCoilBlockStats) coilType).getTier();
        } else {
            this.blastFurnaceTemperature = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
            this.coilTier = BlockWireCoil.CoilType.CUPRONICKEL.getTier();
        }
        ParallelLim=Math.min((int)Math.pow(2, coilTier),1024);
        ParallelNum=ParallelLim;
        this.blastFurnaceTemperature += 100 * Math.max(0, GTUtility.getTierByVoltage(getEnergyContainer().getInputVoltage()) - GTValues.MV);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        blastFurnaceTemperature = 0;
        heatingCoilLevel = 0;
    }

    public boolean checkRecipe( Recipe recipe, boolean consumeIfSuccess) {
        return this.blastFurnaceTemperature >= recipe.getProperty(TemperatureProperty.getInstance(), 0);
    }
    public int getCurrentTemperature() {
        return this.blastFurnaceTemperature;
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("MMMMMMMMMMMMMMM","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMSMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMXMMMMMMM","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","MMMMMMMMMMMMMMM")
                .where('X', selfPredicate())
                .where('M', states(getCasingState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                )
                .where('B', heatingCoils())
                .where('A', TiredTraceabilityPredicate.CP_GLASS.get())
                .where('S', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where(' ', air())
                .build();
    }

    private static IBlockState getCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.HIGH_TEMPERATURE_CASING);
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("MMMMMMMMMMMMMMM","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMSMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","MMMMMMMMMMMMMMM")
                .aisle("EENILJMXMMMMMMM","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","MMMMMMMMMMMMMMM")
                .where('X', HUGE_BLAST_FURANCE, EnumFacing.SOUTH)
                .where('A', MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS))
                .where('M', getCasingState())
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[5], EnumFacing.SOUTH)
                .where('N',MetaTileEntities.MAINTENANCE_HATCH, EnumFacing.SOUTH)
                .where('I', MetaTileEntities.FLUID_IMPORT_HATCH[4], EnumFacing.SOUTH)
                .where('L', MetaTileEntities.ITEM_IMPORT_BUS[4], EnumFacing.SOUTH)
                .where('J', MetaTileEntities.FLUID_EXPORT_HATCH[4], EnumFacing.SOUTH)
                .where('S', MetaTileEntities.MUFFLER_HATCH[1], EnumFacing.UP)
                .where(' ', Blocks.AIR.getDefaultState());
        GregTechAPI.HEATING_COILS.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
                .forEach(entry -> shapeInfo.add(builder.where('B', entry.getKey()).build()));
        return shapeInfo;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GCYMTextures.BLAST_CASING;
    }
    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return GCYMTextures.ALLOY_BLAST_SMELTER_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    protected class MetaTileEntityHugeBlastFurnacerWorkable extends MultiblockRecipeLogic {

        public MetaTileEntityHugeBlastFurnacerWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            int MaxProgress = (int) Math.floor(maxProgress * Math.pow(0.8, glassTire));
            super.setMaxProgress(MaxProgress);
        }

        @Override
        protected long getMaxParallelVoltage() {
            return super.getMaxVoltage();
        }

        @Override
        public int getParallelLimit() {
            return ParallelNum;
        }
    }
}