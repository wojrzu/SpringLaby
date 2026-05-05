package com.github.Badgaar.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.github.Badgaar.repository.IUserRepository;
import com.github.Badgaar.impl.Role;
import com.github.Badgaar.impl.User;
import com.github.Badgaar.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

public class AuthService {

    private IUserRepository userRepository;

    public AuthService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(String login, String password) {
        if(login == null || password == null) {
            return false;
        }

        if (userRepository.getUser(login) != null) {
            return false;
        }

        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        User user = new User(UUID.randomUUID().toString(), login, hashedPassword, Role.USER);
        userRepository.add(user);
        return true;
    }

    public Optional<User> login(String login, String password) {
        User user = userRepository.getUser(login);
        if(user == null) {
            return Optional.empty();
        }

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.passwordHash);
        if(result.verified) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
