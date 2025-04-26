package ir.maktab127.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cards")

@Getter
@Setter
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "user_id", nullable = false)
    private int userId;
    
    @Column(name = "card_number", unique = true, nullable = false)
    private String cardNumber;
    
    @Column(name = "bank_name", nullable = false)
    private String bankName;
    
    @Column(name = "balance", nullable = false)
    private double balance;

    public Card(int userId, String cardNumber, String bankName, double balance) {
        this.userId = userId;
        this.cardNumber = cardNumber;
        this.bankName = bankName;
        this.balance = balance;
    }
    

}
