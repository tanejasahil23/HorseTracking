package com.demo.horsetracking.service;

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

		List<Inventory> inventories = inventoryRepository.findAll();
		Integer result = inventories.stream().reduce(0,
				(total, inventory) -> total + (inventory.getDenomination() * inventory.getBillCount()), Integer::sum);
		if ((result - amountWon) >= 0) {
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
		List<Inventory> inventories = inventoryRepository.findAll();

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
