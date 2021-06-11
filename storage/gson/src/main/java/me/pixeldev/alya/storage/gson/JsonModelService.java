package me.pixeldev.alya.storage.gson;

import com.google.gson.Gson;

import me.pixeldev.alya.jdk.reflect.MethodProvider;
import me.pixeldev.alya.storage.gson.meta.JsonModelMeta;
import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.internal.LoggerUtil;
import me.pixeldev.alya.storage.universal.internal.meta.ModelMeta;
import me.pixeldev.alya.storage.universal.service.type.AbstractModelService;
import me.pixeldev.alya.storage.universal.service.type.LocalModelService;

import me.yushust.inject.assisted.Assist;
import me.yushust.inject.assisted.Assisted;

import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JsonModelService<T extends Model>
	extends AbstractModelService<T> {

	@Inject private Gson mapper;

	private final File modelFolder;

	private final Class<T> classType;
	private final Method prePersistMethod;

	@Assisted
	public JsonModelService(@Assist Plugin plugin,
													@Assist ModelMeta<T> modelMeta,
													@Assist Class<T> classType) {
		super(new LocalModelService<>());

		this.classType = classType;

		prePersistMethod = modelMeta.getPrePersistMethod();
		modelFolder = new File(
			plugin.getDataFolder(), ((JsonModelMeta<T>) modelMeta).getFolderName()
		);

		if (!modelFolder.exists()) {
			modelFolder.mkdirs();
		}
	}

	@Override
	public void deleteSync(T model) throws Exception {
		File file = new File(modelFolder, model.getId() + ".json");

		if (file.delete()) {
			cacheModelService.deleteSync(model);
		}
	}

	@Override
	protected Optional<T> internalFind(String id) throws Exception {
		return internalFind(new File(modelFolder, id + ".json"));
	}

	@Override
	protected boolean internalDelete(T model) {
		File file = new File(modelFolder, model.getId() + ".json");

		return file.delete();
	}

	@Override
	protected List<T> internalFindAll() throws Exception {
		File[] listFiles = modelFolder.listFiles();

		if (listFiles == null) {
			return Collections.emptyList();
		}

		List<T> foundModels = new ArrayList<>();

		for (File file : listFiles) {
			Optional<T> modelOptional = internalFind(file);

			if (!modelOptional.isPresent()) {
				continue;
			}

			foundModels.add(modelOptional.get());
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
				mapper.toJson(model, writer);
			}
		} else {
			LoggerUtil.applyCommonErrorHandler(
				new IllegalArgumentException("Cannot write in file with id " + model.getId()),
				"Writing into a file."
			);
		}
	}

	private Optional<T> internalFind(File file) throws Exception {
		if (!file.exists()) {
			return Optional.empty();
		}

		try (FileReader reader = new FileReader(file)) {
			T model = mapper.fromJson(reader, classType);

			if (model == null) {
				return Optional.empty();
			}

			return Optional.of(model);
		}
	}

}