package ir.maktab127.entities;

public class Card {
    private int id;
    private int userId;
    private String cardNumber;
    private String bankName;
    private double balance;

    public Card() {}
    public Card(int userId, String cardNumber, String bankName, double balance) {
        this.userId = userId;
        this.cardNumber = cardNumber;
        this.bankName = bankName;
        this.balance = balance;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
