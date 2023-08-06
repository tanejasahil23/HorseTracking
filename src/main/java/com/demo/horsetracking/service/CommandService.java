package com.demo.horsetracking.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;

@Service
public class CommandService {

	private String errorMessageInvalidBet = "Invalid Bet:";

	private int betHorseNumber;
	private String currentCommand;
	private int wagerAmount;
	private int winningHorseNumber;

	private String errorMessage;

	public CommandService() {
	}

	public void execute(String[] command) {
	}

	public String validateCommand(String commandLine) {

		String[] commandComponents = Arrays.stream(commandLine.split(" ")).map(String::trim).toArray(String[]::new);

		if (commandComponents[0].equalsIgnoreCase("q")) {
			currentCommand = "quit";
			return currentCommand;
		} else if (commandLine.matches("[0-9]+ [0-9]+.?[0-9]*")) {
			betHorseNumber = Integer.parseInt(commandComponents[0]);
			try {
				wagerAmount = Integer.parseInt(commandComponents[1]);
			} catch (NumberFormatException e) {
				errorMessage = errorMessageInvalidBet + " " + commandComponents[1];
				currentCommand = "error";
				return currentCommand;
			}
			currentCommand = "wager";
			return currentCommand;
		} else if (commandComponents[0].equalsIgnoreCase("r")) {
			currentCommand = "restock";
			return currentCommand;
		} else if (commandLine.matches("[W,w] [1-9]")) {
			winningHorseNumber = Integer.parseInt(commandComponents[1]);
			currentCommand = "winner";
			return currentCommand;
		} else {
			currentCommand = "invalid";
			return currentCommand;
		}
	}

	public String getCurrentCommand() {
		return currentCommand;
	}

	public int getBetHorseNumber() {
		return betHorseNumber;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public int getWagerAmount() {
		return wagerAmount;
	}

	public int getWinningHorseNumber() {
		return winningHorseNumber;
	}

}
