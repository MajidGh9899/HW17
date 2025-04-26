package ir.maktab127.services;

import ir.maktab127.entities.Card;
import ir.maktab127.entities.Transaction;
import ir.maktab127.repositories.CardRepository;
import ir.maktab127.repositories.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

public interface TransactionService {
    void transfer(String fromCard, String toCard, double amount, String type) throws Exception;
    void payaGroup(String fromCard, List<String> toCards, double amount, int quantity) throws Exception;
    List<Transaction> getTransactionHistory(String cardNumber) throws Exception;
}