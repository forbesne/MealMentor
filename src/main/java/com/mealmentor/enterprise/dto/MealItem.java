package com.mealmentor.enterprise.dto;

import lombok.Data;


public @Data
class MealItem {

    private int mealId;
    private Integer recipeId;
    private String mealTime;
    private String day;


    public String getMealItemId() {
        String mealItemId = "";
        return mealItemId;
    }
}
