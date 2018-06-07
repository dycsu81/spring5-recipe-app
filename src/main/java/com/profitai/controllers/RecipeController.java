package com.profitai.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.profitai.commands.RecipeCommand;
import com.profitai.exceptions.NotFoundException;
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
	public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) {
		log.debug("saveOrUpdate");
		
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(objErr -> {
				log.error(objErr.toString());
			});
			
			return "recipe/recipeform";
		}
		
		
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}
	
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception ex) {
		
		log.error("Handling not found exception");
		log.error(ex.getMessage());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("404error");
		modelAndView.addObject("exception", ex);
		
		return modelAndView;
	}
}
