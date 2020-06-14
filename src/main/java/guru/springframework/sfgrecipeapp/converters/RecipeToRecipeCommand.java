package guru.springframework.sfgrecipeapp.converters;

import com.sun.istack.Nullable;
import guru.springframework.sfgrecipeapp.commands.RecipeCommand;
import guru.springframework.sfgrecipeapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
    private final CategoryToCategoryCommand categoryConverter;
    private final NotesToNotesCommand notesConverter;
    private final IngredientToIngredientCommand ingredientConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter, NotesToNotesCommand notesConverter,
                                 IngredientToIngredientCommand ingredientConverter) {
        this.categoryConverter = categoryConverter;
        this.notesConverter = notesConverter;
        this.ingredientConverter = ingredientConverter;
    }


    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {

        final RecipeCommand recipeCommand=new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setServing(source.getServing());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setNotes(notesConverter.convert(source.getNotes()));
        if(source.getCategories()!=null && source.getCategories().size()!=0)
            source.getCategories().forEach(category ->
                    recipeCommand.getCategories().add(categoryConverter.convert(category)));
        if (source.getIngredients()!=null && source.getIngredients().size()!=0)
            source.getIngredients().forEach(ingredient ->
                    recipeCommand.getIngredients().add(ingredientConverter.convert(ingredient)));
        return recipeCommand;
    }
}
