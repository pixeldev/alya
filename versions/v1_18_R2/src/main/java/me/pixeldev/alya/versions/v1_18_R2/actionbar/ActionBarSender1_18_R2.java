package me.pixeldev.alya.versions.v1_18_R2.actionbar;

import me.pixeldev.alya.abstraction.actionbar.AbstractActionBarSender;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ActionBarSender1_18_R2 extends AbstractActionBarSender {

	@Override
	public void sendActionBar(Player player, String message) {
		player.spigot().sendMessage(
				ChatMessageType.ACTION_BAR,
				new TextComponent(message)
		);
	}

}
