package net.oskarstrom.dashloader.blockstate.property.value;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import net.oskarstrom.dashloader.DashRegistry;
import net.oskarstrom.dashloader.api.annotation.DashObject;

@DashObject(Boolean.class)
public class DashBooleanValue implements DashPropertyValue {
	@Serialize(order = 0)
	public final Boolean value;

	public DashBooleanValue(@Deserialize("value") Boolean value) {
		this.value = value;
	}


	@Override
	public Boolean toUndash(DashRegistry registry) {
		return value;
	}
}
