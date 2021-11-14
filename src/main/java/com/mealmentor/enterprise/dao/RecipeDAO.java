package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.Recipe;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
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

    @Override
    public Recipe fetch(int recipeId) {
        return null;
    }

    @Override
    public Recipe save(Recipe recipe) throws Exception {
        return null;
    }
}
