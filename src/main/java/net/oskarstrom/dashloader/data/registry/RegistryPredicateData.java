package net.oskarstrom.dashloader.data.registry;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import io.activej.serializer.annotations.SerializeSubclasses;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.block.BlockState;
import net.oskarstrom.dashloader.data.registry.storage.FactoryRegistryStorage;
import net.oskarstrom.dashloader.data.serialization.Pointer2ObjectMap;
import net.oskarstrom.dashloader.model.predicates.DashPredicate;

import java.util.function.Predicate;

public class RegistryPredicateData {
	@Serialize(order = 0)
	@SerializeSubclasses(path = {0}, extraSubclassesId = "predicates")
	public final Pointer2ObjectMap<DashPredicate> predicates;

	public RegistryPredicateData(@Deserialize("predicates") Pointer2ObjectMap<DashPredicate> predicates) {
		this.predicates = predicates;
	}


	public RegistryPredicateData(FactoryRegistryStorage<Predicate<BlockState>, DashPredicate> storage) {
		predicates = storage.export();
	}

	public Int2ObjectMap<DashPredicate> toUndash() {
		return predicates.convert();
	}

}
