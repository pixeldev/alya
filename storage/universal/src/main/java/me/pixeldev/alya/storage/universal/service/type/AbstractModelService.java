package me.pixeldev.alya.storage.universal.service.type;

import me.pixeldev.alya.jdk.concurrent.observer.Observable;
import me.pixeldev.alya.jdk.concurrent.observer.Observables;
import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.service.CompleteModelService;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class AbstractModelService<T extends Model>
		implements CompleteModelService<T> {

	protected final CompleteModelService<T> cacheModelService;

	public AbstractModelService(CompleteModelService<T> cacheModelService) {
		this.cacheModelService = cacheModelService;
	}

	@Override
	public Observable<Boolean> existsByCacheIdentifier(String value) {
		return Observables.safeObservable(() -> existsByCacheIdentifierSync(value));
	}

	@Override
	public boolean existsByCacheIdentifierSync(String value) throws Exception {
		return findByCacheIdentifierSync(value, false).isPresent();
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
	public Observable<Void> delete(String id) {
		return Observables.safeObservable(() -> deleteSync(id));
	}

	@Override
	public void deleteSync(String id) throws Exception {
		Optional<T> modelOptional = findSync(id, true);

		if (!modelOptional.isPresent()) {
			return;
		}

		deleteSync(modelOptional.get());
	}

	@Override
	public Observable<Void> delete(T model) {
		return Observables.safeObservable(() -> deleteSync(model));
	}

	@Override
	public void deleteSync(T model) throws Exception {
		if (internalDelete(model)) {
			cacheModelService.deleteSync(model);
		}
	}

	@Override
	public Observable<Optional<T>> findByCacheIdentifier(String value,
																											 boolean findInDatabaseToo) {
		return Observables.safeObservable(
				() -> findByCacheIdentifierSync(value, findInDatabaseToo),
				error -> {
					error.printStackTrace();
					return Optional.empty();
				}
		);
	}

	@Override
	public Optional<T> findByCacheIdentifierSync(String value,
																							 boolean findInDatabaseToo) throws Exception {
		return cacheModelService.findByCacheIdentifierSync(value, findInDatabaseToo);
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
		return new Observable<>(this::findAllFromCacheSync);
	}

	@Override
	public List<T> findAllFromCacheSync() {
		return cacheModelService.findAllFromCacheSync();
	}

	@Override
	public Observable<List<T>> findAll(Consumer<T> postLoad) {
		return Observables.safeObservable(() -> findAllSync(postLoad));
	}

	@Override
	public List<T> findAllSync(Consumer<T> postLoad) throws Exception {
		List<T> foundModels = internalFindAll();

		for (T model : foundModels) {
			postLoad.accept(model);
			foundModels.add(model);
			cacheModelService.uploadSync(model, false);
		}

		return foundModels;
	}

	@Override
	public Observable<Void> upload(T model, boolean removeFromCache) {
		return Observables.safeObservable(() -> uploadSync(model, removeFromCache));
	}

	@Override
	public void uploadSync(T model, boolean removeFromCache) throws Exception {
		if (!removeFromCache) {
			cacheModelService.uploadSync(model, false);
		} else {
			cacheModelService.deleteSync(model);
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

	protected abstract Optional<T> internalFind(String id) throws Exception;

	protected abstract boolean internalDelete(T model) throws Exception;

	protected abstract List<T> internalFindAll() throws Exception;

	protected abstract void internalUpload(T model) throws Exception;

}