package keqing.gtqtcore.common.covers;

import gregtech.api.GTValues;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.items.behavior.CoverItemBehavior;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.common.covers.CoverConveyor;
import gregtech.common.covers.CoverPump;
import gregtech.common.covers.CoverRoboticArm;
import gregtech.common.covers.CoverSolarPanel;
import keqing.gtqtcore.GTQTCore;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.util.ResourceLocation;

import static gregtech.api.GTValues.OpV;

public class GTQTCoverBehavior {

    public static void init() {

        registerBehavior(new ResourceLocation(GTQTCore.MODID, "pump.max"), GTQTMetaItems.ELECTRIC_PUMP_MAX,
                (def, tile, side) -> new CoverPump(def, tile, side, GTValues.MAX, 1048576));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "conveyor.max"), GTQTMetaItems.CONVEYOR_MODULE_MAX,
                (def, tile, side) -> new CoverConveyor(def, tile, side, GTValues.MAX, 1024));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "robot_arm.max"), GTQTMetaItems.ROBOT_ARM_MAX,
                (def, tile, side) -> new CoverRoboticArm(def, tile, side, GTValues.MAX, 1024));
        registerBehavior(new ResourceLocation(GTQTCore.MODID,  "solar_panel.max"), GTQTMetaItems.COVER_SOLAR_PANEL_MAX,
                (def, tile, side) -> new CoverSolarPanel(def, tile, side, GTValues.V[GTValues.MAX]));


        registerBehavior(new ResourceLocation(GTQTCore.MODID, "dual_cover.lv"), GTQTMetaItems.DUAL_COVER_LV,
                (def, tile, side) -> new CoverDualCover(def, tile, side, GTValues.LV, 8, 1280));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "dual_cover.mv"), GTQTMetaItems.DUAL_COVER_MV,
                (def, tile, side) -> new CoverDualCover(def, tile, side, GTValues.MV, 32, 1280 * 4));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "dual_cover.hv"), GTQTMetaItems.DUAL_COVER_HV,
                (def, tile, side) -> new CoverDualCover(def, tile, side, GTValues.HV, 64, 1280 * 16));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "dual_cover.ev"), GTQTMetaItems.DUAL_COVER_EV,
                (def, tile, side) -> new CoverDualCover(def, tile, side, GTValues.EV, 3 * 64, 1280 * 64));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "dual_cover.iv"), GTQTMetaItems.DUAL_COVER_IV,
                (def, tile, side) -> new CoverDualCover(def, tile, side, GTValues.IV, 8 * 64, 1280 * 64 * 4));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "dual_cover.luv"), GTQTMetaItems.DUAL_COVER_LuV,
                (def, tile, side) -> new CoverDualCover(def, tile, side, GTValues.LuV, 16 * 64, 1280 * 64 * 16));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "dual_cover.zpm"), GTQTMetaItems.DUAL_COVER_ZPM,
                (def, tile, side) -> new CoverDualCover(def, tile, side, GTValues.ZPM, 16 * 64, 1280 * 64 * 64));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "dual_cover.uv"), GTQTMetaItems.DUAL_COVER_UV,
                (def, tile, side) -> new CoverDualCover(def, tile, side, GTValues.UV, 16 * 64, 1280 * 64 * 64 * 4));

        registerBehavior(new ResourceLocation(GTQTCore.MODID, "precise_dual_cover.lv"), GTQTMetaItems.PRECISE_DUAL_COVER_LV,
                (def, tile, side) -> new CoverPreciseDualCover(def, tile, side, GTValues.LV, 8, 1280));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "precise_dual_cover.mv"), GTQTMetaItems.PRECISE_DUAL_COVER_MV,
                (def, tile, side) -> new CoverPreciseDualCover(def, tile, side, GTValues.MV, 32, 1280 * 4));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "precise_dual_cover.hv"), GTQTMetaItems.PRECISE_DUAL_COVER_HV,
                (def, tile, side) -> new CoverPreciseDualCover(def, tile, side, GTValues.HV, 64, 1280 * 16));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "precise_dual_cover.ev"), GTQTMetaItems.PRECISE_DUAL_COVER_EV,
                (def, tile, side) -> new CoverPreciseDualCover(def, tile, side, GTValues.EV, 3 * 64, 1280 * 64));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "precise_dual_cover.iv"), GTQTMetaItems.PRECISE_DUAL_COVER_IV,
                (def, tile, side) -> new CoverPreciseDualCover(def, tile, side, GTValues.IV, 8 * 64, 1280 * 64 * 4));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "precise_dual_cover.luv"), GTQTMetaItems.PRECISE_DUAL_COVER_LuV,
                (def, tile, side) -> new CoverPreciseDualCover(def, tile, side, GTValues.LuV, 16 * 64, 1280 * 64 * 16));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "precise_dual_cover.zpm"), GTQTMetaItems.PRECISE_DUAL_COVER_ZPM,
                (def, tile, side) -> new CoverPreciseDualCover(def, tile, side, GTValues.ZPM, 16 * 64, 1280 * 64 * 64));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "precise_dual_cover.uv"), GTQTMetaItems.PRECISE_DUAL_COVER_UV,
                (def, tile, side) -> new CoverPreciseDualCover(def, tile, side, GTValues.UV, 16 * 64,
                        1280 * 64 * 64 * 4));

        registerBehavior(new ResourceLocation(GTQTCore.MODID, "microwave_cover.lv"), GTQTMetaItems.MICROWAVE_ENERGY_RECEIVER_LV,
                (def, tile, side) -> new CoverMicrowaveEnergyReceiver(def, tile, side, GTValues.LV));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "microwave_cover.mv"), GTQTMetaItems.MICROWAVE_ENERGY_RECEIVER_MV,
                (def, tile, side) -> new CoverMicrowaveEnergyReceiver(def, tile, side, GTValues.MV));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "microwave_cover.hv"), GTQTMetaItems.MICROWAVE_ENERGY_RECEIVER_HV,
                (def, tile, side) -> new CoverMicrowaveEnergyReceiver(def, tile, side, GTValues.HV));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "microwave_cover.ev"), GTQTMetaItems.MICROWAVE_ENERGY_RECEIVER_EV,
                (def, tile, side) -> new CoverMicrowaveEnergyReceiver(def, tile, side, GTValues.EV));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "microwave_cover.iv"), GTQTMetaItems.MICROWAVE_ENERGY_RECEIVER_IV,
                (def, tile, side) -> new CoverMicrowaveEnergyReceiver(def, tile, side, GTValues.IV));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "microwave_cover.luv"), GTQTMetaItems.MICROWAVE_ENERGY_RECEIVER_LuV,
                (def, tile, side) -> new CoverMicrowaveEnergyReceiver(def, tile, side, GTValues.LuV));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "microwave_cover.zpm"), GTQTMetaItems.MICROWAVE_ENERGY_RECEIVER_ZPM,
                (def, tile, side) -> new CoverMicrowaveEnergyReceiver(def, tile, side, GTValues.ZPM));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "microwave_cover.uv"), GTQTMetaItems.MICROWAVE_ENERGY_RECEIVER_UV,
                (def, tile, side) -> new CoverMicrowaveEnergyReceiver(def, tile, side, GTValues.UV));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "microwave_cover.uhv"), GTQTMetaItems.MICROWAVE_ENERGY_RECEIVER_UHV,
                (def, tile, side) -> new CoverMicrowaveEnergyReceiver(def, tile, side, GTValues.UHV));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "microwave_cover.uev"), GTQTMetaItems.MICROWAVE_ENERGY_RECEIVER_UEV,
                (def, tile, side) -> new CoverMicrowaveEnergyReceiver(def, tile, side, GTValues.UEV));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "microwave_cover.uiv"), GTQTMetaItems.MICROWAVE_ENERGY_RECEIVER_UIV,
                (def, tile, side) -> new CoverMicrowaveEnergyReceiver(def, tile, side, GTValues.UIV));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "microwave_cover.uxv"), GTQTMetaItems.MICROWAVE_ENERGY_RECEIVER_UXV,
                (def, tile, side) -> new CoverMicrowaveEnergyReceiver(def, tile, side, GTValues.UXV));
        registerBehavior(new ResourceLocation(GTQTCore.MODID, "microwave_cover.opv"), GTQTMetaItems.MICROWAVE_ENERGY_RECEIVER_OpV,
                (def, tile, side) -> new CoverMicrowaveEnergyReceiver(def, tile, side, OpV));
    }

    /**
     * Used to register cover behavior.
     *
     * <p>
     *     Please pre init item in {@link GTQTMetaItems},
     *     and use {@link gregtech.api.items.metaitem.MetaItem.MetaValueItem#addComponents} to add tooltips.
     * </p>
     *
     * @param coverId          Resource Location of cover, please use modid of gtqtcore (please see: {@link GTQTCore#MODID}, do not use value in mod main class).
     * @param placerItem       Set which item has this cover behavior.
     * @param behaviorCreator  Definition of cover, use lambda to define a cover (you can see some example at gregtech/common/covers).
     */
    @SuppressWarnings("rawtypes")
    public static void registerBehavior(ResourceLocation coverId,
                                        MetaItem.MetaValueItem placerItem,
                                        CoverDefinition.CoverCreator behaviorCreator) {
        CoverDefinition coverDefinition = gregtech.common.covers.CoverBehaviors.registerCover(coverId, placerItem.getStackForm(), behaviorCreator);
        placerItem.addComponents(new CoverItemBehavior(coverDefinition));
    }
}

