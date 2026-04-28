package com.user.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Component
public class MultiDateDeserializer extends JsonDeserializer<Date> {

    private static final List<SimpleDateFormat> FORMATTERS = List.of(
            new SimpleDateFormat("dd-MM-yyyy"),
            new SimpleDateFormat("MM-dd-yyyy"),
            new SimpleDateFormat("yyyy-MM-dd"),
            new SimpleDateFormat("yyyy/MM/dd"),
            new SimpleDateFormat("dd/MM/yyyy"),
            new SimpleDateFormat("dd-MM-yyyy hh:mm a"),
            new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
    );

    static {
        // Strict parsing (no auto-corrections like 32-13-2024)
        FORMATTERS.forEach(format -> format.setLenient(false));
    }
    @Override
    public Date deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {

        String date = parser.getText().trim();

        for (SimpleDateFormat formatter : FORMATTERS) {
            try {
                return formatter.parse(date);
            } catch (Exception ignored) {}
        }

        throw new IllegalArgumentException(
                "Invalid date format. Supported formats: dd-MM-yyyy, MM-dd-yyyy, yyyy-MM-dd, yyyy/MM/dd, dd/MM/yyyy"
        );
    }
}
