package me.pixeldev.alya.abstraction.actionbar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface ActionBarSender {

	void sendActionBar(Player player, String message);

	default void sendActionBarToAllPlayers(String messagePath) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			sendActionBar(player, translateMessage(player, messagePath));
		}
	}

	default void sendActionBarToPlayers(Collection<Player> players,
																			String messagePath) {
		for (Player player : players) {
			sendActionBar(player, translateMessage(player, messagePath));
		}
	}

	String translateMessage(Player player, String messagePath);

}