package me.pixeldev.alya.storage.universal.service.type;

import me.pixeldev.alya.jdk.concurrent.AsyncExecutor;
import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.service.CompleteModelService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class AbstractModelService<T extends Model>
    implements CompleteModelService<T> {

	private final AsyncExecutor executor;

  protected final CompleteModelService<T> cacheModelService;

  public AbstractModelService(AsyncExecutor executor,
                              CompleteModelService<T> cacheModelService) {
    this.executor = executor;
    this.cacheModelService = cacheModelService;
  }

  @Override
  public CompletableFuture<Boolean> existsByCacheIdentifier(String value) {
    return executor.supply(
        () -> existsByCacheIdentifierSync(value)
    );
  }

  @Override
  public boolean existsByCacheIdentifierSync(String value) throws Exception {
    return findByCacheIdentifierSync(value, false).isPresent();
  }

  @Override
  public CompletableFuture<Boolean> exists(String id) {
    return executor.supply(
        () -> existsSync(id)
    );
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
  public CompletableFuture<Void> delete(String id) {
    return executor.run(
        () -> deleteSync(id)
    );
  }

  @Override
  public void deleteSync(String id) throws Exception {
    findSync(id, true)
        .ifPresent(this::delete);
  }

  @Override
  public CompletableFuture<Void> delete(T model) {
    return executor.run(
        () -> deleteSync(model)
    );
  }

  @Override
  public void deleteSync(T model) throws Exception {
    if (internalDelete(model)) {
      cacheModelService.deleteSync(model);
    }
  }

  @Override
  public CompletableFuture<Optional<T>> findByCacheIdentifier(String value,
                                                              boolean findInDatabaseToo) {
    return executor.supply(
        () -> findByCacheIdentifierSync(value, findInDatabaseToo)
    );
  }

  @Override
  public Optional<T> findByCacheIdentifierSync(String value,
                                               boolean findInDatabaseToo) throws Exception {
    return cacheModelService.findByCacheIdentifierSync(value, findInDatabaseToo);
  }

  @Override
  public CompletableFuture<Optional<T>> find(String id,
                                             boolean findInDatabaseToo) {
    return executor.supply(
        () -> findSync(id, findInDatabaseToo)
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
  public CompletableFuture<List<T>> findAllFromCache() {
    return executor.supply(
        this::findAllFromCacheSync
    );
  }

  @Override
  public List<T> findAllFromCacheSync() {
    return cacheModelService.findAllFromCacheSync();
  }

  @Override
  public CompletableFuture<List<T>> findAll(Consumer<T> postLoad) {
    return executor.supply(
        () -> findAllSync(postLoad)
    );
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
  public CompletableFuture<Void> upload(T model, boolean removeFromCache) {
    return executor.run(
        () -> uploadSync(model, removeFromCache)
    );
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
  public CompletableFuture<Void> uploadAll(Consumer<T> prePersist) {
    return executor.run(
        () -> uploadAllSync(prePersist)
    );
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