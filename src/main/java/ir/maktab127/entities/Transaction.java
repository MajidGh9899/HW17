package ir.maktab127.entities;

public class Transaction {
    private int id;
    private String fromCard;
    private String toCard;
    private double amount;
    private String transactionType;
    private String status;
    private double fee;

    public Transaction() {}

    public Transaction(String fromCard, String toCard, double amount, String transactionType, String status, double fee) {
        this.fromCard = fromCard;
        this.toCard = toCard;
        this.amount = amount;
        this.transactionType = transactionType;
        this.status = status;
        this.fee = fee;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromCard() {
        return fromCard;
    }

    public void setFromCard(String fromCard) {
        this.fromCard = fromCard;
    }

    public String getToCard() {
        return toCard;
    }

    public void setToCard(String toCard) {
        this.toCard = toCard;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", fromCard='" + fromCard + '\'' +
                ", toCard='" + toCard + '\'' +
                ", amount=" + amount +
                ", transactionType='" + transactionType + '\'' +
                ", status='" + status + '\'' +
                ", fee=" + fee +

                '}';
    }
}