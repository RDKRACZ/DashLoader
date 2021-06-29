package net.quantumfusion.dashloader.data.mappings;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import net.minecraft.client.render.Shader;
import net.quantumfusion.dashloader.data.VanillaData;
import net.quantumfusion.dashloader.image.shader.DashShader;
import net.quantumfusion.dashloader.util.DashHelper;
import net.quantumfusion.dashloader.util.TaskHandler;
import net.quantumfusion.dashloader.util.ThreadHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class DashShaderData {
    @Serialize(order = 0)
    public Map<String, DashShader> shaders;


    public DashShaderData(@Deserialize("shaders") Map<String, DashShader> shaders) {
        this.shaders = shaders;
    }

    public DashShaderData(VanillaData data, TaskHandler taskHandler) {
        taskHandler.setSubtasks(1);
        this.shaders = DashHelper.convertMapValues(data.getShaderData(), DashShader::new);
        taskHandler.completedSubTask();
    }

    public <T> Map<String, Shader> toUndash() {
        Map<String, Shader> out = new ConcurrentHashMap<>();
        List<Callable<T>> callables = new ArrayList<>();
        //noinspection unchecked, stfu
        shaders.forEach((key, value) -> callables.add(() -> (T) out.put(key, value.toUndash())));
        ThreadHelper.exec(callables);
        return out;
    }
}
