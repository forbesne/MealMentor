package com.mealmentor.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

public @Data
class ShoppingList {
    @SerializedName("aisles")
    public List<Aisle> aisles;
}
