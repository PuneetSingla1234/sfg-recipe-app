package guru.springframework.sfgrecipeapp.controllers;

import guru.springframework.sfgrecipeapp.domain.Recipe;
import guru.springframework.sfgrecipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
public class IndexController {
    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","index","index.html"})
    public String getIndexPage(Model model){
        Set<Recipe> recipes=recipeService.getRecipes();
        model.addAttribute("recipes",recipes);
        return "index";
    }
}
