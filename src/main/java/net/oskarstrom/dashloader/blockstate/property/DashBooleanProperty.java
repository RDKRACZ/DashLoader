package net.oskarstrom.dashloader.blockstate.property;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import net.minecraft.state.property.BooleanProperty;
import net.oskarstrom.dashloader.DashRegistry;
import net.oskarstrom.dashloader.api.annotation.DashObject;

import java.util.Objects;

@DashObject(BooleanProperty.class)
public class DashBooleanProperty implements DashProperty {

	@Serialize(order = 0)
	public final String name;


	public DashBooleanProperty(@Deserialize("name") String name) {
		this.name = name;
	}

	public DashBooleanProperty(BooleanProperty property) {
		name = property.getName();
	}

	@Override
	public BooleanProperty toUndash(DashRegistry registry) {
		return BooleanProperty.of(name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DashBooleanProperty that = (DashBooleanProperty) o;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
