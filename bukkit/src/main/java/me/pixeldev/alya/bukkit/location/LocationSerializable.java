package me.pixeldev.alya.bukkit.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.beans.ConstructorProperties;

public class LocationSerializable {

	private final String worldName;
	private final double x;
	private final double y;
	private final double z;
	private final float yaw;
	private final float pitch;

	@ConstructorProperties({
			"worldName",
			"x", "y", "z",
			"yaw", "pitch"
	})
	public LocationSerializable(String worldName,
															double x, double y, double z,
															float yaw, float pitch) {
		this.worldName = worldName;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public String getWorldName() {
		return worldName;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public Location toBukkitLocation() {
		return new Location(
				Bukkit.getWorld(worldName),
				x, y, z,
				yaw, pitch
		);
	}

	public String replaceFormat(String format) {
		return format
				.replace("%world%", worldName)
				.replace("%x%", x + "")
				.replace("%y%", y + "")
				.replace("%z%", z + "")
				.replace("%yaw%", yaw + "")
				.replace("%pitch%", pitch + "");
	}

	@Override
	public String toString() {
		return worldName + ";" + x + ";" + y + ";" + z + ";" + yaw + ";" + pitch;
	}

	public static LocationSerializable fromBukkitLocation(Location location) {
		return new LocationSerializable(
				location.getWorld().getName(),
				location.getX(), location.getY(), location.getZ(),
				location.getYaw(), location.getPitch()
		);
	}

	public static LocationSerializable fromString(String input) {
		String[] components = input.split(";");

		return new LocationSerializable(
				components[0],
				parseDouble(components[1]), parseDouble(components[2]), parseDouble(components[3]),
				parseFloat(components[4]), parseFloat(components[5])
		);
	}

	private static double parseDouble(String line) {
		return Double.parseDouble(line);
	}

	private static float parseFloat(String line) {
		return Float.parseFloat(line);
	}

}