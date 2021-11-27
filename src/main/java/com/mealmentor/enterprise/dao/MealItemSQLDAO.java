package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.MealItem;
import com.mealmentor.enterprise.dto.Nutrition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository()
@Profile({"dev", "default"})
public class MealItemSQLDAO implements IMealItemDAO {

    @Autowired
    private IRecipeDAO recipeDAO;

    private final MealItemRepository mealItemRepository;
    public MealItemSQLDAO(MealItemRepository mealItemRepository) {
        this.mealItemRepository = mealItemRepository;
    }

    @Override
    public MealItem save(MealItem mealItem) throws Exception {
        Nutrition nutrition = recipeDAO.fetchNutrition(mealItem.getRecipeId());
        mealItem.setCalories(nutrition.getCalories());
        MealItem createdMealItem = mealItemRepository.save(mealItem);
        return createdMealItem;
    }

    @Override
    public List<MealItem> fetchAll() {
        List<MealItem> allMealItems = new ArrayList<>();
        Iterable<MealItem> mealItems = mealItemRepository.findAll();
        for (MealItem mealItem : mealItems) {
            allMealItems.add(mealItem);
        }
        return allMealItems;
    }

    @Override
    public MealItem fetch(int id) {
        return mealItemRepository.findById(id).get();
    }

    @Override
    public void delete(int id) {
        mealItemRepository.deleteById(id);
    }
}
