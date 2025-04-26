package ir.maktab127.services;

import ir.maktab127.entities.Card;

import java.util.List;

public interface CardService {
    void addCard(Card card) throws Exception;
    void deleteCard(String cardNumber) throws Exception;
    Card getCardByNumber(String cardNumber) throws Exception;
    List<Card> getCardsByBank(String bankName) throws Exception;
    List<Card> getAllCards() throws Exception;
    boolean updateBalance(String cardNumber, double newBalance) throws Exception;
}
