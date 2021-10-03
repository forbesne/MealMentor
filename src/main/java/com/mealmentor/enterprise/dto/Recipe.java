package com.mealmentor.enterprise.dto;

import lombok.Data;

import java.util.List;

public @Data
class Recipe {
    private String name;
    private int calories;
    private List<RecipeIngredient> recipeIngredientList;

}
