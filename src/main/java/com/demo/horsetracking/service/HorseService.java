package com.demo.horsetracking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.horsetracking.RaceStatus;
import com.demo.horsetracking.model.Horse;
import com.demo.horsetracking.repository.HorseRepository;

@Service
public class HorseService {

	@Autowired
	private HorseRepository horseRepository;

	public String getHorseName(int horseNumber) {
		Horse horse = horseRepository.findByHorseNumberEquals(horseNumber);

		return horse.getHorseName();
	}

	public int getHorseOdds(int horseNumber) {
		Horse horse = horseRepository.findByHorseNumberEquals(horseNumber);

		return horse.getOdds();
	}

	public boolean isHorseWinnerByHourseNo(int horseNumber) {
		Horse horse = horseRepository.findByHorseNumberEquals(horseNumber);
		if (horse.getRaceStatus() == RaceStatus.WON) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isValidByHorseNumber(int horseNumber) {
		if (horseRepository.findByHorseNumberEquals(horseNumber) == null) {
			return false;
		} else {
			return true;
		}
	}

	public void setRaceWinner(int horseNumber) {

		List<Horse> horses = horseRepository.findAll();

		if (!horses.isEmpty()) {

			horses.stream().filter((horse) -> horse.getRaceStatus() == RaceStatus.WON).forEach(losingHorse -> {
				losingHorse.setRaceStatus(RaceStatus.LOST);
				horseRepository.save(losingHorse);
			});

			horses.stream().filter((horse) -> horse.getHorseNumber() == horseNumber).forEach(winningHorse -> {
				winningHorse.setRaceStatus(RaceStatus.WON);
				horseRepository.save(winningHorse);
			});
		}
	}
}
