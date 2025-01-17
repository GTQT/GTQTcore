package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMultiblockCasing;
import gregtech.common.blocks.BlockTurbineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneVariantBlock;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.IDrillHead;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTDateHelper;
import keqing.gtqtcore.api.utils.GTQTOreHelper;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;

import static gregtech.api.unification.material.Materials.Lubricant;

//大矿机
public class MetaTileEntityMiningDrill extends RecipeMapMultiblockController {
    private final ItemStackHandler containerInventory;
    int tier = 1;
    int casing;
    int tube;
    int drillBedTier;
    int drillTier;
    int random;
    int kind;

    public MetaTileEntityMiningDrill(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.MINING_DRILL_RECIPES);
        this.recipeMapWorkable = new IndustrialDrillWorkableHandler(this);
        this.containerInventory = new GTItemStackHandler(this, 1);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setTag("ContainerInventory", this.containerInventory.serializeNBT());
        data.setInteger("random", random);
        return super.writeToNBT(data);
    }


    public void readFromNBT(NBTTagCompound data) {
        this.containerInventory.deserializeNBT(data.getCompoundTag("ContainerInventory"));
        random = data.getInteger("random");
        super.readFromNBT(data);
    }


    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityMiningDrill(metaTileEntityId);
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("               ", "     DDDDD     ", "     DDDDD     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "   DDDDDDDDD   ", "   DDDDDDDDD   ", "    HH   HH    ", "    HH   HH    ", "    HH   HH    ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "  DDDDDDDDDDD  ", "  DDDDDDDDDDD  ", "    HH   HH    ", "    HH   HH    ", "    HH   HH    ", "     BB BB     ", "     BB BB     ", "     BB BB     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", " DDDDDDDDDDDDD ", " DDDDDDDDDDDDD ", "               ", "               ", "               ", "     BB BB     ", "     BB BB     ", "     BB BB     ", "     BB BB     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", " DDDDDDDDDDDDD ", " DDDDDDDDDDDDD ", " HH         HH ", " HH         HH ", " HH         HH ", "               ", "       E       ", "     BBEBB     ", "     AAEAA     ", "       E       ", "       E       ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "DDDDDD   DDDDDD", "DDDDDD   DDDDDD", " HH         HH ", " HH         HH ", " HH         HH ", "  BB       BB  ", "  BB   E   BB  ", "  BBBAAAAABBB  ", "   BAAACAAAB   ", "     GGCGG     ", "     BGEGB     ", "     BGAGB     ", "     B   B     ", "     B   B     ", "     BB  B     ", "     B B B     ", "     B  BB     ", "     B   B     ")
                .aisle("               ", "DDDDD  F  DDDDD", "DDDDD FFF DDDDD", "       A       ", "      BAB      ", "      BAB      ", "  BB  BAB  BB  ", "  BB  BAB  BB  ", "  BBBAAAAABBB  ", "   BAACCCAAB   ", "     GCCCG     ", "     GCCCG     ", "     GGCGG     ", "               ", "               ", "         B     ", "      ACA      ", "     BAAA      ", "               ")
                .aisle("       X       ", "DDDDD FFF DDDDD", "DDDDD FFF DDDDD", "      ACA      ", "      ACA      ", "      ACA      ", "      ACA      ", "    EEACAEE    ", "    EAACAAE    ", "    ECCCCCE    ", "    ECCCCCE    ", "    EECCCEE    ", "     AGGGA     ", "       C       ", "       C       ", "       C       ", "     BCCCB     ", "      AAA      ", "       A       ")
                .aisle("               ", "DDDDD  F  DDDDD", "DDDDD FFF DDDDD", "       A       ", "      BSB      ", "      BIB      ", "  BB  BAB  BB  ", "  BB  BAB  BB  ", "  BBBAAAAABBB  ", "   BAACCCAAB   ", "     GCCCG     ", "     GCCCG     ", "     GGCGG     ", "               ", "               ", "     B         ", "      ACA      ", "      AAAB     ", "               ")
                .aisle("               ", "DDDDDD   DDDDDD", "DDDDDD   DDDDDD", " HH         HH ", " HH         HH ", " HH         HH ", "  BB       BB  ", "  BB   E   BB  ", "  BBBAAAAABBB  ", "   BAAACAAAB   ", "     GGCGG     ", "     BGEGB     ", "     BGAGB     ", "     B   B     ", "     B   B     ", "     B  BB     ", "     B B B     ", "     BB  B     ", "     B   B     ")
                .aisle("               ", " DDDDDDDDDDDDD ", " DDDDDDDDDDDDD ", " HH         HH ", " HH         HH ", " HH         HH ", "               ", "       E       ", "     BBEBB     ", "     AAEAA     ", "       E       ", "       E       ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", " DDDDDDDDDDDDD ", " DDDDDDDDDDDDD ", "               ", "               ", "               ", "     BB BB     ", "     BB BB     ", "     BB BB     ", "     BB BB     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "  DDDDDDDDDDD  ", "  DDDDDDDDDDD  ", "    HH   HH    ", "    HH   HH    ", "    HH   HH    ", "     BB BB     ", "     BB BB     ", "     BB BB     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "   DDDDDDDDD   ", "   DDDDDDDDD   ", "    HH   HH    ", "    HH   HH    ", "    HH   HH    ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "     DDDDD     ", "     DDDDD     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .where('S', selfPredicate())
                .where('I', abilities(MultiblockAbility.IMPORT_ITEMS))
                .where('A', TiredTraceabilityPredicate.CP_CASING.get()
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2).setPreviewCount(2)))
                .where('B', states(MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel)))
                .where('H', states(MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel)))
                .where('C', states(MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STEEL_GEARBOX)))
                .where('D', states(MetaBlocks.STONE_BLOCKS.get(StoneVariantBlock.StoneVariant.SMOOTH).getState(StoneVariantBlock.StoneType.CONCRETE_LIGHT)))
                .where('E', TiredTraceabilityPredicate.CP_TUBE.get())
                .where('F', TiredTraceabilityPredicate.CP_DRI_CASING.get())
                .where('X', abilities(GTQTMultiblockAbility.DRILL_HEAD_MULTIBLOCK_ABILITY))
                .where('G', states(MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING)))
                .where(' ', any())
                .build();
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 180, 240);
        builder.dynamicLabel(28, 12, () -> "大型钻机平台", 0xFFFFFF);
        builder.widget(new SlotWidget(containerInventory, 0, 8, 8, false, true)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setTooltipText("输入槽位"));

        builder.image(4, 28, 172, 128, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(8, 32, this::addDisplayText, 16777215)).setMaxWidthLimit(180).setClickHandler(this::handleDisplayClick));


        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 8, 160);
        return builder;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (casing != tube)
            textList.add(new TextComponentTranslation("gtqtcore.equal", casing, tube));
        if (getInputFluidInventory() != null) {
            FluidStack LubricantStack = getInputFluidInventory().drain(Lubricant.getFluid(Integer.MAX_VALUE), false);
            int liquidOxygenAmount = LubricantStack == null ? 0 : LubricantStack.amount;
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.ma.amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
        }
        if (getDrillHeadHatch().isAvailable()) {
            textList.add(new TextComponentTranslation("钻头仓等级：%s 钻头等级：%s", getDrillHeadHatch().getTier(), getDrillHeadHatch().getDrillHeadTier()));
            textList.add(new TextComponentString("距离损坏: " + GTQTDateHelper.getTimeFromTicks(getDrillHeadHatch().getTotalTick() - getDrillHeadHatch().getWorkTime())));
            if (drillTier != getDrillHeadHatch().getTier())
                textList.add(new TextComponentTranslation("开采等级：%s", drillTier));
        } else textList.add(new TextComponentTranslation("钻头仓等级：%s", getDrillHeadHatch().getTier()));
        if (checkCard()) {
            textList.add(new TextComponentTranslation(GTQTOreHelper.getInfo(kind % 4)));
        }
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        switch (this.casing) {
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
    public void updateFormedValid() {
        if (!isStructureFormed()) return;
        if (!checkAvailable()) return;
        drillTier = Math.min(drillBedTier, getDrillHatchTier());
    }

    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("gtqt.tooltip.update")};
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }


    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GTQTValue.UPDATE_TIER22) {
            this.casing = buf.readInt();
        }
        if (dataId == GTQTValue.REQUIRE_DATA_UPDATE22) {
            this.writeCustomData(GTQTValue.UPDATE_TIER22, buf1 -> buf1.writeInt(this.casing));
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.casing);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.casing = buf.readInt();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object drill = context.get("DriTieredStats");
        Object casing = context.get("ChemicalPlantCasingTieredStats");
        Object tubeTier = context.get("ChemicalPlantTubeTieredStats");
        this.casing = GTQTUtil.getOrDefault(() -> casing instanceof WrappedIntTired,
                () -> ((WrappedIntTired) casing).getIntTier(),
                0);

        this.drillBedTier = GTQTUtil.getOrDefault(() -> drill instanceof WrappedIntTired,
                () -> ((WrappedIntTired) drill).getIntTier(),
                0);

        this.tube = GTQTUtil.getOrDefault(() -> tubeTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) tubeTier).getIntTier(),
                0);


        this.tier = Math.min(this.casing, this.tube);

        random = 0;
        if ((this.getPos().getX() / 64) % 2 == 0) random += 2;
        if ((this.getPos().getZ() / 64) % 2 == 0) random += 2;
        else random += 1;

        this.writeCustomData(GTQTValue.UPDATE_TIER22, buf -> buf.writeInt(this.casing));
    }

    @Override
    public boolean isMultiblockPartWeatherResistant(@Nonnull IMultiblockPart part) {
        return true;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("无中生有", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.mdi.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.mdi.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.mdi.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.machine.mdi.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.machine.parallel.pow.custom", 2,"Math.min(tier, drillTier)",128));
        tooltip.add(I18n.format("gtqtcore.machine.progress_time","maxProgress * (100 - drillTier * 5) / 100.0"));
    }


    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (!checkCard()) textList.add(new TextComponentTranslation("缺少参数或者参数不符合！！"));
    }

    public boolean checkCard() {
        ItemStack item = containerInventory.getStackInSlot(0);
        if (item.getItem() == GTQTMetaItems.GTQT_META_ITEM && item.getMetadata() == GTQTMetaItems.POS_ORE_CARD.getMetaValue()) {
            NBTTagCompound compound = item.getTagCompound();
            if (compound != null && compound.hasKey("Kind")) {
                if (compound.getInteger("Kind") == random + this.getWorld().provider.getDimension() * 4) {
                    kind = compound.getInteger("Kind");
                    return true;
                }
            }
        }
        return false;
    }

    public IDrillHead getDrillHeadHatch() {
        List<IDrillHead> abilities = getAbilities(GTQTMultiblockAbility.DRILL_HEAD_MULTIBLOCK_ABILITY);
        if (abilities.isEmpty())
            return null;
        return abilities.get(0);
    }

    public int getDrillHatchTier() {
        return getDrillHeadHatch().getTier();
    }

    public boolean checkAvailable() {
        return getDrillHeadHatch().isAvailable();
    }

    public void setWork(boolean work) {
        getDrillHeadHatch().setWork(work);
    }

    protected class IndustrialDrillWorkableHandler extends MultiblockRecipeLogic {

        private final MetaTileEntityMiningDrill metaTileEntityMiningDrill;
        private final FluidStack LUBRICANT_STACK = Lubricant.getFluid(1);

        public IndustrialDrillWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
            this.metaTileEntityMiningDrill = (MetaTileEntityMiningDrill) tileEntity;
        }

        @Override
        public int getParallelLimit() {
            return Math.min((int) Math.pow(2, Math.min(tier, drillTier)), 128);
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = (int) (maxProgress * (100 - drillTier * 5) / 100.0);
        }

        protected void updateRecipeProgress() {
            IMultipleTankHandler inputTank = metaTileEntityMiningDrill.getInputFluidInventory();
            if (checkCard() && this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true) && LUBRICANT_STACK.isFluidStackIdentical(inputTank.drain(LUBRICANT_STACK, false))) {
                this.drawEnergy(this.recipeEUt, false);
                LUBRICANT_STACK.isFluidStackIdentical(inputTank.drain(LUBRICANT_STACK, true));
                setWork(true);
                if (++this.progressTime > this.maxProgressTime) {
                    this.completeRecipe();
                    setWork(false);
                }
            }
        }
    }
}