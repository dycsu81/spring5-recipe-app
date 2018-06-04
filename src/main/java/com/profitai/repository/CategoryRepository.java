package com.profitai.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.profitai.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	Optional<Category> findByDescription(String description);
}
