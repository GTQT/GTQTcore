package keqing.gtqtcore.api.unification;


import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.common.blocks.BlockMachineCasing;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockGlass1;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.blocks.BlockMachineCasing.MachineCasingType.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockGlass1.CasingType.*;

public class MaterialHelper {
    public static Material[] Superconductor = {LVSuperconductor, MVSuperconductor, HVSuperconductor, EVSuperconductor, IVSuperconductor, LuVSuperconductor, ZPMSuperconductor, UVSuperconductor, UHVSuperconductor};
    public static Material[] SecondPlate = {RedAlloy,GalvanizedSteel,Invar,Talonite,Magnalium,NiobiumTitanium,Naquadah,Europium,Duranium,Trinium,Neutronium};
    public static BlockMultiblockGlass1.CasingType[] Glass = {TI_BORON_SILICATE_GLASS, W_BORON_SILICATE_GLASS, OSMIR_BORON_SILICATE_GLASS, NAQ_BORON_SILICATE_GLASS, FORCE_FIELD_CONSTRAINED_GLASS, COSMIC_MICROWAVE_BACKGROUND_RADIATION_ABSORPTION_GLASS, SPACETIME_SUPERCONDENSER_GLASS, SUPRACAUSAL_LIGHT_CONE_GLASS};
    public static Material[] Glue ={Materials.Glue,Polyethylene,PolyvinylChloride,Epoxy,ReinforcedEpoxyResin,Polytetrafluoroethylene,Zylon,Polybenzimidazole,Polyetheretherketone,Kevlar,KaptonK,KaptonE};
    public static BlockMachineCasing.MachineCasingType[] MachineCasing = {ULV, LV, MV, HV, EV, IV, LuV, ZPM, UV, UHV, UEV, UIV, UXV, OpV, MAX};
}
