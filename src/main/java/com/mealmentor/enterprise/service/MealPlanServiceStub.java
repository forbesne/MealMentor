package com.mealmentor.enterprise.service;

import com.mealmentor.enterprise.dao.IMealItemDAO;
import com.mealmentor.enterprise.dto.MealItem;
import com.mealmentor.enterprise.dto.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealPlanServiceStub implements IMealPlanService {

    private IMealItemDAO mealItemDAO;

    public MealPlanServiceStub() {

    }

    public MealPlanServiceStub(IMealItemDAO mealItemDAO) {
        this.mealItemDAO = mealItemDAO;
    }
    @Override
    public MealItem save(MealItem mealItem) {
        return mealItemDAO.save(mealItem);
    }

    @Override
    public Recipe searchByName(String chicken) {
        Recipe recipe = new Recipe();
        recipe.setName("Chicken Burger");
        return recipe;
    }
}
