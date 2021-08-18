package me.pixeldev.alya.bukkit.command;

import me.fixeddev.commandflow.CommandContext;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.usage.DefaultUsageBuilder;

import me.pixeldev.alya.api.auto.command.AutoCommand;
import me.yushust.message.MessageHandler;

import net.kyori.text.Component;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;

import org.bukkit.command.CommandSender;

import javax.inject.Inject;

public final class CommonUsageBuilder extends DefaultUsageBuilder {

	@Inject private MessageHandler messageHandler;

	@Override
	public Component getUsage(CommandContext commandContext) {
		Component component = super.getUsage(commandContext);
		CommandSender commandSender = commandContext.getObject(
				CommandSender.class,
				BukkitCommandManager.SENDER_NAMESPACE
		);

		return TextComponent.of(messageHandler.get(commandSender, "commands.translation.usage"))
				.append(component.color(TextColor.RED));
	}

}