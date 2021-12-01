package com.mealmentor.enterprise.service;

import com.mealmentor.enterprise.dto.MealItem;
import com.mealmentor.enterprise.dto.Recipe;
import com.mealmentor.enterprise.dto.ShoppingList;

import java.io.IOException;
import java.util.List;

public interface IMealPlanService {
    /**
     * Fetch a meal with a given ID
     */
    MealItem fetchById(int id);

    /**
     * save a meal item to the database
     */
    MealItem save(MealItem mealItem) throws Exception;

    Recipe searchByName(String chicken);

    void delete(int id) throws Exception;

    List<MealItem> fetchAll();

    /**
     * used for recipe autocomplete
     */
    List<Recipe> fetchRecipes(String recipeName) throws IOException;

    Recipe saveRecipe(Recipe recipe) throws Exception;

    Recipe fetchRecipeById(Integer recipeId);

    List<Recipe> fetchRecipesInformation(String recipeIds) throws IOException;

    /**
     * compute shopping list based on meals in the database
     */
    ShoppingList fetchShoppingList() throws IOException;

    /**
     * calculate calories for each day of the week
     */
    int[] getTotalCalories();
}
