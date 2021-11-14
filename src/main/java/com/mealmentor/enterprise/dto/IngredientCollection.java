package com.mealmentor.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

public @Data
class IngredientCollection {
    @SerializedName("items")
    private List<String> itemList;
}
