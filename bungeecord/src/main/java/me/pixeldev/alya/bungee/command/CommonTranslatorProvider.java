package me.pixeldev.alya.bungee.command;

import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.bungee.BungeeCommandManager;
import me.fixeddev.commandflow.translator.TranslationProvider;

import me.yushust.message.MessageHandler;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import javax.inject.Inject;

public final class CommonTranslatorProvider implements TranslationProvider {

	@Inject private MessageHandler messageHandler;

	@Override
	public String getTranslation(Namespace namespace, String key) {
		CommandSender commandSender = namespace.getObject(
				CommandSender.class,
				BungeeCommandManager.SENDER_NAMESPACE
		);

		Object entity = "es";
		if (commandSender instanceof ProxiedPlayer) {
			entity = commandSender;
		}

		return messageHandler.get(entity, "commands.translation." + key);
	}

}