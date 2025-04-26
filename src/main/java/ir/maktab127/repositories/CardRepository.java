package ir.maktab127.repositories;

import ir.maktab127.entities.Card;

import java.sql.SQLException;
import java.util.List;

public interface CardRepository {
    void createCardTable() throws SQLException;
    void addCard(Card card) throws SQLException;
    void deleteCard(String cardNumber) throws SQLException;
    Card getCardByNumber(String cardNumber) throws SQLException;
    List<Card> getCardsByBank(String bankName) throws SQLException;
    List<Card> getAllCards() throws SQLException;
    boolean updateBalance(String cardNumber, double newBalance) throws SQLException;
}