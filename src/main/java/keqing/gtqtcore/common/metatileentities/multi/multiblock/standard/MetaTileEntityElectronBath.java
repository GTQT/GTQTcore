package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.MetaBlocks;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.IElectrode;
import keqing.gtqtcore.api.metatileentity.GTQTRecipeMapMultiblockController;
import keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.ElectronBathProperties;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class MetaTileEntityElectronBath extends GTQTRecipeMapMultiblockController {

    private int casingTier;
    private int tubeTier;
    private int ElectrodeTier;

    public MetaTileEntityElectronBath(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.ELECTROBATH
        });
        this.recipeMapWorkable = new ElectronBathLogic(this);
        setTierFlag(true);
        //setTier(auto);
        setMaxParallel(128);
        setMaxParallelFlag(true);
        //setMaxVoltage(auto);
        setMaxVoltageFlag(true);
        //setTimeReduce(coilLevel);
        setTimeReduceFlag(true);
        setOverclocking(3);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("  BBB  ", "  BBB  ", "  BBB  ", "       ")
                .aisle(" BBBBB ", " BB BB ", "  B B  ", "  BBB  ")
                .aisle("ABBBBBA", "ACC CCA", "AAC CAA", "  BDB  ")
                .aisle(" BBBBB ", " BB BB ", "  B B  ", "  BDB  ")
                .aisle("ABBBBBA", "ACC CCA", "AAC CAA", "  BDB  ")
                .aisle(" BBBBB ", " BB BB ", "  B B  ", "  BDB  ")
                .aisle("ABBBBBA", "ACC CCA", "AAC CAA", "  BDB  ")
                .aisle(" BBBBB ", " BB BB ", "  B B  ", "  BDB  ")
                .aisle("ABBBBBA", "ACC CCA", "AAC CAA", "  BDB  ")
                .aisle(" BBBBB ", " BB BB ", "  B B  ", "  BDB  ")
                .aisle("ABBBBBA", "ACC CCA", "AAC CAA", "  BDB  ")
                .aisle(" BBBBB ", " BB BB ", "  B B  ", "  BBB  ")
                .aisle("  BBB  ", "  BSB  ", "  BBB  ", "       ")
                .where('S', selfPredicate())
                .where('B', TiredTraceabilityPredicate.CP_CASING.get()
                        .or(autoAbilities())
                )
                .where('D', abilities(GTQTMultiblockAbility.ELECTRODE_MULTIBLOCK_ABILITY))
                .where('C', TiredTraceabilityPredicate.CP_TUBE.get())
                .where('A', states(MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel)))
                .where(' ', any())
                .build();
    }

    @Override
    public boolean shouldShowVoidingModeButton() {
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
        if (!isStructureFormed()) return;
        if (casingTier != tubeTier)
            textList.add(new TextComponentTranslation("gtqtcore.equal", casingTier, tubeTier));
        textList.add(new TextComponentTranslation("电极状态：%s 电极等级：%s", checkAvailable(), ElectrodeTier));
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("谁还需要电解机", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.ele.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.ele.tooltip.2"));
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public void updateFormedValid() {
        super.updateFormedValid();
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

        setTier(Math.min(this.casingTier, this.tubeTier));
        setMaxVoltage(Math.min(this.casingTier, this.tubeTier));
        setTimeReduce((100 - Math.min(Math.min(this.casingTier, this.tubeTier), 10) * 5.0) / 100);
        this.writeCustomData(GTQTValue.UPDATE_TIER5, buf -> buf.writeInt(this.tier));
    }

    public List<IElectrode> getElectrodeHatch() {
        return getAbilities(GTQTMultiblockAbility.ELECTRODE_MULTIBLOCK_ABILITY);
    }

    public int getElectrodeTier() {
        return getElectrodeHatch().stream()
                .mapToInt(IElectrode::getElectrodeTier)
                .min()
                .orElse(5); // 默认值可根据业务需求调整
    }

    public boolean checkAvailable() {
        return getElectrodeHatch().stream()
                .allMatch(IElectrode::isAvailable);
    }

    public void setWork(boolean work) {
        getElectrodeHatch().forEach(e -> e.setWork(work));
    }

    protected class ElectronBathLogic extends GTQTMultiblockLogic {

        boolean work = false;

        public ElectronBathLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true) && !getElectrodeHatch().isEmpty()) {
                if (!work) {
                    setWork(true);
                    work = true;
                }
                this.drawEnergy(this.recipeEUt, false);

                if (++progressTime > maxProgressTime) {
                    completeRecipe();
                    setWork(false);
                    work = false;
                }
            }
        }

        @Override
        public boolean checkRecipe(@NotNull Recipe recipe) {
            return recipe.getProperty(ElectronBathProperties.getInstance(), 0) <= ElectrodeTier;
        }
    }
}