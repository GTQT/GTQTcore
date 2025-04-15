package keqing.gtqtcore.api.recipes.builder;


import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.properties.impl.PrimitiveProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.ValidationResult;
import keqing.gtqtcore.api.recipes.properties.EvaporationEnergyProperty;

public class EvaporationPoolRecipeBuilder  extends RecipeBuilder<EvaporationPoolRecipeBuilder> {
    long eutStorage = -1; //according to mtbo what is done with eut will change at some point, so I am just grabbing it when the method is called instead of trusting its later availability

    public EvaporationPoolRecipeBuilder() {

    }

    public EvaporationPoolRecipeBuilder(EvaporationPoolRecipeBuilder other) {
        super(other);
        this.eutStorage = other.eutStorage;
    }

    @Override
    public EvaporationPoolRecipeBuilder copy() {
        return new EvaporationPoolRecipeBuilder(this);
    }

    public EvaporationPoolRecipeBuilder Jt(int Jt) {
        if(Jt <= 0) {

            recipeStatus = EnumValidationResult.INVALID;
        }

        eutStorage = Jt;

        this.applyProperty(EvaporationEnergyProperty.getInstance(), Jt);
        return this;
    }

    @Override
    public boolean applyPropertyCT(String key,Object value) {
        if (key.equals(EvaporationEnergyProperty.KEY)) {
            this.Jt((int) value);
            return true;
        }

        return super.applyPropertyCT(key, value);
    }

    //store provided EUt for later calculations for the sake of supporting old recipes
    @Override
    public EvaporationPoolRecipeBuilder EUt(long EUt) {
        eutStorage = EUt * 10;
        return super.EUt(EUt);
    }

    @Override
    public ValidationResult<Recipe> build() {
        if (this.recipePropertyStorage == null || !this.recipePropertyStorage.contains(EvaporationEnergyProperty.getInstance())) {
            if (eutStorage <= 0) {
                //use latent heat of vaporization for water w/ 55mol/L in case of recipes with no energy specified, with 40800 / 10000 to give reasonable numbers
                this.Jt(408 * 55 * getFluidInputs().get(0).getAmount() / (100 * (getDuration() == 0 ? 200 : getDuration())));
            } else {
                //calculate joules needed per tick from EUt -> J/t and use eutStorage as variable, as it will no longer be needed
                this.Jt((int) (eutStorage *10));
            }
        }

        this.EUt(-1);
        this.applyProperty(PrimitiveProperty.getInstance(), true);
        return super.build();
    }
}