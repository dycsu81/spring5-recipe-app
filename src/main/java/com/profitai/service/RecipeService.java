package com.profitai.service;

import java.util.Set;

import com.profitai.commands.RecipeCommand;
import com.profitai.model.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();
	
	Recipe findById(Long l);
	
	RecipeCommand findCommandById(Long l);
	
	RecipeCommand saveRecipeCommand(RecipeCommand command);
	
	void deleteById(Long l);
}
