package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Hematite;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static supercritical.api.unification.material.SCMaterials.*;

public class OreRecipeHandler {
    public static List<ItemStack> oreBasic = new ArrayList<>();
    public static List<ItemStack> oreAdvanced = new ArrayList<>();
    public static List<ItemStack> oreUltimate = new ArrayList<>();

    public static void register() {
        List<Material> Basic = new ArrayList<>();

        Collections.addAll(Basic,
                Aluminium, Beryllium, Chrome, Cobalt, Copper, Gallium, Gold, Iron, Lead,Lithium,Manganese,Molybdenum,
                Nickel,Selenium,Silver,Sulfur, Tellurium,Thorium,Tin, Almandine,Asbestos,BandedIron,BlueTopaz,BrownLimonite,
                Calcite,Cassiterite,CassiteriteSand,Chalcopyrite,Chromite, Cinnabar,Coal,Cobaltite,Cooperite,Diamond,Emerald,Galena,
                Garnierite,GreenSapphire,Grossular,Ilmenite,Rutile, Bauxite,Lazurite,Magnesite,Molybdenite,Powellite,Pyrite,Pyrolusite,
                Pyrope,RockSalt,Ruby,Salt,Saltpeter, Sapphire,Sodalite,Tantalite,Spessartine,Sphalerite,Stibnite,Tetrahedrite,Topaz,
                Tungstate,Uraninite, Wulfenite,YellowLimonite,NetherQuartz,CertusQuartz,Graphite,Bornite,Chalcocite,Realgar,
                Bastnasite, Pentlandite,Spodumene,Lepidolite,GlauconiteSand,Malachite,Mica,Barite,Alunite,Talc,Kyanite,Pyrochlore,
                Oilsands,Olivine,Opal,Amethyst,Lapis,Apatite,TricalciumPhosphate,GarnetRed,GarnetYellow,VanadiumMagnetite,Pollucite,
                Bentonite,FullersEarth,Monazite,Trona,Gypsum,Zeolite,Redstone,Electrotine,Diatomite,GraniticMineralSand,GarnetSand,
                BasalticMineralSand,Hematite,Fluix,PreciousMetal,Fluorite,Caliche,LeanGoldSulphide,RichGoldSulphide,Lignite,
                Crocoite,Cryolite,Amblygonite,Amber,Ulexite,Gashydrate,Zircon,Bismuth
        );
        List<Material> Advanced = new ArrayList<>(Basic);

        Collections.addAll(Advanced,
                Iridium, Lanthanum,Neodymium,Niobium,Osmium,Palladium,Platinum,Rhodium,Ruthenium,Samarium,Scandium,Yttrium,
                Titanium,NaquadahOxide,Scheelite,NetherStar,Pitchblende,Thorianite, Plutonium239,Lutetium);
        List<Material> Ultimate = new ArrayList<>(Advanced);
        Collections.addAll(Ultimate,
                Americium,Californium,Europium,Gadolinium,Holmium,Lawrencium,Nobelium,Praseodymium,Technetium,Terbium);

        for(Material material: Basic) oreBasic.add(OreDictUnifier.get(new UnificationEntry(OrePrefix.ore, material)));
        for(Material material: Advanced) oreAdvanced.add(OreDictUnifier.get(new UnificationEntry(OrePrefix.ore, material)));
        for(Material material: Ultimate) oreUltimate.add(OreDictUnifier.get(new UnificationEntry(OrePrefix.ore, material)));
    }
}
