package com.mealmentor.enterprise.service;

import com.mealmentor.enterprise.dao.IMealItemDAO;
import com.mealmentor.enterprise.dao.IRecipeDAO;
import com.mealmentor.enterprise.dto.DailyCounter;
import com.mealmentor.enterprise.dto.MealItem;
import com.mealmentor.enterprise.dto.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * MealPlanServiceStub handles application critical operations such as
 * fetch item by id, delete meal item, save meal item, search meal item
 * fetch all meal items, save daily counter, and fetch recipes.
 */
@Service
public class MealPlanServiceStub implements IMealPlanService {


    private IMealItemDAO mealItemDAO;

    @Autowired
    private IRecipeDAO recipeDAO;

    public MealPlanServiceStub(IMealItemDAO mealItemDAO) {

        this.mealItemDAO = mealItemDAO;
    }

    @Override
    public MealItem fetchById(int id) {
        MealItem foundMealItem = mealItemDAO.fetch(id);
        return foundMealItem;
    }

    @Override
    public void delete(int id) throws Exception {
        mealItemDAO.delete(id);
    }

    @Override
    public MealItem save(MealItem mealItem) throws Exception {
        return mealItemDAO.save(mealItem);
    }

    @Override
    public Recipe searchByName(String chicken) {
        Recipe recipe = new Recipe();
        recipe.setName("Chicken Burger");
        return recipe;
    }

    @Override
    public List<MealItem> fetchAll() {
        return mealItemDAO.fetchAll();
    }
  
    @Override
    public DailyCounter saveDailyCounter(DailyCounter dailyCounter) {
        return dailyCounter;
    }

    @Override
    public List<Recipe> fetchRecipes(String recipeName) throws IOException {
        return recipeDAO.fetchAutocompleteRecipes(recipeName);
    }


}
