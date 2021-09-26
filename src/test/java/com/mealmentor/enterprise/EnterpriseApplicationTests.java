package com.mealmentor.enterprise;

import com.mealmentor.enterprise.dao.IMealItemDAO;
import com.mealmentor.enterprise.dto.DailyCounter;
import com.mealmentor.enterprise.dto.MealItem;
import com.mealmentor.enterprise.dto.Recipe;
import com.mealmentor.enterprise.service.IMealPlanService;
import com.mealmentor.enterprise.service.MealPlanServiceStub;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest
class EnterpriseApplicationTests {

    private IMealPlanService mealPlanService;
    private Recipe recipe = new Recipe();
    private MealItem mealItem = new MealItem();
    private DailyCounter dailyCounter = new DailyCounter();

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

    private void givenRecipeDataAreAvailable() {
        Mockito.when(mealItemDAO.save(mealItem)).thenReturn(mealItem);
        mealPlanService = new MealPlanServiceStub(mealItemDAO);
    }

    private void whenUserAddsANewMealItemAndSaves() {
        mealItem.setMealtime("dinner");
        mealItem.setDay("Monday");
        recipe.setName("Lasagna");
        mealItem.setRecipe(recipe);
    }

    private void thenCreateNewMealItemRecordAndReturnIt() {
        MealItem addedMealItem = mealPlanService.save(mealItem);
        assertEquals(mealItem, addedMealItem);
        verify(mealItemDAO, atLeastOnce()).save(mealItem);
    }

    @Test
    void searchRecipesByName_returnsRecipesContainingChicken()  {
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
        mealItem.setRecipe(recipe);
        dailyCounter.setDay(mealItem.getDay());
        dailyCounter.setCalorieCount(mealItem.getRecipe().getCalories());
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
        mealItem.setRecipe(recipe);
        if (Objects.equals(dailyCounter.getDay(), mealItem.getDay())) {
            dailyCounter.setCalorieCount(dailyCounter.getCalorieCount() + mealItem.getRecipe().getCalories());
        }
        else {
            dailyCounter.setDay(mealItem.getDay());
            dailyCounter.setCalorieCount(mealItem.getRecipe().getCalories());
        }
    }

    private void thenReturnDailyCounterWith370Calories() {
        DailyCounter addedDailyCounter = mealPlanService.saveDailyCounter(dailyCounter);
        assertEquals(370, addedDailyCounter.getCalorieCount());
    }

}
