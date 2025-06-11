package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.endGame;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.utils.TooltipHelper;
import keqing.gtqtcore.api.capability.IWarpSwarm;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.WARP_SWARM_MULTIBLOCK_ABILITY;
import static keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate.CP_DM_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockQuantumCasing.CasingType.*;

public class MetaTileEntityDimensionalMixer extends MultiMapMultiblockController {

    public MetaTileEntityDimensionalMixer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.EXTRADIMENSIONAL_MIXING_RECIPES,
                RecipeMaps.MIXER_RECIPES,

        });
        this.recipeMapWorkable = new DimensionalMixerRecipeLogic(this);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockQuantumCasing.getState(HIGH_ENERGY_CASING);
    }

    private static IBlockState getSecondCasingState() {
        return GTQTMetaBlocks.blockQuantumCasing.getState(DIMENSIONAL_INJECTION_CASING);
    }

    private static IBlockState getThirdCasingState() {
        return GTQTMetaBlocks.blockQuantumCasing.getState(DIMENSIONAL_BRIDGE_CASING);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityDimensionalMixer(metaTileEntityId);
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
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" CAC ", " ABA ", " ABA ", " ABA ", " ABA ", " ABA ", " CAC ")
                .aisle("CBBBC", "A###A", "A###A", "A###A", "A###A", "A###A", "CBBBC")
                .aisle("ABBBA", "B###B", "B###B", "B###B", "B###B", "B###B", "ABBBA")
                .aisle("CBBBC", "A###A", "A###A", "A###A", "A###A", "A###A", "CBBBC")
                .aisle(" CAC ", " ABA ", " ABA ", " ASA ", " ABA ", " ABA ", " CAC ")
                .where('S', this.selfPredicate())
                .where('A', states(getCasingState()))
                .where('B', states(getSecondCasingState())
                        .or(autoAbilities())
                        .or(abilities(MultiblockAbility.INPUT_LASER)
                                .setMaxGlobalLimited(1))
                        .or(abilities(WARP_SWARM_MULTIBLOCK_ABILITY)
                                .setExactLimit(1))
                )
                .where('C', states(getThirdCasingState()))
                .where('F', CP_DM_CASING.get())
                .where('#', air())
                .where(' ', any())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return iMultiblockPart == null ? GTQTTextures.HIGH_ENERGY_CASING : GTQTTextures.DIMENSIONAL_INJECTION_CASING;
    }

    @SideOnly(Side.CLIENT)

    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.DIMENSIONALLY_TRANSCENDENT_PLASMA_FORGE_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return true;
    }

    public IWarpSwarm getAbility() {
        if (this.getAbilities(WARP_SWARM_MULTIBLOCK_ABILITY) != null)
            return this.getAbilities(WARP_SWARM_MULTIBLOCK_ABILITY).get(0);
        return null;
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.BLINKING_RED + I18n.format("搅拌原始星云"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("本设备支持纳米蜂群仓，每完成一次配方会消耗一点耐久（无视并行）"));
        tooltip.add(I18n.format("每等级纳米蜂群提供Math.pow(2,tier)*256的并行"));
        tooltip.add(I18n.format("每等级纳米蜂群提供10%%的耗时减免"));
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.laser.tooltip"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("每Tick消耗 1mb 超维度催化剂MKI,产生4点维度翘曲点"));
        tooltip.add(I18n.format("每Tick消耗 1mb 超维度催化剂MKII,产生6点维度翘曲点"));
        tooltip.add(I18n.format("每Tick消耗 1mb 超维度催化剂MKIII,产生8点维度翘曲点"));
        tooltip.add(I18n.format("维度翘曲点数量多于576000时，多方块进入无损超频模式"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("正常情况下机器每Tick降低1点维度翘曲点"));
        tooltip.add(I18n.format("维度翘曲点数量多于576000时,每Tick降低2点维度翘曲点"));
        tooltip.add(I18n.format("=============================================="));
    }

    public class DimensionalMixerRecipeLogic extends MultiblockRecipeLogic {

        public DimensionalMixerRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity, true);
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (maxProgress * getTimeBound()));
        }

        public double getTimeBound() {
            if (getAbility().isAvailable()) return (10 - getAbility().getWarpSwarmTier()) / 10.0;
            return 1;
        }

        @Override
        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);
                if (++this.progressTime > this.maxProgressTime) {
                    getAbility().applyDamage(1);
                    this.completeRecipe();
                }
                if (this.hasNotEnoughEnergy && this.getEnergyInputPerSecond() > 19L * this.recipeEUt) {
                    this.hasNotEnoughEnergy = false;
                }
            } else if (this.recipeEUt > 0L) {
                this.hasNotEnoughEnergy = true;
                this.decreaseProgress();
            }
        }
    }
}
