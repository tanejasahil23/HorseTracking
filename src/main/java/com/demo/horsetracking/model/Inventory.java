package com.demo.horsetracking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "INVENTORY")
public class Inventory {

	public Inventory() {
	}

	public Inventory(int denomination, int billCount) {
		this.denomination = denomination;
		this.billCount = billCount;
	}

	@Id
	@GeneratedValue
	private int id;

	@Column
	private int denomination;

	@Column
	private int billCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDenomination() {
		return denomination;
	}

	public void setDenomination(int denomination) {
		this.denomination = denomination;
	}

	public int getBillCount() {
		return billCount;
	}

	public void setBillCount(int billCount) {
		this.billCount = billCount;
	}

}
