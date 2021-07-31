package net.oskarstrom.dashloader.mixin;

import net.minecraft.client.texture.Sprite;
import net.oskarstrom.dashloader.util.duck.SpriteInterpolationDuck;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Sprite.Interpolation.class)
public class SpriteInterpolationMixin implements SpriteInterpolationDuck {


	@SuppressWarnings("ShadowTarget")
	@Shadow
	@Final
	@Mutable
	private Sprite field_21757;


	@Override
	public void interpolation(Sprite owner) {
		field_21757 = owner;
	}
}
