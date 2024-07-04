package com.sviryd.chat.service;

import com.sviryd.chat.domain.Message;
import com.sviryd.chat.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;

    public Page<Message> getMessages(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return messageRepo.findAll(pageable);
    }
}
