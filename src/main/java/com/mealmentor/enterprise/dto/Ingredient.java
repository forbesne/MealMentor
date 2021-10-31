package com.mealmentor.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

public @Data
class Ingredient {
    private String name;
    private String unit;
    @SerializedName("originalString")
    public String originalString;
}
