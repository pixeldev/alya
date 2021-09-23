package me.pixeldev.alya.versions.v1_17_R1.packet;

import me.pixeldev.alya.abstraction.packet.intercept.PacketChannelDuplexHandler;
import me.pixeldev.alya.abstraction.packet.intercept.PacketInterceptorAssigner;

import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketInterceptorAssigner1_17_R1
		implements PacketInterceptorAssigner {
	@Override
	public void assignInterceptor(Player player, String channelName) {
		((CraftPlayer) player).getHandle()
				.b.a.k
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
