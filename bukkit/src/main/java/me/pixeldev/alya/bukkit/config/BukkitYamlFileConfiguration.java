package me.pixeldev.alya.bukkit.config;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;
import me.pixeldev.alya.api.yaml.YamlFileConfiguration;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public final class BukkitYamlFileConfiguration implements YamlFileConfiguration {

	private final YamlConfiguration delegate;
	private final ConfigurationSection section;

	private final String fileName;
	private final File file;

	public BukkitYamlFileConfiguration(Plugin plugin,
																		 String filename,
																		 String fileExtension,
																		 File folder) {
		delegate = new YamlConfiguration();
		section = delegate;
		this.fileName = filename + (filename.endsWith(fileExtension) ? "" : fileExtension);
		this.file = new File(folder, this.fileName);
		this.createFile(plugin);
	}

	public BukkitYamlFileConfiguration(Plugin plugin,
																		 String fileName) {
		this(plugin, fileName, ".yml");
	}

	public BukkitYamlFileConfiguration(Plugin plugin,
																		 String fileName, String fileExtension) {
		this(plugin, fileName, fileExtension, plugin.getDataFolder());
	}

	public BukkitYamlFileConfiguration(Plugin plugin,
																		 String fileName, String fileExtension,
																		 String filePath) {
		this(plugin,
				fileName, fileExtension,
				new File(plugin.getDataFolder(), filePath)
		);
	}

	public BukkitYamlFileConfiguration(ConfigurationSection section) {
		this.delegate = null;
		this.section = section;
		fileName = null;
		file = null;
	}

	private void createFile(Plugin plugin) {
		try {
			if (!file.exists()) {
				if (plugin.getResource(fileName) != null) {
					plugin.saveResource(fileName, false);
				} else {
					delegate.save(file);
				}

				delegate.load(file);
				return;
			}

			delegate.load(file);
			delegate.save(file);
		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}

	public void reload() throws Exception {
		delegate.load(file);
	}

	public void save() throws Exception {
		delegate.save(file);
	}

	@Override
	public Object get(String path) {
		return section.get(path);
	}

	@Override
	public void set(String path, Object value) {
		section.set(path, value);
	}

	@Override
	public String getString(String path) {
		return section.getString(path);
	}

	@Override
	public int getInt(String path) {
		return section.getInt(path);
	}

	@Override
	public Set<String> getKeys(boolean deep) {
		return section.getKeys(deep);
	}

	@Override
	public List<String> getStringList(String path) {
		return section.getStringList(path);
	}

	@Override
	public boolean getBoolean(String path) {
		return section.getBoolean(path);
	}

	@Override
	public double getDouble(String path) {
		return section.getDouble(path);
	}

	@Override
	public float getFloat(String path) {
		return (float) getDouble(path);
	}

	@Override
	public YamlConfigurationSection getSection(String path) {
		ConfigurationSection section = this.section.getConfigurationSection(path);

		if (section == null || section.getKeys(false).isEmpty()) {
			return null;
		}

		return new BukkitYamlFileConfiguration(section);
	}

}
