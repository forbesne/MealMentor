package com.mealmentor.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

public @Data
class Nutrition {
    @SerializedName("nutrients")
    public List<Nutrient> nutrients;
    @SerializedName("calories")
    public String calories;
    @SerializedName("carbs")
    public String carbs;
    @SerializedName("fat")
    public String fat;
    @SerializedName("protein")
    public String protein;

    public int getCalories() {
        return Integer.parseInt(calories.replaceAll("[^\\d.]", ""));
    }

}
