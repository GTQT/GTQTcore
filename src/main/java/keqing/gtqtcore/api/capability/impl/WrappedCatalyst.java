package keqing.gtqtcore.api.capability.impl;

import keqing.gtqtcore.api.capability.item.ICatalyst;

import java.util.Optional;

public abstract class WrappedCatalyst implements ICatalyst {

    ICatalyst inner;

    public WrappedCatalyst(ICatalyst inner){
        this.inner = inner;
    }

    public void update(ICatalyst newCatalyst){
        this.inner = newCatalyst;
    }

    @Override
    public Optional<String> getName() {
        return inner.getName();
    }

    public abstract void consumeCatalyst(int amount);
}