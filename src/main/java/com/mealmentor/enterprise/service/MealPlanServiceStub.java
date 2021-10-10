package com.mealmentor.enterprise.service;

import com.mealmentor.enterprise.dao.IMealItemDAO;
import com.mealmentor.enterprise.dto.DailyCounter;
import com.mealmentor.enterprise.dto.MealItem;
import com.mealmentor.enterprise.dto.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealPlanServiceStub implements IMealPlanService {


    private IMealItemDAO mealItemDAO;

    public MealPlanServiceStub(IMealItemDAO mealItemDAO) {

        this.mealItemDAO = mealItemDAO;
    }

    @Override
    public MealItem fetchById(int id) {
        MealItem foundMealItem = mealItemDAO.fetch(id);
        return foundMealItem;
    }

    @Override
    public void delete(int id) {
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

}
