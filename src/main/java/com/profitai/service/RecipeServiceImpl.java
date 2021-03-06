package com.profitai.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.profitai.commands.RecipeCommand;
import com.profitai.converters.RecipeCommandToRecipe;
import com.profitai.converters.RecipeToRecipeCommand;
import com.profitai.exceptions.NotFoundException;
import com.profitai.model.Recipe;
import com.profitai.repository.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		super();
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Set<Recipe> getRecipes() {
		log.debug("getRecipes");
		Set<Recipe> recipeSet =  new HashSet<Recipe>();
		recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
		
		return recipeSet;
	}
	
	@Override
	public Recipe findById(Long l) {
		
		Optional<Recipe> recipeOptional = recipeRepository.findById(l);
		
		if (!recipeOptional.isPresent()) {
			throw new NotFoundException("Recipe not found for id:"+l);
		}
		
		return recipeOptional.get();
	}

	@Transactional
	@Override
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
		
		Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
	}

	@Transactional
	@Override
	public RecipeCommand findCommandById(Long l) {
		return recipeToRecipeCommand.convert(findById(l));
	}

	@Transactional
	@Override
	public void deleteById(Long l) {
		recipeRepository.deleteById(l);
	}
}
