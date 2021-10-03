package com.mealmentor.enterprise;

import com.mealmentor.enterprise.dao.IMealItemDAO;
import com.mealmentor.enterprise.dto.MealItem;
import com.mealmentor.enterprise.dto.Recipe;
import com.mealmentor.enterprise.service.IMealPlanService;
import com.mealmentor.enterprise.service.MealPlanServiceStub;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest
class EnterpriseApplicationTests {

    private IMealPlanService mealPlanService;
    private Recipe recipe = new Recipe();
    private MealItem mealItem = new MealItem();

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
}
