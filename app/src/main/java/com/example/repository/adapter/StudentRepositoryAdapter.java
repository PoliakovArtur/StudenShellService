package com.example.repository.adapter;

import com.example.model.Student;

public interface StudentRepositoryAdapter {
    void findAll();
    void findById(String id);
    void save(String firstName, String lastName, String age);
    void deleteById(String id);
    void clear();
}
