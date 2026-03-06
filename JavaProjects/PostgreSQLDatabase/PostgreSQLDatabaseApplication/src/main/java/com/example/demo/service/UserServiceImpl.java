package com.example.demo.service;

import com.example.demo.DAO.UserDAO;
import com.example.demo.utils.JWTService;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.example.demo.utils.PasswordConfig;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

//    @Autowired
//    BCryptPasswordEncoder encoder;

    private final UserDAO userDAO;

    @Autowired
    private JWTService jwtService;



    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
//        this.pwConfig = pwConfig;
    }

    @Override
    @Transactional
    public User create(User newUser) {
        String pw = newUser.getUserPass();
        String encryptedPw = PasswordConfig.passwordConfig(pw);

        System.out.println("Original: " + pw);
        System.out.println("Encrypted: " + encryptedPw);

        newUser.setUserPass(encryptedPw);
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

    @Override
    @Transactional
    public String login(String userID, String userPass)
    {

        String encrypted = PasswordConfig.passwordConfig(userPass);
        User user = userDAO.login(userID, encrypted);

        if (user == null){
            return "LOGIN FAILED";
        }

        return jwtService.generateToken(userID);



    }
}
