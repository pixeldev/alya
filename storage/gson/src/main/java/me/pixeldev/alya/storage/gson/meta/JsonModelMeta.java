package me.pixeldev.alya.storage.gson.meta;

import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.internal.meta.ModelMeta;

public class JsonModelMeta<T extends Model> extends ModelMeta<T> {

	private final String folderName;

	public JsonModelMeta(Class<T> type) {
		super(type);

		JsonFolder jsonFolder = type.getAnnotation(JsonFolder.class);

		if (jsonFolder == null) {
			throw new IllegalStateException("Json Model doesn't have JsonFolder annotation");
		}

		folderName = jsonFolder.value();
	}

	public String getFolderName() {
		return folderName;
	}

}