package com.acme.browsereventsapi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@Document(collation = "events")
public class Event {
    @Id
    @ApiModelProperty(notes = "The database generated event ID")
    private String id;
    @ApiModelProperty(notes = "The type of the event", required = true)
    private String type;
    @ApiModelProperty(notes = "The timestamp of the event")
    private Date timestamp;

    public Event(String type, Date timestamp) {
        this.type = type;
        this.timestamp = timestamp;
    }
}
