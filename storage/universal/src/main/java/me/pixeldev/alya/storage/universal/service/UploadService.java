package me.pixeldev.alya.storage.universal.service;

import me.pixeldev.alya.jdk.concurrent.AsyncResponse;
import me.pixeldev.alya.storage.universal.Model;

import java.util.function.Consumer;

public interface UploadService<T extends Model> {

	AsyncResponse<Void> upload(T model, boolean removeFromCache);

	void uploadSync(T model, boolean removeFromCache) throws Exception;

	AsyncResponse<Void> uploadAll(Consumer<T> prePersist);

	void uploadAllSync(Consumer<T> prePersist) throws Exception;

}
