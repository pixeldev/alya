package me.pixeldev.alya.bungee.translation;

import me.yushust.inject.AbstractModule;
import me.yushust.inject.Provides;
import me.yushust.message.MessageHandler;
import me.yushust.message.config.ConfigurationModule;
import me.yushust.message.source.MessageSource;

import net.md_5.bungee.api.plugin.Plugin;

import javax.inject.Singleton;

public class MessageModule extends AbstractModule {

	@Provides
	@Singleton
	public MessageSource createSource(Plugin plugin) {
		return new YamlBungeeMessageSource(
				"lang_%lang%",
				plugin
		);
	}

	@Provides
	@Singleton
	public MessageHandler createHandler(MessageSource source,
																			ConfigurationModule configurationModule) {
		return MessageHandler.of(source, configurationModule);
	}

}