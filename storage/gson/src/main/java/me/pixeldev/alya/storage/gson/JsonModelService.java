package me.pixeldev.alya.storage.gson;

import com.google.gson.Gson;

import me.pixeldev.alya.jdk.functional.FailableConsumer;
import me.pixeldev.alya.jdk.reflect.MethodProvider;
import me.pixeldev.alya.storage.gson.meta.JsonModelMeta;
import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.util.LoggerUtil;
import me.pixeldev.alya.storage.universal.internal.meta.ModelMeta;
import me.pixeldev.alya.storage.universal.service.type.AbstractRemoteModelService;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonModelService<T extends Model>
		extends AbstractRemoteModelService<T> {

	private final Gson mapper;
	private final File modelFolder;

	public JsonModelService(Gson mapper,
													Plugin plugin,
													ModelMeta<T> modelMeta) {
		super(modelMeta);

		this.mapper = mapper;
		modelFolder = new File(
				plugin.getDataFolder(), ((JsonModelMeta<T>) modelMeta).getFolderName()
		);

		if (!modelFolder.exists()) {
			modelFolder.mkdirs();
		}
	}

	@Override
	protected T internalFind(String id) throws Exception {
		return internalFind(new File(modelFolder, id + ".json"));
	}

	@Override
	protected boolean internalDelete(String id) {
		return new File(modelFolder, id + ".json").delete();
	}

	@Override
	protected List<T> internalFindAll(FailableConsumer<T> consumer) throws Exception {
		File[] listFiles = modelFolder.listFiles();

		if (listFiles == null) {
			return Collections.emptyList();
		}

		List<T> foundModels = new ArrayList<>();

		for (File file : listFiles) {
			T model = internalFind(file);

			if (model == null) {
				continue;
			}

			consumer.accept(model);
			foundModels.add(model);
		}

		return foundModels;
	}

	@Override
	protected void internalUpload(T model) throws Exception {
		File file = new File(modelFolder, model.getId() + ".json");
		boolean canWrite;

		if (!file.exists()) {
			canWrite = file.createNewFile();
		} else {
			canWrite = true;
		}

		if (canWrite) {
			if (prePersistMethod != null) {
				MethodProvider.invoke(prePersistMethod, model);
			}

			try (FileWriter writer = new FileWriter(file)) {
				writer.write(mapper.toJson(model, classType));
			}
		} else {
			LoggerUtil.applyCommonErrorHandler(
					new IllegalArgumentException("Cannot write in file with id " + model.getId()),
					"Writing into a file."
			);
		}
	}

	private T internalFind(File file) throws Exception {
		if (!file.exists()) {
			return null;
		}

		try (FileReader reader = new FileReader(file)) {
			T model = mapper.fromJson(reader, classType);

			if (model == null) {
				return null;
			}

			if (postLoadMethod != null) {
				MethodProvider.invoke(postLoadMethod, model);
			}

			return model;
		}
	}

}
