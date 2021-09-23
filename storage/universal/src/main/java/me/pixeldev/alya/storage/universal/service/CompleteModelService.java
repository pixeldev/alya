package me.pixeldev.alya.storage.universal.service;

import me.pixeldev.alya.storage.universal.Model;

public interface CompleteModelService<T extends Model> extends
		FindService<T>,
		UploadService<T>,
		DeleteService<T> {

	CompleteModelService<T> getCacheModelService();

}
