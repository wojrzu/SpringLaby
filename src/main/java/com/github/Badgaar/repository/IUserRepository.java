package com.github.Badgaar.repository;

import com.github.Badgaar.model.User;
import java.util.List;


public interface IUserRepository {
    User getUser(String login);
    User getUserById(String id);
    List<User> getUsers();
    void update(User user);
    void add(User user);
    void remove(String login);
}
