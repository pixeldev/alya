package me.pixeldev.alya.storage.universal.service;

import me.pixeldev.alya.storage.universal.Model;

import java.util.concurrent.CompletableFuture;

public interface CheckerService<T extends Model> {

  CompletableFuture<Boolean> existsByCacheIdentifier(String value);

  boolean existsByCacheIdentifierSync(String value) throws Exception;

  CompletableFuture<Boolean> exists(String id);

  boolean existsSync(String id) throws Exception;
}