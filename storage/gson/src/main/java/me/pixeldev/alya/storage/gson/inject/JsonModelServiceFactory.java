package me.pixeldev.alya.storage.gson.inject;

import me.pixeldev.alya.storage.gson.JsonModelService;
import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.internal.meta.ModelMeta;

import me.yushust.inject.assisted.ValueFactory;

import org.bukkit.plugin.Plugin;

public interface JsonModelServiceFactory<T extends Model>
	extends ValueFactory {

  JsonModelService<T> create(Plugin plugin,
														 ModelMeta<T> modelMeta,
														 Class<T> classType);

}