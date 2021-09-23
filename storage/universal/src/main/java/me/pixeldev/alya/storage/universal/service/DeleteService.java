package me.pixeldev.alya.storage.universal.service;

import me.pixeldev.alya.jdk.concurrent.AsyncResponse;
import me.pixeldev.alya.storage.universal.Model;

public interface DeleteService<T extends Model> {

	default AsyncResponse<Boolean> delete(T model) {
		return delete(model.getId());
	}

	default boolean deleteSync(T model) throws Exception {
		return deleteSync(model.getId());
	}

	AsyncResponse<Boolean> delete(String id);

	boolean deleteSync(String id) throws Exception;

}
