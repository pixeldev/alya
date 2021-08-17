package me.pixeldev.alya.storage.sql.identity;

import me.pixeldev.alya.storage.sql.SQLModel;

import java.util.Map;

public interface SQLMapSerializer<T extends SQLModel> {

	Map<String, Object> serialize(T model);

}