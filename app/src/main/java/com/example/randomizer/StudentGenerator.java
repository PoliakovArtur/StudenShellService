package com.example.randomizer;

import com.example.model.Student;
import com.example.repository.jpa.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Component
@ConditionalOnProperty(value = "student-shell-service.enable-beans.student-generator", havingValue = "true")
public class StudentGenerator {
    private final StudentRepository repository;

    @EventListener(ApplicationStartedEvent.class)
    public void generateStudents() throws IOException {
        List<String> firstNames = firstNames();
        List<String> lastNames = lastNames();
        Random random = new Random();
        int count = random.nextInt(20, 100),
                firstNamesCount = firstNames.size(),
                lastNamesCount = lastNames.size();

        for (int i = 0; i < count; i++) {
            String firstName = firstNames.get(random.nextInt(firstNamesCount));
            String lastName = lastNames.get(random.nextInt(lastNamesCount));
            int age = random.nextInt(20, 100);
            repository.save(new Student(firstName, lastName, age));
        }
    }

    private List<String> firstNames() throws IOException {
        return Jsoup.connect("https://www.kp.ru/family/deti/znachenie-imeni/muzhskie-imena/")
                .get()
                .select("ul[data-c9]")
                .stream()
                .flatMap(e -> e.getElementsByTag("li").stream())
                .map(Element::text)
                .toList();
    }

    private List<String> lastNames() throws IOException {
        return Jsoup.connect("http://www.rusinkg.ru/russkij-yazyk/article/42-russkij-yazyk-v-mire/6164-samye-rasprostranennye-russkie-familii-rejting-500")
                .get()
                .getElementsByTag("p")
                .stream()
                .map(Element::text)
                .filter(e -> e.matches("(\\d+ \\p{IsCyrillic}+ ?)+"))
                .flatMap(e -> Arrays.stream(e.split("\\d+\\s")))
                .filter(e -> !e.isBlank())
                .toList();
    }
}
