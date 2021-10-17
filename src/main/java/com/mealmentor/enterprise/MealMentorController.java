package com.mealmentor.enterprise;

import com.mealmentor.enterprise.dto.MealItem;
import com.mealmentor.enterprise.service.IMealPlanService;
import jdk.jshell.SourceCodeAnalysis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.mealmentor.enterprise.dto.Recipe;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MealMentorController {

    Logger log = LoggerFactory.getLogger(this.getClass());

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
            log.error("unable to save item", e);
            e.printStackTrace();
            throw(e);
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
        model.addAttribute("Recipe", new Recipe());
        return "start";
    }


    @RequestMapping(value = "/searchRecipe",method = RequestMethod.GET)
    @ResponseBody
    public String searchReceipe(@RequestParam(value="searchTerm", required = false, defaultValue="") String searchTerm) {
        String term = searchTerm+"";
        return "recipes";
    }

    @GetMapping("/recipeNameAutocomplete")
    @ResponseBody
    public List<String> recipeNameAutocomplete(@RequestParam(value="term", required = false, defaultValue="") String term) {

        List <String> suggestions = new ArrayList<>();
        suggestions.add("Sausage and Shrimp");
        suggestions.add("Beef and Barley Soup");
        suggestions.add("Chiken");
        suggestions.add("Fried Rice");
        return suggestions;

    }

}
