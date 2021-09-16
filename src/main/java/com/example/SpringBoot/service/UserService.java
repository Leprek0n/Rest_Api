package com.example.SpringBoot.service;

import com.example.SpringBoot.model.User;
import com.example.SpringBoot.transferObject.NewUserRequest;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();

    public User getUserById(Long id);

    public User getUserByUsername(String username);

    public void save(User user);

    public void updateUser(Long id, NewUserRequest updatedUser);

    public void deleteUser(Long id);

    public void save(NewUserRequest newUserRequest);



    public boolean existsByUsername(String username);
}
