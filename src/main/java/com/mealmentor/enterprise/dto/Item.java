package com.mealmentor.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

public @Data
class Item {
    @SerializedName("name")
    public String name;
    @SerializedName("measures")
    public Measures measures;
}
