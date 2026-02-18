package com.example.demo.service;

//import com.example.demo.repository.repo;
import com.example.demo.entity.User;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

//    private final repo userRepo ;

    private List<User> users = new ArrayList<>();
//    private User newUser;
    private long id;
    private String name;
    private String email;
    private long counter = 1L;

    public User create (User newUser){
        newUser.setId(counter++);
        users.add(newUser);
        return newUser;
    }

    public List<User> readAll (){
        return users;
    }

    public User findUserById(long id){

        for (int i =0; i< users.size(); i++){
            if(id == users.get(i).getId()){
                System.out.println("found!");
                return users.get(i);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found!");
    }

    public User updateUser(long id, User updatedUserData){

        User existingUser = findUserById(id);

        existingUser.setName(updatedUserData.getName());
        existingUser.setEmail(updatedUserData.getEmail());

        return existingUser;
    }

    public User deleteUser(long id){

        User existingUser = findUserById(id);
        users.remove(existingUser);

        return existingUser;
    }
//    public UserService(repo userRepo) {
//        this.userRepo = userRepo;
//    }
//
//    public List<entityTableUser> getAll(){
//        return userRepo.findAll();
//    }

    // coba cari kenapa ini ditypecasting, harusnya tanpa type casting juga bisa
//    public entityTableUser getById(Long id) throws Throwable {
//        return (entityTableUser) userRepo.findById(id)
//                .orElseThrow(()-> new RuntimeException("User not found"));
//    }

//    public entityTableUser save(entityTableUser user){
//        return userRepo.save(user);
//    }


}
