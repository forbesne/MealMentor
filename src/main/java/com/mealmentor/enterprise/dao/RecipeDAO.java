package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile({"dev", "default"})
public class RecipeDAO implements IRecipeDAO {
    public List<Recipe> fetchRecipesInformation(String recipeIds) throws IOException {
        Retrofit retrofitInstance = RetrofitClientInstance.getRetrofitInstance();
        IRecipeRetrofitDAO recipeRetrofitDAO = retrofitInstance.create(IRecipeRetrofitDAO.class);
        Call<List<Recipe>> allRecipes = recipeRetrofitDAO.getRecipes(recipeIds);
        Response<List<Recipe>> executeRecipes = allRecipes.execute();
        List<Recipe> recipes = executeRecipes.body();
        return recipes;
    }
    public List<Recipe> fetchAutocompleteRecipes(String recipeName) throws IOException {
        Retrofit retrofitInstance = RetrofitClientInstance.getRetrofitInstance();
        IRecipeAutocompleteRetrofitDAO recipeRetrofitDAO = retrofitInstance.create(IRecipeAutocompleteRetrofitDAO.class);
        Call<List<Recipe>> allRecipes = recipeRetrofitDAO.getRecipes(recipeName);
        Response<List<Recipe>> executeRecipes = allRecipes.execute();
        List<Recipe> recipe = executeRecipes.body();
        return recipe;
    }
    public Nutrition fetchNutrition(int recipeId) throws IOException {
        Retrofit retrofitInstance = RetrofitClientInstance.getRetrofitInstance();
        INutritionRetrofitDAO nutritionRetrofitDAO = retrofitInstance.create(INutritionRetrofitDAO.class);
        Call<Nutrition> allNutrition = nutritionRetrofitDAO.getNutrition(recipeId);
        Response<Nutrition> executeRecipes = allNutrition.execute();
        Nutrition nutrition = executeRecipes.body();
        return nutrition;
    }

    @Override
    public Recipe fetch(int recipeId) {
        return null;
    }

    @Override
    public Recipe save(Recipe recipe) throws Exception {
        return null;
    }

    @Override
    public ShoppingList fetchShoppingList(String recipeIds) throws IOException {
        List<String> ingredientList = new ArrayList<>();
        IngredientCollection ingredientCollection = new IngredientCollection();

        List<Recipe> recipes = fetchRecipesInformation(recipeIds);

        for (Recipe recipe : recipes) {
            List<Ingredient> ingredients = recipe.getIngredients();
            for (Ingredient ingredient : ingredients) {
                ingredientList.add(ingredient.toString());
            }
        }

        ingredientCollection.setItemList(ingredientList);

        Retrofit retrofitInstance = RetrofitClientInstance.getRetrofitInstance();
        IShoppingListRetrofitDAO shoppingListRetrofitDAO = retrofitInstance.create(IShoppingListRetrofitDAO.class);
        Call<ShoppingList> allRecipes = shoppingListRetrofitDAO.getShoppingList(ingredientCollection);
        Response<ShoppingList> executeMeals = allRecipes.execute();
        ShoppingList shoppingList = executeMeals.body();
        return shoppingList;
    }
}
