package com.mealmentor.enterprise.service;

import com.mealmentor.enterprise.dto.MealItem;
import com.mealmentor.enterprise.dto.Recipe;
import com.mealmentor.enterprise.dto.ShoppingList;

import java.io.IOException;
import java.util.List;

public interface IMealPlanService {
    /**
     * Fetch a specimen with a given ID
     * @param id a unique identifier for a MealItem
     * @return the matching MealItem, or null if no matches found
     */
    MealItem fetchById(int id);

    MealItem save(MealItem mealItem) throws Exception;

    Recipe searchByName(String chicken);

    void delete(int id) throws Exception;

    List<MealItem> fetchAll();

    List<Recipe> fetchRecipes(String recipeName) throws IOException;

    Recipe saveRecipe(Recipe recipe) throws Exception;

    Recipe fetchRecipeById(Integer recipeId);

    List<Recipe> fetchRecipesInformation(String recipeIds) throws IOException;

    ShoppingList fetchShoppingList() throws IOException;

    int[] getTotalCalories();
}
