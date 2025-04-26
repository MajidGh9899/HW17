package ir.maktab127.services;

import ir.maktab127.entities.User;
import ir.maktab127.repositories.UserRepository;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(User user) throws Exception {
        userRepository.registerUser(user);
    }

    @Override
    public User login(String username, String password) throws Exception {
        return userRepository.login(username, password);
    }
} 