package net.oskarstrom.dashloader.blockstate.property.value;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import net.minecraft.util.math.Direction;
import net.oskarstrom.dashloader.DashRegistry;
import net.oskarstrom.dashloader.api.annotation.DashObject;

@DashObject(Direction.class)
public class DashDirectionValue implements DashPropertyValue {
	@Serialize(order = 0)
	public final byte direction;

	public DashDirectionValue(@Deserialize("direction") byte direction) {
		this.direction = direction;
	}

	public DashDirectionValue(Direction direction) {
		this.direction = (byte) direction.getId();
	}


	@Override
	public Direction toUndash(DashRegistry registry) {
		return Direction.byId(direction);
	}
}
