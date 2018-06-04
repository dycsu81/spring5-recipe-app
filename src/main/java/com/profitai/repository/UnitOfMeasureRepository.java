package com.profitai.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.profitai.model.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

	Optional<UnitOfMeasure> findByDescription(String description);
}
