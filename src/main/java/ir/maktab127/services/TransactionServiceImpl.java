package ir.maktab127.services;

import ir.maktab127.entities.Card;
import ir.maktab127.entities.Transaction;
import ir.maktab127.repositories.CardRepository;
import ir.maktab127.repositories.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, CardRepository cardRepository) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public void transfer(String fromCard, String toCard, double amount, String type) throws Exception {
        Card sourceCard = cardRepository.getCardByNumber(fromCard);
        Card destinationCard = cardRepository.getCardByNumber(toCard);

        if (sourceCard == null || destinationCard == null) {
            throw new Exception("Invalid card number");
        }

        if (sourceCard.getBalance() < amount) {
            Transaction transaction = new Transaction(fromCard, toCard, amount, type, "Unsuccessful", 0);
            throw new Exception("Insufficient balance");
        }
        double fee;
        if (sourceCard.getBankName().equals(destinationCard.getBankName()) && type.equals("normal")) {
            fee = 0.0;
        } else {
            fee = calculateFee(amount, type);
        }

        cardRepository.updateBalance(fromCard, sourceCard.getBalance() - amount - fee);
        cardRepository.updateBalance(toCard, destinationCard.getBalance() + amount);

        Transaction transaction = new Transaction(fromCard, toCard, amount, type, "Success", fee);
        transactionRepository.saveTransaction(transaction);
    }

    @Override
    public void payaGroup(String fromCard, List<String> toCards, double amount, int quantity) throws Exception {
        Card sourceCard = cardRepository.getCardByNumber(fromCard);
        if (sourceCard == null) {
            throw new Exception("Invalid card number");
        }

        int validCardQty=0;
        List<Card>validCards=new ArrayList<>();
        for(String toCard:toCards){
            Card destinationCard = cardRepository.getCardByNumber(toCard);
            if (destinationCard != null) {
                validCardQty++;
                validCards.add(destinationCard);
            }
            else
            {
                System.out.println(toCard  + " Invalid card number");
                Transaction transaction1 = new Transaction(fromCard, toCard, amount, "GroupPaya", "Unsuccessful", 0);
                transactionRepository.saveTransaction(transaction1);
            }
        }
        double fee = calculateFeeGroupPaya(validCardQty);
        if (sourceCard.getBalance() < amount * quantity + fee) {
            throw new Exception("Insufficient balance");
        }

        for (Card toCard : validCards) {
            cardRepository.updateBalance(toCard.getCardNumber(), toCard.getBalance() + amount);
            transactionRepository.saveTransaction(new Transaction(fromCard, toCard.getCardNumber(), amount, "GroupPaya", "Success", fee));
        }

        cardRepository.updateBalance(fromCard, sourceCard.getBalance() - amount * validCardQty - fee);
        System.out.println("GroupPaya transfer "+validCardQty+ " of "+quantity+" successfully!");
    }

    @Override
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

    private double calculateFeeGroupPaya(int quantity) {
        if (quantity <= 10) {
            return 12000;
        }
        return 12000 + (quantity - 10) * 1200;
    }
} 