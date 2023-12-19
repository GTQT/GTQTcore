package keqing.gtqtcore.common.items;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.StandardMetaItem;
import keqing.gtqtcore.common.CommonProxy;
import keqing.gtqtcore.common.items.behaviors.IntBcircuitBehavior;
import keqing.gtqtcore.common.items.behaviors.MillBallBehavior;

import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class GTQTMetaItem1 extends StandardMetaItem {

    public GTQTMetaItem1() {
        this.setRegistryName("gtqt_meta_item_1");
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
    }



    public void registerSubItems() {
        BIOLOGY_INTEGRATED_CIRCUIT = this.addItem(39, "item.biology_integrated_circuit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB).addComponents(new IntBcircuitBehavior());
        POTASSIUM_ETHYLATE=this.addItem(58, "item.potassium.ethylate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        POTASSIUM_ETHYLXANTHATE=this.addItem(59, "item.potassium.ethylxanthate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SODIUM_ETHYLXANTHATE=this.addItem(57, "item.sodium.ethylxanthate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SODIUM_ETHYLATE=this.addItem(56, "item.sodium.ethylate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        PINE_CONE=this.addItem(47, "item.pine_cone").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        PINE_FRAGMENT=this.addItem(48, "item.pine_fragment").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GRINDBALL_SOAPSTONE = this.addItem(370, "mill.grindball_soapstone").setMaxStackSize(1).addComponents(new MillBallBehavior());
        GRINDBALL_ALUMINIUM = this.addItem(371, "mill.grindball_aluminium").setMaxStackSize(1).addComponents(new MillBallBehavior());



        WRAP_CIRCUIT_ULV = this.addItem(400, "wrap.circuit.ulv");
        WRAP_CIRCUIT_LV = this.addItem(401, "wrap.circuit.lv");
        WRAP_CIRCUIT_MV = this.addItem(402, "wrap.circuit.mv");
        WRAP_CIRCUIT_HV = this.addItem(403, "wrap.circuit.hv");
        WRAP_CIRCUIT_EV = this.addItem(404, "wrap.circuit.ev");
        WRAP_CIRCUIT_IV = this.addItem(405, "wrap.circuit.iv");
        WRAP_CIRCUIT_LuV = this.addItem(406, "wrap.circuit.luv");
        WRAP_CIRCUIT_ZPM = this.addItem(407, "wrap.circuit.zpm");
        WRAP_CIRCUIT_UV = this.addItem(408, "wrap.circuit.uv");
        WRAP_CIRCUIT_UHV = this.addItem(409, "wrap.circuit.uhv");
        WRAP_CIRCUIT_UEV = this.addItem(410, "wrap.circuit.uev");
        WRAP_CIRCUIT_UIV = this.addItem(411, "wrap.circuit.uiv");
        WRAP_CIRCUIT_UXV = this.addItem(412, "wrap.circuit.uxv");
        WRAP_CIRCUIT_OpV = this.addItem(413, "wrap.circuit.opv");
        WRAP_CIRCUIT_MAX = this.addItem(414, "wrap.circuit.max");
    }
}
