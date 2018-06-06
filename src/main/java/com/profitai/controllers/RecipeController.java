package com.profitai.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.profitai.commands.RecipeCommand;
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
	
	
	@GetMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		log.debug("showById");
		model.addAttribute("recipe", recipeService.findById(new Long(id)));
		
		return "recipe/show";
	}
	
	@GetMapping("recipe/new")
    public String newRecipe(Model model){
		log.debug("newRecipe");
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }
	
	@GetMapping("recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		log.debug("updateRecipe");
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipe/recipeform";
	}
	
	@GetMapping("recipe/{id}/delete")
	public String deleteById(@PathVariable String id) {
		log.debug("deleteById");
		recipeService.deleteById(Long.valueOf(id));
		return "redirect:/";
	}
	
	@PostMapping("recipe")
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		log.debug("saveOrUpdate");
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}
}
