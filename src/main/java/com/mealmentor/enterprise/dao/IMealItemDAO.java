package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.MealItem;

import java.util.List;

public interface IMealItemDAO {
    MealItem save(MealItem mealItem) throws Exception;

    List<MealItem> fetchAll();

    MealItem fetch(int id);

    void delete(int id);

}
