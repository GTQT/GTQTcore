package keqing.gtqtcore.common.block.blocks;

import gregtech.api.GregTechAPI;
import gregtech.api.block.VariantBlock;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import java.util.Random;
import javax.annotation.Nonnull;

import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class GTQTStoneVariantBlock extends VariantBlock<GTQTStoneVariantBlock.StoneType> {
    private static final PropertyEnum<StoneType> PROPERTY = PropertyEnum.create("variant", StoneType.class);
    private final StoneVariant stoneVariant;

    public GTQTStoneVariantBlock(@Nonnull StoneVariant stoneVariant) {
        super(Material.ROCK);
        this.stoneVariant = stoneVariant;
        this.setRegistryName(stoneVariant.id);
        this.setTranslationKey(stoneVariant.translationKey);
        this.setHardness(stoneVariant.hardness);
        this.setResistance(stoneVariant.resistance);
        this.setSoundType(SoundType.STONE);
        this.setHarvestLevel("pickaxe", 0);
        this.setDefaultState(this.getState(StoneType.GABBRO));
        this.setCreativeTab(GregTechAPI.TAB_GREGTECH_DECORATIONS);
    }

    @Nonnull
    protected BlockStateContainer createBlockState() {
        this.VARIANT = PROPERTY;
        this.VALUES = GTQTStoneVariantBlock.StoneType.values();
        return new BlockStateContainer(this, this.VARIANT);
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }



    protected boolean canSilkHarvest() {
        return this.stoneVariant == GTQTStoneVariantBlock.StoneVariant.SMOOTH;
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock((Block)(this.stoneVariant == GTQTStoneVariantBlock.StoneVariant.SMOOTH ? (Block) GTQTMetaBlocks.GTQT_STONE_BLOCKS.get(GTQTStoneVariantBlock.StoneVariant.COBBLE) : this));
    }

    public static enum StoneVariant {
        SMOOTH("gtqt_stone_smooth"),
        COBBLE("gtqt_stone_cobble", 2.0F, 10.0F),
        BRICKS("gtqt_stone_bricks");

        public final String id;
        public final String translationKey;
        public final float hardness;
        public final float resistance;

        private StoneVariant(@Nonnull String id) {
            this(id, id);
        }

        private StoneVariant(@Nonnull String id, @Nonnull String translationKey) {
            this(id, translationKey, 1.5F, 10.0F);
        }

        private StoneVariant(@Nonnull String id, float hardness, float resistance) {
            this(id, id, hardness, resistance);
        }

        private StoneVariant(@Nonnull String id, @Nonnull String translationKey, float hardness, float resistance) {
            this.id = id;
            this.translationKey = translationKey;
            this.hardness = hardness;
            this.resistance = resistance;
        }
    }

    public static enum StoneType implements IStringSerializable {
        GABBRO("gabbro", MapColor.GRAY),
        GNEISS("gneiss", MapColor.RED_STAINED_HARDENED_CLAY),
        LIMESTONE("limestone", MapColor.GRAY_STAINED_HARDENED_CLAY),
        PHYLLITE("phyllite", MapColor.GRAY),
        QUARTZITE("quartzite", MapColor.QUARTZ),
        SHALE("shale", MapColor.RED_STAINED_HARDENED_CLAY),
        SLATE("slate", MapColor.RED_STAINED_HARDENED_CLAY),
        SOAPSTONE("soapstone", MapColor.GRAY_STAINED_HARDENED_CLAY),
        KIMBERLITE("kimberlite", MapColor.GRAY);

        private final String name;
        public final MapColor mapColor;

        private StoneType(@Nonnull String name, @Nonnull MapColor mapColor) {
            this.name = name;
            this.mapColor = mapColor;
        }

        @Nonnull
        public String getName() {
            return this.name;
        }

        public OrePrefix getOrePrefix() {
            switch (this) {
                case GABBRO:
                case GNEISS:
                case LIMESTONE:
                case PHYLLITE:
                case QUARTZITE:
                case SHALE:
                case SLATE:
                case SOAPSTONE:
                case KIMBERLITE:
                    return OrePrefix.stone;
                default:
                    throw new IllegalStateException("Unreachable");
            }
        }

        public gregtech.api.unification.material.Material getMaterial() {
            switch (this) {
                case GABBRO:
                    return GTQTMaterials.Gabbro;
                case GNEISS:
                    return GTQTMaterials.Gneiss;
                case LIMESTONE:
                    return GTQTMaterials.Limestone;
                case PHYLLITE:
                    return GTQTMaterials.Phyllite;
                case QUARTZITE:
                    return Materials.Quartzite;
                case SHALE:
                    return GTQTMaterials.Shale;
                case SLATE:
                    return GTQTMaterials.Slate;
                case SOAPSTONE:
                    return Materials.Soapstone;
                case KIMBERLITE:
                    return GTQTMaterials.Kimberlite;
                default:
                    throw new IllegalStateException("Unreachable");
            }
        }
    }
}