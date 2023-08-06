package com.demo.horsetracking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.horsetracking.service.CommandService;
import com.demo.horsetracking.service.ConfigService;
import com.demo.horsetracking.service.HorseService;
import com.demo.horsetracking.service.InventoryService;
import com.demo.horsetracking.service.ReporterService;
import com.demo.horsetracking.service.WagerService;

@Component
public class AccessorModeController implements AccessorModeInterface {

	private boolean quit = false;

	public AccessorModeController() {
	}

	@Autowired
	private HorseService horseService;

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private ConfigService configService;
	@Autowired
	private ReporterService reporterService;

	@Autowired
	private CommandService commandService;

	@Autowired
	private WagerService wagerService;

	@Override
	public void execute(String commandLine) {
		if (commandLine.length() > 0) {
			if ((commandService.validateCommand(commandLine).equalsIgnoreCase("invalid"))) {
				reporterService.printInvalidCommand(commandLine);
			} else {
				commandFactory(commandLine);
				reporterService.postCommand();
			}
		}
	}

	private void commandFactory(String commandLine) {
		String command = commandService.validateCommand(commandLine);

		switch (command) {
		case "quit":
			quit = true;
			break;
		case "restock":
			restock();
			break;
		case "winner":
			winner(commandService.getWinningHorseNumber());
			break;
		case "wager":
			wager(commandService.getBetHorseNumber(), commandService.getWagerAmount());
			break;
		case "error":
			reporterService.printErrorMessage(commandService.getErrorMessage());
			break;
		default:
			// Do nothing
		}
	}

	@Override
	public boolean quit() {
		return quit;
	}

	@Override
	public void restock() {
		inventoryService.restocking();
		reporterService.printInventory();
	}

	@Override
	public void wager(int horseNumber, int wagerAmount) {
		if (!(horseService.isValidByHorseNumber(horseNumber))) {
			reporterService.printInvalidHorse(horseNumber);
			return;
		}

		if (!(horseService.isHorseWinnerByHourseNo(horseNumber))) {
			reporterService.printNoPayout(horseService.getHorseName(horseNumber));
			return;
		}

		int amountWon = wagerService.calculateAmountWon(wagerAmount, horseService.getHorseOdds(horseNumber));
		if (inventoryService.sufficientFundsByAmount(amountWon)) {
			reporterService.printPayout(horseService.getHorseName(horseNumber), amountWon);
			reporterService.printDispense(wagerService.dispenseWinnings(amountWon));
		} else {
			reporterService.printInsufficientFunds(amountWon);
		}

		reporterService.printInventory();
		reporterService.printHorses();
	}

	@Override
	public void winner(int horseNumber) {
		if (horseService.isValidByHorseNumber(horseNumber)) {
			horseService.setRaceWinner(horseNumber);
			reporterService.printInventory();
			reporterService.printHorses();
		} else {
			reporterService.printInvalidHorse(horseNumber);
		}
	}

	@Override
	public void printStartupMessages() {
		reporterService.startup();
	}

	@Override
	public void initialize() {
		configService.initalizations();
	}

}
