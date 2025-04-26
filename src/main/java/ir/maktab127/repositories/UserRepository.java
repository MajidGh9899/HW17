package ir.maktab127.repositories;

import ir.maktab127.entities.User;
import ir.maktab127.config.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.sql.*;

public interface UserRepository {
    void createUserTable() throws SQLException;
    void registerUser(User user) throws SQLException;
    User login(String username, String password) throws SQLException;
}