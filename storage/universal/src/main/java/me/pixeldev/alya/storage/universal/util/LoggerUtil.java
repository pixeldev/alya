package me.pixeldev.alya.storage.universal.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class LoggerUtil {

	private static final Logger LOGGER =
			Logger.getLogger("alya-storage");

	public static void applyCommonErrorHandler(Throwable error,
																						 String processMessage) {
		if (error != null) {
			LOGGER.log(Level.SEVERE, processMessage, error);
		}
	}

}
