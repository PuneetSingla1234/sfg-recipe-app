package guru.springframework.sfgrecipeapp.converters;

import com.sun.istack.Nullable;
import guru.springframework.sfgrecipeapp.commands.RecipeCommand;
import guru.springframework.sfgrecipeapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private final CategoryCommandToCategory categoryConverter;
    private final NotesCommandToNotes notesConverter;
    private final IngredientCommandToIngredient ingredientConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConverter, NotesCommandToNotes notesConverter,
                                 IngredientCommandToIngredient ingredientConverter) {
        this.categoryConverter = categoryConverter;
        this.notesConverter = notesConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {

        final Recipe recipe=new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setDirections(source.getDirections());
        recipe.setServing(source.getServing());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setUrl(source.getUrl());
        recipe.setSource(source.getSource());
        recipe.setNotes(notesConverter.convert(source.getNotes()));
        if(source.getCategories()!=null && source.getCategories().size()!=0)
            source.getCategories().forEach(categoryCommand ->
                    recipe.getCategories().add(categoryConverter.convert(categoryCommand)));
        if (source.getIngredients()!=null && source.getIngredients().size()!=0)
            source.getIngredients().forEach(ingredientCommand ->
                    recipe.getIngredients().add(ingredientConverter.convert(ingredientCommand)));
        return recipe;
    }
}
