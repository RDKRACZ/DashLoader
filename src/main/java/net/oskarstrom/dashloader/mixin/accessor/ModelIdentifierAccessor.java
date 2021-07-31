package net.oskarstrom.dashloader.mixin.accessor;

import net.minecraft.client.util.ModelIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ModelIdentifier.class)
public interface ModelIdentifierAccessor {
	@Invoker("<init>")
	static ModelIdentifier init(String[] strings) {
		throw new AssertionError();
	}

	@Accessor
	void setVariant(String variant);

}
