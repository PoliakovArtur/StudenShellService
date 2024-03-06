package com.example.service;

import com.example.events.RepositoryEvent;
import com.example.repository.adapter.StudentRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepositoryAdapter repositoryAdapter;

    @EventListener
    public void doRepoAction(RepositoryEvent event) {
        switch (event.method()) {
            case SAVE -> {
                String[] data = event.data();
                repositoryAdapter.save(data[0], data[1], data[2]);
            }
            case FIND_ALL -> repositoryAdapter.findAll();
            case FIND_BY_ID -> repositoryAdapter.findById(event.data()[0]);
            case DELETE_BY_ID -> repositoryAdapter.deleteById(event.data()[0]);
            case DELETE_ALL -> repositoryAdapter.clear();
        }
    }
}
