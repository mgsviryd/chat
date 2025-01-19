package com.sviryd.chat.repo;

import com.sviryd.chat.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CardRepo extends CrudRepository<Card, UUID>, JpaRepository<Card, UUID> {
}
