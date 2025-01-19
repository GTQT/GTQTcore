package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.GTValues;
import gregtech.api.capability.*;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.utils.TooltipHelper;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static keqing.gtqtcore.api.utils.GTQTUtil.getAccelerateByCWU;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockGlass.CasingType.COPY_GALSS;

public class MetaTileEntityLargeElementDuplicator extends RecipeMapMultiblockController implements IOpticalComputationReceiver {
    int requestCWUt;
    private IOpticalComputationProvider computationProvider;
    public MetaTileEntityLargeElementDuplicator(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.COPY_RECIPES);
        this.recipeMapWorkable = new DuplicatorRecipeLogic(this, true);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLargeElementDuplicator(metaTileEntityId);
    }
    @Override
    public void update() {
        super.update();
        if (isStructureFormed() && isActive()) {
            requestCWUt = computationProvider.requestCWUt(1024, false);
        }
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("gtqtcore.kqcc_accelerate", requestCWUt, getAccelerateByCWU(requestCWUt)));
    }
    @Override
    protected BlockPattern createStructurePattern() {
        TraceabilityPredicate abilities = autoAbilities();
        return FactoryBlockPattern.start()
                .aisle("###XXX###", "##XXXXX##", "#XXXXXXX#", "XXXXXXXXX","XXXXXXXXX","XXXXXXXXX","#XXXXXXX#", "##XXXXX##", "###XXX###")
                .aisle("###XWX###", "##WQZQW##", "#WQZTZQW#", "XQZTYTZQX","WQZYYYZQW","XQZTYTZQX","#WQZTZQW#", "##WQZQW##", "###XWX###")
                .aisle("###XGX###", "##G###G##", "#G#####G#", "X###Y###X","G##YYY##G","X###Y###X","#G#####G#", "##G###G##", "###XGX###")
                .aisle("###XGX###", "##G###G##", "#G#####G#", "X###Y###X","G##YYY##G","X###Y###X","#G#####G#", "##G###G##", "###XGX###")
                .aisle("###XWX###", "##WQZQW##", "#WQZTZQW#", "XQZTYTZQX","WQZYYYZQW","XQZTYTZQX","#WQZTZQW#", "##WQZQW##", "###XWX###")
                .aisle("###XXX###", "##XXXXX##", "#XXXXXXX#", "XXXXXXXXX","XXXXSXXXX","XXXXXXXXX","#XXXXXXX#", "##XXXXX##", "###XXX###")
                .where('S', selfPredicate())
                .where('C', states(getCasingState3()))
                .where('G', states(getGlassState()))
                .where('X', states(getCasingState()).setMinGlobalLimited(138)
                        .or(abilities)
                        .or(abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1))
                        .or(abilities(MultiblockAbility.INPUT_LASER)
                                .setMaxGlobalLimited(1)))
                .where('W', states(getCasingState1()))
                .where('Q', states(getCasingState2()))
                .where('Z', states(getCasingState4()))
                .where('T', states(getCasingState5()))
                .where('Y', states(getCasingState6()))
                .where('#', air())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.ELEMENT_CONSTRAINS_MACHINE_CASING;
    }
    protected IBlockState getGlassState() {
        return GTQTMetaBlocks.blockMultiblockGlass.getState(COPY_GALSS);
    }
    protected IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing.getState(ELEMENT_CONSTRAINS_MACHINE_CASING);
    }
    protected IBlockState getCasingState1() {
        return GTQTMetaBlocks.blockMultiblockCasing.getState(MASS_GENERATION_CASING);
    }
    protected IBlockState getCasingState2() {
        return GTQTMetaBlocks.blockMultiblockCasing.getState(MASS_GENERATION_COIL_CASING);
    }

    protected IBlockState getCasingState3() {
        return GTQTMetaBlocks.blockMultiblockCasing.getState(MASS_GENERATION_COIL_CASING);
    }

    protected IBlockState getCasingState4() {
        return GTQTMetaBlocks.blockMultiblockCasing.getState(RESONATOR_CASING);
    }

    protected IBlockState getCasingState5() {
        return GTQTMetaBlocks.blockMultiblockCasing.getState(BUNCHER_CASING);
    }

    protected IBlockState getCasingState6() {
        return GTQTMetaBlocks.blockMultiblockCasing.getState(HIGH_VOLTAGE_CAPACITOR_BLOCK_CASING);
    }
    @Override
    protected void initializeAbilities() {
        this.inputInventory = new ItemHandlerList(this.getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.inputFluidInventory = new FluidTankList(this.allowSameFluidFillForOutputs(), this.getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(this.getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.outputFluidInventory = new FluidTankList(this.allowSameFluidFillForOutputs(), this.getAbilities(MultiblockAbility.EXPORT_FLUIDS));
        List<IEnergyContainer> energyContainer = new ArrayList<>(this.getAbilities(MultiblockAbility.INPUT_ENERGY));
        energyContainer.addAll(this.getAbilities(MultiblockAbility.INPUT_LASER));
        this.energyContainer = new EnergyContainerList(energyContainer);
    }
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gregtech.machine.perfect_oc"));
        tooltip.add(I18n.format("gtqtcore.machine.parallel.pow.machineTier", 2, 256));
        tooltip.add(I18n.format("gtqtcore.machine.progress_time","maxProgress *0.8"));
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.acc.tooltip"));
        tooltip.add(I18n.format("本机器允许使用激光能源仓代替能源仓！"));
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        List<IOpticalComputationHatch> providers = this.getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
        if (providers != null && !providers.isEmpty()) {
            this.computationProvider = providers.get(0);
        }
    }
    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.LARGE_UU_PRODUCTER;
    }
    @Override
    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
    }

    protected class DuplicatorRecipeLogic extends MultiblockRecipeLogic{

        public DuplicatorRecipeLogic(RecipeMapMultiblockController tileEntity, boolean hasPerfectOC) {
            super(tileEntity, hasPerfectOC);
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = (int) (maxProgress *0.8* getAccelerateByCWU(requestCWUt));
        }

        @Override
        public int getParallelLimit() {
            int tire = 1;
            for (int i = 0; i < GTValues.V.length; i++) {
                if(GTValues.V[i]==this.getMaxVoltage())
                    tire = i;
            }
            return (int) Math.max(Math.pow(2, tire),256);
        }
    }
}