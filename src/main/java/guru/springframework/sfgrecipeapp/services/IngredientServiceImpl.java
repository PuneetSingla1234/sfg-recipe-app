package guru.springframework.sfgrecipeapp.services;

import guru.springframework.sfgrecipeapp.commands.IngredientCommand;
import guru.springframework.sfgrecipeapp.commands.RecipeCommand;
import guru.springframework.sfgrecipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.sfgrecipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.sfgrecipeapp.domain.Ingredient;
import guru.springframework.sfgrecipeapp.domain.Recipe;
import guru.springframework.sfgrecipeapp.repositories.RecipeRepository;
import guru.springframework.sfgrecipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService{
    private final RecipeService recipeService;
    private final UnitOfMeasureRepository uomRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    public IngredientServiceImpl(RecipeService recipeService, UnitOfMeasureRepository uomRepository, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeService = recipeService;
        this.uomRepository = uomRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        RecipeCommand recipeCommand=recipeService.findCommandById(recipeId);
        Optional<IngredientCommand> command=recipeCommand.getIngredients().stream()
                .filter(ingredientCommand -> ingredientCommand.getId().equals(ingredientId))
                .findFirst();
        return command.orElse(null);
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Recipe recipe=recipeService.getRecipeById(command.getRecipeId());
        if(recipe==null){
            return new IngredientCommand();
        }
        Optional<Ingredient> ingredientOptional=recipe.getIngredients()
                .stream().filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst();
        if(ingredientOptional.isPresent()){
            Ingredient ingredientFound=ingredientOptional.get();
            ingredientFound.setDescription(command.getDescription());
            ingredientFound.setUom(uomRepository.findById(command.getUom().getId()).orElse(null));
        }
        else{
            Ingredient ingredient=ingredientCommandToIngredient.convert(command);
            ingredient.setUom(uomRepository.findById(command.getUom().getId()).orElse(null));
            recipe.addIngredient(ingredient);
        }

        Recipe savedRecipe=recipeRepository.save(recipe);
        Optional<Ingredient> savedIngredient=recipeService.getRecipeById(command.getRecipeId()).getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst();
        if(savedIngredient.isPresent())
            return ingredientToIngredientCommand.convert(savedIngredient.get());
        Optional<Ingredient> matchedIngredient=recipeService.getRecipeById(command.getRecipeId()).getIngredients().stream()
                .filter(ingredient ->
                    ingredient.getAmount().equals(command.getAmount()) &&
                            ingredient.getDescription().equals(command.getDescription()) &&
                            ingredient.getUom().getId().equals(command.getUom().getId())
                ).findFirst();

        if(matchedIngredient.isPresent())
            return ingredientToIngredientCommand.convert(matchedIngredient.get());
        return new IngredientCommand();
    }

    @Override
    @Transactional
    public void deleteIngredient(Long recipeId, Long ingredientId) {
        System.out.println("Entered!!");
        Recipe recipe=recipeService.getRecipeById(recipeId);
        if(recipe==null)
            return;
        Optional<Ingredient> optionalIngredient=recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();
        if(!optionalIngredient.isPresent())
            return;
        System.out.println("Found!!");
        //System.out.println(recipe.getIngredients().size());

        recipe.getIngredients().remove(optionalIngredient.get());
        optionalIngredient.get().setRecipe(null);
        //System.out.println(recipe.getIngredients().size());
        Recipe savedRecipe = recipeRepository.save(recipe);
        //System.out.println(recipe.getIngredients().size());


    }


}
