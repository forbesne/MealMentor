package com.mealmentor.enterprise.dto;

import lombok.Data;

public @Data
class MealItem {
    private String mealtime;
    private String day;
    private Recipe recipe;

    public String getMealItemId() {
        String mealItemId = "";
        return mealItemId;
    }
}
