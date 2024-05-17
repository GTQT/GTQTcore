package keqing.gtqtcore.common.entities;

import gregtech.common.entities.EntityGTExplosive;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import static keqing.gtqtcore.common.block.GTQTMetaBlocks.STNT;

public class STNTEntity extends EntityGTExplosive {

    public STNTEntity(World world, double x, double y, double z, EntityLivingBase exploder) {
        super(world, x, y, z, exploder);
    }

    @SuppressWarnings("unused")
    public STNTEntity(World world) {
        super(world);
    }

    @Override
    protected float getStrength() {
        return 12;
    }

    @Override
    public boolean dropsAllBlocks() {
        return false;
    }

    @Override
    protected int getRange() {
        return 24;
    }

    @Override
    public  IBlockState getExplosiveState() {
        return STNT.getDefaultState();
    }
}