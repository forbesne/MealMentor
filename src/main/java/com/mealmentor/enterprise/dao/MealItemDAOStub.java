package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.MealItem;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@Profile("test")
public class MealItemDAOStub implements IMealItemDAO {

    HashMap<Integer, MealItem> allMealItems = new HashMap<>();

    @Override
    public MealItem save(MealItem mealItem) throws Exception {
        Integer mealItemID = Integer.parseInt(mealItem.getMealItemId());
        allMealItems.put(mealItemID, mealItem);
        return mealItem;
    }

    @Override
    public List<MealItem> fetchAll() {
        return new ArrayList<>(allMealItems.values());
    }

    @Override
    public MealItem fetch(int id) {
        return allMealItems.get(id);
    }

    @Override
    public void delete(int id) {
        allMealItems.remove(id);

    }
}
