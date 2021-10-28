package com.mealmentor.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

public @Data
class Nutrition {
    @SerializedName("nutrients")
    public List<Nutrient> nutrients;
}
