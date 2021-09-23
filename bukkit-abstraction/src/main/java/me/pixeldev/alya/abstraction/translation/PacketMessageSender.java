package me.pixeldev.alya.abstraction.translation;

import me.pixeldev.alya.abstraction.actionbar.ActionBarSender;
import me.pixeldev.alya.abstraction.title.TitleDisplay;
import me.pixeldev.alya.abstraction.title.TitleSender;
import me.pixeldev.alya.bukkit.BukkitBasePlugin;
import me.pixeldev.alya.bukkit.sound.CompatibleSound;
import me.pixeldev.alya.bukkit.translation.sender.SendingModes;

import me.yushust.message.send.MessageSender;
import me.yushust.message.util.StringList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class PacketMessageSender implements MessageSender<CommandSender> {

	@Inject private TitleSender titleSender;
	@Inject private ActionBarSender actionBarSender;

	@Override
	public void send(CommandSender commandSender, String mode, String message) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			switch (mode) {
				case SendingModes.TITLE: {
					titleSender.sendTitle(player, TitleDisplay.builder(message)
							.setFadeIn(20)
							.setStay(40)
							.setFadeOut(20)
							.build()
					);
					return;
				}

				case SendingModes.ACTION_BAR: {
					actionBarSender.sendActionBar(player, message);
					return;
				}

				default: {
					CompatibleSound compatibleSound = SendingModes.SOUNDS.get(mode);
					compatibleSound.play(player, 1, 1);
					break;
				}
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

		if (receiver instanceof Player) {
			Player player = (Player) receiver;

			if (mode.equals(SendingModes.FULL_TITLE)) {
				titleSender.sendTitle(player, TitleDisplay.builder(messages.get(0))
						.setSubtitle(messages.get(1))
						.setFadeIn(20)
						.setStay(40)
						.setFadeOut(20)
						.build());

				return;
			}
		}

		send(receiver, mode, messages.join("\n"));
	}

}
