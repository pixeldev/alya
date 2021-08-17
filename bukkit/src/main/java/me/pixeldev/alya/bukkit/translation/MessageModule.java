package me.pixeldev.alya.bukkit.translation;

import me.yushust.inject.AbstractModule;
import me.yushust.inject.Provides;
import me.yushust.message.MessageHandler;
import me.yushust.message.bukkit.BukkitMessageAdapt;
import me.yushust.message.bukkit.YamlMessageSource;
import me.yushust.message.config.ConfigurationModule;
import me.yushust.message.source.MessageSource;

import org.bukkit.plugin.Plugin;

import javax.inject.Singleton;

public final class MessageModule extends AbstractModule {

	@Provides
	@Singleton
	public MessageSource createSource(Plugin plugin) {
		return BukkitMessageAdapt.newYamlSource(plugin);
	}

	@Provides
	@Singleton
	public MessageHandler createHandler(MessageSource source,
																			ConfigurationModule configurationModule) {
		return MessageHandler.of(source, configurationModule);
	}

}