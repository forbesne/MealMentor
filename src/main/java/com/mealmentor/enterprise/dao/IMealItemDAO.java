package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.MealItem;

import java.util.List;

public interface IMealItemDAO {
    /**
     * Saves a meal item to the database
     */
    MealItem save(MealItem mealItem) throws Exception;
    /**
     * Fetches all the meal items from the database
     */
    List<MealItem> fetchAll();
    /**
     * Fetches one meal item for the specified id from the database
     */
    MealItem fetch(int id);
    /**
     * Deletes one meal item for the specified id from the database
     */
    void delete(int id);

}
