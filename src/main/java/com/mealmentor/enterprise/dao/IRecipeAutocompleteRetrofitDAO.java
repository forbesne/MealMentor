package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface IRecipeAutocompleteRetrofitDAO {
    /**
     * returns a list of recipes containing their name and id based on the partial string entered for the name
     */
    @GET("/recipes/autocomplete?number=10&apiKey=0cea569c323f45d090b3335b48f39341")
    Call<List<Recipe>> getRecipes(@Query("query") String recipeName);
}
