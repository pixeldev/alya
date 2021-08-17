package me.pixeldev.alya.api.yaml;

import java.util.List;
import java.util.Set;

public interface YamlConfigurationSection {

	Object get(String path);

	void set(String path, Object value);

	String getString(String path);

	int getInt(String path);

	Set<String> getKeys(boolean deep);

	List<String> getStringList(String path);

	boolean getBoolean(String path);

	double getDouble(String path);

	float getFloat(String path);

	YamlConfigurationSection getSection(String path);

}