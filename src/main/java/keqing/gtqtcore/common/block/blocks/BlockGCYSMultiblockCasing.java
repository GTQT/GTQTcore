package keqing.gtqtcore.common.block.blocks;

import gregtech.api.block.VariantBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public class BlockGCYSMultiblockCasing extends VariantBlock<BlockGCYSMultiblockCasing.CasingType> {

    public BlockGCYSMultiblockCasing() {
        super(Material.IRON);
        setTranslationKey("multiblock_casing1");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 1);
        setDefaultState(getState(CasingType.DRILL_HEAD));
    }

    public enum CasingType implements IStringSerializable {
        DRILL_HEAD("drill_head"),
        SUPERHEAVY_QUANTUM_CASING("superheavy_quantum_casing"),

        EXTREME_STRENGTH_TRITANIUM_CASING("extreme_strength_tritanium_casing"),

        MASS_NULLIFIER_PLATFORM("mass_nullifier_platform");

        private final String name;

        @Nonnull
        @Override
        public String getName() {
            return name;
        }

        CasingType(String name) {
            this.name = name;
        }
    }
}
