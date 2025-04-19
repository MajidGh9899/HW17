package ir.maktab127.services;

import ir.maktab127.entities.Card;
import ir.maktab127.repositories.CardRepository;

import java.util.List;


public class CardService {
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void addCard(Card card) throws Exception {

        cardRepository.addCard(card);
    }

    public void deleteCard(String cardNumber) throws Exception {
        cardRepository.deleteCard(cardNumber);
    }

    public Card getCardByNumber(String cardNumber) throws Exception {
        return cardRepository.getCardByNumber(cardNumber);
    }

    public List<Card> getCardsByBank(String bankName) throws Exception {
        return cardRepository.getCardsByBank(bankName);
    }

    public List<Card> getAllCards() throws Exception {
        return cardRepository.getAllCards();
    }

    public boolean updateBalance(String cardNumber, double newBalance) throws Exception {
        return cardRepository.updateBalance(cardNumber, newBalance);
    }
}
