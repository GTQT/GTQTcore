package keqing.gtqtcore.common.metatileentities.single.electric;

import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.ICubeRenderer;
import net.minecraft.util.ResourceLocation;

public class MetaTileEntityParticleAcceleratorIO extends SimpleMachineMetaTileEntity {
    public int circuit;
    public int tier;
    public MetaTileEntityParticleAcceleratorIO(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, ICubeRenderer renderer, int tier, boolean hasFrontFacing) {
        super(metaTileEntityId, recipeMap, renderer, tier, hasFrontFacing);
        this.tier=tier;
    }
    public void update() {
        super.update();
        circuit=circuitInventory.getCircuitValue();
    }
}
