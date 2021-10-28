package com.mealmentor.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

public @Data
class Nutrient {
    @SerializedName("name")
    public String name;
    @SerializedName("title")
    public String title;
    @SerializedName("amount")
    public Double amount;
    @SerializedName("unit")
    public String unit;
}
