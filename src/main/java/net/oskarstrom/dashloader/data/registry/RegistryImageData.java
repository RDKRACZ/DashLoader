package net.oskarstrom.dashloader.data.registry;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import net.minecraft.client.texture.NativeImage;
import net.oskarstrom.dashloader.DashRegistry;
import net.oskarstrom.dashloader.data.registry.storage.AbstractRegistryStorage;
import net.oskarstrom.dashloader.data.serialization.Pointer2ObjectMap;
import net.oskarstrom.dashloader.image.DashImage;

public class RegistryImageData {
	@Serialize(order = 0)
	public final Pointer2ObjectMap<DashImage> images;

	public RegistryImageData(@Deserialize("images") Pointer2ObjectMap<DashImage> images) {
		this.images = images;
	}

	public RegistryImageData(AbstractRegistryStorage<NativeImage, DashImage> storage) {
		images = storage.export();
	}

	public void dumpData(DashRegistry registry) {
		registry.images.populate(images);
	}


}
