package com.github.Badgaar.repository;

import com.github.Badgaar.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Repository
@Profile("json")
public class UserRepository implements IUserRepository {
    List<User> users = new ArrayList<>();
    String usersPath = "users.json";
    private Gson gson = new GsonBuilder().create();

    public UserRepository() {
        load();
    }

    @Override
    public User getUser(String login){
        for(User user : users) {
            if (user.login.equals(login)){
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserById(String id) {
        for(User user : users) {
            if(user.id.equals(id)) return user;
        }
        return null;
    }

    @Override
    public List<User> getUsers(){
        return users;
    }

    private void load() {
        File file = new File(usersPath);
        if (!file.exists()) return;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            User[] loaded = gson.fromJson(reader, User[].class);
            reader.close();
            if (loaded != null) {
                users.addAll(Arrays.asList(loaded));
            }
        } catch (IOException e) {
        }
    }

    private void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(usersPath));
            gson.toJson(users, writer);
            writer.close();
        } catch (IOException e) {
        }
    }

    @Override
    public void update(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).login.equals(user.login)) {
                users.set(i, user);
                save();
                return;
            }
        }
    }

    @Override
    public void add(User user) {
        users.add(user);
        save();
    }

    @Override
    public void remove(String login) {
        users.removeIf(user -> user.login.equals(login));
        save();
    }
}