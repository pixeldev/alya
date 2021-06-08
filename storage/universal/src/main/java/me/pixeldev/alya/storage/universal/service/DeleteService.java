package me.pixeldev.alya.storage.universal.service;

import me.pixeldev.alya.storage.universal.Model;

import java.util.concurrent.CompletableFuture;

public interface DeleteService<T extends Model> {

  CompletableFuture<Void> delete(T model);

  void deleteSync(T model) throws Exception;

  CompletableFuture<Void> delete(String id);

  void deleteSync(String id) throws Exception;
}