package me.pixeldev.alya.storage.gson.inject;

import me.pixeldev.alya.storage.gson.JsonModelService;
import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.inject.UniversalModelModule;
import me.pixeldev.alya.storage.universal.service.CompleteModelService;

import me.yushust.inject.key.TypeReference;

import javax.inject.Provider;

public class JsonModelModule<T extends Model> extends UniversalModelModule<T> {

	public JsonModelModule(Class<T> modelClass,
												 Class<? extends Provider<CompleteModelService<T>>> modelServiceProvider) {
		super(modelClass, modelServiceProvider);
	}

	@Override
	protected void configure() {
		bind(TypeReference.of(JsonModelService.class, modelClass))
			.toFactory(TypeReference.of(JsonModelServiceFactory.class, modelClass));
		super.configure();
	}

}