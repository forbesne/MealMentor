package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.MealItem;
import org.springframework.data.repository.CrudRepository;

public interface MealItemRepository extends CrudRepository<MealItem, Integer> {

}
