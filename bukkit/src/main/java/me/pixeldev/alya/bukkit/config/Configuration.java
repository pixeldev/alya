package me.pixeldev.alya.bukkit.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public final class Configuration extends YamlConfiguration {

	private final String fileName;
	private final File file;

	public Configuration(Plugin plugin,
											 String filename, String fileExtension,
											 File folder) {
		this.fileName = filename + (filename.endsWith(fileExtension) ? "" : fileExtension);
		this.file = new File(folder, this.fileName);
		this.createFile(plugin);
	}

	public Configuration(Plugin plugin,
											 String fileName) {
		this(plugin, fileName, ".yml");
	}

	public Configuration(Plugin plugin,
											 String fileName, String fileExtension) {
		this(plugin, fileName, fileExtension, plugin.getDataFolder());
	}

	public Configuration(Plugin plugin,
											 String fileName, String fileExtension,
											 String filePath) {
		this(plugin,
				fileName, fileExtension,
				new File(plugin.getDataFolder(), filePath));
	}

	private void createFile(Plugin plugin) {
		try {
			if (!file.exists()) {
				if (plugin.getResource(fileName) != null) {
					plugin.saveResource(fileName, false);
				} else {
					save(file);
				}

				load(file);
				return;
			}

			load(file);
			save(file);
		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}

	public void reload() throws IOException, InvalidConfigurationException {
		load(file);
	}

	public void save() throws IOException {
		save(file);
	}

}