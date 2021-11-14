package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface IRecipeRetrofitDAO {
    @GET("/recipes/informationBulk?apiKey=0cea569c323f45d090b3335b48f39341&includeNutrition=true")
    Call<List<Recipe>> getRecipes(@Query("ids") String idList);
}
