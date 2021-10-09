package com.mealmentor.enterprise.service;

import com.mealmentor.enterprise.dto.DailyCounter;
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

    /**
     * Save a mealItem
     * @param mealItem
     * @return MealItem
     * @throws Exception
     */
    MealItem save(MealItem mealItem) throws Exception;

    /**
     * Search a recipe by name
     * @param chicken
     * @return Recipe
     */
    Recipe searchByName(String chicken);

    /**
     * Delete a specimen with a given ID
     * @param id
     * @throws Exception
     */
    void delete(int id) throws Exception;

    /**
     * Fetch all specimens
     * @return
     */
    List<MealItem> fetchAll();

    /**
     * Save Daily Counter
     * @param dailyCounter
     * @return DailyCounter
     */
    DailyCounter saveDailyCounter(DailyCounter dailyCounter);

}
