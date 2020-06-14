package guru.springframework.sfgrecipeapp.converters;

import com.sun.istack.Nullable;
import guru.springframework.sfgrecipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.sfgrecipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {
    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if(source==null)
            return null;
        final UnitOfMeasure uom=new UnitOfMeasure();
        uom.setId(source.getId());
        uom.setDescription(source.getDescription());
        return uom;
    }
}
