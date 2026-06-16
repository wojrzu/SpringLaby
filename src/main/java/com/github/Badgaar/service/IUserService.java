package com.github.Badgaar.service;

import com.github.Badgaar.model.User;

import java.util.List;

public interface IUserService {
    void removeUser(String login);
    User findById(String userId);
    List<User> findAllUsers();
    void deleteUser(String userToBeDeletedID, String currentUserID);
}