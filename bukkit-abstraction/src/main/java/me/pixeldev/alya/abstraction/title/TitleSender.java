package me.pixeldev.alya.abstraction.title;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface TitleSender {

	void sendTitle(Player player, TitleDisplay titleDisplay);

	default void sendTitleToAllPlayers(TitleDisplay titleDisplay) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			sendTitle(player, translateTitleDisplay(player, titleDisplay));
		}
	}

	default void sendTitleToPlayers(Collection<Player> players,
																	TitleDisplay titleDisplay) {
		for (Player player : players) {
			sendTitle(player, translateTitleDisplay(player, titleDisplay));
		}
	}

	TitleDisplay translateTitleDisplay(Player player, TitleDisplay titleDisplay);

}