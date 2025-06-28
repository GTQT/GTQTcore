package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.value.sync.IntSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.metatileentity.multiblock.ui.KeyManager;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIBuilder;
import gregtech.api.metatileentity.multiblock.ui.TemplateBarBuilder;
import gregtech.api.metatileentity.multiblock.ui.UISyncer;
import gregtech.api.mui.GTGuiTextures;
import gregtech.api.pattern.*;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.*;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityFluidHatch;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;

import static gregtech.api.GTValues.V;
import static gregtech.api.unification.material.Materials.Steam;
import static gregtech.api.util.RelativeDirection.*;

//闪蒸
public class MetaTileEntityMSF extends MultiMapMultiblockController implements ProgressBarMultiblock {
    int[] steam = new int[3];

    FluidStack STEAM = Steam.getFluid(1000);

    private int coilLevel;
    private int number;

    public MetaTileEntityMSF(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.SFM,
                RecipeMaps.DISTILLATION_RECIPES
        });
        this.recipeMapWorkable = new MFSWorkableHandler(this);
    }

    private static IBlockState getCasingAState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
    }

    private static IBlockState getCasingBState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE);
    }

    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.StainlessSteel).getBlock(Materials.StainlessSteel);
    }

    @Override
    public void update() {
        super.update();
        if (steam[0] <= 9000) {
            IMultipleTankHandler inputTank = getInputFluidInventory();
            if (STEAM.isFluidStackIdentical(inputTank.drain(STEAM, false))) {
                inputTank.drain(STEAM, true);
                steam[0] = steam[0] + 180 * coilLevel;

            }
        }

        for (int i = 0; i < coilLevel; i++) {
            if (steam[0] > steam[1]) {
                steam[0] = steam[0] - 40;
                steam[1] = steam[1] + 30;
            }

            if (steam[0] < steam[1]) {
                steam[1] = steam[1] - 30;
                steam[0] = steam[0] + 40;
            }

            if (steam[1] > steam[2]) {
                steam[1] = steam[1] - 10;
                steam[2] = steam[2] + 10;
            }

            if (steam[1] < steam[2]) {
                steam[2] = steam[2] - 10;
                steam[1] = steam[1] + 10;
            }
        }

    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("fluid1", steam[0]);
        data.setInteger("fluid2", steam[1]);
        data.setInteger("fluid3", steam[2]);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        steam[0] = data.getInteger("fluid1");
        steam[1] = data.getInteger("fluid2");
        steam[2] = data.getInteger("fluid3");
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.msf.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.msf.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.msf.tooltip.3"));
        tooltip.add(I18n.format("gregtech.machine.msf.tooltip.4"));
    }

    @Override
    protected void configureWarningText(MultiblockUIBuilder builder) {
        super.configureWarningText(builder);
        builder.addCustom((manager, syncer) -> {
            if (isStructureFormed()) {
                FluidStack lubricantStack = getInputFluidInventory().drain(Steam.getFluid(Integer.MAX_VALUE), false);
                if (lubricantStack == null || lubricantStack.amount < 1000) {
                    manager.add(KeyUtil.lang(TextFormatting.RED,
                            "gtqtcore.multiblock.sfm.no_water1"));
                }
            }
        });
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        number = context.getInt("Count");

        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.coilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
        } else {
            this.coilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
        }
    }

    public boolean getStatue() {
        return steam[0] > 6000 && steam[1] > 6000 && steam[2] > 6000;
    }

    @Override
    protected void configureDisplayText(MultiblockUIBuilder builder) {
        builder.setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(this.getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addCustom(this::addCustomCapacity)
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgress(), recipeMapWorkable.getMaxProgress())
                .addRecipeOutputLine(recipeMapWorkable);
    }

    private void addCustomCapacity(KeyManager keyManager, UISyncer syncer) {
        if (getInputFluidInventory() != null) {
            FluidStack SteamStack = getInputFluidInventory().drain(Steam.getFluid(Integer.MAX_VALUE), false);
            int SteamAmount = SteamStack == null ? 0 : SteamStack.amount;
            keyManager.add(KeyUtil.lang(TextFormatting.GREEN, "gtqtcore.gc.count1",syncer.syncString(TextFormattingUtil.formatNumbers(SteamAmount))));
        }

        keyManager.add(KeyUtil.lang(TextFormatting.GREEN, "gtqtcore.msf.count2",syncer.syncDouble(steam[0] / 1000.0), syncer.syncDouble(steam[1] / 1000.0), syncer.syncDouble(steam[2] / 1000.0)));
        if (getStatue()) keyManager.add(KeyUtil.lang(TextFormatting.GREEN,
                "gtqtcore.msf.good"));
        else keyManager.add(KeyUtil.lang(TextFormatting.YELLOW,
                "gtqtcore.msf.no"));

    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityMSF(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        FactoryBlockPattern pattern = FactoryBlockPattern.start(RIGHT, BACK, UP)
                .aisle("HHH", "HHH", "HHH", "HHH", " H ")
                .aisle("HHH", "HHH", "HHH", "HHH", " S ")
                .aisle("HHH", "HHH", "HHH", "HHH", "FHF")
                .aisle("F F", "FUF", "FUF", "FUF", "F F")
                .aisle("F F", "FUF", "FUF", "FUF", "F F")
                .aisle("F F", "FUF", "FUF", "FUF", "F F")
                .aisle("F F", "FUF", "FUF", "FUF", "F F")
                .aisle("F F", "YYY", "YCO", "YYY", "FYF").setRepeatable(1, 12)
                .aisle("F F", "YYY", "YYB", "YYY", " Y ")
                .where('S', selfPredicate())
                .where('Y', states(getCasingAState()))
                .where('U', states(getCasingBState()))
                .where('H', states(getCasingAState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2).setPreviewCount(2))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setExactLimit(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(4).setPreviewCount(2))
                )
                .where('B', (abilities(MultiblockAbility.EXPORT_ITEMS)))
                .where('O', metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.EXPORT_FLUIDS).stream()
                        .filter(mte -> (mte instanceof MetaTileEntityFluidHatch))
                        .toArray(MetaTileEntity[]::new)))
                .where('C', coilPredicate())
                .where('F', states(getFrameState()))
                .where(' ', any());
        return pattern.build();
    }

    private TraceabilityPredicate coilPredicate() {
        return new TraceabilityPredicate((blockWorldState) -> {
            IBlockState blockState = blockWorldState.getBlockState();
            if (blockState.getBlock() instanceof BlockWireCoil blockWireCoil) {
                BlockWireCoil.CoilType coilType = blockWireCoil.getState(blockState);
                Object currentCoilType = blockWorldState.getMatchContext().getOrPut("CoilType", coilType);
                if (!currentCoilType.toString().equals(coilType.getName())) {
                    blockWorldState.setError(new PatternStringError("gregtech.multiblock.pattern.error.coils"));
                    return false;
                } else {
                    blockWorldState.getMatchContext().increment("Count", 1);
                    blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>()).add(blockWorldState.getPos());
                    return true;
                }
            } else {
                return false;
            }
        }, () -> Arrays.stream(BlockWireCoil.CoilType.values()).map((type) -> new BlockInfo(MetaBlocks.WIRE_COIL.getState(type), null)).toArray(BlockInfo[]::new)).addTooltips("gregtech.multiblock.pattern.error.coils");
    }

    public boolean hasMufflerMechanics() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.CLEAN_STAINLESS_STEEL_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }

    @Override
    public int getProgressBarCount() {
        return 3;
    }

    @Override
    public void registerBars(List<UnaryOperator<TemplateBarBuilder>> bars, PanelSyncManager syncManager) {
        for(int i=0;i<3;i++)
        {
            IntSyncValue hatch = new IntSyncValue(()->steam[0]);
            syncManager.syncValue("hatch"+i, hatch);

            bars.add(barTest -> barTest
                    .texture(GTGuiTextures.PROGRESS_BAR_FUSION_HEAT)
                    .tooltipBuilder(tooltip -> {
                        IKey heatInfo = KeyUtil.string(TextFormatting.AQUA,
                                "%s / %s  kPa",
                                hatch.getIntValue()+100, 10000);
                        tooltip.add(KeyUtil.lang(
                                "仓室压力",
                                heatInfo));
                    })
                    .progress(() -> hatch.getIntValue() > 0 ?
                            (double) (hatch.getIntValue() + 1000) /10000 : 0));
        }
    }
    private class MFSWorkableHandler extends MultiblockRecipeLogic {
        public MFSWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public int getParallelLimit() {
            if (getStatue()) {
                return number * 2;
            }
            return number;
        }

        public long getMaxVoltage() {
            return V[5];
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            if (getStatue()) {
                maxProgressTime = (int) (maxProgress * 0.6);
            } else this.maxProgressTime = (int) (maxProgress * 0.8);
        }

        public boolean getStatue() {
            return steam[0] > 6000 && steam[1] > 6000 && steam[2] > 6000;
        }

        protected void updateRecipeProgress() {
            if (canRecipeProgress && drawEnergy(recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);
                if (++progressTime > maxProgressTime) {
                    steam[0] = (int) (steam[0] * 0.24);
                    steam[1] = (int) (steam[1] * 0.48);
                    steam[2] = (int) (steam[2] * 0.64);
                    completeRecipe();
                }
            }
        }
    }
}
