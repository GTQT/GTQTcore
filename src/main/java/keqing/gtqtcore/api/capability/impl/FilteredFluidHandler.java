package keqing.gtqtcore.api.capability.impl;


import gregtech.api.capability.IFilter;
import gregtech.api.capability.IFilteredFluidContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Predicate;

public class FilteredFluidHandler extends FluidTank implements IFilteredFluidContainer {

    private IFilter<FluidStack> filter;

    public FilteredFluidHandler(int capacity) {
        super(capacity);
    }

    public FilteredFluidHandler(@Nullable FluidStack fluidStack, int capacity) {
        super(fluidStack, capacity);
    }

    public FilteredFluidHandler(Fluid fluid, int amount, int capacity) {
        super(fluid, amount, capacity);
    }


    public IFilter<FluidStack> getFilter() {
        return this.filter;
    }

    @Nonnull
    public FilteredFluidHandler setFilter(@Nullable IFilter<FluidStack> filter) {
        this.filter = filter;
        return this;
    }


    @Deprecated
    public FilteredFluidHandler setFillPredicate(Predicate<FluidStack> predicate) {
        Objects.requireNonNull(predicate);
        return this.setFilter(predicate::test);
    }

    public boolean canFillFluidType(FluidStack fluid) {
        return this.canFill() && (this.filter == null || this.filter.test(fluid));
    }
}
