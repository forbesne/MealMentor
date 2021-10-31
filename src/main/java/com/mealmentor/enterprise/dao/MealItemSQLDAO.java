package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.MealItem;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile({"dev", "default"})
public class MealItemSQLDAO implements IMealItemDAO {

    MealItemRepository mealItemRepository;

    @Override
    public MealItem save(MealItem mealItem) throws Exception {
        return null;
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
