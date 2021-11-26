package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.Nutrition;
import com.mealmentor.enterprise.dto.Recipe;
import com.mealmentor.enterprise.dto.ShoppingList;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile("test")
public class RecipeDAOStub implements IRecipeDAO {
    Map<Integer, Recipe> allRecipes = new HashMap<>();
    @Override
    public Recipe fetch(int recipeId) {
        return allRecipes.get(recipeId);
    }

    @Override
    public Recipe save(Recipe recipe) throws Exception {
        Integer recipeID = recipe.getId();
        allRecipes.put(recipeID, recipe);
        return recipe;
    }

    @Override
    public ShoppingList fetchShoppingList(String recipeIds) throws IOException {
        return null;
    }

    @Override
    public Nutrition fetchNutrition(int recipeId) throws IOException {
        return null;
    }

    @Override
    public List<Recipe> fetchRecipesInformation(String recipeIds) throws IOException {
        return null;
    }

    @Override
    public List<Recipe> fetchAutocompleteRecipes(String recipeName) throws IOException {
        return null;
    }
}
