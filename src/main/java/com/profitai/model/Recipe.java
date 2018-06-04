package com.profitai.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private String source;
	private String url;
	
	@Lob
	private String directions;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
	private Set<Ingredient> ingredients;
	
	@Lob
	private Byte[] image;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Notes notes;

	@Enumerated(value = EnumType.STRING)
	private Difficulty difficulty;
	
	@ManyToMany
	@JoinTable(name = "recipe_category", 
		joinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
	)
	private Set<Category> categories;
	
	public Notes getNotes() {
		return notes;
	}

	public void setNotes(Notes notes) {
		this.notes = notes;
		notes.setRecipe(this);
	}
	
	public Recipe addIngredient(Ingredient ingredient) {
		if (this.ingredients == null)
			this.ingredients = new HashSet<Ingredient>();
		
		ingredient.setRecipe(this);
		this.ingredients.add(ingredient);
		return this;
	}

	public Set<Ingredient> getIngredients() {
		if (ingredients == null)
			ingredients = new HashSet<Ingredient>();
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public Set<Category> getCategories() {
		if (categories == null)
			categories = new HashSet<Category>();
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	
	
}
