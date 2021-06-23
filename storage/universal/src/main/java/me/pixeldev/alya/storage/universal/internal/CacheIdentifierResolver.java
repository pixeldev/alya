package me.pixeldev.alya.storage.universal.internal;

import me.pixeldev.alya.storage.universal.Model;

import java.util.HashMap;
import java.util.Map;

public class CacheIdentifierResolver<T extends Model> {

	private final Map<String, String> resolver;

	public CacheIdentifierResolver() {
		resolver = new HashMap<>();
	}

	public void removeResolver(String cacheIdentifier) {
		resolver.remove(cacheIdentifier);
	}

	public void removeResolver(T model) {
		removeResolver(invoke(model));
	}

	public void addResolver(String cacheIdentifier, String modelId) {
		resolver.put(cacheIdentifier, modelId);
	}

	public void addResolver(T model) {
		addResolver(invoke(model), model.getId());
	}

	public String resolve(String cacheIdentifier) {
		return resolver.get(cacheIdentifier);
	}

	private String invoke(T model) {
		return model.getCacheIdentifier();
	}

}