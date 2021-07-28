package com.acme.browsereventsapi;

import com.acme.browsereventsapi.model.Event;
import com.acme.browsereventsapi.repository.EventRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class BrowserEventsApiApplication {


    public static void main(String[] args) {
        SpringApplication.run(BrowserEventsApiApplication.class, args);
    }

    @Bean
    CommandLineRunner init(EventRepository eventRepository) {
        return args -> {
            Faker feku = new Faker();
            List<Event> events = new ArrayList<>();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

            for (int i = 0; i < 400; i++) {
                events.add(new Event(feku.color().name(), feku.date().between(formatter.parse("01-01-2020"), formatter.parse("31-12-2020"))));
            }
            eventRepository.saveAll(events);
        };
    }

}
