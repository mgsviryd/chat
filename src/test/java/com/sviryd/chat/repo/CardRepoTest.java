package com.sviryd.chat.repo;

import com.sviryd.chat.domain.Card;
import com.sviryd.chat.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles(value = "test")
public class CardRepoTest {

    @Autowired
    private CardRepo cardRepo;

    @Autowired
    private UserRepo userRepo;
    private Card card;
    private User user;
    private User user1;

    @BeforeEach
    public void init(){
        initAndSaveUser();
        initAndSaveCard();
    }

    private void initAndSaveCard() {
        initCard();
        saveCard();
    }

    private void initAndSaveUser() {
        initUser();
        saveUser();
    }

    private void saveCard() {
        cardRepo.save(card);
    }

    private void initCard() {
        card = Card.builder().word("apple").translation("яблоко").author(user).build();
    }

    private void saveUser() {
        user = userRepo.save(user);
        user1 = userRepo.save(user1);
    }

    private void initUser() {
        user = User.builder().username("pasha").enabled(true).build();
        user1 = User.builder().username("misha").enabled(true).build();
    }

    @Test
    public void findAll(){
        card.setAuthor(user1);
        saveCard();
        User author = card.getAuthor();
        System.out.println(author);

        List<Card> all = cardRepo.findAll();
        assertEquals(1, all.size());
    }
}
