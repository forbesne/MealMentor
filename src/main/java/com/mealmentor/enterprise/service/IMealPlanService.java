package com.mealmentor.enterprise.service;

import com.mealmentor.enterprise.dto.MealItem;
import com.mealmentor.enterprise.dto.Recipe;

public interface IMealPlanService {
    MealItem save(MealItem mealItem);

    Recipe searchByName(String chicken);
}
