package com.mealmentor.enterprise.dao;

import com.mealmentor.enterprise.dto.MealItem;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

@Profile("!test")
public interface MealItemRepository extends CrudRepository<MealItem, Integer> {

}
