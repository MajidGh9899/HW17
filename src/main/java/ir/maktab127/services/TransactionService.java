package ir.maktab127.services;

import ir.maktab127.entities.Card;
import ir.maktab127.entities.Transaction;
import ir.maktab127.repositories.CardRepository;
import ir.maktab127.repositories.TransactionRepository;

import java.util.List;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;

    public TransactionService(TransactionRepository transactionRepository, CardRepository cardRepository) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
    }

    public void transfer(String fromCard, String toCard, double amount, String type) throws Exception {



        Card sourceCard = cardRepository.getCardByNumber(fromCard);
        Card destinationCard = cardRepository.getCardByNumber(toCard);

        if (sourceCard == null || destinationCard == null) {
            throw new Exception("Invalid card number");
        }


        if (sourceCard.getBalance() < amount) {
            throw new Exception("Insufficient balance");
        }
        double fee;
        if(sourceCard.getBankName().equals(destinationCard.getBankName()) && type.equals("normal")){
            fee=0.0;
        }
        else
        {fee = calculateFee(amount, type);}


        cardRepository.updateBalance(fromCard, sourceCard.getBalance() - amount - fee);
        cardRepository.updateBalance(toCard, destinationCard.getBalance() + amount);


        Transaction transaction = new Transaction(fromCard, toCard, amount, type, "Success", fee);
        transactionRepository.saveTransaction(transaction);
    }
    public List<Transaction> getTransactionHistory(String cardNumber) throws Exception {
        return transactionRepository.getTransactionsByUser(cardNumber);
    }

    private double calculateFee(double amount, String type) {

        switch (type) {
            case "normal":
                if (amount <= 10_000_000) {
                    return 720;
                } else {
                    return 720 + ((amount - 10_000_000) / 1_000_000) * 100;
                }
            case "paya":
                return amount * 0.001;
            case "satna":
                return amount * 0.002;
            default:
                return 0;
        }
    }
}