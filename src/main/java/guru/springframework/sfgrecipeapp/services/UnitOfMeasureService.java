package guru.springframework.sfgrecipeapp.services;

import guru.springframework.sfgrecipeapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    public Set<UnitOfMeasureCommand> listUoms();
}
