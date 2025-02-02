package keqing.gtqtcore.core.advancement;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import keqing.gtqtcore.api.io.advancement.IAdvancementCriterion;
import keqing.gtqtcore.api.io.advancement.IAdvancementTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

import static keqing.gtqtcore.api.utils.GTQTUtil.gtqtId;

public class AdvancementTrigger<T extends IAdvancementCriterion> implements IAdvancementTrigger<T> {

    private final ResourceLocation id;
    private final T criterion;
    private final Map<PlayerAdvancements, AdvancementListeners<T>> listeners = Maps.newHashMap();

    public AdvancementTrigger(String name, T criterion) {
        this.id = gtqtId(name);
        this.criterion = criterion;
    }

    @Override
    public void addListener(PlayerAdvancements advancementsIn,
                            Listener<T> listener) {
        AdvancementListeners<T> advancementListener = listeners.get(advancementsIn);
        if (advancementListener == null) {
            advancementListener = new AdvancementListeners<>(advancementsIn);
            listeners.put(advancementsIn, advancementListener);
        }
        advancementListener.add(listener);
    }

    @Override
    public void removeListener( PlayerAdvancements advancementsIn,
                                Listener<T> listener) {
        AdvancementListeners<T> advancementListener = listeners.get(advancementsIn);
        if (advancementListener != null) {
            advancementListener.remove(listener);
            if (advancementListener.isEmpty()) {
                listeners.remove(advancementsIn);
            }
        }
    }

    @Override
    public void removeAllListeners( PlayerAdvancements advancementsIn) {
        listeners.remove(advancementsIn);
    }


    @Override
    public T deserializeInstance( JsonObject object,
                                  JsonDeserializationContext context) {
        return this.criterion;
    }

    @Override
    public void trigger(EntityPlayerMP player) {
        AdvancementListeners<T> listener = listeners.get(player.getAdvancements());
        if (listener != null) {
            listener.trigger(player);
        }
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

}
