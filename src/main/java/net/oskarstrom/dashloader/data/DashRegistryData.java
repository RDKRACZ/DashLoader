package net.oskarstrom.dashloader.data;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import io.activej.serializer.annotations.SerializeSubclasses;
import net.oskarstrom.dashloader.DashLoader;
import net.oskarstrom.dashloader.DashRegistry;
import net.oskarstrom.dashloader.api.DashDataClass;
import net.oskarstrom.dashloader.data.registry.*;

import java.util.List;

public class DashRegistryData {
	@Serialize(order = 0)
	public final RegistryBlockStateData blockStateRegistryData;
	@Serialize(order = 1)
	public final RegistryFontData fontRegistryData;
	@Serialize(order = 2)
	public final RegistryIdentifierData identifierRegistryData;
	@Serialize(order = 3)
	public final RegistryPropertyData propertyRegistryData;
	@Serialize(order = 4)
	public final RegistryPropertyValueData propertyValueRegistryData;
	@Serialize(order = 5)
	public final RegistrySpriteData spriteRegistryData;
	@Serialize(order = 6)
	public final RegistryPredicateData predicateRegistryData;
	@Serialize(order = 7)
	public final RegistryBakedQuadData registryBakedQuadData;
	@Serialize(order = 8)
	@SerializeSubclasses(extraSubclassesId = "data", path = {0})
	public final List<DashDataClass> dataClassList;


	public DashRegistryData(@Deserialize("blockStateRegistryData") RegistryBlockStateData blockStateRegistryData,
							@Deserialize("fontRegistryData") RegistryFontData fontRegistryData,
							@Deserialize("identifierRegistryData") RegistryIdentifierData identifierRegistryData,
							@Deserialize("propertyRegistryData") RegistryPropertyData propertyRegistryData,
							@Deserialize("propertyValueRegistryData") RegistryPropertyValueData propertyValueRegistryData,
							@Deserialize("spriteRegistryData") RegistrySpriteData spriteRegistryData,
							@Deserialize("predicateRegistryData") RegistryPredicateData predicateRegistryData,
							@Deserialize("registryBakedQuadData") RegistryBakedQuadData registryBakedQuadData,
							@Deserialize("dataClassList") List<DashDataClass> dataClassList
	) {
		this.blockStateRegistryData = blockStateRegistryData;
		this.fontRegistryData = fontRegistryData;
		this.identifierRegistryData = identifierRegistryData;
		this.propertyRegistryData = propertyRegistryData;
		this.propertyValueRegistryData = propertyValueRegistryData;
		this.spriteRegistryData = spriteRegistryData;
		this.predicateRegistryData = predicateRegistryData;
		this.registryBakedQuadData = registryBakedQuadData;
		this.dataClassList = dataClassList;
	}

	public DashRegistryData(DashRegistry registry) {
		this.blockStateRegistryData = new RegistryBlockStateData(registry.blockstates);
		this.fontRegistryData = new RegistryFontData(registry.fonts);
		this.identifierRegistryData = new RegistryIdentifierData(registry.identifiers);
		this.propertyRegistryData = new RegistryPropertyData(registry.properties);
		this.propertyValueRegistryData = new RegistryPropertyValueData(registry.propertyValues);
		this.spriteRegistryData = new RegistrySpriteData(registry.sprites);
		this.predicateRegistryData = new RegistryPredicateData(registry.predicates);
		this.registryBakedQuadData = new RegistryBakedQuadData(registry.bakedQuads);
		this.dataClassList = DashLoader.getInstance().getApi().dataClasses;
	}

	public void dumpData(DashRegistry dashRegistry) {
		dashRegistry.blockstates.populate(blockStateRegistryData.blockstates);
		dashRegistry.sprites.populate(spriteRegistryData.sprites);
		dashRegistry.fonts.populate(fontRegistryData.fonts);
		dashRegistry.predicates.populate(predicateRegistryData.predicates);
		dashRegistry.properties.populate(propertyRegistryData.property);
		dashRegistry.propertyValues.populate(propertyValueRegistryData.propertyValues);
		dashRegistry.identifiers.populate(identifierRegistryData.identifiers);
		dashRegistry.bakedQuads.populate(registryBakedQuadData.quads);
	}

}
