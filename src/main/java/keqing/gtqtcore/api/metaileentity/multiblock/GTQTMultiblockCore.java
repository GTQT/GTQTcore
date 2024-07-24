package keqing.gtqtcore.api.metaileentity.multiblock;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.common.items.MetaItems;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.MetaTileEntityStewStoolStove;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBaseWithControl;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.ArrayList;
import java.util.List;

public abstract class GTQTMultiblockCore extends MetaTileEntityBaseWithControl {
    public long currentEU = 0;
    public long currentRecipeEU = 0;
    public GTQTMultiblockCore(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.fluidInventory = new FluidTankList(false, makeFluidTanks(25));
        this.itemInventory = new NotifiableItemStackHandler(this, 25, this, false);
        this.exportFluids = (FluidTankList) fluidInventory;
        this.importFluids = (FluidTankList) fluidInventory;
        this.exportItems = (IItemHandlerModifiable) itemInventory;
        //this.importItems = new ItemHandlerList(makeGhostCircuitList());

    }

    @Override
    protected void updateFormedValid() {
        if(this.energyContainer.getEnergyStored()>0&&currentEU<this.energyContainer.getEnergyCapacity()) {
            currentEU = this.energyContainer.getEnergyStored();
            this.energyContainer.removeEnergy(currentEU);
        }


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
/*
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
*/
    private List<FluidTank> makeFluidTanks(int length) {
        List<FluidTank> fluidTankList = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            fluidTankList.add(new NotifiableFluidTank(32000, this, false));
        }
        return fluidTankList;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), true,
                true);
    }
}
