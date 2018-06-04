package com.profitai.repository;

import org.springframework.data.repository.CrudRepository;

import com.profitai.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
