package keqing.gtqtcore.client.particle;


import codechicken.lib.vec.Vector3;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.client.particle.GTLaserBeamParticle;
import gregtech.client.utils.EffectRenderContext;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import static keqing.gtqtcore.GTQTCore.MODID;

public class LaserBeamParticle extends GTLaserBeamParticle {
    private int particleAge = 0;
    private final int particleMaxAge;
    public static final ResourceLocation body = new ResourceLocation(MODID,"textures/laser/laser.png");
    public static final ResourceLocation head = new ResourceLocation(MODID,"textures/laser/laser_start.png");

    public LaserBeamParticle(MetaTileEntity mte, Vector3 startPos, Vector3 endPos, int maxAge) {
        super(mte, startPos, endPos);
        this.particleMaxAge = maxAge;
        this.setBody(body);
        this.setHead(head);
        this.setEmit(1.0F);
    }
    public LaserBeamParticle(MetaTileEntity mte, BlockPos startPos, BlockPos endPos, int maxAge) {

        super(mte, new Vector3(startPos.getX()+0.5,startPos.getY()+0.5,startPos.getZ()+0.5),new Vector3(endPos.getX()+0.5,endPos.getY()+0.5,endPos.getZ()+0.5));
        this.particleMaxAge = maxAge;
        this.setBody(body);
        this.setHead(head);
        this.setEmit(1.0F);
    }
    public float getAlpha() {
        return (float)this.particleAge / (float)this.particleMaxAge;
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.particleAge != this.particleMaxAge) {
            this.setAlpha(1.0F - (float)this.particleAge / (float)this.particleMaxAge);
            ++this.particleAge;
        } else {
            this.setExpired();
        }

    }

    public void renderParticle( BufferBuilder buffer,  EffectRenderContext context) {
        this.setAlpha(1.0F - (float)this.particleAge / (float)this.particleMaxAge);
        super.renderParticle(buffer, context);
    }
}
