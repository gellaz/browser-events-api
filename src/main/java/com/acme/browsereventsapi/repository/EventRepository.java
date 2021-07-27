package com.acme.browsereventsapi.repository;

import com.acme.browsereventsapi.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {
    public List<Event> findEventByType(String type);
}
