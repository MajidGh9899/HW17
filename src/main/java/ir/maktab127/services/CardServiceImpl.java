package ir.maktab127.services;

import ir.maktab127.entities.Card;
import ir.maktab127.repositories.CardRepository;

import java.util.List;

public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void addCard(Card card) throws Exception {
        cardRepository.addCard(card);
    }

    @Override
    public void deleteCard(String cardNumber) throws Exception {
        cardRepository.deleteCard(cardNumber);
    }

    @Override
    public Card getCardByNumber(String cardNumber) throws Exception {
        return cardRepository.getCardByNumber(cardNumber);
    }

    @Override
    public List<Card> getCardsByBank(String bankName) throws Exception {
        return cardRepository.getCardsByBank(bankName);
    }

    @Override
    public List<Card> getAllCards() throws Exception {
        return cardRepository.getAllCards();
    }

    @Override
    public boolean updateBalance(String cardNumber, double newBalance) throws Exception {
        return cardRepository.updateBalance(cardNumber, newBalance);
    }
} 