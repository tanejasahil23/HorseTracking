package com.demo.horsetracking.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.horsetracking.model.Inventory;

import java.util.List;

public interface InventoryRepository extends CrudRepository<Inventory,Integer> {
  List<Inventory> findAll();
  Inventory findByDenominationEquals(int denomination);
}
