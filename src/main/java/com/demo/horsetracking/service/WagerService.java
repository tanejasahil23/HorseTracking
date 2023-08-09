package com.demo.horsetracking.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.horsetracking.model.Inventory;
import com.demo.horsetracking.model.Wager;

@Service
public class WagerService {

	@Autowired
	private InventoryService inventoryService;

	public int calculateAmountWon(int wager, int odds) {
		return wager * odds;
	}

	public List<Wager> dispenseWinnings(int winning) {
		List<Wager> list = new ArrayList<>();
		Map<Integer, Wager> hm = new HashMap<>();
		List<Inventory> inventories = inventoryService.getInventories();

		// Sort inventories by denomination in descending order
		inventories.sort(Comparator.comparingInt(Inventory::getDenomination).reversed());

		for (Inventory inventory : inventories) {
			int denomination = inventory.getDenomination();
			int billCount = inventory.getBillCount();

			if (winning >= denomination) {
				int maxBillsToUse = Math.min(billCount, winning / denomination);
				hm.put(denomination, new Wager(denomination, maxBillsToUse));
				winning -= denomination * maxBillsToUse;
			}
		}

		if (inventories.size() != hm.size()) {
			inventories.stream().forEach(invenory -> {

				if (!hm.containsKey(invenory.getDenomination())) {
					Wager wager = new Wager(invenory.getDenomination(), 0);
					hm.put(invenory.getDenomination(), wager);
				}
			});

		}
		list = hm.values().stream().collect(Collectors.toList());
		list.forEach(k -> {
			inventoryService.decrementInventory(k.getDenomination(), k.getBillCount());
		});

		return list;
	}

}
