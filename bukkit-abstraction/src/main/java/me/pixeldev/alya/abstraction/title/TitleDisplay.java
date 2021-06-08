package me.pixeldev.alya.abstraction.title;

public class TitleDisplay {

	private final String title;
	private final String subTitle;
	private final int fadeIn;
	private final int stay;
	private final int fadeOut;

	public TitleDisplay(String title, String subTitle,
											int fadeIn, int stay, int fadeOut) {
		this.title = title;
		this.subTitle = subTitle;
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
	}

	public String getTitle() {
		return title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public int getFadeIn() {
		return fadeIn;
	}

	public int getStay() {
		return stay;
	}

	public int getFadeOut() {
		return fadeOut;
	}

	public static TitleDisplayBuilder builder(String title) {
		return new TitleDisplayBuilder(title);
	}

}