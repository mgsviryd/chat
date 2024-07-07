package com.sviryd.chat.controller.rest;

import com.sviryd.chat.domain.Message;
import com.sviryd.chat.domain.User;
import com.sviryd.chat.dto.MessageDTO;
import com.sviryd.chat.dto.MessagesDTO;
import com.sviryd.chat.service.MessageService;
import com.sviryd.chat.service.UserService;
import com.sviryd.chat.util.OffsetBasedPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
public class MessageRestController {
    private static final Sort BY_CREATION_LDT_ASC = Sort.by("creationLDT").ascending();
    private final MessageService messageService;
    private final UserService userService;

    public MessageRestController(final MessageService messageService, final UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/messages")
    public MessagesDTO getOffsetBasedPage(
            @RequestParam(value = "offset", defaultValue = "0", required = false) long offset,
            @RequestParam(value = "limit", defaultValue = "100", required = false) int limit
    ) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit, BY_CREATION_LDT_ASC);
        Page<Message> page = messageService.getPage(pageable);
        List<Message> messages = new ArrayList<>(page.getContent());
        messages.sort(Comparator.comparing(Message::getCreationLDT).reversed());
        List<MessageDTO> dtos = messages.stream().map(MessageDTO::toDTO).toList();
        return MessagesDTO.builder()
                .result(true)
                .count(dtos.size())
                .data(dtos)
                .build();
    }


    @PostMapping("/messages")
    public MessageDTO save(
            @AuthenticationPrincipal User user,
            Principal principal,
            @RequestBody String text
    ) {
        if (user == null) {
            user = userService.findByUsername(principal.getName());
        }
        Message message = new Message();
        message.setAuthor(user);
        message.setAuthorId(user.getId());
        message.setText(text);
        message = messageService.save(message);
        return MessageDTO.toDTO(message);
    }
}
