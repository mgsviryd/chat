package com.sviryd.chat.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.sql.Timestamp;
import java.time.*;

@Converter(autoApply = true)
public class LocalDateToTimestampConverter implements AttributeConverter<LocalDate, Timestamp> {
    private static final LocalTime TIME = LocalTime.of(0, 0);

    @Override
    public Timestamp convertToDatabaseColumn(LocalDate ld) {
        LocalDateTime ldt = LocalDateTime.of(ld, TIME);
        Instant instant = ldt.toInstant(ZoneOffset.UTC);
        return Timestamp.from(instant);
    }

    @Override
    public LocalDate convertToEntityAttribute(Timestamp timestamp) {
        if (timestamp != null) {
            Instant instant = timestamp.toInstant();
            return LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate();
        } else {
            return null;
        }
    }
}
