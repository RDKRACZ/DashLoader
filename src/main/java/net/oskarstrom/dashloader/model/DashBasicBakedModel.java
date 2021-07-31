package net.oskarstrom.dashloader.model;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import io.activej.serializer.annotations.SerializeNullable;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.BasicBakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;
import net.oskarstrom.dashloader.DashRegistry;
import net.oskarstrom.dashloader.api.annotation.DashObject;
import net.oskarstrom.dashloader.data.DashDirection;
import net.oskarstrom.dashloader.data.serialization.PairMap;
import net.oskarstrom.dashloader.mixin.accessor.BasicBakedModelAccessor;
import net.oskarstrom.dashloader.model.components.DashModelOverrideList;
import net.oskarstrom.dashloader.model.components.DashModelTransformation;
import net.oskarstrom.dashloader.util.DashHelper;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@DashObject(BasicBakedModel.class)
public class DashBasicBakedModel implements DashModel {
	@Serialize(order = 0)
	public final List<Integer> quads;
	@Serialize(order = 1)
	public final PairMap<DashDirection, List<Integer>> faceQuads;
	@Serialize(order = 2)
	public final boolean usesAo;
	@Serialize(order = 3)
	public final boolean hasDepth;
	@Serialize(order = 4)
	public final boolean isSideLit;
	@Serialize(order = 5)
	@SerializeNullable
	public final DashModelTransformation transformation;
	@Serialize(order = 6)
	public final DashModelOverrideList itemPropertyOverrides;
	@Serialize(order = 7)
	public final int spritePointer;


	public DashBasicBakedModel(@Deserialize("quads") List<Integer> quads,
							   @Deserialize("faceQuads") PairMap<DashDirection, List<Integer>> faceQuads,
							   @Deserialize("usesAo") boolean usesAo,
							   @Deserialize("hasDepth") boolean hasDepth,
							   @Deserialize("isSideLit") boolean isSideLit,
							   @Deserialize("transformation") DashModelTransformation transformation,
							   @Deserialize("itemPropertyOverrides") DashModelOverrideList itemPropertyOverrides,
							   @Deserialize("spritePointer") int spritePointer) {
		this.quads = quads;
		this.faceQuads = faceQuads;
		this.usesAo = usesAo;
		this.hasDepth = hasDepth;
		this.isSideLit = isSideLit;
		this.transformation = transformation;
		this.itemPropertyOverrides = itemPropertyOverrides;
		this.spritePointer = spritePointer;
	}

	public DashBasicBakedModel(BasicBakedModel basicBakedModel, DashRegistry registry) {
		BasicBakedModelAccessor access = ((BasicBakedModelAccessor) basicBakedModel);
		quads = new ArrayList<>();
		access.getQuads().forEach(bakedQuad -> quads.add(registry.bakedQuads.register(bakedQuad)));
		final Map<Direction, List<BakedQuad>> faceQuads = access.getFaceQuads();
		this.faceQuads = DashHelper.convertMapToPM(
				faceQuads,
				(direction, bakedQuads) -> Pair.of(new DashDirection(direction), DashHelper.convertList(bakedQuads, registry.bakedQuads::register)));
		itemPropertyOverrides = new DashModelOverrideList(access.getItemPropertyOverrides(), registry);
		usesAo = access.getUsesAo();
		hasDepth = access.getHasDepth();
		isSideLit = access.getIsSideLit();
		final ModelTransformation transformation = access.getTransformation();
		this.transformation = transformation == ModelTransformation.NONE ? null : DashModelTransformation.createDashModelTransformation(transformation);
		spritePointer = registry.sprites.register(access.getSprite());
	}


	@Override
	public BasicBakedModel toUndash(final DashRegistry registry) {
		final Sprite sprite = registry.sprites.getObject(spritePointer);
		final List<BakedQuad> quadsOut = DashHelper.convertList(quads, registry.bakedQuads::getObject);
		final Map<Direction, List<BakedQuad>> faceQuadsOut = DashHelper.convertPairMapToMap(faceQuads, (dashDirection, dashBakedQuads) ->
				Pair.of(dashDirection.toUndash(registry), DashHelper.convertList(dashBakedQuads, registry.bakedQuads::getObject)));

		return new BasicBakedModel(quadsOut, faceQuadsOut, usesAo, isSideLit, hasDepth, sprite, transformation == null ? ModelTransformation.NONE : transformation.toUndash(), itemPropertyOverrides.toUndash(registry));
	}

	@Override
	public void apply(DashRegistry registry) {
		itemPropertyOverrides.applyOverrides(registry);
	}

}
