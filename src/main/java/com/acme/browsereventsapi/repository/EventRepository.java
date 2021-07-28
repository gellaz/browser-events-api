package com.acme.browsereventsapi.repository;

import com.acme.browsereventsapi.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
    Slice<Event> findAllByType(String type);
    Slice<Event> findAllByType(String type, Pageable pageable);
}
