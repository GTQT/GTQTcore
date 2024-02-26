package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.GTValues;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
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
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTRecipeMapMultiblockOverwrite;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.ELEProperties;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;

import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.HydrochloricAcid;
import static gregtech.api.unification.material.Materials.Lubricant;

public class MetaTileEntityElectrobath extends GTQTRecipeMapMultiblockOverwrite {

    private int eleTier;
    private int casingTier;
    private int tubeTier;
    private int tier;

    private int clean=1000000;
    private int eu=10;

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (isStructureFormed()) {
            if (clean<=200000) {
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.ele.no_water"));
            }
        }
    }
     int ParallelNum;
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("modern", modern);
        data.setInteger("casingTier", casingTier);
        data.setInteger("clean", clean);
        return super.writeToNBT(data);
    }
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        modern = data.getInteger("modern");
        casingTier = data.getInteger("casingTier");
        clean = data.getInteger("clean");
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
            P = (int) ((this.energyContainer.getEnergyStored() + energyContainer.getInputPerSec()) / getMinVa());
            ParallelNum = Math.min(P, ParallelLim);
        }
    }
    public int getMinVa()
    {
        if((Math.min(this.energyContainer.getEnergyCapacity()/32,VA[tier])*20)==0)return 1;
        return (int)(Math.min(this.energyContainer.getEnergyCapacity()/32,VA[tier]));

    }
    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 18, "", this::decrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("gtqtcore.multiblock.ele.threshold_decrement"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 18, "", this::incrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("gtqtcore.multiblock.ele.threshold_increment"));
        return group;
    }

    private void incrementThreshold(Widget.ClickData clickData) {
            this.eu = MathHelper.clamp(eu + 1, 1, 20);
    }

    private void decrementThreshold(Widget.ClickData clickData) {
            this.eu = MathHelper.clamp(eu - 1, 1, 20);
    }

    public MetaTileEntityElectrobath(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.ELECTROBATH);
        this.recipeMapWorkable = new ELELogic(this);
    }


    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return super.checkRecipe(recipe, consumeIfSuccess) && recipe.getProperty(ELEProperties.getInstance(), 0) <= eleTier;
    }


    @SuppressWarnings("SpellCheckingInspection")
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("NNNNN","NNNNN","NNNNN","#NNN#")
                .aisle("NNNNN","N###N","N#X#N","#NMN#")
                .aisle("NNNNN","N#N#N","N#X#N","#NMN#")
                .aisle("NNNNN","N#N#N","N#X#N","#NMN#")
                .aisle("NNNNN","N###N","N#X#N","#NMN#")
                .aisle("NNNNN","N#N#N","N#X#N","#NMN#")
                .aisle("NNNNN","N###N","N#X#N","#NMN#")
                .aisle("NNNNN","N#N#N","N#X#N","#NMN#")
                .aisle("NNNNN","N#N#N","N#X#N","#NMN#")
                .aisle("NNNNN","N###N","N#X#N","#NMN#")
                .aisle("NNNNN","NNCNN","NNNNN","#NNN#")
                .where('C', selfPredicate())
                .where('N', TiredTraceabilityPredicate.CP_CASING
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.MUFFLER_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2).setPreviewCount(1)))
                .where('M', TiredTraceabilityPredicate.CP_ELE_CASING)
                .where('X', TiredTraceabilityPredicate.CP_TUBE)
                .where('#', any())

                .build();
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.CHEMICAL_PLANT_OVERLAY;
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == GTQTValue.UPDATE_TIER){
            this.casingTier = buf.readInt();
        }
        if(dataId == GTQTValue.REQUIRE_DATA_UPDATE){
            this.writeCustomData(GTQTValue.UPDATE_TIER,buf1 -> buf1.writeInt(this.casingTier));
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
        if(casingTier!=tubeTier)
            textList.add(new TextComponentTranslation("gtqtcore.equal", casingTier,tubeTier));
        textList.add(new TextComponentTranslation("gtqtcore.eleTire",tier, eleTier,GTValues.V[tier]));
        textList.add(new TextComponentTranslation("gregtech.multiblock.ele.1", 5*eleTier,eu,clean));
        if(modern==0) textList.add(new TextComponentTranslation("gtqtcore.tire1",clean));
        if(modern==1) textList.add(new TextComponentTranslation("gtqtcore.tire2",clean));
        textList.add(new TextComponentTranslation("gtqtcore.parr",ParallelNum,ParallelLim));
        if (getInputFluidInventory() != null) {
            FluidStack STACK = getInputFluidInventory().drain(HydrochloricAcid.getFluid(Integer.MAX_VALUE), false);
            int liquidOxygenAmount = STACK == null ? 0 : STACK.amount;
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.ele.amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
        }
    }
    @Override
    public void addInformation(ItemStack stack,  World player, List<String> tooltip, boolean advanced) {
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
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityElectrobath(metaTileEntityId);
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
        Object eleTier = context.get("EleTiredStats");
        Object casingTier = context.get("ChemicalPlantCasingTiredStats");
        Object tubeTier = context.get("ChemicalPlantTubeTiredStats");
        this.casingTier = GTQTUtil.getOrDefault(() -> casingTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)casingTier).getIntTier(),
                0);

        this.eleTier = GTQTUtil.getOrDefault(() -> eleTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)eleTier).getIntTier(),
                0);

        this.tubeTier = GTQTUtil.getOrDefault(() -> tubeTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)tubeTier).getIntTier(),
                0);


        this.tier = Math.min(this.casingTier,this.tubeTier);

        this.writeCustomData(GTQTValue.UPDATE_TIER,buf -> buf.writeInt(this.tier));
        ParallelLim=2*clean;
        ParallelNum=ParallelLim;
    }

    public static int getMaxParallel(int clean) {
        if(clean>800000) return 16;
        if(clean>400000) return 8;
        if(clean>200000) return 4;
        return 1;
    }

    protected class ELELogic extends MultiblockRecipeLogic {

        @Override
        public int getParallelLimit() {
            return clean;
        }

        FluidStack COLD_STACK = HydrochloricAcid.getFluid(1);

        @Override
        public void update() {
            super.update();
            if (clean<=999900) {
                IMultipleTankHandler inputTank = getInputFluidInventory();
                if (COLD_STACK.isFluidStackIdentical(inputTank.drain(COLD_STACK, false))) {
                    inputTank.drain(COLD_STACK, true);
                    clean = clean + 100;
                }
            }
        }



        protected void updateRecipeProgress() {
            if (canRecipeProgress) {
                if(clean>=0) clean=clean-eu;
                if(clean>100000)
                {
                    if (++progressTime > maxProgressTime) {
                        completeRecipe();
                    }
                }
            }

        }

        public ELELogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity,true);
        }

        public long getMaxVoltage() {
            return Math.min(super.getMaxVoltage(), VA[tier]);
        }


        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = maxProgress*(100-eu)/100;
        }

        protected void modifyOverclockPost(int[] resultOverclock, @Nonnull IRecipePropertyStorage storage) {
            super.modifyOverclockPost(resultOverclock, storage);
            if (eleTier > 0) {
                resultOverclock[0] = (int)((double)resultOverclock[0] * (100 - (double)eleTier*5)/100);
                resultOverclock[0] = Math.max(1, resultOverclock[0]);
            }
        }
        
    }
}