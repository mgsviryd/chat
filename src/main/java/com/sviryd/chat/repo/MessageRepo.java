package com.sviryd.chat.repo;

import com.sviryd.chat.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface MessageRepo extends JpaRepository<Message, UUID>, PagingAndSortingRepository<Message, UUID> {
    Page<Message> findAll(Pageable pageable);
}
