package me.pixeldev.alya.jdk.statistic;

public class IntegerStatistic {

	private int value;

	public IntegerStatistic(int value) {
		this.value = value;
	}

	public IntegerStatistic() {
		this(0);
	}

	public int increment(int value) {
		return this.value += value;
	}

	public int decrement(int value) {
		return this.value -= value;
	}

	public boolean majorOrEqualThan(int otherValue) {
		return value >= otherValue;
	}

	public int getValue() {
		return value;
	}

}