package me.pixeldev.alya.versions.v1_16_R3.packet;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PlayerConnection;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public final class Packets {

	private Packets() {}

	/**
	 * Sends the given {@code packets} to the
	 * specified {@code player}
	 */
	public static void send(Player player, Packet<?>... packets) {
		PlayerConnection connection = ((CraftPlayer) player)
				.getHandle().playerConnection;
		for (Packet<?> packet : packets) {
			connection.sendPacket(packet);
		}
	}

	public static void send(Player player, Iterable<Packet<?>> packets) {
		PlayerConnection connection = ((CraftPlayer) player)
				.getHandle().playerConnection;
		for (Packet<?> packet : packets) {
			connection.sendPacket(packet);
		}
	}

	/**
	 * Sends the given {@code packets} to all the
	 * specified {@code players}
	 */
	public static void send(Iterable<? extends Player> players, Packet<?>... packets) {
		for (Player player : players) {
			PlayerConnection connection = ((CraftPlayer) player)
					.getHandle().playerConnection;
			for (Packet<?> packet : packets) {
				connection.sendPacket(packet);
			}
		}
	}

	public static void send(Iterable<? extends Player> players,
													Iterable<Packet<?>> packets) {
		for (Player player : players) {
			PlayerConnection connection = ((CraftPlayer) player)
					.getHandle().playerConnection;
			for (Packet<?> packet : packets) {
				connection.sendPacket(packet);
			}
		}
	}

}
