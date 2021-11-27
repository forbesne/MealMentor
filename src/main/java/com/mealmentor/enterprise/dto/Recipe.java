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
    @SerializedName("readyInMinutes")
    public Integer readyInMinutes;
    @SerializedName("servings")
    public Integer servings;
    @SerializedName("extendedIngredients")
    public List<Ingredient> ingredients;
    @SerializedName("nutrition")
    public Nutrition nutrition;
    @SerializedName("instructions")
    public String instructions;
    @SerializedName("sourceUrl")
    public String sourceUrl;
    @SerializedName("image")
    public String image;
    @SerializedName("imageType")
    public String imageType;
    public String toString ()
    {
        return name + " " + id;
    }
}
