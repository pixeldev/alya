package me.pixeldev.alya.bukkit.translation.sender;

import me.pixeldev.alya.abstraction.title.TitleDisplay;
import me.pixeldev.alya.abstraction.title.TitleSender;
import me.pixeldev.alya.bukkit.AlyaBasePlugin;
import me.pixeldev.alya.bukkit.sound.CompatibleSound;

import me.yushust.message.send.MessageSender;
import me.yushust.message.util.StringList;

import org.bukkit.entity.Player;

import javax.inject.Inject;

public class CommonMessageSender implements MessageSender<Player> {

	@Inject private TitleSender titleSender;

	@Override
	public void send(Player player, String mode, String message) {
		if (mode.equals(SendingModes.TITLE)) {
			titleSender.sendTitle(player, TitleDisplay.builder(message)
				.setFadeIn(20)
				.setStay(40)
				.setFadeOut(20)
				.build());

			return;
		}

		CompatibleSound compatibleSound = SendingModes.SOUNDS.get(mode);
		compatibleSound.play(player, 1, 1);

		player.sendMessage(message);
	}

	@Override
	public void send(Player receiver, String mode, StringList messages) {
		if (mode.equals(SendingModes.RANDOM)) {
			receiver.sendMessage(messages.get(AlyaBasePlugin.RANDOM.nextInt(
				messages.size()
			)));

			return;
		}

		if (mode.equals(SendingModes.FULL_TITLE)) {
			titleSender.sendTitle(receiver, TitleDisplay.builder(
				messages.get(0)
			)
				.setSubtitle(messages.get(1))
				.setFadeIn(20)
				.setStay(40)
				.setFadeOut(20)
				.build());

			return;
		}

		send(receiver, mode, messages.join("\n"));
	}

}