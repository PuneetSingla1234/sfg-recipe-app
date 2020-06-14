package guru.springframework.sfgrecipeapp.controllers;

import guru.springframework.sfgrecipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngredientController {
    private final RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @RequestMapping("/recipe/{recipeId}/show/ingredient")
    public String listIngredients(@PathVariable String recipeId, Model model){
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(recipeId)));
        return "ingredient/list";
    }

}
