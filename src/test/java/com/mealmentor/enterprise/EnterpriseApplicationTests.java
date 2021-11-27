package com.mealmentor.enterprise;

import com.mealmentor.enterprise.dao.IMealItemDAO;
import com.mealmentor.enterprise.dao.IRecipeDAO;
import com.mealmentor.enterprise.dto.*;
import com.mealmentor.enterprise.service.IMealPlanService;
import com.mealmentor.enterprise.service.MealPlanServiceStub;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest
class EnterpriseApplicationTests {

    private IMealPlanService mealPlanService;
    private Recipe recipe = new Recipe();
    private MealItem mealItem = new MealItem();
    private final List<MealItem> mealItemList = new ArrayList<>();
    private final Nutrition nutrition = new Nutrition();

    @MockBean
    private IMealItemDAO mealItemDAO;

    @MockBean
    private IRecipeDAO recipeDAO;
    private final List<String> shoppingList = new ArrayList<>();

    @Test
    void contextLoads() {
    }

    @Test
    void saveMealItem_validateReturnMealItemWithLasagnaMondayDinner() throws Exception  {
        givenNewRecipeDataAreAvailable();
        givenRecipeDataAreAvailable();
        whenUserAddsANewMealItemAndSaves();
        thenCreateNewMealItemRecordAndReturnIt();
    }

    private void givenNewRecipeDataAreAvailable() throws Exception {
        Mockito.when(recipeDAO.save(recipe)).thenReturn(recipe);
        mealPlanService = new MealPlanServiceStub(recipeDAO);
    }

    private void givenRecipeDataAreAvailable() throws Exception {
        Mockito.when(mealItemDAO.save(mealItem)).thenReturn(mealItem);
        mealPlanService = new MealPlanServiceStub(mealItemDAO);
    }

    private void whenUserAddsANewMealItemAndSaves() {
        mealItem.setMealId(15); //1 indicates a Monday and 5 indicates dinner
        mealItem.setDayOfWeek(DayOfWeek.MONDAY);
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
    void saveDailyCalories_validateReturnDailyCaloriesWith120Calories() throws Exception  {
        givenRecipeDataAreAvailable();
        whenUserAddsANewMealItemWithCaloriesAndSaves();
        thenCalculateDailyCaloriesWith120calorieCount();
    }

    private void whenUserAddsANewMealItemWithCaloriesAndSaves() {
        mealItem.setMealId(15); //1 indicates a Monday and 5 indicates dinner
        mealItem.setDayOfWeek(DayOfWeek.MONDAY);
        nutrition.setCalories("120");
        recipe.setNutrition(nutrition);
        recipe.setName("Lasagna");
        recipe.setId(2);
        mealItem.setRecipeId(recipe.getId());
        mealItem.setCalories(recipe.getNutrition().getCalories());
        mealItemList.add(mealItem);
    }

    private void thenCalculateDailyCaloriesWith120calorieCount() {
        int totalCalories = 0;
        for (MealItem mealItem : mealItemList) {
            if (mealItem.getDayOfWeek() == DayOfWeek.MONDAY) {
                totalCalories += mealItem.getCalories();
            }
        }
        assertEquals(120, totalCalories);
    }

    @Test
    void increaseDailyCalories_validateReturnDailyCaloriesWith370Calories() throws Exception  {
        givenRecipeDataAreAvailable();
        givenUserHas120CaloriesInDailyCalories();
        whenUserAddsANewMealItemWith250CaloriesAndSaves();
        thenReturnDailyCounterWith370Calories();
    }

    private void givenUserHas120CaloriesInDailyCalories() {
        whenUserAddsANewMealItemWithCaloriesAndSaves();
    }

    private void whenUserAddsANewMealItemWith250CaloriesAndSaves() {
        mealItem = new MealItem();
        mealItem.setMealId(13); //1 indicates a Monday and 3 indicates lunch
        mealItem.setDayOfWeek(DayOfWeek.MONDAY);
        nutrition.setCalories("250");
        recipe.setNutrition(nutrition);
        recipe.setName("Pizza");
        recipe.setId(3);
        mealItem.setRecipeId(recipe.getId());
        mealItem.setCalories(recipe.getNutrition().getCalories());
        mealItemList.add(mealItem);
    }

    private void thenReturnDailyCounterWith370Calories() {
        int totalCalories = 0;
        for (MealItem mealItem : mealItemList) {
            if (mealItem.getDayOfWeek() == DayOfWeek.MONDAY) {
                totalCalories += mealItem.getCalories();
            }
        }
        assertEquals(370, totalCalories);
    }

    @Test
    void createShoppingList_validateReturnShoppingList() throws Exception {
        givenNewRecipeDataAreAvailable();
        givenUserSavedMeals();
        whenUserGeneratesShoppingList();
        thenDisplayList();
    }

    private void givenUserSavedMeals() throws Exception {
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient = new Ingredient();
        ingredient.setOriginalString("1 tbsp butter");
        ingredients.add(ingredient);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setOriginalString("about 2 cups frozen cauliflower florets");
        ingredients.add(ingredient2);

        recipe.setId(2);
        recipe.setName("Pasta with chicken");
        recipe.setIngredients(ingredients);
        Recipe createdRecipe = mealPlanService.saveRecipe(recipe);
        Mockito.when(recipeDAO.fetch(2)).thenReturn(createdRecipe);
        MealItem mealItem = new MealItem();
        mealItem.setMealId(1);
        mealItem.setRecipeId(recipe.getId());

        mealItemList.add(mealItem);
    }

    private void whenUserGeneratesShoppingList() {

        for (MealItem mi : mealItemList) {
            recipe = mealPlanService.fetchRecipeById(mi.getRecipeId());
            List<Ingredient> ingredients = recipe.getIngredients();
            for (Ingredient ingredient : ingredients) {
                shoppingList.add(ingredient.originalString);
            }

        }
    }

    private void thenDisplayList() {
        assertEquals(2, shoppingList.size());
    }
}
