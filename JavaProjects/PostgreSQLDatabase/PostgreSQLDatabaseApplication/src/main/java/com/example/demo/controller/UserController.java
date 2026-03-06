package com.example.demo.controller;

import com.example.demo.DTO.Login;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
//import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
//@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
//    private final Login login;
    private long id;
//    private String username;
//    private String userpass;

    public UserController(UserService userService) {
        this.userService = userService;
//        this.login = login;
    }

    //CREATE USER
    @PostMapping("/create")
    public User create(@RequestBody User user){
//        System.out.println("masuk");
        return userService.create(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Login login){
//        System.out.println(login.getUserID());
//        System.out.println(login.getUserPass());

        // Menambahkan jwt token implementation ke service login ini
        return userService.login(login.getUserID(), login.getUserPass());

//        return userService.login(userID, userPass);
    }

    //  READ ALL
    @GetMapping("/read")
    public List<User> readAll(){
        return userService.readAll();
    }
    //
    //FIND USER BY ID
    @GetMapping("/{id}")
    public User findUser(@PathVariable String id){
        return userService.findUser(id);
    }
    //
//
    @PatchMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUsername(@PathVariable String id, @RequestBody Map<String, Object> updates){
        User updatedUser = userService.updatedUsername(id, updates);
        return ResponseEntity.ok(updatedUser);
    }
    //
    //SISA DELETE
    @DeleteMapping("/deleteUser/{id}")
    public User deleteUser(@PathVariable String id){
        return userService.deleteUser(id);
    }


}
