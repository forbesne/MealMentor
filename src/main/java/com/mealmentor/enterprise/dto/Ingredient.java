package com.mealmentor.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

public @Data
class Ingredient {
    @SerializedName("name")
    public String name;
    @SerializedName("amount")
    public Double amount;
    @SerializedName("unit")
    public String unit;

    public String toString() {
        return amount + " " + unit + " " + name;
    }

    @SerializedName("originalString")
    public String originalString;
}
