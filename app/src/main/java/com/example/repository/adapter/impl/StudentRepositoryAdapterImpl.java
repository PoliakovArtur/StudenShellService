package com.example.repository.adapter.impl;

import com.example.events.ErrorEvent;
import com.example.events.MessageEvent;
import com.example.model.Student;
import com.example.repository.adapter.StudentRepositoryAdapter;
import com.example.repository.jpa.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.joining;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryAdapterImpl implements StudentRepositoryAdapter {
    private final StudentRepository repository;
    private final ApplicationEventPublisher publisher;

    @Transactional(readOnly = true)
    @Override
    public void findAll() {
        List<Student> students = repository.findAll();
        publisher.publishEvent(students.isEmpty()
                ? new ErrorEvent("На данный момент нет сохраненных студентов")
                : new MessageEvent(students.stream()
                .map(Student::toString)
                .collect(joining(System.lineSeparator()))));
    }

    @Transactional(readOnly = true)
    @Override
    public void findById(String id) {
        try {
            Optional<Student> opt = repository.findById(parseLong(id));
            publisher.publishEvent(opt.isEmpty()
                    ? new ErrorEvent(format("Студент с id:{0} не найден", id))
                    : new MessageEvent(opt.get().toString()));
        } catch (NumberFormatException ex) {
            publisher.publishEvent(new ErrorEvent(format("Неверное значение id: {0}", id)));
        }
    }

    @Transactional
    @Override
    public void save(String firstName, String lastName, String age) {
        if(age.matches("[1-9]\\d?")) {
            Student saved = repository.save(new Student(firstName, lastName, parseInt(age)));
            publisher.publishEvent(new MessageEvent(format("Студент с id:{0} сохранен", saved.getId())));
        } else {
            publisher.publishEvent(new ErrorEvent(format("Неверное значение возраста: {0}", age)));
        }
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        try {
            Long parsedId = parseLong(id);
            Optional<Student> opt = repository.findById(parsedId);
            if (opt.isEmpty()) {
                publisher.publishEvent(new ErrorEvent(format("Студент с id:{0} не найден", id)));
            } else {
                repository.deleteById(parsedId);
                publisher.publishEvent(new MessageEvent(format("Студент с id:{0} успешно удален", id)));
            }
        } catch (NumberFormatException ex) {
            publisher.publishEvent(new ErrorEvent(format("Неверное значение id: {0}", id)));
        }
    }

    @Transactional
    @Override
    public void clear() {
        repository.deleteAll();
        publisher.publishEvent(new MessageEvent("Список студентов очищен"));
    }
}
