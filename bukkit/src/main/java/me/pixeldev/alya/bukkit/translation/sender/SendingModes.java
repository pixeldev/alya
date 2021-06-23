package me.pixeldev.alya.bukkit.translation.sender;

import com.google.common.collect.ImmutableMap;
import me.pixeldev.alya.bukkit.sound.CompatibleSound;

import java.util.Map;

public final class SendingModes {

	public static final String INFO = "info";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String RANDOM = "random";
	public static final String TITLE = "title";
	public static final String FULL_TITLE = "full-title";
	public static final String ACTION_BAR = "action-bar";

	public static final Map<String, CompatibleSound> SOUNDS = ImmutableMap
			.<String, CompatibleSound>builder()
			.put(INFO, CompatibleSound.NOTE_PLING)
			.put(SUCCESS, CompatibleSound.LEVEL_UP)
			.put(ERROR, CompatibleSound.NOTE_BASS)
			.build();

}