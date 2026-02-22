package com.example.demo.DAO;

import com.example.demo.model.User;

import java.util.List;
import java.util.Map;

public interface UserDAO {
    public User create(User newUser);
    public List<User> readAll();
    public User findUser(String id);
    public User updatedUsername(String id, Map<String,Object> updates);
    public User deleteUser(String id);
}
