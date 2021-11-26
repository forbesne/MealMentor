package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.MealItem;
import com.mealmentor.enterprise.dto.Nutrition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
@Profile({"dev", "default"})
public class MealItemSQLDAO implements IMealItemDAO {

    @Autowired
    private IRecipeDAO recipeDAO;

    MealItemRepository mealItemRepository;

    @Override
    public MealItem save(MealItem mealItem) throws Exception {
        Nutrition nutrition = recipeDAO.fetchNutrition(mealItem.getRecipeId());
        mealItem.setCalories(nutrition.getCalories());
        MealItem createdMealItem = mealItemRepository.save(mealItem);
        return createdMealItem;
    }

    @Override
    public List<MealItem> fetchAll() {
        return null;
    }

    @Override
    public MealItem fetch(int id) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
