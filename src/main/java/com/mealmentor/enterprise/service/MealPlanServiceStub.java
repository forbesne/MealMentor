package com.mealmentor.enterprise.service;

import com.mealmentor.enterprise.dao.IMealItemDAO;
import com.mealmentor.enterprise.dao.IRecipeDAO;
import com.mealmentor.enterprise.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.List;

@Service
public class MealPlanServiceStub implements IMealPlanService {

    @Autowired
    private IMealItemDAO mealItemDAO;

    @Autowired
    private IRecipeDAO recipeDAO;

    public MealPlanServiceStub() {

    }

    public MealPlanServiceStub(IMealItemDAO mealItemDAO) {

        this.mealItemDAO = mealItemDAO;
    }

    public MealPlanServiceStub(IRecipeDAO recipeDAO) {

        this.recipeDAO = recipeDAO;
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

    @Override
    public Recipe saveRecipe(Recipe recipe) throws Exception {
        return recipeDAO.save(recipe);
    }

    @Override
    public Recipe fetchRecipeById(Integer recipeId) {
        return recipeDAO.fetch(recipeId);
    }

    @Override
    public List<Recipe> fetchRecipesInformation(String recipeIds) throws IOException {
        return recipeDAO.fetchRecipesInformation(recipeIds);
    }

    @Override
    public ShoppingList fetchShoppingList(String recipeIds) throws IOException {
        return recipeDAO.fetchShoppingList(recipeIds);
    }

    @Override
    public int getTotalCalories(DayOfWeek day, List<MealItem> mealItems) {
        int totalCalories = 0;
        for (MealItem mealItem : mealItems) {
            if (mealItem.getDayOfWeek() == day) {
                totalCalories += mealItem.getCalories();
            }
        }
        return totalCalories;
    }
}
