package com.mealmentor.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

public @Data
class Recipe {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String name;
    @SerializedName("dishTypes")
    public List<String> dishTypes;
    private int calories;
    private List<RecipeIngredient> recipeIngredientList;
    @SerializedName("readyInMinutes")
    public Integer readyInMinutes;
    @SerializedName("servings")
    public Integer servings;
    @SerializedName("extendedIngredients")
    public List<Ingredient> ingredients;
    @SerializedName("nutrition")
    public Nutrition nutrition;

    public String toString ()
    {
        return name + " " +dishTypes+ " " + calories + " " + id;
    }
}
