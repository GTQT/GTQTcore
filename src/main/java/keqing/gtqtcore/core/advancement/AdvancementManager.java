package keqing.gtqtcore.core.advancement;

import keqing.gtqtcore.api.GTQTAPI;
import keqing.gtqtcore.api.io.advancement.IAdvancementCriterion;
import keqing.gtqtcore.api.io.advancement.IAdvancementManager;
import keqing.gtqtcore.api.io.advancement.IAdvancementTrigger;
import keqing.gtqtcore.api.module.ModuleStage;
import keqing.gtqtcore.api.utils.GTQTLog;
import net.minecraft.advancements.CriteriaTriggers;

import static keqing.gtqtcore.GTQTCore.moduleManager;

public class AdvancementManager implements IAdvancementManager {

    private static final AdvancementManager INSTANCE = new AdvancementManager();

    private AdvancementManager() {}

    public static AdvancementManager getInstance() {
        return INSTANCE;
    }

    @Override
    public <T extends IAdvancementCriterion> IAdvancementTrigger<T> registerTrigger(String id, T criterion) {
        if (moduleManager.hasPassedStage(ModuleStage.PRE_INIT)) {
            GTQTLog.logger.error("Could not register Advancement Trigger {}, as trigger registration has ended!", id);
            return null;
        }
        IAdvancementTrigger<T> trigger = new AdvancementTrigger<>(id, criterion);
        criterion.setId(trigger.getId());
        CriteriaTriggers.register(trigger);
        return trigger;
    }

}
