package com.sviryd.chat.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonPropertyOrder({"result", "count", "data"})
public class MessagesDTO {
    private boolean result;
    private int count;
    private List<MessageDTO> data;
}
