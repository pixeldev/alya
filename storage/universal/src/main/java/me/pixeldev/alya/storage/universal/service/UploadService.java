package me.pixeldev.alya.storage.universal.service;

import me.pixeldev.alya.jdk.concurrent.observer.Observable;
import me.pixeldev.alya.storage.universal.Model;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface UploadService<T extends Model> {

	Observable<Void> upload(T model, boolean removeFromCache);

	void uploadSync(T model, boolean removeFromCache) throws Exception;

	Observable<Void> uploadAll(Consumer<T> prePersist);

	void uploadAllSync(Consumer<T> prePersist) throws Exception;

}