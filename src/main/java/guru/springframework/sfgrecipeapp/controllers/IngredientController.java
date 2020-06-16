package guru.springframework.sfgrecipeapp.controllers;

import guru.springframework.sfgrecipeapp.commands.IngredientCommand;
import guru.springframework.sfgrecipeapp.commands.RecipeCommand;
import guru.springframework.sfgrecipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.sfgrecipeapp.services.IngredientService;
import guru.springframework.sfgrecipeapp.services.RecipeService;
import guru.springframework.sfgrecipeapp.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;
    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }
    @RequestMapping("/recipe/{recipeId}/ingredient/list")
    public String listIngredients(@PathVariable String recipeId, Model model){
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(recipeId)));
        return "ingredient/list";
    }
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String viewIngredientDetails(@PathVariable String recipeId,@PathVariable String ingredientId,
                                        Model model){
        IngredientCommand command=ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),
                Long.valueOf(ingredientId));
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(recipeId)));
        model.addAttribute("ingredient",command);
        return "ingredient/show";
    }
    @RequestMapping("/recipe/{recipeId}/ingredient/new")
    public String newRecipeIngredient(@PathVariable String recipeId, Model model){
        RecipeCommand recipeCommand=recipeService.findCommandById(Long.valueOf(recipeId));
        Set<UnitOfMeasureCommand> uomsList=unitOfMeasureService.listUoms();

        model.addAttribute("recipe",recipeCommand);
        IngredientCommand command=new IngredientCommand();
        command.setRecipeId(Long.valueOf(recipeId));
        UnitOfMeasureCommand uomCommand=new UnitOfMeasureCommand();
        command.setUom(uomCommand);
        model.addAttribute("ingredient",command);
        model.addAttribute("uomList",uomsList);
        return "ingredient/ingredientform";

    }
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                               @PathVariable String ingredientId, Model model){
        RecipeCommand recipeCommand=recipeService.findCommandById(Long.valueOf(recipeId));
        IngredientCommand ingredientCommand=ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId)
        ,Long.valueOf(ingredientId));
        Set<UnitOfMeasureCommand> uomsList=unitOfMeasureService.listUoms();
        System.out.println("UOM Size:"+uomsList.size());
        model.addAttribute("recipe",recipeCommand);
        model.addAttribute("ingredient",ingredientCommand);
        model.addAttribute("uomList",uomsList);
        return "ingredient/ingredientform";
    }
    @PostMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@PathVariable String recipeId,@ModelAttribute IngredientCommand ingredientCommand){
        System.out.println("In the method");
        IngredientCommand savedCommand=ingredientService.saveIngredientCommand(ingredientCommand);
        return "redirect:/recipe/"+savedCommand.getRecipeId()+"/ingredient/"+savedCommand.getId()+"/show";
    }

    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId){
        ingredientService.deleteIngredient(Long.valueOf(recipeId),Long.valueOf(ingredientId));
        return "redirect:/recipe/"+recipeId+"/ingredient/list";
    }
}
