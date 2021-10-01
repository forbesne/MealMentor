package com.mealmentor.enterprise.dto;

import lombok.Data;

public @Data
class RecipeIngredient {
    private Ingredient ingredient;
    private int quantity;
}
