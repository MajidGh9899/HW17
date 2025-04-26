package ir.maktab127.repositories;

import ir.maktab127.config.ApplicationContext;
import ir.maktab127.entities.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface TransactionRepository {
    void createTransactionTable() throws SQLException;
    void saveTransaction(Transaction transaction) throws SQLException;
    List<Transaction> getTransactionsByUser(String cardNumber) throws SQLException;
}
