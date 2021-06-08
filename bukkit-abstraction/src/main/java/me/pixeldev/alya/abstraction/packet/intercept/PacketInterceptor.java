package me.pixeldev.alya.abstraction.packet.intercept;

import org.bukkit.entity.Player;

public interface PacketInterceptor<T> {

	/**
	 * Called when a packet is received, the received packet
	 * is passed as parameter, and the returned packet is
	 * given to the real channel.
	 */
	default T in(Player player, T packet) {
		return packet;
	}

	/**
	 * Called when a packet is sent to the player, the
	 * original packet is given as parameter, and the
	 * intercepted packet is returned by the method.
	 */
	default T out(Player player, T packet) {
		return packet;
	}

}