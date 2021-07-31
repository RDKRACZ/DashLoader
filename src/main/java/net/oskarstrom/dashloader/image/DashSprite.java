package net.oskarstrom.dashloader.image;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import io.activej.serializer.annotations.SerializeNullable;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.Sprite;
import net.oskarstrom.dashloader.DashRegistry;
import net.oskarstrom.dashloader.Dashable;
import net.oskarstrom.dashloader.mixin.accessor.SpriteAccessor;
import net.oskarstrom.dashloader.util.UnsafeHelper;

import static net.oskarstrom.dashloader.util.DashHelper.nullable;

public class DashSprite implements Dashable<Sprite> {
	@Serialize(order = 0)
	@SerializeNullable
	public final DashSpriteAnimation animation;
	@Serialize(order = 1)
	public final int x;
	@Serialize(order = 2)
	public final int y;
	@Serialize(order = 3)
	public final int width;
	@Serialize(order = 4)
	public final int height;
	@Serialize(order = 5)
	public final float uMin;
	@Serialize(order = 6)
	public final float uMax;
	@Serialize(order = 7)
	public final float vMin;
	@Serialize(order = 8)
	public final float vMax;
	@Serialize(order = 9)
	public int[] images;


	public DashSprite(@Deserialize("animation") DashSpriteAnimation animation,
					  @Deserialize("x") int x,
					  @Deserialize("y") int y,
					  @Deserialize("width") int width,
					  @Deserialize("height") int height,
					  @Deserialize("uMin") float uMin,
					  @Deserialize("uMax") float uMax,
					  @Deserialize("vMin") float vMin,
					  @Deserialize("vMax") float vMax,
					  @Deserialize("images") int[] images
	) {
		this.animation = animation;
		this.images = images;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.uMin = uMin;
		this.uMax = uMax;
		this.vMin = vMin;
		this.vMax = vMax;
	}

	public DashSprite(Sprite sprite, DashRegistry registry) {
		final NativeImage[] images = ((SpriteAccessor) sprite).getImages();
		this.images = new int[images.length];
		for (int i = 0; i < images.length; i++) {
			this.images[i] = registry.images.register(images[i]);
		}
		x = sprite.getX();
		y = sprite.getY();
		width = sprite.getWidth();
		height = sprite.getHeight();
		uMin = sprite.getMinU();
		uMax = sprite.getMaxU();
		vMin = sprite.getMinV();
		vMax = sprite.getMaxV();
		this.animation = nullable((Sprite.Animation) sprite.getAnimation(), animation1 -> new DashSpriteAnimation(animation1, registry));
	}

	public final Sprite toUndash(final DashRegistry registry) {
		final Sprite out = UnsafeHelper.allocateInstance(Sprite.class);
		final SpriteAccessor spriteAccessor = ((SpriteAccessor) out);
		final NativeImage[] imagesOut = new NativeImage[images.length];
		for (int i = 0; i < images.length; i++) {
			imagesOut[i] = registry.images.getObject(images[i]);
		}
		spriteAccessor.setImages(imagesOut);
		spriteAccessor.setX(x);
		spriteAccessor.setY(y);
		spriteAccessor.setWidth(width);
		spriteAccessor.setHeight(height);
		spriteAccessor.setUMin(uMin);
		spriteAccessor.setUMax(uMax);
		spriteAccessor.setVMin(vMin);
		spriteAccessor.setVMax(vMax);
		spriteAccessor.setAnimation(nullable(animation, animation -> animation.toUndash(out, registry)));
		return out;
	}


}
