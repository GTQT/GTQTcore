package keqing.gtqtcore.mixin.gregtech;

import gregtech.api.damagesources.DamageSources;
import gregtech.api.items.materialitem.MetaPrefixItem;
import gregtech.api.items.metaitem.StandardMetaItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.core.sound.GTSoundEvents;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import keqing.gtqtcore.GTQTCore;
import keqing.gtqtcore.GTQTCoreConfig;
import keqing.gtqtcore.api.items.IItemRenderer;
import keqing.gtqtcore.api.items.IItemRendererManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static gregtech.api.items.materialitem.MetaPrefixItem.purifyMap;

@Mixin(value = MetaPrefixItem.class, remap = false)
public abstract class MixinMetaPrefixItem extends StandardMetaItem {

    @Unique
    private static final Map<OrePrefix, OrePrefix> hotMap = new HashMap<>();

    static {
        hotMap.put(OrePrefix.ingotHot, OrePrefix.nugget);
    }

    @Unique
    private final boolean easyCooling = GTQTCoreConfig.difficultySwitch.easyCooling;
    @Unique
    private final boolean easyCleaning = GTQTCoreConfig.difficultySwitch.easyCleaning;
    @Unique
    private final boolean hardCleaning = GTQTCoreConfig.difficultySwitch.hardCleaning;
    @Unique
    private final boolean tweaks = (easyCooling || easyCleaning || hardCleaning);
    @Final
    @Shadow
    private OrePrefix prefix;

    @SuppressWarnings("rawtypes")
    @Inject(
            method = "registerModels()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/block/model/ModelBakery;registerItemVariants(Lnet/minecraft/item/Item;[Lnet/minecraft/util/ResourceLocation;)V",
                    ordinal = 0
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void registerModels(CallbackInfo ci,
                                Map alreadyRegistered,
                                ShortIterator var2,
                                short metaItem,
                                MaterialIconSet materialIconSet,
                                short registrationKey,
                                ResourceLocation resourceLocation) {
        if (materialIconSet instanceof IItemRendererManager rendererManager) {
            rendererManager.onRendererRegistry(resourceLocation);
            metaItems.get(metaItem).addComponents(((IItemRenderer) materialIconSet).getRendererManager());
        }
    }

    @Inject(method = "onEntityItemUpdate",
            at = @At(value = "INVOKE",
                    target = "Lgregtech/api/items/materialitem/MetaPrefixItem;getMaterial(Lnet/minecraft/item/ItemStack;)Lgregtech/api/unification/material/Material;"),
            cancellable = true)
    public void onEntityItemUpdateMixin(EntityItem itemEntity, CallbackInfoReturnable<Boolean> cir) {
        if (tweaks) {
            MetaPrefixItem metaPrefixItem = (MetaPrefixItem) (Object) this;
            int count = itemEntity.getItem().getCount();
            ItemStack stack = itemEntity.getItem();
            World worlds = itemEntity.getEntityWorld();
            Material mat = metaPrefixItem.getMaterial(itemEntity.getItem());
            // Cooling with water
            if (easyCooling) {
                if (hotMap.containsKey(this.prefix)) {
                    boolean checkWater = true;
                    BlockPos pos = itemEntity.getPosition();
                    AxisAlignedBB boundingBox = new AxisAlignedBB(
                            itemEntity.posX - 2, itemEntity.posY - 2, itemEntity.posZ - 2,
                            itemEntity.posX + 2, itemEntity.posY + 2, itemEntity.posZ + 2);
                    List<EntityPlayer> players1 = itemEntity.world.getEntitiesWithinAABB(EntityPlayer.class,
                            boundingBox);
                    float heatDamage = prefix.heatDamageFunction.apply(mat.getBlastTemperature());
                    for (int left = -1; left <= 1; left++) {
                        for (int up = -1; up <= 1; up++) {
                            BlockPos checkPos = pos.add(left, 0, up);
                            IBlockState state = itemEntity.world.getBlockState(checkPos);
                            Block block = state.getBlock();
                            if (block != Blocks.WATER) {
                                checkWater = false;
                            }
                        }
                        if (!checkWater) {
                            break;
                        }
                    }
                    if (checkWater) {
                        ItemStack newStack = stack.copy();
                        NBTTagCompound data = itemEntity.getEntityData();
                        if (!data.hasKey("cooling")) {
                            itemEntity.getEntityData().setInteger("cooling", 0);
                        }
                        int cooling = data.getInteger("cooling");
                        if (cooling < 200) {
                            if (cooling % 40 == 0) {
                                itemEntity.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1.0F, 1.0F);
                                for (EntityPlayer player : players1) {
                                    player.attackEntityFrom(DamageSources.getHeatDamage().setDamageBypassesArmor(),
                                            heatDamage);
                                }
                                data.setInteger("cooling", cooling + 1);
                            } else if (cooling % 10 == 0) {
                                itemEntity.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1.0F, 1.0F);
                                data.setInteger("cooling", cooling + 1);
                            } else {
                                data.setInteger("cooling", cooling + 1);
                            }
                        } else {
                            itemEntity.getEntityData().removeTag("cooling");
                            itemEntity.world.setBlockState(pos, Blocks.AIR.getDefaultState());
                            itemEntity.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1.0F, -2.0F);
                            itemEntity.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
                            ItemStack nuggetStack = OreDictUnifier.get(hotMap.get(prefix), mat, 9);
                            EntityItem nuggetEntity = new EntityItem(worlds, pos.getX(), pos.getY() + 0.25, pos.getZ(),
                                    nuggetStack);
                            if (count > 1) {
                                newStack.setCount(count - 1);
                                EntityItem overStack = new EntityItem(worlds, pos.getX(), pos.getY(), pos.getZ(),
                                        newStack);
                                itemEntity.world.spawnEntity(overStack);
                            }
                            List<EntityPlayer> players2 = itemEntity.world.getEntitiesWithinAABB(EntityPlayer.class,
                                    boundingBox.expand(2.0, 2.0, 2.0));
                            for (EntityPlayer player : players2) {
                                player.attackEntityFrom(DamageSources.getHeatDamage().setDamageBypassesArmor(),
                                        heatDamage + 0.5F);
                            }
                            itemEntity.world.spawnEntity(nuggetEntity);
                            itemEntity.setDead();
                        }
                        cir.setReturnValue(false);
                    }
                }
            }
            // Washing with water
            if (easyCleaning) {
                if (purifyMap.containsKey(this.prefix)) {
                    BlockPos pos = itemEntity.getPosition();
                    IBlockState state = itemEntity.world.getBlockState(pos);
                    Block block = state.getBlock();
                    if (block == Blocks.WATER) {
                        ItemStack replacementStack = OreDictUnifier.get(purifyMap.get(prefix),
                                mat, 1);
                        if (count > 1) {
                            ItemStack newStack = itemEntity.getItem().copy();
                            newStack.setCount(count - 1);
                            EntityItem overStack = new EntityItem(worlds, itemEntity.posX, itemEntity.posY,
                                    itemEntity.posZ, newStack);
                            itemEntity.world.spawnEntity(overStack);
                            overStack.setPickupDelay(10);
                        }
                        itemEntity.playSound(GTSoundEvents.BATH, 0.5F, 1.0F);
                        itemEntity.world.setBlockState(pos, Blocks.AIR.getDefaultState());
                        itemEntity.setItem(replacementStack);
                        cir.setReturnValue(false);
                    }
                }
            }
            // Cleaning onw by one
            if (hardCleaning) {
                if (purifyMap.containsKey(this.prefix)) {
                    BlockPos blockPos = new BlockPos(itemEntity);
                    IBlockState blockState = itemEntity.getEntityWorld().getBlockState(blockPos);
                    if ((blockState.getBlock() instanceof BlockCauldron)) {
                        int waterLevel = blockState.getValue(BlockCauldron.LEVEL);
                        if (waterLevel != 0) {
                            itemEntity.getEntityWorld().setBlockState(blockPos,
                                    blockState.withProperty(BlockCauldron.LEVEL, waterLevel - 1));
                            ItemStack replacementStack = OreDictUnifier.get(purifyMap.get(prefix),
                                    mat, 1);
                            EntityItem cleanStack = new EntityItem(worlds, itemEntity.posX, itemEntity.posY + 0.1,
                                    itemEntity.posZ, replacementStack);
                            itemEntity.world.spawnEntity(cleanStack);
                            if (count > 1) {
                                ItemStack newStack = itemEntity.getItem().copy();
                                newStack.setCount(count - 1);
                                itemEntity.setItem(newStack);
                            } else {
                                itemEntity.setDead();
                            }
                        }
                        cir.setReturnValue(false);
                    }
                }
                cir.cancel();
            }
        }
    }
}
