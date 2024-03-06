package com.example.repository.listener;

import com.example.repository.events.StudentEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Queue;

@Component
public class ApplicationStudentEventListener {

    private Queue<StudentEvent> queue;


    @TransactionalEventListener
    public void listenStudentEvent(StudentEvent event) {
        queue.add(event);
    }

    public String pollEvent() {

    }
}
