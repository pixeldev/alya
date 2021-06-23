package me.pixeldev.alya.versions.v1_16_R3.packet;

import me.pixeldev.alya.abstraction.packet.intercept.PacketChannelDuplexHandler;
import me.pixeldev.alya.abstraction.packet.intercept.PacketInterceptorAssigner;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketInterceptorAssigner1_16_R3
		implements PacketInterceptorAssigner {
	@Override
	public void assignInterceptor(Player player, String channelName) {
		((CraftPlayer) player).getHandle()
				.playerConnection
				.networkManager
				.channel
				.pipeline()
				.addBefore(
						"packet_handler",
						channelName,
						new PacketChannelDuplexHandler(player)
				);
	}

	@Override
	public void assignUsingUuid(Player player) {
		assignInterceptor(player, player.getUniqueId().toString());
	}
}