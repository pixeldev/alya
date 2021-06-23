package me.pixeldev.alya.versions.v1_8_R3.packet;

import me.pixeldev.alya.abstraction.packet.PacketSender;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PlayerConnection;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketSender1_8_R3 implements PacketSender {

	@Override
	public void sendPackets(Player player, Object... packets) {
		PlayerConnection playerConnection = ((CraftPlayer) player)
				.getHandle()
				.playerConnection;

		for (Object packet : packets) {
			playerConnection.sendPacket((Packet<?>) packet);
		}
	}

}