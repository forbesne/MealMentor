package com.mealmentor.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

public @Data
class Metric {
    @SerializedName("amount")
    public Double amount;
    @SerializedName("unit")
    public String unit;
}
