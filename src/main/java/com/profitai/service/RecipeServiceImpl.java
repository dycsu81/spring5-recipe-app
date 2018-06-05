package com.profitai.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.profitai.model.Recipe;
import com.profitai.repository.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRep;
	
	public RecipeServiceImpl(RecipeRepository recipeRep) {
		super();
		this.recipeRep = recipeRep;
	}

	@Override
	public Set<Recipe> getRecipes() {
		log.debug("getRecipes");
		Set<Recipe> recipeSet =  new HashSet<Recipe>();
		recipeRep.findAll().iterator().forEachRemaining(recipeSet::add);
		
		return recipeSet;
	}
	
	@Override
	public Recipe findById(Long l) {
		
		Optional<Recipe> recipeOptional = recipeRep.findById(l);
		
		if (!recipeOptional.isPresent()) {
			throw new RuntimeException("Recipe not found");
		}
		
		return recipeOptional.get();
	}
}
