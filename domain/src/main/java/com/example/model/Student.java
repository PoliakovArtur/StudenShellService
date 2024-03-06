package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.text.MessageFormat.format;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;

    private String lastName;

    private Integer age;

    public Student(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        return format("Студент: id {0}, имя {1}, фамилия {2}, возраст {3}", id, firstName, lastName, age);
    }
}
