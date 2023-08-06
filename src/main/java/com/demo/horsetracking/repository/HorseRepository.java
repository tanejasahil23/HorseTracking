package com.demo.horsetracking.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.horsetracking.model.Horse;

import java.util.List;

public interface HorseRepository extends CrudRepository<Horse, Integer> {
  List<Horse> findAll();
  Horse findByHorseNumberEquals(int horseNumber);
}
