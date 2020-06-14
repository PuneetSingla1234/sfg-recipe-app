package guru.springframework.sfgrecipeapp.services;

import guru.springframework.sfgrecipeapp.commands.RecipeCommand;
import guru.springframework.sfgrecipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.sfgrecipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.sfgrecipeapp.domain.Recipe;
import guru.springframework.sfgrecipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
@RunWith(SpringRunner.class)
@SpringBootTest
class RecipeServiceImplIT {
    @Autowired
    RecipeService recipeService;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;
    final String NEW_DESCRIPTION="New Description";
    @BeforeEach
    void setUp() {

    }
    @Transactional
    @Test
    void testSaveOfDescription(){
        Set<Recipe> recipeSet=recipeService.getRecipes();
        if(recipeSet==null||recipeSet.size()==0)
            return;
        Recipe testRecipe=recipeSet.iterator().next();
        RecipeCommand testRecipeCommand=recipeToRecipeCommand.convert(testRecipe);

        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand=recipeService.saveRecipeCommand(testRecipeCommand);
        assertEquals(NEW_DESCRIPTION,savedRecipeCommand.getDescription());
        assertEquals(testRecipeCommand.getId(),savedRecipeCommand.getId());
        System.out.println("Categories:"+testRecipeCommand.getCategories().size());
        assertEquals(testRecipeCommand.getCategories().size(),savedRecipeCommand.getCategories().size());
        assertEquals(testRecipeCommand.getIngredients().size(),savedRecipeCommand.getIngredients().size());
    }

}