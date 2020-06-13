package guru.springframework.sfgrecipeapp.services;

import guru.springframework.sfgrecipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe getRecipeById(Long id);
}
