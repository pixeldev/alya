package me.pixeldev.alya.storage.universal.service;

import me.pixeldev.alya.jdk.concurrent.observer.Observable;
import me.pixeldev.alya.storage.universal.Model;

public interface CheckerService<T extends Model> {

	Observable<Boolean> existsByCacheIdentifier(String value);

	boolean existsByCacheIdentifierSync(String value) throws Exception;

	Observable<Boolean> exists(String id);

	boolean existsSync(String id) throws Exception;
}