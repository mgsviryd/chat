package com.sviryd.chat.domain.resource;

import lombok.*;

@Value
public class UserResource {
    @NonNull
    String username;
    @NonNull
    String password;
}
