package ir.maktab127.repositories;

import ir.maktab127.entities.User;
import ir.maktab127.config.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.sql.*;

public class UserRepository {
    private final ApplicationContext context;

    public UserRepository(ApplicationContext context) {
        this.context = context;
    }

    public void createUserTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY," +
                "username VARCHAR(255) UNIQUE NOT NULL," +
                "password VARCHAR(255) NOT NULL)";
        try (Connection connection = context.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }

    public void registerUser(User user) throws SQLException {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection connection = context.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        }
    }

    public User login(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = context.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        }
        return null;
    }
}