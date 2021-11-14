package com.mealmentor.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

public @Data
class Aisle {
    @SerializedName("aisle")
    public String aisle;
    @SerializedName("items")
    public List<Item> items = null;
}
