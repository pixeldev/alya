package me.pixeldev.alya.bungee.translation.sender;

import me.pixeldev.alya.bungee.BungeeBasePlugin;

import me.yushust.message.send.MessageSender;
import me.yushust.message.util.StringList;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class CommonMessageSender implements MessageSender<CommandSender> {

	@Override
	public void send(CommandSender player, String mode, String message) {
		if (player instanceof ProxiedPlayer) {
			ChatMessageType chatMessageType = ChatMessageType.CHAT;

			if (mode.equals(SendingModes.ACTION_BAR)) {
				chatMessageType = ChatMessageType.ACTION_BAR;
			}

			((ProxiedPlayer) player).sendMessage(
					chatMessageType,
					new TextComponent(message)
			);
		} else {
			player.sendMessage(new TextComponent(message));
		}
	}

	@Override
	public void send(CommandSender receiver, String mode, StringList messages) {
		if (mode.equals(SendingModes.RANDOM)) {
			receiver.sendMessage(new TextComponent(messages
					.get(BungeeBasePlugin.RANDOM.nextInt(
							messages.size()
					))
			));

			return;
		}

		for (String message : messages) {
			receiver.sendMessage(new TextComponent(message));
		}
	}

}