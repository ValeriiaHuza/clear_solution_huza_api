package com.springbootcourse.clear_solution_huza_api.controller;

import com.springbootcourse.clear_solution_huza_api.entity.User;
import com.springbootcourse.clear_solution_huza_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/users/{userId}")
    public User findUserById (@PathVariable int userId){
        return userService.findById(userId);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addNewUser(@Valid @RequestBody User user){
        user.setId(0);

        try {
            return new ResponseEntity<>(this.userService.save(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //check validation
    @PutMapping("/users/{userId}")
    public User updateNewUser(@RequestBody User user, @PathVariable int userId){
        return userService.update(user, userId);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUserById(@PathVariable int userId){
        userService.deleteById(userId);
    }
}
