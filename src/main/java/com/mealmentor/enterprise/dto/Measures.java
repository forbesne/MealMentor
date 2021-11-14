package com.mealmentor.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

public @Data
class Measures {
    @SerializedName("metric")
    public Metric metric;
    @SerializedName("us")
    public Us us;
}
