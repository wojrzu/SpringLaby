package com.github.Badgaar;

import java.util.List;

public interface IUserRepository {
    User getUser();
    List<User> getUsers();
    void save();
    void load();
    void update();
}
