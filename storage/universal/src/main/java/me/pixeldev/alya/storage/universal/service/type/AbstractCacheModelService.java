package me.pixeldev.alya.storage.universal.service.type;

import me.pixeldev.alya.jdk.concurrent.AsyncResponse;
import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.internal.CacheIdentifierResolver;
import me.pixeldev.alya.storage.universal.internal.meta.ModelMeta;
import me.pixeldev.alya.storage.universal.service.CompleteModelService;

import java.util.List;
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
	public CompleteModelService<T> getCacheModelService() {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public AsyncResponse<Boolean> delete(String id) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public boolean deleteSync(String id) throws Exception {
		if (resolver == null) {
			return internalDelete(id);
		} else {
			T model = internalFind(id);

			if (model == null) {
				return false;
			}

			return deleteSync(model);
		}
	}

	@Override
	public AsyncResponse<Boolean> delete(T model) {
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
	public AsyncResponse<T> findByCacheIdentifier(String value) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public T findByCacheIdentifierSync(String value) throws Exception {
		if (resolver == null) {
			return null;
		}

		String modelId = resolver.resolve(value);

		if (modelId == null) {
			return null;
		}

		return internalFind(modelId);
	}

	@Override
	public AsyncResponse<T> find(String id,
																			boolean findInDatabaseToo) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public T findSync(String id,
															boolean findInDatabaseToo) throws Exception {
		return internalFind(id);
	}

	@Override
	public AsyncResponse<List<T>> findAllFromCache() {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public List<T> findAllFromCacheSync() throws Exception {
		return internalFindAll(model -> {});
	}

	@Override
	public AsyncResponse<List<T>> findAll(Consumer<T> postLoad) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public List<T> findAllSync(Consumer<T> postLoad) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public AsyncResponse<Void> upload(T model, boolean removeFromCache) {
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
	public AsyncResponse<Void> uploadAll(Consumer<T> prePersist) {
		throw UNSUPPORTED_EXCEPTION;
	}

	@Override
	public void uploadAllSync(Consumer<T> prePersist) {
		throw UNSUPPORTED_EXCEPTION;
	}

}
