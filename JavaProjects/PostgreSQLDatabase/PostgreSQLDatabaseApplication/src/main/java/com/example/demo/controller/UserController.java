package com.example.demo.controller;

import com.example.demo.DTO.Login;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
//import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "User Management", description = "CRUD User Operation Endpoints")
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
    @Operation(summary = "Create user")
    public User create(@RequestBody User user){
        System.out.println(user);
        return userService.create(user);
    }


    @PostMapping("/login")
    @Operation(summary = "Login user")
    public String login(@RequestBody Login login){

        // Menambahkan jwt token implementation ke service login ini
        return userService.login(login.getUserID(), login.getUserPass());

    }

    //  READ ALL
    @GetMapping("/read")
    @Operation(summary = "Read all users", security = @SecurityRequirement(name = "bearerAuth"))
    public List<User> readAll(){
        return userService.readAll();
    }
    //

    //FIND USER BY ID
    @GetMapping("/{userID}")
    @Operation(summary = "Find user by userID", security = @SecurityRequirement(name = "bearerAuth"))
    public User findUser(@PathVariable String userID){
        return userService.findUser(userID);
    }
    //
//
    @PatchMapping("/updateUser/{userID}")
    @Operation(summary = "Update user by userID", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<User> updateUsername(@PathVariable String userID, @RequestBody Map<String, Object> updates){
        User updatedUser = userService.updatedUsername(userID, updates);
        return ResponseEntity.ok(updatedUser);
    }
    //
    //SISA DELETE
    @DeleteMapping("/deleteUser/{userID}")
    @Operation(summary = "Delete user by userID", security = @SecurityRequirement(name = "bearerAuth"))
    public User deleteUser(@PathVariable String userID){
        return userService.deleteUser(userID);
    }


}
