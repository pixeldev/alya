package me.pixeldev.alya.versions.v1_8_R3.actionbar;

import me.pixeldev.alya.abstraction.actionbar.AbstractActionBarSender;
import me.pixeldev.alya.abstraction.packet.PacketSender;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

import org.bukkit.entity.Player;

import javax.inject.Inject;

public class ActionBarSender1_8_R3 extends AbstractActionBarSender {

	@Inject private PacketSender packetSender;

	@Override
	public void sendActionBar(Player player, String message) {
		PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(
			IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}"),
			(byte) 2
		);

		packetSender.sendPacket(player, packetPlayOutChat);
	}

}