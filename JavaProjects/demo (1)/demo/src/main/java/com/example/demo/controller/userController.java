package com.example.demo.controller;

//import com.example.demo.entity.entityTableUser;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.User;
//import com.example.demo.repository.repo;
import java.util.List;
import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class userController {

    private final UserService userService;
    private long id;

    public userController(UserService userService) {
        this.userService = userService;
    }

    //CREATE USER
    @PostMapping("/create")
    public User create(@RequestBody User user){
        System.out.println("masuk");
        return userService.create(user);
    }

    //READ ALL
    @GetMapping("/read")
    public List<User> readAll(){
        return userService.readAll();
    }

    //FIND USER BY ID
    @GetMapping("/{id}")
    public User findUser(@PathVariable long id){
        return userService.findUserById(id);
    }


    @PutMapping("/updateUser/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User updatedUserData){
        return userService.updateUser(id, updatedUserData);
    }

    //SISA DELETE
    @DeleteMapping("/deleteUser/{id}")
    public User deleteUser(@PathVariable long id){
        return userService.deleteUser(id);
    }

}
