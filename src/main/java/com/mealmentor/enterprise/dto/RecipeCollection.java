package com.mealmentor.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

public @Data
class RecipeCollection {
    @SerializedName("results")
    private List<Recipe> recipeList;
}
