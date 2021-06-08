package me.pixeldev.alya.jdk.time;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class TimeUtils {

  private static final Map<Character, ChronoUnit> units;

  static {
    units = new HashMap<>();
    units.put('s', ChronoUnit.SECONDS);
    units.put('m', ChronoUnit.MINUTES);
    units.put('h', ChronoUnit.HOURS);
    units.put('H', ChronoUnit.HOURS);
    units.put('d', ChronoUnit.DAYS);
    units.put('D', ChronoUnit.DAYS);
    units.put('w', ChronoUnit.WEEKS);
    units.put('W', ChronoUnit.WEEKS);
    units.put('S', ChronoUnit.WEEKS);
    units.put('M', ChronoUnit.MONTHS);
    units.put('y', ChronoUnit.YEARS);
    units.put('Y', ChronoUnit.YEARS);
    units.put('a', ChronoUnit.YEARS);
    units.put('A', ChronoUnit.YEARS);
  }

  public static long parseDuration(String stringDuration) {
    long sum = 0;
    StringBuilder number = new StringBuilder();

    for (final char c : stringDuration.toCharArray()) {
      if (Character.isDigit(c)) {
        number.append(c);
      } else {
        if (units.containsKey(c) && (number.length() > 0)) {
          long parsedLong = Long.parseLong(number.toString());

          ChronoUnit unit = units.get(c);

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