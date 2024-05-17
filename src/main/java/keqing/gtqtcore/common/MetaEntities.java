package keqing.gtqtcore.common;

import gregtech.api.GregTechAPI;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.handler.DynamiteRenderer;
import gregtech.client.renderer.handler.GTBoatRenderer;
import gregtech.client.renderer.handler.GTExplosiveRenderer;
import gregtech.client.renderer.handler.PortalRenderer;
import gregtech.common.entities.DynamiteEntity;
import gregtech.common.entities.GTBoatEntity;
import gregtech.common.entities.ITNTEntity;
import gregtech.common.entities.PortalEntity;
import gregtech.common.entities.PowderbarrelEntity;

import keqing.gtqtcore.common.entities.STNTEntity;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MetaEntities {

    public static void init() {

        EntityRegistry.registerModEntity(GTUtility.gregtechId("stnt"), STNTEntity.class, "STNT", 1,
                GregTechAPI.instance, 64, 3, true);
    }

    @SideOnly(Side.CLIENT)
    public static void initRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(STNTEntity.class, GTExplosiveRenderer::new);
    }
}