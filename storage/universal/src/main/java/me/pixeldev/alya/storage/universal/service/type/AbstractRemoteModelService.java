package me.pixeldev.alya.storage.universal.service.type;

import me.pixeldev.alya.jdk.concurrent.AsyncResponse;
import me.pixeldev.alya.jdk.concurrent.SimpleAsyncResponse;
import me.pixeldev.alya.jdk.concurrent.SimpleResponse;
import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.internal.meta.Cached;
import me.pixeldev.alya.storage.universal.internal.meta.ModelMeta;
import me.pixeldev.alya.storage.universal.service.CompleteModelService;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static me.pixeldev.alya.jdk.concurrent.AsyncExecutor.EXECUTOR;

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
	public CompleteModelService<T> getCacheModelService() {
		return cacheModelService;
	}

	@Override
	public AsyncResponse<Boolean> delete(String id) {
		return new SimpleAsyncResponse<>(
				CompletableFuture.supplyAsync(() -> {
					try {
						return SimpleResponse.success(deleteSync(id));
					} catch (Exception e) {
						return SimpleResponse.error(e);
					}
				}, EXECUTOR)
		);
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
	public AsyncResponse<T> findByCacheIdentifier(String value) {
		return new SimpleAsyncResponse<>(
				CompletableFuture.supplyAsync(() -> {
					try {
						return SimpleResponse.success(findByCacheIdentifierSync(value));
					} catch (Exception e) {
						return SimpleResponse.error(e);
					}
				}, EXECUTOR)
		);
	}

	@Override
	public T findByCacheIdentifierSync(String value) throws Exception {
		if (cacheModelService == null) {
			return null;
		}

		return cacheModelService.findByCacheIdentifierSync(value);
	}

	@Override
	public AsyncResponse<T> find(String id,
															 boolean findInDatabaseToo) {
		return new SimpleAsyncResponse<>(
				CompletableFuture.supplyAsync(() -> {
					try {
						return SimpleResponse.success(findSync(id, findInDatabaseToo));
					} catch (Exception e) {
						return SimpleResponse.error(e);
					}
				}, EXECUTOR)
		);
	}

	@Override
	public T findSync(String id,
										boolean findInDatabaseToo) throws Exception {
		if (cacheModelService == null) {
			return internalFind(id);
		}

		T foundModel = cacheModelService.findSync(id, findInDatabaseToo);

		if (foundModel == null) {
			if (findInDatabaseToo) {
				T foundModelInDatabase = internalFind(id);

				if (foundModelInDatabase != null) {
					cacheModelService.uploadSync(foundModelInDatabase, false);
				}

				return foundModelInDatabase;
			} else {
				return null;
			}
		} else {
			return foundModel;
		}
	}

	@Override
	public AsyncResponse<List<T>> findAllFromCache() {
		return new SimpleAsyncResponse<>(
				CompletableFuture.supplyAsync(() -> {
					try {
						return SimpleResponse.success(findAllFromCacheSync());
					} catch (Exception e) {
						return SimpleResponse.error(e);
					}
				}, EXECUTOR)
		);
	}

	@Override
	public List<T> findAllFromCacheSync() throws Exception {
		if (cacheModelService == null) {
			return Collections.emptyList();
		}

		return cacheModelService.findAllFromCacheSync();
	}

	@Override
	public AsyncResponse<List<T>> findAll(Consumer<T> postLoad) {
		return new SimpleAsyncResponse<>(
				CompletableFuture.supplyAsync(() -> {
					try {
						return SimpleResponse.success(findAllSync(postLoad));
					} catch (Exception e) {
						return SimpleResponse.error(e);
					}
				}, EXECUTOR)
		);
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
	public AsyncResponse<Void> upload(T model, boolean removeFromCache) {
		return new SimpleAsyncResponse<>(
				CompletableFuture.supplyAsync(() -> {
					try {
						uploadSync(model, removeFromCache);
						return SimpleResponse.success(null);
					} catch (Exception e) {
						return SimpleResponse.error(e);
					}
				}, EXECUTOR)
		);
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
	public AsyncResponse<Void> uploadAll(Consumer<T> prePersist) {
		return new SimpleAsyncResponse<>(
				CompletableFuture.supplyAsync(() -> {
					try {
						uploadAllSync(prePersist);
						return SimpleResponse.success(null);
					} catch (Exception e) {
						return SimpleResponse.error(e);
					}
				}, EXECUTOR)
		);
	}

	@Override
	public void uploadAllSync(Consumer<T> prePersist) throws Exception {
		for (T model : findAllFromCacheSync()) {
			prePersist.accept(model);
			uploadSync(model, true);
		}
	}

}
