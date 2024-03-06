package com.example.repository.adapter.impl;

import com.example.model.Student;
import com.example.repository.adapter.StudentRepositoryAdapter;
import com.example.repository.jpa.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.joining;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryAdapterImpl implements StudentRepositoryAdapter {

    private final StudentRepository repository;

    @Transactional(readOnly = true)
    @Override
    public String findAll() {
        List<Student> students = repository.findAll();
        return students.isEmpty()
                ? "На данный момент нет сохраненных студентов"
                : students.stream()
                .map(Student::toString)
                .collect(joining(System.lineSeparator()));
    }

    @Transactional(readOnly = true)
    @Override
    public String findById(Long id) {
        Optional<Student> opt = repository.findById(id);
        return opt.isEmpty()
                ? format("Студент с id:{0} не найден", id)
                : opt.get().toString();
    }

    @Transactional
    @Override
    public String save(String firstName, String lastName, String age) {
        if (!age.matches("[1-9]\\d?")) return "Неверно введен возраст";
        Student saved = repository.save(Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(Integer.parseInt(age))
                .build());
        return format("Студент с id:{0} сохранен", saved.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public String deleteById(Long id) {
        Optional<Student> opt = repository.findById(id);
        return format(opt.isEmpty()
                ? "Студент с id:{0} не найден" : "Студент с id:{0} успешно удален", id);
    }
}
