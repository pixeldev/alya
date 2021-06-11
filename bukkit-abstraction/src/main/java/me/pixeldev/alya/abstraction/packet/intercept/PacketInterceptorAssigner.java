package me.pixeldev.alya.abstraction.packet.intercept;

import org.bukkit.entity.Player;

public interface PacketInterceptorAssigner {

	void assignInterceptor(Player player, String channelName);

	void assignUsingUuid(Player player);

}