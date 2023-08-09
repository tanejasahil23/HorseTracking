package com.demo.horsetracking.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.horsetracking.model.Inventory;
import com.demo.horsetracking.repository.InventoryRepository;

@Service
public class InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Value("${restock.amount}")
	private int restockAmount;

	public boolean sufficientFundsByAmount(int amountWon) {

		List<Inventory> inventories = getInventories();
		Integer result = inventories.stream().reduce(0,
				(total, inventory) -> total + (inventory.getDenomination() * inventory.getBillCount()), Integer::sum);

		if ((result - amountWon) >= 0) {

			// Sort inventories by denomination in descending order
			inventories.sort(Comparator.comparingInt(Inventory::getDenomination).reversed());

			for (Inventory inventory : inventories) {
				int denomination = inventory.getDenomination();
				int billCount = inventory.getBillCount();
				if (amountWon < 0) {
					break;
				}
				if (amountWon >= denomination) {
					int maxBillsToUse = Math.min(billCount, amountWon / denomination);
					amountWon -= denomination * maxBillsToUse;
				}
			}
			if (amountWon > 0) {
				return false;
			}

			return true;
		} else {
			return false;
		}
	}

	public void decrementInventory(int denomination, int amount) {

		Inventory inventory = inventoryRepository.findByDenominationEquals(denomination);

		int currentBillCount = inventory.getBillCount();
		if ((currentBillCount - amount) >= 0) {
			inventory.setBillCount(currentBillCount - amount);
			inventoryRepository.save(inventory);
		}
	}

	public void restocking() {
		List<Inventory> inventories = getInventories();

		inventories.stream().forEach(inventry -> {
			inventry.setBillCount(restockAmount);
			inventoryRepository.save(inventry);
		});
	}

	public List<Inventory> getInventories() {
		return inventoryRepository.findAll();
	}

	public Inventory getInventory(int denomination) {
		return inventoryRepository.findByDenominationEquals(denomination);
	}

}
