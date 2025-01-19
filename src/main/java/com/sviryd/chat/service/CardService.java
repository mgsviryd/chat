package com.sviryd.chat.service;

import com.sviryd.chat.domain.Card;
import com.sviryd.chat.repo.CardRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CardService {
    private final CardRepo cardRepo;

    public CardService(final CardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }
    public Optional<Card> findById(UUID id){
        return cardRepo.findById(id);
    }
    public Card save(Card card){
        return cardRepo.save(card);
    }
}
