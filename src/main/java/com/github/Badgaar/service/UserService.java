package com.github.Badgaar.service;

import com.github.Badgaar.impl.Role;
import com.github.Badgaar.repository.IRentalRepository;
import com.github.Badgaar.impl.User;
import com.github.Badgaar.repository.IUserRepository;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserService {

    List<User> Users = new ArrayList<>();
    IUserRepository userRepository;
    IRentalRepository rentalRepository;

    public UserService(IUserRepository userRepository, IRentalRepository rentalRepository) {
        this.Users = new ArrayList<>();
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }

    public void removeUser(String login) {
        User user = userRepository.getUser(login);
        /*if (user == null) {
            return;
        }*/

        userRepository.remove(login);
    }

    public User findById(String userId) {
        User user = userRepository.getUser(userId);
        if(user == null) {
            return null;
        }

        return user;
    }

    public List<User> findAllUsers() {
        return Users;
    }

    public void deleteUser(String userToBeDeletedID, String currentUserID) {
        User currentUser = userRepository.getUser(currentUserID);
        if (currentUser == null || !currentUser.getRole().equals(Role.ADMIN)) {
            return;
        }

        User user = userRepository.getUser(userToBeDeletedID);
        if(user == null) {
            return;
        }
        userRepository.remove(userToBeDeletedID);
    }
}
