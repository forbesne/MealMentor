package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.RecipeCollection;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IRecipeRetrofitDAO {
    @GET("/recipes/complexSearch?apiKey=0cea569c323f45d090b3335b48f39341&number=2&fillIngredients=true&addRecipeNutrition=true")
    Call<RecipeCollection> getRecipes(@Query("query") String recipeName);
}
