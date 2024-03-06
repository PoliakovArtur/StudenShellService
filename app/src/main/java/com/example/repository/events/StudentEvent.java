package com.example.repository.events;

import org.springframework.context.ApplicationEvent;

public class StudentEvent extends ApplicationEvent {
    public StudentEvent(Object source) {
        super(source);
    }
}
