package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.IngredientCollection;
import com.mealmentor.enterprise.dto.ShoppingList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IShoppingListRetrofitDAO {
    /**
     * takes an ingredient list and computes it to return a shopping list
     */
    @Headers("Content-Type: application/json")
    @POST("/mealplanner/shopping-list/compute?apiKey=1267976c3ea54d019cd319ddae5b7093")
    Call<ShoppingList> getShoppingList(@Body IngredientCollection shoppingList);
}
