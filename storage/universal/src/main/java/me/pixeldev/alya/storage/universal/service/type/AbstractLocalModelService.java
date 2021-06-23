package me.pixeldev.alya.storage.universal.service.type;

import me.pixeldev.alya.jdk.concurrent.observer.Observable;
import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.internal.CacheIdentifierResolver;
import me.pixeldev.alya.storage.universal.service.CompleteModelService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

abstract class AbstractLocalModelService<T extends Model>
		implements CompleteModelService<T> {

	private static final UnsupportedOperationException UNSUPPORTED_EXCEPTION
			= new UnsupportedOperationException("Local model service cannot perform this action.");

	private final CacheIdentifierResolver<T> resolver;

	public AbstractLocalModelService() {
		resolver = new CacheIdentifierResolver<>();
	}

	@Override
	public Observable<Boolean> existsByCacheIdentifier(String value) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public boolean existsByCacheIdentifierSync(String value) {
		String modelId = resolver.resolve(value);

		return cache().containsKey(modelId);
	}

	@Override
	public Observable<Boolean> exists(String id) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public boolean existsSync(String id) {
		return cache().containsKey(id);
	}

	@Override
	public CompleteModelService<T> getCacheModelService() {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public Observable<Void> delete(String id) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public void deleteSync(String id) {
		findSync(id, false).ifPresent(this::deleteSync);
	}

	@Override
	public Observable<Void> delete(T model) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public void deleteSync(T model) {
		resolver.removeResolver(model);
		cache().remove(model.getId());
	}

	@Override
	public Observable<Optional<T>> findByCacheIdentifier(String value,
																											 boolean findInDatabaseToo) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public Optional<T> findByCacheIdentifierSync(String value,
																							 boolean findInDatabaseToo) {
		String modelId = resolver.resolve(value);

		if (modelId == null) {
			return Optional.empty();
		}

		return Optional.ofNullable(cache().get(modelId));
	}

	@Override
	public Observable<Optional<T>> find(String id,
																			boolean findInDatabaseToo) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public Optional<T> findSync(String id,
															boolean findInDatabaseToo) {
		return Optional.ofNullable(cache().get(id));
	}

	@Override
	public Observable<List<T>> findAllFromCache() {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public List<T> findAllFromCacheSync() {
		return new ArrayList<>(cache().values());
	}

	@Override
	public Observable<List<T>> findAll(Consumer<T> postLoad) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public List<T> findAllSync(Consumer<T> postLoad) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public Observable<Void> upload(T model, boolean removeFromCache) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public void uploadSync(T model, boolean removeFromCache) {
		resolver.addResolver(model);
		cache().put(model.getId(), model);
	}

	@Override
	public Observable<Void> uploadAll(Consumer<T> prePersist) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public void uploadAllSync(Consumer<T> prePersist) {
		throw UNSUPPORTED_EXCEPTION;
	}

	protected abstract Map<String, T> cache();

}