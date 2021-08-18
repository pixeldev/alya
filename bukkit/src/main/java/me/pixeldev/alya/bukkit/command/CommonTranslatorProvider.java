package me.pixeldev.alya.bukkit.command;

import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.translator.TranslationProvider;

import me.pixeldev.alya.api.auto.command.AutoCommand;
import me.yushust.message.MessageHandler;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public final class CommonTranslatorProvider implements TranslationProvider {

	@Inject private MessageHandler messageHandler;

	@Override
	public String getTranslation(Namespace namespace, String key) {
		CommandSender commandSender = namespace.getObject(
				CommandSender.class,
				BukkitCommandManager.SENDER_NAMESPACE
		);

		return messageHandler.get(commandSender, "commands.translation." + key);
	}

}