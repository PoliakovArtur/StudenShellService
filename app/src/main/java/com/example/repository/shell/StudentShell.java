package com.example.repository.shell;

import com.example.repository.adapter.StudentRepositoryAdapter;
import com.example.repository.jpa.StudentRepository;
import com.example.repository.listener.ApplicationStudentEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import static java.util.stream.Collectors.joining;

@ShellComponent
@RequiredArgsConstructor
public class StudentShell {
    private final ApplicationStudentEventListener listener;


    @ShellMethod(key = "students ls")
    public String findAll() {
        return listener.pollEvent();
    }


    @ShellMethod(key = "students")
    public void findById(Long id) {

    }


    @ShellMethod(key = "students save ")
    public void save(String firstName, String lastName, String age) {

    }


    @ShellMethod
    public void deleteById(Long id) {

    }


}
