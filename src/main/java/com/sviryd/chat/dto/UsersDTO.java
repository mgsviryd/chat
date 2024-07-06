package com.sviryd.chat.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonPropertyOrder({"result", "data"})
public class UsersDTO {
    private boolean result;
    private List<UserDTO> data;
}
