package me.pixeldev.alya.bungee.translation;

import me.pixeldev.alya.api.yaml.YamlFileConfiguration;
import me.pixeldev.alya.bungee.config.BungeeYamlFileConfiguration;
import me.yushust.message.source.AbstractCachedFileSource;
import me.yushust.message.source.MessageSource;

import net.md_5.bungee.api.plugin.Plugin;

import org.jetbrains.annotations.Nullable;

import java.io.*;

public class YamlBungeeMessageSource
		extends AbstractCachedFileSource<YamlFileConfiguration>
		implements MessageSource {

	private final Plugin plugin;

	protected YamlBungeeMessageSource(String fileFormat, Plugin plugin) {
		super(fileFormat);
		this.plugin = plugin;
	}

	private YamlFileConfiguration loadImpl(String filename) {
		try {
			return new BungeeYamlFileConfiguration(plugin, filename);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create " + filename + " language file.");
		}
	}

	public void load(String language) {
		String filename = this.getFilename(language);
		YamlFileConfiguration config = this.loadImpl(filename);
		this.cache.put(language, config);
	}

	@Nullable
	protected YamlFileConfiguration getSource(String filename) {
		return this.loadImpl(filename);
	}

	@Nullable
	protected Object getValue(YamlFileConfiguration source, String path) {
		return source.get(path);
	}
}