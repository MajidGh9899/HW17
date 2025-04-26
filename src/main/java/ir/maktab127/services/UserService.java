package ir.maktab127.services;

import ir.maktab127.entities.User;

import java.sql.SQLException;

public interface UserService {
    void register(User user) throws Exception;
    User login(String username, String password) throws Exception;
}