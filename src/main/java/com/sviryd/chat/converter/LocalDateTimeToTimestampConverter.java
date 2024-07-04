package com.sviryd.chat.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Converter(autoApply = true)
public class LocalDateTimeToTimestampConverter implements AttributeConverter<LocalDateTime, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime ldt) {
        if (ldt != null) {
            Instant instant = ldt.toInstant(ZoneOffset.UTC);
            return Timestamp.from(instant);
        } else {
            return null;
        }
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        if (timestamp != null) {
            Instant instant = timestamp.toInstant();
            return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        } else {
            return null;
        }
    }
}
