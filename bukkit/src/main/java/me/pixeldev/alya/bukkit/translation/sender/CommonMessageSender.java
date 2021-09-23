package me.pixeldev.alya.bukkit.translation.sender;

import me.pixeldev.alya.bukkit.BukkitBasePlugin;
import me.pixeldev.alya.bukkit.sound.CompatibleSound;

import me.yushust.message.send.MessageSender;
import me.yushust.message.util.StringList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommonMessageSender implements MessageSender<CommandSender> {

	@Override
	public void send(CommandSender commandSender, String mode, String message) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			CompatibleSound compatibleSound = SendingModes.SOUNDS.get(mode);

			if (compatibleSound != null) {
				compatibleSound.play(player, 1, 1);
			}
		}

		commandSender.sendMessage(message);
	}

	@Override
	public void send(CommandSender receiver, String mode, StringList messages) {
		if (mode.equals(SendingModes.RANDOM)) {
			receiver.sendMessage(messages.get(BukkitBasePlugin.RANDOM.nextInt(
					messages.size()
			)));

			return;
		}

		send(receiver, mode, messages.join("\n"));
	}

}
