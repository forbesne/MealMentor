package com.mealmentor.enterprise;

import com.mealmentor.enterprise.dto.MealItem;
import com.mealmentor.enterprise.dto.RecipeLabelValue;
import com.mealmentor.enterprise.service.IMealPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mealmentor.enterprise.dto.Recipe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MealMentorController {

    @Autowired
    IMealPlanService mealPlanService;

    @RequestMapping("/")
    public String index() {
        return "start";
    }

    @GetMapping("/mealItem")
    @ResponseBody
    public List<MealItem> fetchAllMealItems(){

        return mealPlanService.fetchAll();
    }

    /**
     * Fetch a meal item by a unique Id
     * @param id unique Id
     * @return meal item with the given Id
     */
    @GetMapping("/mealItem/{id}/")
    public ResponseEntity fetchMealItemById(@PathVariable("id") String id) {
        MealItem foundMealItem = mealPlanService.fetchById(Integer.parseInt(id));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(foundMealItem, headers, HttpStatus.OK);
    }

    /**
     * Create a new meal item
     * @param mealItem meal Item object to be created
     * @return newly created meal item
     * @throws Exception
     */
    @PostMapping(value="/mealItem", consumes="application/json", produces="application/json")
    public MealItem createMealItem(@RequestBody MealItem mealItem) throws Exception {

        MealItem newMealItem = null;
        try{
            newMealItem = mealPlanService.save(mealItem);
        } catch (Exception e) {
            //TODO ADD LOGGING
            System.out.println("tried to access methods in business logic but failed");
        }
        return newMealItem;
    }


    /**
     * Delete a meal item by a unique Id
     * @param id Unique Id
     * @return HTTP status code 200 if successful, 500 if not successfull
     */
    @DeleteMapping("/mealItem/{id}/")
    public ResponseEntity deleteMealItem(@PathVariable("id") String id) {
        try {
            mealPlanService.delete(Integer.parseInt(id));
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String read(Model model){
        model.addAttribute("recipe", new Recipe());
        return "start";
    }

    /**
     * Search recipe by the search term
     * @param searchTerm the search term
     * @param model model
     * @return Return user back to the recipes page, with the loaded model object
     * @throws IOException
     */
    @GetMapping(value = "/searchRecipe")
    @ResponseBody
    public String searchRecipe(@RequestParam(value="searchTerm", required=false, defaultValue="None")  String searchTerm, Model model) throws IOException {
        try {
            List<Recipe> recipes= mealPlanService.fetchRecipes(searchTerm);
            model.addAttribute("recipes", recipes);
            return "recipes";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

    }


    /**
     * Saves a meal item
     * @param mealItem meal item
     * @return Return user back to the start page
     */
    @RequestMapping("/saveMeal")
    public String saveMeal(MealItem mealItem){
        try{
            mealPlanService.save(mealItem);
        } catch (Exception e) {
            e.printStackTrace();
            return "start";

        }
        return "start";
    }


    /**
     * Autocomplete method for jquery autocomplete
     * @param term term to use for autocomplete
     * @return  possible recipe list based on the term
     */
    @GetMapping("/recipeNameAutocomplete")
    @ResponseBody
    public List<RecipeLabelValue> recipeNameAutocomplete(@RequestParam(value="term", required = false, defaultValue="") String term) {

        List <RecipeLabelValue> allRecipeNames= new ArrayList<RecipeLabelValue>();

    try {
        List<Recipe> recipes = mealPlanService.fetchRecipes(term);
        for (Recipe recipe : recipes)
        {
            RecipeLabelValue recipeLabelValue = new RecipeLabelValue();
            recipeLabelValue.setLabel(recipe.getName());
            recipeLabelValue.setValue(recipe.getId());
            allRecipeNames.add(recipeLabelValue);
        }
    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<RecipeLabelValue>();
    }

    return allRecipeNames;

    }

}
