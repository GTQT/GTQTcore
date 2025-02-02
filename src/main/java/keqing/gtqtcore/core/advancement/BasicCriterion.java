package keqing.gtqtcore.core.advancement;

import keqing.gtqtcore.api.io.advancement.impl.AbstractCriterion;
import net.minecraft.entity.player.EntityPlayerMP;

// Will always succeed when a Advancement Trigger with this Advancement Criterion is fired.
public class BasicCriterion extends AbstractCriterion {
    @Override
    public boolean test(EntityPlayerMP player) {
        return true;
    }

}
