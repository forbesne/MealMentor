package com.mealmentor.enterprise;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MealMentorController {

    @RequestMapping("/")
    public String index() {
        return "start";
    }
}
