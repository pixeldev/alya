package me.pixeldev.alya.versions.v1_16_R3.title;

import me.pixeldev.alya.abstraction.title.AbstractTitleSender;
import me.pixeldev.alya.abstraction.title.TitleDisplay;

import me.pixeldev.alya.versions.v1_16_R3.packet.Packets;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketPlayOutTitle;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class TitleSender1_16_R3 extends AbstractTitleSender {

	@Override
	public void sendTitle(Player player, TitleDisplay titleDisplay) {
		String title = titleDisplay.getTitle();
		String subTitle = titleDisplay.getSubTitle();

		int fadeIn = titleDisplay.getFadeIn();
		int stay = titleDisplay.getStay();
		int fadeOut = titleDisplay.getFadeOut();

		Packet<?>[] packets = new Packet<?>[2];

		packets[0] = createTitlePacket(
				PacketPlayOutTitle.EnumTitleAction.TITLE, buildComponent(title),
				fadeIn, stay, fadeOut
		);

		if (subTitle != null) {
			packets[1] = createTitlePacket(
					PacketPlayOutTitle.EnumTitleAction.SUBTITLE, buildComponent(subTitle),
					fadeIn, stay, fadeOut
			);
		}

		Packets.send(player, packets);
	}

	private IChatBaseComponent buildComponent(String text) {
		return IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}");
	}

	private PacketPlayOutTitle createTitlePacket(PacketPlayOutTitle.EnumTitleAction action,
																							 IChatBaseComponent baseComponent,
																							 int fadeIn, int stay, int fadeOut) {
		return new PacketPlayOutTitle(
				action, baseComponent,
				fadeIn, stay, fadeOut
		);
	}

}
