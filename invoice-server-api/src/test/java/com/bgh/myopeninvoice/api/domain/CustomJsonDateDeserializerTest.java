package com.bgh.myopeninvoice.api.domain;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.Assert.*;

public class CustomJsonDateDeserializerTest {

    String test1 = "2018-11-25T22:12:09.433-0500";
    String test2 = "2018-11-25T22:12:09.433Z";

    @Test
    public void deserialize() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd'T'HH:mm:ss.SSSZ][yyyy-MM-dd'T'HH:mm:ss.SSSX]");

        System.out.println(OffsetDateTime.parse(test1, formatter));
        System.out.println(OffsetDateTime.parse(test2, formatter));
//        System.out.println(OffsetDateTime.parse(test1));

//        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//        OffsetDateTime offsetDateTime = OffsetDateTime.parse(test1, timeFormatter);
//        System.out.println(Date.from(Instant.from(offsetDateTime)));
//
//        timeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
//        offsetDateTime = OffsetDateTime.parse(test2, timeFormatter);
//        System.out.println(Date.from(Instant.from(offsetDateTime)));
    }

}