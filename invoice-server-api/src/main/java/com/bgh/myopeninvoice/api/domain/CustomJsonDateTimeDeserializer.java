package com.bgh.myopeninvoice.api.domain;

import com.bgh.myopeninvoice.common.util.Constants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

public class CustomJsonDateTimeDeserializer extends JsonDeserializer<Date> {

  @Override
  public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {

    if (jsonParser.getText() == null) {
      return null;
    } else if (jsonParser.getText().length() == 10) {
      LocalDate localDate = LocalDate.parse(jsonParser.getText(), Constants.DATE_FORMATTER);
      return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    } else {
      OffsetDateTime offsetDateTime =
          OffsetDateTime.parse(jsonParser.getText(), Constants.DATE_TIME_FORMATTER);
      return Date.from(Instant.from(offsetDateTime));
    }
  }
}
