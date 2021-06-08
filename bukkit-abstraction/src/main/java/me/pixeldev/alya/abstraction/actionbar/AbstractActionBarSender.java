package me.pixeldev.alya.abstraction.actionbar;

import me.yushust.message.MessageHandler;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public abstract class AbstractActionBarSender implements ActionBarSender {

	@Inject private MessageHandler messageHandler;

	@Override
	public String translateMessage(Player player, String messagePath) {
		return messageHandler.get(player, messagePath);
	}

}