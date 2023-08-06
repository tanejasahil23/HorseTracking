package com.demo.horsetracking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.horsetracking.model.Inventory;
import com.demo.horsetracking.model.Wager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WagerService {

  final int ONE = 1;
  final int FIVE = 5;
  final int TEN = 10;
  final int TWENTY = 20;
  final int HUNDRED = 100;

  @Autowired
  private InventoryService inventoryService;

  public int calculateAmountWon(int wager, int odds) {
    return wager * odds;
  }

  public List<Wager> dispenseWinnings(int winnings) {

    List<Wager> list = new ArrayList<>();
    Wager wager;
    boolean wagerAdded = false;

    List<Integer> denoms = inventoryService.getInventories()
                              .stream()
                              .map(Inventory::getDenomination)
                              .collect(Collectors.toList());
    Collections.reverse(denoms);

    for (Integer denomination : denoms) {
      int bill = denomination;
      wagerAdded = false;
      for (int cnt = inventoryService.getInventory(bill).getBillCount(); cnt >0; cnt--) {
        int totalAmountOfBills = bill * cnt;
        if (winnings >= totalAmountOfBills) {
          wager = new Wager(bill,cnt);
          list.add(wager);
          wagerAdded = true;
          winnings -= totalAmountOfBills;
          break;
        }
      }
      if (!wagerAdded) {
        wager = new Wager(bill,0);
        list.add(wager);
      }
    }

    list.forEach(k-> {
      inventoryService.decrementInventory(k.getDenomination(), k.getBillCount());
    });
    return list;
  }

}
