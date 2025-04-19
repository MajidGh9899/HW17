package ir.maktab127.repositories;

import ir.maktab127.config.ApplicationContext;
import ir.maktab127.entities.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TransactionRepository {
    private final ApplicationContext context;

    public TransactionRepository(ApplicationContext context) {
        this.context = context;
    }

    public void createTransactionTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id SERIAL PRIMARY KEY," +
                "from_card VARCHAR(255) NOT NULL," +
                "to_card VARCHAR(255) NOT NULL," +
                "amount DOUBLE PRECISION NOT NULL," +
                "transaction_type VARCHAR(255) NOT NULL," +
                "status VARCHAR(255) NOT NULL," +
                "fee DOUBLE PRECISION NOT NULL)";
        try (Connection connection = context.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }

    public void saveTransaction(Transaction transaction) throws SQLException {
        String query = "INSERT INTO transactions (from_card, to_card, amount, transaction_type, status, fee) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = context.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, transaction.getFromCard());
            statement.setString(2, transaction.getToCard());
            statement.setDouble(3, transaction.getAmount());
            statement.setString(4, transaction.getTransactionType());
            statement.setString(5, transaction.getStatus());
            statement.setDouble(6, transaction.getFee());
            statement.executeUpdate();
        }
    }
    public List<Transaction> getTransactionsByUser(String cardNumber) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE from_card = ? OR to_card = ?";
        try (Connection connection = context.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cardNumber);
            statement.setString(2, cardNumber);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(resultSet.getInt("id"));
                transaction.setFromCard(resultSet.getString("from_card"));
                transaction.setToCard(resultSet.getString("to_card"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setTransactionType(resultSet.getString("transaction_type"));
                transaction.setStatus(resultSet.getString("status"));
                transaction.setFee(resultSet.getDouble("fee"));
                transactions.add(transaction);
            }
        }
        return transactions;
    }

}
