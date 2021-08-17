package me.pixeldev.alya.storage.universal.service.type;

import me.pixeldev.alya.jdk.concurrent.observer.Observable;
import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.internal.CacheIdentifierResolver;
import me.pixeldev.alya.storage.universal.internal.meta.ModelMeta;
import me.pixeldev.alya.storage.universal.service.CompleteModelService;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class AbstractCacheModelService<T extends Model>
		extends AbstractModelService<T> {

	private static final UnsupportedOperationException UNSUPPORTED_EXCEPTION
			= new UnsupportedOperationException("Local model service cannot perform this action.");

	private final CacheIdentifierResolver<T> resolver;

	public AbstractCacheModelService(ModelMeta<T> modelMeta) {
		if (modelMeta.canCreateCacheResolver()) {
			resolver = new CacheIdentifierResolver<>();
		} else {
			resolver = null;
		}
	}

	@Override
	public Observable<Boolean> existsByCacheIdentifier(String value) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public boolean existsByCacheIdentifierSync(String value) throws Exception {
		if (resolver == null) {
			return false;
		}

		return internalFind(resolver.resolve(value)).isPresent();
	}

	@Override
	public Observable<Boolean> exists(String id) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public boolean existsSync(String id) throws Exception {
		return internalFind(id).isPresent();
	}

	@Override
	public CompleteModelService<T> getCacheModelService() {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public Observable<Boolean> delete(String id) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public boolean deleteSync(String id) throws Exception {
		if (resolver == null) {
			return internalDelete(id);
		} else {
			Optional<T> modelOptional = internalFind(id);

			if (!modelOptional.isPresent()) {
				return false;
			}

			return deleteSync(modelOptional.get());
		}
	}

	@Override
	public Observable<Boolean> delete(T model) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public boolean deleteSync(T model) throws Exception {
		if (resolver != null) {
			resolver.removeResolver(model);
		}

		return internalDelete(model.getId());
	}

	@Override
	public Observable<Optional<T>> findByCacheIdentifier(String value) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public Optional<T> findByCacheIdentifierSync(String value) throws Exception {
		if (resolver == null) {
			return Optional.empty();
		}

		String modelId = resolver.resolve(value);

		if (modelId == null) {
			return Optional.empty();
		}

		return internalFind(modelId);
	}

	@Override
	public Observable<Optional<T>> find(String id,
																			boolean findInDatabaseToo) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public Optional<T> findSync(String id,
															boolean findInDatabaseToo) throws Exception {
		return internalFind(id);
	}

	@Override
	public Observable<List<T>> findAllFromCache() {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public List<T> findAllFromCacheSync() throws Exception {
		return internalFindAll(model -> {});
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
	public void uploadSync(T model, boolean removeFromCache) throws Exception {
		if (resolver != null) {
			resolver.addResolver(model);
		}

		internalUpload(model);
	}

	@Override
	public Observable<Void> uploadAll(Consumer<T> prePersist) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public void uploadAllSync(Consumer<T> prePersist) {
		throw UNSUPPORTED_EXCEPTION;
	}

}