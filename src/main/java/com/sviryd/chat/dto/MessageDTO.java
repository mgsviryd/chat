package com.sviryd.chat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sviryd.chat.domain.Message;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@Builder
@JsonPropertyOrder({"id", "time", "authorId", "message"})
public class MessageDTO {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");

    private Long id;
    @JsonProperty("time")
    private String creationLDT;
    private Long authorId;
    private String message;

    public static MessageDTO toDTO(Message message) {
        return MessageDTO
                .builder()
                .id(message.getId())
                .creationLDT(message.getCreationLDT().format(FORMATTER))
                .authorId(message.getAuthorId())
                .message(message.getText())
                .build();
    }
}
