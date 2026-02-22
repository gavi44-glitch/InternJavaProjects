package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
//@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private long id;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //CREATE USER
    @PostMapping("/create")
    public User create(@RequestBody User user){
//        System.out.println("masuk");
        return userService.create(user);
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
