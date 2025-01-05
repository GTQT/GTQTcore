package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.IElectrode;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTRecipeMapMultiblockOverwrite;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.ElectronBathProperties;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;

import static gregtech.api.GTValues.V;
import static gregtech.api.GTValues.VA;
import static keqing.gtqtcore.api.unification.GTQTMaterials.EleAcid;

public class MetaTileEntityElectronBath extends GTQTRecipeMapMultiblockOverwrite {
    int ParallelNum = 1;
    private int casingTier;
    private int tubeTier;
    private int tier;

    private int ElectrodeTier;

    public MetaTileEntityElectronBath(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.ELECTROBATH);
        this.recipeMapWorkable = new ELELogic(this);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("modern", modern);
        data.setInteger("casingTier", casingTier);
        data.setInteger("ElectrodeTier", ElectrodeTier);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        modern = data.getInteger("modern");
        casingTier = data.getInteger("casingTier");
        ElectrodeTier = data.getInteger("ElectrodeTier");
    }

    @Override
    public void update() {
        super.update();
        if (modern == 0) {
            ParallelNum = ParallelNumA;
        }
        if (modern == 1) {
            P = (int) ((this.energyContainer.getEnergyStored() + energyContainer.getInputPerSec()) / (getMinVa() == 0 ? 1 : getMinVa()));
            ParallelNum = Math.min(P, ParallelLim);
        }
    }

    public int getMinVa() {
        if ((Math.min(this.energyContainer.getEnergyCapacity() / 32, VA[tier]) * 20) == 0) return 1;
        return (int) (Math.min(this.energyContainer.getEnergyCapacity() / 32, VA[tier]));

    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return super.checkRecipe(recipe, consumeIfSuccess) && recipe.getProperty(ElectronBathProperties.getInstance(), 0) <= ElectrodeTier;
    }


    @SuppressWarnings("SpellCheckingInspection")
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("NNNNN", "NNNNN", "NNNNN", "#NNN#")
                .aisle("NNNNN", "N###N", "N#X#N", "#NMN#")
                .aisle("NNNNN", "N#N#N", "N#X#N", "#NMN#")
                .aisle("NNNNN", "N#N#N", "N#X#N", "#NMN#")
                .aisle("NNNNN", "N###N", "N#X#N", "#NMN#")
                .aisle("NNNNN", "N#N#N", "N#X#N", "#NMN#")
                .aisle("NNNNN", "N###N", "N#X#N", "#NMN#")
                .aisle("NNNNN", "N#N#N", "N#X#N", "#NMN#")
                .aisle("NNNNN", "N#N#N", "N#X#N", "#NMN#")
                .aisle("NNNNN", "N###N", "N#X#N", "#NMN#")
                .aisle("NNNNN", "NNCNN", "NNNNN", "#NNN#")
                .where('C', selfPredicate())
                .where('N', TiredTraceabilityPredicate.CP_CASING.get()
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.MUFFLER_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2).setPreviewCount(1)))
                .where('M', abilities(GTQTMultiblockAbility.ELECTRODE_MULTIBLOCK_ABILITY))
                .where('X', TiredTraceabilityPredicate.CP_TUBE.get())
                .where('#', any())
                .build();
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return true;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.CHEMICAL_PLANT_OVERLAY;
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GTQTValue.UPDATE_TIER5) {
            this.casingTier = buf.readInt();
        }
        if (dataId == GTQTValue.REQUIRE_DATA_UPDATE5) {
            this.writeCustomData(GTQTValue.UPDATE_TIER5, buf1 -> buf1.writeInt(this.casingTier));
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.casingTier);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.casingTier = buf.readInt();
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (casingTier != tubeTier)
            textList.add(new TextComponentTranslation("gtqtcore.equal", casingTier, tubeTier));
        textList.add(new TextComponentTranslation("电极状态：%s 电极等级：%s", checkAvailable(), ElectrodeTier));
        if (modern == 0) textList.add(new TextComponentTranslation("gtqtcore.tire1", tier));
        if (modern == 1) textList.add(new TextComponentTranslation("gtqtcore.tire2", tier));
        textList.add(new TextComponentTranslation("gtqtcore.parr", ParallelNum, ParallelLim));
        if (getInputFluidInventory() != null) {
            FluidStack STACK = getInputFluidInventory().drain(EleAcid.getFluid(Integer.MAX_VALUE), false);
            int liquidOxygenAmount = STACK == null ? 0 : STACK.amount;
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.ele.amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
        }
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("谁还需要电解机", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.ele.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.ele.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.ele.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.machine.ele.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.machine.ele.tooltip.5"));
        tooltip.add(I18n.format("gregtech.machine.cracker.gtqtupdate.1"));
        tooltip.add(I18n.format("gregtech.machine.cracker.gtqtupdate.2"));
    }

    @Override
    public void updateFormedValid() {
        if (!isStructureFormed()) return;
        if (!checkAvailable()) return;
        ElectrodeTier = getElectrodeTier();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityElectronBath(metaTileEntityId);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        switch (this.casingTier) {
            case (2) -> {
                return Textures.SOLID_STEEL_CASING;
            }
            case (3) -> {
                return Textures.FROST_PROOF_CASING;
            }
            case (4) -> {
                return Textures.CLEAN_STAINLESS_STEEL_CASING;
            }
            case (5) -> {
                return Textures.STABLE_TITANIUM_CASING;
            }
            case (6) -> {
                return Textures.ROBUST_TUNGSTENSTEEL_CASING;
            }
            case (7) -> {
                return GTQTTextures.PD_CASING;
            }
            case (8) -> {
                return GTQTTextures.NQ_CASING;
            }
            case (9) -> {
                return GTQTTextures.ST_CASING;
            }
            case (10) -> {
                return GTQTTextures.AD_CASING;
            }
            default -> {
                return Textures.BRONZE_PLATED_BRICKS;
            }
        }
    }

    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("gtqt.tooltip.update")};
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object casingTier = context.get("ChemicalPlantCasingTieredStats");
        Object tubeTier = context.get("ChemicalPlantTubeTieredStats");
        this.casingTier = GTQTUtil.getOrDefault(() -> casingTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) casingTier).getIntTier(),
                0);

        this.tubeTier = GTQTUtil.getOrDefault(() -> tubeTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) tubeTier).getIntTier(),
                0);

        this.tier = Math.min(this.casingTier, this.tubeTier);

        this.writeCustomData(GTQTValue.UPDATE_TIER5, buf -> buf.writeInt(this.tier));
        ParallelLim = (int) Math.pow(2, this.casingTier);
        ParallelNum = ParallelLim;
    }

    public IElectrode getElectrodeHatch(int i) {
        List<IElectrode> abilities = getAbilities(GTQTMultiblockAbility.ELECTRODE_MULTIBLOCK_ABILITY);
        if (abilities.isEmpty())
            return null;
        return abilities.get(i);
    }

    public int getElectrodeTier() {
        int minTier = 1;
        for (int i = 0; i < 9; i++) {
            if (minTier > getElectrodeHatch(i).getElectrodeTier())
                minTier = getElectrodeHatch(i).getElectrodeTier();
        }
        return minTier;
    }

    public boolean checkAvailable() {
        for (int i = 0; i < 9; i++) {
            if (!getElectrodeHatch(i).isAvailable())
                return false;
        }
        return true;
    }

    public void setWork(boolean work) {
        if (checkAvailable()) {
            for (int i = 0; i < 9; i++)
                getElectrodeHatch(i).setWork(work);
        }
    }

    protected class ELELogic extends MultiblockRecipeLogic {


        public ELELogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity, true);
        }

        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true) && checkAvailable()) {
                setWork(true);
                this.drawEnergy(this.recipeEUt, false);

                if (++progressTime > maxProgressTime) {
                    completeRecipe();
                    setWork(false);
                    if (this.hasNotEnoughEnergy && this.getEnergyInputPerSecond() > 19L * (long) this.recipeEUt) {
                        this.hasNotEnoughEnergy = false;
                    }
                } else if (this.recipeEUt > 0) {
                    this.hasNotEnoughEnergy = true;
                    this.decreaseProgress();
                }
            }
        }

        public long getMaxVoltage() {
            return Math.min(super.getMaxVoltage(), V[tier]);
        }

        @Override
        public int getParallelLimit() {
            return ParallelNum;
        }

        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = (int) (maxProgress * (100 - tier * 5) / 100.0);
        }

        protected void modifyOverclockPost(int[] resultOverclock, @Nonnull IRecipePropertyStorage storage) {
            super.modifyOverclockPost(resultOverclock, storage);
            if (ElectrodeTier > 0) {
                resultOverclock[0] = (int) ((double) resultOverclock[0] * (20 - ElectrodeTier) / 20.0);
                resultOverclock[0] = Math.max(1, resultOverclock[0]);
            }
        }

    }
}