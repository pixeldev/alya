package me.pixeldev.alya.storage.universal.inject;

import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.service.*;

import me.yushust.inject.AbstractModule;
import me.yushust.inject.key.TypeReference;

import javax.inject.Provider;

public class UniversalModelModule<T extends Model>
		extends AbstractModule {

	protected final Class<T> modelClass;
	private final Class<? extends Provider<CompleteModelService<T>>> modelServiceProvider;

	public UniversalModelModule(Class<T> modelClass,
															Class<? extends Provider<CompleteModelService<T>>> modelServiceProvider) {
		this.modelClass = modelClass;
		this.modelServiceProvider = modelServiceProvider;
	}

	@Override
	protected void configure() {
		TypeReference<CompleteModelService<T>> modelServiceType =
				TypeReference.of(CompleteModelService.class, modelClass);

		bind(modelServiceType).toProvider(modelServiceProvider).singleton();

		bind(TypeReference.of(DeleteService.class, modelClass)).to(modelServiceType).singleton();
		bind(TypeReference.of(FindService.class, modelClass)).to(modelServiceType).singleton();
		bind(TypeReference.of(UploadService.class, modelClass)).to(modelServiceType).singleton();
	}

}
