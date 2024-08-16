package keqing.gtqtcore.common.metatileentities.multi.multiblock;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.GTTransferUtils;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import java.util.ArrayList;
import java.util.List;

public class MetaTileEntityStewStoolStove extends MultiblockWithDisplayBase {
    private long currentHeat = 0;
    private long currentRecipeHeat = 0;
    private boolean outputMode = false;
    protected IItemHandlerModifiable inputInventory;
    protected IItemHandlerModifiable outputInventory;
    protected IMultipleTankHandler inputFluidInventory;
    protected IMultipleTankHandler outputFluidInventory;

    public MetaTileEntityStewStoolStove(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.fluidInventory = new FluidTankList(false, makeFluidTanks(25));
        this.itemInventory = new NotifiableItemStackHandler(this, 25, this, false);
        this.exportFluids = (FluidTankList) fluidInventory;
        this.importFluids = (FluidTankList) fluidInventory;
        this.exportItems = (IItemHandlerModifiable) itemInventory;
        this.importItems = new ItemHandlerList(makeGhostCircuitList());


    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.inputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.inputFluidInventory = new FluidTankList(false, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.outputFluidInventory = new FluidTankList(false, getAbilities(MultiblockAbility.EXPORT_FLUIDS));
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    @Override
    public TraceabilityPredicate autoAbilities() {
        TraceabilityPredicate predicate = super.autoAbilities();
        return predicate.or(abilities(MultiblockAbility.IMPORT_ITEMS))
                .or(abilities(MultiblockAbility.IMPORT_FLUIDS))
                .or(abilities(MultiblockAbility.EXPORT_ITEMS))
                .or(abilities(MultiblockAbility.EXPORT_FLUIDS));
    }

    private List<IItemHandler> makeGhostCircuitList() {
        List<IItemHandler> itemHandlerList = new ArrayList<>();
        itemHandlerList.add(this.itemInventory);
        if (MetaItems.INTEGRATED_CIRCUIT != null)
            for (int i = 0; i <= 32; i++) {
                GhostCircuitItemStackHandler handler = new GhostCircuitItemStackHandler(this);
                handler.setCircuitValue(i);
                itemHandlerList.add(handler);
            }
        return itemHandlerList;
    }

    private List<FluidTank> makeFluidTanks(int length) {
        List<FluidTank> fluidTankList = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            fluidTankList.add(new NotifiableFluidTank(32000, this, false));
        }
        return fluidTankList;
    }

    @Override
    protected void updateFormedValid() {
        boolean did = false;
        if (!outputMode) {
            GTTransferUtils.moveInventoryItems(this.inputInventory, this.itemInventory);
            GTTransferUtils.transferFluids(this.inputFluidInventory, this.fluidInventory);
            Recipe fuelCellRecipe = GTQTcoreRecipeMaps.FUEL_CELL.findRecipe(GTValues.V[GTValues.MAX], this.importItems, this.importFluids);
            if (fuelCellRecipe != null && fuelCellRecipe.matches(true, this.importItems, this.importFluids)) {
                this.currentHeat += (long) fuelCellRecipe.getEUt() * fuelCellRecipe.getDuration();
                GTTransferUtils.addFluidsToFluidHandler(this.exportFluids, false, fuelCellRecipe.getFluidOutputs());
                GTTransferUtils.addItemsToItemHandler(this.importItems, false, fuelCellRecipe.getOutputs());
                did = true;
            }
            Recipe chemicalReactorRecipe = RecipeMaps.CHEMICAL_RECIPES.findRecipe(this.currentHeat, this.importItems, this.importFluids);
            if (chemicalReactorRecipe != null && chemicalReactorRecipe.matches(false, this.importItems, this.importFluids)) {
                currentRecipeHeat = (long) chemicalReactorRecipe.getEUt() * chemicalReactorRecipe.getDuration();
                if (currentRecipeHeat <= this.currentHeat) {
                    this.currentHeat -= currentRecipeHeat;
                    this.currentRecipeHeat = 0;
                    chemicalReactorRecipe.matches(true, this.importItems, this.importFluids);
                    GTTransferUtils.addFluidsToFluidHandler(this.exportFluids, false, chemicalReactorRecipe.getFluidOutputs());
                    GTTransferUtils.addItemsToItemHandler(this.importItems, false, chemicalReactorRecipe.getOutputs());
                    did = true;
                }
            }

        } else {
            GTTransferUtils.moveInventoryItems(this.itemInventory, this.outputInventory);
            GTTransferUtils.transferFluids(this.fluidInventory, this.outputFluidInventory);
        }
        if (!did && this.currentHeat > 0 && this.getOffsetTimer() % 20 == 0) {
            this.currentHeat -= Math.max(this.currentHeat / 100, 1);
        }
    }

    @Override
    protected  BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXX", "XYYYX", "XXXXX", "XXXXX")
                .aisleRepeatable(3, 14, "XXXXX", "Y###Y", "X###X", "X###X")
                .aisle("XXXXX", "XY@YX", "XXXXX", "XXXXX")
                .where('X', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(6).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(6).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(6).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(6).setPreviewCount(1)))
                .where('Y', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS))
                        .or(this.autoAbilities()))
                .where('#', air())
                .where('@', selfPredicate())
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.PRIMITIVE_BRICKS;
    }
    @SideOnly(Side.CLIENT)
    protected  ICubeRenderer getFrontOverlay() {
        return Textures.PRIMITIVE_PUMP_OVERLAY;
    }
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), true,
                true);
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityStewStoolStove(this.metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("Current heat:%s/%s", currentHeat, currentRecipeHeat));
        textList.add(new TextComponentTranslation("Output mode:%s", outputMode));
        for (int slot = 0; slot < itemInventory.getSlots(); slot++) {
            ItemStack stack = itemInventory.getStackInSlot(slot);
            if (!stack.isEmpty())
                textList.add(new TextComponentTranslation("%sx%s", stack.getCount(), stack.getDisplayName()));
        }
        for (IFluidTankProperties properties : fluidInventory.getTankProperties()) {
            //ItemStack stack=itemInventory.getStackInSlot(slot);
            FluidStack stack = properties.getContents();
            if (stack != null && stack.amount != 0) {
                textList.add(new TextComponentTranslation("%sx%s", stack.amount, stack.getLocalizedName()));
            }
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data = super.writeToNBT(data);
        data.setLong("currentHeat", currentHeat);
        data.setLong("currentRecipeHeat", currentRecipeHeat);
        data.setBoolean("outputMode", outputMode);
        return data;
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        currentHeat = data.getLong("currentHeat");
        currentRecipeHeat = data.getLong("currentRecipeHeat");
        outputMode = data.getBoolean("outputMode");
    }

    @Override
    public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if (!getWorld().isRemote) {
            outputMode = !outputMode;
        }
        return true;
    }
}