package ir.maktab127.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "from_card", nullable = false)
    private String fromCard;
    
    @Column(name = "to_card", nullable = false)
    private String toCard;
    
    @Column(name = "amount", nullable = false)
    private double amount;
    
    @Column(name = "transaction_type", nullable = false)
    private String transactionType;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @Column(name = "fee", nullable = false)
    private double fee;

    public Transaction(String fromCard, String toCard, double amount, String transactionType, String status, double fee) {
        this.fromCard = fromCard;
        this.toCard = toCard;
        this.amount = amount;
        this.transactionType = transactionType;
        this.status = status;
        this.fee = fee;
    }

    @Override
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