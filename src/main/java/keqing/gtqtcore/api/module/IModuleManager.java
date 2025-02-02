package keqing.gtqtcore.api.module;

import net.minecraft.util.ResourceLocation;

import static keqing.gtqtcore.api.utils.GTQTUtil.gtqtId;

public interface IModuleManager {

    boolean isModuleEnabled(ResourceLocation namespace);

    default boolean isModuleEnabled(String containerID, String moduleID) {
        return this.isModuleEnabled(new ResourceLocation(containerID, moduleID));
    }

    default boolean isModuleEnabled(String moduleID) {
        return isModuleEnabled(gtqtId(moduleID));
    }

    void registerContainer(IModuleContainer container);

    IModuleContainer getLoadedContainer();

    ModuleStage getStage();

    boolean hasPassedStage(ModuleStage stage);

}
