package me.pixeldev.alya.versions.v1_18_R2.title;

import me.pixeldev.alya.abstraction.title.AbstractTitleSender;
import me.pixeldev.alya.abstraction.title.TitleDisplay;
import org.bukkit.entity.Player;

public class TitleSender1_18_R2 extends AbstractTitleSender {

	@Override
	public void sendTitle(Player player, TitleDisplay titleDisplay) {
		String title = titleDisplay.getTitle();
		String subTitle = titleDisplay.getSubTitle();

		int fadeIn = titleDisplay.getFadeIn();
		int stay = titleDisplay.getStay();
		int fadeOut = titleDisplay.getFadeOut();

		player.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
	}

}
