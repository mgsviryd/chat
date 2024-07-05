package com.sviryd.chat.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.sviryd.chat.domain.Message;
import com.sviryd.chat.domain.User;
import com.sviryd.chat.domain.Views;
import com.sviryd.chat.service.MessageService;
import com.sviryd.chat.util.OffsetBasedPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@RestController
public class MessageRestController {
    private static final Sort BY_CREATION_LDT_ASC = Sort.by("creationLDT").ascending();
    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    @JsonView({Views.Messages.class})
    public HashMap<Object, Object> getOffsetBasePage(
            @RequestParam(value = "offset", defaultValue = "0", required = false) long offset,
            @RequestParam(value = "limit", defaultValue = "100", required = false) int limit
    ) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit, BY_CREATION_LDT_ASC);
        Page<Message> page = messageService.getPage(pageable);
        List<Message> messages = page.getContent();
        messages.sort(Comparator.comparing(Message::getCreationLDT).reversed());
        HashMap<Object, Object> data = new HashMap<>();
        data.put("result", true);
        data.put("count", messages.size());
        data.put("data", messages);
        return data;
    }

    @PostMapping("/messages")
    public Message save(
            @AuthenticationPrincipal User user,
            @RequestBody String text
    ) {
        Message message = new Message();
        message.setAuthor(user);
        message.setMessage(text);
        return messageService.save(message);
    }
}
