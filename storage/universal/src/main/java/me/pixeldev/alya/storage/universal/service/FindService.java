package me.pixeldev.alya.storage.universal.service;

import me.pixeldev.alya.jdk.concurrent.AsyncResponse;
import me.pixeldev.alya.storage.universal.Model;

import java.util.List;
import java.util.function.Consumer;

public interface FindService<T extends Model> {

	AsyncResponse<T> findByCacheIdentifier(String value);

	T findByCacheIdentifierSync(String value) throws Exception;

	AsyncResponse<T> find(String id, boolean findInDatabaseToo);

	T findSync(String id, boolean findInDatabaseToo) throws Exception;

	AsyncResponse<List<T>> findAllFromCache();

	List<T> findAllFromCacheSync() throws Exception;

	AsyncResponse<List<T>> findAll(Consumer<T> postLoad);

	List<T> findAllSync(Consumer<T> postLoad) throws Exception;

}
