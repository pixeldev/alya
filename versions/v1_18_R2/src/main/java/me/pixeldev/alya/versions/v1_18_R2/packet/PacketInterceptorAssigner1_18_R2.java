package me.pixeldev.alya.versions.v1_18_R2.packet;

import me.pixeldev.alya.abstraction.packet.intercept.PacketChannelDuplexHandler;
import me.pixeldev.alya.abstraction.packet.intercept.PacketInterceptorAssigner;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketInterceptorAssigner1_18_R2
		implements PacketInterceptorAssigner {
	@Override
	public void assignInterceptor(Player player, String channelName) {
		((CraftPlayer) player).getHandle()
				.b.a.m
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
