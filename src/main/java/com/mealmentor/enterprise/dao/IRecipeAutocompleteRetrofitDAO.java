package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface IRecipeAutocompleteRetrofitDAO {
    @GET("/recipes/autocomplete?number=10&apiKey=")
    Call<List<Recipe>> getRecipes(@Query("query") String recipeName);
}
