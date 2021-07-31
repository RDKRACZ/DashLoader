package net.oskarstrom.dashloader.model;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import io.activej.serializer.annotations.SerializeNullable;
import net.minecraft.client.render.model.BuiltinBakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.oskarstrom.dashloader.DashRegistry;
import net.oskarstrom.dashloader.api.annotation.DashObject;
import net.oskarstrom.dashloader.mixin.accessor.BuiltinBakedModelAccessor;
import net.oskarstrom.dashloader.model.components.DashModelOverrideList;
import net.oskarstrom.dashloader.model.components.DashModelTransformation;

@DashObject(BuiltinBakedModel.class)
public class DashBuiltinBakedModel implements DashModel {
	@Serialize(order = 0)
	@SerializeNullable
	public final DashModelTransformation transformation;
	@Serialize(order = 1)
	public final DashModelOverrideList itemPropertyOverrides;
	@Serialize(order = 2)
	public final int spritePointer;
	@Serialize(order = 3)
	public final boolean sideLit;

	public DashBuiltinBakedModel(
			@Deserialize("transformation") DashModelTransformation transformation,
			@Deserialize("itemPropertyOverrides") DashModelOverrideList itemPropertyOverrides,
			@Deserialize("spritePointer") int spritePointer,
			@Deserialize("sideLit") boolean sideLit) {
		this.transformation = transformation;
		this.itemPropertyOverrides = itemPropertyOverrides;
		this.spritePointer = spritePointer;
		this.sideLit = sideLit;
	}


	public DashBuiltinBakedModel(BuiltinBakedModel builtinBakedModel, DashRegistry registry) {
		BuiltinBakedModelAccessor access = ((BuiltinBakedModelAccessor) builtinBakedModel);
		final ModelTransformation transformation = access.getTransformation();
		this.transformation = transformation == ModelTransformation.NONE ? null : DashModelTransformation.createDashModelTransformation(transformation);
		itemPropertyOverrides = new DashModelOverrideList(access.getItemPropertyOverrides(), registry);
		spritePointer = registry.sprites.register(access.getSprite());
		sideLit = access.getSideLit();
	}


	@Override
	public BuiltinBakedModel toUndash(DashRegistry registry) {
		Sprite sprite = registry.sprites.getObject(spritePointer);
		return new BuiltinBakedModel(transformation == null ? ModelTransformation.NONE : transformation.toUndash(), itemPropertyOverrides.toUndash(registry), sprite, sideLit);
	}

	@Override
	public void apply(DashRegistry registry) {
		itemPropertyOverrides.applyOverrides(registry);
	}

}
