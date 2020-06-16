package guru.springframework.sfgrecipeapp.services;

import guru.springframework.sfgrecipeapp.commands.IngredientCommand;

public interface IngredientService {
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId,Long ingredientId);
    public IngredientCommand saveIngredientCommand(IngredientCommand command);
    public void deleteIngredient(Long recipeId,Long ingredientId);
}
