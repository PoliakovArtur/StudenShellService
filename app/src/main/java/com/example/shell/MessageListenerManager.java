package com.example.shell;

import com.example.events.ErrorEvent;
import com.example.events.MessageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

import static java.lang.System.err;
import static java.lang.System.out;

@RequiredArgsConstructor
@Component
public class MessageListenerManager {

    @TransactionalEventListener
    public void message(MessageEvent event) {
        out.printf("%s\n%s%n", LocalDateTime.now(), event.message());
    }

    @TransactionalEventListener
    public void error(ErrorEvent event) {
        err.printf("%s\n%s%n", LocalDateTime.now(), event.message());
    }
}
