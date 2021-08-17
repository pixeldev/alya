package me.pixeldev.alya.storage.universal.service.type;

import me.pixeldev.alya.jdk.concurrent.observer.Observable;
import me.pixeldev.alya.jdk.concurrent.observer.Observables;
import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.internal.meta.Cached;
import me.pixeldev.alya.storage.universal.internal.meta.ModelMeta;
import me.pixeldev.alya.storage.universal.service.CompleteModelService;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class AbstractRemoteModelService<T extends Model>
		extends AbstractModelService<T> {

	protected final ModelMeta<T> modelMeta;
	protected final Class<T> classType;
	protected final CompleteModelService<T> cacheModelService;
	protected final Method postLoadMethod;
	protected final Method prePersistMethod;

	public AbstractRemoteModelService(ModelMeta<T> modelMeta) {
		this.modelMeta = modelMeta;
		classType = modelMeta.getType();
		prePersistMethod = modelMeta.getPrePersistMethod();
		postLoadMethod = modelMeta.getPostLoadMethod();
		Cached.CacheType cacheType = modelMeta.getCacheType();

		if (cacheType == null) {
			cacheModelService = null;
		} else {
			switch (cacheType) {
				case REDIS:
				case LOCAL: {
					cacheModelService = new LocalCacheModelService<>(modelMeta);
					break;
				}
				default:
					cacheModelService = null;
					break;
			}
		}
	}

	@Override
	public Observable<Boolean> existsByCacheIdentifier(String value) {
		return Observables.safeObservable(() -> existsByCacheIdentifierSync(value));
	}

	@Override
	public boolean existsByCacheIdentifierSync(String value) throws Exception {
		return findByCacheIdentifierSync(value).isPresent();
	}

	@Override
	public Observable<Boolean> exists(String id) {
		return Observables.safeObservable(() -> existsSync(id));
	}

	@Override
	public boolean existsSync(String id) throws Exception {
		return findSync(id, false).isPresent();
	}

	@Override
	public CompleteModelService<T> getCacheModelService() {
		return cacheModelService;
	}

	@Override
	public Observable<Boolean> delete(String id) {
		return Observables.safeObservable(() -> deleteSync(id));
	}

	@Override
	public boolean deleteSync(String id) throws Exception {
		if (internalDelete(id)) {
			if (cacheModelService != null) {
				return cacheModelService.deleteSync(id);
			}

			return true;
		}

		return false;
	}

	@Override
	public Observable<Optional<T>> findByCacheIdentifier(String value) {
		return Observables.safeObservable(
				() -> findByCacheIdentifierSync(value),
				error -> {
					error.printStackTrace();
					return Optional.empty();
				}
		);
	}

	@Override
	public Optional<T> findByCacheIdentifierSync(String value) throws Exception {
		if (cacheModelService == null) {
			return Optional.empty();
		}

		return cacheModelService.findByCacheIdentifierSync(value);
	}

	@Override
	public Observable<Optional<T>> find(String id,
																			boolean findInDatabaseToo) {
		return Observables.safeObservable(
				() -> findSync(id, findInDatabaseToo),
				error -> {
					error.printStackTrace();
					return Optional.empty();
				}
		);
	}

	@Override
	public Optional<T> findSync(String id,
															boolean findInDatabaseToo) throws Exception {
		if (cacheModelService == null) {
			return internalFind(id);
		}

		Optional<T> foundModel = cacheModelService.findSync(id, findInDatabaseToo);

		if (!foundModel.isPresent()) {
			if (findInDatabaseToo) {
				Optional<T> foundModelInDatabase = internalFind(id);

				if (foundModelInDatabase.isPresent()) {
					T model = foundModelInDatabase.get();

					cacheModelService.uploadSync(model, false);
				}

				return foundModelInDatabase;
			} else {
				return Optional.empty();
			}
		}

		return foundModel;
	}

	@Override
	public Observable<List<T>> findAllFromCache() {
		return Observables.safeObservable(this::findAllFromCacheSync);
	}

	@Override
	public List<T> findAllFromCacheSync() throws Exception {
		if (cacheModelService == null) {
			return Collections.emptyList();
		}

		return cacheModelService.findAllFromCacheSync();
	}

	@Override
	public Observable<List<T>> findAll(Consumer<T> postLoad) {
		return Observables.safeObservable(() -> findAllSync(postLoad));
	}

	@Override
	public List<T> findAllSync(Consumer<T> postLoad) throws Exception {
		return internalFindAll(model -> {
			postLoad.accept(model);

			if (cacheModelService == null) {
				return;
			}

			cacheModelService.uploadSync(model, false);
		});
	}

	@Override
	public Observable<Void> upload(T model, boolean removeFromCache) {
		return Observables.safeObservable(() -> uploadSync(model, removeFromCache));
	}

	@Override
	public void uploadSync(T model, boolean removeFromCache) throws Exception {
		if (cacheModelService != null) {
			if (!removeFromCache) {
				cacheModelService.uploadSync(model, false);
			} else {
				cacheModelService.deleteSync(model);
			}
		}

		internalUpload(model);
	}

	@Override
	public Observable<Void> uploadAll(Consumer<T> prePersist) {
		return Observables.safeObservable(() -> uploadAllSync(prePersist));
	}

	@Override
	public void uploadAllSync(Consumer<T> prePersist) throws Exception {
		for (T model : findAllFromCacheSync()) {
			prePersist.accept(model);
			uploadSync(model, true);
		}
	}

}