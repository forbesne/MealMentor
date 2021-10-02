package com.mealmentor.enterprise.service;

import com.mealmentor.enterprise.dto.DailyCounter;
import com.mealmentor.enterprise.dto.MealItem;
import com.mealmentor.enterprise.dto.Recipe;

public interface IMealPlanService {
    MealItem save(MealItem mealItem) throws Exception;

    Recipe searchByName(String chicken);

    DailyCounter saveDailyCounter(DailyCounter dailyCounter);
}
