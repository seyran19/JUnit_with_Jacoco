package org.example.service;

import org.example.dao.UserDao;
import org.example.dto.User;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class UserService {

    List<User> users = new ArrayList<>();

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public List<User> getAllUsers() {
        return users;
    }

    public void add(User user) {
        users.add(user);
    }

    public Optional<User> login(String username, int password) {
        if(username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        return users.stream()
                .filter(user -> user.getName().equals(username) && user.getPassword() == password)
                .findFirst();
    }

    public Map<Integer, User> getAllConvertedById() {
        return users.stream().collect(toMap(User::getId, Function.identity()));
    }

    public boolean deleteById(int id) {
        return userDao.delete(id);
    }
}
