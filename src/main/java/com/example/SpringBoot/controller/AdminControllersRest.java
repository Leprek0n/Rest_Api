package com.example.SpringBoot.controller;


import com.example.SpringBoot.model.User;
import com.example.SpringBoot.service.UserServiceImpl;
import com.example.SpringBoot.transferObject.NewUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/users/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminControllersRest {

    private UserServiceImpl userService;

    @Autowired
    public AdminControllersRest(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<User>> getAllUsers() {
        final List<User> userList = userService.getAllUsers();

        return userList != null && !userList.isEmpty()
                ? ResponseEntity.ok(userList)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/principal")
    public ResponseEntity<User> principal(Principal principal) {

        return ResponseEntity.ok(userService.getUserByUsername(principal.getName()));
    }

    @PostMapping(value = "/newUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> newUserCreate(@RequestBody NewUserRequest newUserRequest) {
        if (userService.existsByUsername(newUserRequest.getUsername())) {

            return ResponseEntity.badRequest().body("Username is exist");
        }

        userService.save(newUserRequest);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
    }


    @PatchMapping(value = "/{id}/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @PathVariable String username, @RequestBody NewUserRequest userRequest) {
        if (userService.existsByUsername(userRequest.getUsername())
                & !(userRequest.getUsername().equals(username))) {

            return ResponseEntity.badRequest().body("Username is exist");
        }
        userService.updateUser(id, userRequest);
        return ResponseEntity.ok().build();
    }

}