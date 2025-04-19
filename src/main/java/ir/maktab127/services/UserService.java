package ir.maktab127.services;

import ir.maktab127.entities.User;
import ir.maktab127.repositories.UserRepository;



public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(User user) throws Exception {

        userRepository.registerUser(user);
    }

    public User login(String username, String password) throws Exception {
        return userRepository.login(username, password);
    }
}