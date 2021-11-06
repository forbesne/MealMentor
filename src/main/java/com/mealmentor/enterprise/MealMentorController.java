package com.mealmentor.enterprise;

import com.mealmentor.enterprise.dto.Error;
import com.mealmentor.enterprise.dto.MealItem;
import com.mealmentor.enterprise.dto.RecipeLabelValue;
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
import java.util.ArrayList;
import java.util.List;

import com.mealmentor.enterprise.dto.Recipe;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MealMentorController {


    Logger logger = LoggerFactory.getLogger(MealMentorController.class);

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

        try{
            MealItem foundMealItem = mealPlanService.fetchById(Integer.parseInt(id));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity(foundMealItem, headers, HttpStatus.OK);
        }catch (Exception e){
            logger.error("problem in fetchMealItemById.");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Create a new meal item
     * @param mealItem meal Item object to be created
     * @return newly created meal item
     */
    @PostMapping(value="/mealItem", consumes="application/json", produces="application/json")
    public MealItem createMealItem(@RequestBody MealItem mealItem){

        MealItem newMealItem = null;
        try{
            newMealItem = mealPlanService.save(mealItem);
        } catch (Exception e) {
            logger.error("problem in createMealItem.");
        }
        return newMealItem;
    }


    /**
     * Delete a meal item by a unique Id
     * @param id Unique Id
     * @return HTTP status code 200 if successful, 500 if not successful
     */
    @DeleteMapping("/mealItem/{id}/")
    public ResponseEntity deleteMealItem(@PathVariable("id") String id) {
        try {
            mealPlanService.delete(Integer.parseInt(id));
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("problem in deleteMealItem.");
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
    public ModelAndView searchRecipe(@RequestParam(value="searchTerm", required=false, defaultValue="None")  String searchTerm, Model model) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<Recipe> recipes= mealPlanService.fetchRecipes(searchTerm);
            modelAndView.addObject("recipes", recipes);
            modelAndView.setViewName("recipes");
            return modelAndView;

        } catch (IOException e) {
            logger.error("problem in searchRecipe.");
            modelAndView = createErrorModelAndView("There was a problem with the search",
                    "please restart the application and let the admins know if the problem persists");
            modelAndView.setViewName("error");
            return modelAndView;
        }

    }


    /**
     * Saves a meal item
     * @param mealItem meal item
     * @return Return user back to the start page
     */
    @RequestMapping("/saveMeal")
    public ModelAndView saveMeal(MealItem mealItem){

        ModelAndView modelAndView = new ModelAndView();

        try{
            mealPlanService.save(mealItem);
            modelAndView.setViewName("start");

        } catch (Exception e) {
            logger.error("problem in saveMeal.");

            modelAndView =  createErrorModelAndView("There was a problem saving the meal",
                    "Please confirm the inputs and try again.");
            return modelAndView;

        }
        return modelAndView;
    }


    /**
     * Autocomplete method for jquery autocomplete
     * @param term term to use for autocomplete
     * @return  possible recipe list based on the term
     */
    @GetMapping("/recipeNameAutocomplete")
    @ResponseBody
    public List<RecipeLabelValue> recipeNameAutocomplete(@RequestParam(value="term", required = false, defaultValue="") String term) {

        List <RecipeLabelValue> allRecipeNames= new ArrayList<>();

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
        logger.error("problem in recipeNameAutocomplete.");
        return new ArrayList<RecipeLabelValue>();
    }

    return allRecipeNames;

    }


    /**
     * Receives error details and prepares a ModelAndView object for it.
     * @param errorTitle Error Title
     * @param errorDetails Error Details
     * @return modelAndView - a populated ModelAndView object
     */
    private ModelAndView createErrorModelAndView (String errorTitle, String errorDetails){

        ModelAndView modelAndView = new ModelAndView();

        Error error = new Error();
        error.setTitle(errorTitle);
        error.setDetails(errorDetails);
        modelAndView.setViewName("error");
        modelAndView.addObject("error", error);

        return modelAndView;
    }

}
