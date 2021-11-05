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

    /**
     * Fetch meal item by id
     * @param id a unique identifier for a MealItem
     * @return MealItem
     */
    @Override
    public MealItem fetchById(int id) {
        MealItem foundMealItem = mealItemDAO.fetch(id);
        return foundMealItem;
    }

    /**
     * Delete meal item of specific id
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(int id) throws Exception {
        mealItemDAO.delete(id);
    }

    /**
     * Save a meal item
     * @param mealItem
     * @return saved meal item
     * @throws Exception
     */
    @Override
    public MealItem save(MealItem mealItem) throws Exception {
        return mealItemDAO.save(mealItem);
    }

    /**
     * Search recipe by name
     * @param chicken
     * @return recipe
     */
    @Override
    public Recipe searchByName(String chicken) {
        Recipe recipe = new Recipe();
        recipe.setName("Chicken Burger");
        return recipe;
    }

    /**
     * Fetch all meal items
     * @return list of meal items
     */
    @Override
    public List<MealItem> fetchAll() {
        return mealItemDAO.fetchAll();
    }

    /**
     * Save daily calorie counter
     * @param dailyCounter
     * @return daily counter object
     */
    @Override
    public DailyCounter saveDailyCounter(DailyCounter dailyCounter) {
        return dailyCounter;
    }

    /**
     * Fetch recipe by name
     * @param recipeName
     * @return recipe
     * @throws IOException
     */
    @Override
    public List<Recipe> fetchRecipes(String recipeName) throws IOException {
        return recipeDAO.fetchAutocompleteRecipes(recipeName);
    }


}
