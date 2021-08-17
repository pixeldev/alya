package me.pixeldev.alya.bungee.command;

import me.fixeddev.commandflow.CommandContext;
import me.fixeddev.commandflow.bungee.BungeeCommandManager;
import me.fixeddev.commandflow.usage.DefaultUsageBuilder;

import me.yushust.message.MessageHandler;

import net.kyori.text.Component;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import javax.inject.Inject;

public final class CommonUsageBuilder extends DefaultUsageBuilder {

	@Inject private MessageHandler messageHandler;

	@Override
	public Component getUsage(CommandContext commandContext) {
		Component component = super.getUsage(commandContext);
		CommandSender commandSender = commandContext.getObject(
				CommandSender.class,
				BungeeCommandManager.SENDER_NAMESPACE
		);

		return TextComponent.of(messageHandler.get(commandSender, "commands.translation.usage"))
				.append(component.color(TextColor.RED));
	}

}