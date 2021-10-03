package com.mealmentor.enterprise.service;

import com.mealmentor.enterprise.dto.MealItem;
import com.mealmentor.enterprise.dto.Recipe;

import java.util.List;

public interface IMealPlanService {
    /**
     * Fetch a specimen with a given ID
     * @param id a unique identifier for a MealItem
     * @return the matching MealItem, or null if no matches found
     */
    MealItem fetchById(int id);

    MealItem save(MealItem mealItem) throws Exception;

    Recipe searchByName(String chicken);

    void delete(int id) throws Exception;

    List<MealItem> fetchAll();
}
