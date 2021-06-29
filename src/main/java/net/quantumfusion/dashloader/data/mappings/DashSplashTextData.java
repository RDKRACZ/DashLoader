package net.quantumfusion.dashloader.data.mappings;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import net.quantumfusion.dashloader.DashLoader;
import net.quantumfusion.dashloader.data.VanillaData;

import java.util.List;

public class DashSplashTextData {
    @Serialize(order = 0)
    public List<String> splashList;

    public DashSplashTextData(@Deserialize("splashList") List<String> splashList) {
        this.splashList = splashList;
    }

    public DashSplashTextData(VanillaData data, DashLoader.TaskHandler taskHandler) {
        taskHandler.setSubtasks(1);
        splashList = data.getSplashText();
        taskHandler.completedSubTask();
    }

    public List<String> toUndash() {
        return splashList;
    }


}
