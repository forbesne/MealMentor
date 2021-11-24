package com.mealmentor.enterprise.dto;

import lombok.Data;


public @Data
class MealItem {

    private int mealId;
    private Integer recipeId;
    private String mealtime;
    private String mealDateTimeId;
    private String day;


    public String getMealItemId() {
        String mealItemId = "";
        return mealItemId;
    }
}
