package me.pixeldev.alya.jdk.statistic;

import java.beans.ConstructorProperties;

public class ShortStatistic {

	private short value;

	@ConstructorProperties("value")
	public ShortStatistic(short value) {
		this.value = value;
	}

	public ShortStatistic() {
		this((short) 0);
	}

	public short increment(short value) {
		return this.value += value;
	}

	public short decrement(short value) {
		return this.value -= value;
	}

	public short set(short value) {
		return this.value = value;
	}

	public boolean majorOrEqualThan(short otherValue) {
		return value >= otherValue;
	}

	public short getValue() {
		return value;
	}

}