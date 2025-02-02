package keqing.gtqtcore.api.io.advancement.impl;

import keqing.gtqtcore.api.io.advancement.IAdvancementCriterion;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractCriterion implements IAdvancementCriterion {

    private ResourceLocation id = new ResourceLocation("MISSING");

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void setId(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AbstractCriterion{id=" + this.id + "}";
    }

}
