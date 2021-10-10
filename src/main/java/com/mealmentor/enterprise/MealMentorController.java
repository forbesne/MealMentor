package com.mealmentor.enterprise;

import com.mealmentor.enterprise.dto.MealItem;
import com.mealmentor.enterprise.service.IMealPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public MealItem createMealItem(@RequestBody MealItem mealItem)  {
        MealItem newMealItem = null;
        try{
            newMealItem = mealPlanService.save(mealItem);
        } catch (Exception e) {
            //TODO ADD LOGGING

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


    @RequestMapping (value = "/searchRecipe")
    public String searchRecipe (Recipe Recipe)
    {
        Recipe.setName("Chicken Burger");
        return "start";
    }

}
