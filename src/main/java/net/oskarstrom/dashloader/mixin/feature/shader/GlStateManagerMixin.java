package net.oskarstrom.dashloader.mixin.feature.shader;

import com.mojang.blaze3d.platform.GlStateManager;
import net.oskarstrom.dashloader.DashLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GlStateManager.class)
public class GlStateManagerMixin {

	@Inject(method = "glShaderSource", at = @At(value = "HEAD"), cancellable = true)
	private static void glShaderSourceInject(int shader, List<String> strings, CallbackInfo ci) {
		DashLoader.getVanillaData().addProgramData(shader, strings);
	}

}

