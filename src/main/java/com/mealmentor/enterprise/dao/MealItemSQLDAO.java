package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.MealItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mealItemDAO")
public class MealItemSQLDAO implements IMealItemDAO {

    @Autowired
    MealItemRepository mealItemRepository;

    @Override
    public MealItem save(MealItem mealItem) throws Exception {
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
