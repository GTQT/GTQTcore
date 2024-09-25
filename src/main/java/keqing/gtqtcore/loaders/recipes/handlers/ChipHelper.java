package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.GTRecipeHandler;
import keqing.gtqtcore.api.metaileentity.multiblock.GCYLCleanroomType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.common.items.MetaItems.NEUTRONIUM_BOULE;
import static gregtech.common.items.MetaItems.NEUTRONIUM_WAFER;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class ChipHelper {
    static int SECOND=20;
    static int MINUTE=1200;
    public static void init() {
        soc();
        pic();
        wafer();
    }

    private static void wafer() {
        cutter(96,EUROPIUM_BOULE,EUROPIUM_WAFER,5,CleanroomType.CLEANROOM);
        cutter(128,AMERICIUM_BOULE,AMERICIUM_WAFER,6,CleanroomType.CLEANROOM);
        cutter(160,DUBNIUM_BOULE,DUBNIUM_WAFER,7,CleanroomType.CLEANROOM);
        //接中子素
        //  Delete Neutronium Boule -> Wafer recipes
        GTRecipeHandler.removeRecipesByInputs(CUTTER_RECIPES,
                new ItemStack[]{NEUTRONIUM_BOULE.getStackForm()},
                new FluidStack[]{Water.getFluid(1000)});

        GTRecipeHandler.removeRecipesByInputs(CUTTER_RECIPES,
                new ItemStack[]{NEUTRONIUM_BOULE.getStackForm()},
                new FluidStack[]{DistilledWater.getFluid(750)});

        GTRecipeHandler.removeRecipesByInputs(CUTTER_RECIPES,
                new ItemStack[]{NEUTRONIUM_BOULE.getStackForm()},
                new FluidStack[]{Lubricant.getFluid(250)});
        cutter(192,NEUTRONIUM_BOULE,NEUTRONIUM_WAFER,8,CleanroomType.STERILE_CLEANROOM);
    }

    private static void pic() {
        cutter(NANO_POWER_IC_WAFER,NANO_POWER_IC,9,CleanroomType.STERILE_CLEANROOM);
        cutter(PICO_POWER_IC_WAFER,PICO_POWER_IC,10,CleanroomType.STERILE_CLEANROOM);
        cutter(FEMTO_POWER_IC_WAFER,FEMTO_POWER_IC,11, GCYLCleanroomType.ISO3);
        cutter(ATTO_PIC_WAFER,ATTO_PIC_CHIP,12,GCYLCleanroomType.ISO2);
        cutter(ZEPTO_PIC_WAFER,ZEPTO_PIC_CHIP,13,GCYLCleanroomType.ISO1);

        NanoPIC();
        PicoPIC();
        FemtoPIC();
    }

    private static void soc() {
        cutter(UHASOC_WAFER,UHASOC_CHIP,9,CleanroomType.STERILE_CLEANROOM);
    }
    private static void NanoPIC() {

        //  Nb2O3 + 10HCl -> 2NbCl5 + 3H2O + 4H
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, NiobiumPentoxide, 5)
                .fluidInputs(HydrochloricAcid.getFluid(10000))
                .output(dust, NiobiumPentachloride, 12)
                .fluidOutputs(Hydrogen.getFluid(4000))
                .fluidOutputs(Steam.getFluid(3000))
                .EUt(VA[EV])
                .duration(20 * SECOND)
                .blastFurnaceTemp(9000)
                .buildAndRegister();

        //  NbCl5 + LiH + 2H2O2 -> LiNbO4 + 5HCl
        BLAST_RECIPES.recipeBuilder()
                .input(dust, NiobiumPentachloride, 6)
                .input(dust, LithiumHydride, 2)
                .notConsumable(dust, Hafnium)
                .fluidInputs(HydrogenPeroxide.getFluid(2000))
                .output(ingotHot, LithiumNiobate, 6)
                .fluidOutputs(HydrochloricAcid.getFluid(5000))
                .EUt(VA[HV])
                .duration(16 * SECOND)
                .blastFurnaceTemp(4500)
                .buildAndRegister();

        //  Lithium Niobate Lens
        LATHE_RECIPES.recipeBuilder()
                .input(plate, LithiumNiobate)
                .output(lens, LithiumNiobate)
                .output(dustSmall, LithiumNiobate)
                .EUt(VA[MV])
                .duration(MINUTE)
                .buildAndRegister();


    }

    private static void PicoPIC() {

        //  Lu2O3 + Tm2O3 + 3Y2O3 + 30HCl -> (LuCl3)2(TmCl3)2(YCl3)6(H2O)15
        MIXER_RECIPES.recipeBuilder()
                .input(dust, LutetiumOxide)
                .input(dust, ThuliumOxide)
                .input(dust, YttriumOxide, 3)
                .fluidInputs(HydrochloricAcid.getFluid(30000))
                .fluidOutputs(LuTmYChloridesSolution.getFluid(30000))
                .EUt(VA[EV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  Na3VO4 + 2CH4N2O + (LuCl3)2(TmCl3)2(YCl3)6(H2O)15 -> Lu/Tm:YVO? + 0.9Cl
        CVD_RECIPES.recipeBuilder()
                .input(dust, SodiumVanadate, 8)
                .input(dust, Carbamide, 16)
                .fluidInputs(LuTmYChloridesSolution.getFluid(1000))
                .output(dust, LuTmDopedYttriumVanadateDeposition)
                .fluidOutputs(Chlorine.getFluid(900))
                .EUt(VA[ZPM])
                .duration(6 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Lu/Tm:YVO? + C2H6O -> Lu/Tm:YVO + (NH4)2CO3 + C3H6
        DRYER_RECIPES.recipeBuilder()
                .input(dust, LuTmDopedYttriumVanadateDeposition)
                .fluidInputs(Ethanol.getFluid(1000))
                .output(dust, LuTmYVO)
                .output(dust, AmmoniumCarbonate, 14)
                .fluidOutputs(Propene.getFluid(1000))
                .EUt(VA[IV])
                .duration(10 * SECOND)
                .buildAndRegister();
    }

    private static void FemtoPIC() {

        //  LiH + HF -> LiF + 2H
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, LithiumHydride, 2)
                .fluidInputs(HydrofluoricAcid.getFluid(1000))
                .output(dust, LithiumFluoride, 2)
                .fluidOutputs(Hydrogen.getFluid(2000))
                .EUt(VA[MV])
                .duration(6 * SECOND)
                .buildAndRegister();

        //  SiF4 + 2HF -> H2SiF6
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(SiliconTetrachloride.getFluid(1000))
                .fluidInputs(HydrofluoricAcid.getFluid(2000))
                .fluidOutputs(HexafluorosilicicAcid.getFluid(1000))
                .EUt(VA[HV])
                .duration(5 * SECOND)
                .buildAndRegister();

        //  H2SiF6 + 6NH3 + 2H2O -> SiO2 + 6NH4F
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(HexafluorosilicicAcid.getFluid(1000))
                .fluidInputs(Ammonia.getFluid(6000))
                .fluidInputs(Water.getFluid(2000))
                .circuitMeta(9)
                .output(dust, SiliconDioxide, 3)
                .fluidOutputs(AmmoniumFluoride.getFluid(6000))
                .EUt(VA[HV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  2NH4F -> NH4HF2 + NH3
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(AmmoniumFluoride.getFluid(2000))
                .output(dust, AmmoniumDifluoride, 8)
                .fluidOutputs(Ammonia.getFluid(1000))
                .EUt(VA[MV])
                .duration(17 * SECOND)
                .buildAndRegister();

        //  Pr2O3 + Ho2O3 + 3Y2O3 -> (Pr(NO3)3)2(Ho(NO3)3)2(Y(NO3)3)6(H2O)15
        MIXER_RECIPES.recipeBuilder()
                .input(dust, PraseodymiumOxide, 5)
                .input(dust, HolmiumOxide, 5)
                .input(dust, YttriumOxide, 15)
                .fluidInputs(NitricAcid.getFluid(30000))
                .fluidOutputs(PrHoYNitratesSolution.getFluid(30000))
                .EUt(VA[EV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  Be + LiF + 2NH4HF2 + 2(Pr(NO3)3)2(Ho(NO3)3)2(Y(NO3)3)6(H2O)15 + CO -> Pr/Ho:YLF + BeF2 + 2NH4NO3 + 2HF + CO2
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Beryllium)
                .input(dust, LithiumFluoride, 2)
                .input(dust, AmmoniumDifluoride, 16)
                .notConsumable(EthylenediaminetetraaceticAcid.getFluid(1))
                .fluidInputs(PrHoYNitratesSolution.getFluid(2000))
                .fluidInputs(CarbonMonoxide.getFluid(1000))
                .output(dust, PrHoYLF, 2)
                .output(dust, BerylliumDifluoride, 3)
                .fluidOutputs(AmmoniumNitrate.getFluid(2000))
                .fluidOutputs(HydrofluoricAcid.getFluid(2000))
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .EUt(VA[IV])
                .duration(10 * SECOND)
                .buildAndRegister();
    }
    public static void cutter(int amount,MetaItem<?>.MetaValueItem wafer, MetaItem<?>.MetaValueItem chip, int tier,CleanroomType a)
    {
        if(amount==96) {
            CUTTER_RECIPES.recipeBuilder()
                    .input(wafer)
                    .output(chip, 64)
                    .output(chip, 32)
                    .EUt(VA[tier])
                    .duration(90 * 20)
                    .cleanroom(a)
                    .buildAndRegister();
        }
        else if(amount==128)
        {
            CUTTER_RECIPES.recipeBuilder()
                    .input(wafer)
                    .output(chip, 64)
                    .output(chip, 64)
                    .EUt(VA[tier])
                    .duration(90 * 20)
                    .cleanroom(a)
                    .buildAndRegister();
        }
        else if(amount==160)
        {
            CUTTER_RECIPES.recipeBuilder()
                    .input(wafer)
                    .output(chip, 64)
                    .output(chip, 64)
                    .output(chip, 32)
                    .EUt(VA[tier])
                    .duration(90 * 20)
                    .cleanroom(a)
                    .buildAndRegister();
        }
        else if(amount==192)
        {
            CUTTER_RECIPES.recipeBuilder()
                    .input(wafer)
                    .output(chip, 64)
                    .output(chip, 64)
                    .output(chip, 64)
                    .EUt(VA[tier])
                    .duration(90 * 20)
                    .cleanroom(a)
                    .buildAndRegister();
        }
        else if(amount==224)
        {
            CUTTER_RECIPES.recipeBuilder()
                    .input(wafer)
                    .output(chip, 64)
                    .output(chip, 64)
                    .output(chip, 64)
                    .output(chip, 32)
                    .EUt(VA[tier])
                    .duration(90 * 20)
                    .cleanroom(a)
                    .buildAndRegister();
        }
        else if(amount==256)
        {
            CUTTER_RECIPES.recipeBuilder()
                    .input(wafer)
                    .output(chip, 64)
                    .output(chip, 64)
                    .output(chip, 64)
                    .output(chip, 64)
                    .EUt(VA[tier])
                    .duration(90 * 20)
                    .cleanroom(a)
                    .buildAndRegister();
        }

    }

    public static void cutter(MetaItem<?>.MetaValueItem wafer, MetaItem<?>.MetaValueItem chip, int tier,CleanroomType a)
    {
        CUTTER_RECIPES.recipeBuilder()
                .input(wafer)
                .output(chip, 2)
                .EUt(VA[tier])
                .duration(90*20)
                .cleanroom(a)
                .buildAndRegister();
    }
}
