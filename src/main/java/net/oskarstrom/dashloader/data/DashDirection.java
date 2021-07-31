package net.oskarstrom.dashloader.data;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import net.minecraft.util.math.Direction;
import net.oskarstrom.dashloader.DashRegistry;
import net.oskarstrom.dashloader.Dashable;

public class DashDirection implements Dashable {
	@Serialize(order = 0)
	public final byte id;

	public DashDirection(@Deserialize("id") byte id) {
		this.id = id;
	}

	public DashDirection(Direction direction) {
		id = (byte) direction.getId();
	}

	public Direction toUndash(DashRegistry registry) {
		return Direction.byId(id);
	}
}
