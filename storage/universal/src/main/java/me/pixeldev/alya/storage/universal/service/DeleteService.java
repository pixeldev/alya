package me.pixeldev.alya.storage.universal.service;

import me.pixeldev.alya.jdk.concurrent.observer.Observable;
import me.pixeldev.alya.storage.universal.Model;

import java.util.concurrent.CompletableFuture;

public interface DeleteService<T extends Model> {

	default Observable<Boolean> delete(T model) {
		return delete(model.getId());
	}

	default boolean deleteSync(T model) throws Exception {
		return deleteSync(model.getId());
	}

	Observable<Boolean> delete(String id);

	boolean deleteSync(String id) throws Exception;

}