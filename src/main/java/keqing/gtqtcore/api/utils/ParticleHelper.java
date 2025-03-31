package keqing.gtqtcore.api.utils;

import gregtech.api.items.metaitem.MetaItem;

import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class ParticleHelper {
    public static MetaItemWithFloat[] metaItemArray = new MetaItemWithFloat[20];

    public static void init() {
        metaItemArray[0] = new MetaItemWithFloat(ALPHA, ANTIALPHA, 3727.379);
        metaItemArray[1] = new MetaItemWithFloat(BOTTOM_QUARK, ANTIBOTTOM_QUARK, 4180);
        metaItemArray[2] = new MetaItemWithFloat(CHARM_QUARK, ANTICHARM_QUARK, 1275);
        metaItemArray[3] = new MetaItemWithFloat(DELTA_MINUS, ANTIDELTA_MINUS, 1232);
        metaItemArray[4] = new MetaItemWithFloat(DELTA_PLUS_PLUS, ANTIDELTA_PLUS_PLUS, 1232);
        metaItemArray[5] = new MetaItemWithFloat(DEUTERON, ANTIDEUTERON, 1875.613);
        metaItemArray[6] = new MetaItemWithFloat(DOWN_QUARK, ANTIDOWN_QUARK, 4.8);
        metaItemArray[7] = new MetaItemWithFloat(HELION, ANTIHELION, 2808.391);
        metaItemArray[8] = new MetaItemWithFloat(KAON_NAUGHT, ANTIKAON_NAUGHT, 497.614);
        metaItemArray[9] = new MetaItemWithFloat(MUON, ANTIMUON, 105.658);
        metaItemArray[10] = new MetaItemWithFloat(NEUTRON, ANTINEUTRON, 939.565);
        metaItemArray[11] = new MetaItemWithFloat(PROTON, ANTIPROTON, 938.272);
        metaItemArray[12] = new MetaItemWithFloat(SIGMA_MINUS, ANTISIGMA_MINUS, 1189.37);
        metaItemArray[13] = new MetaItemWithFloat(SIGMA_NAUGHT, ANTISIGMA_NAUGHT, 1192.64);
        metaItemArray[14] = new MetaItemWithFloat(SIGMA_PLUS, ANTISIGMA_PLUS, 1197.45);
        metaItemArray[15] = new MetaItemWithFloat(STRANGE_QUARK, ANTISTRANGE_QUARK, 95);
        metaItemArray[16] = new MetaItemWithFloat(TAU, ANTITAU, 1776.82);
        metaItemArray[17] = new MetaItemWithFloat(TOP_QUARK, ANTITOP_QUARK, 172000);
        metaItemArray[18] = new MetaItemWithFloat(TRITON, ANTITRITON, 2808.391);
        metaItemArray[19] = new MetaItemWithFloat(UP_QUARK, ANTIUP_QUARK, 2.3);
    }
    public static class MetaItemWithFloat {
        public MetaItem<?>.MetaValueItem item1;
        public MetaItem<?>.MetaValueItem item2;
        public double floatValue;

        public MetaItemWithFloat(MetaItem<?>.MetaValueItem item1, MetaItem<?>.MetaValueItem item2, double floatValue) {
            this.item1 = item1;
            this.item2 = item2;
            this.floatValue = floatValue;
        }

        public int getDurationByTicket() {
            if((floatValue/10) <=1)return 1;
            return (int) (floatValue/10);
        }
    }


}
