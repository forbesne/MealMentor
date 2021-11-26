package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.Nutrition;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface INutritionRetrofitDAO {
    @GET("/recipes/{id}/nutritionWidget.json?apiKey=0cea569c323f45d090b3335b48f39341")
    Call<Nutrition> getNutrition(@Path("id") int recipeId);
}
