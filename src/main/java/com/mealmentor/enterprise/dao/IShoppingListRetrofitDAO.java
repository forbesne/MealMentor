package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.IngredientCollection;
import com.mealmentor.enterprise.dto.ShoppingList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IShoppingListRetrofitDAO {
    @Headers("Content-Type: application/json")
    @POST("/mealplanner/shopping-list/compute?apiKey=0cea569c323f45d090b3335b48f39341")
    Call<ShoppingList> getShoppingList(@Body IngredientCollection shoppingList);
}