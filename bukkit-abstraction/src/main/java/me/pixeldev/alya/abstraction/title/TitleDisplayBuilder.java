package me.pixeldev.alya.abstraction.title;

public final class TitleDisplayBuilder {

	private final String title;
	private String subtitle;
	private int fadeIn;
	private int stay;
	private int fadeOut;

	protected TitleDisplayBuilder(String title) {
		this.title = title;
	}

	public TitleDisplayBuilder setSubtitle(String subtitle) {
		this.subtitle = subtitle;
		return this;
	}

	public TitleDisplayBuilder setFadeIn(int fadeIn) {
		this.fadeIn = fadeIn;
		return this;
	}

	public TitleDisplayBuilder setStay(int stay) {
		this.stay = stay;
		return this;
	}

	public TitleDisplayBuilder setFadeOut(int fadeOut) {
		this.fadeOut = fadeOut;
		return this;
	}

	public TitleDisplay build() {
		return new TitleDisplay(title, subtitle, fadeIn, stay, fadeOut);
	}

}