package me.pixeldev.alya.storage.universal.service;

import me.pixeldev.alya.storage.universal.Model;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface UploadService<T extends Model> {

  CompletableFuture<Void> upload(T model, boolean removeFromCache);

  void uploadSync(T model, boolean removeFromCache) throws Exception;

  CompletableFuture<Void> uploadAll(Consumer<T> prePersist);

  void uploadAllSync(Consumer<T> prePersist) throws Exception;
}