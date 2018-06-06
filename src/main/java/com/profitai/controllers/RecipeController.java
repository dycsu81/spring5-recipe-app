package com.profitai.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.profitai.commands.RecipeCommand;
import com.profitai.converters.RecipeToRecipeCommand;
import com.profitai.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RecipeController {

	RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}
	
	@RequestMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		log.debug("showById");
		model.addAttribute("recipe", recipeService.findById(new Long(id)));
		
		return "recipe/show";
	}
	
	@RequestMapping("recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }
	
	@RequestMapping("recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipe/recipeform";
	}
	
	@PostMapping("recipe")
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}
}
