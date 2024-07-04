package keqing.gtqtcore.loaders.recipes;

import keqing.gtqtcore.loaders.recipes.chain.*;
import keqing.gtqtcore.loaders.recipes.chain.AcetyleneChain;
import keqing.gtqtcore.loaders.recipes.chain.AmmoniaChain;
import keqing.gtqtcore.loaders.recipes.chain.BZChain;
import keqing.gtqtcore.loaders.recipes.chain.BoronNitrideChain;
import keqing.gtqtcore.loaders.recipes.chain.BrineChain;
import keqing.gtqtcore.loaders.recipes.chain.DimethylformamideChain;
import keqing.gtqtcore.loaders.recipes.chain.EDTAChain;
import keqing.gtqtcore.loaders.recipes.chain.EtchingMaterialsChain;
import keqing.gtqtcore.loaders.recipes.chain.EthyleneGlycolChain;
import keqing.gtqtcore.loaders.recipes.chain.FantasyMaterials;
import keqing.gtqtcore.loaders.recipes.chain.FullereneChain;
import keqing.gtqtcore.loaders.recipes.chain.GalliumNitrideChain;
import keqing.gtqtcore.loaders.recipes.chain.GrapheneChain;
import keqing.gtqtcore.loaders.recipes.chain.HydrogenPeroxideChain;
import keqing.gtqtcore.loaders.recipes.chain.IsotopesChain;
import keqing.gtqtcore.loaders.recipes.chain.KaptonChain;
import keqing.gtqtcore.loaders.recipes.chain.KevlarChain;
import keqing.gtqtcore.loaders.recipes.chain.MagneticsChain;
import keqing.gtqtcore.loaders.recipes.chain.MethylamineChain;
import keqing.gtqtcore.loaders.recipes.chain.NanotubesChain;
import keqing.gtqtcore.loaders.recipes.circuits.*;
import keqing.gtqtcore.loaders.recipes.component.GCYSComponentRecipes;
import keqing.gtqtcore.loaders.recipes.handlers.*;

public class GTQTRecipesManager {
    private GTQTRecipesManager() {

    }
    public static void load() {
    }
    public static void init() {
        ZirconChain.init();
        DeltamethrinChain.init();
        RubbersChain.init();
        BoronChain.init();
        EnzymesRecipes.init();
        GTQTOreRecipeHandler.init();
        StrontiumRadiumChain.init();
        AlgaeChain.init();
        AirDistillation.init();
        SodiumChain.init();
        ParticleAccelerator.init();
        CTABChain.init();
        DragonChain.init();
        CBDOPolycarbonateChain.init();
        CaliforniumChain.init();
        TitanateChain.init();
        NuclearLine.init();
        OceanChain.init();
        GermaniumChain.init();
        NaquadahChain.init();
        MachineCasing.init();
        Bittern.init();
        GasChain.init();
        HexanitrohexaaxaisowurtzitaneChain.init();
        ZylonChain.init();
        MiscRecipes.init();
        MagicChain.init();
        RecipeConflicts.init();
        AluminiumLine.init();
        CoilWire.init();
        GCYSMetaTileEntityLoader.init();
        GCYSBlockRecipeLoader.init();
        BoulesRecipes.init();
        BouleRecipeHandler.register();
        GrapheneChain.init();
        NanoCoatingRecipes.init();
        PreciousMetalChain.init();
        MiningDrill.init();
        HydrogenPeroxideChain.init();
        AcetyleneChain.init();
        TurpentineChain.init();
        EDTAChain.init();
        EtchingMaterialsChain.init();
        OilProcessing.init();
        DimethylformamideChain.init();
        CleanWaterChain.init();
        KaptonChain.init();
        PowerSupply.init();
        AmmoniaChain.init();
        BrineChain.init();
        MethylamineChain.init();
        KevlarChain.init();
        BoronNitrideChain.init();
        BZChain.init();
        PhosphorusChain.init();
        EthyleneGlycolChain.init();
        IodineChain.init();
        PMMAChain.init();
        GalliumNitrideChain.init();
        PhotoresistivesChain.init();
        MagneticsChain.init();
        NanotubesChain.init();
        IsotopesChain.init();
        FullereneChain.init();
        Batteries.init();
        FantasyMaterials.init();
        SimpleMaterials.init();
        PedotChain.init();
        SeleniumTelluriumChain.init();
        BiochemChains.init();
        BiologyRecipe.init();
        PhotoresistChains.init();
        PetrochemRecipes.init();
        MaterialOreChains.init();
        LubricantChains.init();
        PEEKChain.init();
        ELE.init();
        CPULine.init();
        AllouKlin.init();
        GTComponents.init();
        PlatinumDeal.init();
        DecompositionRecipeHandler.runRecipeGeneration();
        TESEDecompositionRecipeHandler.runRecipeGeneration();
        CircuitryMaterialChains.init();
        MetaTileEntityLoader.init();
        MetaTileEntityMachine.init();
        OilChains.init();
        EFusionLoader.init();
        PCBline.init();
        NanotubesChain.init();
        initCircuits();
        initOreProcessing();
        GCYSComponentRecipes.init();
        AutoChiselRecipeLoader.init();
        ChiselMachineRecipeLoader.init();
        PreciseAssemblerChain.init();
    }
    private static void initOreProcessing() {
        PlatinumGroupProcessing.init();
        RareEarthProcessing.init();
        TungstenProcessing.init();
        GermaniumProcessing.init();
        NiobiumTantalumChain.init();
        MolybdenumProcessing.init();
        ThalliumProcessing.init();
        TaraniumProcessing.init();
        RubidiumProcessing.init();
    }
    private static void initCircuits() {
        PrimitiveCircuits.init();
        CrystalCircuits.init();
        WetwareCircuits.init();
        GoowareCircuits.init();
        OpticalCircuits.init();
        SpintronicCircuits.init();
    }
}
