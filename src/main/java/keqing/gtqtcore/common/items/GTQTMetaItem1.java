package keqing.gtqtcore.common.items;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.StandardMetaItem;

import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class GTQTMetaItem1 extends StandardMetaItem {

    public GTQTMetaItem1() {
        this.setRegistryName("gtqt_meta_item_1");
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
    }



    public void registerSubItems() {
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
