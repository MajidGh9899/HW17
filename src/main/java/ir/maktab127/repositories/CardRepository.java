package ir.maktab127.repositories;

import ir.maktab127.config.ApplicationContext;
import ir.maktab127.entities.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;


public class CardRepository {
    private final ApplicationContext context;

    public CardRepository(ApplicationContext context) {
        this.context = context;
    }

    // Create cards table if not exists
    public void createCardTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS cards (" +
                "id SERIAL PRIMARY KEY," +
                "user_id INT NOT NULL," +
                "card_number VARCHAR(255) UNIQUE NOT NULL," +
                "bank_name VARCHAR(255) NOT NULL," +
                "balance DOUBLE PRECISION NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES users(id))";
        try (Connection connection = context.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }


    public void addCard(Card card) throws SQLException {
        String query = "INSERT INTO cards (user_id, card_number, bank_name, balance) VALUES (?, ?, ?, ?)";
        try (Connection connection = context.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, card.getUserId());
            statement.setString(2, card.getCardNumber());
            statement.setString(3, card.getBankName());
            statement.setDouble(4, card.getBalance());
            statement.executeUpdate();
        }
    }


    public void deleteCard(String cardNumber) throws SQLException {
        String query = "DELETE FROM cards WHERE card_number = ?";
        try (Connection connection = context.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cardNumber);
            statement.executeUpdate();
        }
    }


    public Card getCardByNumber(String cardNumber) throws SQLException {
        String query = "SELECT * FROM cards WHERE card_number = ?";
        try (Connection connection = context.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cardNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Card card = new Card();
                card.setId(resultSet.getInt("id"));
                card.setUserId(resultSet.getInt("user_id"));
                card.setCardNumber(resultSet.getString("card_number"));
                card.setBankName(resultSet.getString("bank_name"));
                card.setBalance(resultSet.getDouble("balance"));
                return card;
            }
        }
        return null;
    }


    public List<Card> getCardsByBank(String bankName) throws SQLException {
        List<Card> cards = new ArrayList<>();
        String query = "SELECT * FROM cards WHERE bank_name = ?";
        try (Connection connection = context.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, bankName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Card card = new Card();
                card.setId(resultSet.getInt("id"));
                card.setUserId(resultSet.getInt("user_id"));
                card.setCardNumber(resultSet.getString("card_number"));
                card.setBankName(resultSet.getString("bank_name"));
                card.setBalance(resultSet.getDouble("balance"));
                cards.add(card);
            }
        }
        return cards;
    }


    public List<Card> getAllCards() throws SQLException {
        List<Card> cards = new ArrayList<>();
        String query = "SELECT * FROM cards";
        try (Connection connection = context.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Card card = new Card();
                card.setId(resultSet.getInt("id"));
                card.setUserId(resultSet.getInt("user_id"));
                card.setCardNumber(resultSet.getString("card_number"));
                card.setBankName(resultSet.getString("bank_name"));
                card.setBalance(resultSet.getDouble("balance"));
                cards.add(card);
            }
        }
        return cards;
    }


    public boolean updateBalance(String cardNumber, double newBalance) throws SQLException {
        String query = "UPDATE cards SET balance = ? WHERE card_number = ?";
        try (Connection connection = context.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, newBalance);
            statement.setString(2, cardNumber);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
}