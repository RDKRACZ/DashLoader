package net.oskarstrom.dashloader.util.enums;

import net.oskarstrom.dashloader.DashLoader;

import java.nio.file.Path;

public enum DashCachePaths {
	REGISTRY_CACHE("registry-data"),
	REGISTRY_MODEL_CACHE("registry-model-data"),
	REGISTRY_IMAGE_CACHE("registry-image-data"),
	MAPPINGS_CACHE("mappings-data");


	private final String path;

	DashCachePaths(String path) {
		this.path = path;
	}

	public Path getPath() {
		return DashLoader.getInstance().getResourcePackBoundDir().resolve(path + ".activej");

	}
}
