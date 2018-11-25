package com.bgh.myopeninvoice.api.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        OffsetDateTime offsetDateTime = OffsetDateTime.parse("2018-11-24T23:08:07.148Z", timeFormatter);
        return Date.from(Instant.from(offsetDateTime));
    }

}
