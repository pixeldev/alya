package me.pixeldev.alya.abstraction.title;

import me.yushust.message.MessageHandler;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public abstract class AbstractTitleSender
		implements TitleSender {

	@Inject private MessageHandler messageHandler;

	@Override
	public TitleDisplay translateTitleDisplay(Player player, TitleDisplay titleDisplay) {
		String title = titleDisplay.getTitle();
		String subTitle = titleDisplay.getSubTitle();

		title = messageHandler.get(player, title);
		if (subTitle != null) {
			subTitle = messageHandler.get(player, subTitle);
		}

		return TitleDisplay.builder(title)
				.setSubtitle(subTitle)
				.setFadeIn(titleDisplay.getFadeIn())
				.setStay(titleDisplay.getStay())
				.setFadeOut(titleDisplay.getFadeOut())
				.build();
	}

}