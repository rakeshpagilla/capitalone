package com.rakesh.domain;

import java.io.IOException;
import java.util.Locale;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DateTimeDeserializer extends JsonDeserializer<DateTime> {

	 public static final String PATTERN ="yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	 public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern(PATTERN).withLocale(Locale.US).withZoneUTC();
	    
	@Override
    public DateTime deserialize(final JsonParser jsonParser, final DeserializationContext context) throws IOException {

        final String dateTime = jsonParser.getText();
        if (dateTime == null ) {
            return null;
        }
        try {
        	return FORMATTER.parseDateTime(dateTime);
        } catch (Exception e) {
            return null;
        }
    }
}
