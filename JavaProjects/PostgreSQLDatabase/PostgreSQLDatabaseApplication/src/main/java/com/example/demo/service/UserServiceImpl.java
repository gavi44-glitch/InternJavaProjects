package com.example.demo.service;

import com.example.demo.DAO.UserDAO;
import com.example.demo.config.JWTService;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.example.demo.config.PasswordConfig;

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

//    private final PasswordConfig pwConfig;

//    @Autowired
//    AuthenticationManager authManager;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
//        this.pwConfig = pwConfig;
    }

    @Override
    @Transactional
    public User create(User newUser) {
//        String hashedPw = encoder.encode(newUser.getUserPass());
        String pw = newUser.getUserPass();
        String hashedPw = PasswordConfig.sha256(pw);
        System.out.println(pw);
        newUser.setUserPass(hashedPw);
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


        String hashed = PasswordConfig.sha256(userPass);
        User user = userDAO.login(userID, hashed);

        if (user == null){
            return "LOGIN FAILED";
        }

        return jwtService.generateToken(userID);

//        // jadi SPring security didesign untuk tiap enkripsi itu hasil hasnya akan selalu beda tiap saat
//        // jadinya yang dilookup dari databasenya itu pake
//        if(!encoder.matches(userPass, user.getUserPass())){
//            throw new RuntimeException("Invalid password!");
//        }
//        userPass = encoder.encode(userPass)
//        return userDAO.login(userID);

    }
}
