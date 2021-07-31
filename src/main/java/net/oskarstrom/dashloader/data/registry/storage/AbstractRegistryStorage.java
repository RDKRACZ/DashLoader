package net.oskarstrom.dashloader.data.registry.storage;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.oskarstrom.dashloader.DashLoader;
import net.oskarstrom.dashloader.DashRegistry;
import net.oskarstrom.dashloader.Dashable;
import net.oskarstrom.dashloader.data.serialization.Pointer2ObjectMap;
import net.oskarstrom.dashloader.util.ThreadHelper;
import org.apache.logging.log4j.Logger;

/**
 * @param <O> Original Object
 * @param <D> Dash Object
 */
public abstract class AbstractRegistryStorage<O, D extends Dashable<O>> {
	@Serialize(order = 0)
	protected final Int2ObjectMap<D> registryStorage;
	protected String objectName;
	protected DashRegistry registry;
	protected Int2ObjectMap<O> registryStorageUndashed;

	public AbstractRegistryStorage() {
		this.registryStorage = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());
		this.registryStorageUndashed = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());
	}

	public AbstractRegistryStorage(@Deserialize("registryStorage") Int2ObjectMap<D> registryStorage) {
		this.registryStorage = registryStorage;
		this.registryStorageUndashed = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());
	}

	public void init(DashRegistry registry, String objectName) {
		this.objectName = objectName;
		this.registry = registry;
	}

	public void populate(Pointer2ObjectMap<D> dashables) {
		dashables.forEach(dEntry -> registryStorage.put(dEntry.key, dEntry.value));
	}

	public final O getObject(int ptr) {
		final O originalObject = registryStorageUndashed.get(ptr);
		if (originalObject == null) {
			//reified type parameters when?  - leocth
			//DashLoader.LOGGER.error(T.class.getSimpleName() + " not found in data. PINTR: " + ptr);
			DashLoader.LOGGER.error(objectName + " not found in data. PINTR: " + ptr);
		}
		return originalObject;
	}

	protected abstract int register(O originalObject);

	public final void registerDashObject(int ptr, D dashObject) {
		registryStorage.put(ptr, dashObject);
	}

	protected final boolean missing(int ptr) {
		return !registryStorage.containsKey(ptr);
	}

	public void toUndash(Logger logger) {
		registryStorageUndashed = parallelToUndash(registryStorage);
	}

	public int getSize() {
		return registryStorage.size();
	}

	public final Pointer2ObjectMap<D> export() {
		return new Pointer2ObjectMap<>(registryStorage);
	}

	protected final Int2ObjectMap<O> parallelToUndash(Int2ObjectMap<D> in) {
		final Int2ObjectMap<O> out = ThreadHelper.execParallel(in, registry);
		in.clear();
		return out;
	}

	@Override
	public String toString() {
		return objectName + " Registry";
	}
}
