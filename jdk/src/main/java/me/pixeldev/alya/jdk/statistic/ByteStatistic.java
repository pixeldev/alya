package me.pixeldev.alya.jdk.statistic;

public class ByteStatistic {

	private byte value;

	public ByteStatistic(byte value) {
		this.value = value;
	}

	public ByteStatistic() {
		this((byte) 0);
	}

	public byte increment(byte value) {
		return this.value += value;
	}

	public byte decrement(byte value) {
		return this.value -= value;
	}

	public boolean majorOrEqualThan(byte otherValue) {
		return value >= otherValue;
	}

	public byte getValue() {
		return value;
	}

}