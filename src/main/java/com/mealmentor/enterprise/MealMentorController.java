package com.mealmentor.enterprise;

import com.mealmentor.enterprise.dto.Recipe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MealMentorController {

    @RequestMapping("/")
    public String index() {
        return "start";
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String read(Model model){
        model.addAttribute("Recipe", new Recipe());
        return "start";
    }

}
