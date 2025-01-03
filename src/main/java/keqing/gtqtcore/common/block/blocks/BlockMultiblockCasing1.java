package keqing.gtqtcore.common.block.blocks;


import gregtech.api.block.VariantBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;

public class BlockMultiblockCasing1 extends VariantBlock<BlockMultiblockCasing1.CasingType> {


    public BlockMultiblockCasing1() {
        super(Material.IRON);
        this.setTranslationKey("multiblock_casing1");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 1);
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public static enum CasingType implements IStringSerializable {

        Inconel625("inconel_625"),
        HastelloyN("hastelloy_n"),
        Stellite("stellite"),
        Hdcs("hdcs"),
        Lafium("lafium"),
        BlackTitanium("black_titanium"),
        Talonite("talonite"),
        BlackPlutonium("black_plutonium"),
        MaragingSteel250("maraging_steel_250"),
        Staballoy("staballoy"),
        BabbittAlloy("babbitt_alloy"),
        ZirconiumCarbide("zirconium_carbide"),
        Inconel792("inconel_792"),
        IncoloyMA813("incoloy_ma_813"),
        HastelloyX78("hastelloy_x_78"),
        HastelloyK243("hastelloy_k_243");
        private final String name;

        CasingType(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}