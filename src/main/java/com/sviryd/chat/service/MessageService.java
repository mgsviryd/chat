package com.sviryd.chat.service;

import com.sviryd.chat.domain.Message;
import com.sviryd.chat.repo.MessageRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepo messageRepo;

    public MessageService(final MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public Page<Message> getPage(Pageable pageable) {
        return messageRepo.findAll(pageable);
    }

    public Message save(Message message) {
        return messageRepo.save(message);
    }

}
