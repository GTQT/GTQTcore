package keqing.gtqtcore.common;

import gregtech.api.GregTechAPI;
import gregtech.api.items.armor.ArmorMetaItem;
import gregtech.api.metatileentity.registry.MTEManager;
import gregtech.api.unification.material.event.MaterialEvent;
import gregtech.api.unification.material.event.MaterialRegistryEvent;
import keqing.gtqtcore.GTQTCore;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.api.unification.OrePrefixAdditions;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.loaders.recipes.handlers.OreRecipeHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.Objects;

import static keqing.gtqtcore.GTQTCore.PACK;
import static keqing.gtqtcore.GTQTCore.VERSION;
import static keqing.gtqtcore.common.items.GTQTMetaItems.PISTON_BOOTS;
import static net.minecraft.util.text.TextFormatting.*;


@Mod.EventBusSubscriber(
        modid = "gtqtcore"
)

public class GTQTEventHandler {

    public GTQTEventHandler() {
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void registerMTERegistry(MTEManager.MTERegistryEvent event) {
        GregTechAPI.mteManager.createRegistry(GTQTCore.MODID);
    }
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void createMaterialRegistry(MaterialRegistryEvent event) {
        GregTechAPI.materialManager.createRegistry(GTQTCore.MODID);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void registerMaterials(MaterialEvent event) {
        GTQTMaterials.register();
        OrePrefixAdditions.init();
    }

    // override GTCEu fall event to enable piston boots fall damage
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onEntityLivingFallEvent(LivingFallEvent event) {
        if (event.getEntity() instanceof EntityPlayerMP player) {
            ItemStack armor = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
            if (player.fallDistance < 3.2F) {
                return;
            }

            if (!armor.isEmpty() && armor.getItem() instanceof ArmorMetaItem &&
                    ((ArmorMetaItem<?>) armor.getItem()).getItem(armor).equals(PISTON_BOOTS)) {
                ISpecialArmor.ArmorProperties properties = ((ArmorMetaItem<?>) (armor.getItem())).getProperties(player,
                        armor, DamageSource.FALL, (int) (player.fallDistance), EntityEquipmentSlot.FEET.getSlotIndex());
                if (properties.AbsorbRatio > 0) {
                    event.setCanceled(true);
                    EntityLivingBase entityLivingBase = event.getEntityLiving();

                    PotionEffect potioneffect = event.getEntityLiving().getActivePotionEffect(MobEffects.JUMP_BOOST);
                    float f = potioneffect == null ? 0.0F : (float) (potioneffect.getAmplifier() + 1);
                    int i = MathHelper.ceil((properties.AbsorbRatio - f) * event.getDamageMultiplier());

                    if (i > 0) {
                        entityLivingBase.attackEntityFrom(DamageSource.FALL, (float) i);
                        int j = MathHelper.floor(entityLivingBase.posX);
                        int k = MathHelper.floor(entityLivingBase.posY - 0.20000000298023224D);
                        int l = MathHelper.floor(entityLivingBase.posZ);
                        IBlockState iblockstate = entityLivingBase.world.getBlockState(new BlockPos(j, k, l));

                        if (iblockstate.getMaterial() != Material.AIR) {
                            SoundType soundtype = iblockstate.getBlock().getSoundType(iblockstate,
                                    entityLivingBase.world, new BlockPos(j, k, l), entityLivingBase);
                            entityLivingBase.playSound(soundtype.getFallSound(), soundtype.getVolume() * 0.5F,
                                    soundtype.getPitch() * 0.75F);
                        }
                    }
                }
            }
        }
    }

    public static class PlayerLoginEventHandler {

        private static final String[] lines = {
                GOLD + "============================================",
                BOLD + "Welcome to GregTech QuantumTransition " + GREEN + PACK + LIGHT_PURPLE + "-" + VERSION,
                GRAY + "The current game is" + RED + " beta version",
                GRAY + "All content in this version is for preview only and does not guarantee that the game can be played according to the normal survival mode process.",
                GRAY + "communication Group 1:" + YELLOW + "1073091808" + GRAY + "(QQ)",
                GRAY + "Communication Group 2:" + YELLOW + "494136307" + GRAY + "(QQ)",
                GRAY + "Community Link:" + GREEN + " https://www.mcmod.cn/modpack/590.html ",
                GRAY + "Feedback channel:" + GREEN + " https://github.com/GTQT ",
                GOLD + "============================================"
        };

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
            Objects.requireNonNull(event.player);
            for (String line : lines) {
                event.player.sendMessage(new TextComponentString(line));
            }
        }
    }
    @SubscribeEvent
    public static void registerRecipeHandlers(RegistryEvent.Register<IRecipe> event) {
        GTQTLog.logger.info("Registering recipe handlers...");
        OreRecipeHandler.register();
    }

}
