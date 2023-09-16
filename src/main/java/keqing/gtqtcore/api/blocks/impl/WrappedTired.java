package keqing.gtqtcore.api.blocks.impl;

import keqing.gtqtcore.api.blocks.ITired;
import net.minecraft.util.IStringSerializable;

public class WrappedTired implements ITired {

    private final IStringSerializable inner;

    public WrappedTired(IStringSerializable inner) {
        this.inner = inner;
    }

    @Override
    public String getName() {
        return inner.getName();
    }
}