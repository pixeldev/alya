package me.pixeldev.alya.storage.universal.service;

import me.pixeldev.alya.storage.universal.Model;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface FindService<T extends Model> {

  CompletableFuture<Optional<T>> findByCacheIdentifier(String value, boolean findInDatabaseToo);

  Optional<T> findByCacheIdentifierSync(String value, boolean findInDatabaseToo) throws Exception;

  CompletableFuture<Optional<T>> find(String id, boolean findInDatabaseToo);

  Optional<T> findSync(String id, boolean findInDatabaseToo) throws Exception;

  CompletableFuture<List<T>> findAllFromCache();

  List<T> findAllFromCacheSync();

  CompletableFuture<List<T>> findAll(Consumer<T> postLoad);

  List<T> findAllSync(Consumer<T> postLoad) throws Exception;
}