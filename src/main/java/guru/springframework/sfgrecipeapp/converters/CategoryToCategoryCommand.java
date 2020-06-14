package guru.springframework.sfgrecipeapp.converters;

import com.sun.istack.Nullable;
import guru.springframework.sfgrecipeapp.commands.CategoryCommand;
import guru.springframework.sfgrecipeapp.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {
    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        final CategoryCommand categoryCommand=new CategoryCommand();
        categoryCommand.setId(source.getId());
        categoryCommand.setDescription(source.getDescription());
        return categoryCommand;
    }
}
