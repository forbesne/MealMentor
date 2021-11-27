package com.mealmentor.enterprise.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.DayOfWeek;

@Entity
public @Data
class MealItem {

    @Id
    private int mealId;
    private Integer recipeId;
    private String recipeName;
    private int calories;
    private DayOfWeek dayOfWeek;

}
