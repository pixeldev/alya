package me.pixeldev.alya.jdk.statistic;

import java.beans.ConstructorProperties;

public class FloatStatistic {

	private float value;

	@ConstructorProperties("value")
	public FloatStatistic(float value) {
		this.value = value;
	}

	public FloatStatistic() {
		this(0);
	}

	public float increment(float value) {
		return this.value += value;
	}

	public float decrement(float value) {
		return this.value -= value;
	}

	public float set(float value) {
		return this.value = value;
	}

	public boolean majorOrEqualThan(float otherValue) {
		return value >= otherValue;
	}

	public float getValue() {
		return value;
	}

}