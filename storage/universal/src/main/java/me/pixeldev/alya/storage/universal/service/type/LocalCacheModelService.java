package me.pixeldev.alya.storage.universal.service.type;

import me.pixeldev.alya.jdk.functional.FailableConsumer;
import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.internal.meta.ModelMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class LocalCacheModelService<T extends Model> extends AbstractCacheModelService<T> {

	private final Map<String, T> cache;

	public LocalCacheModelService(ModelMeta<T> modelMeta) {
		super(modelMeta);
		this.cache = new ConcurrentHashMap<>();
	}

	@Override
	protected T internalFind(String id) {
		return cache.get(id);
	}

	@Override
	protected boolean internalDelete(String id) {
		return cache.remove(id) != null;
	}

	@Override
	protected List<T> internalFindAll(FailableConsumer<T> failableConsumer) {
		return new ArrayList<>(cache.values());
	}

	@Override
	protected void internalUpload(T model) {
		cache.put(model.getId(), model);
	}

}
