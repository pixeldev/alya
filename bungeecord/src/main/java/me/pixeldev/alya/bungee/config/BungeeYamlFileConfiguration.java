package me.pixeldev.alya.bungee.config;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;
import me.pixeldev.alya.api.yaml.YamlFileConfiguration;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class BungeeYamlFileConfiguration implements YamlFileConfiguration {

	private static final ConfigurationProvider PROVIDER = ConfigurationProvider
			.getProvider(YamlConfiguration.class);

	private final Configuration delegate;

	private final String fileName;
	private final File file;

	public BungeeYamlFileConfiguration(Plugin plugin, File folder, String fileName) throws IOException {
		this.fileName = fileName + ".yml";
		file = new File(folder, this.fileName);
		createFile(plugin);
		delegate = PROVIDER.load(file);
	}

	public BungeeYamlFileConfiguration(Plugin plugin, String fileName) throws IOException {
		this(plugin, plugin.getDataFolder(), fileName);
	}

	public BungeeYamlFileConfiguration(Configuration delegate) {
		this.delegate = delegate;
		fileName = null;
		file = null;
	}

	private void createFile(Plugin plugin) {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}

		if (!file.exists()) {
			try (InputStream stream = new BufferedInputStream(plugin.getResourceAsStream(fileName))) {
				Files.copy(stream, file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Object get(String path) {
		return delegate.get(path);
	}

	@Override
	public void set(String path, Object value) {
		delegate.set(path, value);
	}

	@Override
	public String getString(String path) {
		return delegate.getString(path);
	}

	@Override
	public int getInt(String path) {
		return delegate.getInt(path);
	}

	@Override
	public Set<String> getKeys(boolean deep) {
		return new HashSet<>(delegate.getKeys());
	}

	@Override
	public List<String> getStringList(String path) {
		return delegate.getStringList(path);
	}

	@Override
	public boolean getBoolean(String path) {
		return delegate.getBoolean(path);
	}

	@Override
	public double getDouble(String path) {
		return delegate.getDouble(path);
	}

	@Override
	public float getFloat(String path) {
		return (float) getDouble(path);
	}

	@Override
	public YamlConfigurationSection getSection(String path) {
		Configuration section = this.delegate.getSection(path);

		if (section == null || section.getKeys().isEmpty()) {
			return null;
		}

		return new BungeeYamlFileConfiguration(section);
	}

	@Override
	public void reload() throws Exception {
		PROVIDER.load(file);
	}

	@Override
	public void save() throws Exception {
		PROVIDER.save(delegate, file);
	}

}
