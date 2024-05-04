package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.MetaBlocks;
import gregtech.loaders.recipe.CraftingComponent;
import gregtech.loaders.recipe.MetaTileEntityLoader;
import net.minecraftforge.oredict.OreDictionary;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.loaders.recipe.CraftingComponent.*;
import static gregtech.loaders.recipe.CraftingComponent.GLASS;
import static keqing.gtqtcore.api.utils.GTQTUtil.getItemById;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.AUTO_CHISEL;


public class ChiselMachineRecipeLoader {

    public static void init() {

        OreDictionary.registerOre("craftingChisel", getItemById("chisel", "chisel_iron"));
        OreDictionary.registerOre("craftingChisel", getItemById("chisel", "chisel_diamond"));
        OreDictionary.registerOre("craftingChisel", getItemById("chisel", "chisel_hitech"));

        CraftingComponent.Component LOWER_CIRCUIT = new CraftingComponent.Component(Stream.of(
                        new Object[]{0, new UnificationEntry(gem, Diamond)},
                        new Object[]{1, new UnificationEntry(circuit, MarkerMaterials.Tier.ULV)},
                        new Object[]{2, new UnificationEntry(circuit, MarkerMaterials.Tier.LV)},
                        new Object[]{3, new UnificationEntry(circuit, MarkerMaterials.Tier.MV)},
                        new Object[]{4, new UnificationEntry(circuit, MarkerMaterials.Tier.HV)},
                        new Object[]{5, new UnificationEntry(circuit, MarkerMaterials.Tier.EV)},
                        new Object[]{6, new UnificationEntry(circuit, MarkerMaterials.Tier.IV)},
                        new Object[]{7, new UnificationEntry(circuit, MarkerMaterials.Tier.LuV)},
                        new Object[]{8, new UnificationEntry(circuit, MarkerMaterials.Tier.ZPM)},
                        new Object[]{9, new UnificationEntry(circuit, MarkerMaterials.Tier.UV)},
                        new Object[]{10, new UnificationEntry(circuit, MarkerMaterials.Tier.UHV)},
                        new Object[]{11, new UnificationEntry(circuit, MarkerMaterials.Tier.UEV)},
                        new Object[]{12, new UnificationEntry(circuit, MarkerMaterials.Tier.UIV)},
                        new Object[]{13, new UnificationEntry(circuit, MarkerMaterials.Tier.UXV)},
                        new Object[]{14, new UnificationEntry(circuit, MarkerMaterials.Tier.OpV)})
                .collect(Collectors.toMap((data) -> (Integer)data[0], (data) -> data[1])));

        MetaTileEntityLoader.registerMachineRecipe(true, AUTO_CHISEL,
                "GYG", "CHX", "WMW",
                'H', HULL,
                'M', MOTOR,
                'W', CABLE,
                'C', CONVEYOR,
                'X', "craftingChisel",
                'Y', LOWER_CIRCUIT,
                'G', GLASS);


    }
}