package keqing.gtqtcore.integration.jei;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.integration.IntegrationSubmodule;
import keqing.gtqtcore.api.utils.GTQTCPUHelper;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static gregtech.common.items.MetaItems.*;
import static gregtech.common.items.MetaItems.VACUUM_TUBE;
import static keqing.gtqtcore.GTQTCore.MODID;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

@JEIPlugin
public class JustEnoughItemsModule extends IntegrationSubmodule implements IModPlugin {
    public static IGuiHelper guiHelper;
    @Override
    public void registerCategories( IRecipeCategoryRegistration registry) {
        guiHelper = registry.getJeiHelpers().getGuiHelper();

        registry.addRecipeCategories(new PhotolithographyFactoryJeiCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new CircuitJeiCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new AlgaeJeiCategory(registry.getJeiHelpers().getGuiHelper()));
    }
    @Override
    public void register(IModRegistry registry) {

        String PA = MODID + ":" + "PhotolithographyFactory";
        List<PhotolithographyFactoryJei> PAInfo = new ArrayList<>();
        List<ItemStack> wafer=new ArrayList<>();
        List<ItemStack> output=new ArrayList<>();
        for(MetaItem.MetaValueItem item:GTQTCPUHelper.wafer) wafer.add(item.getStackForm());
        for(MetaItem.MetaValueItem item:GTQTCPUHelper.item) output.add(item.getStackForm());
        PAInfo.add(new PhotolithographyFactoryJei(wafer,output));
        registry.addRecipes(PAInfo, PA);
        registry.addRecipeCatalyst(GTQTMetaTileEntities.PHOTOLITHOGRAPHY_FACTORY.getStackForm(), PA);

        String Circuit = MODID + ":" + "Circuit";
        List<CircuitJei> CircuitInfo = new ArrayList<>();
        MetaItem.MetaValueItem [] tier1={VACUUM_TUBE,ELECTRONIC_CIRCUIT_LV,ELECTRONIC_CIRCUIT_MV};
        MetaItem.MetaValueItem [] tier2={INTEGRATED_CIRCUIT_LV,INTEGRATED_CIRCUIT_MV,INTEGRATED_CIRCUIT_HV};
        MetaItem.MetaValueItem [] tier3={NAND_CHIP_ULV,MICROPROCESSOR_LV,PROCESSOR_MV,PROCESSOR_ASSEMBLY_HV,WORKSTATION_EV,MAINFRAME_IV};
        MetaItem.MetaValueItem [] tier4={NANO_PROCESSOR_HV,NANO_PROCESSOR_ASSEMBLY_EV,NANO_COMPUTER_IV,NANO_MAINFRAME_LUV};
        MetaItem.MetaValueItem [] tier5={QUANTUM_PROCESSOR_EV,QUANTUM_ASSEMBLY_IV,QUANTUM_COMPUTER_LUV,QUANTUM_MAINFRAME_ZPM};
        MetaItem.MetaValueItem [] tier6={CRYSTAL_PROCESSOR_IV,CRYSTAL_ASSEMBLY_LUV,CRYSTAL_COMPUTER_ZPM,CRYSTAL_MAINFRAME_UV};
        MetaItem.MetaValueItem [] tier7={WETWARE_PROCESSOR_LUV,WETWARE_PROCESSOR_ASSEMBLY_ZPM,WETWARE_SUPER_COMPUTER_UV,WETWARE_MAINFRAME_UHV};
        MetaItem.MetaValueItem [] tier8={GOOWARE_PROCESSOR,GOOWARE_ASSEMBLY,GOOWARE_COMPUTER,GOOWARE_MAINFRAME};
        MetaItem.MetaValueItem [] tier9={OPTICAL_PROCESSOR,OPTICAL_ASSEMBLY,OPTICAL_COMPUTER,OPTICAL_MAINFRAME};
        MetaItem.MetaValueItem [] tier10={SPINTRONIC_PROCESSOR,SPINTRONIC_ASSEMBLY,SPINTRONIC_COMPUTER,SPINTRONIC_MAINFRAME};
        MetaItem.MetaValueItem [] tier11={COSMIC_PROCESSOR,COSMIC_ASSEMBLY,COSMIC_COMPUTER,COSMIC_MAINFRAME};
        MetaItem.MetaValueItem [] tier12={SUPRACAUSAL_PROCESSOR,SUPRACAUSAL_ASSEMBLY,SUPRACAUSAL_COMPUTER,SUPRACAUSAL_MAINFRAME};

        CircuitInfo.add(new CircuitJei(tier1));
        CircuitInfo.add(new CircuitJei(tier2));
        CircuitInfo.add(new CircuitJei(tier3));
        CircuitInfo.add(new CircuitJei(tier4));
        CircuitInfo.add(new CircuitJei(tier5));
        CircuitInfo.add(new CircuitJei(tier6));
        CircuitInfo.add(new CircuitJei(tier7));
        CircuitInfo.add(new CircuitJei(tier8));
        CircuitInfo.add(new CircuitJei(tier9));
        CircuitInfo.add(new CircuitJei(tier10));
        CircuitInfo.add(new CircuitJei(tier11));
        CircuitInfo.add(new CircuitJei(tier12));
        registry.addRecipes(CircuitInfo, Circuit);

        String Algae = MODID + ":" + "Algae";
        List<AlgaeJei> AlgaeInfo = new ArrayList<>();
        MetaItem.MetaValueItem [] algaeList={COMMON_ALGAE,GREEN_ALGAE,RED_ALGAE,BROWN_ALGAE,GOLD_ALGAE};
        AlgaeInfo.add(new AlgaeJei(algaeList));
        registry.addRecipes(AlgaeInfo, Algae);
        registry.addRecipeCatalyst(GTQTMetaTileEntities.ALGAE_FARM.getStackForm(), Algae);

        String oreByProductId = "gregtech:ore_by_product";
        registry.addRecipeCatalyst(GTQTMetaTileEntities.INTEGRATED_ORE_PROCESSOR.getStackForm(), oreByProductId);
        registry.addRecipeCatalyst(GTQTMetaTileEntities.INTEGRATED_MINING_DIVISION.getStackForm(), oreByProductId);

        registry.addIngredientInfo( ALPHA.getStackForm(), VanillaTypes.ITEM, "metaitem.alpha.jei_description");
        registry.addIngredientInfo( ANTIALPHA.getStackForm(), VanillaTypes.ITEM, "metaitem.antialpha.jei_description");
        registry.addIngredientInfo( ANTIBOTTOM_QUARK.getStackForm(), VanillaTypes.ITEM, "metaitem.antibottom_quark.jei_description");
        registry.addIngredientInfo( ANTICHARM_QUARK.getStackForm(), VanillaTypes.ITEM, "metaitem.anticharm_quark.jei_description");
        registry.addIngredientInfo( ANTIDELTA_MINUS.getStackForm(), VanillaTypes.ITEM, "metaitem.antidelta_minus.jei_description");
        registry.addIngredientInfo( ANTIDELTA_PLUS_PLUS.getStackForm(), VanillaTypes.ITEM, "metaitem.antidelta_plus_plus.jei_description");
        registry.addIngredientInfo( ANTIDEUTERON.getStackForm(), VanillaTypes.ITEM, "metaitem.antideuteron.jei_description");
        registry.addIngredientInfo( ANTIDOWN_QUARK.getStackForm(), VanillaTypes.ITEM, "metaitem.antidown_quark.jei_description");
        registry.addIngredientInfo( ANTIHELION.getStackForm(), VanillaTypes.ITEM, "metaitem.antihelion.jei_description");
        registry.addIngredientInfo( ANTIKAON_NAUGHT.getStackForm(), VanillaTypes.ITEM, "metaitem.antikaon_naught.jei_description");
        registry.addIngredientInfo( ANTIMUON.getStackForm(), VanillaTypes.ITEM, "metaitem.antimuon.jei_description");
        registry.addIngredientInfo( ANTINEUTRON.getStackForm(), VanillaTypes.ITEM, "metaitem.antineutron.jei_description");
        registry.addIngredientInfo( ANTIPROTON.getStackForm(), VanillaTypes.ITEM, "metaitem.antiproton.jei_description");
        registry.addIngredientInfo( ANTISIGMA_MINUS.getStackForm(), VanillaTypes.ITEM, "metaitem.antisigma_minus.jei_description");
        registry.addIngredientInfo( ANTISIGMA_NAUGHT.getStackForm(), VanillaTypes.ITEM, "metaitem.antisigma_naught.jei_description");
        registry.addIngredientInfo( ANTISIGMA_PLUS.getStackForm(), VanillaTypes.ITEM, "metaitem.antisigma_plus.jei_description");
        registry.addIngredientInfo( ANTISTRANGE_QUARK.getStackForm(), VanillaTypes.ITEM, "metaitem.antistrange_quark.jei_description");
        registry.addIngredientInfo( ANTITAU.getStackForm(), VanillaTypes.ITEM, "metaitem.antitau.jei_description");
        registry.addIngredientInfo( ANTITOP_QUARK.getStackForm(), VanillaTypes.ITEM, "metaitem.antitop_quark.jei_description");
        registry.addIngredientInfo( ANTITRITON.getStackForm(), VanillaTypes.ITEM, "metaitem.antitriton.jei_description");
        registry.addIngredientInfo( ANTIUP_QUARK.getStackForm(), VanillaTypes.ITEM, "metaitem.antiup_quark.jei_description");
        registry.addIngredientInfo( BORON_ION.getStackForm(), VanillaTypes.ITEM, "metaitem.boron_ion.jei_description");
        registry.addIngredientInfo( BOTTOM_ETA.getStackForm(), VanillaTypes.ITEM, "metaitem.bottom_eta.jei_description");
        registry.addIngredientInfo( BOTTOM_QUARK.getStackForm(), VanillaTypes.ITEM, "metaitem.bottom_quark.jei_description");
        registry.addIngredientInfo( CALCIUM_48_ION.getStackForm(), VanillaTypes.ITEM, "metaitem.calcium_48_ion.jei_description");
        registry.addIngredientInfo( CHARM_QUARK.getStackForm(), VanillaTypes.ITEM, "metaitem.charm_quark.jei_description");
        registry.addIngredientInfo( CHARMED_ETA.getStackForm(), VanillaTypes.ITEM, "metaitem.charmed_eta.jei_description");
        registry.addIngredientInfo( DELTA_MINUS.getStackForm(), VanillaTypes.ITEM, "metaitem.delta_minus.jei_description");
        registry.addIngredientInfo( DELTA_PLUS_PLUS.getStackForm(), VanillaTypes.ITEM, "metaitem.delta_plus_plus.jei_description");
        registry.addIngredientInfo( DEUTERON.getStackForm(), VanillaTypes.ITEM, "metaitem.deuteron.jei_description");
        registry.addIngredientInfo( DOWN_QUARK.getStackForm(), VanillaTypes.ITEM, "metaitem.down_quark.jei_description");
        registry.addIngredientInfo( ELECTRON.getStackForm(), VanillaTypes.ITEM, "metaitem.electron.jei_description");
        registry.addIngredientInfo( ELECTRON_ANTINEUTRINO.getStackForm(), VanillaTypes.ITEM, "metaitem.electron_antineutrino.jei_description");
        registry.addIngredientInfo( ELECTRON_NEUTRINO.getStackForm(), VanillaTypes.ITEM, "metaitem.electron_neutrino.jei_description");
        registry.addIngredientInfo( ETA.getStackForm(), VanillaTypes.ITEM, "metaitem.eta.jei_description");
        registry.addIngredientInfo( ETA_PRIME.getStackForm(), VanillaTypes.ITEM, "metaitem.eta_prime.jei_description");
        registry.addIngredientInfo( GLUEBALL.getStackForm(), VanillaTypes.ITEM, "metaitem.glueball.jei_description");
        registry.addIngredientInfo( GLUON.getStackForm(), VanillaTypes.ITEM, "metaitem.gluon.jei_description");
        registry.addIngredientInfo( HELION.getStackForm(), VanillaTypes.ITEM, "metaitem.helion.jei_description");
        registry.addIngredientInfo( HIGGS_BOSON.getStackForm(), VanillaTypes.ITEM, "metaitem.higgs_boson.jei_description");
        registry.addIngredientInfo( KAON_MINUS.getStackForm(), VanillaTypes.ITEM, "metaitem.kaon_minus.jei_description");
        registry.addIngredientInfo( KAON_NAUGHT.getStackForm(), VanillaTypes.ITEM, "metaitem.kaon_naught.jei_description");
        registry.addIngredientInfo( KAON_PLUS.getStackForm(), VanillaTypes.ITEM, "metaitem.kaon_plus.jei_description");
        registry.addIngredientInfo( MUON.getStackForm(), VanillaTypes.ITEM, "metaitem.muon.jei_description");
        registry.addIngredientInfo( MUON_ANTINEUTRINO.getStackForm(), VanillaTypes.ITEM, "metaitem.muon_antineutrino.jei_description");
        registry.addIngredientInfo( MUON_NEUTRINO.getStackForm(), VanillaTypes.ITEM, "metaitem.muon_neutrino.jei_description");
        registry.addIngredientInfo( NEUTRON.getStackForm(), VanillaTypes.ITEM, "metaitem.neutron.jei_description");
        registry.addIngredientInfo( PHOTON.getStackForm(), VanillaTypes.ITEM, "metaitem.photon.jei_description");
        registry.addIngredientInfo( PION_MINUS.getStackForm(), VanillaTypes.ITEM, "metaitem.pion_minus.jei_description");
        registry.addIngredientInfo( PION_NAUGHT.getStackForm(), VanillaTypes.ITEM, "metaitem.pion_naught.jei_description");
        registry.addIngredientInfo( PION_PLUS.getStackForm(), VanillaTypes.ITEM, "metaitem.pion_plus.jei_description");
        registry.addIngredientInfo( POSITRON.getStackForm(), VanillaTypes.ITEM, "metaitem.positron.jei_description");
        registry.addIngredientInfo( PROTON.getStackForm(), VanillaTypes.ITEM, "metaitem.proton.jei_description");
        registry.addIngredientInfo( SIGMA_MINUS.getStackForm(), VanillaTypes.ITEM, "metaitem.sigma_minus.jei_description");
        registry.addIngredientInfo( SIGMA_NAUGHT.getStackForm(), VanillaTypes.ITEM, "metaitem.sigma_naught.jei_description");
        registry.addIngredientInfo( SIGMA_PLUS.getStackForm(), VanillaTypes.ITEM, "metaitem.sigma_plus.jei_description");
        registry.addIngredientInfo( STRANGE_QUARK.getStackForm(), VanillaTypes.ITEM, "metaitem.strange_quark.jei_description");
        registry.addIngredientInfo( TAU.getStackForm(), VanillaTypes.ITEM, "metaitem.tau.jei_description");
        registry.addIngredientInfo( TAU_ANTINEUTRINO.getStackForm(), VanillaTypes.ITEM, "metaitem.tau_antineutrino.jei_description");
        registry.addIngredientInfo( TAU_NEUTRINO.getStackForm(), VanillaTypes.ITEM, "metaitem.tau_neutrino.jei_description");
        registry.addIngredientInfo( TOP_QUARK.getStackForm(), VanillaTypes.ITEM, "metaitem.top_quark.jei_description");
        registry.addIngredientInfo( TRITON.getStackForm(), VanillaTypes.ITEM, "metaitem.triton.jei_description");
        registry.addIngredientInfo( UP_QUARK.getStackForm(), VanillaTypes.ITEM, "metaitem.up_quark.jei_description");
        registry.addIngredientInfo( W_MINUS_BOSON.getStackForm(), VanillaTypes.ITEM, "metaitem.w_minus_boson.jei_description");
        registry.addIngredientInfo( W_PLUS_BOSON.getStackForm(), VanillaTypes.ITEM, "metaitem.w_plus_boson.jei_description");
        registry.addIngredientInfo( Z_BOSON.getStackForm(), VanillaTypes.ITEM, "metaitem.z_boson.jei_description");

    }

}
