package com.mealmentor.enterprise.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public @Data
class MealItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer recipeId;
    private String mealtime;
    private String day;
    private Integer calories;


    public String getMealItemId() {
        String mealItemId = "";
        return mealItemId;
    }
}
