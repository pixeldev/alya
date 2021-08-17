package me.pixeldev.alya.storage.universal.internal.meta;

import me.pixeldev.alya.jdk.reflect.MethodProvider;
import me.pixeldev.alya.storage.universal.Model;

import java.lang.reflect.Method;

public class ModelMeta<T extends Model> {

	private final Method prePersistMethod;
	private final Method postLoadMethod;
	private final Cached.CacheType cacheType;
	private final Class<T> type;
	private final boolean createCacheResolver;

	public ModelMeta(Class<T> type) {
		this.type = type;
		prePersistMethod = MethodProvider.getMethodByAnnotation(
				type,
				PrePersist.class
		).orElse(null);

		postLoadMethod = MethodProvider.getMethodByAnnotation(
				type,
				PostLoad.class
		).orElse(null);

		Cached cached = type.getAnnotation(Cached.class);

		if (cached == null) {
			cacheType = null;
		} else {
			cacheType = cached.value();
		}

		boolean temporalCreateCacheResolver;

		try {
			temporalCreateCacheResolver = type
					.getMethod("getCacheIdentifier")
					.getDeclaringClass() != Model.class;
		} catch (NoSuchMethodException e) {
			temporalCreateCacheResolver = false;
		}

		createCacheResolver = temporalCreateCacheResolver;
	}

	public Class<T> getType() {
		return type;
	}

	public Cached.CacheType getCacheType() {
		return cacheType;
	}

	public Method getPrePersistMethod() {
		return prePersistMethod;
	}

	public Method getPostLoadMethod() {
		return postLoadMethod;
	}

	public boolean canCreateCacheResolver() {
		return createCacheResolver;
	}

}