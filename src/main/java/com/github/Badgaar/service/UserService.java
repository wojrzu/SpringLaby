package com.github.Badgaar.service;

import com.github.Badgaar.model.Role;
import com.github.Badgaar.model.User;
import com.github.Badgaar.repository.IRentalRepository;
import com.github.Badgaar.repository.IUserRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Service
public class UserService implements IUserService {

    List<User> Users = new ArrayList<>();
    IUserRepository userRepository;
    IRentalRepository rentalRepository;

    public UserService(IUserRepository userRepository, IRentalRepository rentalRepository) {
        this.Users = new ArrayList<>();
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }

    @Override
    public void removeUser(String login) {
        userRepository.remove(login);
    }

    @Override
    public User findById(String userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.getUsers();
    }

    @Override
    public void deleteUser(String userToBeDeletedID, String currentUserID) {
        User currentUser = userRepository.getUserById(currentUserID);
        if (currentUser == null || !currentUser.getRole().equals(Role.ADMIN)) {
            return;
        }

        User user = userRepository.getUser(userToBeDeletedID);
        if (user == null) {
            return;
        }
        userRepository.remove(userToBeDeletedID);
    }
}