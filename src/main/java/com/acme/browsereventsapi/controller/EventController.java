package com.acme.browsereventsapi.controller;

import com.acme.browsereventsapi.model.Event;
import com.acme.browsereventsapi.repository.EventRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EventController {
    private final EventRepository eventRepository;

    @Autowired
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Get all events saved in the database
     *
     * @return the list of all the events
     */
    @GetMapping("/events")
    @ApiOperation(value = "getAllEvents", notes = "Get all the events saved on the database", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All the events successfully retrieved"),
            @ApiResponse(code = 204, message = "There is no event"),
            @ApiResponse(code = 500, message = "Error retrieving all the events"),
    })
    public ResponseEntity<List<Event>> getAllEvents() {
        try {
            List<Event> events = new ArrayList<>();

            eventRepository.findAll().addAll(events);

            if (events.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get the event with the given id saved on the database, if present
     *
     * @param id of the event you are querying for
     * @return the event with the given id, if present
     */
    @GetMapping("/events/{id}")
    @ApiOperation(value = "getEventById", notes = "Get the event with the given id saved on the database, if present", response = Event.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Event with the given ID successfully retrieved"),
            @ApiResponse(code = 404, message = "Event with the given ID not found"),
    })
    public ResponseEntity<Event> getEventById(@PathVariable("id") String id) {
        Optional<Event> eventData = eventRepository.findById(id);

        if (eventData.isPresent())
            return new ResponseEntity<>(eventData.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Get all the events with the given type saved on the database, if any
     *
     * @param type of events you are querying for
     * @return the events with the given type saved on the database, if any
     */
    @GetMapping("/events/{type}")
    @ApiOperation(value = "getEventsByType", notes = "Get all the events with the given type saved on the database, if any", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Events with the given type successfully retrieved"),
            @ApiResponse(code = 204, message = "There are no events of the given type"),
            @ApiResponse(code = 500, message = "Error retrieving the events with the given type"),
    })
    public ResponseEntity<List<Event>> getEventsByType(@PathVariable("type") String type) {
        try {
            List<Event> events = eventRepository.findEventByType(type);

            if (events.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a new event and saves it to the database
     *
     * @param event to create
     * @return the event saved on the database
     */
    @PostMapping("/events")
    @ApiOperation(value = "createEvent", notes = "Creates a new event and saves it to the database", response = Event.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Event successfully created"),
            @ApiResponse(code = 500, message = "Error creating event"),
    })
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        try {
            Event _event = eventRepository.save(new Event(event.getType(), event.getTimestamp()));

            return new ResponseEntity<>(_event, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the event with the id passed as input to the database, if it exists
     *
     * @param id    of the event
     * @param event to update
     * @return the updated event
     */
    @PutMapping("/events/{id}")
    @ApiOperation(value = "updateEvent", notes = "Updates the event with the id passed as input to the database, if it exists", response = Event.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Event with the given ID successfully updated"),
            @ApiResponse(code = 404, message = "Event with the given ID not found"),
    })
    public ResponseEntity<Event> updateEvent(@PathVariable("id") String id, @RequestBody Event event) {
        Optional<Event> eventData = eventRepository.findById(id);

        if (eventData.isPresent()) {
            Event _event = eventData.get();
            _event.setType(event.getType());
            _event.setTimestamp(event.getTimestamp());
            return new ResponseEntity<>(eventRepository.save(_event), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Removes the event with the id passed as input from the database
     *
     * @param id of the event
     * @return HTTP response with status NO_CONTENT (204) if the element is successfully deleted or INTERNAL_SERVER_ERROR (500)
     */
    @DeleteMapping("/events/{id}")
    @ApiOperation(value = "deleteEvent", notes = "Removes the event with the id passed as input from the database", response = HttpStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Event with the given ID successfully removed"),
            @ApiResponse(code = 500, message = "Error deleting the event with the given ID"),
    })
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable("id") String id) {
        try {
            eventRepository.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Remove all the events saved on the database
     *
     * @return HTTP response with status NO_CONTENT (204) if the element is successfully deleted or INTERNAL_SERVER_ERROR (500)
     */
    @DeleteMapping("/events")
    @ApiOperation(value = "deleteAllEvents", notes = "Remove all the events saved on the database", response = HttpStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "All events successfully removed"),
            @ApiResponse(code = 500, message = "Error deleting all the events"),
    })
    public ResponseEntity<HttpStatus> deleteAllEvents() {
        try {
            eventRepository.deleteAll();

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
