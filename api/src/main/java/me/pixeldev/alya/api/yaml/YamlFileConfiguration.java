package me.pixeldev.alya.api.yaml;

public interface YamlFileConfiguration extends YamlConfigurationSection {

	void reload() throws Exception;

	void save() throws Exception;

}