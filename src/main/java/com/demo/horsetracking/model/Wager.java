package com.demo.horsetracking.model;

public class Wager {

	private final int denomination;
	private final int billCount;

	public Wager(int denomination, int billCount) {
		this.denomination = denomination;
		this.billCount = billCount;
	}

	public int getDenomination() {
		return denomination;
	}

	public int getBillCount() {
		return billCount;
	}

}
