package me.pixeldev.alya.storage.universal.service.type;

import me.pixeldev.alya.jdk.functional.FailableConsumer;
import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.service.CompleteModelService;

import java.util.List;
import java.util.Optional;

public abstract class AbstractModelService<T extends Model>
		implements CompleteModelService<T> {

	protected abstract Optional<T> internalFind(String id) throws Exception;

	protected abstract boolean internalDelete(String id) throws Exception;

	protected abstract List<T> internalFindAll(FailableConsumer<T> consumer) throws Exception;

	protected abstract void internalUpload(T model) throws Exception;

}