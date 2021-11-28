package com.mealmentor.enterprise;

import com.mealmentor.enterprise.dto.MealItem;
import com.mealmentor.enterprise.dto.RecipeLabelValue;
import com.mealmentor.enterprise.dto.ShoppingList;
import com.mealmentor.enterprise.service.IMealPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import com.mealmentor.enterprise.dto.Recipe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MealMentorController {

    @Autowired
    IMealPlanService mealPlanService;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    public String index(Model model) {
        MealItem mealItem = new MealItem();
        mealItem.setMealId(1);
        mealItem.setRecipeId(1);
        model.addAttribute(mealItem);
        return "start";
    }

    @GetMapping("/mealItem")
    @ResponseBody
    public List<MealItem> fetchAllMealItems(){

        return mealPlanService.fetchAll();
    }

    @GetMapping("/mealItem/{id}/")
    public ResponseEntity fetchMealItemById(@PathVariable("id") String id) {
        MealItem foundMealItem = mealPlanService.fetchById(Integer.parseInt(id));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(foundMealItem, headers, HttpStatus.OK);
    }

    @PostMapping(value="/mealItem", consumes="application/json", produces="application/json")
    public MealItem createMealItem(@RequestBody MealItem mealItem) throws Exception {

        MealItem newMealItem = null;
        try{
            newMealItem = mealPlanService.save(mealItem);
        } catch (Exception e) {
            //TODO ADD LOGGING
            log.error("mealItem :" + e);
            System.out.println("tried to access methods in business logic but failed");
        }
        return newMealItem;
    }

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
        int[] totalCals = mealPlanService.getTotalCalories();
        for (int i=0; i<totalCals.length; i++)
        {
            model.addAttribute("Cals" + String.valueOf(i), totalCals[i]);
        }

        List<MealItem> meals = mealPlanService.fetchAll();
        for (MealItem meal:meals) {
            model.addAttribute("Meal" + String.valueOf(meal.getMealId()), meal.getRecipeName());
        }

        return "start";
    }

    @GetMapping(value = "/searchRecipe", consumes="application/json", produces="application/json")
    public ResponseEntity searchRecipeForm(@RequestParam(value="searchTerm", required=false, defaultValue="None")  String searchTerm) throws IOException {
        try {
            List<Recipe> recipes= mealPlanService.fetchRecipes(searchTerm);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity(recipes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping(value = "/searchRecipe")
    public String searchRecipe(@RequestParam(value="searchTerm", required=false, defaultValue="None")  String searchTerm, Model model) throws IOException {
        try {
            List<Recipe> recipesList = mealPlanService.fetchRecipes(searchTerm);
            String recipeIds = "";
            for (Recipe recipe : recipesList) {
                recipeIds += recipe.getId() + ",";
            }
            recipeIds = recipeIds.substring(0, recipeIds.length() - 1);
            List<Recipe> recipes = mealPlanService.fetchRecipesInformation(recipeIds);
            model.addAttribute("searchTerm", "Recipe results for '" + searchTerm + "'");

            model.addAttribute("recipes", recipes);
            return "recipes";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

    }

    @PostMapping("/saveMeal")
    public ModelAndView saveMealTest(@RequestParam(value="recipeId", required = false, defaultValue="") int  recipeId, @RequestParam(value="mealId", required = false, defaultValue="") String mealId, @RequestParam(value="recipeName", required = false, defaultValue="") String recipeName,Model model){
        ModelAndView modelAndView = new ModelAndView();
        MealItem mealItem = new MealItem();
        mealItem.setMealId(Integer.parseInt(mealId));
        mealItem.setRecipeId(recipeId);
        mealItem.setRecipeName(recipeName);
        DayOfWeek day = DayOfWeek.of(Integer.parseInt(mealId.substring(0,1)));
        mealItem.setDayOfWeek(day);
        try{
            mealPlanService.save(mealItem);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            model.addAttribute("start", mealItem);
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.setViewName("error");

        }
        read(model);
        return modelAndView;
    }


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
        log.error("recipeNameAutocomplete :" + e);
        return new ArrayList<RecipeLabelValue>();
    }

    return allRecipeNames;

    }
    @GetMapping("/shoppingList")
    public String displayShoppingList (Model model) throws IOException{
        ShoppingList shoppingList= null;
        try {
            shoppingList =mealPlanService.fetchShoppingList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("shoppingList", shoppingList);
        return "shoppingList";
    }

    @GetMapping("/home")
    public String displayWeekyMeal (Model model) throws IOException{
        List <MealItem> mealItems= null;
        try {
            mealItems = mealPlanService.fetchAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("mealItems", mealItems);
        read(model);
        return "start";
    }

    @GetMapping(value = "/recipeById/{recipe.id }")
    public ModelAndView fetchRecipeById(@PathVariable("recipe.id ") String recipeId ) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("recipeDetails");
        List<Recipe> recipes = mealPlanService.fetchRecipesInformation(recipeId);
        modelAndView.addObject("recipes", recipes);
        return  modelAndView;

    }



}
