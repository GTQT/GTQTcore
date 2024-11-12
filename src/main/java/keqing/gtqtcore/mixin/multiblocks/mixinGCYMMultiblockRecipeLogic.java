package keqing.gtqtcore.mixin.multiblocks;

import gregicality.multiblocks.api.capability.IParallelMultiblock;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import org.spongepowered.asm.mixin.Mixin;


import gregicality.multiblocks.api.capability.impl.GCYMMultiblockRecipeLogic;

@Mixin(GCYMMultiblockRecipeLogic.class)
public abstract class mixinGCYMMultiblockRecipeLogic  extends MultiblockRecipeLogic {

    public mixinGCYMMultiblockRecipeLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity);
    }

    public int getParallelLimit() {
        return ((IParallelMultiblock)this.metaTileEntity).isParallel() ? ((IParallelMultiblock)this.metaTileEntity).getMaxParallel() : 1;
    }
    @Override
   public void setMaxProgress(int maxProgress)
    {
        if(((IParallelMultiblock)this.metaTileEntity).getMaxParallel()<=16)
            this.maxProgressTime = maxProgress;
        else if (((IParallelMultiblock) this.metaTileEntity).getMaxParallel() <= 64)
                this.maxProgressTime = (int) (maxProgress * 2.0 / ((IParallelMultiblock) this.metaTileEntity).getMaxParallel());
        else if (((IParallelMultiblock) this.metaTileEntity).getMaxParallel() <= 256)
                this.maxProgressTime = (int) (maxProgress * 16.0 / ((IParallelMultiblock) this.metaTileEntity).getMaxParallel());
        else if (((IParallelMultiblock) this.metaTileEntity).getMaxParallel() <= 1024)
                this.maxProgressTime = (int) (maxProgress * 128.0 / ((IParallelMultiblock) this.metaTileEntity).getMaxParallel());
        else this.maxProgressTime = maxProgress;

    }
}
