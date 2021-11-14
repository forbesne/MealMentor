package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.Recipe;

import java.io.IOException;
import java.util.List;

public interface IRecipeDAO {
    /**
     * Returns all recipe information including ingredients and nutrients
     */
    List<Recipe> fetchRecipesInformation(String recipeIds) throws IOException;
    /**
     * returns id, title, imageType for 10 recipes
     * requires less points than the complex search
     */
    List<Recipe> fetchAutocompleteRecipes(String recipeName) throws IOException;

    Recipe fetch(int recipeId);

    Recipe save(Recipe recipe) throws Exception;
}
