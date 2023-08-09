package com.demo.horsetracking.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.horsetracking.model.Horse;
import com.demo.horsetracking.model.Inventory;
import com.demo.horsetracking.model.Wager;
import com.demo.horsetracking.repository.HorseRepository;
import com.demo.horsetracking.repository.InventoryRepository;

@Service
public class ReporterService {

	public ReporterService() {
	}

	@Autowired
	private HorseRepository horseRepository;

	@Autowired
	private InventoryRepository inventoryRepository;

	@Value("${currency.symbol}")
	private String currencySymbol;

	// Messages
	private static final String messageInventory = "Inventory:";
	private static final String messageHorses = "Horses:";
	private static final String messageNoPayout = "No Payout:";
	private static final String messagePayout = "Payout:";
	private static final String messageDispensing = "Dispensing:";

	// Error Messages
	private static final String errorMessageInsufficientFunds = "Insufficient Funds:";
	private static final String errorMessageInvalidBet = "Invalid Bet:";
	private static final String errorMessageInvalidCommand = "Invalid Command";
	private static final String errorMessageInvalidHorseNumber = "Invalid Horse Number:";

	public void printHorses() {

		Iterable<Horse> horses = horseRepository.findAll();
		System.out.println(messageHorses);
		horses.forEach((horse) -> {
			System.out.println(horse.getHorseNumber() + "," + horse.getHorseName() + "," + horse.getOdds() + ","
					+ horse.getRaceStatus().toString().toLowerCase());
		});
	}

	public void printInventory() {

		Iterable<Inventory> inventories = inventoryRepository.findAll();
		System.out.println(messageInventory);
		StreamSupport.stream(inventories.spliterator(), false).sorted((i1, i2) -> {
			Integer deenomination1 = i1.getDenomination();
			return deenomination1.compareTo(i2.getDenomination());

		}).forEach((inventory) -> {
			System.out.println(currencySymbol + inventory.getDenomination() + "," + inventory.getBillCount());
		});

	}

	public void printInvalidCommand(String command) {
		System.out.println(errorMessageInvalidCommand + " " + command);
	}

	public void printInsufficientFunds(int amountWon) {
		System.out.println(errorMessageInsufficientFunds + " " + currencySymbol + amountWon);
	}

	public void printInvalidHorse(int horseNumber) {
		System.out.println(errorMessageInvalidHorseNumber + " " + horseNumber);
	}

	public void printNoPayout(String horseName) {
		System.out.println(messageNoPayout + " " + horseName);
	}

	public void printPayout(String horseName, int amountWon) {
		System.out.println(messagePayout + " " + horseName + "," + currencySymbol + amountWon);
	}

	public void printInvalidBet(String invalidBet) {
		System.out.println(errorMessageInvalidBet + " " + invalidBet);
	}

	public void printDispense(List<Wager> dispense) {
		System.out.println(messageDispensing);
		dispense.stream().sorted((d1, d2) -> {
			Integer denominationTemp = d1.getDenomination();
			return denominationTemp.compareTo(d2.getDenomination());
		}).forEach(wager -> {
			System.out.println(currencySymbol + wager.getDenomination() + "," + wager.getBillCount());
		});
	}

	public void printErrorMessage(String message) {
		System.out.println(message);
	}

	public void startup() {
		printInventory();
		printHorses();
	}

	public void postCommand() {
		startup();
	}

}
