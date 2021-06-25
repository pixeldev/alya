package me.pixeldev.alya.jdk.statistic;

public class DoubleStatistic {

	private double value;

	public DoubleStatistic(int value) {
		this.value = value;
	}

	public DoubleStatistic() {
		this(0);
	}

	public double increment(double value) {
		return this.value += value;
	}

	public double decrement(double value) {
		return this.value -= value;
	}

	public boolean majorOrEqualThan(double otherValue) {
		return value >= otherValue;
	}

	public double getValue() {
		return value;
	}

}