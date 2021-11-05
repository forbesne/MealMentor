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
    public List<MealItem> fetchAllMealItems() {

        return mealPlanService.fetchAll();
    }

    @GetMapping("/mealItem/{id}/")
    public ResponseEntity fetchMealItemById(@PathVariable("id") String id) {
        try {
            MealItem foundMealItem = mealPlanService.fetchById(Integer.parseInt(id));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity(foundMealItem, headers, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value = "/mealItem", consumes = "application/json", produces = "application/json")
    public MealItem createMealItem(@RequestBody MealItem mealItem) throws Exception {

        MealItem newMealItem;
        try {
            newMealItem = mealPlanService.save(mealItem);
        } catch (Exception e) {
            //TODO ADD LOGGING
            throw new Exception("tried to access methods in business logic but failed");
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String read(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "start";
    }


    @GetMapping(value = "/searchRecipe")
    @ResponseBody
    public String searchReceipe(@RequestParam(value = "searchTerm", required = false, defaultValue = "None") String searchTerm, Model model) throws IOException {
        try {
            List<Recipe> recipes = mealPlanService.fetchRecipes(searchTerm);
            model.addAttribute("recipes", recipes);
            return "recipes";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

    }

    @RequestMapping("/saveMeal")
    public String saveMeal(MealItem mealItem) {
        try {
            mealPlanService.save(mealItem);
        } catch (Exception e) {
            e.printStackTrace();
            return "start";

        }
        return "start";
    }

    @GetMapping("/recipeNameAutocomplete")
    @ResponseBody
    public List<RecipeLabelValue> recipeNameAutocomplete(@RequestParam(value = "term", required = false, defaultValue = "") String term) {

        List<RecipeLabelValue> allRecipeNames = new ArrayList<>();

        try {
            List<Recipe> recipes = mealPlanService.fetchRecipes(term);
            for (Recipe recipe : recipes) {
                RecipeLabelValue recipeLabelValue = new RecipeLabelValue();
                recipeLabelValue.setLabel(recipe.getName());
                recipeLabelValue.setValue(recipe.getId());
                allRecipeNames.add(recipeLabelValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return allRecipeNames;

    }

}
