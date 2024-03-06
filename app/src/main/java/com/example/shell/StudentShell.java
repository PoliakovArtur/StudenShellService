package com.example.shell;

import com.example.events.RepositoryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import static com.example.events.RepositoryEvent.RepositoryMethodEnum.DELETE_ALL;
import static com.example.events.RepositoryEvent.RepositoryMethodEnum.DELETE_BY_ID;
import static com.example.events.RepositoryEvent.RepositoryMethodEnum.FIND_ALL;
import static com.example.events.RepositoryEvent.RepositoryMethodEnum.FIND_BY_ID;
import static com.example.events.RepositoryEvent.RepositoryMethodEnum.SAVE;

@ShellComponent
@RequiredArgsConstructor
public class StudentShell {
    private final ApplicationEventPublisher publisher;

    @ShellMethod(key = "find all", value = "получить список всех студентов")
    public void findAll() {
        publisher.publishEvent(new RepositoryEvent(FIND_ALL));
    }

    @ShellMethod(key = "find by id", value = "получить студента по id")
    public void findById(String id) {
        publisher.publishEvent(new RepositoryEvent(FIND_BY_ID, id));
    }

    @ShellMethod(key = "save", value = "сохранить студента")
    public void save(
            @ShellOption(value = "fn", help = "Имя") String firstName,
            @ShellOption(value = "ln", help = "Фамилия") String lastName,
            @ShellOption(value = "a", help = "Возраст") String age) {
        publisher.publishEvent(new RepositoryEvent(SAVE, firstName, lastName, age));
    }

    @ShellMethod(key = "delete by id", value = "удалить студента по id")
    public void deleteById(String id) {
        publisher.publishEvent(new RepositoryEvent(DELETE_BY_ID, id));
    }

    @ShellMethod(key = "delete all", value = "удалить всех студентов")
    public void deleteAll() {
        publisher.publishEvent(new RepositoryEvent(DELETE_ALL));
    }
}
