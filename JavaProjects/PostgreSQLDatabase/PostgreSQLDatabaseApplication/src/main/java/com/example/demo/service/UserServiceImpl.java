package com.example.demo.service;

import com.example.demo.DAO.UserDAO;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public User create(User newUser) {
        return userDAO.create(newUser);
    }

    @Override
    @Transactional
    public List<User> readAll(){
        return userDAO.readAll();
    }

    @Override
    @Transactional
    public User findUser(String id){
        return userDAO.findUser(id);
    }

    @Override
    @Transactional
    public User updatedUsername(String id, Map<String, Object> updates){
        return userDAO.updatedUsername(id, updates);
    }

    @Override
    @Transactional
    public User deleteUser(String id){
        return userDAO.deleteUser(id);
    }
}
