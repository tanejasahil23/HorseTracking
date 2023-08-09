package com.demo.horsetracking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.demo.horsetracking.RaceStatus;

@Entity
@Table(name = "HORSE")
public class Horse {

	public Horse() {
	}

	public Horse(int horseNumber, String horseName, int odds, RaceStatus raceStatus) {
		this.horseNumber = horseNumber;
		this.horseName = horseName;
		this.odds = odds;
		this.raceStatus = raceStatus;
	}

	@Id
	@GeneratedValue
	private int id;

	@Column
	private int horseNumber;

	@Column
	private String horseName;

	@Column
	private int odds;

	@Column
	private RaceStatus raceStatus = RaceStatus.LOST;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHorseNumber() {
		return horseNumber;
	}

	public void setHorseNumber(int horseNumber) {
		this.horseNumber = horseNumber;
	}

	public String getHorseName() {
		return horseName;
	}

	public void setHorseName(String horseName) {
		this.horseName = horseName;
	}

	public int getOdds() {
		return odds;
	}

	public void setOdds(int odds) {
		this.odds = odds;
	}

	public RaceStatus getRaceStatus() {
		return raceStatus;
	}

	public void setRaceStatus(RaceStatus raceStatus) {
		this.raceStatus = raceStatus;
	}

}
