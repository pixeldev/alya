package me.pixeldev.alya.versions.v1_8_R3.actionbar;

import me.pixeldev.alya.abstraction.actionbar.AbstractActionBarSender;
import me.pixeldev.alya.versions.v1_8_R3.packet.Packets;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

import org.bukkit.entity.Player;

public class ActionBarSender1_8_R3 extends AbstractActionBarSender {

	@Override
	public void sendActionBar(Player player, String message) {
		PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(
				IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}"),
				(byte) 2
		);

		Packets.send(player, packetPlayOutChat);
	}

}
