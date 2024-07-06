package com.sviryd.chat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sviryd.chat.domain.User;
import com.sviryd.chat.domain.type.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder({"id", "username", "gender"})
public class UserDTO {

    private Long id;
    @JsonProperty("name")
    private String username;
    @JsonProperty("male")
    private Gender gender;

    public static UserDTO toDTO(User user) {
        return UserDTO
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .gender(user.getGender())
                .build();
    }
}
