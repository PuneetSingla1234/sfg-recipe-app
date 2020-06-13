package guru.springframework.sfgrecipeapp.controllers;

import guru.springframework.sfgrecipeapp.domain.Recipe;
import guru.springframework.sfgrecipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @RequestMapping("/recipe/show/{id}")
    public String showById(@PathVariable String id, Model model){
        try{
            Long longId=Long.valueOf(id);
        }
        catch (Exception e){
            throw new RuntimeException("Invalid Format");
        }
        Recipe recipe=recipeService.getRecipeById(Long.valueOf(id));
        if(recipe==null)
            throw new RuntimeException("Invalid ID entered");
        model.addAttribute("recipe",recipe);
        return "recipe/show";
    }
}
