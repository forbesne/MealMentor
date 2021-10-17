package com.mealmentor.enterprise;

import com.mealmentor.enterprise.dao.IMealItemDAO;
import com.mealmentor.enterprise.dao.ISpoonacularDAO;
import com.mealmentor.enterprise.dao.SpoonacularDAO;
import com.mealmentor.enterprise.dao.TDEEDAO;
import com.mealmentor.enterprise.dto.*;
import com.mealmentor.enterprise.service.IMealPlanService;
import com.mealmentor.enterprise.service.MealPlanServiceStub;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.net.URISyntaxException;
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
    private MealItem mealItem = new MealItem();
    private DailyCounter dailyCounter = new DailyCounter();
    private Ingredient ingredient = new Ingredient();
    private RecipeIngredient recipeIngredient = new RecipeIngredient();
    private List<ShoppingItem> shoppingItemList = new ArrayList<>();
    private List<MealItem> mealItemList = new ArrayList<>();
    private List<RecipeIngredient> recipeIngredientList = new ArrayList<>();
    private SpoonacularDAO spoonacularDAO = new SpoonacularDAO();
    private TDEEDAO tdeeDAO = new TDEEDAO();

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
        recipe.setName("Lasagna");
        mealItem.setRecipe(recipe);
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

    @Test
    void createShoppingList_validateReturnShoppingList() throws Exception {
        givenUserSavedMeals();
        whenUserGeneratesShoppingList();
        thenDisplayList();
    }

    private void givenUserSavedMeals() {

        recipe.setName("Pizza");

        ingredient.setName("dough");
        ingredient.setUnit("grams");
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setQuantity(500);
        recipeIngredientList.add(recipeIngredient);

        ingredient = new Ingredient();
        ingredient.setName("cheese");
        ingredient.setUnit("grams");

        recipeIngredient = new RecipeIngredient();
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setQuantity(50);
        recipeIngredientList.add(recipeIngredient);

        recipe.setRecipeIngredientList(recipeIngredientList);
        mealItem.setRecipe(recipe);
        mealItemList.add(mealItem);

        recipe = new Recipe();
        recipe.setName("Lasagna");

        recipeIngredient = new RecipeIngredient();
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setQuantity(100);
        recipeIngredientList = new ArrayList<>();
        recipeIngredientList.add(recipeIngredient);

        recipe.setRecipeIngredientList(recipeIngredientList);
        mealItem = new MealItem();
        mealItem.setRecipe(recipe);
        mealItemList.add(mealItem);

    }

    private void whenUserGeneratesShoppingList() {
        for (MealItem mi : mealItemList) {
            ShoppingItem shoppingItem = new ShoppingItem();
            List<RecipeIngredient> recipeIngredientList = mi.getRecipe().getRecipeIngredientList();
            for (RecipeIngredient ri : recipeIngredientList) {
                shoppingItem.setIngredient(ri.getIngredient());
                shoppingItem.setQuantity(ri.getQuantity());
                shoppingItemList.add(shoppingItem);
            }
        }
    }

    private void thenDisplayList() {
        assertEquals(3, shoppingItemList.size());
    }

    @Test
    void createEmptyShoppingList_validateReturnNothing() throws Exception {
        givenUserNeverSavedMeals();
        whenUserGeneratesShoppingList();
        thenDisplayNothing();
    }

    private void givenUserNeverSavedMeals() {

    }

    private void thenDisplayNothing() {
        assertEquals(0, shoppingItemList.size());
    }

    @Test
    void getMealItemFromNameAndCalorieThreshold(){
        String response = spoonacularDAO.get("pasta", 2000);
        System.out.println("The response for this method call was: " + response);
        assert(response!=null);
    }

    @Test
    void getTDEEFromUserInfo() throws URISyntaxException, IOException {
        int targetTDEE = tdeeDAO.post("6", "1", "220", "200", "28", "1.375", "2", "15", "30", "male");
        assert(targetTDEE == 2529);
    }

}
