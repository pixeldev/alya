package me.pixeldev.alya.jdk.time;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class TimeUtils {

	private static final Map<Character, ChronoUnit> UNITS;

	static {
		UNITS = new HashMap<>();
		UNITS.put('s', ChronoUnit.SECONDS);
		UNITS.put('m', ChronoUnit.MINUTES);
		UNITS.put('h', ChronoUnit.HOURS);
		UNITS.put('H', ChronoUnit.HOURS);
		UNITS.put('d', ChronoUnit.DAYS);
		UNITS.put('D', ChronoUnit.DAYS);
		UNITS.put('w', ChronoUnit.WEEKS);
		UNITS.put('W', ChronoUnit.WEEKS);
		UNITS.put('S', ChronoUnit.WEEKS);
		UNITS.put('M', ChronoUnit.MONTHS);
		UNITS.put('y', ChronoUnit.YEARS);
		UNITS.put('Y', ChronoUnit.YEARS);
		UNITS.put('a', ChronoUnit.YEARS);
		UNITS.put('A', ChronoUnit.YEARS);
	}

	public static long parseDuration(String stringDuration) {
		long sum = 0;
		StringBuilder number = new StringBuilder();

		for (final char c : stringDuration.toCharArray()) {
			if (Character.isDigit(c)) {
				number.append(c);
			} else {
				if (UNITS.containsKey(c) && (number.length() > 0)) {
					long parsedLong = Long.parseLong(number.toString());

					ChronoUnit unit = UNITS.get(c);

					sum += unit.getDuration().multipliedBy(parsedLong).toMillis();

					number = new StringBuilder();
				}
			}
		}

		return sum;
	}

	public static Date createDateAddingTime(ChronoUnit additionUnit, long amount) {
		Instant instant = Instant.now();

		return Date.from(instant.plus(amount, additionUnit));
	}

}