package com.profitai.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.profitai.commands.IngredientCommand;
import com.profitai.model.Ingredient;

import lombok.Synchronized;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

	private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;
	
	public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
		super();
		this.uomConverter = uomConverter;
	}

	@Synchronized
    @Nullable
	@Override
	public Ingredient convert(IngredientCommand source) {
		
		if (source == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUom(uomConverter.convert(source.getUnitOfMeasure()));
        return ingredient;
	}
}
