package com.example.repository.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface StudentRepositoryAdapter {
    String findAll() throws JsonProcessingException;
    String findById(Long id);
    String save(String firstName, String lastName, String age);
    String deleteById(Long id);
}
