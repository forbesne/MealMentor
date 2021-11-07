package com.mealmentor.enterprise;

import com.mealmentor.enterprise.dao.IMealItemDAO;
import com.mealmentor.enterprise.dto.*;
import com.mealmentor.enterprise.service.IMealPlanService;
import com.mealmentor.enterprise.service.MealPlanServiceStub;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest
class EnterpriseApplicationTests {

    private IMealPlanService mealPlanService;
    private Recipe recipe = new Recipe();
    private final MealItem mealItem = new MealItem();
    private final DailyCounter dailyCounter = new DailyCounter();
    private final Ingredient ingredient = new Ingredient();
    private final RecipeIngredient recipeIngredient = new RecipeIngredient();
    private final List<ShoppingItem> shoppingItemList = new ArrayList<>();
    private final List<MealItem> mealItemList = new ArrayList<>();
    private final List<RecipeIngredient> recipeIngredientList = new ArrayList<>();

    @MockBean
    private IMealItemDAO mealItemDAO;

    @Test
    void contextLoads() {
    }

    @Test
    void saveMealItem_validateReturnMealItemWithLasagnaMondayDinner() throws Exception  {
        givenRecipeDataAreAvailable();
        whenUserAddsANewMealItemAndSaves();
        thenCreateNewMealItemRecordAndReturnIt();
    }

    private void givenRecipeDataAreAvailable() throws Exception {
        Mockito.when(mealItemDAO.save(mealItem)).thenReturn(mealItem);
        mealPlanService = new MealPlanServiceStub(mealItemDAO);
    }

    private void whenUserAddsANewMealItemAndSaves() {
        mealItem.setMealtime("dinner");
        mealItem.setDay("Monday");
        recipe.setId(1);
        recipe.setName("Lasagna");
        mealItem.setRecipeId(recipe.getId());
    }

    private void thenCreateNewMealItemRecordAndReturnIt() throws Exception {
        MealItem addedMealItem = mealPlanService.save(mealItem);
        assertEquals(mealItem, addedMealItem);
        verify(mealItemDAO, atLeastOnce()).save(mealItem);
    }

    @Test
    void searchRecipesByName_returnsRecipesContainingChicken() throws Exception {
        givenRecipeDataAreAvailable();
        whenUserSearchesChicken();
        thenReturnRecipesContainingChicken();
    }

    private void whenUserSearchesChicken() {
        recipe = mealPlanService.searchByName("chicken");
    }

    private void thenReturnRecipesContainingChicken() {
        String name = recipe.getName();
        assertEquals("Chicken Burger", name);
    }

    @Test
    void saveDailyCounter_validateReturnDailyCounterWith120Calories() throws Exception  {
        givenRecipeDataAreAvailable();
        whenUserAddsANewMealItemWithCaloriesAndSaves();
        thenCreateDailyCounterWith120calorieCount();
    }

    private void whenUserAddsANewMealItemWithCaloriesAndSaves() {
        mealItem.setMealtime("dinner");
        mealItem.setDay("Monday");
        recipe.setName("Lasagna");
        recipe.setCalories(120);
        recipe.setId(2);
        mealItem.setRecipeId(recipe.getId());
        dailyCounter.setDay(mealItem.getDay());
        dailyCounter.setCalorieCount(recipe.getCalories());
    }

    private void thenCreateDailyCounterWith120calorieCount() {
        DailyCounter addedDailyCounter = mealPlanService.saveDailyCounter(dailyCounter);
        assertEquals(120, addedDailyCounter.getCalorieCount());
    }

    @Test
    void increaseDailyCounter_validateReturnDailyCounterWith370Calories() throws Exception  {
        givenRecipeDataAreAvailable();
        givenUserHas120CaloriesInDailyCounter();
        whenUserAddsANewMealItemWith250CaloriesAndSaves();
        thenReturnDailyCounterWith370Calories();
    }

    private void givenUserHas120CaloriesInDailyCounter() {
        dailyCounter.setDay("Monday");
        dailyCounter.setCalorieCount(120);
    }

    private void whenUserAddsANewMealItemWith250CaloriesAndSaves() {
        mealItem.setMealtime("lunch");
        mealItem.setDay("Monday");
        recipe.setName("Pizza");
        recipe.setCalories(250);
        recipe.setId(3);
        mealItem.setRecipeId(recipe.getId());
        if (Objects.equals(dailyCounter.getDay(), mealItem.getDay())) {
            dailyCounter.setCalorieCount(dailyCounter.getCalorieCount() + recipe.getCalories());
        }
        else {
            dailyCounter.setDay(mealItem.getDay());
            dailyCounter.setCalorieCount(recipe.getCalories());
        }
    }

    private void thenReturnDailyCounterWith370Calories() {
        DailyCounter addedDailyCounter = mealPlanService.saveDailyCounter(dailyCounter);
        assertEquals(370, addedDailyCounter.getCalorieCount());
    }

}
