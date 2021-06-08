package me.pixeldev.alya.abstraction.packet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface PacketSender {

	default void sendPacket(Player player, Object packet) {
		sendPackets(player, packet);
	}

	void sendPackets(Player player, Object... packets);

	default void sendPacketToAllPlayers(Object packet) {
		sendPacketsToAllPlayers(packet);
	}

	default void sendPacketsToAllPlayers(Object... packets) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			sendPackets(player, packets);
		}
	}

	default void sendPacketToPlayers(Collection<Player> players, Object packet) {
		sendPacketsToPlayers(players, packet);
	}

	default void sendPacketsToPlayers(Collection<Player> players, Object... packets) {
		for (Player player : players) {
			sendPackets(player, packets);
		}
	}

}