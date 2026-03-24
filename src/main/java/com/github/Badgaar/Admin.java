package com.github.Badgaar;

public class Admin {
    String login;
    String password;
    Role role;

    Admin() {
        role = Role.ADMIN;
    }

    void hashPassword(String password) {
    }

}
