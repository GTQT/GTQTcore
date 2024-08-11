package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.overwrite;

import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.api.utils.GTQTOreHelper;
import gregtech.api.capability.IGhostSlotConfigurable;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.GhostCircuitItemStackHandler;
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
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.*;
import gregtech.common.blocks.StoneVariantBlock;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.multi.multiblockpart.appeng.MetaTileEntityMEInputBus;
import gregtech.common.pipelike.cable.tile.TileEntityCable;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.MDProperties;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBlazingBlastFurnace;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import gregtech.api.unification.material.Materials;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.Lubricant;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Pyrotheum;

//大矿机
public class MetaTileEntityMiningDrill extends RecipeMapMultiblockController {
    int tier=1;
    int casing;
    int tube;
    int drilla;
    int naijiu;
    int maxCircuit=10;
    int worktime;
    int random;
    boolean cycle;
    int kind;

    public MetaTileEntityMiningDrill(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.MINING_DRILL_RECIPES );
        this.recipeMapWorkable = new IndustrialDrillWorkableHandler(this);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setDouble("naijiu", naijiu);
        data.setBoolean("cycle",cycle);
        data.setInteger("worktime",worktime);
        data.setInteger("random",random);
        return super.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        naijiu = data.getInteger("naijiu");
        cycle=data.getBoolean("cycle");
        worktime=data.getInteger("worktime");
        random=data.getInteger("random");
        super.readFromNBT(data);
    }


    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityMiningDrill(metaTileEntityId);
    }
    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 18, 18, "", this::clear)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("普适模式"));

        return group;
    }

    private void clear(Widget.ClickData clickData) {
        cycle=!cycle;
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
                .aisle("       F       ", "DDDDD FFF DDDDD", "DDDDD FFF DDDDD", "      ACA      ", "      ACA      ", "      ACA      ", "      ACA      ", "    EEACAEE    ", "    EAACAAE    ", "    ECCCCCE    ", "    ECCCCCE    ", "    EECCCEE    ", "     AGGGA     ", "       C       ", "       C       ", "       C       ", "     BCCCB     ", "      AAA      ", "       A       ")
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
                .where('G', states(MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING)))
                .where(' ', any())
                .build();
    }


    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if(casing!=tube)
            textList.add(new TextComponentTranslation("gtqtcore.equal", casing,tube));
        if (getInputFluidInventory() != null) {
            FluidStack LubricantStack = getInputFluidInventory().drain(Lubricant.getFluid(Integer.MAX_VALUE), false);
            int liquidOxygenAmount = LubricantStack == null ? 0 : LubricantStack.amount;
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.ma.amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
        }
        textList.add(new TextComponentTranslation("gtqtcore.md",tier, drilla, naijiu));
        if(checkCard())
        {
            if (!cycle) {
                textList.add(new TextComponentTranslation(GTQTOreHelper.getInfo(kind)));
            } else {
                textList.add(new TextComponentTranslation(GTQTOreHelper.getInfo(kind % 4)));
            }
        }
        textList.add(new TextComponentTranslation("普适模式：%s",cycle));
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
    public String[] getDescription() {
        return new String[]{I18n.format("gtqt.tooltip.update")};
    }
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }


    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        //this.inputInventory = new NotifiableItemStackHandler(this,1, this, false);
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == GTQTValue.UPDATE_TIER22){
            this.casing = buf.readInt();
        }
        if(dataId == GTQTValue.REQUIRE_DATA_UPDATE22){
            this.writeCustomData(GTQTValue.UPDATE_TIER22,buf1 -> buf1.writeInt(this.casing));
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
                () -> ((WrappedIntTired)casing).getIntTier(),
                0);

        this.drilla = GTQTUtil.getOrDefault(() -> drill instanceof WrappedIntTired,
                () -> ((WrappedIntTired)drill).getIntTier(),
                0);

        this.tube = GTQTUtil.getOrDefault(() -> tubeTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)tubeTier).getIntTier(),
                0);


        this.tier = Math.min(this.casing,this.tube);

        random=0;
        if((this.getPos().getX()/64)%2==0)random+=2;
        if((this.getPos().getZ()/64)%2==0)random+=2;
        else random+=1;

        this.writeCustomData(GTQTValue.UPDATE_TIER22, buf -> buf.writeInt(this.casing));
        naijiu=drilla*15*15000;

    }
    @Override
    public boolean isMultiblockPartWeatherResistant(@Nonnull IMultiblockPart part) {
        return true;
    }
    @Override
    public void invalidateStructure() {
       super.invalidateStructure();
    }

    @Override
    public boolean canBeDistinct() {
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("无中生有", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.mdi.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.mdi.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.mdi.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.machine.mdi.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.machine.mdi.tooltip.5"));
        tooltip.add(I18n.format("gtqtcore.machine.mdi.tooltip.6"));
    }


    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if(!checkCard()) textList.add(new TextComponentTranslation("缺少参数或者参数不符合！！"));
    }
    public boolean checkCard() {
        var slots = this.getInputInventory().getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack item = this.getInputInventory().getStackInSlot(i);
            if (item.getItem() == GTQTMetaItems.GTQT_META_ITEM && item.getMetadata() == GTQTMetaItems.POS_ORE_CARD.getMetaValue()) {
                NBTTagCompound compound = item.getTagCompound();
                if (compound != null && compound.hasKey("Kind")) {
                    if(cycle)
                    {
                        if (compound.getInteger("Kind") == random) {
                            kind = compound.getInteger("Kind");
                            this.getInputInventory().extractItem(i, 1, true);
                            return true;
                        }
                    }

                    if (compound.getInteger("Kind") == random + this.getWorld().provider.getDimension() * 4) {
                        kind = compound.getInteger("Kind");
                        this.getInputInventory().extractItem(i, 1, true);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    protected class IndustrialDrillWorkableHandler extends MultiblockRecipeLogic {

        private final MetaTileEntityMiningDrill combustionEngine;
        public IndustrialDrillWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
            this.combustionEngine = (MetaTileEntityMiningDrill) tileEntity;
        }

        int time;
        @Override
        public void update() {

            time++;
            if(time==4000)
            {
            if(worktime<16)worktime++;
            }
            final int xDir = this.metaTileEntity.getFrontFacing().getOpposite().getXOffset();
            final int zDir = this.metaTileEntity.getFrontFacing().getOpposite().getZOffset();

            if(naijiu<-1) {
                for (int h = -4; h <-1; h++) for (int i=-1;i<=1;i++) for (int j=-1;j<=1;j++)
                {
                    BlockPos waterCheckPos = this.metaTileEntity.getPos().add(xDir+i , h, zDir+j );
                    this.metaTileEntity.getWorld().setBlockState(
                            waterCheckPos,
                            Blocks.AIR.getDefaultState());
                }
                naijiu=0;
            }


        }

        @Override
        public int getParallelLimit() {
            return (int) Math.pow(2, Math.min(tier,drilla*2)-1)+worktime;
        }
        private final FluidStack LUBRICANT_STACK = Lubricant.getFluid(1);
        @Override
        public void setMaxProgress(int maxProgress)
        {
            this.maxProgressTime = maxProgress/8*drilla;
        }
        protected void updateRecipeProgress() {
            IMultipleTankHandler inputTank = combustionEngine.getInputFluidInventory();
            if (checkCard()&&this.canRecipeProgress && this.drawEnergy(this.recipeEUt, false)&&LUBRICANT_STACK.isFluidStackIdentical(inputTank.drain(LUBRICANT_STACK, true))) {
                naijiu-=(5-drilla);
                if (++this.progressTime > this.maxProgressTime) {
                    this.completeRecipe();
                }
            }
        }
    }
}